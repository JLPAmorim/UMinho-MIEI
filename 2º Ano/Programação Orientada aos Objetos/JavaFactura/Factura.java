import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import static java.util.stream.Collectors.toMap;
import java.io.Serializable;


public class Factura implements Serializable{  
    /** Código da Factura */
    private String codigo;
    
    /** Número Fiscal do Emitente */
    private String nifEmitente;
    
    /** Designação do Emitente */
    private String desiEmitente;
    
    /** Data da Despesa */
    private Date data;
    
    /** Número Fiscal do Cliente */
    private String nifCliente;
    
    /** Descrição da Despesa */
    private String despesa;
    
    /** Actividade Económica da Despesa */
    private Actividade activ;
    
    /** Produtos da Factura */
    private ArrayList<Produto> produtos;
    
    /** Valor da Despesa */
    private double valor;
    
    /** Estado da Factura */
    private String estado;
    
    /**
     * Construtor vazio
     */
    public Factura(){
        this.codigo = "n/a";
        this.nifEmitente = "n/a";
        this.desiEmitente = "n/a";
        this.data = new Date();
        this.nifCliente = "n/a";
        this.despesa = "n/a";
        this.activ = new Actividade();
        this.produtos = new ArrayList<Produto>();
        this.valor = 0.0;
        this.estado = "Pendente";
    }
    
    /**
     * Construtor por parâmetros
     * @param codigo codigo da factura
     * @param nifEmitente nif da empresa que emite a factura
     * @param desiEmitente designacao da empresa que emitiu a factura
     * @param data data da emissao da factura
     * @param nifCliente nif do cliente a que se destina a factura
     * @param despesa descricao da despesa
     * @param activ actividade da factura
     * @param produtos produtos da factura
     * @param valor valor total correspondente a fatura
     * @param estado estado da factura, "Pendente" por defeito
     */
    public Factura(String codigo, String nifEmitente, String desiEmitente, Date data, String nifCliente, String despesa,
                    Actividade activ, ArrayList<Produto> produtos, double valor, String estado){
        this.codigo = codigo;
        this.nifEmitente = nifEmitente;
        this.desiEmitente = desiEmitente;
        this.data = data; 
        this.nifCliente = nifCliente;
        this.despesa = despesa;
        this.activ= activ;
        this.produtos = produtos;
        this.valor = valor;
        this.estado = estado;
    }
    
    /**
     * Construtor por cópia
     * @param f Factura a copiar
     */
    public Factura(Factura f){
        this.codigo = f.getId();
        this.nifEmitente = f.getNifEmitente();
        this.desiEmitente = f.getDesiEmitente();
        this.data = f.getData();
        this.nifCliente = f.getNifCliente();
        this.despesa = f.getDespesa();
        this.activ = f.getActividade();
        this.produtos = f.getProdutos();
        this.valor = f.getValor();
        this.estado = f.getEstado();
    }
    
    /**
     * Obter o codigo da factura
     * @return codigo codigo da factura
     */
    public String getId(){
        return this.codigo;
    }
    
    /**
     * Obter o nif da empresa emitente
     * @return nifEmitente nif da empresa emitente
     */
    public String getNifEmitente(){
        return this.nifEmitente;
    }
    
    /**
     * Obter a designacao da empresa emitente
     * @return desiEmitente designacao da empresa emitente
     */
    public String getDesiEmitente(){
        return this.desiEmitente;
    }
    
    /**
     * Obter a data da factura
     * @return data da factura
     */
    public Date getData(){
        return this.data;
    }
    
    /**
     * Obter o nif do cliente da factura
     * @return nifCliente nif do cliente da factura
     */
    public String getNifCliente(){
        return this.nifCliente;
    }
    
    /**
     * Obter a descricao da despesa
     * @return despesa descricao da despesa
     */
    public String getDespesa(){
        return this.despesa;
    }
    
    /**
     * Obter a actividade da factura
     * @return activ actividade da factura
     */
    public Actividade getActividade(){
        return this.activ;
    }
    
    /**
     * Obter a lista dos produtos da factura
     * @return produtos produtos da factura
     */
    public ArrayList<Produto> getProdutos(){
        return this.produtos;
    }
    
    /**
     * Obter o valor total da factura
     * @return valor valor da factura
     */
    public double getValor(){
        return this.valor;
    }
    
    /**
     * Obter o estado da factura
     * @return estado estado da factura
     */
    public String getEstado(){
        return this.estado;
    }
    
    /**
     * Definir o codigo da factura
     * @param codigo codigo da factura
     */
    public void setId(String codigo){
        this.codigo = codigo;
    }
    
    /**
     * Definir o nif da empresa emitente
     * @param nif nif da empresa emitente
     */
    public void setNifEminente(String nif){
        this.nifEmitente = nif;
    }
    
    /**
     * Definir a designacao do emitente
     * @param des designacao do emitente
     */
    public void setDesiEmitente(String des){
        this.desiEmitente = des;
    }
    
    /**
     * Definir a data da factura
     * @param data data da factura
     */
    public void setData(Date data){
        this.data = data;
    }
    
    /**
     * Definir o nif do cliente da factura
     * @param nif nif do cliente
     */
    public void setNifCliente(String nif){
        this.nifCliente = nif;
    }
    
    /**
     * Definir a descricao da despesa
     * @param despesa descricao da despesa
     */
    public void setDespesa(String despesa){
        this.despesa = despesa;
    }
    
    /**
     * Definir a actividade da factura
     * @param activ actividade da factura
     */
    public void setActividade(Actividade activ){
        this.activ = activ;
    }
    
    /**
     * Adicionar um produto a lista de produtos da factura
     * @param p produto a adicionar
     */
    public void setProdutos(Produto p) {
        this.produtos.add(p);
    }
    
    /**
     * Definir o valor da factura
     * @param valor valor factura
     */
    public void setValor(double valor){
        this.valor = valor;
    }
    
    /**
     * Definir o estado da factura
     * @param estado estado factura
     */
    public void setEstado(String estado){
        this.estado = estado;
    }
    
    /**
     * Devolve uma cópia desta instância
     * @return Devolve uma cópia de Factura
     */
    public Factura clone(){
        return new Factura(this);
    }
    
    /**
     * Devolve uma representação no formato textual
     * @return Devolve uma representação textual de Factura
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Código da Factura: ").append(codigo).append("\n");
        sb.append("NIF do Emitente: ").append(nifEmitente).append("\n");
        sb.append("Designação do Emitente: ").append(desiEmitente).append("\n");
        sb.append("Data: ").append(data).append("\n");
        sb.append("NIF do cliente: ").append(nifCliente).append("\n");
        sb.append("Despesa: ").append(despesa).append("\n");
        sb.append("Actividade Económica da Factura: ").append(activ.getDesignacao()).append("\n");
        for(Produto p : this.produtos){
            sb.append(p.toString());
        }
        sb.append("Valor: ").append(valor).append("\n");
        sb.append("Estado: ").append(estado).append("\n");
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
        Factura f = (Factura) o;
        return f.getNifEmitente().equals(nifEmitente) && f.getDesiEmitente().equals(desiEmitente) && f.getData().equals(data)
        && f.getNifCliente().equals(nifCliente) && f.getDespesa().equals(despesa) && f.getActividade().equals(activ)
        && f.getProdutos().equals(produtos) && f.getValor() == valor;
    }
    
    /**
     * Código de hash
     * @return hashCode
     */
    public int hashCode() {
        return codigo.hashCode();
    }
}
