package soundshareclient;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class SoundShareClient {
    //se true faz sleep a cada bloco de dados enviados
    public final static boolean DEBUG_DELAY_UPLOAD = false;
    
    public final static int CLIENT_TIMEOUT = 60; //em segundos
    public final static int MAX_SIZE = 1024; 
    private boolean reading = false;
    
    private CommandClient client;

    /**
     * mostra um menu para acesso às varias funcionalidades
     */
    public void start() throws IOException{    
        client = new CommandClient();
        String response = null;
        int opt;
        do{ 
            switch(opt=mainMenu()){
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    listAllMusics();
                    break;
                case 4:
                    downloadMusic();
                    break;
                case 5:              
                    uploadMusic();
                    break;
                case 6:              
                    listMusicsByTag();
                    break;
                case 0:
                    close();
                    break;
            }
        }while(opt!=0);
    }
    
    /**
     * efetua o login no servidor
     * pede o nome e password e via CommandClient efetua o login no servidor
     */
    private void login() {
        String name,pass;

        connectToServer();
        if (!client.isConnected())
            return;

        if (client.isLoggedIn()){
            System.out.println("Já está logged in");
            return;
        }
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nome: ");
        name = scanner.nextLine().trim();
        if (name.isEmpty())
            return;
        
        System.out.println("Password: ");
        pass = scanner.nextLine().trim();
        if (pass.isEmpty())
            return;
        
        try {
            client.login(name,pass);
        } catch (IOException ex) {
            System.out.println("Erro na comunicação");
            ex.printStackTrace();
        } catch (ClientException ex) {
            System.out.println("Erro no Login: " + ex.getMessage());
        }
    }
    
    /**
     * regista um novo utilizador 
     * pede os dados e via o CommandClient efetua o registo
     * 
     */
    private void register() {
        String name,pass,passConfirm,s;
        boolean isAuthor;

        connectToServer();
        if (!client.isConnected())
            return;

        if (client.isLoggedIn()){
            System.out.println("Já está logged in");
            return;
        }
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nome: ");
        name = scanner.nextLine().trim();
        if (name.isEmpty())
            return;
        
        System.out.println("É Autor (S/N): ");
        s = scanner.nextLine().trim();
        if (s.isEmpty())
            return;
        
        isAuthor = s.equalsIgnoreCase("S");

        do{
            System.out.println("Password: ");
            pass = scanner.nextLine().trim();
            if (pass.isEmpty())
                return;

            System.out.println("Confirmação Password: ");
            passConfirm = scanner.nextLine().trim();
            if (passConfirm.isEmpty())
                return;

            if (!passConfirm.equals(pass))
                System.out.println("A password é diferente da confirmação");
        }while(!passConfirm.equals(pass));
        
        try {
            client.register(name,pass,isAuthor);
        } catch (ClientException ex) {
            System.out.println("Erro no Registo: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro na comunicação");
            ex.printStackTrace();
        }
        
    }

    /**
     * establece a ligacao com o servidor
     * a ligacao fica activa ate terminar a aplicacao
     */
    private void connectToServer(){
        
        if (client.isConnected())
            return;
        
        String server;
        int port=0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Servidor [127.0.0.1]: ");
        server = scanner.nextLine().trim();
        if (server.isEmpty())
            server="127.0.0.1";
        
        do {
            System.out.println("Porta [12345]: ");
            String s = scanner.nextLine();
            if (s.trim().isEmpty()){
                port=12345;
            }else{
                try {
                    port=Integer.parseInt(s);    
                } catch (Exception e) {
                }
            }
        }while(port==0);
        
        try {
            client.setServerAddress(Inet4Address.getByName(server));
            client.setServerPort(port);
            client.connect();
        } catch (UnknownHostException ex) {
            System.out.println("O Servidor não foi encontrado...");
            return;
        } catch (IOException ex) {
            System.out.println("Erro ao conectar ao servidor: " + ex.getMessage());
            ex.printStackTrace();
            return;
        }
    }
    
    private void listAllMusics() {
        if (!client.isConnected() || !client.isLoggedIn()){
            System.out.println("Não está ligado ao servidor ou não está logged in");
            return;
        }
        try{
            client.listAllMusics();
        } catch (ClientException ex) {
            System.out.println("Erro ao obter a lista de musicas: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro na comunicação");
            ex.printStackTrace();
        }    }

    private void listMusicsByTag() {
        if (!client.isConnected() || !client.isLoggedIn()){
            System.out.println("Não está ligado ao servidor ou não está logged in");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduza uma tag para obter a lista de musicas correspondente (ou enter para cancelar): ");
        String tag = scanner.nextLine().trim();
        if (tag.isEmpty())
            return;
        
        try{
            client.listMusics(tag);
        } catch (ClientException ex) {
            System.out.println("Erro ao obter a lista de musicas: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro na comunicação");
            ex.printStackTrace();
        }    }
    
    private void downloadMusic() {
        if (!client.isConnected() || !client.isLoggedIn()){
            System.out.println("Não está ligado ao servidor ou não está logged in");
            return;
        }
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduza o id da Música que pretende descarregar");
        int id = scanner.nextInt(); 
        scanner.nextLine();//para consumir o new line
        if (id<=0)
            return;
        
        System.out.println("Introduza o nome do ficheiro onde guardar a música (ou enter para cancelar): ");
        String filename = scanner.nextLine().trim();
        if (filename.isEmpty())
            return;
        
        try {
            client.download(id, filename);
        } catch (ClientException ex) {
            System.out.println("Erro no Download: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro na comunicação");
            ex.printStackTrace();
        }
    }

    /**
     * pede os metadados da musica e o ficheiro e invoca o upload no CommandClient
     */
    private void uploadMusic() {
        if (!client.isConnected() || !client.isLoggedIn()){
            System.out.println("Não está ligado ao servidor ou não está logged in");
            return;
        }
        
        if (!client.isAuthor()){
            System.out.println("Não está loggado como Autor. Só pode efetuar uploads se for um Autor");
            return;
        }
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduza o Título (ou enter para cancelar): ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty())
            return;
        
        System.out.println("Introduza o Autor (ou enter para cancelar): ");
        String author = scanner.nextLine().trim();
        if (author.isEmpty())
            return;

        System.out.println("Introduza o Interprete (ou enter para cancelar): ");
        String performer = scanner.nextLine().trim();
        if (performer.isEmpty())
            return;

        System.out.println("Introduza o Ano (ou 0 para cancelar): ");
        int year = scanner.nextInt(); 
        scanner.nextLine();//para consumir o new line
        if (year<=0)
            return;

        System.out.println("Introduza o Género (ou enter para cancelar): ");
        String genre = scanner.nextLine().trim();
        if (genre.isEmpty())
            return;
        
        System.out.println("Introduza uma ou mais Etiquetas separadas por um espaço (ou enter para cancelar): ");
        String s = scanner.nextLine().trim();
        if (s.isEmpty())
            return;
        
        String[] tags=s.split(" ");
        
        String filename;
        do{
            System.out.println("Introduza o Ficheiro (ou enter para cancelar): ");
            filename = scanner.nextLine().trim();
            if (filename.isEmpty())
                return;
            
            if (!Files.exists(Paths.get(filename)))
                System.out.println("O Ficheiro não foi encontrado...");
        }while(!Files.exists(Paths.get(filename)));
                
        try {
            client.upload(title, author, performer, year, genre, tags, filename);
        } catch (ClientException ex) {
            System.out.println("Erro no Upload: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro na comunicação");
            ex.printStackTrace();
        }
    }

    private void close() {
        try {
            client.close();
            System.out.println("Sessão Encerrada!");
        } catch (IOException ex) {
            System.out.println("Erro na comunicação");
            ex.printStackTrace();
        }
    }

    /**
     * mostra o menu e devolve a opção escolhida
     * @return 
     */
    private int mainMenu() {
        System.out.println();
        System.out.println("Opções:");
        
        if (!client.isLoggedIn()){
            System.out.println("1 - Login");
            System.out.println("2 - Registar");
        }else{
            System.out.println("3 - Listar Músicas");
            System.out.println("4 - Descarregar uma Música");
            if (client.isAuthor())
                System.out.println("5 - Carregar uma Música");
            System.out.println("6 - Pesquisa por Tag");
        }
        System.out.println();
        System.out.println("0 - Terminar");
        System.out.println();
        System.out.println();
        
        Scanner s = new Scanner(System.in);
        
        while(true){
            try{
                int i = s.nextInt();
                return i;
            }catch(Exception e){
            }
        }
    }

}
