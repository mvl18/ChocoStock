package chocostock.itens.produtos;

import chocostock.enums.TiposComplementos;
import chocostock.enums.TiposChocolates;
import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Iteravel;
import chocostock.itens.materiais.Embalagem;

import java.util.ArrayList;
import java.util.Date;

public class Chocolate extends Produto implements AddRemovivel, Iteravel {
    private int lote;
    private TiposChocolates tipo;
    private ArrayList<TiposComplementos> complementos;
    private String origem_cacau;

    public Chocolate(String nome, int quantidade, float preco, TiposChocolates tipo, Date validade, int peso, Embalagem embalagem, int lote, String origem_cacau) {
        super(nome, quantidade, preco, validade, peso, embalagem);
        this.lote = lote;
        this.tipo = tipo;
        this.complementos = new ArrayList<TiposComplementos>();
        this.origem_cacau = origem_cacau;
    }

    public Chocolate(String nome, int quantidade, float preco, TiposChocolates tipo, Date validade, int peso, Embalagem embalagem, int lote, ArrayList<TiposComplementos> complementos, String origem_cacau) {
        this(nome, quantidade, preco, tipo, validade, peso, embalagem, lote, origem_cacau);
        this.complementos = complementos;
    }

    public int getLote() {
        return lote;
    }

    public void setLote(int lote) {
        this.lote = lote;
    }

    public TiposChocolates getTipo() {
        return tipo;
    }

    public void setTipo(TiposChocolates tipo) {
        this.tipo = tipo;
    }

    public boolean addComplemento(TiposComplementos complemento) {
        return addObjeto(complementos, complemento);
    }

    public boolean removeComplemento(TiposComplementos complemento) {
        return removeObjeto(complementos, complemento);
    }

    public String listaComplementos() {
        return listaObjetos(complementos);
    }

    public String getOrigem_cacau() {
        return origem_cacau;
    }

    public void setOrigem_cacau(String origem_cacau) {
        this.origem_cacau = origem_cacau;
    }
}
