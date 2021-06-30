package soundshareserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SoundShareServer {
    //se true faz sleep a cada bloco de dados enviados
    public final static boolean DEBUG_DELAY_DOWNLOAD = true;
    
    
    public final static int MAXDOWN = 10;
    public final static int SERVER_PORT = 12345;
    public final static int CLIENT_TIMEOUT = 3600; //em segundos, 3600 = 1 hora
    public final static int MAX_SIZE = 1024;
    public static String ROOT_FOLDER = "sound_share_files";    
    private static int numThreads=0;
    
    private Manager manager;
    
    public SoundShareServer(){
        try {
            manager = new Manager(ROOT_FOLDER);
        } catch (Exception e) {
            Manager.println("Erro imprevisto ao inicializar o servidor: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
        public void start() {
            ServerSocket serverSocket = null;

            try{
                serverSocket = new ServerSocket(SoundShareServer.SERVER_PORT);
                Manager.println("socket do servidor iniciado na porta " + SoundShareServer.SERVER_PORT);

            } catch (IOException e) {
                Manager.println("Erro ao iniciar o socket do servidor: " + e.getMessage());
                e.printStackTrace();
                System.exit(-1);
            }

            while (true) {
                try {
                    Manager.println("a aguardar conexao");
                    Socket socket = serverSocket.accept();
                    numThreads++;
                    new SoundShareThread(socket, numThreads, manager).start();
                } catch (Exception e) {
                    Manager.println("Erro no accept do socket: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    
    public Manager getManager(){
        return manager;
    }

}
