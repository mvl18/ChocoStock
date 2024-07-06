package chocostock.enums;

import chocostock.interfaces.Identificavel;
import chocostock.interfaces.Nomeavel;

/**

 Seta o cargo de um funcionário.*/
public enum Cargos implements Identificavel, Nomeavel {
    INDEFINIDO("", -1),
    PROPRIETARIO("Proprietario", 1),
    VENDEDOR("Vendedor", 2),
    ENTREGADOR("Entregador", 3),
    CONTADOR("Contador", 4),
    ASSISTENTE_DE_COZINHA("Assistente de Cozinha", 5),
    ASSISTENTE_DE_MARKETING("Assistente de Marketing", 6),
    TECNICO_DE_TI("Técnico de TI", 7);

    private final String nome;
    private final int id;

    Cargos(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public static String[] getTipos(){
        int num_tipos = Cargos.values().length;
        int i = 0;
        String[] tipos = new String[num_tipos];
        for(Cargos t : Cargos.values()){
            tipos[i] = t.getNome();
            i++;
        }
        return tipos;
    }

    public static Cargos parseCargo(String nome) {
        for (Cargos cargo : Cargos.values()) {
            if (cargo.getNome().equals(nome))
                return cargo;
        }
        return INDEFINIDO;
    }
}