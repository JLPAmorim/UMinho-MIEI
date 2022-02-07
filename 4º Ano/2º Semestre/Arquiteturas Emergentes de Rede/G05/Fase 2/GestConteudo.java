/*Nesta classe vamos tratar de fazer
pedidos por udp e receber conteudo por tcp
 */

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class GestConteudo extends Thread {
    private DatagramSocket socketConteudo;
    private Integer id;
    private List<String> conteudos;
    private Set<String> pedidos;
    private Handler handler;
    private ServerSocket ss;//=new ServerSocket(6666);
    private Integer nrPath;



    public GestConteudo(Integer idd, Integer path) throws IOException {
        this.id = idd;
        this.conteudos = new ArrayList<>();
        this.pedidos = new HashSet<>();
        this.socketConteudo = new DatagramSocket(5001);
        this.ss = new ServerSocket(6666);
	    this.handler = new Handler();
	    this.nrPath = path;

    }



    //Esta sempre a ouvir caso lhe passe algum conteudo para retornar
    public void run(){

        handler.criarFicheiro(this.id,this.nrPath);

        while(true){


            Socket s = null;
            try {
                s = ss.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
    

            ObjectInputStream is = null;
            try {
                is = new ObjectInputStream(s.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ObjectOutputStream os = null;
            try {
                os = new ObjectOutputStream(s.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Mensagem m = null;
            try {
                m = (Mensagem) is.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


            try {
                os.writeObject(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }





            int quemPediu = m.getIdOriginal();
            String request = m.getPedidoCounter();

            if(!this.pedidos.contains(request)) {
                //System.out.println("nao contem");
                String desc = m.getDescricao();
                this.pedidos.add(request);

                this.conteudos.add(desc);
                System.out.println("recebeu conteudo do nodo = " + quemPediu);
                if (quemPediu == this.id) {
                    try {
                        guardaFicheiro(m);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else enviarConteudo(m);
            }





           


        }


    }



    public void enviarConteudo(Mensagem recebido) {

        Thread thread2 = new Thread(() -> {
            recebido.retirarNodo(this.id);
            int i = 0;
	    //List<String> list = new ArrayList<>();
            while(i==0) {
                List<String> list;
                list = handler.checkHosts("10.0.0", this.id);

                Set<Integer> nodosAtr = recebido.getNodosAtr();
                AtomicReference<Integer> z= new AtomicReference<>(0);
                nodosAtr.forEach((e) -> {
                    if (list.contains("10.0.0."+ e)) {
                        //enviar para esse parvalhao
                        z.getAndSet(z.get() + 1);

                                        try {
                                            Socket socket = new Socket("10.0.0." + e, 6666);

                                            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                                            outputStream.writeObject(recebido);

                                        } catch (UnknownHostException unknownHostException) {
                                            unknownHostException.printStackTrace();
                                        } catch (IOException ioException) {
                                            ioException.printStackTrace();
                                        }

                                    }
                                });
                if(z.get()>0) i=1;

                            }




            });
                thread2.start();
                }



    public void guardaFicheiro(Mensagem m) throws IOException {
        String nomeFicheiro = m.getNomeFile();
        byte[] zat = m.getConteudo();
        String Filename = "/tmp/pycore."+nrPath+"/n"+id +".conf/"+nomeFicheiro;
        File file = new File(Filename);

        OutputStream os = new FileOutputStream(file);

        os.write(zat);
        System.out.println("Ficheiro obtido com sucesso");

        os.close();

        handler.adicionaContTxt(nomeFicheiro,this.id,this.nrPath);
    }

    public void adicionaConteudo(String cont){
        this.conteudos.add(cont);
        handler.adicionaContTxt(cont,this.id,this.nrPath);
    }

}