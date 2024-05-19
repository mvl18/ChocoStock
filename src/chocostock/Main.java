package chocostock;

import chocostock.auxiliar.CriarTeste;
import chocostock.auxiliar.Endereco;
import chocostock.loja.Loja;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    /*
    * System.out.println("Bem-vindo ao ChocoStock! O doce controle de vendas e estoque.\n");
    *
    * Esta linha foi a primeira linha de código do nosso programa, escrita com carinho.
    * Embora não seja mais necessária para a execução do código, decidimos manter uma
    * recordação dela aqui. Obrigado por fazer parte da nossa jornada!
    */

        Scanner scanner = new Scanner(System.in);

        Endereco endereco = CriarTeste.Endereco();
        Loja loja = new Loja("Primeira e única loja!", endereco);  //Cria loja

        CriarTeste.addTudo(loja);  //RETIRAR

        Sistema sistema = new Sistema(scanner, loja);
        sistema.iniciarSistema();  //Inicia o Sistema (Interface de terminal)

        scanner.close();
    }
}