/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configurafacil.business;

/**
 *
 * @author João
 */
public class Funcionario extends Utilizador{

    //Construtores
    
    /**
     * Construtor por parâmetros.
     * 
     * @param username  Username do funcionario
     * @param password  Password do funcionario
     */
    public Funcionario(String username, String password) {
        super(username, password);
    }
    
    /**
     * Construtor a partir do mesmo objeto.
     * 
     * @param f funcionario
     */
    public Funcionario(Funcionario f) {
        super(f.getUsername(),f.getPassword());
    }
    
    //Métodos de instância
    
    /**
     * Valida o username e a password de um funcionario.
     * 
     * @return true se forem válidos
     *         false caso contrário
     */
    public boolean validaFuncionario(){
        return this.validaUsername() && this.validaPassword();
    }
    
    /**
     * Verifica se o username e a password de um utilizador recebido
     * são iguais aos do funcionario.
     * 
     * @param u Utilizador a verificar
     * @return  true se for funcionario
     *          false caso contrário
     */
    public boolean isFuncionario(Utilizador u){
        return this.getUsername().equals(u.getUsername()) &&
               this.getPassword().equals(u.getPassword());
    }
    
    
}
