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
        System.out.println("Bem-vindo ao ChocoStock! O doce controle de vendas e estoque.\n" +
                "Digite 'add' para adicionar pedidos, ou 'sair' para encerrar o programa!");

        Scanner scanner = new Scanner(System.in);

        Endereco endereco = CriarTeste.Endereco();
//        System.out.println(endereco);

        // TESTES INICIAIS MARAOLT

        Colaborador cliente1 = CriarTeste.Cliente("André Silva");
        Cliente cliente2 = CriarTeste.Cliente("José");
//        System.out.println(cliente1);
//        System.out.println(cliente2);

        Loja loja = new Loja("Primeira e unica loja!", endereco);
        loja.addCliente(cliente1);
        loja.addCliente(cliente2);

        System.out.println("=========================");
        String comando = scanner.nextLine();
        boolean continuar = true;
        while(continuar) {
            switch(comando) {
                case "add": loja.addPedido(novoPedido(scanner, loja));
                            break;
                case "sair": continuar = false;
                             break;
            }
        }
        System.out.println(loja.getClientes());
        System.out.println(loja.getPedidos());



        scanner.close();
    }
}