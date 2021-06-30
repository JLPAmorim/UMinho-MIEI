/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configurafacil.business;

import java.util.List;
import java.util.ArrayList;

public class Configuracao {
    
    /* Cliente da Configuracao */
    private Cliente cliente;
    /* Pacote na Configuracao */
    private Pacote pacote;
    /* Lista de componentes na Configuracao */
    private List<Componente> listaComponentes;
    
    //Construtores

    /**
     * Construtor vazio.
     */
    public Configuracao(){
        this.cliente = new Cliente();
        this.pacote = new Pacote();
        this.listaComponentes = new ArrayList<>();
    }
    
    /**
     * Construtor através de um objeto Configuracao.
     * @param c Configuracao
     */
    public Configuracao(Configuracao c){
        this.cliente = c.getCliente();
        this.pacote = c.getPacote();
        this.listaComponentes = c.getListaComponentes();
    }

    /**
     * Construtor por parâmetros.
     * @param cliente cliente da configuracao
     * @param pacote pacote da configuracao
     * @param listaComponentes lista de componentes da configuracao
     */
    public Configuracao(Cliente cliente, Pacote pacote, List<Componente> listaComponentes) {
        this.cliente = cliente;
        this.pacote = pacote;
        this.listaComponentes = listaComponentes;
    }

    /**
     * Devolve o cliente da configuracao
     * 
     * @return cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Faz o set do cliente.
     * 
     * @param cliente Novo cliente 
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Devolve o pacote na configuracao
     * 
     * @return pacote 
     */
    public Pacote getPacote() {
        return pacote;
    }

    /**
     * Faz o set do novo pacote.
     * 
     * @param nome Novo pacote
     */
    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }

    /**
     * Devolve a listade componentes
     * 
     * @return listaComponentes lista de componentes
     */
    public List<Componente> getListaComponentes() {
        return listaComponentes;
    }
    
    /**
     * Devolve o valor total dos componentes contidos na lista
     * 
     * @return sum valor total
     */
    public double total(){
        double valor;
        double sum = 0;
        for(Componente c: listaComponentes){
            valor = c.getPreco();
            sum += valor;
        }
        return sum;
    }
    
    /**
     * Adiciona um componente à lista de componente na configuracao
     * 
     * @param c Componente a adicionar
     */
    public void addComponente(Componente c){
        this.listaComponentes.add(c);
    }
    
    /**
     * Remove um componente da lista de componente na configuracao
     * 
     * @param c Componente a remover
     */
    public void removeComponente(Componente c){
        this.listaComponentes.remove(c);
    }
    
    /**
     * Cria um cópia de configuracao e devolve-a
     * 
     * @return Clone da configuracao.
     */
    public Configuracao clone(){
        return new Configuracao(this);
    }
}
