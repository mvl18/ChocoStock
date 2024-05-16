package chocostock.enums;

public enum TiposIngredientes {
    //Produtos e Codigos retirados da planilha
    //Produtos estao com letras minusculas sem acento
    CACAU("cacau", 1),
    MANTEIGA_DE_CACAU("manteiga de cacau", 2),
    ACUCAR_DEMERARA("acucar demerara", 3),
    ACUCAR_MASCAVO("acucar mascavo", 4),
    LEITE_PO_INTEGRAL("leite em po integral", 5),
    LEITE_PO_DESNATADO("leite em po desnatado", 6),
    LECITINA_GIRASOL("lecitina de girasol", 7),
    CAJU("caju", 8),
    AVELA("avela", 9),
    AMENDOA("amendoa", 10),
    CRANBERRY("cranberry", 11),
    DAMASCO("damasco", 12),
    BANANA_CHIPS_DOCE("banana chips doce", 13),
    UVA_PASSA("uva passa", 14),
    POLPA_ACAI("polpa de acai", 15),
    FARINHA_BETERRABA("farinha de beterraba", 16);

    private final String produto;
    private final int codigo;
    TiposIngredientes(String produto, int codigo) {
        this.produto = produto;
        this.codigo = codigo;
    }
}
