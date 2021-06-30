import java.io.Serializable;
/**
 * Escreva a descrição da classe Utilizador aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Actor implements Serializable
{
    /** Número Fiscal (NIF) */
    private String nif;
    
    /** Email do Actor */
    private String email;
    
    /** Nome do Actor */
    private String nome;
    
    /** Password do Actor */
    private String password;
    
    /** Morada do Actor */
    private String morada;
    
    /** Contribuinte Individual ou Empresa (por defeito é Individual)*/
    private boolean tipoActor;
    
    /**
     * Construtor vazio
     */
    public Actor(){
        this.nif = "000000000";
        this.email = "n/a";
        this.nome = "n/a";
        this.password = "n/a";
        this.morada = "n/a";
        this.tipoActor = false;
    }
    
    /**
     * Construtor por cópia
     * @param a actor a copiar
     */
    public Actor(Actor a) {
        this.nif = a.getNif();
        this.email = a.getEmail();
        this.nome = a.getNome();
        this.password = a.getPassword();
        this.morada = a.getMorada();
        this.tipoActor = a.getTipoActor();
    }
    
    /**
     * Construtor por parâmetros
     * @param nif nif do actor
     * @param email email do actor
     * @param nome nome do actor
     * @param password password do acto
     * @param morada morada do actor
     * @param tipoActor false se individual, true se empresa
     */
    public Actor(String nif, String email, String nome, String password, String morada, boolean tipoActor) {
        this.nif = nif;
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.morada = morada;
        this.tipoActor = tipoActor;
    }
    
    /**
     * Obter o Nif do Actor
     * @return nif nif do actor
     */
    public String getNif() {
        return this.nif;
    }
    
    /**
     * Obter o email
     * @return email email do actor
     */
    public String getEmail() {
        return this.email;
    }
    
    /**
     * Obter o nome
     * @return nome nome do actor
     */ 
    public String getNome() {
        return this.nome;
    }
    
    /**
     * Obter a password
     * @return password password do actor
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * Obter a morada
     * @return morada morada do actor
     */
    public String getMorada() {
        return this.morada;
    }
    
    /**
     * Obter o tipo de actor
     * @return tipoActor false se individual, true se empresa
     */
    public boolean getTipoActor() {
        return this.tipoActor;
    }
    
    /**
     * Definir o nif do Actor
     * @param nif nif do actor
     */
    public void setNif(String nif) {
        this.nif = nif;
    }
        
    /**
     * Define o email
     * @param email email do actor
     */
    public void setEmail(String email) {
        this.email = email;
    }
        
    /**
     * Define o nome
     * @param nome nome do actor
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
            
    /**
     * Define a password
     * @param password password do actor
     */
    public void setPassword(String password) {
        this.password = password;
    }
            
    /**
     * Define a morada
     * @param morada morada do actor
     */
    public void setMorada(String morada) {
        this.morada = morada;
    }
        
    /**
     * Definir o tipo de actor
     * @param tipoActor false se individual, true se empresa
     */
    public void setTipoActor(boolean tipoActor) {
        this.tipoActor = tipoActor;
    }
    
    /**
     * Código de hash
     * @return hashCode
     */
    public int hashCode() {
        return nif.hashCode();
    }
    
    /**
     * Devolve uma cópia desta instância
     * @return uma cópia desta instância
     */
    public Actor clone(){
        return new Actor(this);
    }
    
    /**
     * Devolve uma representação no formato textual
     * @return Devolve uma representação textual de Actor
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("NIF do Actor: ").append(nif).append("\n");
        if(tipoActor)
            sb.append("Tipo de actor: Empresa\n");
        else
            sb.append("Tipo de actor: Individual\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("Morada: ").append(morada).append("\n");
        return sb.toString();
    }
    
    /**
     * Compara a igualdade com outro objeto
     * @param o obejto a comparar
     * @return true se são iguais, false se o oposto
     */
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        Actor a = (Actor) o;
        return a.getNif().equals(nif) && a.getEmail().equals(email) && a.getNome().equals(nome) && 
               a.getPassword().equals(password) && a.getMorada().equals(morada) && a.getTipoActor() == tipoActor;
    }
}
