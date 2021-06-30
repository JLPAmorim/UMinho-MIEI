/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configurafacil.business;

import java.util.List;
import java.util.ArrayList;

public class Encomenda {
    
    /* Lista das Configuracoes existentes no sistema */
    private List<Configuracao> configuracoes;

    //Construtores

    /**
     * Construtor vazio.
     */
    public Encomenda() {
        this.configuracoes = new ArrayList<>();
    }
    
    /**
     * Construtor através de um objeto Encomenda.
     * @param e Encomenda
     */
    public Encomenda(Encomenda e) {
        this.configuracoes = e.getConfiguracoes();
    }
    
    /**
     * Construtor por parâmetros.
     * @param configuracoes lista de configuracoes existentes
     */
    public Encomenda(List<Configuracao> configuracoes) {
        this.configuracoes= configuracoes;
    }

    /**
     * Devolve a lista de configuracoes existentes
     * 
     * @return configuracoes 
     */
    public List<Configuracao> getConfiguracoes() {
        return configuracoes;
    }

    /**
     * Adiciona uma configuracao
     * 
     * @param config configuracao a adicionar
     */
    public void addConfiguracao(Configuracao config){
        this.configuracoes.add(config);
    }
    
    /**
     * Remove uma configuracao
     * 
     * @param config configuracao a adicionar
     */
    public void removeConfiguracao(Configuracao config){
        this.configuracoes.remove(config);
    }
    
    /**
     * Cria um cópia de encomenda e devolve-a
     * 
     * @return Clone da encomenda
     */
    public Encomenda clone(){
        return new Encomenda(this);
    }   
}
