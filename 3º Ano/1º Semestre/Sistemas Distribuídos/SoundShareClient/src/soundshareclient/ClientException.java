package soundshareclient;

public class ClientException extends Exception {

    public ClientException(String string) {
        super(string);
    }
    
    public ClientException(String string, Throwable cause) {
        super(string, cause);
    }
}
