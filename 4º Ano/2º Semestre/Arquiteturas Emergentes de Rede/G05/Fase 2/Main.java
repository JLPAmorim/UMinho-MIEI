import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
    //Tens de passar o ultimo digito do endereco ipv4, pois esse vai ser o identificador

        if (args.length != 1) {
            System.out.println("insere o identificador do nodo");
        } else {

            Integer id = Integer.parseInt(args[0]);
            Peer2 peer = new Peer2(4444, id);
            //peer.ouvirPedidosdeNodos();

            System.out.println("\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"Bem vindo ao nodo \"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"");
            System.out.println("\n\n\n\n\n");
            System.out.println("O que queres fazer?");
	    System.out.println("Escreve 'peer' para ficares peer");
	    Scanner myObj = new Scanner(System.in);
            String resposta = "zat";

            while (true){

		resposta=myObj.nextLine();

            if (resposta.equals("peer")) {
                peer.ficaPeer();
                System.out.println("Saiste de modo peer");
            }
                System.out.println("Escreve 'peer' para ficares peer");

            }

        }
    }
}
