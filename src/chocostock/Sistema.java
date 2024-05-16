package chocostock;

import chocostock.loja.Loja;

import java.util.Scanner;

public class Sistema {

    private Scanner input;
    private String msg;
    private int opcao;
    private Loja loja;

    Sistema(Scanner input, Loja loja){
        this.input = input;
        this.loja = loja;
        this.msg = "";
        this.opcao = -1;
    }

    public void iniciarSistema(){
        msg =  """
                --- Bem-vinfo ao ChocoStock! ---
                O doce controle de vendas e estoque!
                """;
        System.out.println(msg);
        menuInicial();
    }

    public void menuInicial(){
        msg =   """
                --- MENU INICIAL ---
                Selecione uma das opcoes:
                (1) - Menu Pedidos.
                (2) - Menu Estoque.
                (3) - Menu Colaboradores.
                (0) - Encerrar Sistema.
                """;
        System.out.println(msg);
        opcao = input.nextInt();
        input.nextLine();
        switch(opcao) {
            case 0: finalizarSistema();
                    break;
            case 1: menuPedidos();
                    break;
            case 2: menuEstoque();
                    break;
            case 3: menuColaboradores();
                    break;
            default: System.out.println("Opcao invalida.");
                     menuInicial();
                     break;
        }
    }

    public void menuPedidos(){
        msg = """
                --- MENU PEDIDOS ---
                Selecione uma opcao:
                (1) - Novo pedido.
                (2) - Listar pedidos.
                (3) - Atualizar pedido.
                (0) - Voltar para o menu inicial.""";
        System.out.println(msg);
        opcao = input.nextInt();
        input.nextLine();
        switch(opcao) {
            case 0: menuInicial();
                    break;
            case 1: loja.addPedido(loja.novoPedido(input, loja));
                    menuPedidos();
                    break;
            case 2: System.out.println("Pedidos Atuais:\n" + loja.listaPedidos());
                    menuPedidos();
                    break;
            case 3: System.out.println("Nao implementado\n");//loja.atualizaPedido();
                    menuPedidos();
                    break;
            default: System.out.println("Opcao invalida. Voltando para o MENU INICIAL.");
                     menuInicial();
                     break;
        }
    }

    public void menuEstoque() {
        msg = """
                --- MENU ESTOQUE ---
                (X) - Adicionar Produto
                (2) - Adicionar Ingrediente
                (X) - Adicionar Embalagem
                (X) - Status Produto
                (5) - Status Ingredientes
                (X) - Status Embalagens
                """;
        System.out.println(msg);
        opcao = input.nextInt();
        input.nextLine();
        switch(opcao){
            case 2: loja.getEstoque().addMaterial(loja.novoIngrediente(input));
                    menuEstoque();
                    break;
            case 5: System.out.println(loja.getEstoque().statusIngredientes());
                    menuEstoque();
                    break;
            default: System.out.println("Opcao invalida. Voltando para o MENU INICIAL.");
                     menuInicial();
                     break;
        }
    }

    public void menuColaboradores(){
        msg = """
                --- MENU COLABORADORES ---
                (x) - Adicionar Cliente
                (x) - Adicionar Fornecedor
                (x) - Adicionar Funcionario
                (x) - Listar Clientes
                (x) - Listar Fornecedor
                (x) - Listar Funcionario""";
        System.out.println(msg);
        System.out.println("Nao implementado. Voltando para o MENU INICIAL.");
        menuInicial();
    }

    public void finalizarSistema() {
        System.out.println("--- Sistema Desligado ---");
    }

}
