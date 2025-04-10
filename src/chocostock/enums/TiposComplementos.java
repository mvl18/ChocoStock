package chocostock.enums;

import chocostock.interfaces.Identificavel;
import chocostock.interfaces.Nomeavel;

/**
 * Enumera todos os complementos para os chocolates.
 */
public enum TiposComplementos implements Identificavel, Nomeavel {
    NIBS_CACAU(1, "Nibs de cacau"),
    CAJU(2, "Caju"),
    AVELA(3, "Avelã"),
    AMENDOA(4, "Amêndoa"),
    BANANA(5, "Banana"),
    DAMASCO(6, "Damasco"),
    CRANBERRY(7, "Cranberry");

    private final int id;
    private final String nome;
    TiposComplementos(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * Retorna um array com todos os nomes de tipos de complementos.
     */
    public static String[] getTipos() {
        int num_tipos = TiposComplementos.values().length;
        String[] tipos = new String[num_tipos];
        for(int i = 0; i < num_tipos; i++){
            tipos[i] = TiposComplementos.values()[i].getNome();
        }
        return tipos;
    }
}
