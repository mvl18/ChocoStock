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
        Loja loja = new Loja("Primeira e unica loja!", endereco);
        System.out.println(endereco);
        Colaborador cliente1 = new Cliente("Andre", "1923949394", "andre.andre@gmail.com", endereco);
        Cliente cliente2 = new Cliente("Jose", "1132932843", "jose.jose@gmail.com", endereco);
        loja.addCliente((Cliente) cliente1);
        loja.addCliente(cliente2);
        System.out.println(cliente1);
        System.out.println(cliente2);


        for (int i = 0; i < 3; i++) {
            loja.addPedido(loja.novoPedido(scanner, loja));
            System.out.println(loja.listaClientes());
            System.out.println(loja.listaPedidos());
        }

        scanner.close();
    }
}