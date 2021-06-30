package soundshareserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SoundShareThread extends Thread {

    private Socket socket = null;
    private User login = null;
    BufferedWriter out;
    BufferedReader in;
    private int threadId;
    private Manager manager;
    private boolean notifiable = false;
    BlockingQueue<String> notificationQueue;

    public SoundShareThread(Socket socket, int threadId, Manager manager) throws Exception {
        try {
            this.socket = socket;
            this.threadId=threadId;
            this.manager=manager;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //define um timeout de 1 segundo para a leitura de dados
            socket.setSoTimeout(1000);
            
            Manager.println("T" + threadId + ": iniciada");
            
            notificationQueue = new LinkedBlockingQueue<>();
            
            manager.addThread(this);
            Manager.println("T" + threadId + " adicionada à lista de Threads activas");
        } catch (IOException ex) {
            throw new Exception("T" + threadId + ": Erro ao ligar aos streams", ex);
        }
    }
    
    /**
     * devolve o valor da variável notifiable indicando se a thread é notifiable
     * 
     * @return true se for notifiable e false se não for
     */
    public boolean isNotifiable() {
        return notifiable;
    }

    /**
     * thread de comunicacao com o cliente, 
     * fica em loop ate ao timeout ou receber pedido de close
     */
    @Override
    public void run() {
        //ficar continuamente a aguardar dados do cliente até receber um comando de close
        //ou ocorrer um timeout
             
        while (true) {
            
            String command=null;
            try{
                command = readString(SoundShareServer.CLIENT_TIMEOUT);
                Manager.println("T" + threadId + ": comando recebido: " + command);
                if (command == null) {
                    Manager.println("T" + threadId + ": tempo excedido");
                    break;
                }
                
                switch (command.toUpperCase()) {
                    case "LOGIN":
                        login = login();
                        if(login!=null)
                            notifiable=true; //a partir de agora pode receber notificacoes de novas musicas
                        break;
                    case "REGISTER":
                        register();
                        break;
                    case "UPLOAD_REQUEST":
                        uploadRequest();
                        break;
                    case "DOWNLOAD_REQUEST":
                        downloadRequest();
                        break;
                    case "UPLOAD":                        
                        int id = upload();                        
                        Manager.println("Thread " + threadId + " removida da lista de Threads activas");
                        if (id>0)
                            manager.notifier(manager.getMusicById(id));
                        break;
                    case "DOWNLOAD":
                        download();
                        break;
                    case "LIST_ALL":
                        listAll();
                        break;
                    case "LIST_BY_TAG":
                        listByTag();
                        break;
                    case "CLOSE":
                        break;
                }
                //se foi um upload ou DOWNLOAD a thread vai terminar...
                if ("UPLOAD".equalsIgnoreCase(command) || "DOWNLOAD".equalsIgnoreCase(command) || "CLOSE".equalsIgnoreCase(command))
                    break;
            }catch(Exception ex){
                Manager.println("T" + threadId + ": Erro no comando " + command + ex.getMessage());                    
            }
        }
        if(login!=null)
            manager.removeLoggedUser(login.getName());
        
        Manager.println("T" + threadId + ": Thread terminada");
        manager.removeThread(this);
        Manager.println("Thread " + threadId + " removida da lista de Threads activas");
        
        try {
            
            if (out != null) {
                out.close();
            }

            if (in != null) {
                in.close();
            }

            if (socket != null) {
                socket.close();
                manager.removeThread(this);
            }
        } catch (IOException ex) {
            Manager.println("T" + threadId + ": Erro ao fechar o socket: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * processa o login 
     * 
     * @return o utilizador que efetuou o login
     * @throws Exception
     */
    private User login() throws Exception {
        sendString("ENTER_LOGIN");
        //out.write("ENTER_LOGIN");out.newLine();
        //out.flush();

        //lê-se duas strings, uma é o login, a outra a password
        //timeout de 30 segundos para cada 
        String name = readString(30);
        if (name==null){
            Manager.println("T" + threadId + ": Timeout ao esperar pelo nome de utilizador");
            return null;
        }
            
        String pass = readString(30);
        if (pass==null){
            Manager.println("T" + threadId + ": Timeout ao esperar pela password");
            return null;
        }        
                
        User user = manager.login(name,pass);
        
        if (user==null){
            Manager.println("T" + threadId + ": Login inválido");
            sendString("LOGIN_INVALID");
            //out.write("LOGIN_INVALID");out.newLine();
            //out.flush();
        }else{
            Manager.println("T" + threadId + ": Login com exito: " + user.getName() + "; autor:" + user.isAuthor());
            sendString(user.isAuthor() ? "LOGIN_AUTHOR_OK" : "LOGIN_LISTENER_OK");
            //out.write(user.isAuthor() ? "LOGIN_AUTHOR_OK" : "LOGIN_LISTENER_OK");
            //out.newLine();
            //out.flush();
        }
        return user;
    }

    /**
     * processa o registo de um novo utilizador
     * este novo utilizador nao fica logado
     * @throws Exception 
     */
    private void register() throws Exception {
        sendString("ENTER_NEW_LOGIN");
        //out.write("ENTER_NEW_LOGIN");out.newLine();
        //out.flush();

        //lê-se duas strings, uma é o login, a outra a password
        //timeout de 30 segundos para cada 
        String name = readString(30);
        if (name==null){
            Manager.println("T" + threadId + ": Timeout ao esperar pelo nome de utilizador");
            return;
        }
            
        String pass = readString(30);
        if (pass==null){
            Manager.println("T" + threadId + ": Timeout ao esperar pela password");
            return;
        }        
                
        String type = readString(30);
        if (pass==null){
            Manager.println("T" + threadId + ": Timeout ao esperar pelo tipo");
            return;
        }        
        boolean author;
        switch (type.toUpperCase()){
            case "AUTHOR":
                author=true;
                break;
            case "LISTENER":
                author=false;
                break;
            default:
                Manager.println("T" + threadId + ": tipo de utilizador inesperado: " + type);
                sendString("REGISTER_INVALID");
                //out.write("REGISTER_INVALID");out.newLine();
                //out.flush();
                return;
        }
        
        if (manager.register(name,pass,author)){
            sendString("REGISTER_OK");
            //out.write("REGISTER_OK");out.newLine();
            //out.flush();
        }else{
            sendString("REGISTER_INVALID");
            //out.write("REGISTER_INVALID");out.newLine();
            //out.flush();
        }
    }

    /**
     * obtem uma lista de todas as musicas e devolve
     * comeca por indicar quantas musicas sao e depois envia-as uma a uma
     * para cada musica envia o os seus metadados linha a linha
     * as tags sao enviadas todas numa unica linha
     */
    private void listAll() throws IOException {
        List<Music> musics = manager.getAllMusics();
        
        sendMusics(musics);        
    }

    private void listByTag() throws IOException {
        //a tag foi enviada de seguida
        String tag = readString(30);
        if (tag==null){
            Manager.println("T" + threadId + ": tempo excedido ao aguardar pela etiqueta a pesquisar");
            return;
        }
        
        List<Music> musics = manager.getMusicsByTag(tag);
        
        sendMusics(musics);        
    }

    private void sendMusics(List<Music> musics) throws IOException{
        sendString("NUM_MUSICS: " + musics.size());
        //out.write("NUM_MUSICS: " + musics.size());out.newLine();
        //out.flush();
        for (Music music : musics) {
//            out.write("ID: " + music.getId());out.newLine();
//            out.write("TITLE: " + music.getTitle());out.newLine();
//            out.write("AUTHOR: " + music.getAuthor());out.newLine();
//            out.write("PERFORMER: " + music.getPerformer());out.newLine();
//            out.write("YEAR: " + music.getYear());out.newLine();
//            out.write("GENRE: " + music.getGenre());out.newLine();
//            String tags="";
//            for (String tag : music.getTags()) {
//                tags+=" "+tag;
//            }
//            out.write("TAGS: " + tags.substring(1));out.newLine();
//            out.flush();
            String s = "ID: " + music.getId() + System.lineSeparator()
                      +"TITLE: " + music.getTitle() + System.lineSeparator()
                      +"AUTHOR: " + music.getAuthor() + System.lineSeparator()
                      +"PERFORMER: " + music.getPerformer() + System.lineSeparator()
                      +"YEAR: " + music.getYear() + System.lineSeparator()
                      +"GENRE: " + music.getGenre() + System.lineSeparator();
            String tags="";
            for (String tag : music.getTags()) {
                tags+=" "+tag;
            }
            s+="TAGS: " + tags.substring(1);
            sendString(s);
        }
    }
    
    /**
     * processa o pedido de upload
     * se o ficheiro ainda nao existir
     * e a musica nao possui um titulo repetido aceita
     * acrescenta os meta dados aos uploads pendentes
     * e fornece um token para o cliente iniciar o upload
     * 
     * o token será simplesmente o id gerado para a nova musica
     * 
     */
    private void uploadRequest() throws IOException {
        sendString("ENTER_METADATA");
        //out.write("ENTER_METADATA");out.newLine();
        //out.flush();

        //lê-se uma sequencia de strings que são so metadados 
        //aguarda-se 30 segundos por cada
        String title = readString(30);
        if (title==null){
            Manager.println("T" + threadId + ": Timeout ao esperar pelo Titulo");
            return;
        }
        
        String author = readString(30);
        if (author==null){
            Manager.println("T" + threadId + ": Timeout ao esperar pelo Autor");
            return;
        }
        
        String performer = readString(30);
        if (performer==null){
            Manager.println("T" + threadId + ": Timeout ao esperar pelo Interprete");
            return;
        }
        
        String s = readString(30);
        if (s==null){
            Manager.println("T" + threadId + ": Timeout ao esperar pelo Ano");
            return;
        }
        int year=0;
        try{
            year=Integer.parseInt(s);
        }catch(Exception e){
        }
        if (year<1900 || year > LocalDate.now().getYear()){
            sendString("INVALID_DATA: O ano tem de ser um inteiro entre 1900 e o ano corrente");
            //out.write("INVALID_DATA: O ano tem de ser um inteiro entre 1900 e o ano corrente");out.newLine();
            //out.flush();
            Manager.println("T" + threadId + ": Ano recebido invalido, upload recusado: " + year);
            return;
        }
        
        String genre = readString(30);
        if (genre==null){
            Manager.println("T" + threadId + ": Timeout ao esperar pelo Género");
            return;
        }

        s = readString(30);
        if (s==null){
            Manager.println("T" + threadId + ": Timeout ao esperar pelas tags");
            return;
        }
        int numtags=0;
        try{
            numtags=Integer.parseInt(s);
        }catch(Exception e){
        }

        List<String> tags = new ArrayList<>();
        for(int t=0;t<numtags;t++){
            s = readString(30);
            if (s==null){
                Manager.println("T" + threadId + ": Timeout ao esperar pelas tags");
                return;
            }
            tags.add(s);
        }
            
        //temos todos os elementos, é necessario determinar se a musica ja existe
        //considera-se existente se o title for repetido
        if (manager.musicExists(title)){
            sendString("MUSIC_ALREADY_EXISTS");
            //out.write("MUSIC_ALREADY_EXISTS");out.newLine();
            //out.flush();
            Manager.println("T" + threadId + ": Música já existemte");
            return;
        }

        //upload aceite
        //obtem-se um id de uma nova musica
        int id = manager.prepareUpload(title, author, performer, year, genre, tags);
        sendString("UPLOAD_ACCEPTED"+System.lineSeparator()
                  +"TOKEN:"+id);
        
//        out.write("UPLOAD_ACCEPTED");out.newLine();
//        out.write("TOKEN:"+id);out.newLine();
//        out.flush();

        Manager.println("T" + threadId + ": Música aceite para upload '" + title + "' com id " + id);
        
    }
    
    /**
     * processa o pedido de download
     */
    private void downloadRequest() throws IOException {
        sendString("ENTER_ID");
        //out.write("ENTER_ID");out.newLine();
        //out.flush();

        String s = readString(30);
        if (s==null){
            Manager.println("T" + threadId + ": Timeout ao esperar pelo Id do Download");
            return;
        }
        
        int id=0;
        try {
            id = Integer.parseInt(s);
        } catch (Exception e) {
        }
        
        if (id==0){
            sendString("INVALID_ID");
            //out.write("INVALID_ID");out.newLine();
            //out.flush();
            Manager.println("T" + threadId + ": Download: ID inválido: " + s);
            return;
        }
        
        Music m = manager.getMusicById(id);
        
        if (m==null){
            sendString("MUSIC_NOT_FOUND");
            //out.write("MUSIC_NOT_FOUND");out.newLine();
            //out.flush();
            Manager.println("T" + threadId + ": Download: ID não encontrado: " + s);
            return;
        }
        
        long size;
        try {
            size = Files.size(Paths.get(SoundShareServer.ROOT_FOLDER, "musica_"+id));    
        } catch (Exception ex) {
            sendString("ID_NOT_FOUND");
            //out.write("ID_NOT_FOUND");out.newLine();
            //out.flush();
            Manager.println("T" + threadId + ": Download: ID existe mas erro no acesso ao ficheiro: " + ex.getMessage());
            return;
        }
        
        //encontrou, envia o tamanho para o cliente saber 
        //o cliente irá criar uma outra conexao para o download propriamente dito
        //uma vez que para simplificar, o token é o proprio ID não é necessário
        //guardar o download
        sendString("DOWNLOAD_ACCEPTED"+System.lineSeparator()
                    +"TOKEN: " + id + System.lineSeparator()
                    +"SIZE: " + size);
        
//        out.write("DOWNLOAD_ACCEPTED");out.newLine();
//        out.write("TOKEN: " + id);out.newLine();
//        out.write("SIZE: " + size);out.newLine();
//        out.flush();
        
        Manager.println("T" + threadId + ": Música aceite para download com id " + id);
    }
    
    
    /**
     * processa o upload
     * ao contrario dos outros metodos, apos este, a thread ira terminar
     */
    int upload() throws IOException{
        //lê-se o token que deverá ter sido enviado logo de seguida
        String token = readString(30);
        if (token==null){
            Manager.println("T" + threadId + ": Timeout ao esperar pelo token do upload");
            return -1;
        }
        
        int id;
        try {
            id = Integer.parseInt(token);
        } catch (Exception e) {
            Manager.println("T" + threadId + ": token inválido: " + token);
            
            out.write("INVALID_TOKEN");out.newLine();
            out.flush();
            return -1;
        }
        
        String s = readString(30);
        if (s==null){
            Manager.println("T" + threadId + ": Timeout ao esperar pelo tamanho do ficheiro");
            return -1;
        }
        long size;
        try {
            size = Long.parseLong(s);
        } catch (Exception e) {
            Manager.println("T" + threadId + ": tamanho do ficheiro inválido: " + s);
            
            out.write("INVALID_FILE_SIZE");out.newLine();
            out.flush();
            return -1;
        }
        
        //verifica se esta musica esta na lista de musicas a aguardar upload
        if (manager.musicExistsInMusicUploads(id)){
            out.write("OK");out.newLine();
            out.flush();
        }else{
            out.write("INVALID_TOKEN");out.newLine();
            out.flush();
            return -1;
        }
        
        //o cliente agora vai enviar os dados como um stream de bytes
        //vai-se usar um BufferedInputStream
        BufferedInputStream bin = null;
        BufferedOutputStream fout = null;
        try {
            //aumenta-se o timeout deste socket para 10 minutos
            socket.setSoTimeout(1000*60*10);
            
            bin = new BufferedInputStream(socket.getInputStream());
            //e um outputstream para gravar o ficheiro
            fout = new BufferedOutputStream(new FileOutputStream(new File(SoundShareServer.ROOT_FOLDER, "musica_"+id)));
            
            byte[] buffer = new byte[SoundShareServer.MAX_SIZE];
            long bytesWritten=0;
            while (bytesWritten < size) {
                int n = bin.read(buffer, 0, (int) (size - bytesWritten > 1024 ? 1024 : size - bytesWritten));
                fout.write(buffer, 0, n);
                bytesWritten += n;
                
                if (bytesWritten % SoundShareServer.MAX_SIZE*100==0)
                    System.out.println("T" + threadId + ": " + bytesWritten + " bytes recebidos.");
            }            
            //fechar o file, garantindo que o ficheiro existe antes de o acrescentar à lista de musicas
            fout.close();
            System.out.println("T" + threadId + ": " + bytesWritten + " bytes recebidos.");
            
            //agora que o ficheiro existe passa-se para a lista de musicas disponiveis
            manager.addUploadedMusic(id);
            
            //sinaliza ao cliente que o upload decorreu sem problemas
            out.write("UPLOAD_OK");out.newLine();
            out.flush();
            return id;
        } catch (SocketTimeoutException ex) {
            Manager.println("T" + threadId + ": Erro ao receber a musica: tempo excedido");
            out.write("ERROR_IN_UPLOAD");out.newLine();
            out.flush();
        } catch (Exception e) {
            Manager.println("T" + threadId + ": Erro ao receber a musica: " + e.getMessage());
            //sinaliza o erro ao cliente
            //o cliente poderá tentar outra vez
            out.write("ERROR_IN_UPLOAD");out.newLine();
            out.flush();
        } finally {
            try{
                if (bin != null) { //isto tambem fecha o InputStream do Socket mas nao vai ser mais utilizado
                    bin.close();
                }
                if (fout != null) { 
                    fout.close();
                }
            } catch (IOException ex) {
            }
        }
        return -1;
    }
    
    /**
     * processa o download
     * ao contrario dos outros metodos, apos este, a thread ira terminar
     */
    void download() throws IOException{
        //lê-se o token que deverá ter sido enviado logo de seguida
        String token = readString(30);
        if (token==null){
            Manager.println("T" + threadId + ": Timeout ao esperar pelo token do download");
            return;
        }
        
        int id;
        try {
            id = Integer.parseInt(token);
        } catch (Exception e) {
            Manager.println("T" + threadId + ": token inválido: " + token);
            
            out.write("INVALID_TOKEN");out.newLine();
            out.flush();
            return;
        }
        
        //verifica que a musica existe
        Music m = manager.getMusicById(id);
        if (m==null){
            Manager.println("T" + threadId + ": id/token do ficheiro inválido: " + id);
            
            out.write("INVALID_TOKEN");out.newLine();
            out.flush();
            return;
        }

        out.write("OK");out.newLine();
        out.flush();
        
        //o ficheiro vai ser enviado recorrendo a um BufferedOutputStream
        //usando o mesmo stream de output do socket
        //lê-se o ficheiro e vai-se enviadndo bloco a bloco
        

        BufferedOutputStream bout = null;
        BufferedInputStream fin = null;
        try {
            //aumenta-se o timeout deste socket para 10 minutos
            socket.setSoTimeout(1000*60*10);
            
            bout = new BufferedOutputStream(socket.getOutputStream());
            //e um outputstream para gravar o ficheiro
            fin = new BufferedInputStream(new FileInputStream(new File(SoundShareServer.ROOT_FOLDER, "musica_"+id)));
            
            byte[] buffer = new byte[SoundShareServer.MAX_SIZE];
            int b;
            long bytesSent=0;
            while((b = fin.read(buffer))>0){
                if (SoundShareServer.DEBUG_DELAY_DOWNLOAD) try {Thread.sleep(1000);} catch (InterruptedException ex) {}
                                
                bout.write(buffer, 0, b);
                bytesSent+=b;
                if (bytesSent % SoundShareServer.MAX_SIZE*100==0)
                    System.out.println("T" + threadId + ": " + bytesSent + " bytes enviados.");
            }
            bout.flush();
            System.out.println("T" + threadId + ": " + bytesSent + " bytes enviados.");
            //ao fechar o stream termina-se a conexao 
        } catch (SocketTimeoutException ex) {
            Manager.println("T" + threadId + ": Erro ao receber a musica: tempo excedido");
            out.write("ERROR_IN_UPLOAD");out.newLine();
            out.flush();
        } catch (Exception e) {
            Manager.println("T" + threadId + ": Erro ao receber a musica: " + e.getMessage());
            //sinaliza o erro ao cliente
            //o cliente poderá tentar outra vez
            out.write("ERROR_IN_UPLOAD");out.newLine();
            out.flush();
        } finally {
            try{
                if (bout != null) {
                    bout.close();
                }

                if (fin != null) {
                    fin.close();
                }
            } catch (IOException ex) {
                Manager.println("T" + threadId + ": Erro ao fechar os streams do upload: " + ex.getMessage());
                ex.printStackTrace();
            }
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
            flushNotifications();
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

    public int getThreadId(){
        return this.threadId;
    }

    synchronized void sendString(String message) throws IOException {
        out.write(message);
        out.newLine();
        out.flush();
        
        flushNotifications();
    }

    void sendNotification(String notification) {
        try {
            notificationQueue.put(notification);
        } catch (InterruptedException ex) {            
        }
    }

    private void flushNotifications() throws IOException {
        while(!notificationQueue.isEmpty()){
            out.write(notificationQueue.poll());
            out.newLine();
            out.flush();
        }        
    }
}
