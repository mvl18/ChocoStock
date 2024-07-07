package chocostock.enums;

import chocostock.interfaces.Identificavel;
import chocostock.interfaces.Nomeavel;

/**
 * - `TiposCaixas`: Enumera todos os tipos de caixa que a fábrica vende.
 */
public enum TiposCaixas implements Identificavel, Nomeavel {
    INDEFINIDO(-1, ""),
    CAIXA_MINI_TABLETES_70(1, "Caixa mini tabletes"),
    CAIXA_BARRAS_AO_LEITE_CARAMELIZADO_40(2, "Caixa barras ao leite caramelizado"),
    CAIXA_ASORTI_P(3, "Caixa sortida P"),
    CAIXA_ASORTI_M(4, "Caixa sortida M"),
    CAIXA_ASORTI_G(5, "Caixa sortida G");

    private final int id;
    private final String nome;

    TiposCaixas(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    /**
     * Retorna um array com todos os nomes de tipos de caixas.
     */
    public static String[] getTipos() {
        int num_tipos = TiposCaixas.values().length;
        String[] tipos = new String[num_tipos];
        for(int i = 0; i < num_tipos; i++){
            tipos[i] = TiposCaixas.values()[i].getNome();
        }
        return tipos;
    }

    public static TiposCaixas parseCaixa(String nome) {
        for (TiposCaixas caixa : TiposCaixas.values()) {
            if (caixa.getNome().equals(nome))
                return caixa;
        }
        return INDEFINIDO;
    }
}
