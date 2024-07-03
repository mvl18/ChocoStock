package chocostock.enums;

import chocostock.interfaces.Identificavel;
import chocostock.interfaces.Nomeavel;

/**
 * - `TiposIngredientes`: Enumera os tipos de ingredientes que a fábrica precisa comprar.
 */
public enum TiposIngredientes implements Identificavel, Nomeavel {
    CACAU("Cacau", 1),
    MANTEIGA_DE_CACAU("Manteiga de cacau", 2),
    ACUCAR_DEMERARA("Acucar demerara", 3),
    ACUCAR_MASCAVO("Acucar mascavo", 4),
    LEITE_PO_INTEGRAL("Leite em po integral", 5),
    LEITE_PO_DESNATADO("Leite em po desnatado", 6),
    LECITINA_GIRASOL("Lecitina de girasol", 7),
    CAJU("Caju", 8),
    AVELA("Avela", 9),
    AMENDOA("Amendoa", 10),
    CRANBERRY("Cranberry", 11),
    DAMASCO("Damasco", 12),
    BANANA_CHIPS_DOCE("Banana chips doce", 13),
    UVA_PASSA("Uva passa", 14),
    POLPA_ACAI("Polpa de acai", 15),
    FARINHA_BETERRABA("Farinha de beterraba", 16);

    private final String nome;
    private final int id;
    TiposIngredientes(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }
    public String getNome() {return nome;}

    public int getId() {return id;}

    public static String[] getTipos(){
        int num_tipos = TiposIngredientes.values().length;
        int i = 0;
        String[] tipos = new String[num_tipos];
        for(TiposIngredientes t : TiposIngredientes.values()){
            tipos[i] = t.getNome();
            i++;
        }
        return tipos;
    }

}
