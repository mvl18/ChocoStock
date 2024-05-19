package chocostock;

import chocostock.auxiliar.CriarTeste;
import chocostock.auxiliar.Endereco;
import chocostock.colaboradores.Cliente;
import chocostock.colaboradores.Colaborador;
import chocostock.colaboradores.Fornecedor;
import chocostock.enums.Estados;
import chocostock.enums.TiposCaixas;
import chocostock.itens.produtos.Caixa;
import chocostock.itens.produtos.Chocolate;
import chocostock.loja.Loja;

import java.util.Scanner;

import static chocostock.enums.TiposChocolates.*;
import static chocostock.enums.TiposChocolates.CHOCOLATE_INTENSO;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Bem-vindo ao ChocoStock! O doce controle de vendas e estoque.\n"); // primeira linha de codigo NAO APAGAR
        Scanner scanner = new Scanner(System.in);

        Endereco endereco = CriarTeste.Endereco();
        Loja loja = new Loja("Primeira e única loja!", endereco);

        CriarTeste.addTudo(loja);

        //TESTE DE SISTEMA DOUGLAS
        Sistema sistema = new Sistema(scanner, loja);

        loja.getEstoque().addProduto(new Chocolate(CHOCOLATE_AO_LEITE_INTENSO, 3, 5.50F, null, 1, null, 1, "Bahia"));
        loja.getEstoque().addProduto(new Chocolate(CHOCOLATE_INTENSO, 3, 5.50F, null, 1, null, 1, "Bahia"));
        loja.getEstoque().addProduto(new Chocolate(CHOCOLATE_AO_LEITE_INTENSO, 5, 5.50F, null, 1, null, 1, "Bahia"));
        loja.getEstoque().addProduto(new Chocolate(CHOCOLATE_BRANCO_ACAI_BETERRABA, 5, 5.50F, null, 1, null, 1, "Bahia"));
        loja.getEstoque().addProduto(new Chocolate(CHOCOLATE_INTENSO, 5, 5.50F, null, 1, null, 1, "Bahia"));
        loja.getEstoque().addProduto(new Caixa(TiposCaixas.CAIXA_ASORTI_G, 6, 101010, 22.25F, null, 1, null));
        loja.getEstoque().addProduto(new Caixa(TiposCaixas.CAIXA_ASORTI_M, 10, 101010, 16.25F, null, 1, null));
        loja.getEstoque().addProduto(new Caixa(TiposCaixas.CAIXA_ASORTI_P, 20, 101010, 12.85F, null, 1, null));
        loja.getEstoque().addProduto(new Caixa(TiposCaixas.CAIXA_ASORTI_G, 7, 101010, 22.25F, null, 1, null));
        loja.getEstoque().addProduto(new Caixa(TiposCaixas.CAIXA_MINI_TABLETES_70, 3, 101010, 11.75F, null, 1, null));
        loja.getEstoque().addProduto(new Caixa(TiposCaixas.CAIXA_BARRAS_AO_LEITE_CARAMELIZADO_40, 2, 101010, 19.15F, null, 1, null));

        loja.getEstoque().imprimirProdutos();
        System.out.println(loja.listaPedidos());
        sistema.iniciarSistema();

        scanner.close();
    }
}