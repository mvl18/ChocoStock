package chocostock.enums;

import chocostock.interfaces.Identificavel;
import chocostock.interfaces.Nomeavel;

/**
 * - `TiposCaixas`: Enumera todos os tipos de caixa que a fábrica vende.
 */
public enum TiposCaixas implements Identificavel, Nomeavel {
    CAIXA_MINI_TABLETES_70(1, "Caixa mini tabletes", 70, 4),
    CAIXA_BARRAS_AO_LEITE_CARAMELIZADO_40(2, "Caixa barras ao leite caramelizado", 40, 4),
    CAIXA_ASORTI_P(3, "Caixa sortida P", -1, 4),
    CAIXA_ASORTI_M(4, "Caixa sortida M", -1, 6),
    CAIXA_ASORTI_G(5, "Caixa sortida G", -1, 8);

    private final int id;
    private final String nome;
    private final int porcentagem_cacau;
    private final int quantidade_chocolates;

    TiposCaixas(int id, String nome, int porcentagem_cacau, int quantidade_chocolates) {
        this.id = id;
        this.nome = nome;
        this.porcentagem_cacau = porcentagem_cacau;
        this.quantidade_chocolates = quantidade_chocolates;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }
}
