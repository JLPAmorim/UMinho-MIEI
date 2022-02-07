import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class GestPedidos extends Thread {
    private Integer id;

    private Set<String> pedidos;
    private DatagramSocket socketPedidos;
    private Handler handler;
    private Integer nrPath;
    private Integer pedidoID;


    public GestPedidos(Integer ID, Integer path) throws SocketException {
        this.id = ID;
        this.socketPedidos = new DatagramSocket(6000);
        this.pedidos = new HashSet<>();
	    this.handler = new Handler();
	    this.nrPath = path;
	    this.pedidoID=0;
    }


    public void run(){



        while(true){


            byte[] buffer = new byte[512];
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);

            try {
                this.socketPedidos.receive(dp);
            } catch (IOException e) {
                e.printStackTrace();
            }
		
            String quote = new String(buffer, 0, dp.getLength());
            InetAddress quemdisse = dp.getAddress();
            System.out.println("O NODO " + quemdisse + " DISSE ISTO: " + quote);

	
            String[] arrOfStr = quote.split(":",6);

            //verificar se TTL ainda é valido
            if((!arrOfStr[4].equals("0")) ) {
                this.pedidos.add(arrOfStr[5]);
                Boolean b= false;
                try {

                    List<String> lista = handler.obterConteudo(this.id,this.nrPath);
		    if(lista.size()>0){

                        //Verificar se o pedido de interesse bate certo com o conteudo
                        for (String s : lista){

                            if (s.contains(arrOfStr[1])){

                                String filePath = "/tmp/pycore."+ nrPath +"/n"+id +".conf/"+s;

                                byte[] bytes= null;
                                try {
                                    bytes = Files.readAllBytes(Paths.get(filePath));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                String[] nodosAtr = arrOfStr[3].split(",");

                                HashSet<Integer> nodos = new HashSet<>();

                                for (String z : nodosAtr){
                                    nodos.add(Integer.parseInt(z));
                                }

                                for (Integer i : nodos) System.out.println("nodooooo= " +i);
                                String id = ""+this.id+this.pedidoID;
                                Mensagem m = new Mensagem(s,this.id,Integer.parseInt(arrOfStr[2]),arrOfStr[1],bytes, nodos,id);
                                //System.out.println("entrou no enviar");
                                enviarConteudo(m);
                                pedidoID++;

                                b=true;
                            }
                        }
                    }
                    if(b.equals(false)){
                        System.out.println("Nao tenho ficheiros desse tipo");
                        reencaminharPedido(quote);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }
            else System.out.println("ja processei esse pedido");


        }


    }

    public void fazerPedido(String pedido) throws IOException {
        //1-Enviar um pedido a todos os nodos alcancaveis.


        //this.pedidos.add(pedido);

        List<String> enderecos;
        enderecos = handler.checkHosts("10.0.0",this.id);

        if(enderecos.size()==0) System.out.println("Tente mais tarde, não há nodos disponiveis");

        else{
            String id = ""+this.id + this.pedidoID;
            this.pedidoID++;
            String request = "RequestforFile:" + pedido + ":" + this.id + ":" + this.id + ":5:"+id;
            byte[] req = request.getBytes();

            for (String endereco : enderecos) {
                DatagramPacket dp = new DatagramPacket(req, req.length, InetAddress.getByName(endereco), 6000);
                this.socketPedidos.send(dp);
            }

        }

    }

    public void enviarConteudo(Mensagem recebido) {
	//System.out.println(recebido.getDescricao());
        Thread thread2 = new Thread(() -> {
        System.out.println(recebido.getIdOriginal());
        int i = 0;
            while(i==0) {
		//System.out.println(this.id);
                List<String> list = handler.checkHosts("10.0.0", this.id);
                Set<Integer> nodosAtr = recebido.getNodosAtr();
		
		//System.out.println("tamanho dos nodos = "+ list.size());
                for(Integer e : nodosAtr) {
                    if (list.contains("10.0.0."+ e)) {
                        //enviar para esse parvalhao
			//System.out.println("ZATAOOOO");
                        try {
				
                            Socket socket = new Socket("10.0.0." + e, 6666);

                            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                            outputStream.writeObject(recebido);
				                System.out.println("ja enviou conteudo");


                        } catch (UnknownHostException unknownHostException) {
                            unknownHostException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        i=1;
                    }
                }

            }

        });
        thread2.start();




    }

    public void reencaminharPedido(String quote){

        Thread thread2 = new Thread(() -> {
            boolean flag = true;
            while (flag) {

                //list contem os nodos alcancaveis
                List list = handler.checkHosts("10.0.0", this.id);

                //agora é preciso verificar que nao mandamos o pedido para nodos que ja o receberam

                String[] arrOfStr = quote.split(":", 6);
		String[] arrOfNodes;
		if(arrOfStr[3].contains(",")) arrOfNodes = arrOfStr[3].split(",");
		else {
			arrOfNodes = new String[] {arrOfStr[3]};
		
		}
                
                List<String> list2 = new ArrayList<>();

                for (String s : arrOfNodes){
                    list2.add("10.0.0." + s);
                }

                //retirar da lista de nodos alcancaveis os que ja receberam a mensagem
                for (Object nodo : list2){
                    if(list.contains(nodo)){
                        list.remove(nodo);
                    }
                }

	
                if(!(list.size() ==0)) {

                    arrOfStr[3] = arrOfStr[3] + "," + this.id;
                    arrOfStr[4] = String.valueOf(Integer.parseInt(arrOfStr[4]) - 1);
                    String reencaminhar = arrOfStr[0] + ':' + arrOfStr[1] + ':' + arrOfStr[2] + ':' + arrOfStr[3] + ':' + arrOfStr[4]+':' + arrOfStr[5];
		    System.out.println(reencaminhar);
                    byte[] ped = reencaminhar.getBytes();


                    for (Object s : list) {
                        DatagramPacket dp = null;
                        try {
                            dp = new DatagramPacket(ped, ped.length, InetAddress.getByName((String) s), 6000);

                            socketPedidos.send(dp);
			    System.out.println("PEdido reencaminhado com sucesso");
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("algo correu mal ao reencaminhar pedido");
                        }
                    }
                    flag=false;
                }
            }
        });
        thread2.start();
    }

}
