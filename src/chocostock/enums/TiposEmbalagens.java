package chocostock.enums;

import chocostock.interfaces.Identificavel;
import chocostock.interfaces.Nomeavel;

public enum TiposEmbalagens implements Nomeavel, Identificavel {
    CAIXA_PP("Caixa", "4cm x 12cm x 4cm", 1),
    CAIXA_P("Caixa borda premium", "4 doces", 2),
    CAIXA_M("Caixa borda premium", "6 doces", 3),
    CAIXA_G("Caixa borda premium", "9 doces", 4),
    CAIXA_ISOPOR("Caixa", "1L", 5),
    SAQUINHO("Saquinho", "8cm x 20cm", 6),
    PAPEL_CHUMBO_P("Papel Chumbo", "8cm x 7.8cm", 7),
    PAPEL_CHUMBO_M("Papel Chumbo", "10cm x 9.8cm", 8),
    FORMINHAS_KRAFT("Forminhas Kraft", "", 9),
    FORMINHAS_N4("Forminhas gourmet marrom", "N4", 10),
    FITA_CETIM("Fita cetim", "10mm x 10m", 11),
    PAPEL_KRAFT("Papel kraft","69cm x 89cm", 12),
    SACOLA_KRAFT("Sacola papel kraft", "14cm x 8cm x 16cm", 13),
    PAPEL_RECICLADO("Papel reciclado 180g", "210mm x 297mm", 14);

    private final String nome;
    private final String tamanho;
    private final int id;

    TiposEmbalagens(String nome, String tamanho, int id) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getTamanho() {
        return tamanho;
    }

    public int getId() {return id;}

    public String toString(){
        String msg = "";
        msg += "Modelo: " + getNome() + ", " + getTamanho();
        return msg;
    }
}
