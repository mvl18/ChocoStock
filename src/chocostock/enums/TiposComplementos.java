package chocostock.enums;

public enum TiposComplementos {
    NIBS_CACAU("Nibs de cacau"),
    CAJU("Caju"),
    AVELA("Avelã"),
    AMENDOA("Amêndoa"),
    BANANA("Banana"),
    DAMASCO("Damasco"),
    CRANBERRY("Cranberry");

    private final String nome;
    TiposComplementos(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
