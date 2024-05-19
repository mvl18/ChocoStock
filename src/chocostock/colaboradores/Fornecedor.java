package chocostock.colaboradores;

import chocostock.auxiliar.Endereco;

/**
 * A classe Fornecedor representa um fornecedor que herda de Colaborador.
 * Esta classe gerencia informações específicas do fornecedor, como um
 * identificador único, CNPJ e site.
 */
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
        return id + ". " + super.toString() +
                "CNPJ: " + cnpj +
                "\nSite: " + site +
                "\n";
    }
}
