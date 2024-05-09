package chocostock.itens.produtos;

import chocostock.itens.Produto;
import chocostock.itens.materiais.Embalagem;

import java.util.Date;

public class Chocolate extends Produto {
    private int lote;

    public Chocolate(String nome, int quantidade, float preco, TiposChocolates tipo, Date validade, int peso, Embalagem embalagem, int lote) {
        super(nome, quantidade, preco, tipo, validade, peso, embalagem);
        this.lote = lote;
    }

    public int getLote() {
        return lote;
    }

    public void setLote(int lote) {
        this.lote = lote;
    }
}
