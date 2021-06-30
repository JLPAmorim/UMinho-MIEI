
/**
 * Escreva a descrição da classe FacturaExistenteException aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class FacturaExistenteException extends Exception 
{
    public FacturaExistenteException() {
        super();
    }
    
    public FacturaExistenteException(String message) {
        super(message);
    }
    
    public FacturaExistenteException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public FacturaExistenteException(Throwable cause) {
        super(cause);
    }
}
