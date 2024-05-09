package chocostock;

import chocostock.itens.Produto;
import chocostock.itens.produtos.TiposChocolates;

import java.util.ArrayList;
import java.util.Date;

public class Pedido {
    private static int id_pedidos = 100000;
    private int id;
    private int id_cliente;
    private Date data;
    private Date data_entrega;
    private boolean pago;
    private Status status;
    private ArrayList<Produto> produtos;
    private ArrayList<TiposChocolates> produtos_pendentes; //talvez seja bom ter uma quantidade junto, mas ai precisa fazer algo diferente de ArrayList<E>
    private float preco_total;

    public Pedido(int id_cliente, Date data, Date data_entrega, boolean pago, Status status, ArrayList<TiposChocolates> produtos_pendentes, float preco_total) {
        this.id = id_pedidos++;
        this.id_cliente = id_cliente;
        this.data = data; // talvez esse Date n funcione pq ele só pega a data do dia q foi criado, ou seja, da hr q o código esta sendo executado
        this.data_entrega = data_entrega;
        this.pago = pago;
        this.status = status;
        this.produtos = new ArrayList<Produto>();
        this.produtos_pendentes = produtos_pendentes;
        this.preco_total = preco_total;
    }

    public Pedido(int id_cliente, Date data_entrega, boolean pago, Status status, float preco_total) {
        this(id_cliente, new Date(), data_entrega, pago, status, new ArrayList<TiposChocolates>(), preco_total);
    }

    public Pedido() {
        this(-1, new Date(), false, Status.PENDENDE, 0.0F);
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData_entrega() {
        return data_entrega;
    }

    public void setData_entrega(Date data_entrega) {
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

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public ArrayList<TiposChocolates> getProdutos_pendentes() {
        return produtos_pendentes;
    }

    public void setProdutos_pendentes(ArrayList<TiposChocolates> produtos_pendentes) {
        this.produtos_pendentes = produtos_pendentes;
    }

    public float getPreco_total() {
        return preco_total;
    }

    public void setPreco_total(float preco_total) {
        this.preco_total = preco_total;
    }
}
