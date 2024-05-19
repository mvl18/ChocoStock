package chocostock.enums;

import chocostock.interfaces.Identificavel;
import chocostock.interfaces.Nomeavel;

/**
 * Seta o status do pedido.
 */
public enum Status implements Identificavel, Nomeavel {
    CANCELADO("Cancelado", 1),
    PENDENTE("Pendente", 2),
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
}
