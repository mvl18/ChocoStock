package chocostock.itens.produtos;

import chocostock.enums.TiposCaixas;
import chocostock.enums.TiposEmbalagens;
import chocostock.itens.suprimentos.Embalagem;

import java.time.LocalDate;

/**
 * A classe Caixa representa um tipo específico de produto com base
 * na classe Produto. Possuindo seu tipo e seu lote.
 */
public class Caixa extends Produto {
    private TiposCaixas tipo;
    private int lote;

    public Caixa(TiposCaixas tipo, int quantidade, float preco, LocalDate validade, int peso, TiposEmbalagens embalagem, int lote) {
        super(tipo.getNome(), quantidade, preco, validade, peso, embalagem);
        this.tipo = tipo;
        this.lote = lote;
    }

    public Caixa() {
        this(TiposCaixas.CAIXA_ASORTI_P, -1, -1.0F, null, -1, null, -1);
    }

    public TiposCaixas getTipo() {
        return tipo;
    }

    public void setTipo(TiposCaixas tipo) {
        this.tipo = tipo;
    }

    public int getLote() {
        return lote;
    }

    public void setLote(int lote) {
        this.lote = lote;
    }
}
