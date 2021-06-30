/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configurafacil.business;


public class Administrador extends Utilizador {
    
    //Construtores
    
    /**
     * Construtor por parâmetros.
     * 
     * @param username  Username do administrador
     * @param password  Password do administrador
     */
    public Administrador(String username, String password) {
        super(username, password);
    }
    
    /**
     * Construtor a partir do mesmo objeto.
     * 
     * @param admin Administrador
     */
    public Administrador(Administrador admin) {
        super(admin.getUsername(), admin.getPassword());
    }
    
    
    //Métodos de instância
    
    /**
     * Valida o username e a password de um administrador.
     * 
     * @return true se forem válidos
     *         false caso contrário
     */
    public boolean validaAdministrador() {
        return this.validaUsername() && this.validaPassword();
    }
    
    /**
     * Verifica se o username e a password de um utilizador recebido
     * são iguais aos do administrador.
     * 
     * @param u Utilizador a verificar
     * @return  true se for administrador
     *          false caso contrário
     */
    public boolean isAdministrador (Utilizador u) {
        return this.getUsername().equals(u.getUsername()) &&
               this.getPassword().equals(u.getPassword());
    }
}
