package chocostock.loja;

import chocostock.enums.Status;
import chocostock.interfaces.AddRemovivel;
import chocostock.itens.produtos.Produto;
import chocostock.itens.produtos.Pendente;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pedido implements AddRemovivel {
    private static int id_pedidos = 100000;
    private int id;
    private int id_cliente;
    private LocalDate data;
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
        this.produtos = new ArrayList<Integer>();
        this.preco_total = preco_total;
    }

    public Pedido(int id_cliente, LocalDate data_entrega, boolean pago, Status status, float preco_total) {
        this(id_cliente, LocalDate.now(), data_entrega, pago, status, new ArrayList<Pendente>(), preco_total);
    }

    public Pedido() {
        this(-1, null, false, Status.PENDENTE, 0.0F);
    }



    public int getId() {
        return id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDate getData_entrega() {
        return data_entrega;
    }

    public void setData_entrega(LocalDate data_entrega) {
        this.data_entrega = data_entrega;
    }

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

    public boolean addProduto(int posicao, int id_produto) {
        return addObjeto(posicao, produtos, id_produto);
    }

    public boolean addProduto(int id_produto) {
        return addObjeto(produtos, id_produto);
    }
    public boolean removeProduto(int id_produto) {
        return removeObjeto(produtos, id_produto);
    }

    public boolean addProduto_pendente(Pendente pendente) {
        return addObjeto(produtos_pendentes, pendente);
    }
    public boolean removeProduto_pendente(Pendente pendente) {
        return removeObjeto(produtos_pendentes, pendente);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", id_cliente=" + id_cliente +
                ", data=" + data +
                ", data_entrega=" + data_entrega +
                ", pago=" + pago +
                ", status=" + status +
                ", produtos=" + produtos +
                ", produtos_pendentes=" + produtos_pendentes +
                ", preco_total=" + preco_total +
                '}';
    }

    public float calculaPrecoTotal() {
        return 0.0F; // fazer
    }
}
