package chocostock;

import chocostock.enums.TiposComplementos;
import chocostock.interfaces.ValidadorInput;
import chocostock.itens.produtos.Chocolate;
import chocostock.loja.Loja;
import chocostock.interfaces.Criavel;

import java.util.ArrayList;
import java.util.Scanner;

import static chocostock.enums.TiposChocolates.CHOCOLATE_AO_LEITE_CARAMELIZADO;
import static chocostock.enums.TiposChocolates.CHOCOLATE_AO_LEITE_INTENSO;
import static chocostock.enums.TiposChocolates.CHOCOLATE_INTENSO;
import static chocostock.enums.TiposChocolates.CHOCOLATE_BRANCO_ACAI_BETERRABA;


/**
 * A classe Sistema é responsável por: <br>
 * - Inicializar o sistema e exibir a mensagem de boas-vindas. <br>
 * - Gerenciar e navegar entre os diferentes menus do sistema: menu inicial, menu de pedidos,
 * menu de estoque e menu de colaboradores. <br>
 * - Delegar tarefas específicas aos métodos correspondentes em resposta às opções selecionadas
 * pelo usuário em cada menu. <br>
 * - Fornecer métodos de interação com a loja (instância da classe Loja) para adicionar pedidos,
 * clientes, fornecedores e ingredientes, e para listar os pedidos, clientes e fornecedores existentes.
 */
public class Sistema implements Criavel, ValidadorInput {

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
                (0) - Encerrar Sistema.""";

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

        opcao = verificaOpcao(input, msg, 0, 3);
        switch(opcao) {
            case 0: menuInicial();
                    break;
            case 1: loja.addPedido(loja.novoPedido(input));
                    menuPedidos();
                    break;
            case 2: System.out.println("--- PEDIDOS ATUAIS ---\n" + loja.listaPedidos());
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
                (X) - Adicionar Produto.
                (2) - Adicionar Ingrediente.
                (X) - Adicionar Embalagem.
                (4) - Status Produtos.
                (5) - Status Ingredientes.
                (X) - Status Embalagens.
                (0) - Voltar para o menu inicial.
                """;

        opcao = verificaOpcao(input, msg, 0, 6);
        switch(opcao){
            case 0: menuInicial();
                    break;
            case 2: loja.getEstoque().addMaterial(loja.estocarIngrediente(input));
                    menuEstoque();
                    break;
            case 4: loja.getEstoque().imprimirProdutos();
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
                (1) - Adicionar Cliente.
                (2) - Adicionar Fornecedor.
                (x) - Adicionar Funcionário.
                (4) - Listar Clientes.
                (5) - Listar Fornecedores.
                (x) - Listar Funcionários.
                (0) - Voltar para o menu inicial.""";

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
