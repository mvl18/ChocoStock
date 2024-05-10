package chocostock;

public enum Status implements Identificavel, Nomeavel {
    CANCELADO("CANCELADO", 1),
    PENDENTE("PENDENTE", 2),
    PRONTO("PRONTO", 3),
    TRANSITO("TRANSITO", 4),
    FINALIZADO("FINALIZADO", 5);

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
