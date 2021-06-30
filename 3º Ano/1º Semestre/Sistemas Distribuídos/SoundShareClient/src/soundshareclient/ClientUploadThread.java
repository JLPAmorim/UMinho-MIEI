package soundshareclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClientUploadThread extends Thread {

    private final InetAddress serverAddress;
    private final int serverPort;
    private final String filename;
    private final String token;

    /**
     * esta thread vai correr independentemente do thread principal
     * para efetuar o upload de uma musica para o servidor
     * ao establecer a ligacao ao server vai comecar por enviar o comando UPLOAD seguido do token
     * aguarda por uma resposta afirmativa e depois envia o ficheiro 
     * em blocos MAX_SIZE bytes
     * 
     * @param serverAddress
     * @param serverPort
     * @param filename
     * @param token 
     */
    ClientUploadThread(InetAddress serverAddress, int serverPort, String filename, String token) {
        this.serverAddress = serverAddress;
        this.serverPort=serverPort;
        this.filename = filename;
        this.token = token;
    }
    
    @Override
    public void run() {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        Socket client = null;
        try {
            
            long size = Files.size(Paths.get(filename));

            client = new Socket (serverAddress, serverPort);
            client.setSoTimeout(1000);
            in = new BufferedInputStream(client.getInputStream());
            out = new BufferedOutputStream(client.getOutputStream());
            //envia o pedido seguido do token e do tamanho do ficheiro
            out.write("UPLOAD\n".getBytes());
            out.write((token+"\n").getBytes());
            out.write((size+"\n").getBytes());
            out.flush();
            
            //aguarda 30 segundos pela resposta
            String response = readString(in, 30);
            if (response==null){
                System.out.println("Thread Upload token " + token + ": tempo de espera excedido");
                return;
            }
            
            switch(response.toUpperCase()){
                case "OK":
                    uploadFile(in, out);
                    break;
                default:
                    System.out.println("Thread Upload token " + token + ": resposta inesperada: " + response);
            }
            
        } catch (IOException ex) {
            System.out.println("Thread Upload token " + token + ": Erro inesperado: " + ex.getMessage());
            ex.printStackTrace();
        }finally{
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
                System.out.println("Thread Upload token " + token + ": Erro ao fechar o socket: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void uploadFile(BufferedInputStream in, BufferedOutputStream out) {
        BufferedInputStream fin = null;
        try {
            //abre o ficheiro indicado, vai lendo e enviando para o servidor            
            fin = new BufferedInputStream(new FileInputStream(filename));
            byte[] buffer = new byte[SoundShareClient.MAX_SIZE];
            int b;
            long written=0;
            while((b=fin.read(buffer))>0){
                if (SoundShareClient.DEBUG_DELAY_UPLOAD) try {Thread.sleep(1000);} catch (InterruptedException ex) {}                
                
                out.write(buffer,0,b);
                written+=b;
                if (written % SoundShareClient.MAX_SIZE*100==0)
                    System.out.println("Thread Upload token " + token + ": " + written + " bytes enviados.");
            }
            out.flush();
            System.out.println("Thread Upload token " + token + ": " + written + " bytes enviados.");
            //findo o upload espera por um resultado
            String response = readString(in, 30);
            if (response==null){
                System.out.println("Thread Upload token " + token + ": tempo de espera excedido apos o upload");
                return;
            }
            
            switch(response.toUpperCase()){
                case "UPLOAD_OK":
                    System.out.println("Thread Upload token " + token + ": ficheiro enviado com sucesso");                                
                    break;
                default:
                    System.out.println("Thread Upload token " + token + ": resposta inesperada após o upload: " + response);
            }            
        } catch (FileNotFoundException ex) {
            System.out.println("Thread Upload token " + token + ": ficheiro não encontrado: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Thread Upload token " + token + ": erro inesperado: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (fin!=null)
                    fin.close();
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
