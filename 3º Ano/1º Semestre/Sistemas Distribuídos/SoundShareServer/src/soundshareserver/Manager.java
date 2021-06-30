package soundshareserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import javax.xml.bind.DatatypeConverter;

public class Manager {

    private final String rootFolder;
    
    //lista de musicas facilmente acessivel pelo id
    private Map<Integer,Music> musics;
    //lista de musicas facilmente acessivel pelo titulo
    private Map<String,Music> musicsByTitle;
    
    //lista de tags, cada tag contem uma lista de musicas que partilha esse tag    
    private Map<String,List<Music>> tags;

    //lista de musicas que aguardam o upload
    private Map<Integer,Music> musicsAwaitingUploads;
    
    //lista de users registados
    private Map<String, User> users;
    
    //lista de users com sessão iniciada
    private Map<String, User> usersLogged;
    
    //lista de threads a correr
    public static ArrayList<SoundShareThread> threads;
    
    
    //lock para controlar o acesso às listas de musicas
    private ReentrantLock musicLock = new ReentrantLock();
    //lock para controlar o acesso à lista de users
    private ReentrantLock userLock = new ReentrantLock();
    //lock para controlar o acesso à lista de users
    private ReentrantLock threadsLock = new ReentrantLock();
    
    /**
     * cria os diretorios necessarios para guardar os ficheiros e inicializa as listas
     * @param rootFolder
     * @throws Exception 
     */
    public Manager(String rootFolder) throws Exception{
        this.rootFolder=rootFolder;
        musics=new HashMap<>();
        musicsByTitle=new HashMap<>();
        musicsAwaitingUploads=new HashMap<>();
        tags=new HashMap<>();
        users=new HashMap<>();
        usersLogged=new HashMap();
        threads=new ArrayList<>();
      
        try {
            //garantir que este caminho existe
            Files.createDirectories(Paths.get(rootFolder));
        } catch (IOException ex) {
            throw new Exception("Manager: Erro ao criar os diretorios", ex);
        }
    }

    /**
     * efetua o login verificando se o utilizador existe e a password é igual (o hash)
     * usa um lock para controlar o acesso ao Map dos users
     * @param name
     * @param pass
     * @return 
     */
    public User login(String name, String pass) {

        userLock.lock();
        
        try {
            User l = users.get(name);
            
            if (l!=null && l.getPass().equals(hashMD5(pass)) && !usersLogged.containsValue(l)){
                usersLogged.put(name, l);
                return l;
            }
            
        } catch (NoSuchAlgorithmException ex) {
            println("Manager: Erro gerar o hash da password: " + ex.getMessage());            
            ex.printStackTrace();
        }finally{
            userLock.unlock();
        }
        return null;
    }

    /**
     * regista um novo utilizador caso ainda nao exista
     * usa um lock para controlar o acesso ao Map dos users
     * @param name
     * @param pass
     * @param author
     * @return 
     */
    boolean register(String name, String pass, boolean author) {

        userLock.lock();
        
        try {
            User l = users.get(name);
            
            //se ja existe nao permite
            if (l!=null)
                return false;
            
            users.put(name, new User(name, hashMD5(pass), author));
            return true;
        } catch (NoSuchAlgorithmException ex) {
            println("Manager: Erro gerar o hash da password: " + ex.getMessage());            
            ex.printStackTrace();
        }finally{
            userLock.unlock();
        }

        return false;
    }
    
    /**
     * constroi uma lista com todas as musicas existentes
     * a lista devolvida corresponde às musicas existentes
     * no momento da sua criacao
     * usa um lock para garantir que enquanto é verificado, a lista nao muda
     * 
     * @return 
     */
    List<Music> getAllMusics(){
        List<Music> m = new ArrayList<>();
        musicLock.lock();
        try{
            m.addAll(musics.values());
        }catch(Exception ex){
            println("Manager - getAllMusics: erro inesperado: " + ex.getMessage());            
        }finally{
            musicLock.unlock();
        }
        
        return m;
    }
    
    /**
     * constroi uma lista com todas as musicas existentes que possuam a tag indicada
     * a lista devolvida corresponde às musicas existentes no momento da sua criacao
     * usa um lock para garantir que enquanto é verificado, a lista nao muda
     * 
     * @return 
     */
    List<Music> getMusicsByTag(String tag){
        List<Music> m = new ArrayList<>();
        musicLock.lock();
        try{
            List<Music> ms = tags.get(tag);
            if (ms!=null)
                m.addAll(ms);
        }catch(Exception ex){
            println("Manager - getMusicsWithTag: erro inesperado: " + ex.getMessage());            
        }finally{
            musicLock.unlock();
        }        
        
        return m;
    }

    /**
     * devolve a Musica com o id indicado
     * 
     * @param id
     * @return null se a musica nao existir
     */
    public Music getMusicById(int id) {
        musicLock.lock();
        try{
            return musics.get(id);
        }catch(Exception ex){
            println("Manager - getMusicById: erro inesperado: " + ex.getMessage());            
        }finally{
            musicLock.unlock();
        }        
        return null;
    }
    
    /**
     * verifica se ja existe alguma musica na base de dados ou à aguardar o upload, com
     * o titulo indicado
     * 
     * este metodo usa um lock para garantir que enquanto é verificado, a lista 
     * de musicas nao é alterada
     * @param title
     * @return 
     */
    public boolean musicExists(String title) {
        musicLock.lock();
        try{
            if (musicsByTitle.containsKey(title))
                return true;
            
            for (Music music : musicsAwaitingUploads.values()) {
                if (music.getTitle().equalsIgnoreCase(title))
                    return true;
            }
            return false;
        }catch(Exception ex){
            println("Manager - musicsExists: erro inesperado: " + ex.getMessage());            
            //em caso de erro assume o mais seguro - ja existir
            return true;
        }finally{
            musicLock.unlock();
        }        
    }

    /**
     * prepara o upload de uma nova musica
     * ou seja cria uma nova instancia de Music e coloca-a nas musicas a aguardar upload
     * 
     * 
     * @param title
     * @param author
     * @param performer
     * @param year
     * @param genre
     * @param tags
     * @return  o id gerado para a nova musica
     */
    int prepareUpload(String title, String author, String performer, int year, String genre, List<String> tags) {
        musicLock.lock();
        try{
            Music m = new Music();
            m.setTitle(title);
            m.setAuthor(author);
            m.setPerformer(performer);
            m.setYear(year);
            m.setGenre(genre);
            m.setTags(tags);

            musicsAwaitingUploads.put(m.getId(), m);
            
            return m.getId();
        }catch(Exception ex){
            println("Manager - prepareUpload: erro inesperado: " + ex.getMessage());            
            //em caso de erro devolve -1
            return -1;
        }finally{
            musicLock.unlock();
        }        

        
    }

    /**
     * verifica se a musica com este id existe na lista de musicas a aguardar upload
     * @param id
     * @return 
     */
    boolean musicExistsInMusicUploads(int id) {
        musicLock.lock();
        try{
            return musicsAwaitingUploads.containsKey(id);
        }catch(Exception ex){
            println("Manager - musicExistsInMusicUploads: erro inesperado: " + ex.getMessage());            
            return false;
        }finally{
            musicLock.unlock();
        }        
    }

    /**
     * passa a musica com o id indicado da lista a aguardar uploads para a lista de musicas
     * @param id 
     */
    public void addUploadedMusic(int id) {
        musicLock.lock();
        try{
            Music m = musicsAwaitingUploads.get(id);
            //coloca na lista
            musics.put(id, m);
            //e na lista por titulo
            musicsByTitle.put(m.getTitle(), m);
            //e em cada um dos tags
            for (String tag : m.getTags()) {
                if (!tags.containsKey(tag))
                    tags.put(tag, new ArrayList<>());
                
                tags.get(tag).add(m);
            }            
            musicsAwaitingUploads.remove(id);
        }catch(Exception ex){
            println("Manager - musicExistsInMusicUploads: erro inesperado: " + ex.getMessage());
            throw ex;
        }finally{
            musicLock.unlock();
        }        
    }
    
    public void notifier(Music music) throws IOException {
            for(SoundShareThread client: threads){
                if (client.isNotifiable()){
                    println("A enviar notificação para cliente" + client.getThreadId());
                    client.sendNotification("SERVER BROADCAST: " + music.getTitle());
                }
            }
    }
    
    /**
     * adiciona a thread indicada à lista de threads que estão actualmente a correr
     * @param t
     */
    public void addThread(SoundShareThread t){
        threadsLock.lock();
        try {
            threads.add(t);
            System.out.println("Thread adicionada" + t.getId());
        } catch (Exception ex) {
            println("Manager: Erro ao adicionar Thread: " + ex.getMessage());            
            ex.printStackTrace();
        }finally{
            threadsLock.unlock();
        }
    }
    
    /**
     * remove a thread indicada da lista de threads assim que a mesma é encerrada
     * @param t
     */
    public void removeThread(SoundShareThread t){
        threadsLock.lock();
        try {
            threads.remove(t);
        } catch (Exception ex) {
            println("Manager: Erro ao adicionar Thread: " + ex.getMessage());            
            ex.printStackTrace();
        }finally{
            threadsLock.unlock();
        }
    }
    
    public void removeLoggedUser(String name){
        userLock.lock();
        try {
            usersLogged.remove(name);
        } catch (Exception ex) {
            println("Manager: Erro ao remover user com sessão iniciada: " + ex.getMessage());            
            ex.printStackTrace();
        }finally{
            userLock.unlock();
        }
    }
    
    /**
     * calcula um hash md5 do texto recebido
     * @param texto
     * @return
     * @throws NoSuchAlgorithmException 
     */
    private String hashMD5(String texto) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(texto.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
         
        return hash.toLowerCase();
    }
        
    /**
     * imprime a string para a consola prefixada pela data e hora
     * @param string 
     */
    public static void println(String string) {
        LocalDateTime dt = LocalDateTime.now();
        System.out.println(dt.format(DateTimeFormatter.ISO_DATE_TIME) + ": " + string);
    }

}
