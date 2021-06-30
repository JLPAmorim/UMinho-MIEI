/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configurafacil.business;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ConfiguraFacilFacade {
    
    /* Instância de ConfiguraFacil */
    private ConfiguraFacil cf;

    /**
     * Construtor vazio.
     */
    public ConfiguraFacilFacade(){
            this.cf = new ConfiguraFacil();
    }
    
    //Utilizadores
    
    public boolean isAdmin(String username, String password){
        boolean ret = false;
        
        try{
            ret = cf.isAdmin(username, password);
        }catch(SQLException e){
            ret = false;
        }
        return ret;
    }
    
    public boolean isFuncionario(String username, String password){
        boolean ret = false;
        
        try{
            ret = cf.isFuncionario(username, password);
        }catch(SQLException e){
            ret = false;
        }
        return ret;
    }
    
    public boolean funcionarioExistente(String username){
        boolean ret = false;
        
        try{
            ret = cf.funcionarioExistente(username);
        }catch(SQLException e){
            ret = false;
        }
        return ret;
    }
    
    public String registarFuncionario(String username, String password) {
        String ret;
        
        try {
            cf.registarFuncionario(username, password);
            ret = "Funcionário removido.";
        } catch (SQLException ex) {
            ret = "Não foi possível ligar à Base de Dados.";
        }
        
        return ret;
    }
    
    public void removeFuncionario(String username){
        
        try{
            cf.removeFuncionario(username);
        }catch(SQLException e){
        }
    }
    
    
    /**
     * Verifica se nenhum dos campos recebidos está vazio.
     * É utilizado para verificar se todos os campos foram preenchidos na
     * janela da interface de login
     * 
     * @param username  Campo correspondente ao username
     * @param password  Campo correspondente à password
     * @return          true caso exista pelo menos um campo vazio
     *                  false caso contrário
     */
    public boolean fieldSize(String username, String password) {
        return (username.length() == 0 || password.length() == 0);
    }
    
    /**
     * Verifica se nenhum dos campos recebidos está vazio.
     * É utilizado para verificar se todos os campos foram preenchidos na
     * janela da interface de login
     * 
     * @param nome  Campo correspondente ao username
     * @param nif Campo correspondente à password
     * @param contacto  Campo correspondente à password
     * @return          true caso exista pelo menos um campo vazio
     *                  false caso contrário
     */
    public boolean fieldSizeCliente(String nome, String nif, String contacto) {
        return (nome.length() == 0 || nif.length() == 0 || contacto.length() == 0);
    }
    
    //Configuracao
    public List<String> compToStringByType(String s){
        List<String> nomes = new ArrayList<>();
        
        nomes = cf.compToStringByType(s);
        
        return nomes;
    }
   
    public void criaCliente(String nome, String nif, String contacto){
        cf.criaCliente(nome, nif, contacto);
    }
    
    public void addListaConfiguracao(String s){
        cf.addListaConfiguracao(s);
    }
    
    public void addCompPacoteToList(Pacote p){
        cf.addCompPacoteToList(p);
    }
    
    public void addPacoteConfiguracao(Pacote p){
        cf.addPacoteConfiguracao(p);
    }
    
    public List<String> getAllListaConfig(){
        List<String> nomes = cf.getAllListaConfig();
        return nomes;
    }
    
    
    public Pacote getPacote(int i){
        Pacote p = new Pacote();
        try{
            p = cf.getPacote(i);
        }catch(SQLException e){
        }
        return p;
    }
    
    public boolean verificaIncompatibilidade(String s){
        boolean incompativel = false;
        incompativel = cf.verificaIncompatibilidade(s);
        return incompativel;
    }
    
    public boolean verificaPacote(Pacote p){
        boolean incompativel = false;
        incompativel = cf.verificaPacote(p);
        return incompativel;
    }
    
    public void removeListaConfig(String s){
        cf.removeListaConfig(s);
    }
    
    public void addConfiguracao(){
        cf.addConfiguracao();
    }
    
    public void resetCliente(){
        cf.resetCliente();
    }
    
    public void resetPacote(){
        cf.resetPacote();
    }
    
    public void resetComponente(String s){
        cf.resetComponente(s);
    }
    
    public boolean foiEscolhidoPacote(){
        boolean foi = cf.foiEscolhidoPacote();
        return foi;
    }
    
    public String updateStock(int codigo, int quantidade){
        String ret = null;
        try{
            cf.updateStock(codigo, quantidade);
            ret = "ReStock efectuado com sucesso";
        }catch(SQLException e){
            e.getMessage();
        }
        return ret;
    }
    
     public double precoTotal(){
         return cf.precoTotal();
     }
   

   
    
    
}
