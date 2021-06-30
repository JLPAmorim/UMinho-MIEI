
/**
 * Escreva a descrição da classe Concelho aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Concelho
{
    /** Nome do Concelho */
    private String nome;
    /** Indica se é Empresa do Interior ou não, por defeito não é */
    private boolean interior;
    
    /**
     * Constructor vazio
     */
    public Concelho(){
        this.nome = "n/a";
        this.interior = false;
    }
    
    /**
     * Contrutor por parametros
     * @param data data da consulta
     * @param email email do utilizador que fez a consulta (null se nao estava registado)
     */
    public Concelho(String nome, boolean interior){
        this.nome = nome;
        this.interior = interior;
    }
    
    /**
     * Construtor por cópia
     * @param c concelho a copiar
     */
    public Concelho(Concelho c){
        this.nome = c.getNome();
        this.interior = c.getInterior();
    }
    
    /**
     * Obter nome do concelho
     * @return nome nome do concelho
     */
    public String getNome(){
        return this.nome;
    }
    
    /**
     * Obter a informação sobre a localizacao da empresa
     * @return interior informacao sobre a localizacao da empresa
     */
    public boolean getInterior(){
        return this.interior;
    }
    
    /**
     * Definir o nome do concelho
     * @param nome nome do concelho
     */
    public void setNome(String nome){
        this.nome = nome;
    }
    
    /**
     * Definir a localizacao do concelho, se é interior ou nao
     * @param interior false se não é do interior, true se é
     */
    public void setInterior(boolean interior){
        this.interior = interior;
    }
    
    /**
     * Devolve uma cópia desta instância
     * @return uma cópia desta instância
     */
    public Concelho clone(){
        return new Concelho(this);
    }
    
    /**
     * Compara a igualdade com outro objecto
     * @param o objeto a comparar
     * @return true se iguais, false se o oposto
     */
    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        Concelho c = (Concelho) o;
        return c.getNome().equals(nome) && c.getInterior() == interior;
    }
    
    /**
     * Devolve uma representação no formato textual
     * @return string com a representação
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome do Concelho: ").append(nome).append("\n");
        sb.append("É do Interior: ").append(interior).append("\n");
        return sb.toString();
    }
    
    

}
