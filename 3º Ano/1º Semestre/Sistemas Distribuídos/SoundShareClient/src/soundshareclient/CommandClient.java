package soundshareclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandClient{
    Socket client = null;
    BufferedReader in;
    BufferedWriter out;

    
    private String name;
    private boolean connected=false;
    private boolean author=false;
    private boolean loggedIn=false;
    private InetAddress serverAddress;
    private int serverPort;
    
    IncomingThread incomingThread;
    BlockingQueue<String> incomingQueue;
    
    /**
     * permite definir o endereço do servidor
     * @param serverAddress 
     */
    public void setServerAddress(InetAddress serverAddress){
        this.serverAddress=serverAddress;
    }
    
    /**
     * permite definir a porta do servidor
     * @param serverPort 
     */
    public void setServerPort(int serverPort){
        this.serverPort=serverPort;
    }
    
    /**
     * efetua a conexao ao servidor de acordo com o host e porta
     * no socket é definido um timeout de 1 segundo
     *  
     * @throws IOException 
     */
    public void connect() throws IOException{
        client = new Socket (serverAddress, serverPort);
        client.setSoTimeout(1000);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        connected=true;
        incomingQueue = new LinkedBlockingQueue<>();
        incomingThread = new IncomingThread(in, incomingQueue);
        incomingThread.start();
    }
   

    /**
     * establece uma conversa com o servidor para efetuar o login
     * @param name
     * @param pass 
     */
    void login(String name, String pass) throws IOException, ClientException {
        author=false;
        this.name="";
        loggedIn = false;
        
        out.write("LOGIN");out.newLine();      
        out.flush();
        
        String response = readString(10);
        if (response==null)
            throw new ClientException("Tempo de espera excedido");

        //response should be ENTER_LOGIN
        if (!"ENTER_LOGIN".equalsIgnoreCase(response))
            throw new ClientException("Resposta inesperada: " + response);
        
        out.write(name);out.newLine();
        out.write(pass);out.newLine();
        out.flush();
        
        response = readString(10);
        if (response==null)
            throw new ClientException("Tempo de espera excedido");
        
        switch(response.toUpperCase()){
            case "LOGIN_AUTHOR_OK":
                author=true;
            case "LOGIN_LISTENER_OK":
                this.name=name;
                this.loggedIn=true;
                System.out.println(author ? "Login AUTOR efetuado com Sucesso" : "Login OUVINTE efetuado com Sucesso");
                break;
            case "LOGIN_INVALID":
                throw new ClientException("Dados inválidos ou sessão já iniciada");
            default:
                throw new ClientException("Resposta inesperada: " + response);
        }        
    }

    /**
     * 
     * @return o estado da conexao ao servidor
     */
    public boolean isConnected() {
        return connected;
    }
    
    /**
     * 
     * @return true se o login atual é de um autor
     */
    boolean isAuthor() {
        return author;
    }

    /**
     * 
     * @return o estado do login
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    /**
     * establece comunicacao com o servidor
     * indicando primeiro que se pretende registar um novo utilizador
     * se aceite o servidor responde com 'ENTER_NEW_LOGIN' e sâo enviados os dados
     * 
     * @param name
     * @param pass
     * @param author
     * @throws ClientException
     * @throws IOException 
     */
    void register(String name, String pass, boolean author) throws ClientException, IOException {
        out.write("REGISTER");out.newLine();      
        out.flush();
        
        String response = readString(10);
        if (response==null)
            throw new ClientException("Tempo de espera excedido");

        //response should be ENTER_LOGIN
        if (!"ENTER_NEW_LOGIN".equalsIgnoreCase(response))
            throw new ClientException("Resposta inesperada: " + response);
        
        out.write(name);out.newLine();
        out.write(pass);out.newLine();
        out.write(author ? "AUTHOR" : "LISTENER");out.newLine();
        out.flush();
        
        response = readString(10);
        if (response==null)
            throw new ClientException("Tempo de espera excedido");
        
        switch(response.toUpperCase()){
            case "REGISTER_OK":
                System.out.println("Registo efetuado com Sucesso");
                break;
            case "REGISTER_INVALID":
                throw new ClientException("O Registo é inválido, possivelmente já existe");
            default:
                throw new ClientException("Resposta inesperada: " + response);
        }                
    }

    /**
     * inica uma conversacao com o servidor para fazer upload de um ficheiro
     * o upload será feito numa outra thread e portanto numa outra conexao
     * o servidor fornece um "token" de autorizacao para efetuar o upload
     * 
     * @param title
     * @param author
     * @param performer
     * @param year
     * @param genre
     * @param tags
     * @param filename 
     */
    public void upload(String title, String author, String performer, int year, String genre, String[] tags, String filename) throws ClientException, IOException {
        out.write("UPLOAD_REQUEST");out.newLine();      
        out.flush();
        
        String response = readString(10);
        if (response==null)
            throw new ClientException("Tempo de espera excedido");

        //response should be ENTER_LOGIN
        if (!"ENTER_METADATA".equalsIgnoreCase(response))
            throw new ClientException("Resposta inesperada: " + response);
        
        //envia-se os meta dados da musica, um item em cada linha
        out.write(title);out.newLine();
        out.write(author);out.newLine();
        out.write(performer);out.newLine();
        out.write(""+year);out.newLine();
        out.write(genre);out.newLine();
        out.write(""+tags.length);out.newLine();
        for (String tag : tags) {
            out.write(tag);out.newLine();
        }
        out.flush();
        
        response = readString(10);
        if (response==null)
            throw new ClientException("Tempo de espera excedido");
        
        switch(response.toUpperCase()){
            case "UPLOAD_ACCEPTED":
                System.out.println("Upload aceite");
                //obtem-se o token para a thread de upload
                String token = readString(10);
                if (token==null)
                    throw new ClientException("Tempo de espera excedido");

                if (!token.startsWith("TOKEN:"))
                    throw new ClientException("Resposta inesperada: " + token);
                
                System.out.println("Token de Upload: " + token.substring(6).trim());

                new ClientUploadThread(serverAddress, serverPort, filename, token.substring(6).trim()).start();
                
                break;
            case "MUSIC_ALREADY_EXISTS":
                throw new ClientException("A Música já existe no servidor");
            default:
                throw new ClientException("Resposta inesperada: " + response);
        }                
        /*response = readString(10);
        
        if (response==null){
            throw new ClientException("Tempo de espera excedido");
        }else if(response.startsWith("SERVER BROADCAST: ")){
            System.out.println(response);
        }else{
            throw new ClientException("Resposta inesperada: " + response);
        }*/
    }

    /**
     * prepara o donwload de uma musica
     * informa ao servidor o id da musica e obtem deste o token e tamanho do ficheiro
     * 
     * @param id
     * @param filename
     * @throws IOException
     * @throws ClientException 
     */
    void download(int id, String filename) throws IOException, ClientException {
        out.write("DOWNLOAD_REQUEST");out.newLine();      
        out.flush();
        
        String response = readString(10);
        if (response==null)
            throw new ClientException("Tempo de espera excedido");

        if (!response.equalsIgnoreCase("ENTER_ID"))
            throw new ClientException("Resposta inesperada: " + response);
        
        //enviar o id pretendido
        out.write(""+id);out.newLine();      
        out.flush();
        
        response = readString(10);
        if (response==null)
            throw new ClientException("Tempo de espera excedido");
        
        switch(response.toUpperCase()){
            case "DOWNLOAD_ACCEPTED":
                System.out.println("Download aceite");
                //obtem-se o token para a thread de upload
                String token = readString(10);
                if (token==null)
                    throw new ClientException("Tempo de espera excedido");

                if (!token.startsWith("TOKEN:"))
                    throw new ClientException("Resposta inesperada: " + token);
                
                System.out.println("Token de Download: " + token.substring(6).trim());

                //obter o size
                String s = readString(10);
                if (s==null)
                    throw new ClientException("Tempo de espera excedido");

                if (!s.startsWith("SIZE:"))
                    throw new ClientException("Resposta inesperada: " + s);
                
                System.out.println("Tamanho do ficheiro: " + s.substring(5).trim());
                
                long size;
                try {
                    size = Long.parseLong(s.substring(5).trim());
                } catch (Exception e) {
                    throw new ClientException("O Tamanho do ficheiro não é válido");
                }
                
                new ClientDownloadThread(serverAddress, serverPort, filename, token.substring(6).trim(), size).start();

                break;                
            case "INVALID_ID":
                throw new ClientException("O ID não é válido");
            case "MUSIC_NOT_FOUND":
                throw new ClientException("A Música não existe no servidor");
            default:
                throw new ClientException("Resposta inesperada: " + response);
        }                
    }

    /**
     * pede ao servidor uma lista de todas as musicas
     * @throws IOException
     * @throws ClientException 
     */
    public void listAllMusics() throws IOException, ClientException {
        out.write("LIST_ALL");out.newLine();      
        out.flush();

        receiveList();
    }

    /**
     * pede ao servidor uma lista de todas as musicas correspondetes à tag indicada
     * @throws IOException
     * @throws ClientException 
     */
    public void listMusics(String tag) throws IOException, ClientException {
        out.write("LIST_BY_TAG");out.newLine();      
        out.write(tag);out.newLine();      
        out.flush();
        
        receiveList();
    }

    private void receiveList() throws IOException, ClientException{
        String s = readString(10);
        System.out.println(s + " erro");
        if (s==null)
            throw new ClientException("Tempo de espera excedido");
        
        if (!s.startsWith("NUM_MUSICS: "))
            throw new ClientException("Número de musicas invalido");
        
        int num=0;
        try {
            num = Integer.parseInt(s.substring(12));
        } catch (Exception e) {
            throw new ClientException("Número de musicas invalido");
        }
        
        System.out.println("Número total de Músicas: " + num);
        
        //recebe cada uma das musicas e mostra-as na consola
        for (int i = 0; i < num; i++) {
            //sao 7 linhas de dados para cada musica
            System.out.println("Musica nº " + (i+1));
            for (int j = 0; j < 7; j++) {
                s = readString(10); 
                if (s==null) 
                    throw new ClientException("Tempo de espera excedido");
                
                System.out.println("    " + s);
            }
            System.out.println("");
        }
    }
    
    /**
     * pede ao servidor uma lista de todas as musicas correspondetes à tag indicada
     * @throws IOException
     * @throws ClientException 
     */
    public void close() throws IOException{
        out.write("CLOSE");out.newLine();          
        out.flush();
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
        
        try {
            return incomingQueue.poll(timeout, TimeUnit.SECONDS);
            
//
//        int seconds = 0;
//
//        while (seconds < timeout) {
//            String s = null;
//            try {                   
//                s = in.readLine();
//                if(s.equalsIgnoreCase("SERVER BROADCAST")){
//                    System.out.println(s);
//                    s = "";
//                }
//                return s;
//            } catch (SocketTimeoutException ex) {
//            }
//            seconds++;
//        }
//        return null;
        } catch (InterruptedException ex) {
            return null;
        }
    }

}