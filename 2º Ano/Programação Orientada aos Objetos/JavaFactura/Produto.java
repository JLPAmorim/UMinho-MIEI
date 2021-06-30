import java.io.Serializable;
/**
 * Escreva a descrição da classe Produto aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Produto implements Serializable
{
    /** Nome do Produto */
    private String nome;
    
    /** Preço do Produto */
    private double preco;

    
    /**
     * Construtor vazio
     */
    public Produto(){
        this.nome = "n/a";
        this.preco = 0.0;
    }
    
        /**
     * Construtor por parâmetros
     * @param nome nome do produto
     * @param preco preço do produto
     */
    public Produto(String nome, double preco){
        this.nome = nome;
        this.preco = preco;
    }
    
    /**
     * Construtor por cópia
     * @param p Produto a copiar
     */
    public Produto(Produto p){
        this.nome = p.getNome();
        this.preco = p.getPreco();
    }
    
    /**
     * Obter o nome
     * @return nome do actor
     */ 
    public String getNome(){
        return this.nome;
    }
    
    /**
     * Obter o preço
     * @return preco preco do produto
     */ 
    public double getPreco(){
        return this.preco;
    }
    
    /**
     * Define o nome
     * @param nome nome do produto
     */
    public void setNome(String nome){
        this.nome = nome;
    }
    
    /**
     * Definir o preco do produto
     * @param preco preco do produto
     */
    public void setPreco(double preco){
        this.preco = preco;
    }
    
    /**
     * Devolve uma cópia desta instância
     * @return Devolve uma cópia de Produto
     */
    public Produto clone() {
        return new Produto(this);
    }
    
    /**
     * Devolve uma representação no formato textual
     * @return Devolve uma representação textual de Produto
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome do Produto: ").append(nome).append("\n");
        sb.append("Preço do Produto: ").append(preco).append("\n");
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
        Produto p = (Produto) o;
        return p.getNome().equals(nome) && p.getPreco() == preco;
    }
}
