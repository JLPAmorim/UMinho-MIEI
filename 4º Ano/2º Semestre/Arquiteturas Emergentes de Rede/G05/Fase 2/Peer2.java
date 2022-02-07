import java.io.IOException;
import java.net.*;
import java.util.*;

public class Peer2 extends Thread {

    private Integer peer; //Se for 0 nao é peer
    private Integer id;
    private Integer nrPath;


    public Handler handler = new Handler();

    public Peer2(int port, int id) throws SocketException {
       this.peer = 0;
        this.id = id;
        this.nrPath = 33599;




    }




    public void ficaPeer() throws IOException, ClassNotFoundException {
        this.peer = 1;

        GestConteudo tConteudo = new GestConteudo(this.id,this.nrPath);
        GestPedidos tPedido = new GestPedidos(this.id,this.nrPath);
        tPedido.start();
        tConteudo.start();



        Scanner myObj = new Scanner(System.in);


        while (this.peer.equals(1)) {

            System.out.println("Se quiseres fazer um pedido prime '1'");
            System.out.println("Se quiseres adicionar conteudo, prime '2'");
	    System.out.println("Se quiseres deixar de ser peer, prime '3'");
        //    System.out.println("Se quiseres eliminar conteudo, prime '4'");
            System.out.println("Se quiseres ver conteudos, prime '4'");
            //5 é para adicionar apple
            //6 é para adicionar firefox
	    String resposta = myObj.nextLine();
            switch (resposta) {

                case "1": //fazer pedido
                    System.out.println("Que ficheiro desejas transferir");
                    String ficheiro = myObj.nextLine();
                    System.out.println("A averiguar se se encontra na rede...");

                    tPedido.fazerPedido(ficheiro);



                    break;

                case "2":
	
                    System.out.println("Que ficheiro desejas adicionar (escreve o nome do ficheiro)");
                    String cont = myObj.nextLine();
		            tConteudo.adicionaConteudo(cont);
		            System.out.println("Conteudo adicionado com sucesso");

	
		    break;
			
                case "3":


                    this.peer=0;
                    break;
                case "4":
			
                    
		    List<String> zat= handler.obterConteudo(this.id,this.nrPath);
			
                    if(zat.size()>0) {
                        for(String s : zat){
                            System.out.println(s);
                        }
                    }
                    else System.out.println("nao tens conteudos");
                    break;
                case "5":

                    tConteudo.adicionaConteudo("apple_raw.png");
                    break;
                case "6":
                    tConteudo.adicionaConteudo("firefox.png");
                    break;
                default:
                    System.out.println("Nao percebi");
            }




        }
    }


    }


