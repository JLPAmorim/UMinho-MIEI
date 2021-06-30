/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configurafacil.business;

import configurafacil.data.AdministradorDAO;
import configurafacil.data.ClienteDAO;
import configurafacil.data.ComponenteDAO;
import configurafacil.data.Connect;
import configurafacil.data.EncomendaDAO;
import configurafacil.data.FuncionarioDAO;
import configurafacil.data.PacoteDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class ConfiguraFacil {
    
    private Connect connection;
    private Utilizador utilizador;
    private AdministradorDAO adminDAO;
    private ClienteDAO clienteDAO;
    private ComponenteDAO componenteDAO;
    private EncomendaDAO encomendaDAO;
    private FuncionarioDAO funcionarioDAO;
    private PacoteDAO pacoteDAO;
    private List<Componente> armazem;
    private Cliente cliente;
    private Pacote pacote;
    private Configuracao configuracao;
    private List<Componente> listaComponentes;
    

    
    public ConfiguraFacil(){
        this.utilizador = null;
        this.adminDAO = new AdministradorDAO(); 
        this.clienteDAO = new ClienteDAO();
        this.componenteDAO = new ComponenteDAO();
        this.encomendaDAO = new EncomendaDAO();
        this.funcionarioDAO = new FuncionarioDAO();
        this.pacoteDAO = new PacoteDAO();
        this.armazem = componenteDAO.carregarComponentes();
        this.cliente = new Cliente();
        this.pacote = new Pacote();
        this.configuracao = new Configuracao();
        this.listaComponentes = new ArrayList<>();
        
        
        /*this.pacote = pacoteDAO.getPacote(1);
        boolean incompativel = false;
        incompativel = verificaPacote(pacote);
        System.out.println(incompativel);*/
        /*String s1 = "320i XDrive";
        String s2 = "Branco Alpine";
        String s3 = "Suspensão Adaptativa";*/
        
        /*this.pacote = pacoteDAO.getPacote(1);
        Componente c1 = criaComponente(s1);
        List<Integer> incomp1 = new ArrayList<>();
        incomp1 = c1.getIncompativeis();
        
        Componente c2 = criaComponente(s2);
        List<Integer> incomp2 = new ArrayList<>();
        incomp2 = c2.getIncompativeis();
        Componente c3 = criaComponente(s3);
        List<Integer> incomp3 = new ArrayList<>();
        incomp3 = c3.getIncompativeis();
        this.listaComponentes.add(c1);*
        
        
        boolean incompativel = verificaIncompatibilidade(s3);
        System.out.println(incompativel);*/
        
        //boolean incompativel = verificaPacote(this.pacote);
        //System.out.println(incompativel);
        
        
        
             
        
        
        //System.out.println(pacote.toString());
       
        
        
        //Testa incompatibilidade. Para s2 == true enquanto que para s == false. 
        /*
        String s2 = "320i XDrive
        String s = "330d XDrive";
        String s1 = "Suspensão Desportiva M";
        Componente comp = criaComponente(s1);
        this.listaComponentes.add(comp);
        boolean incompativel = false;
        incompativel = verificaIncompatibilidade(s);
        System.out.println("Existe incompatibilidade? " + incompativel);*/
        
        
        
        
        //for(int i = 0; i < this.armazem.size(); i++)
          //  System.out.println(this.armazem.get(i));
        
    }
    
    //Utilizadores
   
    public boolean isAdmin(String username, String password)throws SQLException{
        return adminDAO.exists(username,password);
    }
    
    public boolean isFuncionario(String username, String password)throws SQLException{
        return funcionarioDAO.exists(username,password);
    }
    
    public boolean funcionarioExistente(String username)throws SQLException{
        return funcionarioDAO.jaExiste(username);
    }
    
    public void registarFuncionario(String username, String password) throws SQLException {
        funcionarioDAO.put(username, password);
    }
    
    public void removeFuncionario(String username)throws SQLException{
        funcionarioDAO.removeFuncionario(username);
    }  
    
    //Configuração
    
    public void criaCliente(String nome, String nif, String contacto){
        this.cliente = new Cliente(nome,nif,contacto);
    }
    
    public List<Componente> getArmazem(){
        return this.armazem;
    }
    
    public Pacote getPacote(int key)throws SQLException{
        this.pacote = pacoteDAO.getPacote(key);
        return pacote;
    }
    
    //Cria uma lista com os nomes de todos os Componentes
    public List<String> compToStringByType(String s){
        List<String> nomes = new ArrayList<>();
        
        for(Componente c: this.armazem){
            if(c.getTipo().equals(s)){
                nomes.add(c.toString());
                
            }
        }
        return nomes;
    }  
    
    //Vai buscar um componente dado o seu nome
    
    public Componente criaComponente(String s){
        Componente componente = new Componente();
        for(Componente c: this.armazem){
            if(c.getDesignacao().equals(s)){
                componente = c.clone();
            }
        }
        return componente;
    }
    
    //Adiciona o componente escolhido à lista de componentes da configuracao
    
    public void addListaConfiguracao(String s){
        Componente c = criaComponente(s);
        this.listaComponentes.add(c);
    }
    
    //Verifica  se o Componente escolhido é Incompatível com algum dos já selecionados, dado o nome do componente
    public boolean verificaIncompatibilidade(String s){
        boolean incompativel = false;
        Componente compCriado = criaComponente(s);
        List<Integer> incompativeisComp = compCriado.getIncompativeis();
        List<Integer> idComponentes = new ArrayList<>(); 
            
        for(Componente c: this.listaComponentes){
            idComponentes.add(c.getCodigo());
        }
        
        for(Integer intIdComponentes: idComponentes){
            for(Integer intIncompativeis: incompativeisComp)
                if(intIdComponentes == intIncompativeis){
                    incompativel = true;
                }else{
                    incompativel = false;
                }
        }
        
        for(Integer inteiro: incompativeisComp){
            for(Integer in: idComponentes){
                
                if(inteiro == in){
                    incompativel = true;
                }
            }
        }
        return incompativel;
    }
    
    public boolean verificaPacote(Pacote pacote){
        boolean incompatibilidade = false;
        for(Componente c: pacote.getComponentes()){
            String s = c.getDesignacao();
            //System.out.println(s);
            
            if(verificaIncompatibilidade(s)){
                incompatibilidade = true;
            }
        }
        return incompatibilidade;
    }
    
    public void addCompPacoteToList(Pacote p){
        for(Componente c: p.getComponentes()){
            this.listaComponentes.add(c);
        }
    }
    
    public void addPacoteConfiguracao(Pacote p){
        this.pacote = p;
    }
    
    public List<String> getAllListaConfig(){
        List<String> nomes = new ArrayList<>();
        for(Componente c: this.listaComponentes){
            nomes.add(c.getDesignacao());
        }
        return nomes;
    }
    
    public void removeListaConfig(String s){
        List<Componente> compTemporarios = new ArrayList<>();
        Componente c2 = new Componente();
        for(Componente c1:this.listaComponentes){
            if(!c1.getTipo().equals(s)){
                compTemporarios.add(c1);
            }
        }
        this.listaComponentes = compTemporarios;
    }
    
    public void criaConfiguracao(Cliente c, Pacote p, List<Componente> list){
        this.configuracao = new Configuracao(c, p, this.listaComponentes);
    }
    
    public void addConfiguracao(){
        encomendaDAO.addConfiguracao(this.configuracao);
    }
    
    public void resetCliente(){
        this.cliente = new Cliente();
    }
    
    public void resetPacote(){
        this.pacote = new Pacote();
    }
    
    public void resetComponente(String s){
        for(Componente c: this.listaComponentes){
            if(c.getTipo().equals(s)){
                removeListaConfig(c.getDesignacao());
            }
        }
    }
    
    public boolean foiEscolhidoPacote(){
        boolean foi = false;
        
        if(this.pacote == null)
            foi = true;
        return foi;
    }
    
    public void updateStock(int codigo, int quantidade)throws SQLException{
        componenteDAO.updateStock(codigo, quantidade);
    }
    
    public double precoTotal(){
        double total = 2;
        int i = 0;
        List<Componente> compPacote = this.pacote.getComponentes();
        List<Componente> compTemporarios = this.listaComponentes;

        for(Componente c1: compPacote){
            for(Componente c2: compTemporarios){
                if(c1.getDesignacao().equals(c2.getDesignacao())){
                    compTemporarios.remove(c2);
                }
            }
        }
        total = this.pacote.getPreco();
        for(Componente c3: compTemporarios){
            total += c3.getPreco();
        }
        return total;
    }    
}
