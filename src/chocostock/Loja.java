package chocostock;

import chocostock.colaboladores.Colaborador;

import java.util.ArrayList;

public class Loja {
    private String descricao;
    private Endereco endereco;
    private ArrayList<Pedido> pedidos;
    private Estoque estoque;
    private ArrayList<Colaborador> colaboradors;

    public Loja(String descricao, Endereco endereco, ArrayList<Pedido> pedidos, Estoque estoque, ArrayList<Colaborador> colaboradors) {
        this.descricao = descricao;
        this.endereco = endereco;
        this.pedidos = pedidos;
        this.estoque = estoque;
        this.colaboradors = colaboradors;
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

    public ArrayList<Colaborador> getColaboradors() {
        return colaboradors;
    }

    public void setColaboradors(ArrayList<Colaborador> colaboradors) {
        this.colaboradors = colaboradors;
    }
}
