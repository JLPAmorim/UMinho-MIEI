package soundshareclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientDownloadThread extends Thread {

    private final InetAddress serverAddress;
    private final int serverPort;
    private final String filename;
    private final String token;
    private final long size;

    /**
     * esta thread vai correr independentemente do thread principal para efetuar
     * o download de uma musica do servidor ao establecer a ligacao ao server
     * vai comecar por enviar o comando DOWNLOAD seguido do token aguarda por
     * uma resposta afirmativa e depois recebe o ficheiro em blocos MAX_SIZE
     * bytes, ao mesmo tempo que recebe grava no ficheiro
     *
     * @param serverAddress
     * @param serverPort
     * @param filename
     * @param token
     */
    ClientDownloadThread(InetAddress serverAddress, int serverPort, String filename, String token, long size) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.filename = filename;
        this.token = token;
        this.size = size;
    }

    @Override
    public void run() {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        Socket client = null;
        try {
            client = new Socket(serverAddress, serverPort);
            client.setSoTimeout(1000);
            in = new BufferedInputStream(client.getInputStream());
            out = new BufferedOutputStream(client.getOutputStream());
            //envia o pedido seguido do token e do tamanho do ficheiro
            out.write("DOWNLOAD\n".getBytes());
            out.write((token + "\n").getBytes());
            out.flush();

            //aguarda 30 segundos pela resposta
            String response = readString(in, 30);
            if (response == null) {
                System.out.println("Thread Download token " + token + ": tempo de espera excedido");
                return;
            }

            switch (response.toUpperCase()) {
                case "OK":
                    //aumenta o timeout para 10 minutos
                    client.setSoTimeout(10*60*1000);
                    downloadFile(in, out);
                    break;
                default:
                    System.out.println("Thread Download token " + token + ": resposta inesperada: " + response);
            }

        } catch (IOException ex) {
            System.out.println("Thread Download token " + token + ": Erro inesperado: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException ex) {
                System.out.println("Thread Download token " + token + ": Erro ao fechar o socket: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void downloadFile(BufferedInputStream in, BufferedOutputStream out) {
        BufferedOutputStream fout = null;
        try {
            byte[] buffer = new byte[SoundShareClient.MAX_SIZE];
            
            //cria um ficheiro novo, vai recebendo do servidor e escrevendo no ficheiro
            fout = new BufferedOutputStream(new FileOutputStream(filename));

            long bytesRead=0;
            while (bytesRead < size) {
                int n = in.read(buffer, 0, (int) (size - bytesRead > 1024 ? 1024 : size - bytesRead));
                fout.write(buffer, 0, n);
                bytesRead += n;
                if (bytesRead % SoundShareClient.MAX_SIZE*100==0)
                    System.out.println("Thread Download token " + token + ": " + bytesRead + " bytes recebidos.");
            }
            System.out.println("Thread Download token " + token + ": " + bytesRead + " bytes recebidos.");
            
            System.out.println("Thread Download token " + token + ": Ficheiro recebido com sucesso: " + filename);
        } catch (SocketTimeoutException ex) {
            System.out.println("Thread Download token " + token + ": tempo excedido");
        } catch (IOException ex) {
            System.out.println("Thread Download token " + token + ": erro inesperado: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    /**
     * le uma string a partir do BufferedInputStream
     * @param i
     * @return null se tempo de espera excedido
     */
    private String readString(BufferedInputStream in, int timeout) throws IOException {
        int seconds = 0;

        while (seconds < timeout) {
            String s = null;
            try {
                byte[] buffer = new byte[50];
                int read = in.read(buffer);
                if (read != 0) {
                    String response = new String(buffer, 0, read);
                    response = response.replace("\n", "").replace("\r", "");
                    return response;
                }
            } catch (SocketTimeoutException ex) {
            }
            seconds++;
        }
        return null;
    }
}
