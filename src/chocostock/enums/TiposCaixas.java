package chocostock.enums;

public enum TiposCaixas {
    CAIXA_MINI_TABLETES_70("Caixa mini tabletes", 70),
    CAIXA_BARRAS_AO_LEITE_CARAMELIZADO_40("Caixa barras ao leite caramelizado", 40),
    CAIXA_ASORTI_P("Caixa sortida", 0),
    CAIXA_ASORTI_M("Caixa sortida", 0),
    CAIXA_ASORTI_G("Caixa sortida", 0);

    private final String tipo;
    private final int porcentagem_cacau;

    TiposCaixas(String tipo, int porcentagem_cacau) {
        this.tipo = tipo;
        this.porcentagem_cacau = porcentagem_cacau;
    }
}
