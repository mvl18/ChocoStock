package chocostock;

import chocostock.auxiliar.CriarTeste;
import chocostock.colaboradores.Cliente;
import chocostock.colaboradores.Colaborador;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bem-vindo ao ChocoStock! O doce controle de vendas e estoque.\n");

        Scanner scanner = new Scanner(System.in);

        Endereco endereco = CriarTeste.Endereco();
//        System.out.println(enderecmaino);

      
        // TESTES INICIAIS MARAOLT
        Colaborador cliente1 = CriarTeste.Cliente("André Silva");
        Cliente cliente2 = CriarTeste.Cliente("José");

        Loja loja = new Loja("Primeira e unica loja!", endereco);
        loja.addCliente((Cliente) cliente1);
        loja.addCliente(cliente2);
        System.out.println("teste");

        System.out.println("=========================");
        boolean continuar = true;
        while(continuar) {
            System.out.println("Digite 'add' para adicionar pedidos, 'list' para mostrar a lista de pedidos, ou 'end' para encerrar o programa!");
            String comando = scanner.nextLine();
            switch(comando) {
                case "add": loja.addPedido(loja.novoPedido(scanner, loja));
                            break;
                case "list": System.out.println(loja.listaPedidos());
                             break;
                case "end": continuar = false;
                             break;
            }
        }

        System.out.println(loja.listaClientes());
        System.out.println(loja.listaPedidos());

        scanner.close();
    }
}