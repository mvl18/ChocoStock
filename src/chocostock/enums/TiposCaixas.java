package chocostock.enums;

import chocostock.interfaces.Identificavel;
import chocostock.interfaces.Nomeavel;

public enum TiposCaixas implements Identificavel, Nomeavel {
    CAIXA_MINI_TABLETES_70(1, "Caixa mini tabletes", 70),
    CAIXA_BARRAS_AO_LEITE_CARAMELIZADO_40(2, "Caixa barras ao leite caramelizado", 40),
    CAIXA_ASORTI_P(3, "Caixa sortida P", 0),
    CAIXA_ASORTI_M(4, "Caixa sortida M", 0),
    CAIXA_ASORTI_G(5, "Caixa sortida G", 0);

    private final int id;
    private final String nome;
    private final int porcentagem_cacau;

    TiposCaixas(int id, String nome, int porcentagem_cacau) {
        this.id = id;
        this.nome = nome;
        this.porcentagem_cacau = porcentagem_cacau;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public int getPorcentagem_cacau() {
        return porcentagem_cacau;
    }
}
