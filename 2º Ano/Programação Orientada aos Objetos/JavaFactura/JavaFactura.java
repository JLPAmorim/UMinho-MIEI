import java.util.Scanner;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Comparator;
import java.io.IOException;
import java.util.stream.Collectors;
import java.lang.ClassNotFoundException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Serializable;
import static java.util.stream.Collectors.toMap;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;

public class JavaFactura implements Serializable
{
    /** Login de Individual */
    private boolean loginIndividual;
    
    /** Login de Empresa */
    private boolean loginEmpresa;
    
    /** Login de Admin */
    private boolean loginAdmin;
    
    private ArrayList<Registo> registos;
    
    /** Código do Actor actual*/
    private String codigoActor;   
     
    /** Mapeamento de Actores */
    private Map<String,Actor> actores;
    
    /** Mapeamento de Facturas */
    private Map<String, Factura> facturas;
    
    /**Mapeamento de Actividades */
    
    private Map<String, Actividade> todasActiv;
    
   /**
     * Construtor vazio
     */
    public JavaFactura(){
        this.loginIndividual = false;
        this.loginEmpresa = false;
        this.loginAdmin = false;
        this.registos = new ArrayList<Registo>();
        this.codigoActor = null;
        this.actores = new HashMap<String, Actor>();
        this.facturas = new HashMap<String, Factura>();
        this. todasActiv = new HashMap<String, Actividade>();
   }
    
   /**
     * Construtor por parâmetros
     * @param loginIndividual Sessão como individual se true
     * @param loginEmpresa Sessão como Empresa se true
     * @param loginAdmin Sessão como Admin se true
     * @param registos lista de todos os registos de facturas confirmadas
     * @param codigoActor Código do Actor com sessão iniciada
     * @param actores Mapeamento de actores registados
     * @param facturas Mapeamento de facturas registadas
     * @param todasActiv Mapeamento de todas as Actividades Económicas existentes
     */
    public JavaFactura(boolean loginIndividual, boolean loginEmpresa, boolean loginAdmin, ArrayList<Registo> registos, 
                       String codigoActor, Map<String, Actor> actores, Map<String, Factura> facturas, 
                       Map<String, Actividade> todasActiv){
        this.loginIndividual = loginIndividual;
        this.loginEmpresa = loginEmpresa;
        this.loginAdmin = loginAdmin;
        this.registos = registos;
        this.codigoActor = codigoActor;
        this.actores = actores;
        this.facturas = facturas;
        this.todasActiv = todasActiv;
   }
    
   /**
     * Construtor por cópia
     * @param jf JavaFactura a copiar
     */
    public JavaFactura(JavaFactura jf){
        this.loginIndividual = jf.getLoginIndividual();
        this.loginEmpresa = jf.getLoginEmpresa();
        this.loginAdmin = jf.getLoginAdmin();
        this.registos = jf.getRegistos();
        this.codigoActor = jf.getCodigoActor();
        this.actores = jf.getActores();
        this.facturas = jf.getFacturas();
        this.todasActiv = jf.getTodasActiv();
   }
    
   /**
     * Obter informação sobre o login do Contribuinte Individual
     * @return true se tem sessao como individual
     */
    public boolean getLoginIndividual() {
        return this.loginIndividual;
   }
    
   /**
     * Obter informação sobre o login de empresa
     * @return true se tem sessao como empresa
     */
    public boolean getLoginEmpresa() {
        return this.loginEmpresa;
   }
    
   /**
     * Obter informação sobre o login de empresa
     * @return true se tem sessao como empresa
     */
    public boolean getLoginAdmin() {
        return this.loginAdmin;
   }
    
   /**
     * Obter a lista dos registos de confirmações de facturas
     * @return lista dos dos registos
     */
    public ArrayList<Registo> getRegistos(){
        return this.registos;
   }
    
   /**
     * Obter o código do actor atual
     * @return codigo do actor com sessao iniciada
     */
    public String getCodigoActor() {
        return this.codigoActor;
   }
    
   /**
     * Obter o mapeamento de de actores da aplicação
     * @return mapeamento de actores da aplicação
     */
   public Map<String, Actor> getActores() {
        return this.actores;
   }
    
   /**
     * Obter o mapeamentos de facturas da aplicação
     * @return mapeamento de codigos para facturas
     */
    public Map<String, Factura> getFacturas() {
        return this.facturas;
   }
    
   /**
     * Obter o mapeamentos de todas as Actividades já pré-definidas da aplicação
     * @return mapeamento de codigos para actividades
     */
    public Map<String, Actividade> getTodasActiv(){
       Map<String, Actividade> acts = new HashMap<String, Actividade>();
       Actividade act1 = new Actividade("1","Saúde",true,0.15);
       Actividade act2 = new Actividade("2","Educação",true,0.15);
       Actividade act3 = new Actividade("3","Imóveis",true,0.2);
       Actividade act4 = new Actividade("4","Lares",true,0.2);
       Actividade act5 = new Actividade("5","Manutenção e reparação de veículos",true,0.2);
       Actividade act6 = new Actividade("6","Manutenção e reparação e motocilcos",true,0.2);
       Actividade act7 = new Actividade("7","Alojamento, restauração e similares",true,0.2);
       Actividade act8 = new Actividade("8","Actividades de Salões de cabeleireiro e institutos de beleza",true,0.2);
       Actividade act9 = new Actividade("9","Actividades Veterinárias",true,0.2);
       Actividade act10 = new Actividade("10","Passes mensais para utilizacação de transportes públicos",true,0.2);
       Actividade act11 = new Actividade("11","Outros",true,0.2);
       
       acts.put(act1.getId(), act1.clone());
       acts.put(act2.getId(), act2.clone());
       acts.put(act3.getId(), act3.clone());
       acts.put(act4.getId(), act4.clone());
       acts.put(act5.getId(), act5.clone());
       acts.put(act6.getId(), act6.clone());
       acts.put(act7.getId(), act7.clone());
       acts.put(act8.getId(), act8.clone());
       acts.put(act9.getId(), act9.clone());
       acts.put(act10.getId(), act10.clone());
       acts.put(act11.getId(), act11.clone());
       this.todasActiv = acts;
       
       return this.todasActiv;
   }
   
   /**
    * Definir o código do Actor actual
    * @param codigo codigo do actor actual
    */
   public void setCodigoActor(String codigo){
        this.codigoActor = codigo;
   }
   
   /**
     * Função de teste do programa
     * @return JavaFactura para poder testar as funcionalidades
     */
   public static JavaFactura testApp(){
       Map<String, Factura> fact = new HashMap<String, Factura>();
       Map<String, Actividade> allActividades = new HashMap<String, Actividade>();
       Map<String, Actividade> actEmpresa1 = new HashMap<String, Actividade>();
       Map<String, Actividade> actEmpresa2 = new HashMap<String, Actividade>();
       ArrayList<String> actIndividual = new ArrayList<String>();
       ArrayList<String> nifAgregado = new ArrayList<String>();
       ArrayList<Produto> prods = new ArrayList<Produto>();
       JavaFactura jf = new JavaFactura();
       
       // allActividades passa a conter todas as actividades respectivas à aplicação
       // devido ao uso do método mapActs()
       allActividades = jf.getTodasActiv();
       
       //Adiciona todas as Actividades que deduzem a actIndividual
       for(Actividade a: allActividades.values()){
           if(a.getPermiteDed()){
               actIndividual.add(a.getId());
           }
       }      
       
       //Adiciona as Actividades às empresas de teste.
       Actividade a1 = new Actividade("1","Saúde",true,0.15);
       Actividade a2 = new Actividade("2","Educação",true,0.15);
       actEmpresa1.put(a1.getId(), a1.clone());
       actEmpresa1.put(a2.getId(), a2.clone());
       
       Actividade act1 = new Actividade("8","Actividades de Salões de cabeleireiro e institutos de beleza",true,0.2);       
       actEmpresa2.put(act1.getId(), act1.clone());

       Concelho conc1 = new Concelho("Belmonte", true);
       Concelho conc2 = new Concelho("Famalicão", false);
       
       Actor testeIndividual1 = new Individual("111111111", "individual1@gmail.com", "João", "123", 
                                              "Rua José Joaquim Ribeiro", false, 3, nifAgregado, 0.2, actIndividual,5);
       Actor testeIndividual2 = new Individual("222222222", "individual2@gmail.com", "Helena", "123", 
                                              "Rua José Joaquim Ribeiro", false, 3, nifAgregado, 0.2, actIndividual,2);
       Actor testeIndividual3 = new Individual("333333333", "individual3@gmail.com", "José", "123", 
                                              "Rua José Joaquim Ribeiro", false, 3, nifAgregado, 0.2, actIndividual,6);                                       
                                                                                  
       Actor testeEmpresa1 = new Empresa("444444444", "empresal@gmail.com", "Molaflex", "123", 
                                        "Avenida Qualquer-Coisa", true, 0.3, actEmpresa1, fact, conc1);
       Actor testeEmpresa2 = new Empresa("555555555", "empresa2@gmail.com", "Cabeleireiro Rosinha", "123", 
                                        "Avenida Qualquer-Coisa", true, 0.3, actEmpresa2, fact, conc2);

       nifAgregado.add(testeIndividual1.getNif());
       nifAgregado.add(testeIndividual2.getNif());
       nifAgregado.add(testeIndividual3.getNif());
       
       try{
           jf.registaActor(testeIndividual1);
           jf.registaActor(testeIndividual2);
           jf.registaActor(testeIndividual3);
           jf.registaActor(testeEmpresa1);
           jf.registaActor(testeEmpresa2);
       }
       catch(ActorExistenteException e){
           System.out.println("Actor já existe!");
       }
       try{
           jf.iniciaSessao(testeEmpresa1.getNif(), testeEmpresa1.getPassword());
       }
       catch(SemAutorizacaoException e){
           System.out.println("Inicio de Sessão: " + e.getMessage());
       }
       
       Date d1 = new Date();
       try{
           SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
           String date1 = "15/09/2017";
           d1 = sdf.parse(date1);
       }
       catch(ParseException p){
           System.out.println(p.getMessage());
       }
       
       Factura f1 = new Factura("1", testeEmpresa1.getNif(), testeEmpresa1.getNome(), d1, "222222222", "Compra de Colchões", 
                               new Actividade(), prods, 58, "Pendente");                      
       
                               
       Date d2 = new Date();
       try{
           SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
           String date1 = "21/05/2017";
           d2 = sdf.parse(date1);
       }
       catch(ParseException p){
           System.out.println(p.getMessage());
       }       
       
       Factura f2 = new Factura("2", testeEmpresa1.getNif(), testeEmpresa1.getNome(), d2, "111111111", "Compra de Almofadas",
                              new Actividade(), prods, 100, "Pendente");
                          
                              
       Date d3 = new Date();
       try{
           SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
           String date1 = "11/10/2017";
           d3 = sdf.parse(date1);
       }
       catch(ParseException p){
           System.out.println(p.getMessage());
       }       
       Factura f3 = new Factura("3", testeEmpresa1.getNif(), testeEmpresa1.getNome(), d3, "222222222", "Compra de Estrados",
                                    new Actividade(), prods, 110, "Pendente");  
                               
       try{
           jf.criaFactura(f1);
           jf.criaFactura(f2);
           jf.criaFactura(f3);
       }
       catch(FacturaExistenteException | SemAutorizacaoException p){
           System.out.println("Criação de Factura: " + p.getMessage());
       }

       jf.fechaSessao();
       
       try{
           jf.iniciaSessao(testeEmpresa2.getNif(), testeEmpresa2.getPassword());
       }
       catch(SemAutorizacaoException e){
           System.out.println("Inicio de Sessão: " + e.getMessage());
       }
       
       Date d4 = new Date();
       try{
           SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
           String date4 = "03/01/2018";
           d4 = sdf.parse(date4);
       }
       catch(ParseException p){
           System.out.println(p.getMessage());
       }
       
       Factura f4 = new Factura("4", testeEmpresa2.getNif(), testeEmpresa2.getNome(), d4, "333333333", "Corte de Cabelo", 
                               act1, prods, 20, "Confirmada");                      
       
                               
       Date d5 = new Date();
       try{
           SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
           String date5 = "22/02/2018";
           d5 = sdf.parse(date5);
       }
       catch(ParseException p){
           System.out.println(p.getMessage());
       }       
       
       Factura f5 = new Factura("5", testeEmpresa2.getNif(), testeEmpresa2.getNome(), d5, "111111111", "Corte de Cabelo",
                              act1, prods, 20, "Confirmada");
                          
                              
       Date d6 = new Date();
       try{
           SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
           String date6 = "27/03/2018";
           d6 = sdf.parse(date6);
       }
       catch(ParseException p){
           System.out.println(p.getMessage());
       }       
       Factura f6 = new Factura("6", testeEmpresa2.getNif(), testeEmpresa2.getNome(), d6, "333333333", "Pintura de Unhas",
                                    act1, prods, 9, "Confirmada");  
                               
       try{
           jf.criaFactura(f4);
           jf.criaFactura(f5);
           jf.criaFactura(f6);
       }
       catch(FacturaExistenteException | SemAutorizacaoException p){
           System.out.println("Criação de Factura: " + p.getMessage());
       }
       jf.fechaSessao();
       return jf;       
   }
  
  
   /**
    * Regista um novo actor
    * @param actor o actor a registar
    * @throws ActorExistenteException
    */
   public void registaActor(Actor actor) throws ActorExistenteException {
        if(this.actores.containsKey(actor.getNif()))
            throw new ActorExistenteException("O actor já existe!");
        else {
            this.actores.put(actor.getNif(), actor.clone());
        }
   }
    
    /**
     * Inicia a sessão
     * @param nif Nif do Actor
     * @param password Password do actor
     * @throws SemAutorizacaoException
     */
   public void iniciaSessao(String nif, String password) throws SemAutorizacaoException{
        for(Actor a: this.actores.values()){
            if(a.getNif().equals(nif)){
                if(a.getPassword().equals(password)){
                    if(a.getTipoActor()){
                        this.loginEmpresa = true;
                    }
                    else {
                        this.loginIndividual = true;
                    }    
                    this.codigoActor = a.getNif();    
                }
            }
        }
        Admin admin = new Admin();
        if(this.loginIndividual == false && this.loginEmpresa == false)
            if(admin.getId().equals(nif)){
                if(admin.getPass().equals(password)){
                    this.loginAdmin = true;
                }
                this.codigoActor = admin.getId();
            }
            else{
                throw new SemAutorizacaoException("Esse utilizador não existe!");
            }
   }
   
   /**
     * Termina sessão
     */
   public void fechaSessao() {
        if(this.loginIndividual){
            this.loginIndividual = false;
        }
        else if(this.loginEmpresa){
            this.loginEmpresa = false;
        }
        else{
            this.loginAdmin = false;
        }
    
        this.codigoActor = "00000000";
   }
   
   /**
     * Cria uma factura
     * @param f Factura a criar
     * @throws FacturaExistenteException
     * @throws SemAutorizacaoException
     */
   public void criaFactura(Factura f)throws FacturaExistenteException, SemAutorizacaoException{
       if(!loginEmpresa)
           throw new SemAutorizacaoException("Não tem autorização para criar Facturas!");
       
       else{
          if(this.getFacturas().containsKey(f.getId())){
               throw new FacturaExistenteException("Essa factura já existe!");
          }
          else {
              Actor a = this.actores.get(codigoActor);
              Empresa e = (Empresa) a;
              Factura fact = f.clone();
              e.makeFactura(fact);
              this.facturas.put(fact.getId(), fact);
          }
       }
   }
   
   /** 
    * Algoritmo de determinação do montante fiscal a deduzir
    * @return montanteDeduz montante a deduzir
    */
   public double montanteDeducao(){
       double montanteDeduz = 0.0;
       Actor a = this.actores.get(codigoActor);
       Reducao r = (Reducao) a;
       if(a instanceof Individual || a instanceof Empresa)
            if(a instanceof Individual){
                for(Factura f: this.facturas.values()){
                    if(f.getNifCliente().equals(a.getNif())){
                        for(Actividade act: this.todasActiv.values()){
                            if(f.getActividade().equals(act)){
                                montanteDeduz += (f.getValor())*(act.getDeducaoFiscal())*r.reducaoImposto();
                            }
                        }
                    }
                }
            }
       return montanteDeduz;
   }
   
   //Métodos para os  Requisitos correspondentes aos Contribuintes Individuais
  /* for(Factura f: this.facturas.values()){
               if(f.getNifCliente().equals(i.getNif())){
                   for(Actividade a: this.todasActiv.values()){
                       if(f.getActividade().equals(a)){
                           montanteDeduz = f.getValor()*a.getDeducaoFiscal();
                        }
                    }
                }
            }*/
   /**
     * Mostra todas as Facturas referentes ao Contribuinte Individual com sessao Iniciada
     */
   public void mostraFacturas(){
       for(Factura f: this.facturas.values()){
            if(this.codigoActor.equals(f.getNifCliente())){
                            System.out.println(f.toString());
            }
       }
   }
   
   /**
     * Confirma as Facturas dos Contribuintes Individuais que ainda se encontram num estado Pendente
     */
   public void confirmaFact(){
       Scanner input = new Scanner(System.in);
       Map<String, Actividade> acts = new HashMap<String, Actividade>();
       JavaFactura jf = new JavaFactura();
       acts = jf.getTodasActiv();
       for(Factura f: this.facturas.values()){
            if(this.codigoActor.equals(f.getNifCliente())){
               if(f.getEstado().equals("Pendente")){
                   for(Actor a: this.actores.values()){
                       if(f.getNifEmitente().equals(a.getNif())){
                           Empresa e = (Empresa) a;
                           Map<String, Actividade> actEmpresa = e.getActividades();
                           System.out.println(f.toString());
                           System.out.println("Actividades Económicas da Empresa que emitiu a Factura: ");
                           for(Actividade act: actEmpresa.values()){
                               System.out.println("Código da Actividade: " + act.getId());
                               System.out.println("Nome da Actividade: " + act.getDesignacao());
                           }
                           System.out.println("Pretende confirmar esta factura? Digite 's' para sim, 'n' para não.");
                           String opcao = input.nextLine();
                           while(opcao.charAt(0) != 's' && opcao.charAt(0) != 'n'){
                               System.out.println("Erro! Digite conforme o que é indicado!");
                           }
                           if(opcao.charAt(0) == 's'){
                               System.out.println("Confirmação da Actividade da Factura com código: " + f.getId());
                               System.out.println("Por favor insira o código da Actividade referente à Factura: ");
                               String codAct = input.nextLine();
                               for(Actividade activ: acts.values()){
                                   if(activ.getId().equals(codAct)){
                                       f.setActividade(activ.clone());
                                       f.setEstado("Confirmada");
                                       Date data = new Date();
                                       Registo regi = new Registo(data, f.getNifCliente(), f.getId(), activ.getDesignacao());
                                       this.registos.add(regi);
                                   }
                               }
                           }
                       }
                   }
               }
            }
       }
   }
   
   /**
    * Calcula o total de despesas efetuadas pelo contribuinte com sessao iniciada
    * @return total total de despesas efetuadas no sistema
    */
   public double totalValorFact(){
       double total = 0;
       for(Factura f: this.facturas.values()){
           if(this.codigoActor.equals(f.getNifCliente())){
               total += f.getValor();
           }
       }
       System.out.println("O total de despesas efectuadas até agora é de: " + total + "\n");
       return total;
   }
   
   /**
     * Obter o mapeamento de Códigos de Actividades para valores de despesas efetuados
     * @return Mapeamento de codigo de actividade para valor de despesas efetuadas nessa actividade
     */
   public Map<String, Double> getValueForActiv() {
    return this.todasActiv.values()
               .stream()
               .collect(Collectors.toMap(Actividade::getDesignacao, this::sumValores));
   }
   
   /**
    * Calcula o total de despesas efetuadas pelo contribuinte com sessao iniciada
    * @param actividade actividade que se pretende saber o valor de despesas efetuadas
    * @return valor de despesas efetuadas na actividade
    */
   public double sumValores(Actividade actividade) {
    return this.facturas.values()
               .stream()
               .filter(e -> this.codigoActor.equals(e.getNifCliente()))
               .filter(e -> e.getActividade().equals(actividade))
               .mapToDouble(Factura::getValor)
               .sum();
   }
   
   
   //Métodos para os  Requisitos correspondentes ás Empresas
   
   /**
     * Obter as facturas da Empresa com sessão iniciada, ordenadas por data
     * @return TreeSet com as facturas da Empresa, ordenadas por data
     */
   public TreeSet<Factura> getFacturasEmpresaData(){
       TreeSet<Factura> t = new TreeSet<Factura>(new ComparadorData());
       Empresa e = (Empresa) this.actores.get(codigoActor);
       for(Factura f: e.getFacturas().values()){
           t.add(f);
       }
       for(Factura f: t){
           System.out.println(f.toString());
       }
       return t;
   }
   
   /**
     * Obter as facturas da Empresa com sessão iniciada, ordenadas por valor
     * @return TreeSet com as facturas da Empresa, ordenadas por valor
     */
   public TreeSet<Factura> getFacturasEmpresaValor(){
       TreeSet<Factura> t = new TreeSet<Factura>(new ComparadorValor(1));
       Empresa e = (Empresa) this.actores.get(codigoActor);
       for(Factura f: e.getFacturas().values()){
           t.add(f.clone());
       }
       for(Factura f: t){
           System.out.println(f.toString());
       }
       return t;
   }
   
   /**
     * Obter as facturas da Empresa com sessão iniciada, por contribuinte, entre um intervalo de datas
     * @return TreeSet com as facturas da Empresa, por contribuinte, entre um intervalo de datas
     */
   public TreeSet<Factura> getListFactIndivDatas(Date d1, Date d2) throws ParseException{
       TreeSet<Factura> t = new TreeSet<Factura>(new ComparadorData());
       Empresa e = (Empresa) this.actores.get(codigoActor);
       for(Actor a: this.actores.values()){
           if(!a.getTipoActor()){
               for(Factura f: e.getFacturas().values()){
                 if(a.getNif().equals(f.getNifCliente())){
                     if((f.getData().after(d1) || f.getData().equals(d1)) && 
                        (f.getData().before(d2) || f.getData().equals(d2))){
                         t.add(f.clone());
                         System.out.println(f.toString());
                        }
                 }
               }
           }
       }
       return t;
   }
   
   /**
     * Obter as facturas da Empresa com sessão iniciada, por contribuinte, ordenadas por ordem decrescente de valor
     * @return TreeSet com as facturas da Empresa, por contribuinte, entre um intervalo de datas
     */
   public List<Factura> getListFactIndivValor() {
    Empresa e = (Empresa) this.actores.get(this.codigoActor);
    List<Factura> facturas = new ArrayList<Factura>();
    for(Factura f: e.getFacturas().values()){
        facturas.add(f.clone());
    }
    Collections.sort(facturas, Comparator.comparing(Factura::getNifCliente)
                                     .thenComparing(Factura::getValor));
    return facturas;
   }
    
   /**
     * Obter o total facturado entre duas datas
     * @param d1 primeiro data 
     * @param d2 segunda data
     * @return total total facturado entre as duas datas
     */
   public double getValorTotal(Date d1, Date d2){
       double total = 0;
       Empresa e = (Empresa) this.actores.get(codigoActor);
       for(Factura f: e.getFacturas().values()){
           if(f.getData().after(d1) && f.getData().before(d2)){
               total += f.getValor();
           }
       }
       return total;
   }
   
   
   /**
     * Regista um novo actor
     * @param jf JavaFactura
     * @return a mesma JavaFactura que recebeu mas com o novo actor
     */
    public static JavaFactura registaNovoActor(JavaFactura jf) throws ActorExistenteException{
        Scanner input = new Scanner(System.in);
        String codigo = null;
        ArrayList<String> nifAgregado = new ArrayList<String>();
        ArrayList<String> activDeduzem = new ArrayList<String>();
        Map<String, Actividade> actEmpresa = new HashMap<String, Actividade>();
        Map<String, Actividade> todasActiv = new HashMap<String, Actividade>();
        
        todasActiv = jf.getTodasActiv();
        
        for(Actividade act: jf.getTodasActiv().values()){
            activDeduzem.add(act.getId());
        }
        
        do{
            System.out.println("Insira o seu nif: ");
            codigo = input.nextLine();
            if(jf.getActores().containsKey(codigo)){
                throw new ActorExistenteException("Nif já existente");
            }
            else if(codigo.length() != 9){
                System.out.println("Insira um nif válido");
            }
        } while(jf.getActores().containsKey(codigo) || codigo.length() != 9);
        System.out.println("Insira o email:");
        String email = input.nextLine();
        System.out.println("Insira o nome:");
        String nome = input.nextLine();
        System.out.println("Insira a password:");
        String pass = input.nextLine();
        System.out.println("Insira a morada:");
        String morada = input.nextLine();
        System.out.println("Pretende registar-se como Contribuinte Individual ou Empresarial? Digite 'i' ou 'e'.");
        Actor actor = new Actor();
       
        if(input.nextLine().charAt(0) == 'i'){
                    System.out.println("Insira o número de filhos que tem");
                    int filhos = input.nextInt();
                    actor = (Actor) new Individual(codigo, email, nome, pass, morada, false,
                                       0, new ArrayList<String>(), 0.0, activDeduzem, filhos);
        }
        else {
                    System.out.println("De seguida é efetuado o registo das Actividades em que a empresa participa \n");
                    System.out.println("Opções disponíveis: \n");
                    System.out.println(todasActiv.toString());
                    System.out.println("");
                    System.out.println("Em quantas Actividades Económicas está a empresa envolvida: ");
                    int count = input.nextInt();
                    for(int i = 0; i<count; i++){
                            System.out.println("Insira o código da Actividade: ");
                            String cod = input.nextLine();
                            input.nextLine();
                            System.out.println("Insira a designacao da Actividade: ");
                            String desi = input.nextLine();
                            boolean perm = true;
                            System.out.println("Insira o factor de dedução da Actividade: ");
                            double factorDed = input.nextDouble();
                            
                            Actividade a = new Actividade(cod, desi, perm, factorDed);
                            actEmpresa.put(cod,a.clone());
                    }
                        
                    System.out.println("Qual o nome do concelho em que esta Empresa está situada?");
                    String nomeConc = input.nextLine();
                    input.nextLine();
                    System.out.println("Este concelho encontra-se numa região do interior? Digite 's' em caso afirmativo, 'n' em caso contrário!");
                    boolean interior = false;
                    if(input.nextLine().charAt(0) == 's'){
                        interior = true;
                    }
                    
                    Concelho conc = new Concelho(nomeConc, interior);
                    actor = (Actor) new Empresa(codigo, email, nome, pass, morada, true,  
                                            0, actEmpresa, new HashMap<String, Factura>(), conc);
                    
        }


        try {
            jf.registaActor(actor);
            System.out.println("Novo Utilizador Registado");
        }
        catch(ActorExistenteException e) {
            System.out.println(e.getMessage());
        }
        return jf;
    }
   
    /**
     * Regista uma nova Factura
     * @param jf JavaFactura
     * @return a mesma JavaFactura que recebeu mas com a nova factura
     */
   public static JavaFactura registaFactura(JavaFactura jf)throws ParseException, FacturaExistenteException, SemAutorizacaoException{
       Scanner input = new Scanner(System.in);
       Map<String, Actor> actores = new HashMap<String, Actor>();
       ArrayList<Produto> prods = new ArrayList<Produto>();
       Map<String, Actividade> acts = new HashMap<String, Actividade>();             
       
       
       Empresa e = new Empresa();
       String codigoActor = jf.getCodigoActor();
       actores = jf.getActores();
       
       for(Actor a: actores.values()){
           if(a.getNif().equals(codigoActor)){
               e = (Empresa) a;
           }
       } 
              
       String codigo = null;
       do {
        System.out.println("Insira o código da Factura: ");
        codigo = input.nextLine();
            if(jf.getFacturas().containsKey(codigo)){
                System.out.println("Código de Factura já existente");
            }
       }while (jf.getFacturas().containsKey(codigo));
       
       String nifEmitente = e.getNif();

       String design = e.getNome();
       
       System.out.println("Insira a Data de Emissão da factura. Use o formato dd/MM/yyy. ");
       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
       String date = input.nextLine();
       Date dt = sdf.parse(date);
       
       String nifCliente;
       do{
            System.out.println("Insira o NIF do Cliente: ");
            nifCliente = input.nextLine();
                if(nifCliente.length() != 9){
                    System.out.println("NIF Inválido! Tente novamente.\n");
                }
       }while(nifCliente.length() != 9);
       
       System.out.println("Insira uma descrição da Despesa: ");
       String despesa = input.nextLine();
       
       acts = e.getActividades();
       Actividade a;
       if(acts.size() > 1){
           a = new Actividade();
       }  
       else{
            a = acts.get(0);
       } 

       
       Produto p = null;
       System.out.println("Quantos Produtos fazem parte desta Factura?");
       int numProd = input.nextInt();
       double precoTotal = 0;
       for(int i = 0; i< numProd; i++){
            if(i == 0 && numProd == 1){
               System.out.println("Digite o nome do Produto");
               String nome = input.nextLine();
               input.nextLine();
               System.out.println("Digite o preço do Produto");
               double preco = input.nextDouble();
               precoTotal += preco;
               
               p = new Produto(nome, preco);
               prods.add(p);               
           }
           else {
               if(i==0 && numProd !=1){
               System.out.println("Digite o nome do Produto");
               String nome = input.nextLine();
               input.nextLine();
               System.out.println("Digite o preço do Produto");
               double preco = input.nextDouble();
               precoTotal += preco;
               
               p = new Produto(nome, preco);
               prods.add(p);  
               }
               else {
               System.out.println("Digite o nome do Produto");
               String nome = input.nextLine();
               input.nextLine();
               System.out.println("Digite o preço do Produto");
               double preco = input.nextDouble();
               precoTotal += preco;
               
               p = new Produto(nome, preco);
               prods.add(p);  
               }
           }
       }
       
       double valor = precoTotal;
       
       String estado;
       int count = acts.size();
       if(count > 1){
           estado = "Pendente";
       }
       else {
            estado = "Confirmada";
            }
       Factura f = new Factura(codigo, nifEmitente, design, dt, nifCliente, despesa, a, prods, valor, estado);
       try {
           jf.criaFactura(f);
           System.out.println("Factura criada com Sucesso! \n");
       }
       catch(FacturaExistenteException | SemAutorizacaoException t){
           System.out.println(t.getMessage());
       }
       
       return jf;
   }
   
   /**
     * Funçao main
     * Função que trata de tornar o programa mais "amigo" do utilizador, fornecendo uma interface textual
     */
   public static void main(){
       Menu menu = new Menu();
       JavaFactura jf = new JavaFactura();
       Scanner input = new Scanner(System.in);
       do {
           menu.menuPrincipal();
           switch(menu.getOpcao()){
               case 1: menu.setOpcao(-1);
                       jf = jf.testApp();
                       menu.menuTestApp();
                       break;
               case 2: System.out.println("Insira o nome do ficheiro:");
                       try{
                           jf = carregarDados(input.nextLine());
                           menu.setOpcao(-1);
                       }
                       catch(IOException e) {
                            System.out.println("\nNão consegui ler os dados!\nErro de leitura!");
                        }
                        catch(ClassNotFoundException e) {
                            System.out.println("Não consegui ler os dados!\nFicheiro com formato desconhecido!");
                        }
           }        
       }while (menu.getOpcao() != 0 && menu.getOpcao() != -1);
       
       do{
          if(menu.getOpcao() !=0){
            if(jf.getLoginEmpresa()){
              menu.menuEmpresa();
              switch(menu.getOpcao()){
                  case 1: try{
                            jf = jf.registaFactura(jf); break;
                          }
                          catch(ParseException | FacturaExistenteException | SemAutorizacaoException e){
                            System.out.println(e.getMessage());
                          }
                          break;
                          
                  case 2: System.out.println("Pretende visualizar as Facturas ordenadas por Data ou Valor?");
                          System.out.println("Digite Data ou Valor consoante a sua opção!");
                          String s;
                          do{
                              s = input.nextLine();
                              if(s.equalsIgnoreCase("Data")){
                                  jf.getFacturasEmpresaData();
                              }
                              else if(s.equalsIgnoreCase("Valor")){ 
                                  jf.getFacturasEmpresaValor();
                              }
                              else{
                                  System.out.println("Erro! Digite conforme o que lhe é indicado!");
                              }
                          }while(!s.equalsIgnoreCase("Data") && !s.equalsIgnoreCase("Valor"));
                          break;
                          
                  case 3: try{
                             Date d1 = new Date();
                             System.out.println("Insira a primeira data. Use o formato dd/MM/yyy.");
                             SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                             String date1 = input.nextLine();
                             d1 = sdf.parse(date1);
                             
                             Date d2 = new Date();
                             System.out.println("Insira a segunda data. Use o formato dd/MM/yyy.");
                             sdf = new SimpleDateFormat("dd/MM/yyyy");
                             String date2 = input.nextLine();
                             d2 = sdf.parse(date2);
                             System.out.println("");
                             
                             jf.getListFactIndivDatas(d1, d2);
                          }
                          catch(ParseException e){
                              System.out.println(e.getMessage());
                          }
                          break;
                          
                  case 4: System.out.println(jf.getListFactIndivValor().toString());;break;
                  
                  case 5: try{
                             Date d1 = new Date();
                             System.out.println("Insira a primeira data. Use o formato dd/MM/yyy.");
                             SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                             String date1 = input.nextLine();
                             d1 = sdf.parse(date1);
                             
                             Date d2 = new Date();
                             System.out.println("Insira a segunda data. Use o formato dd/MM/yyy.");
                             sdf = new SimpleDateFormat("dd/MM/yyyy");
                             String date2 = input.nextLine();
                             d2 = sdf.parse(date2);
                             System.out.println("");
                             
                             System.out.println("O total facturado entre " + d1 + " e " + d2 + "foi de: " + jf.getValorTotal(d1, d2) + "\n");
                          }
                          catch(ParseException e){
                              System.out.println(e.getMessage());
                          }
                          break;
                  case 6: System.out.println("Insira o nome do ficheiro a gravar:"); 
                          try{
                              jf.guardaDados(input.nextLine());
                              jf.log("log.txt", true);
                          }
                          catch(IOException e) {
                              System.out.println(e.getMessage() + "\nNão consegui gravar os dados!");
                          }
                                break;        
                  case 7: jf.fechaSessao(); break;
              }
            }
            else if(jf.getLoginIndividual()){
                menu.menuIndividual();
                switch(menu.getOpcao()){
                  case 1: jf.mostraFacturas(); break;
                  case 2: jf.totalValorFact(); break;
                  case 3: System.out.println(jf.montanteDeducao());break;
                  case 4: jf.confirmaFact(); break;
                  case 5: System.out.println(jf.getValueForActiv().toString()); break;
                  case 6: System.out.println("Insira o nome do ficheiro a gravar:"); 
                          try{
                              jf.guardaDados(input.nextLine());
                              jf.log("log.txt", true);
                          }
                          catch(IOException e) {
                              System.out.println(e.getMessage() + "\nNão consegui gravar os dados!");
                          }
                          break;
                  case 7: jf.fechaSessao(); break;
                }
            }
            else if(jf.getLoginAdmin()){
                menu.menuAdmin();
                switch(menu.getOpcao()){
                  case 1: System.out.println(jf.getRegistos().toString()); break;
                  case 2: break;
                  case 3: jf.fechaSessao(); break; 
                }
            }
            
            else{
                menu.menuNormal();
                switch(menu.getOpcao()){
                 case 1: try{
                            jf = registaNovoActor(jf);
                         }
                         catch(ActorExistenteException e){
                             System.out.println(e.getMessage());
                         }
                         break;
                 case 2: System.out.println("Introduza o seu NIF");
                         String nif = input.nextLine();
                         System.out.println("Introduza a sua password");
                         String password = input.nextLine();
                         try{
                             jf.iniciaSessao(nif, password);
                         }
                         catch(SemAutorizacaoException e) {
                             System.out.println(e.getMessage());                            
                         }
                         break;  
                 case 3: case 6: System.out.println("Insira o nome do ficheiro a gravar:"); 
                         try{
                             jf.guardaDados(input.nextLine());
                             jf.log("log.txt", true);
                         }
                          catch(IOException e) {
                             System.out.println(e.getMessage() + "\nNão consegui gravar os dados!");
                         }
                         break;
              }    
            }
         }
       }while (menu.getOpcao() !=0);
   }
   
   
   /**
   * Função que guarda todos os dados da nossa aplicação
   * @param fich nome do ficheiro a criar
   */
   public void guardaDados(String fich) throws IOException {
        ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(fich));
        obj.writeObject(this);
        obj.flush();
        obj.close();
   }
    
   /**
   * Função responsável por carregar um ficheiro com os dados
   * @return javafactura que estava guardada no ficheiro 
   */
   public static JavaFactura carregarDados(String fich) throws IOException, ClassNotFoundException {
        ObjectInputStream obj = new ObjectInputStream(new FileInputStream(fich));
        JavaFactura jf = (JavaFactura) obj.readObject();
        obj.close();
        return jf;
    }
    
    /**
     * Função que escreve num ficheiro tudo o que foi gravado na função guarda dados
     * @param f nome do ficheiro de log
     * @param ap
     */
    public void log(String f, boolean ap) throws IOException {
        FileWriter fw = new FileWriter(f, ap);
        fw.write("\n---------------- LOG - LOG - LOG - LOG - LOG ------------------\n");
        fw.write(this.toString());
        fw.write("\n---------------- LOG - LOG - LOG - LOG - LOG ------------------\n");
        fw.flush();
        fw.close();
    }
    
    /**
     * Obter a representação textual de uma imobiliaria
     * @return representação textual
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(this.loginIndividual){
            sb.append("Individual com sessão iniciada: ").append(this.codigoActor).append("\n");
        }
        if(this.loginEmpresa){
            sb.append("Empresa com sessão iniciada: ").append(this.codigoActor).append("\n");
        }
        if(this.loginAdmin){
            sb.append("Admin com sessão iniciada: ").append(this.codigoActor).append("\n");
        }
        sb.append("Lista de actores: ").append("\n");
        for(Registo r: this.registos){
            sb.append(r.toString());
        }
        sb.append("Lista de actores: ").append("\n");
        for(Actor a: this.actores.values()){
            sb.append(a.toString());
        }
        sb.append("Lista das facturas: ").append("\n");
        for(Factura f: this.facturas.values()){
            sb.append(f.toString());
        }
        sb.append("Lista das Actividades: ").append("\n");
        for(Actividade a: this.todasActiv.values()){
            sb.append(a.toString());
        }
        return sb.toString();
    }
}
