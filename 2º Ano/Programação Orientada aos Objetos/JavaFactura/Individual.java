import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import static java.util.stream.Collectors.toMap;
import java.io.Serializable;

public class Individual extends Actor implements Serializable, Reducao
{
    /** Número do Agregado Familiar */
    private int numAgregado;
    
    /** Números Fiscais do Agregado Familiar */
    private ArrayList<String> nifAgregado;
    
    /** Coeficiente Fiscal para Efeitos de Dedução */
    private double coefFiscal;
    
    /** Código das Actividades Económicas nas quais Contribuinte Individual pode deduzir */
    private ArrayList<String> idActiv;
    
    /** Número de filhos do Contribuinte Individual */
    private int filhos;
    
    /**
     * Construtor vazio
     */
    public Individual(){
        super();
        this.numAgregado = 0;
        this.nifAgregado = new ArrayList<String>();
        this.coefFiscal = 0.0;
        this.idActiv = new ArrayList<String>();
        this.filhos = 0;
    }
    
    /**
     * Construtor por parâmetros
     * @param nif nif do actor
     * @param email email do actor
     * @param nome nome do actor
     * @param password password do acto
     * @param morada morada do actor
     * @param tipoActor false se individual, true se empresa
     * @param numAgregado numero do agregado familiar
     * @param nifAgregado lista com os nifs do agregado familiar
     * @param coefFsical coeficiente fiscal do contribuinte individual
     * @param idActiv lista com os códigos das actividades nas quais contribuinte individual deduz
     * @param filhos numero de filhos do contribuinte individual
     */
    public Individual(String nif, String email, String nome, String password, String morada, boolean tipoActor,
                      int numAgregado, ArrayList<String> nifAgregado, double coefFiscal, ArrayList<String> idActiv, int filhos){
        super(nif, email, nome, password, morada, tipoActor);
        this.numAgregado = numAgregado;
        this.nifAgregado = nifAgregado;
        this.coefFiscal = coefFiscal;
        this.idActiv = idActiv;
        this.filhos = filhos;
    }
    
    /**
     * Construtor por cópia
     * @param i Individual a copiar
     */
    public Individual(Individual i){
        super(i);
        this.numAgregado = i.getNumAgregado();
        this.nifAgregado = i.getNifAgregado();
        this.coefFiscal = i.getCoefFiscal();
        this.idActiv = i.getIdActiv();
        this.filhos = i.getFilhos();
    }
    
    /**
     * Obter o numero de elementos do agregado familiar
     * @return numAgregado numero de elementos do agregado familiar
     */
    public int getNumAgregado(){
        return this.numAgregado;
    }
    
    /**
     * Obter a lista dos nifs dos elementos do agregado familiar de individual
     * @return nifAgregado lista dos nifs dos elementos do agregado familiar de individual
     */
    public ArrayList<String> getNifAgregado(){
        return nifAgregado;
    }
    
    /**
     * Obter o coeficiente fiscal de individual
     * @return coefFiscal coeficiente fiscalde individual
     */
    public double getCoefFiscal(){
        return this.coefFiscal;
    }
    
    /**
     * Obter a lista dos codigos das actividades nas quais individual deduz
     * @return idActiv lista dos codigos das actividades nas quais individual deduz
     */
    public ArrayList<String> getIdActiv(){
        return this.idActiv;
    }
    
    /**
     * Obter o numero de filhos de individual
     * @return filhos numero de filhos
     */
    public int getFilhos(){
        return this.filhos;
    }
    
    /**
     * Define o numero do agregado familiar
     * @param numAgregado numero do agregado familiar
     */
    public void setNumAgregado(int numAgregado){
        this.numAgregado = numAgregado;
    }
    
    /**
     * Adiciona nif de elemento do agregado
     * @param num nif do elemento a adicionar
     */
    public void setNifAgregado(String num){
        this.nifAgregado.add(num);
    }
    
    /**
     * Define o coeficinte fiscal de individual
     * @param coefFiscal coeficiente fiscal de individual
     */
    public void setCoefFiscal(double coefFiscal){
        this.coefFiscal = coefFiscal;
    }
    
    /**
     * Adiciona código da actividade no qual individual deduz
     * @param idActiv codigo d actividade
     */
    public  void setActividades(String idActiv){
        this.idActiv.add(idActiv);
    }
    
    /**
     * Define o numero de filhos de individual
     * @param filhos filhos de individual
     */
    public void setFilhos(int filhos){
        this.filhos = filhos;
    }
    
    /** 
     * Obter o factor de reducao de imposto de individual
     * @return coefFiscal factor de deducao fiscal de individual
     */
    public double reducaoImposto(){
        if(this.filhos>4){
            this.coefFiscal = 1+(this.filhos*0.05);
        }
        else{
            this.coefFiscal = 1;
        }
        return coefFiscal;
    }
    
    /**
     * Devolve uma cópia desta instância
     * @return uma cópia desta instância
     */
    public Individual clone(){
        return new Individual(this);
    }
    
    /**
     * Devolve uma representação no formato textual
     * @return Devolve uma representação textual de Individual
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Número do Agregado Familiar: ").append(numAgregado).append("\n");
        for(String s: this.nifAgregado){
            sb.append("NIF do Elemento do Agregado: ").append(idActiv).append("\n");
        }
        sb.append("Coeficiente Fiscal para Efeitos de Dedução: ").append(coefFiscal).append("\n");
        for(String s: this.idActiv){
            sb.append("Código das Actividades Económica em que o Contribuinte Desconta: ").append(idActiv).append("\n");
        }
        sb.append("Número de filhos: ").append(filhos).append("\n");
        return sb.toString();
    }
    
    /**
     * Compara a igualdade com outro objeto
     * @param o obejto a comparar
     * @return true se são iguais, false se o oposto
     */
    public boolean equals(Object o){
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        Individual i = (Individual) o;
        return i.getNumAgregado() == numAgregado && i.getCoefFiscal() == coefFiscal &&
        i.getIdActiv().equals(idActiv) && i.getFilhos() == filhos;
    }
}
