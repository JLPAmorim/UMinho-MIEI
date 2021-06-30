import java.util.ArrayList;
import java.io.Serializable;
/**
 * Escreva a descrição da classe Admin aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Admin implements Serializable
{   
    /** Codigo do Admin */
    private String code;
    /** Password do Admin */
    private String password;
    /** Registos do Admin */
    private ArrayList<Registo> registos;
    
    /**
     * Construtor vazio
     */
    public Admin(){
        this.code = "admin";
        this.password = "123";
        this.registos = new ArrayList<Registo>();
    }
    
    /**
     * Construtor por parâmetro
     * @param code codigo do admin
     * @param password password do admin
     * @param registos registos do admin
     */
    public Admin(String code, String password, ArrayList<Registo> registos){
        this.code = code;
        this.password = password;
        this.registos = registos;
    }
    
    /**
     * Construtor por cópia
     * @param a admin a copiar
     */
    public Admin(Admin a){
        this.code = a.getId();
        this.password = a.getPass();
        this.registos = a.getRegistos();
    }
    
    /**
     * Obter o codigo do admin
     * @return code codigo do admin
     */
    public String getId(){
        return this.code;
    }
    
    /**
     * Obter a password
     * @return password pass do admin
     */
    public String getPass(){
        return this.password;
    }
    
    /**
     * Obter os registos do admin
     * @return registos registos do admin
     */
    public ArrayList<Registo> getRegistos(){
        return this.registos;
    }
    
    /**
     * Definir os registos do admin
     * @param registos registos do admin
     */
    public void setRegistos(Registo registo){
        this.registos.add(registo);
    }
    
    /**
     * Devolve uma cópia desta instância
     * @return uma cópia desta instância
     */
    public Admin clone(){
        return new Admin(this);
    }
    
    /**
     * Devolve uma representação no formato textual
     * @return string com a representação
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Código: ").append(code).append("\n");
        sb.append("Código: ").append(password).append("\n");
        for(Registo r: this.registos){
            sb.append("Registos: ").append(registos).append("\n");
        }
        return sb.toString();
    }
}
