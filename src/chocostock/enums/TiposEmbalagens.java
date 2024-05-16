package chocostock.enums;

public enum TiposEmbalagens {
    CaixaPP("Caixa", "4cm x 12cm x 4cm"),
    CaixaP("Caixa borda premium", "4 doces"),
    CaixaM("Caixa borda premium", "6 doces"),
    CaixaG("Caixa borda premium", "9 doces"),
    CaixaIsopor("Caixa", "1L"),
    Saquinho("Saquinho", "8cm x 20cm"),
    PapelChumboP("Papel Chumbo", "8cm x 7.8cm"),
    PapelChumboM("Papel Chumbo", "10cm x 9.8cm"),
    ForminhasKraft("Forminhas Kraft", ""),
    ForminhasN4("Forminhas gourmet marrom", "N4"),
    FitaCetim("Fita cetim", "10mm x 10m"),
    PapelKraft("Papel kraft","69cm x 89cm"),
    SacolaKraft("Sacola papel kraft", "14cm x 8cm x 16cm"),
    PapelReciclado("Papel reciclado 180g", "210mm x 297mm");

    private final String tipo;
    private final String tamanho;

    TiposEmbalagens(String tipo, String tamanho) {
        this.tipo = tipo;
        this.tamanho = tamanho;
    }
}
