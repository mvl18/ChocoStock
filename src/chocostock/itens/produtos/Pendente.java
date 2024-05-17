package chocostock.itens.produtos;

import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Nomeavel;

import java.util.ArrayList;

public class Pendente  implements Nomeavel, AddRemovivel {
    private String nome;
    private ArrayList<String> complementos;
    private int quantidade;

    public Pendente(String nome, ArrayList<String> complementos, int quantidade) {
        this.nome = nome; // chave primaria
        this.complementos = complementos;
        this.quantidade = quantidade;
    }

    public Pendente() {
        this("", new ArrayList<String>(), 0);
    }

    @Override
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<String> getComplementos() {
        return complementos;
    }

    public void setComplementos(ArrayList<String> complementos) {
        this.complementos = complementos;
    }

    public boolean addComplemento(String complemento) {
        return addObjeto(complementos, complemento);
    }

    public boolean removeComplemento(String complemento) {
        return removeObjeto(complementos, complemento);
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Pendente{" +
                "nome='" + nome + '\'' +
                ", complementos=" + complementos +
                ", quantidade=" + quantidade +
                '}';
    }
}
