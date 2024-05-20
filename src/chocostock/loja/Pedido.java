package chocostock.loja;

import chocostock.enums.Status;
import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Iteravel;
import chocostock.itens.produtos.Pendente;
import chocostock.itens.produtos.Produto;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A classe Pedido representa uma ordem de compra realizada por
 * um loja para um cliente.Cada pedido contém informações sobre
 * o cliente, datas relevantes, status de pagamento, produtos
 * incluídos no pedido, produtos pendentes e o preço total. <br>
 * Implementa os métodos "isPago", "addProduto", "removeProduto",
 * "addProduto_pendente", "removeProduto_pendente" e "calculaPrecoTotal".
 */
public class Pedido implements AddRemovivel, Iteravel {
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
}
