import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Mensagem implements Serializable {
    private String nomeFile;
    private Integer idNodo;
    private Integer idOriginal;
    private String descricao;
    private byte[] conteudo;
    private Set<Integer> nodosAtr;
    private String pedidoCounter;

    public Mensagem(String nome, Integer idN, Integer idO, String ded, byte[] cont, HashSet<Integer> nodos, String idPedido){
        this.nomeFile = nome;
        this.idNodo=idN;
        this.idOriginal=idO;
        this.descricao=ded;
        this.conteudo= cont;
        this.nodosAtr=nodos;
        this.pedidoCounter=idPedido;
    }


    public String getPedidoCounter() {
        return pedidoCounter;
    }

    public Integer getIdOriginal() {
        return idOriginal;
    }

    public String getDescricao() {
        return descricao;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    public Set<Integer> getNodosAtr() {
        return nodosAtr;
    }

    public void retirarNodo(Integer idNodo){
        this.nodosAtr.remove(idNodo);
    }

    public String getNomeFile() {
        return nomeFile;
    }

}
