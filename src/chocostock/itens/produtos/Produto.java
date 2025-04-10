package chocostock.itens.produtos;

import chocostock.colaboradores.Fornecedor;
import chocostock.enums.TiposEmbalagens;
import chocostock.interfaces.Identificavel;
import chocostock.itens.Item;

import java.time.LocalDate;

/**
 * A classe Produto é uma subclasse de Item que representa
 * um produto vendido pela loja.
 */
public class Produto extends Item implements Identificavel {
    private static int id_produtos = 100000;
    private final int id;
    private int id_pedido;
    private LocalDate validade;
    private int peso;
    private TiposEmbalagens embalagem;

    public Produto(String nome, int quantidade, float preco, LocalDate validade, int peso, TiposEmbalagens embalagem, Fornecedor fornecedor) {
        super(nome, quantidade, preco, fornecedor);
        this.id = id_produtos++;
        this.id_pedido = -1;
        this.validade = validade;
        this.peso = peso;
        this.embalagem = embalagem;
    }

    public Produto(String nome, int quantidade, float preco, LocalDate validade, int peso, TiposEmbalagens embalagem) {
        super(nome, quantidade, preco);
        this.id = id_produtos++;
        this.id_pedido = -1;
        this.validade = validade;
        this.peso = peso;
        this.embalagem = embalagem;
    }

    public Produto() {
        this(null, 0, 0, null, 0, null);
    }

    public int getId() {
        return id;
    }

    public static int getIdProduto() {
        return id_produtos;
    }

    public static void setIdProduto(int id) {
        id_produtos = id;
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

    public TiposEmbalagens getEmbalagem() {
        return embalagem;
    }

    public void setEmbalagem(TiposEmbalagens embalagem) {
        this.embalagem = embalagem;
    }
}
