package chocostock;

import chocostock.colaboradores.Cliente;
import chocostock.colaboradores.Fornecedor;
import chocostock.colaboradores.Funcionario;
import chocostock.interfaces.Escolhivel;
import chocostock.interfaces.ValidadorInput;
import chocostock.itens.suprimentos.Embalagem;
import chocostock.itens.suprimentos.Ingrediente;
import chocostock.loja.Loja;
import chocostock.interfaces.Criavel;
import chocostock.loja.Pedido;

import java.util.Scanner;


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

    private final Scanner input;
    private String msg;
    private int opcao;
    private final Loja loja;

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

        opcao = ValidadorInput.verificaOpcao(input, msg, 0, 3);
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
                (3) - Atualizar pedido.
                (4) - Cancelar pedido.
                (0) - Voltar para o menu inicial.
                """;

        opcao = ValidadorInput.verificaOpcao(input, msg, 0, 4);
        switch(opcao) {
            case 0: menuInicial();
                    break;
            case 1: Pedido novoPedido = Pedido.novoPedido(input,loja);
                    loja.addPedido(novoPedido);
                    menuPedidos();
                    break;
            case 2: System.out.println("Qual dos seguintes pedidos deseja atualizar? Para voltar digite 'sair'.");
                    System.out.println("Insira o ID ou nome do seu cliente");
                    Pedido pedido = Escolhivel.escolheObjeto(input, loja.getPedidos(), "Pedido inexistente. Digite o ID" +
                            " ou nome de algum pedido listado.", "sair");
                    if (pedido != null) {
                        pedido.atualiza(input, loja);
                    }
                    menuPedidos();
                    break;
            case 3: System.out.println("Não implementado\n");//loja.atualizaPedido();
                    menuPedidos();
                    break;
            case 4: System.out.println("Não implementado\n");//loja.cancelaPedido();
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
                (1) - Adicionar Produto.
                (2) - Adicionar Ingrediente.
                (3) - Adicionar Embalagem.
                (4) - Status Produtos.
                (5) - Status Ingredientes.
                (6) - Status Embalagens.
                (0) - Voltar para o menu inicial.
                """;

        opcao = ValidadorInput.verificaOpcao(input, msg, 0, 6);
        switch(opcao){
            case 0: menuInicial();
                break;
            case 1: loja.getEstoque().addProduto(loja.getEstoque().estocarProduto(input));
                menuEstoque();
                break;
            case 2: Ingrediente novoIngrediente = Ingrediente.estocarIngrediente(input, loja.getEstoque());
                loja.getEstoque().addIngrediente(novoIngrediente);
                menuEstoque();
                break;
            case 3: Embalagem novaEmbalagem = Embalagem.estocarEmbalagem(input, loja.getEstoque());
                loja.getEstoque().addEmbalagem(novaEmbalagem);
                menuEstoque();
                break;
            case 4: loja.getEstoque().imprimirProdutos();
                menuEstoque();
                break;
            case 5: System.out.println(loja.getEstoque().statusIngredientes());
                menuEstoque();
                break;
            case 6: System.out.println(loja.getEstoque().statusEmbalagens());
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
                (3) - Adicionar Funcionário.
                (4) - Listar Clientes.
                (5) - Listar Fornecedores.
                (6) - Listar Funcionários.
                (0) - Voltar para o menu inicial.""";

        opcao = ValidadorInput.verificaOpcao(input, msg, 0, 6);
        switch(opcao){
            case 1: Cliente novoCliente = Cliente.novoCliente(input);
                loja.addCliente(novoCliente);
                menuColaboradores();
                break;
            case 2: Fornecedor novoFornecedor = Fornecedor.novoFornecedor(input);
                loja.getEstoque().addFornecedor(novoFornecedor);
                menuColaboradores();
                break;
            case 3: Funcionario novoFuncionario = Funcionario.novoFuncionario(input);
                loja.addFuncionario(novoFuncionario);
                menuColaboradores();
                break;
            case 4: System.out.println(loja.listaClientes());
                menuColaboradores();
                break;
            case 5: System.out.println(loja.getEstoque().listaFornecedores());
                menuColaboradores();
                break;
            case 6: System.out.println(loja.listaFuncionario());
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
