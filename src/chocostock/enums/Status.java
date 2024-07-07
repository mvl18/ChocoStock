package chocostock.enums;

import chocostock.interfaces.Identificavel;
import chocostock.interfaces.Nomeavel;

/**
 * Seta o status do pedido.
 */
public enum Status implements Identificavel, Nomeavel {
    PENDENTE("Pendente", 2),
    CANCELADO("Cancelado", 1),
    PRONTO("Pronto", 3),
    TRANSITO("Transito", 4),
    FINALIZADO("Finalizado", 5);

    private final String nome;
    private final int id;

    Status(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }
    /**
     * Retorna um array com todos os possíveis status.
     */
    public static String[] getTipos() {
        int num_tipos = Status.values().length;
        String[] tipos = new String[num_tipos];
        for(int i = 0; i < num_tipos; i++){
            tipos[i] = Status.values()[i].getNome();
        }
        return tipos;
    }

    public static Status parseStatus(String nome) {
        for (Status status : Status.values()) {
            if (status.getNome().equalsIgnoreCase(nome))
                return status;
        }
        return PENDENTE;
    }
}

