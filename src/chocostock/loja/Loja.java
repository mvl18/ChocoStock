package chocostock.loja;

import chocostock.auxiliar.Endereco;
import chocostock.auxiliar.Processa;
import chocostock.colaboradores.Funcionario;
import chocostock.enums.*;
import chocostock.interfaces.*;
import chocostock.colaboradores.Cliente;
import chocostock.auxiliar.Verifica;
import chocostock.itens.produtos.Pendente;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * A classe Loja representa uma loja que gerencia clientes,
 * funcionários,fornecedores, pedidos e estoque de produtos. <br>
 * Implementa os métodos "escolheProdutos", "selecionaProduto",
 * "selecionaCaixa", "selecionaBarra", "estocarIngrediente",
 * "novoCliente", "novoPedido".
 */
public class Loja implements AddRemovivel, Criavel, Escolhivel, Iteravel, ValidadorInput {
    private final String descricao;
    private final Endereco endereco;
    private final ArrayList<Pedido> pedidos;
    private final Estoque estoque;
    private static final ArrayList<Cliente> clientes = new ArrayList<>();
    private final ArrayList<Funcionario> funcionarios;

    public Loja(String descricao, Endereco endereco) {
        this.descricao = descricao;
        this.endereco = endereco;
        this.pedidos = new ArrayList<>();
        this.estoque =  new Estoque();
        this.funcionarios = new ArrayList<>();
    }

    // DESCRIÇÃO
    public String getDescricao() {
        return descricao;
    }

    // ENDEREÇO
    public Endereco getEndereco() {
        return endereco;
    }

    // ESTOQUE
    public Estoque getEstoque() {
        return estoque;
    }

    // CLIENTES
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void addPedido(Pedido pedido) {
        addObjeto(pedidos, pedido);
    }

    public void addCliente(Cliente cliente) {
        addObjeto(clientes, cliente);
    }

    public String listaClientes() {
        return listaVertical(clientes);
    }

    public String listaPedidos() {
        return listaVertical(pedidos);
    }

    /**
     *  Método privado para escolher produtos para um pedido, usando um scanner para entrada do usuário.
     */
    private ArrayList<Pendente> escolheProdutos(Scanner scanner) {
        ArrayList<Pendente> produtos_escolhidos = new ArrayList<>();
        while (true) {
            switch (verificaOpcao(scanner, new String[]{"PRODUTOS DO PEDIDO", "Adicionar produto ao pedido.", "Listar produtos adicionados.", "Finalizar escolhas."}, 0)) {
                case 1: produtos_escolhidos.add(selecionaProduto(scanner));
                    break;
                case 2:
                    System.out.print(listaVertical(produtos_escolhidos));
                    break;
                default:
                    produtos_escolhidos.removeIf(Objects::isNull);
                    return produtos_escolhidos;
            }
        }
    }

    /**
     * Método privado para selecionar um tipo de produto, usando um scanner para entrada do usuário.
     */
    private Pendente selecionaProduto(Scanner scanner) {
        Pendente produtoPendente = new Pendente();
        return switch (verificaOpcao(scanner, new String[]{"TIPOS DE PRODUTO", "Barra.", "Caixa.", "Voltar."}, 0)) {
            case 1 -> selecionaBarra(scanner, produtoPendente);
            case 2 -> selecionaCaixa(scanner, produtoPendente);
            default -> null; // Se o usuario digitar 0
        };
    }

    /**
     * Permite ao usuário selecionar um tipo de caixa e a quantidade desejada.
     */
    private Pendente selecionaCaixa(Scanner scanner, Pendente produtoPendente) {
        // Lista todos os tipos de caixas disponíveis
        for (TiposCaixas tipo : TiposCaixas.values()) {
            System.out.println("(" + tipo.getId() + ") - " + tipo.getNome());
        }
        // Solicita ao usuário que selecione um tipo de caixa
        produtoPendente.setNome(escolheObjeto(scanner, TiposCaixas.values(), "Por favor selecione um tipo válido.", "obrigatorio").getNome());
        // Solicita ao usuário a quantidade desejada da caixa selecionada
        produtoPendente.setQuantidade(Integer.parseInt(getInput(scanner, "Quantidade de " + produtoPendente.getNome() + ": ",
                "Coloque um número inteiro maior que 0", Verifica::isNatural)));

        return produtoPendente;
    }

    /**
     * Permite ao usuário selecionar um tipo de barra de chocolate e complementos,
     * além da quantidade desejada.
     */
    private Pendente selecionaBarra(Scanner scanner, Pendente produtoPendente) {
        // Lista todos os tipos de chocolates disponíveis
        for (TiposChocolates tipo : TiposChocolates.values()) {
            System.out.println("(" + tipo.getId() + ") - " + tipo.getNome());
        }
        // Solicita ao usuário que selecione um tipo de chocolate
        produtoPendente.setNome(escolheObjeto(scanner, TiposChocolates.values(), "Por favor selecione um tipo válido.", "obrigatorio").getNome());

        // Lista todos os tipos de complementos disponíveis
        for (TiposComplementos complemento : TiposComplementos.values()) {
            System.out.println("(" + complemento.getId() + ") - " + complemento.getNome());
        }
        // Opção para sair da seleção de complementos
        System.out.println("(0) - Sair");
        System.out.println("Selecione até " + TiposComplementos.values().length + " complementos diferentes.");
        // Solicita ao usuário que selecione complementos para a barra de chocolate
        produtoPendente.setComplementos(escolheObjeto(scanner, TiposComplementos.values(),
                "Por favor selecione um complemento válido.",
                "0", TiposComplementos.values().length));
        produtoPendente.getComplementos().removeIf(Objects::isNull);
        // Solicita ao usuário a quantidade desejada da barra de chocolate selecionada
        produtoPendente.setQuantidade(Integer.parseInt(getInput(scanner, "Quantidade de " + produtoPendente.getNome() + ": ",
                "Coloque um número inteiro maior que 0", Verifica::isNatural)));

        return produtoPendente;

    }


    /**
     * Permite ao usuário cadastrar um novo cliente.
     */
    public Cliente novoCliente(Scanner scanner) {
        Cliente cliente = new Cliente();
        System.out.println("Cadastrando novo cliente: ");
        // Solicitação do nome do cliente
        cliente.setNome(getInput(scanner, "Nome do cliente: ", "Nome inválido.", Verifica::isNome));
        // Solicitação do telefone do cliente
        cliente.setTelefone(getInput(scanner, "Telefone do cliente: ", "Insira um número válido, não esqueça o DDD!",
                Verifica::isTelefone).replaceAll("\\D", ""));
        // Solicitação do email do cliente
        cliente.setEmail(getInput(scanner, "Email do cliente: ", "Insira um email válido!", Verifica::isEmail));
        // Solicitação do endereço do cliente
        System.out.println("Criando endereço: ");
        cliente.setEndereco(criaEndereco(scanner));
        System.out.println("Novo cliente adicionado: " + cliente);

        return cliente;
    }
    /**
     * Cria um novo pedido com as informações fornecidas pelo usuário.
     */
    public Pedido novoPedido(Scanner scanner)  {
        Pedido pedido = new Pedido();

        // CLIENTE
        Cliente cliente;
        String msg =   """
                --- NOVO PEDIDO ---
                Selecione uma das opções:
                (1) - Mostrar lista de clientes já cadastrados.
                (2) - Adicionar novo cliente.
                """;

        // Solicita ao usuário que escolha entre mostrar clientes cadastrados ou adicionar um novo cliente
        switch (verificaOpcao(scanner, msg, 1, 2)) {
            case 1:
                System.out.println(listaClientes());
                System.out.println("Seu cliente não está na lista? Para adicionar um novo cliente digite 'novo'.");
                System.out.println("Insira o ID ou nome do seu cliente");
                cliente = escolheObjeto(scanner, getClientes(), "Cliente inexistente. Digite o ID ou nome de algum usuário listado.", "novo");
                if (cliente == null) {
                    cliente = novoCliente(scanner);
                    addCliente(cliente);
                    pedido.setId_cliente(cliente.getId());
                    break;
                }
                pedido.setId_cliente(cliente.getId());
                cliente.addPedido(pedido.getId());
                break;
            case 2:
                cliente = novoCliente(scanner);
                addCliente(cliente);
                pedido.setId_cliente(cliente.getId());
                cliente.addPedido(pedido.getId());
                break;
            default:
                System.out.println("Da próxima selecione uma resposta válida! Finalizando programa!");
                break;
        }

        // PRODUTOS_PENDENTES
        pedido.setProdutos_pendentes(escolheProdutos(scanner));
        System.out.println("Produtos adicionados ao pedido: ");
        System.out.println(listaVertical(pedido.getProdutos_pendentes()));

        // Retira os produtos do estoque e atualiza o pedido
        pedido = estoque.retiraProdutosEstoque(pedido);

        // Solicita ao usuário a data de entrega do pedido
        pedido.setData_entrega(escolheDataFutura(scanner, "Qual a data de entrega do pedido? Digite uma data futura no formato DD/MM/YYYY: ",
                "Formato de data inválido. Por favor, insira uma data futura no formato DD/MM/YYYY."));
        System.out.println("Data inserida: " + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(pedido.getData_entrega()) + "\n");

        // Calcula o preço total do pedido
        float preco_total = pedido.calculaPrecoTotal(estoque);
        if (preco_total == -1) {
            System.out.println("Preço total do pedido Indeterminado, nenhum dos produtos pedidos está no estoque.");
        } else {
            pedido.setPreco_total(preco_total);
            System.out.println("Preço total dos produtos do pedido que estão no estoque ficou: R$" + pedido.getPreco_total() + ".");
            System.out.println("Os seguintes produtos ainda estão pendentes e não foram contabilizados no preço total: "); // implementar melhor futuramente
            for (Pendente produtos_pendente : pedido.getProdutos_pendentes()) {
                System.out.println(produtos_pendente.getNome() + " (quantidade: " + produtos_pendente.getQuantidade() + ")");
            }
        }

        // Pergunta ao usuário se deseja modificar o status do pedido, inicialmente definido como PENDENTE
        if (Processa.normalizaString(getInput(scanner, "Status do pedido foi definido para PENDENTE. Está correto? Digite 'Sim' ou 'não'. ", "Por favor, digite 'sim' ou 'não'.",
                input -> input.matches("sim|nao|s|n"))).matches("sim|s")) {
            pedido.setStatus(Status.PENDENTE);
        } else {
            System.out.println("Escolha um status dentre os abaixo:");
            for (Status status : Status.values()) {
                System.out.println("(" + status.getId() + ") - " + status.getNome());
            }
            pedido.setStatus(escolheObjeto(scanner, Status.values(), "Status inválido. Digite um número válido ou o nome do status.", "obrigatorio"));
        }
        System.out.println("O status do pedido " + pedido.getId() + " foi definido como " + pedido.getStatus() + ".");

        // Pergunta ao usuário se o pedido já foi pago
        pedido.setPago(Processa.normalizaString(getInput(scanner, "O pedido feito já foi pago? Sim OU Não ", "Por favor, insira uma resposta válida. ",
                input -> input.matches("sim|nao|s|n"))).matches("sim|s"));
        System.out.println(pedido.isPago() ? "Pedido foi marcado como pago!" : "Pedido foi marcado como nao pago!");

        return pedido;
    }
}
