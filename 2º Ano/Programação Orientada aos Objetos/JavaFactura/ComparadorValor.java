import java.util.Comparator;
import java.io.Serializable;
/**
 * Escreva a descrição da classe ComparadorValor aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class ComparadorValor implements Comparator<Factura>, Serializable
{
    private int ordem;
    
    public ComparadorValor(int ordem){
        this.ordem = ordem;
    }
    
    public int compare(Factura f1, Factura f2){
        if(f1.getValor() < f2.getValor())
            return -1*this.ordem;
        else if(f1.getValor() >= f2.getValor())
            return 1*this.ordem;
        else 
            return 0;
    }
}
