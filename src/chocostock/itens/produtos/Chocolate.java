package chocostock.itens.produtos;

import chocostock.enums.TiposComplementos;
import chocostock.enums.TiposChocolates;
import chocostock.itens.materiais.Embalagem;

import java.util.ArrayList;
import java.util.Date;

public class Chocolate extends Produto {
    private int lote;
    private TiposChocolates tipo;
    private int intensidade;
    private ArrayList<TiposComplementos> complementos;
    private String origem;

    public Chocolate(String nome, int quantidade, float preco, TiposChocolates tipo, Date validade, int peso, Embalagem embalagem, int lote, int intensidade, ArrayList<TiposComplementos> complementos, String origem) {
        super(nome, quantidade, preco, validade, peso, embalagem);
        this.lote = lote;
        this.tipo = tipo;
        this.intensidade = intensidade;
        this.complementos = complementos;
        this.origem = origem;
    }

    public int getLote() {
        return lote;
    }

    public void setLote(int lote) {
        this.lote = lote;
    }
}
