package chocostock;

import chocostock.colaboladores.Cliente;
import chocostock.colaboladores.Colaborador;
import chocostock.colaboladores.Funcionario;
import jdk.jshell.execution.JdiExecutionControl;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bem-vindo ao ChocoStock! O doce controle de vendas e estoque.");

        Scanner scanner = new Scanner(System.in);

        // Exemplo de instanciação de endereço e de mudança de estado
        Endereco endereco = new Endereco(34,"1403128", "Pitágoras", "Limoeiro", "Gotham", Estados.SP);
        System.out.println(endereco);
        endereco.setEstado(Estados.AC);
        System.out.println(endereco);

        // TESTES INICIAIS MARAOLT
        System.out.println(endereco);
        Colaborador cliente1 = new Cliente("Andre", "1923949394", "andre.andre@gmail.com", endereco);
        Cliente cliente2 = new Cliente("Jose", "1132932843", "jose.jose@gmail.com", endereco);
        System.out.println(cliente1);
        System.out.println(cliente2);


        Loja loja = new Loja("Primeira e unica loja!", endereco);
        loja.addPedido(novoPedido(scanner, loja));
        loja.addPedido(novoPedido(scanner, loja));
        loja.addPedido(novoPedido(scanner, loja));
        System.out.println(loja.getClientes());
        System.out.println(loja.getPedidos());



        scanner.close();
    }


    public static Pedido novoPedido(Scanner scanner, Loja loja)  {
        Pedido pedido = new Pedido();
        System.out.println("Novo pedido com id " + pedido.getId() + " criado com sucesso.\nQual cliente fez esse pedido? ");
        // CLIENTE
        System.out.println("1-Mostrar lista de clientes ja cadastrados.\n2-Adicionar novo cliente.");
        int resposta = scanner.nextInt();
        scanner.nextLine();
        switch (resposta) {
            case 1:
                System.out.println(loja.getClientes());
                // escolheObjeto(scanner, loja.getClientes());
                System.out.println("Seu cliente não está na lista? Para adicionar um novo cliente digite '1'");
                if (scanner.nextInt() == 1) {
                    Cliente cliente = novoCliente(scanner);
                    loja.addCliente(cliente);
                    pedido.setId_cliente(cliente.getId());
                }
                scanner.nextLine();
                break;
            case 2:
                Cliente cliente = novoCliente(scanner);
                loja.addCliente(cliente);
                pedido.setId_cliente(cliente.getId());
                break;
            default:
                System.out.println("Da proxima selecione uma resposta valida! Finalizando programa!");
                break;
        }
        // DATA_ENTREGA
        System.out.println("Qual a data de entrega do pedido? ");
        // escolheData();
        System.out.println("Ainda nao implementado");
        // PAGO OU N
        System.out.println("O pedido feito ja foi pago? Sim OU Nao");
        boolean pago = scanner.nextBoolean();
        scanner.nextLine();
        pedido.setPago(pago);
        if (pago) {
            System.out.println("Pedido foi marcado como pago!");
        } else {
            System.out.println("Pedido foi marcado como nao pago!");
        }

        // STATUS
        for (Status status : Status.values()) {
            System.out.println(status);
        }
        System.out.println("Qual o status do pedido dentre os acima? ");
        String status_resp = scanner.nextLine();
        for (Status status : Status.values()) {
            if (status.getNome().equals(status_resp)) {
                pedido.setStatus(status);
                break;
            }
        }
        System.out.println("O status do seu pedido foi definido para " + pedido.getStatus().getNome() + ".");
        // PRODUTOS_PENDENTES
        System.out.println("Selecione qual produto precisa ser adicionado ao pedido. ");

        // PRECO TOTAL

        return pedido;
    }

    public static Cliente novoCliente(Scanner scanner) {
        Cliente cliente = new Cliente();



        return cliente;
    }
}