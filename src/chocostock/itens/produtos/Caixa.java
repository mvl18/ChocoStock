package chocostock.itens.produtos;

import chocostock.itens.Produto;
import chocostock.itens.materiais.Embalagem;

import java.util.ArrayList;
import java.util.Date;

public class Caixa extends Produto {
    private ArrayList<Chocolate> chocolates;

    public Caixa(String nome, int quantidade, float preco, TiposChocolates tipo, Date validade, int peso, Embalagem embalagem, ArrayList<Chocolate> chocolates) {
        super(nome, quantidade, preco, tipo, validade, peso, embalagem);
        this.chocolates = chocolates;
    }

    public ArrayList<Chocolate> getChocolates() {
        return chocolates;
    }

    public void setChocolates(ArrayList<Chocolate> chocolates) {
        this.chocolates = chocolates;
    }

    public boolean addChocolate(Chocolate chocolate) {
        if (!this.chocolates.contains(chocolate)) {
            this.getChocolates().add(chocolate);
            return true;
        }

        return false;

    }

    public boolean removeChocolate(Chocolate chocolate) {
        if (this.chocolates.contains(chocolate)) {
            this.getChocolates().remove(chocolate);
            return true;
        }

        return false;
    }
}
