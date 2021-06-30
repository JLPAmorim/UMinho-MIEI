/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configurafacil.business;

import java.util.List;
import java.util.ArrayList;

public class Pacote {
    
    /* Nome do pacote */
    private String nome;
    /* Preco do pacote */
    private double preco;
    /* Desconto do pacote */
    private double desconto;
    /* Lista de componentes que constitui o pacote */
    private List<Componente> componentes;
    
    //Construtores

    /**
     * Construtor vazio.
     */
    public Pacote(){
        this.nome = null;
        this.preco = 0.0;
        this.desconto = 0.0;
        this.componentes = new ArrayList<>();
    }
    
    /**
     * Construtor através de um objeto Pacote.
     * @param p Pacote
     */
    public Pacote(Pacote p){
        this.nome = p.getNome();
        this.preco = p.getPreco();
        this.desconto = p.getDesconto();
        this.componentes = p.getComponentes();
    }

    /**
     * Construtor por parâmetros.
     * @param nome nome do pacote
     * @param preco do pacote
     * @param desconto desconto do pacote
     * @param componentes componentes do pacote
     */
    public Pacote(String nome, double preco, double desconto, List<Componente> componentes) {
        this.nome = nome;
        this.preco = preco;
        this.desconto = desconto;
        this.componentes = componentes;
    }

    // Getters e Setters
    
    /**
     * Devolve o nome do pacote
     * 
     * @return nome do pacote
     */
    public String getNome() {
        return nome;
    }

     /**
     * Faz o set do nome do pacote
     * 
     * @param nome do pacote 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Devolve o preco do pacote
     * 
     * @return preco do pacote
     */
    public double getPreco() {
        return preco;
    }

     /**
     * Faz o set do preco do pacote
     * 
     * @param preco do pacote 
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Devolve o desconto do pacote
     * 
     * @return desconto do pacote
     */
    public double getDesconto() {
        return desconto;
    }

     /**
     * Faz o set do desconto do pacote
     * 
     * @param desconto do pacote 
     */
    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    /**
     * Devolve a lista dos componentes do pacote
     * 
     * @return componentes do pacote
     */
    public List<Componente> getComponentes() {
        return componentes;
    }

    /**
     * Adiciona à lista dos componentes do pacote
     * 
     * @param c componente a adicionar
     */
    public void add(Componente c){
        this.componentes.add(c);
    }
    
    /**
     * Remove lista dos componentes do pacote
     * 
     * @param c componente a remover
     */
    public void remove(Componente c){
        this.componentes.remove(c);
    }

    /**
     * Cria um cópia de pacote e devolve-a
     * 
     * @return Clone do pacote.
     */
    public Pacote clone(){
        return new Pacote(this);
    }
    
    /**
     * Faz o add de um incompativel a lista de incompativeis do componente.
     * 
     * @param c id do precedente 
     * @return String com a designacao do pacote
     */
    public String toString() {
        return this.nome;
        
        
    }
}
