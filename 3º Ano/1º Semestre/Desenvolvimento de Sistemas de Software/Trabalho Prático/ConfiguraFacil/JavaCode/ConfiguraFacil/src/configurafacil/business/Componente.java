/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configurafacil.business;


import java.util.List;
import java.util.ArrayList;

public class Componente {
    
    /* Código do componente */
    private int codigo;
    /* Nome do componente */
    private String designacao;
    /* Preço do componente  */
    private double preco;
    /* Quantidade do componente em stock */
    private int quantidade;
    /* Tipo do componente */
    private String tipo;
    /* Lista dos precedentes do componente */
    private List<Integer> precedentes;
    /* Lista dos incompativeis do componente */
    private List<Integer> incompativeis;
    
    //Construtores

    /**
     * Construtor vazio.
     */
    public Componente(){
        this.codigo = 0;
        this.designacao = null;
        this.preco = 0.0;
        this.quantidade = 0;
        this.tipo = null;
        this.precedentes = new ArrayList<>();
        this.incompativeis = new ArrayList<>();    
    }
    
    /**
     * Construtor através de um objeto Cliente.
     * @param c Cliente
     */
    public Componente(Componente c){
        this.codigo = c.getCodigo();
        this.designacao = c.getDesignacao();
        this.preco = c.getPreco();
        this.quantidade = c.getQuantidade();
        this.tipo = c.getTipo();
        this.precedentes = c.getPrecedentes();
        this.incompativeis = c.getIncompativeis(); 
    }
    
    /**
     * Construtor por parâmetros.
     * @param codigo id do componente
     * @param designacao nome do componente
     * @param preco preco do componente
     * @param quantidade quantidade do componente em stock
     * @param tipo tipo do componente
     * @param precedentes lista de precedentes do componente
     * @param incompativeis lista de incompativeis do componente
     */
    public Componente(int codigo, String designacao, double preco, int quantidade, String tipo, List<Integer> precedentes, List<Integer> incompativeis) {
        this.codigo = codigo;
        this.designacao = designacao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.precedentes = precedentes;
        this.incompativeis = incompativeis;
    }
    
    // Getters e Setters
    
     /**
     * Devolve o codigo do componente
     * 
     * @return codigo do componente
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Faz o set do id do componente.
     * 
     * @param codigo Novo codigo do componente 
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    /**
     * Devolve o nome do componente
     * 
     * @return nome do componente
     */
    public String getDesignacao() {
        return designacao;
    }

    /**
     * Faz o set do nome do componente.
     * 
     * @param designacao Novo nome do componente 
     */
    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }
    
    /**
     * Devolve o preco do componente
     * 
     * @return preco do componente
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Faz o set do preco do componente.
     * 
     * @param preco Novo preco do componente 
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Devolve a quantidade do componente em stock
     * 
     * @return quantidade do componente
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Faz o set da quantidade do componente em stock
     * 
     * @param quantidade Novo codigo do componente 
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Devolve o tipo do componente
     * 
     * @return tipo do componente
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Faz o set do tipo do componente.
     * 
     * @param tipo Novo codigo do componente 
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Devolve a lista de precedentes
     * 
     * @return precedentes lista de precedentes
     */
    public List<Integer> getPrecedentes() {
        return precedentes;
    }
    
    /**
     * Faz o add de um precedente a lista de precedentes do componente.
     * 
     * @param c id do precedente 
     */
    public void addPrecedentes(Integer c) {
        this.precedentes.add(c);
    }

    /**
     * Devolve a lista de incompativeis
     * 
     * @return incompativeis lista de incompativeis
     */
    public List<Integer> getIncompativeis() {
        return incompativeis;
    }

    /**
     * Faz o add de um incompativel a lista de incompativeis do componente.
     * 
     * @param c id do incompativel 
     */
    public void addIncompativeis(Integer c) {
        this.incompativeis.add(c);
    }

    /**
     * Cria um cópia de componente e devolve-a
     * 
     * @return Clone do componente
     */
    public Componente clone(){
        return new Componente(this);
    }

    /*@Override
    public String toString() {
        String s = this.codigo + ", " + this.designacao + ", " + this.preco + ", " + this.quantidade + ", " + this.tipo + ", [";
        
        for(int i = 0; i < this.precedentes.size(); i++)
            s += this.precedentes.get(i) + " ";
        
        s += "], [";
        
        for(int i = 0; i < this.incompativeis.size(); i++)
            s += this.incompativeis.get(i)+",";
        
        return s + "]";   
    }*/
    
    /**
     * Faz o add de um incompativel a lista de incompativeis do componente.
     * 
     * @param c id do precedente 
     * @return designacao com a designacao do componente
     */
    @Override
    public String toString(){
        return this.designacao;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    

    
    
    
   
}
