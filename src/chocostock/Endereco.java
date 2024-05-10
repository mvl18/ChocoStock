package chocostock;

public class Endereco {
    private int numero;
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private Estados estado;

    public Endereco(int numero, String cep, String rua, String bairro, String cidade, Estados estado) {
        this.numero = numero;
        this.cep = cep;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Endereco() {
        this(0, "", "", "", "", Estados.AC); // seria bom criar um estado default talvez
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public String toString(boolean bool) {
        return "CEP: " + cep +
                "\nRua " + rua + ", " + numero + " - Bairro " + bairro +
                "\n" + cidade + " (" + estado.getCodigo() + ")";
    }

    public String toString() {
        return "Endereco{" +
                "numero=" + numero +
                ", cep='" + cep + '\'' +
                ", rua='" + rua + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado=" + estado +
                '}';
    }
}


