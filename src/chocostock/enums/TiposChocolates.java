package chocostock.enums;

import chocostock.interfaces.Identificavel;
import chocostock.interfaces.Nomeavel;

/**
 * Enumera todos os tipos de chocolate que a fábrica produz.
 */
public enum TiposChocolates implements Identificavel, Nomeavel {
    CHOCOLATE_AO_LEITE_CARAMELIZADO(1, "Chocolate ao leite caramelizado"),
    CHOCOLATE_AO_LEITE_INTENSO(2, "Chocolate ao leite intenso"),
    CHOCOLATE_INTENSO(3, "Chocolate intenso"),
    CHOCOLATE_BRANCO_ACAI_BETERRABA(4, "Chocolate branco açaí com beterraba");

    private final int id;
    private final String nome;
    TiposChocolates(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * Retorna um array com todos os nomes de tipos de chocolates.
     */
    public static String[] getTipos() {
        int num_tipos = TiposChocolates.values().length;
        String[] tipos = new String[num_tipos];
        for(int i = 0; i < num_tipos; i++){
            tipos[i] = TiposChocolates.values()[i].getNome();
        }
        return tipos;
    }
}
