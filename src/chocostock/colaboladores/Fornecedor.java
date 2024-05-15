package chocostock.colaboladores;

import chocostock.Endereco;

public class Fornecedor extends Colaborador {
    private int cnpj;
    private String site;
    public Fornecedor(String nome, String telefone, String email, Endereco endereco, int cnpj, String site) {
        super(nome, telefone, email, endereco);
        this.cnpj = cnpj;
        this.site = site;
    }

    public int getCnpj() {
        return cnpj;
    }

    public void setCnpj(int cnpj) {
        this.cnpj = cnpj;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
