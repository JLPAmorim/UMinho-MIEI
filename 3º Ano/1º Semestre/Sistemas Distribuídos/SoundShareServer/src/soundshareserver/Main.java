package soundshareserver;

import java.util.Arrays;

/**
 * inicia a plataforma com users e músicas para teste
 */

public class Main {
    public static void main(String[] args) {
        SoundShareServer server = new SoundShareServer();
        
        //para testes
        Manager manager = server.getManager();
        manager.register("aaa", "aaa", true);
        manager.register("bbb", "bbb", false);
        
        //os ficheiros tem de existir ja com os nomes musica_1, 2 e 3 
        int id = manager.prepareUpload("musica um", "autor um", "Interprete um", 2000, "rock", Arrays.asList("rock","2000s"));        
        manager.addUploadedMusic(id);
        
        id = manager.prepareUpload("musica dois", "autor dois", "Interprete dois", 1600, "classical", Arrays.asList("classical","1600s"));        
        manager.addUploadedMusic(id);
        
        id = manager.prepareUpload("musica tres", "autor tres", "Interprete tres", 2000, "pop", Arrays.asList("pop","2000s"));        
        manager.addUploadedMusic(id);
        
        //a proxima sera a 4
        
        
        server.start();

    }
}
