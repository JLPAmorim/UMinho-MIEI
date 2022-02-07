import java.io.IOException;
import java.net.*;
import java.util.*;

public class Peer2 extends Thread {
    private DatagramSocket socketOuvir;
    private DatagramSocket socketEnviar;
    private DatagramSocket socketPing;
    private DatagramSocket socketConteudo;
    private Integer peer; //Se for 0 nao é peer
    private Integer sucessor;
    private Integer antecessor;
    private Integer id;
    private Integer port;
    private Boolean pingar;

    public Handler handler = new Handler();

    public Peer2(int port, int id) throws SocketException {
        this.socketConteudo = new DatagramSocket(port+2);
        this.socketOuvir = new DatagramSocket(port); //Este é o socket para ouvir pedidos
        this.socketEnviar = new DatagramSocket(port + 1); //Este é o socket para enviar pedidos, no método ficaPeer()
        this.socketPing = new DatagramSocket(port-1); //Este é o socket para enviar pings
        this.peer = 0;
        this.sucessor = 0;
        this.antecessor = 0;
        this.id = id;
        this.port = port;
	    this.pingar=false;


    }



    /*Este metodo é responsavel por pingar os vizinhos
    e verificar se os vizinhos ainda se encontram disponiveis, ou se já
    se perdeu alguem
     */
    public void pingPeer() {
	this.pingar=true;
        Thread thread3 = new Thread(() -> {
        while(this.peer.equals(1)) {
            Integer flag=0;
            if (this.sucessor != 0 && this.antecessor != 0) {
                String mensagem = null;
                InetAddress suce = null;
                InetAddress ante = null;
                try {
                    suce = InetAddress.getByName("10.0.0." + this.sucessor);
                    ante = InetAddress.getByName("10.0.0." + this.antecessor);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }


                //System.out.println("Sending Ping Request to peers");


                    try {

                        if (suce.isReachable(1000)) {
                            //System.out.println("O sucessor esta fixe");
                            //mensagem = "lost sucessor " + this.sucessor;
                        }
                    }
                    catch (IOException e) {
                        System.out.println("Perdeu-se o sucessor");
                        mensagem="lost sucessor " + this.sucessor;
                        flag+=1;
                    }
                     try {
                         if (ante.isReachable(1000)) {
                             //System.out.println("O antecessor esta fixe");
                             //mensagem="lost antecessor " + this.antecessor;
                         }
                     }
                     catch (IOException e) {
                        System.out.println("Perdeu-se o antecessor");
                        mensagem= "lost antecessor " + this.antecessor;
			flag+=1;
                     }



               	
            if(mensagem!=null && flag!=2) {
                Set<String> enderecos = handler.checkHosts("10.0.0", this.id);

                for (String endereco : enderecos) {
                    byte[] bufferSend;
                    InetAddress addPeer;
                    try {
                        addPeer = InetAddress.getByName(endereco);
                        bufferSend = mensagem.getBytes();
                        DatagramPacket request = new DatagramPacket(bufferSend, bufferSend.length, addPeer, port);
                        socketPing.send(request);
                        System.out.println("enviou");


                    } catch (IOException e) {
                        System.out.println("algo correu mal");
                    }

                }
            }
            }
	
	    if(flag.equals(2)){
		System.out.println("Perdi os vizinhos, deixei de ser peer");
		this.antecessor=0;
		this.sucessor=0;
		this.peer=0;
	   }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
                });
                thread3.start();
        }


    /*
    Quando um nodo fica em modo peer, isto é o centro de controlo
    Aqui é onde sao pedidos, por exemplo, para ver quem sao os vizinhos,
     */
    public void ficaPeer() throws IOException, ClassNotFoundException {
        this.peer = 1;
        //System.out.println("Escre mer");
        Scanner myObj = new Scanner(System.in);
        Set<String> enderecos = new HashSet<>();
        try {
            //System.out.println("Para ficar peer escreve 'peer'");
            //if (myObj.nextLine().equals("peer")) {
            enderecos = handler.checkHosts("10.0.0", this.id);

            //}


        } catch (Exception e) {
            System.out.println("Algo correu mal");
        }

        for (String endereco : enderecos) {
            byte[] bufferSend;
            InetAddress addPeer;
            try {
                addPeer = InetAddress.getByName(endereco);
                String qqlcena = "novo peer";
                bufferSend = qqlcena.getBytes();
                DatagramPacket request = new DatagramPacket(bufferSend, bufferSend.length, addPeer, port);
                socketEnviar.send(request);
                System.out.println("enviou");


            } catch (IOException e) {
                System.out.println("Algo correu mal");
            }

        }
        if(this.pingar==false)pingPeer(); //Se vires que há merda nos pings, mas duvido que encontres esses problemas, tira a condicao e deixa tar so pingPeer()

        while (this.peer.equals(1)) {

            System.out.println("Se quiseres ver os peers prime '1'");
            System.out.println("Se quiseres transferir conteudo, prime '2'");
            System.out.println("Se quiseres deixar de ser peer, prime '3'");
            String resposta = myObj.nextLine();
            switch (resposta){
                case "1":
                    System.out.println("sucessor = " + this.sucessor + " antecessor = " + this.antecessor);
                    break;
                case "2":
                    System.out.println("Que ficheiro desejas transferir");
                    String ficheiro = myObj.nextLine();
                    System.out.println("A averiguar se se encontra na rede...");

                    InetAddress suc = InetAddress.getByName("10.0.0." + this.sucessor);
                    InetAddress ant = InetAddress.getByName("10.0.0." + this.antecessor);

                    List<String> nodosPossiveis = new ArrayList<>();
                    String send=handler.constroiString(this.id,ficheiro);
                    String sendSuc = send + ";sucessor";
                    String sendAnt = send + ";antecessor";

                    byte[] enviarsuc = sendSuc.getBytes();
                    byte[] enviarant = sendAnt.getBytes();
                    DatagramPacket requestAnt = new DatagramPacket(enviarant, enviarant.length,ant, port);//Envia ao antecessor
                    DatagramPacket requestSuc = new DatagramPacket(enviarsuc, enviarsuc.length, suc, port);//Envia ao sucessor

                    socketEnviar.send(requestAnt);
                    socketEnviar.send(requestSuc);

                    socketEnviar.setSoTimeout(5000);

                    //receber a resposta da procura
                    byte[] receber = new byte[1000];
                    DatagramPacket dp = new DatagramPacket(receber, receber.length);

                    while(true) {
                        try {
                            socketEnviar.receive(dp);
                            String quote = new String(receber, 0, dp.getLength());
                            nodosPossiveis.add(quote);

                            System.out.println("Recebi uma resposta de transferencia");

                        } catch (SocketTimeoutException e) {
                            //timeout
                            //System.out.println("TimeoutReached");
                            break;
                        }

                    }
                        if (nodosPossiveis.size() < 1)  System.out.println("Ficheiro não existe");
                        else {
                            InetAddress nodoEscolhido = handler.escolheNodo(nodosPossiveis);                            //pedir ao quem para me mandar o ficheiro.
                            String f = "transferir;"+ficheiro;
                            byte[] file = f.getBytes();

                            DatagramPacket fp = new DatagramPacket(file,file.length,nodoEscolhido,4444);
                            this.socketEnviar.send(fp);

                            handler.receberFicheiro(this.id,this.socketConteudo,ficheiro);



                        }



                    break;


                case "3":
                    String respostaAnt = "sair;sucessor;" + this.sucessor;
                    String respostaSuc = "sair;antecessor;" +this.antecessor;
                    byte[] send1 = respostaAnt.getBytes();
                    byte[] send2 = respostaSuc.getBytes();
                    InetAddress ant1 = InetAddress.getByName("10.0.0."+this.antecessor);
                    InetAddress suc1 = InetAddress.getByName("10.0.0."+this.sucessor);

                    DatagramPacket antPacket = new DatagramPacket(send1, send1.length,ant1,4444);
                    DatagramPacket sucPacket = new DatagramPacket(send2, send2.length,suc1,4444);
                    this.socketEnviar.send(antPacket);
                    this.socketEnviar.send(sucPacket);
                    this.peer=0;
                    break;

                default:
                    System.out.println("Nao percebi");
            }




        }
        this.antecessor=0;
        this.sucessor=0;
        this.pingar=false;
    }
		

    /*neste metodo, esta um socket aberto para receber os pedidos de outros peers.
    Se por exemplo, alguem mandar uma mensagem a dizer que perdeu o sucessor,
    este metodo tratar de receber isso, processa a informacao, e se for preciso,
    envia de volta a resposta.

    */

    public void ouvirPedidosdeNodos(){

        Thread thread2 = new Thread(() -> {
            while (true) {

                byte[] bufferReceive = new byte[1000];
                DatagramPacket request = new DatagramPacket(bufferReceive, bufferReceive.length);
                System.out.println(this.port);

                try {
		    //System.out.println("Estou a ouvir");
                    this.socketOuvir.receive(request);
                } catch (IOException e) {
                    System.out.println("Algo correu mal");
                }
                String quote = new String(bufferReceive, 0, request.getLength());
                InetAddress quemdisse = request.getAddress();
                System.out.println("O NODO " + quemdisse + " DISSE ISTO: " + quote);
                String resposta = "zat";

                String merda = quemdisse.toString().substring(1);
                String[] arrOfStr = merda.split("\\.",4);

                Integer newPeer = Integer.parseInt(arrOfStr[3]); //obter o id do nodo que me mandou mensagem
                System.out.println("O nodo que me mandou mensagem foi o nodo " + newPeer);
                InetAddress clientAddress = request.getAddress();
                //novo Peer
		        if (quote.equals("novo peer") && this.peer.equals(1)) { //se houver um novo peer
                    resposta = handler.alocarPeer(newPeer, this.id, this.sucessor, this.antecessor);
			
                    if (resposta.equals("sucessor")) {
                        this.antecessor = newPeer;
                    } else if (resposta.equals("antecessor")) {
                        this.sucessor = newPeer;
                    } else if (resposta.equals("antecessor e sucessor")) {
                        this.sucessor = newPeer;
                        this.antecessor = newPeer;
                    }

		         if(resposta!="zat"){

                    byte[] buffer = resposta.getBytes();

                    //int clientPort = request.getPort();

                    DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, port);
                    try {
                        socketOuvir.send(response);
                    } catch (IOException e) {
                        //e.printStackTrace();
                        System.out.println("Correu mal aqui na response diferente de null");
                        }
		            }

                }
                //o novo nodo vai ser meu sucessor
                else if (quote.equals("sucessor") && this.peer.equals(1)) {
                    this.sucessor = newPeer;

                }
                //o novo nodo vai ser meu antecessor
                else if (quote.equals("antecessor")) {
                    this.antecessor = newPeer;

                }

                //o novo nodo vai ser meu antecessor e sucessor
               else if (quote.equals("antecessor e sucessor")) { //quando so ha um nodo no peer
                    this.antecessor = newPeer;
                    this.sucessor = newPeer;
                }

               //alguem perdeu o antecessor
               else if(quote.contains("lost antecessor")){
                    String[] separar = quote.split(" ",3);
                    Integer quemsePerdeu = Integer.parseInt(separar[2]); //obter o id do nodo que se perdeu

                    if(this.sucessor.equals(quemsePerdeu)){
                        this.sucessor=newPeer;
                        byte[] buffer = "antecessor".getBytes();

                        //int clientPort = request.getPort();

                        DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, port);
                        try {
                            socketOuvir.send(response);
                        } catch (IOException e) {
                            //e.printStackTrace();
				        System.out.println("Correu mal aqui no lost antecessor");
                        }

                    }

                }
                //alguem perdeu o sucessor
               else if(quote.contains("lost sucessor")){
                    String[] separar = quote.split(" ",3);
                    Integer quemsePerdeu = Integer.parseInt(separar[2]); //obter o id do nodo que se perdeu

                    if(this.antecessor.equals(quemsePerdeu)){
                        this.antecessor=newPeer;

                        byte[] buffer = "sucessor".getBytes();

                        //int clientPort = request.getPort();

                        DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, port);
                        try {
                            socketOuvir.send(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

                //Lidar com pedidos de localizacao

               else if(quote.contains("pedido")) {

                    String[] arrOfStr2 = quote.split(";", 5);
                    String sucouant = arrOfStr2[4];
                    String origem = arrOfStr2[1];
                    Integer quempediu = Integer.parseInt(origem.substring(origem.length() - 1));
                    System.out.println("quem pediu incialmente foi o nodo " + quempediu);
                    Integer nrHops = Integer.parseInt(arrOfStr2[3])+1;

                    if (quempediu != this.id) {


                        Boolean tenho = handler.verificaFichAvailable(quote, this.id);

                        if (tenho) {
                            InetAddress nodo = null;
                            try {
                                nodo = InetAddress.getByName(origem);
                            } catch (UnknownHostException e) {
                                e.printStackTrace();
                            }

                            String resposta1 = "10.0.0." + this.id + ";" + nrHops;

                            DatagramPacket dpInicial = new DatagramPacket(resposta1.getBytes(), resposta1.getBytes().length, nodo, 4445); //mensagem para o nodo que pediu

                            try {
                                socketOuvir.send(dpInicial);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //abrir socket para enviar para o nodo que pediu e para o sucessor.

                        }


                        //enviar mensagem para o nodo sucessor ou antecessor
                        InetAddress peer = null;
                        try {



                            if(sucouant.equals("sucessor"))
                            peer = InetAddress.getByName("10.0.0." + this.sucessor);

                            else peer=InetAddress.getByName("10.0.0."+this.antecessor);

                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        }

                        String res = arrOfStr2[0]+";"+arrOfStr2[1]+";"+arrOfStr2[2]+";"+nrHops+";"+sucouant;

                        DatagramPacket dp = new DatagramPacket(res.getBytes(), res.getBytes().length, peer, 4444);
                        try {
                            socketOuvir.send(dp);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

               else if(quote.contains("transferir")){


                    try {
                        handler.sendTransfer(this.socketConteudo,quote,quemdisse,this.id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
               else if(quote.contains("sair")){

                    String[] arrOfStr2 = quote.split(";", 3);
                    Integer ident=Integer.parseInt(arrOfStr2[2]);

                    if(!ident.equals(this.id)) {

                        if (quote.contains("sucessor")) {
                            this.sucessor = ident;
                        } else {
                            this.antecessor = ident;
                        }

                    }
                    else{
                        this.sucessor=0;
                        this.antecessor=0;
                    }

                }


                }





        });
        thread2.start();
    }
}


