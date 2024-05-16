package chocostock.enuns;

public enum TiposEmbalagens {
    CaixaPP("Caixa", "4cm x 12cm x 4cm", "", 1.80, 1, "Aliexpress"),
    CaixaP("Caixa", "4 doces","borda premium", 3.40, 1, "Ricapan"),
    CaixaM("Caixa", "6 doces","borda premium", 3.85, 1, "Ricapan"),
    CaixaG("Caixa", "9 doces","borda premium", 4.40, 1, "Ricapan"),
    CaixaIsopor("Caixa", "1L","", 6.50, 1, "Marília Embalagens"),
    Saquinho("Saquinho", "8cm x 20cm","", 4.50, 50, "Castelo dos Doces"),
    PapelChumboP("Papel Chumbo", "8cm x 7.8cm", "", 12.80, 300, "Sorvemix"),
    PapelChumboM("Papel Chumbo", "10cm x 9.8cm", "", 9.90, 300, "Sorvemix"),
    ForminhasKraft("Forminhas Kraft", "", "", 5.2, 50, "Castelo dos Doces"),
    ForminhasN4("Forminhas", "N4", "gourmet marrom", 4.25, 100, "Castelo dos Doces"),
    FitaCetim("Fita cetim", "10mm x 10m", "", 4.15, 1, "Castelo dos Doces"),
    PapelKraft("Papel kraft","69cm x 89cm", "", 7.35, 1, "Castelo dos Doces"),
    SacolaKraft("Sacola papel kraft", "14cm x 8cm x 16cm", "", 9.90, 10, "Sorvemix"),
    PapelReciclado("Papel reciclado", "210mm x 297mm", "180g", 18.90, 50, "Kalunga");

    private final String tipo;
    private final String tamanho;
    private final String detalhes;
    private final double preco_embalagem;
    private final int quantidade_embalagem;
    private final String fornecedor; // Trocar para objeto fornecedor

    TiposEmbalagens(String tipo, String tamanho, String detalhes, double preco_embalagem, int quantidade_embalagem, String fornecedor) {
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.detalhes = detalhes;
        this.preco_embalagem = preco_embalagem;
        this.quantidade_embalagem = quantidade_embalagem;
        this.fornecedor = fornecedor;
    }
}
