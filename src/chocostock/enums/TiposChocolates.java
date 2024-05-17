package chocostock.enums;

public enum TiposChocolates {
    CHOCOLATE_AO_LEITE_CARAMELIZADO("Chocolate ao leite caramelizado", 40),
    CHOCOLATE_AO_LEITE_INTENSO("Chocolate ao leite intenso", 50),
    CHOCOLATE_INTENSO("Chocolate intenso", 70),
    CHOCOLATE_BRANCO_ACAI_BETERRABA("Chocolate branco açaí com beterraba", 32);

    private final String tipo;
    private final int porcentagem_cacau;

    TiposChocolates(String tipo, int porcentagem_cacau) {
        this.tipo = tipo;
        this.porcentagem_cacau = porcentagem_cacau;
    }
}
