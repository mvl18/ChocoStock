package chocostock.itens.produtos;

import chocostock.itens.Item;
import chocostock.itens.materiais.Embalagem;

import java.time.LocalDate;

public abstract class Produto extends Item {
    private static int id_produtos = 100000;
    private int id_pedido;
    private LocalDate validade;
    private int peso;
    private Embalagem embalagem;

    public Produto(String nome, int quantidade, float preco, LocalDate validade, int peso, Embalagem embalagem) {
        super(nome, quantidade, preco);
        this.setId(id_produtos++);
        this.id_pedido = -1;
        this.validade = validade;
        this.peso = peso;
        this.embalagem = embalagem;
    }

    public Produto() {
        this(null, 0, 0, null, 0, null);
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
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
