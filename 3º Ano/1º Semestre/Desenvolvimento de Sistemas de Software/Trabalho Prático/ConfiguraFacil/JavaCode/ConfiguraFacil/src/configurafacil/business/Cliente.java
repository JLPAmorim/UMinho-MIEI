/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configurafacil.business;


public class Cliente {
    
    /* Nome do Cliente */
    private String nome;
    /* Nif do Cliente */
    private String nif;
    /* Contacto do Cliente */
    private String contacto;
    
    //Construtores

    /**
     * Construtor vazio.
     */
    public Cliente(){
        this.nome = null;
        this.nif = null;
        this.contacto = null;
    }
    
    /**
     * Construtor através de um objeto Cliente.
     * @param c Cliente
     */
    public Cliente(Cliente c){
        this.nome = c.getNome();
        this.nif = c.getNif();
        this.contacto = c.getContacto();
    }
    
    /**
     * Construtor por parâmetros.
     * @param nome nome do cliente
     * @param nif nif do cliente
     * @param contacto contacto do cliente
     */
    public Cliente(String nome, String nif, String contacto) {
        this.nome = nome;
        this.nif = nif;
        this.contacto = contacto;
    }
   
     // Getters e Setters
    
    /**
     * Devolve o nome do cliente
     * 
     * @return nome do cliente
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Faz o set do nome do cliente.
     * 
     * @param nome Novo nome do cliente 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    /**
     * Devolve o nif do cliente.
     * 
     * @return nif do cliente
     */
    public String getNif() {
        return nif;
    }
    
    /**
     * Faz o set no saldo da conta.
     * 
     * @param nif Novo nif do cliente 
     */
    public void setNif(String nif) {
        this.nif = nif;
    }
    
    /**
     * Devolve o contacto do cliente.
     * 
     * @return contacto do cliente
     */
    public String getContacto() {
        return contacto;
    }
    
    /**
     * Faz o set do contacto do cliente
     * 
     * @param contacto Novo contacto do cliente 
     */
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    
    
    /**
     * Cria um cópia de cliente e devolve-a
     * 
     * @return Clone do cliente.
     */
    public Cliente clone(){
        return new Cliente(this); 
    }
    
    
}
