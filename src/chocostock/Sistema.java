package chocostock;

import chocostock.interfaces.ValidadorInput;
import chocostock.loja.Loja;
import chocostock.interfaces.Criavel;

import java.util.Scanner;

public class Sistema implements ValidadorInput {

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
                --- Bem-vindo ao ChocoStock! ---
                O doce controle de vendas e estoque!
                """;
        System.out.println(msg);
        menuInicial();
    }

    public void menuInicial(){
        msg =   """
                --- MENU INICIAL ---
                Selecione uma das opções:
                (1) - Menu Pedidos.
                (2) - Menu Estoque.
                (3) - Menu Colaboradores.
                (0) - Encerrar Sistema.
                """;
//        System.out.println(msg);
//        opcao = input.nextInt();
//        input.nextLine();
        opcao = verificaOpcao(input, msg, 0, 3);
        switch(opcao) {
            case 0: finalizarSistema();
                    break;
            case 1: menuPedidos();
                    break;
            case 2: menuEstoque();
                    break;
            case 3: menuColaboradores();
                    break;
            default: System.out.println("Opção inválida.");
                     menuInicial();
                     break;
        }
    }

    public void menuPedidos(){
        msg = """
                --- MENU PEDIDOS ---
                Selecione uma opção:
                (1) - Novo pedido.
                (2) - Listar pedidos.
                (x) - Atualizar pedido.
                (0) - Voltar para o menu inicial.""";
//        System.out.println(msg);
//        opcao = input.nextInt();
//        input.nextLine();
        opcao = verificaOpcao(input, msg, 0, 3);
        switch(opcao) {
            case 0: menuInicial();
                    break;
            case 1: loja.addPedido(loja.novoPedido(input, loja));
                    menuPedidos();
                    break;
            case 2: System.out.println("Pedidos Atuais:\n" + loja.listaPedidos());
                    menuPedidos();
                    break;
            case 3: System.out.println("Não implementado\n");//loja.atualizaPedido();
                    menuPedidos();
                    break;
            default: System.out.println("Opção inválida. Voltando para o MENU INICIAL.");
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
//        System.out.println(msg);
//        opcao = input.nextInt();
//        input.nextLine();
        opcao = verificaOpcao(input, msg, 1, 6);
        switch(opcao){
            case 2: loja.getEstoque().addMaterial(loja.estocarIngrediente(input));
                    menuEstoque();
                    break;
            case 5: System.out.println(loja.getEstoque().statusIngredientes());
                    menuEstoque();
                    break;
            default: System.out.println("Opção inválida. Voltando para o MENU INICIAL.");
                     menuInicial();
                     break;
        }
    }

    public void menuColaboradores(){
        msg = """
                --- MENU COLABORADORES ---
                (1) - Adicionar Cliente
                (2) - Adicionar Fornecedor
                (x) - Adicionar Funcionário
                (4) - Listar Clientes
                (5) - Listar Fornecedores
                (x) - Listar Funcionario
                (0) - Voltar para o menu inicial.""";
//        System.out.println(msg);
//        opcao = input.nextInt();
//        input.nextLine();
        opcao = verificaOpcao(input, msg, 0, 6);
        switch(opcao){
            case 1: loja.addCliente(loja.novoCliente(input));
                    menuColaboradores();
                    break;
            case 2: loja.addFornecedor(loja.novoFornecedor(input));
                    menuColaboradores();
                    break;
            case 4: System.out.println(loja.listaClientes());
                    menuColaboradores();
                    break;
            case 5: System.out.println(loja.listaFornecedores());
                    menuColaboradores();
                    break;
            case 0: menuInicial();
                    break;
            default: System.out.println("Opção inválida. Voltando para o MENU INICIAL.");
                     menuInicial();
                     break;
        }
    }

    public void finalizarSistema() {
        System.out.println("--- Sistema Desligado ---");
    }

}
