import java.util.Date;
import java.util.Comparator;
import java.io.Serializable;
/**
 * Escreva a descrição da classe ComparadorData aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class ComparadorData implements Comparator<Factura>, Serializable
{
    public int compare(Factura f1, Factura f2){
        if(f1.getData().before(f2.getData()))
            return -1;
        else if(f1.getData().after(f2.getData()))
            return 1;
        else 
            return 0;
    }
}
