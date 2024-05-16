package chocostock;

import chocostock.auxiliar.CriarTeste;
import chocostock.auxiliar.Endereco;
import chocostock.colaboradores.Cliente;
import chocostock.colaboradores.Colaborador;
import chocostock.loja.Loja;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Bem-vindo ao ChocoStock! O doce controle de vendas e estoque.\n"); // primeira linha de codigo NAO APAGAR
        Scanner scanner = new Scanner(System.in);

        Endereco endereco = CriarTeste.Endereco();
        Colaborador cliente1 = CriarTeste.Cliente("André Silva");
        Cliente cliente2 = CriarTeste.Cliente("José");
        Loja loja = new Loja("Primeira e única loja!", endereco);
        loja.addCliente((Cliente) cliente1);
        loja.addCliente(cliente2);

        //TESTE DE SISTEMA DOUGLAS
        Sistema sistema = new Sistema(scanner, loja);
        System.out.println(loja.listaPedidos());
        sistema.iniciarSistema();

        scanner.close();
    }
}