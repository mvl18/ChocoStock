package chocostock.loja;

import chocostock.auxiliar.Processa;
import chocostock.colaboradores.Cliente;
import chocostock.enums.Status;
import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Escolhivel;
import chocostock.interfaces.Iteravel;
import chocostock.interfaces.ValidadorInput;
import chocostock.itens.produtos.Pendente;
import chocostock.itens.produtos.Produto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A classe Pedido representa uma ordem de compra realizada por
 * um loja para um cliente.Cada pedido contém informações sobre
 * o cliente, datas relevantes, status de pagamento, produtos
 * incluídos no pedido, produtos pendentes e o preço total. <br>
 * Implementa os métodos "isPago", "addProduto", "removeProduto",
 * "addProduto_pendente", "removeProduto_pendente" e "calculaPrecoTotal".
 */
public class Pedido implements AddRemovivel, Iteravel, ValidadorInput, Escolhivel {
    private static int id_pedidos = 100000;
    private final int id;
    private int id_cliente;
    private final LocalDate data;
    private LocalDate data_entrega;
    private boolean pago;
    private Status status;
    private ArrayList<Integer> produtos;
    private ArrayList<Pendente> produtos_pendentes; //talvez seja bom ter uma quantidade junto, mas ai precisa fazer algo diferente de ArrayList<E>
    private float preco_total;

    public Pedido(int id_cliente, LocalDate data, LocalDate data_entrega, boolean pago, Status status, ArrayList<Pendente> produtos_pendentes, float preco_total) {
        this.id = id_pedidos++;
        this.id_cliente = id_cliente;
        this.data = data;
        this.data_entrega = data_entrega;
        this.pago = pago;
        this.status = status;
        this.produtos_pendentes = produtos_pendentes;
        this.produtos = new ArrayList<>();
        this.preco_total = preco_total;
    }

    public Pedido(int id_cliente, LocalDate data_entrega, boolean pago, Status status, float preco_total) {
        this(id_cliente, LocalDate.now(), data_entrega, pago, status, new ArrayList<>(), preco_total);
    }

    public Pedido() {
        this(-1, null, false, Status.PENDENTE, 0.0F);
    }



    public int getId() {
        return id;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public LocalDate getData_entrega() {
        return data_entrega;
    }

    public void setData_entrega(LocalDate data_entrega) {
        this.data_entrega = data_entrega;
    }

    /**
     * Retorna verdadeiro se o pedido foi pago, falso caso contrário.
     */
    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<Integer> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Integer> produtos) {
        this.produtos = produtos;
    }

    public ArrayList<Pendente> getProdutos_pendentes() {
        return produtos_pendentes;
    }

    public void setProdutos_pendentes(ArrayList<Pendente> produtos_pendentes) {
        this.produtos_pendentes = produtos_pendentes;
    }

    public float getPreco_total() {
        return preco_total;
    }

    public void setPreco_total(float preco_total) {
        this.preco_total = preco_total;
    }

    /**
     * Adiciona um produto na lista de produtos do pedido.
     */
    public void addProduto(int id_produto) {
        addObjeto(produtos, id_produto);
    }

    @Override
    public String toString() {
        String out = "PEDIDO " + id +
                "\nID cliente: " + id_cliente +
                "\nRealizado em " + data +
                "\nPrazo de entrega: " + data_entrega +
                "\nStatus: " + status.getNome() +
                "\nProdutos: ";
        out += listaHorizontal(produtos);
        out += "\nProdutos pendentes: " + listaHorizontal(produtos_pendentes) +
        "\nPreco total: R$" + String.format("%.2f", preco_total) + " (" + (pago ? "Pago" : "Não pago") + ")";
        return out + "\n";
    }
  
    /**
     * Calcula o preço total do pedido com base nos produtos adicionados e seus preços.
     */
    public float calculaPrecoTotal(Estoque estoque) {
        float soma_preco = 0;

        // Itera sobre todos os produtos no estoque
        for (Produto produto_estoque : estoque.getProdutos()) {
            // Verifica se o produto no estoque pertence a este pedido
            if (produto_estoque.getId_pedido() == id)
                soma_preco += produto_estoque.getPreco() * produto_estoque.getQuantidade();
        }
        // Retorna o preço total acumulado, ou -1 se nenhum produto for encontrado
        return soma_preco > 0 ? soma_preco : -1;
    }

    /**
     * Cria um novo pedido com as informações fornecidas pelo usuário.
     */
    public Pedido novoPedido(Scanner scanner, Loja loja)  {
        Pedido pedido = new Pedido();

        // CLIENTE
        Cliente cliente = null;
        String msg =   """
                --- NOVO PEDIDO ---
                Selecione uma das opções:
                (1) - Mostrar lista de clientes já cadastrados.
                (2) - Adicionar novo cliente.
                """;

        // Solicita ao usuário que escolha entre mostrar clientes cadastrados ou adicionar um novo cliente
        switch (verificaOpcao(scanner, msg, 1, 2)) {
            case 1:
                System.out.println(loja.listaClientes());
                System.out.println("Seu cliente não está na lista? Para adicionar um novo cliente digite 'novo'.");
                System.out.println("Insira o ID ou nome do seu cliente");
                cliente = escolheObjeto(scanner, loja.getClientes(), "Cliente inexistente. Digite o ID ou nome de algum usuário listado.", "novo");
                if (cliente == null) {
                    cliente = cliente.novoCliente(scanner);
                    loja.addCliente(cliente);
                    pedido.setId_cliente(cliente.getId());
                    break;
                }
                pedido.setId_cliente(cliente.getId());
                cliente.addPedido(pedido.getId());
                break;
            case 2:
                cliente = cliente.novoCliente(scanner);
                loja.addCliente(cliente);
                pedido.setId_cliente(cliente.getId());
                cliente.addPedido(pedido.getId());
                break;
            default:
                System.out.println("Da próxima selecione uma resposta válida! Finalizando programa!");
                break;
        }

        // PRODUTOS_PENDENTES
        pedido.setProdutos_pendentes(loja.escolheProdutos(scanner));
        System.out.println("Produtos adicionados ao pedido: ");
        System.out.println(listaVertical(pedido.getProdutos_pendentes()));

        // Retira os produtos do estoque e atualiza o pedido
        pedido = loja.getEstoque().retiraProdutosEstoque(pedido);

        // Solicita ao usuário a data de entrega do pedido
        pedido.setData_entrega(escolheDataFutura(scanner, "Qual a data de entrega do pedido? Digite uma data futura no formato DD/MM/YYYY: ",
                "Formato de data inválido. Por favor, insira uma data futura no formato DD/MM/YYYY."));
        System.out.println("Data inserida: " + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(pedido.getData_entrega()) + "\n");

        // Calcula o preço total do pedido
        float preco_total = pedido.calculaPrecoTotal(loja.getEstoque());
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
        if (Processa.normalizaString(ValidadorInput.getInput(scanner, "Status do pedido foi definido para PENDENTE. Está correto? Digite 'Sim' ou 'não'. ", "Por favor, digite 'sim' ou 'não'.",
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
        pedido.setPago(Processa.normalizaString(ValidadorInput.getInput(scanner, "O pedido feito já foi pago? Sim OU Não ", "Por favor, insira uma resposta válida. ",
                input -> input.matches("sim|nao|s|n"))).matches("sim|s"));
        System.out.println(pedido.isPago() ? "Pedido foi marcado como pago!" : "Pedido foi marcado como nao pago!");

        return pedido;
    }
}
