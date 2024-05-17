package chocostock;

import chocostock.auxiliar.CriarTeste;
import chocostock.auxiliar.Endereco;
import chocostock.colaboradores.Cliente;
import chocostock.colaboradores.Colaborador;
import chocostock.colaboradores.Fornecedor;
import chocostock.enums.Estados;
import chocostock.loja.Loja;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Bem-vindo ao ChocoStock! O doce controle de vendas e estoque.\n"); // primeira linha de codigo NAO APAGAR
        Scanner scanner = new Scanner(System.in);

        Endereco endereco = CriarTeste.Endereco();
        Loja loja = new Loja("Primeira e única loja!", endereco);


        CriarTeste.addTudo(loja);

        //TESTE DE SISTEMA DOUGLAS
        Sistema sistema = new Sistema(scanner, loja);
        System.out.println(loja.listaPedidos());
        sistema.iniciarSistema();

        scanner.close();
    }
}