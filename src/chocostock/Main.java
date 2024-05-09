package chocostock;

import chocostock.colaboladores.Cliente;
import chocostock.colaboladores.Colaborador;
import chocostock.colaboladores.Funcionario;
import jdk.jshell.execution.JdiExecutionControl;

import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem-vindo ao ChocoStock! O doce controle de vendas e estoque.");

        // TESTES INICIAIS MARAOLT
        Endereco endereco1 = new Endereco("17154-1283", "Rua Alves Brito", "Barao Geraldo", "Campinas", "SP");
        System.out.println(endereco1);
        Colaborador cliente1 = new Cliente("Andre", "1923949394", "andre.andre@gmail.com", endereco1);
        Cliente cliente2 = new Cliente("Jose", "1132932843", "jose.jose@gmail.com", endereco1);
        System.out.println(cliente1);
        System.out.println(cliente2);

        Loja loja = new Loja("Primeira e unica loja!", endereco1);
        loja.addPedido(novoPedido(scanner));


        scanner.close();
    }


    public static Pedido novoPedido(Scanner scanner)  {
        Pedido pedido = new Pedido();
        System.out.println("Novo pedido com id " + pedido.getId() + " criado com sucesso.\nQual cliente fez esse pedido? ");
        System.out.println("1-Mostrar lista de clientes ja cadastrados.\n2-Adicionar novo cliente.");
        int resposta = scanner.nextInt();
        scanner.nextLine();
        switch (resposta) {
            case 1:
                // loja.getClientes
                System.out.println("Seu cliente não está na lista? Para adicionar um novo cliente digite '1'");
                if (scanner.nextInt() == 1) {
                    // loja.addCliente
                }
                scanner.nextLine();
            case 2:
                // loja.addCliente

            default:

        }

        return pedido;
    }
}