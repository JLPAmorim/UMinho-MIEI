
/**
 * Escreva a descrição da classe FacturaInexistenteException aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class FacturaInexistenteException extends Exception
{
    public FacturaInexistenteException() {
        super();
    }
    
    public FacturaInexistenteException(String message) {
        super(message);
    }
    
    public FacturaInexistenteException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public FacturaInexistenteException(Throwable cause) {
        super(cause);
    }
}
