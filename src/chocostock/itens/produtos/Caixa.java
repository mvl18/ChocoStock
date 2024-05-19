package chocostock.itens.produtos;

import chocostock.enums.TiposCaixas;
import chocostock.itens.suprimentos.Embalagem;

import java.time.LocalDate;

public class Caixa extends Produto {
    private TiposCaixas tipo;
    private int lote;

    public Caixa(TiposCaixas tipo, int quantidade, int lote, float preco, LocalDate validade, int peso, Embalagem embalagem) {
        super(tipo.getNome(), quantidade, preco, validade, peso, embalagem);
        this.tipo = tipo;
        this.lote = lote;
    }

    public Caixa() {
        this(TiposCaixas.CAIXA_ASORTI_P, -1, -1, -1.0F, null, -1, null);
    }

    public TiposCaixas getTipo() {
        return tipo;
    }

    public void setTipo(TiposCaixas tipo) {
        this.tipo = tipo;
    }
}
