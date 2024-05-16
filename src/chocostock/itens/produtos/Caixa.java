package chocostock.itens.produtos;

import chocostock.enums.TiposCaixas;
import chocostock.itens.materiais.Embalagem;

import java.util.ArrayList;
import java.util.Date;

public class Caixa extends Produto {
    private ArrayList<Chocolate> chocolates;
    private TiposCaixas tipo;

    public Caixa(String nome, int quantidade, float preco, Date validade, int peso, Embalagem embalagem, ArrayList<Chocolate> chocolates, TiposCaixas tipo) {
        super(nome, quantidade, preco, validade, peso, embalagem);
        this.chocolates = chocolates;
        this.tipo = tipo;
    }

    public ArrayList<Chocolate> getChocolates() {
        return chocolates;
    }

    public void setChocolates(ArrayList<Chocolate> chocolates) {
        this.chocolates = chocolates;
    }

    public TiposCaixas getTipo() {
        return tipo;
    }

    public void setTipo(TiposCaixas tipo) {
        this.tipo = tipo;
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
