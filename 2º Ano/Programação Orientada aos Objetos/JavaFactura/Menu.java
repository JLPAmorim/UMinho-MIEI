import java.util.Scanner;
import java.io.Serializable;

public class Menu implements Serializable
{
    /** Opção escolhida pelo utilizador do programa */ 
    private int opcao;

    /**
     * Constructor vazio
     */
    public Menu() {
        opcao = 0;
    }
    
    /**
     * Obter a opcao do utilizador do programa
     * @return opcao do utilizador
     */
    public int getOpcao() {
        return this.opcao;
    }
    
    /**
     * Definir a opcao que o utilizador quer
     * @param opcao opcao escolhida
     */
    public void setOpcao(int opcao) {
        this.opcao = opcao;
    }
    
    /**
     * Trata de imprimir as opções disponiveis
     */
    public void menuPrincipal(){
        Scanner input = new Scanner(System.in);
        System.out.println("-------- JavaFactura -------- \n");
        System.out.println("\tOpções\n");
        System.out.println("1 - Cria JavaFactura Teste");
        System.out.println("2 - Carregar Ficheiro \n \n");
        System.out.println("0 - Sair \n \n");
        this.setOpcao(input.nextInt());
        input.nextLine();
    }
    
    /**
     * Trata de imprimir as opções disponiveis
     */
    public void menuNormal(){
        Scanner input = new Scanner(System.in);
        System.out.println("-------- JavaFactura -------- \n");
        System.out.println("\tOpções\n");
        System.out.println("1 - Registar Utilizador");
        System.out.println("2 - Iniciar Sessão \n \n ");
        System.out.println("3 - Gravar Ficheiro");
        System.out.println("0 - Sair \n \n");
        this.setOpcao(input.nextInt());
        input.nextLine();
    }
    
    /**
     * Trata de imprimir as opções disponiveis
     */
    public void menuTestApp(){
        System.out.println("-------- JavaFactura -------- \n");
        System.out.println("Bem-vindo ao teste da Aplicação JavaFactura!");
        System.out.println("Em baixo encontram-se os Actores já registados no Sistema:\n");
        System.out.println("Contribuinte Individual: \n");
        System.out.println("NIF: 111111111 \t Password: 123  \n");
        System.out.println("Contribuinte Individual: \n");
        System.out.println("NIF: 222222222 \t Password: 123  \n");
        System.out.println("Contribuinte Individual: \n");
        System.out.println("NIF: 333333333 \t Password: 123  \n");
        System.out.println("Empresa: \n");
        System.out.println("NIF: 444444444 \t Password: 123 \n");
        System.out.println("Empresa: \n");
        System.out.println("NIF: 555555555 \t Password: 123 \n");
        System.out.println("Admin: \n");
        System.out.println("Código: admin \t Password: 123 \n");
    }
    
    /**
     * Trata de imprimir as opções disponiveis
     */
    public void menuAdmin(){
        Scanner input = new Scanner(System.in);
        System.out.println("-------- JavaFactura -------- \n");
        System.out.println("\tOpções\n");
        System.out.println("1 - Registo das Confirmações de Facturas");
        System.out.println("2 - Lista dos 10 contribuintes que mais gastam em todo o Sistema");
        System.out.println("3 - Lista das X empresas que têm mais Facturas emitidas, com o respetivo Total do Montante de Dedução Fiscal do");
        System.out.println("4 - Fechar Sessão \n \n");
        System.out.println("0 - Sair \n \n");
        this.setOpcao(input.nextInt());
        input.nextLine();
    }
    
    /**
     * Trata de imprimir as opções disponiveis
     */
    public void menuIndividual(){
        Scanner input = new Scanner(System.in);
        System.out.println("-------- JavaFactura -------- \n");
        System.out.println("Sessão Iniciada: Contribuinte Individual\n");
        System.out.println("\tOpções\n");
        System.out.println("1 - Consultar as Minhas Facturas ");
        System.out.println("2 - Valor total de despesas ");
        System.out.println("3 - Montante de Dedução Fiscal Acumulado ");
        System.out.println("4 - Confirmar Facturas Emitidas");
        System.out.println("5 - Valor total de despesas por Actividade Económica \n \n");
        System.out.println("6 - Gravar Ficheiro");
        System.out.println("7 - Fechar Sessão \n \n");
        System.out.println("0 - Sair \n \n");
        this.setOpcao(input.nextInt());
        input.nextLine();
    }
    
    /**
     * Trata de imprimir as opções disponiveis
     */
    public void menuEmpresa(){
        Scanner input = new Scanner(System.in);
        System.out.println("-------- JavaFactura -------- \n");
        System.out.println("Sessão Iniciada: Empresa\n");
        System.out.println("\tOpções\n");
        System.out.println("1 - Criar Factura");
        System.out.println("2 - Listagem das Facturas da Empresa ordenadas por Data ou Valor");
        System.out.println("3 - Listagem das Facturas por Contribuinte num Intervalo de Tempo");
        System.out.println("4 - Listagem das Facturas por Contribuinte Ordenadas por Valor Decrescente");
        System.out.println("5 - Total Facturado num Intervalo de Tempo \n \n");
        System.out.println("6 - Gravar Ficheiro");
        System.out.println("7 - Fechar Sessão \n \n");
        System.out.println("0 - Sair \n \n");
        
        this.setOpcao(input.nextInt());
        input.nextLine();
    }
    

}
