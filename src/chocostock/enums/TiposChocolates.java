package chocostock.enums;

import chocostock.interfaces.Identificavel;
import chocostock.interfaces.Nomeavel;

public enum TiposChocolates implements Identificavel, Nomeavel {
    CHOCOLATE_AO_LEITE_CARAMELIZADO(1, "Chocolate ao leite caramelizado", 40),
    CHOCOLATE_AO_LEITE_INTENSO(2, "Chocolate ao leite intenso", 50),
    CHOCOLATE_INTENSO(3, "Chocolate intenso", 70),
    CHOCOLATE_BRANCO_ACAI_BETERRABA(4, "Chocolate branco açaí com beterraba", 32);

    private final int id;
    private final String nome;
    private final int porcentagem_cacau;

    TiposChocolates(int id, String nome, int porcentagem_cacau) {
        this.id = id;
        this.nome = nome;
        this.porcentagem_cacau = porcentagem_cacau;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public int getPorcentagem_cacau() {
        return porcentagem_cacau;
    }

    @Override
    public int getId() {
        return id;
    }
}
