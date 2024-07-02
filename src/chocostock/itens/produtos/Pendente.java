package chocostock.itens.produtos;

import chocostock.enums.TiposComplementos;
import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Iteravel;
import chocostock.interfaces.Nomeavel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A classe Pendente representa um produto pendente a ser adicionado a um pedido na loja.
 */
public class Pendente implements Nomeavel, AddRemovivel, Iteravel, Serializable {
    private String nome;
    private ArrayList<TiposComplementos> complementos;
    private int quantidade;

    public Pendente(String nome, ArrayList<TiposComplementos> complementos, int quantidade) {
        this.nome = nome; // chave primaria
        this.complementos = complementos;
        this.quantidade = quantidade;
    }

    public Pendente() {
        this("", new ArrayList<>(), 0);
    }

    @Override
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<TiposComplementos> getComplementos() {
        return complementos;
    }

    public void setComplementos(ArrayList<TiposComplementos> complementos) {
        this.complementos = complementos;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return nome + " (" + quantidade + " unidade" + (quantidade > 1 ? "s" : "") + ")" +
                "\nComplementos: " + listaHorizontalQuebraLinha(complementos);
    }
}
