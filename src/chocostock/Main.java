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
      
        Colaborador cliente1 = CriarTeste.Cliente("André Silva");
        Cliente cliente2 = CriarTeste.Cliente("José");
        Loja loja = new Loja("Primeira e única loja!", endereco);
        loja.addCliente((Cliente) cliente1);
        loja.addCliente(cliente2);

        // Adicionar fornecedores por padrão by Yan
        loja.addFornecedor(new Fornecedor("Aliexpress", "", "", new Endereco(), "", "https://www.aliexpress.com"));
        loja.addFornecedor(new Fornecedor("Ricapan", "1434223088", "ricapan.marilia@gmail.com",
                new Endereco(28, "17506190", "Bassan", "Bassan", "Marília", Estados.SP), "02996613000156", ""));
        loja.addFornecedor(new Fornecedor("Marília Embalagens"));
        loja.addFornecedor(new Fornecedor("Castelo dos Doces"));
        loja.addFornecedor(new Fornecedor("Sorvemix"));
        loja.addFornecedor(new Fornecedor("Kalunga"));

        //TESTE DE SISTEMA DOUGLAS
        Sistema sistema = new Sistema(scanner, loja);
        System.out.println(loja.listaPedidos());
        sistema.iniciarSistema();

        scanner.close();
    }
}