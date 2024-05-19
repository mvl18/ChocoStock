package chocostock.itens;

import chocostock.interfaces.Identificavel;
import chocostock.interfaces.Nomeavel;

/**
 * A classe abstrata Item representa um item genérico que pode ser
 * tanto um Produto quanto um Suprimento. Esta classe serve como
 * base para itens específicos, fornecendo funcionalidades comuns
 * como identificação, nome, quantidade e preço.
 */
public abstract class Item implements Identificavel, Nomeavel {
    private static int idItem = 100000;
    private int id;
    private String nome;
    private int quantidade;
    private float preco;

    public Item(String nome, int quantidade, float preco) {
        this.id = idItem++;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }
}
