
/**
 * Escreva a descrição da classe SemAutorizacaoException aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class SemAutorizacaoException extends Exception {
    public SemAutorizacaoException() {
        super();
    }
    
    public SemAutorizacaoException(String message) {
        super(message);
    }
    
    public SemAutorizacaoException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SemAutorizacaoException(Throwable cause) {
        super(cause);
    }
}
