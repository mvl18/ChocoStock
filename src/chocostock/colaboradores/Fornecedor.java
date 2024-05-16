package chocostock.colaboradores;

import chocostock.auxiliar.Endereco;

public class Fornecedor extends Colaborador {
    private static int id_fornecedores = 100000;
    private final int id;
    private String cnpj;
    private String site;
    public Fornecedor(String nome, String telefone, String email, Endereco endereco, String cnpj, String site) {
        super(nome, telefone, email, endereco);
        this.id = id_fornecedores++;
        this.cnpj = cnpj;
        this.site = site;
    }

    public Fornecedor(String nome) {
        this();
        super.setNome(nome);
    }

    public Fornecedor(){
        this("", "", "", new Endereco(), "", "");
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + id + " " +
                super.toString() +
                ", cnpj='" + cnpj + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}
