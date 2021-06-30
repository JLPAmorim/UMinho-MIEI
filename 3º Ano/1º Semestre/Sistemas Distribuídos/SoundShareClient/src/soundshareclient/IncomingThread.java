package soundshareclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IncomingThread extends Thread {

    private final BlockingQueue<String> incomingQueue;
    private final BufferedReader in;

    IncomingThread(BufferedReader in, BlockingQueue<String> incomingQueue) {
        this.in = in;
        this.incomingQueue=incomingQueue;
    }

    /**
     * esta thread vai receber os pacotes de entrada e caso seja uma notificacao 
     * processa-a caso contrario guarda a mensagem recebida na incomingQueue para 
     * posterior processamento pela thread principal
     *
     */

    @Override
    public void run() {
            
        try{
            while (true){
                String response = readString(30);
                if (response!=null){
                    if (response.startsWith("SERVER BROADCAST:")){
                        response=response.substring(17);
                        System.out.println("Notificação: " + response);
                    }else{
                        incomingQueue.put(response);
                    }            
                }
            }
        } catch (IOException ex) {
            System.out.println("Thread Incoming: Erro inesperado: " + ex.getMessage());
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            System.out.println("Thread Incoming: Erro inesperado: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
   /**
     * espera o tempo indicado por uma string de texto, devolve null se ocorrer
     * um time out
     *
     * @param timeout em segundos
     * @return texto recebido
     * @throws IOException
     */
    String readString(int timeout) throws IOException {
        int seconds = 0;

        while (seconds < timeout) {
            String s = null;
            try {                   
                s = in.readLine();
                return s;
            } catch (SocketTimeoutException ex) {
            }
            seconds++;
        }
        return null;
    }
    

}
