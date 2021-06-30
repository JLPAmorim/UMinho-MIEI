import java.io.Serializable;
/**
 * Escreva a descrição da classe Atividades aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Actividade implements Serializable
{
    /** Código da Actividade */
    private String codigo;
    
    /** Designacao da Actividade Económica */
    private String designacao;
    
    /** Indica se a Dedução Fiscal é permitida (por defeito NÃO permite). */
    private boolean permiteDed;
    
    /** Indica qual o factor de Dedução Fiscal da Actividade */
    private double deducaoFiscal;
    
    /**
     * Construtor vazio
     */
    public Actividade(){
        this.codigo = "n/a";
        this.designacao = "n/a";
        this.permiteDed = false;
        this.deducaoFiscal = 0.0;
    }
    
    /**
     * Construtor por parâmetros
     * @param codigo codigo da actividade
     * @param designacao designacao da actividade
     * @param permiteDed false se não permite deduzir
     * @param deducaoFiscal factor de deducao fiscal
     */
    public Actividade(String codigo, String designacao, boolean permiteDed, double deducaoFiscal){
        this.codigo = codigo;
        this.designacao = designacao;
        this.permiteDed = permiteDed;
        this.deducaoFiscal = deducaoFiscal;
    }
    
    /**
     * Construtor por cópia
     * @param a actor a copiar
     */
    public Actividade(Actividade a){
        this.codigo = a.getId();
        this.designacao = a.getDesignacao();
        this.permiteDed = a.getPermiteDed();
        this.deducaoFiscal = a.getDeducaoFiscal();
    }
    
    /**
     * Obter o codigo da Actividade
     * @return codigo codigo da actividade
     */
    public String getId(){
        return this.codigo;
    }
    
    /**
     * Obter a designacao da Actividade
     * @return designacao designacao da actividade
     */
    public String getDesignacao(){
        return this.designacao;
    }
    
    /**
     * Obter a permissão de dedução da Actividade
     * @return false se não permite, true se permite
     */
    public boolean getPermiteDed(){
        return this.permiteDed;
    }
    
    /**
     * Obter o factor de Dedução Fiscal da Actividade
     * @return deducaoFiscal deducaoFiscal da actividade
     */
    public double getDeducaoFiscal(){
        return this.deducaoFiscal;
    }
    
    /**
     * Define o codigo da Actividade
     * @param codigo codigo da actividade
     */
    public void setId(String codigo){
        this.codigo = codigo;
    }
    
    /**
     * Define a designacao da Actividade
     * @param designacao designacao da actividade
     */
    public void setDesignacao(String designacao){
        this.designacao = designacao;
    }
    
    /**
     * Define a permissão da dedução da Actividade
     * @param permiteDed false se não permite, true se permite
     */
    public void setPermiteDed(boolean permiteDed){
        this.permiteDed = permiteDed;
    }
    
    /**
     * Define o factor de dedução fiscal da Actividade
     * @param deducaoFiscal deducaoFiscal da actividade
     */
    public void setDeducaoFiscal(double deducaoFiscal){
        this.deducaoFiscal = deducaoFiscal;
    }
    
    /**
     * Devolve uma cópia desta instância
     * @return Devolve uma cópia de Actividade
     */
    public Actividade clone() {
        return new Actividade(this);
    }
    
    /**
     * Devolve uma representação no formato textual
     * @return Devolve uma representação textual de Actividade
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Código: ").append(codigo).append("\n");
        sb.append("Designacao: ").append(designacao).append("\n");
        if(permiteDed)
            sb.append("Actividade Económica permite Dedução\n");
        else
            sb.append("Actividade Económica NÃO permite Dedução\n");
        sb.append("Taxa de Dedução Fiscal: ").append(deducaoFiscal).append("\n");
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
        Actividade a = (Actividade) o;
        return a.getId().equals(codigo) && a.getDesignacao().equals(designacao) && a.getPermiteDed() == permiteDed 
               && a.getDeducaoFiscal() == deducaoFiscal;
    }
}


