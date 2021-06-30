import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import static java.util.stream.Collectors.toMap;
import java.io.Serializable;

public class Empresa extends Actor implements Serializable, Reducao
{
    /** Factor de dedução fiscal */
    private double deducaoF;
    
    /** Actividades em que a Empresa actua */
    private Map<String, Actividade> activ;
    
    /** Facturas da empresa */
    private Map<String, Factura> facturas;
    
    /** Concelho da Empresa */
    private Concelho concelho;
    
    /**
     * Construtor vazio
     */
    public Empresa(){
        super();
        this.deducaoF = 0;
        this.activ = new HashMap<String, Actividade>();
        this.facturas = new HashMap<String, Factura>();
        this.concelho = new Concelho();
    }
    
    /**
     * Construtor por parâmetros
     * @param nif nif do actor
     * @param email email do actor
     * @param nome nome do actor
     * @param password password do acto
     * @param morada morada do actor
     * @param tipoActor false se individual, true se empresa
     * @param deducaoF factor de deducao fiscal
     * @param activ actividades referentes à empresa
     * @param facturas facturas referentes à empresa
     * @param concelho concelho da empresa
     */
    public Empresa(String nif, String email, String nome, String password, String morada, boolean tipoActor, 
                   double deducaoF,  Map<String, Actividade> activ, Map<String, Factura> facturas, Concelho concelho){
        super(nif, email, nome, password, morada, tipoActor);
        this.deducaoF = deducaoF;
        this.activ = activ;
        this.facturas = facturas;
        this.concelho = concelho;
    }
    
    /**
     * Construtor por cópia
     * @param e Empresa a copiar
     */
    public Empresa(Empresa e){
        super(e);
        this.deducaoF = e.getDeducaoF();
        this.activ = e.getActividades();
        this.facturas = e.getFacturas();
        this.concelho = e.getConcelho();
    }
    
    /**
     * Obter o factor de deducao fiscal
     * @return deducaoF factor de deducao fiscal
     */
    public double getDeducaoF(){
        return this.deducaoF;
    }
    
    /**
     * Obter o mapeamento de codigo de actividade para actividade da empresa
     * @return mapeamento de codigo de actividade para actividade da empresa
     */
    public  Map<String, Actividade> getActividades(){
        return this.activ.entrySet()
                              .stream()
                              .collect(toMap(e->e.getKey(), e->e.getValue().clone()));
    }
    
    /**
     * Obter o mapeamento de codigo de factura para factura da empresa
     * @return mapeamento de codigo de factura para factura da empresa
     */
    public  Map<String, Factura> getFacturas(){
        return this.facturas.entrySet()
                              .stream()
                              .collect(toMap(e->e.getKey(), e->e.getValue().clone()));
    }
    
    /**
     * Obter o concelho da empresa
     * @return concelho concelho da empresa
     */
    public Concelho getConcelho(){
        return this.concelho;
    }
    
    /**
     * Define o factor de deducao fiscal
     * @param deducaoF factor de deducao fiscal
     */
    public void setDeducaoF(double deducaoF){
        this.deducaoF = deducaoF;
    }
    
    /**
     * Adiciona actividade às actividades da empresa
     * @param activ actividade a adicionar
     */
    public  void setActividades(Actividade activ){
        this.activ.put(activ.getId(), activ.clone());
    }

    /**
     * Define o concelho da empresa
     * @param concelho concelho da empresa
     */
    public void setConcelho(Concelho concelho){
        this.concelho = concelho;
    }
    
    /**
     * Adiciona factura a facturas
     * @param fact factura a adicionar
     */
    public void makeFactura(Factura factura){
         this.facturas.put(factura.getId(), factura);
    }
    
    /** 
     * Obter o factor de reducao de imposto da empresa
     * @return deducaoF factor de deducao fiscal da empresa
     */
    public double reducaoImposto(){
        if(this.concelho.getInterior()){
            this.deducaoF = 1.15;
        }
        else{
            this.deducaoF = 1;
        }
        return deducaoF;
    }
    
    /**
     * Devolve uma representação no formato textual
     * @return Devolve uma representação textual de Empresa
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Dedução Fiscal: ").append(deducaoF).append("\n");
        for(Actividade a: this.activ.values()){
            sb.append(a.toString());
        }
        for(Factura f: this.facturas.values()){
            sb.append(f.toString());
        }
        sb.append("Concelho da Empresa: ").append(concelho).append("\n");
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
        Empresa e = (Empresa) o;
        return e.getDeducaoF() == deducaoF && e.getActividades().equals(activ) && e.getFacturas().equals(facturas) 
               && e.getConcelho().equals(concelho);
    }
    
    /**
     * Devolve uma cópia desta instância
     * @return uma cópia desta instância
     */
    public Empresa clone(){
        return new Empresa(this);
    }
}
