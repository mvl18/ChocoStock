package chocostock.enums;

public enum TiposEmbalagens {
    CaixaPP("Caixa", "4cm x 12cm x 4cm", 1.80, 1),
    CaixaP("Caixa borda premium", "4 doces", 3.40, 1),
    CaixaM("Caixa borda premium", "6 doces", 3.85, 1),
    CaixaG("Caixa borda premium", "9 doces", 4.40, 1),
    CaixaIsopor("Caixa", "1L", 6.50, 1),
    Saquinho("Saquinho", "8cm x 20cm", 4.50, 50),
    PapelChumboP("Papel Chumbo", "8cm x 7.8cm", 12.80, 300),
    PapelChumboM("Papel Chumbo", "10cm x 9.8cm", 9.90, 300),
    ForminhasKraft("Forminhas Kraft", "", 5.2, 50),
    ForminhasN4("Forminhas gourmet marrom", "N4", 4.25, 100),
    FitaCetim("Fita cetim", "10mm x 10m", 4.15, 1),
    PapelKraft("Papel kraft","69cm x 89cm", 7.35, 1),
    SacolaKraft("Sacola papel kraft", "14cm x 8cm x 16cm", 9.90, 10),
    PapelReciclado("Papel reciclado 180g", "210mm x 297mm", 18.90, 50);

    private final String tipo;
    private final String tamanho;
    private final double preco_embalagem;
    private final int quantidade_embalagem;

    TiposEmbalagens(String tipo, String tamanho, double preco_embalagem, int quantidade_embalagem) {
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.preco_embalagem = preco_embalagem;
        this.quantidade_embalagem = quantidade_embalagem;
    }
}
