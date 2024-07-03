package chocostock;

import chocostock.auxiliar.Endereco;
import chocostock.bancodeDados.Persistencia;
import chocostock.colaboradores.Cliente;
import chocostock.colaboradores.Fornecedor;
import chocostock.enums.*;
import chocostock.interfaceGrafica.InterfaceGrafica;
import chocostock.itens.produtos.Caixa;
import chocostock.itens.produtos.Chocolate;
import chocostock.itens.suprimentos.Embalagem;
import chocostock.itens.suprimentos.Equipamento;
import chocostock.loja.Loja;
import chocostock.interfaces.Escolhivel;
import chocostock.loja.Pedido;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main implements Escolhivel {
    /**
     * Instancia objetos de diversas classes dentro de loja assim que o sistema é inicializado.
     */
    public static void instancia(Loja loja) {
        // MELHORES CLIENTES DE TODOS
        Endereco endereco = new Endereco(2023, "13083898", "Alan Turing", "Cidade Universitária", "Javalândia", Estados.SP);
        loja.addCliente(new Cliente("Ainaras", "182338", "a182338@dac.unicamp.br", endereco));
        loja.addCliente(new Cliente("Douglas", "245202", "d245202@dac.unicamp.br", endereco));
        loja.addCliente(new Cliente("Matheus", "269494", "m269494@dac.unicamp.br", endereco));
        loja.addCliente(new Cliente("Yan", "236363", "y236363@dac.unicamp.br", endereco));

        // FORNECEDORES
        loja.getEstoque().addFornecedor(new Fornecedor("Aliexpress", "", "", new Endereco(), "", "https://www.aliexpress.com"));
        loja.getEstoque().addFornecedor(new Fornecedor("Ricapan", "1434223088", "ricapan.marilia@gmail.com",
                new Endereco(28, "17506190", "Bassan", "Bassan", "Marília", Estados.SP), "02996613000156", ""));
        loja.getEstoque().addFornecedor(new Fornecedor("Marília Embalagens"));
        loja.getEstoque().addFornecedor(new Fornecedor("Castelo dos Doces"));
        loja.getEstoque().addFornecedor(new Fornecedor("Sorvemix"));
        loja.getEstoque().addFornecedor(new Fornecedor("Kalunga", "08007752586", "contato@kalunga.com.br",
                new Endereco(309, "01010010", "Líbero Badaró", "Centro Histórico de São Paulo", "São Paulo", Estados.SP),
                "43283811005976", "https://www.kalunga.com.br/"));

        // EMBALAGENS
        ArrayList<Float> valores = new ArrayList<>(Arrays.asList(1.80f, 3.40f, 3.85f, 4.40f, 6.50f, 4.50f, 12.80f, 9.90f, 5.20f, 4.25f, 4.15f, 7.35f, 9.90f, 18.90f));
        ArrayList<Integer> quantidades = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 50, 300, 300, 50, 100, 1, 1, 10, 50));
        for (TiposEmbalagens embalagem : TiposEmbalagens.values())
            loja.getEstoque().addEmbalagem(new Embalagem(embalagem.getNome(), loja.getEstoque().getFornecedores().get(0),
                    embalagem, valores.remove(0), quantidades.remove(0)));

        // CHOCOLATES
        loja.getEstoque().addProduto(new Chocolate(TiposChocolates.CHOCOLATE_AO_LEITE_INTENSO, 3, 5.50F, null, 1, null, 1, "Bahia"));
        loja.getEstoque().addProduto(new Chocolate(TiposChocolates.CHOCOLATE_INTENSO, 3, 5.50F, null, 1, null, 1, "Bahia"));
        loja.getEstoque().addProduto(new Chocolate(TiposChocolates.CHOCOLATE_AO_LEITE_INTENSO, 5, 5.50F, null, 1, null, 1, "Bahia"));
        loja.getEstoque().addProduto(new Chocolate(TiposChocolates.CHOCOLATE_BRANCO_ACAI_BETERRABA, 5, 5.50F, null, 1, null, 1, "Bahia"));
        loja.getEstoque().addProduto(new Chocolate(TiposChocolates.CHOCOLATE_INTENSO, 5, 5.50F, null, 1, null, 1, "Bahia"));

        // CAIXAS
        loja.getEstoque().addProduto(new Caixa(TiposCaixas.CAIXA_ASORTI_G, 6, 22.25F, null, 1, null, 101010));
        loja.getEstoque().addProduto(new Caixa(TiposCaixas.CAIXA_ASORTI_M, 10, 16.25F, null, 1, null, 101010));
        loja.getEstoque().addProduto(new Caixa(TiposCaixas.CAIXA_ASORTI_P, 20, 12.85F, null, 1, null, 101010));
        loja.getEstoque().addProduto(new Caixa(TiposCaixas.CAIXA_ASORTI_G, 7, 22.25F, null, 1, null, 101010));
        loja.getEstoque().addProduto(new Caixa(TiposCaixas.CAIXA_MINI_TABLETES_70, 3, 11.75F, null, 1, null, 101010));
        loja.getEstoque().addProduto(new Caixa(TiposCaixas.CAIXA_BARRAS_AO_LEITE_CARAMELIZADO_40, 2, 19.15F, null, 1, null, 101010));

        // EQUIPAMENTO
        loja.getEstoque().addEquipamento(new Equipamento("Batedeira", 1, 500.0F, "Philco",
                loja.getEstoque().getFornecedores().get(0), LocalDate.parse("19/05/2034", DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

        // PEDIDOS
        Pedido pedido = new Pedido(100003, LocalDate.parse("11/01/2025", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                true, Status.PENDENTE, 65.0F);
        loja.addPedido(pedido);
        loja.getClientes().get(3).addPedido(pedido.getId());

        pedido = new Pedido(100001, LocalDate.parse("25/01/2025", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                true, Status.FINALIZADO, 89.0F);
        loja.addPedido(pedido);
        loja.getClientes().get(1).addPedido(pedido.getId());

        pedido = new Pedido(100000, LocalDate.parse("03/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                false, Status.PENDENTE, 103.0F);
        loja.addPedido(pedido);
        loja.getClientes().get(0).addPedido(pedido.getId());

        pedido = new Pedido(100002, LocalDate.parse("18/05/2025", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                false, Status.FINALIZADO, 155.0F);
        loja.addPedido(pedido);
        loja.getClientes().get(2).addPedido(pedido.getId());

    }

    public static void main(String[] args) {
    /*
    * System.out.println("Bem-vindo ao ChocoStock! O doce controle de vendas e estoque.\n");
    *
    * Esta linha foi a primeira linha de código do nosso programa, escrita com carinho.
    * Embora não seja mais necessária para a execução do código, decidimos manter uma
    * recordação dela aqui. Obrigado por fazer parte da nossa jornada!!!
    */


        Scanner scanner = new Scanner(System.in);

        System.out.println("\n");

        //Loja loja = new Loja("Primeira e única loja", new Endereco(2023, "13083898", "do Cacau", "Amêndoas Caramelizadas", "Willy Wonka City", Estados.SP));  //Cria loja
        //System.out.println(loja.getDescricao() + " criada!");
        //System.out.println("Venha para o endereço: " + loja.getEndereco());


        Loja lojaCarregada = Persistencia.carregarLoja(scanner);

        //COMENTAR APÓS PRIMEIRA VEZ
        instancia(lojaCarregada);

        new InterfaceGrafica(lojaCarregada);
        Sistema sistema = new Sistema(scanner, lojaCarregada);

        sistema.iniciarSistema();  //Inicia o Sistema (Interface de terminal)

        Persistencia.salvarLoja(lojaCarregada);
        scanner.close();
    }
}