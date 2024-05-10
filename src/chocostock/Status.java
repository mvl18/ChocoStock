package chocostock;

public enum Status {
    CANCELADO("CANCELADO"),
    PENDENTE("PENDENTE"),
    PRONTO("PRONTO"),
    TRANSITO("TRANSITO"),
    FINALIZADO("FINALIZADO");

    private final String nome;

    Status(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
