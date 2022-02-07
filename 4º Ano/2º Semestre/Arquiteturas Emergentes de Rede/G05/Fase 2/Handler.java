import java.io.*;
import java.net.*;
import java.util.*;

public class Handler {



    public Handler(){

    }




    //verificar os nodos que estao acessiveis nesta rede
    public List checkHosts(String subnet, Integer id) {
        int timeout = 1000;
       // System.out.println("entrouuuuu");
        List<String> enderecos = new ArrayList<>();
        for (int i = 1; i < 255; i++) {
            String host = subnet + "." + i;
		
            try {
                if (InetAddress.getByName(host).isReachable(timeout) && i!=id) {
		
                    enderecos.add(host);
                    //System.out.println(host + " is reachable");
                }

            }
            catch (Exception e){

            }
        }
		

        return  enderecos;


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


    public void criarFicheiro(Integer id, Integer nrPath){
        try {
            File myObj = new File("/tmp/pycore."+nrPath+"/n"+id +".conf/conteudos.txt");
            if (myObj.createNewFile()) {
                //System.out.println("File created: " + myObj.getName());
            } else {
                myObj.delete();
                myObj.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public List obterConteudo(Integer id, Integer nrPath) throws FileNotFoundException {

        List<String> list = new ArrayList<>();
        File myObj = new File("/tmp/pycore."+nrPath+"/n"+id +".conf/conteudos.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            list.add(data);
        }
        return list;
    }

    public void adicionaContTxt(String zat, Integer id, Integer nrPath){

        try (FileWriter f = new FileWriter("/tmp/pycore."+nrPath+"/n"+id +".conf/conteudos.txt", true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);) {

            p.println(zat);

        } catch (IOException i) {
            i.printStackTrace();
        }

    }


}


