package chocostock.enums;

public enum TiposEmbalagens {
    CAIXA_PP("Caixa PP", "4cm x 12cm x 4cm"),
    CAIXA_P("Caixa borda premium P", "4 doces"),
    CAIXA_M("Caixa borda premium M", "6 doces"),
    CAIXA_G("Caixa borda premium G", "9 doces"),
    CAIXA_ISOPOR("Caixa Isopor", "1L"),
    SAQUINHO("Saquinho", "8cm x 20cm"),
    PAPEL_CHUMBO_P("Papel Chumbo P", "8cm x 7.8cm"),
    PAPEL_CHUMBO_M("Papel Chumbo M", "10cm x 9.8cm"),
    FORMINHAS_KRAFT("Forminhas Kraft", ""),
    FORMINHAS_N4("Forminhas gourmet marrom", "N4"),
    FITA_CETIM("Fita cetim", "10mm x 10m"),
    PAPEL_KRAFT("Papel kraft","69cm x 89cm"),
    SACOLA_KRAFT("Sacola papel kraft", "14cm x 8cm x 16cm"),
    PAPEL_RECICLADO("Papel reciclado 180g", "210mm x 297mm");

    private final String nome;
    private final String tamanho;

    TiposEmbalagens(String nome, String tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
    }

    public String getNome() {
        return nome;
    }

    public String getTamanho() {
        return tamanho;
    }
}
