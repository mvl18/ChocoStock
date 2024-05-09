package chocostock;

import chocostock.colaboladores.Colaborador;
import chocostock.itens.Equipamento;
import chocostock.itens.Material;

import java.util.ArrayList;

public class Loja {
    private String descricao;
    private Endereco endereco;
    private ArrayList<Pedido> pedidos;
    private Estoque estoque;
    private ArrayList<Colaborador> colaboradores;

    public Loja(String descricao, Endereco endereco) {
        this.descricao = descricao;
        this.endereco = endereco;
        this.pedidos = new ArrayList<Pedido>();
        this.estoque =  new Estoque(new ArrayList<Item>(), new ArrayList<Item>(), new ArrayList<Item>());
        this.colaboradores = new ArrayList<Colaborador>();
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public ArrayList<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(ArrayList<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }

    public boolean addPedido(Pedido pedido) {
        if (!this.pedidos.contains(pedido)) {
            this.getPedidos().add(pedido);
            return true;
        }

        return false;

    }

    public boolean removePedido(Pedido pedido) {
        if (this.pedidos.contains(pedido)) {
            this.getPedidos().remove(pedido);
            return true;
        }

        return false;
    }
}
