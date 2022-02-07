import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Handler {

    //private Integer id;

    public Handler(){

    }

    public String alocarPeer(Integer newPeer, Integer id, Integer sucessor, Integer antecessor ) {
        String msgVolta = "nada";             //Esta mensagem diz este nodo vai ser sucessor ou antecessor do nodo que me mandou mensagem
        Boolean ponta=false;


        if((id > sucessor) || (id <antecessor)) {
            ponta=true;
        }

        if(  antecessor.equals(0) &&   sucessor.equals(0)){
              //sucessor=newPeer;
              //antecessor=newPeer;
            msgVolta="antecessor e sucessor";
        }
        else if(newPeer>=  id) { //ver se o nodo é provavel ser à minha frente

            if ((newPeer <=   sucessor)) {
                //  sucessor = newPeer;
                msgVolta = "antecessor"; //o nodo que mandou a mensagem vai ter-me a mim como antecessor
            } else if (ponta) {
                if (  id >=   sucessor) { //
                    //  sucessor = newPeer;
                    msgVolta = "antecessor";
                } else if (newPeer >=   antecessor) {
                      //antecessor = newPeer;
                    msgVolta = "sucessor"; //o nodo que mandou a mensagem vai ter-me a mim como sucessor
                }

            }
        }
        else if (newPeer <=   id) { // ver se o nodo é provavel ser atras de mim

            if (newPeer >=   antecessor) {
                  //antecessor = newPeer;
                msgVolta = "sucessor"; //o nodo que mandou a mensagem vai ter-me a mim como antecessor
            } else if (ponta) {
                if (  id <=   antecessor) { //caso para quando o peer mais pequeno é o peer a seguir
                      //antecessor = newPeer;
                    msgVolta = "sucessor";
                } else if (newPeer <=   sucessor) {
                      //sucessor = newPeer;
                    msgVolta = "antecessor"; //o nodo que mandou a mensagem vai ter-me a mim como sucessor
                }
            }
        }


        return msgVolta;
    }


    //verificar os nodos que estao acessiveis nesta rede
    public Set checkHosts(String subnet, Integer id) {
        int timeout = 1000;
        Set<String> enderecos = new HashSet<>();
        for (int i = 1; i < 255; i++) {
            String host = subnet + "." + i;

            try {
                if (InetAddress.getByName(host).isReachable(timeout) && i!=id) {

                    enderecos.add(host);
                    System.out.println(host + " is reachable");
                }

            }
            catch (Exception e){

            }
        }

        return  enderecos;


    }

    public String constroiString(Integer id, String ficheiro){

        //pedido;10.0.0.1;ficheiro;nrhops
        String pedido ="pedido;10.0.0." + id + ";" + ficheiro + ";0";

        return pedido;
    }

    public InetAddress escolheNodo(List<String> nodosPossiveis) throws UnknownHostException {

        Integer hops=999;
        String nodoEscolh = new String();

        for (String nodo : nodosPossiveis){

            System.out.println(nodo);
            String[] arrOfStr = nodo.split(";", 2);
            String nrHops = arrOfStr[1];

            if(hops>Integer.parseInt(nrHops)){
                nodoEscolh=arrOfStr[0];
                hops=Integer.parseInt(nrHops);
            }

        }

        InetAddress escolha = InetAddress.getByName(nodoEscolh);

        return escolha;

    }

    public Boolean verificaFichAvailable(String quote, Integer id) {

        //pedido;10.0.0._;ficheiro;nrhops
        String[] arrOfStr = quote.split(";", 4);
        String ficheiro = "/tmp/pycore.42829/n"+id +".conf/"+arrOfStr[2]; //PODE ESTAR MAL
        System.out.println("nome do ficheiro = " + ficheiro);

        File tmpDir = new File(ficheiro);
        boolean exists = tmpDir.exists();

        if(exists){
            System.out.println("Tenho o ficheiro");

        }
        return exists;

    }



    public void sendTransfer(DatagramSocket socketOuvir, String quote,InetAddress quem, Integer id) throws IOException {
        int count=0;
        int MAX_SIZE = 1024;

        String[] arrOfStr = quote.split(";", 2);

        byte[] sendData = new byte[MAX_SIZE];

        String filePath = "/tmp/pycore.42829/n"+id +".conf/"+arrOfStr[1];
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);


        int totLength = 0;

        while((count = fis.read(sendData)) != -1)    //calculate total length of file
        {
            totLength += count;
        }

        //System.out.println("Total Length :" + totLength);

        int noOfPackets = totLength/MAX_SIZE;
        //System.out.println("No of packets : " + noOfPackets);

        int off = noOfPackets * MAX_SIZE;  //calculate offset. it total length of file is 1048 and array size is 1000 den starting position of last packet is 1001. this value is stored in off.

        int lastPackLen = totLength - off;
        //System.out.println("\nLast packet Length : " + lastPackLen);

        byte[] lastPack = new byte[lastPackLen-1];  //create new array without redundant information


        fis.close();

        FileInputStream fis1 = new FileInputStream(file);
        //while((count = fis1.read(sendData)) != -1 && (noOfPackets!=0))
        while((count = fis1.read(sendData)) != -1 )
        {
            if(noOfPackets<=0)
                break;
            //System.out.println(new String(sendData));
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, quem, 4446);
            socketOuvir.send(sendPacket);
            System.out.println("========");
            //System.out.println("last pack sent" + sendPacket);
            noOfPackets--;
        }

        //check
        //System.out.println("\nlast packet\n");
        //System.out.println(new String(sendData));

        lastPack = Arrays.copyOf(sendData, lastPackLen);

        //System.out.println("\nActual last packet\n");
        //System.out.println(new String(lastPack));
        //send the correct packet now. but this packet is not being send.
        DatagramPacket sendPacket1 = new DatagramPacket(lastPack, lastPack.length, quem,4446);
        socketOuvir.send(sendPacket1);
        System.out.println("Acabei o envio");

    }

    public void receberFicheiro(Integer id, DatagramSocket socketConteudo, String ficheiro) throws IOException {

        byte[] recData = new byte[1024];
        String filePath = "/tmp/pycore.42829/n"+id +".conf/"+ficheiro;
        FileOutputStream file = new FileOutputStream(filePath);
        DatagramPacket recPacket = new DatagramPacket(recData, recData.length);
        //BufferedOutputStream bos = new BufferedOutputStream(fos);
        socketConteudo.setSoTimeout(5000);
        boolean isListen=true;
        while(isListen)
	
        {
            try {
                    socketConteudo.receive(recPacket);
                    System.out.println("a receber...");
                    //System.out.println("\n Data: " + line);
                    file.write(recPacket.getData());
                    //System.out.println("\nPacket" + ++i + " written to file\n");
                    file.flush();

                    } catch (SocketTimeoutException e) {
                        //if (!isListen) {
                        isListen=false;

                        } // implement your business logic here

/*


 */
        }


        System.out.println("Terminei a transferencia");
    }
}


