package chocostock.itens.produtos;

import chocostock.enums.TiposComplementos;
import chocostock.enums.TiposChocolates;
import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Complementavel;
import chocostock.interfaces.Iteravel;
import chocostock.itens.suprimentos.Embalagem;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A classe Chocolate representa um tipo específico de produto
 * com base na classe Produto. Possuindo seu tipo, seu lote,
 * complementos do chocolate e a origem do cacau.
 */
public class Chocolate extends Produto implements AddRemovivel, Iteravel, Complementavel {
    private int lote;
    private TiposChocolates tipo;
    private ArrayList<TiposComplementos> complementos;
    private String origem_cacau;

    public Chocolate(TiposChocolates tipo, int quantidade, float preco, LocalDate validade, int peso, Embalagem embalagem, int lote, String origem_cacau) {
        super(tipo.getNome(), quantidade, preco, validade, peso, embalagem);
        this.lote = lote;
        this.complementos = new ArrayList<TiposComplementos>();
        this.origem_cacau = origem_cacau;
    }

    public Chocolate(TiposChocolates tipo, int quantidade, float preco, LocalDate validade, int peso, Embalagem embalagem, int lote, ArrayList<TiposComplementos> complementos, String origem_cacau) {
        this(tipo, quantidade, preco, validade, peso, embalagem, lote, origem_cacau);
        this.complementos = complementos;
    }

    public Chocolate() {
        this(TiposChocolates.CHOCOLATE_INTENSO, -1, -1F, null, -1, null, -1, null, null);
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
        return listaHorizontalQuebraLinha(complementos);
    }

    public String getOrigem_cacau() {
        return origem_cacau;
    }

    public void setOrigem_cacau(String origem_cacau) {
        this.origem_cacau = origem_cacau;
    }

    @Override
    public ArrayList<TiposComplementos> getComplementos() {
        return complementos;
    }
}
