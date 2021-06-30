import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;


public class Registo implements Serializable {
    /** Data da consulta */
    private Date data;
    
    /** NIF do actor que fez a consulta */
    private String nif;
    
    /** Código da Factura Confirmada */
    private String codigo;
    
    /** Designação da Actividade da Factura Depois da Confirmação */
    private String nomeAct;
    
    /**
     * Constructor vazio
     */
    public Registo() {
        this.data = new Date();
        this.nif = null;
        this.codigo = null;
        this.nomeAct = null;
    }
    
    /**
     * Contrutor por parametros
     * @param data data do registo
     * @param nif nif do contribuinte que fez a confirmacao 
     * @param codigo codigo da factura confirmada
     * @param nome nome da actividade da factura confirmada
     */
    public Registo(Date data, String nif, String codigo, String nomeAct) {
        this.data = data;
        this.nif = nif;
        this.codigo = codigo;
        this.nomeAct = nomeAct;
    }
    
    /**
     * Cosntrutor por cópia
     * @param r Registo a copiar
     */
    public Registo(Registo r) {
        this.data = r.getData();
        this.nif = r.getNif();
        this.codigo = r.getId();
        this.nomeAct = r.getNomeAct();
    }
    
    /**
     * Obter a data do registo
     * @return data data do registo
     */
    public Date getData() {
        return this.data;
    }
    
    /**
     * Obter o nif do contribuinte que fez a confirmacao
     * @return nif nif do contribuinte que fez a confirmacao
     */
    public String getNif() {
        return this.nif;
    }
    
    /**
     * Obter o codigo do contribuinte que fez a confirmacao
     * @return codigo codigo do contribuinte que fez a confirmacao
     */
    public String getId(){
        return this.codigo;
    }
    
    /**
     * Obter o nome da actividade economica da factura confirmada
     * @return nomeAct nome da actividade economica da factura confirmada
     */
    public String getNomeAct(){
        return this.nomeAct;
    }
    
    /**
     * Definir a data do registo
     * @param data data do registo da confirmacao
     */
    public void setData(Date data){
        this.data = data;
    }
    
    /**
     * Definir nif do contribuinte que efetuou a confirmacao
     * @param nif nif do contribuinte que efetuou a confirmacao
     */
    public void setNif(String nif) {
        this.nif = nif;
    }
    
    /**
     * Definir a data do registo
     * @param codigo codigo da factura confirmada
     */   
    public void setId(String codigo){
        this.codigo = codigo;
    }
    
    /**
     * Definir o nome da actividade economica da factura confirmada
     * @param nomeAct nome da actividade economica da factura confirmada
     */
    public void setNomeAct(String nomeAct){
        this.nomeAct = nomeAct;
    }
    
    /**
     * Devolve uma cópia desta instância
     * @return uma cópia desta instância
     */
    public Registo clone(){
        return new Registo(this);
    }
    
    /**
     * Obter a representação textual de um registo
     * @return representação textual de um registo
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Data: ").append(data.toString()).append("\n");
        sb.append("NIF do Contribuinte: ").append(nif).append("\n");
        sb.append("Código da Factura: ").append(codigo).append("\n");
        sb.append("Nome da Actividade Confirmada: ").append(nomeAct).append("\n");
        return sb.toString();
    }
    
    /**
     * Verifica a igualdade com outro objecto
     * @param o objeto a comparar
     * @return true se forem iguais, false se o oposto
     */
    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        Registo r = (Registo) o;
        return r.getData().equals(data) && r.getNif().equals(nif) && r.getId().equals(codigo) && r.getNomeAct().equals(nomeAct);
    }
}
