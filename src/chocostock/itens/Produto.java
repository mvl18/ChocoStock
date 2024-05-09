package chocostock.itens;

import chocostock.Item;
import chocostock.itens.materiais.Embalagem;
import chocostock.itens.produtos.TiposChocolates;

import java.util.Date;

public abstract class Produto extends Item {
    private static int id_produtos = 100000;
    private int id_pedido;
    private TiposChocolates tipo;
    private Date validade;
    private int peso;
    private Embalagem embalagem;

    public Produto(String nome, int quantidade, float preco, TiposChocolates tipo, Date validade, int peso, Embalagem embalagem) {
        super(nome, quantidade, preco);
        this.setId(id_produtos++);
        this.tipo = tipo;
        this.validade = validade;
        this.peso = peso;
        this.embalagem = embalagem;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public TiposChocolates getTipo() {
        return tipo;
    }

    public void setTipo(TiposChocolates tipo) {
        this.tipo = tipo;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Embalagem getEmbalagem() {
        return embalagem;
    }

    public void setEmbalagem(Embalagem embalagem) {
        this.embalagem = embalagem;
    }
}
