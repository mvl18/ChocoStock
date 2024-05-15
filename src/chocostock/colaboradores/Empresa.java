package chocostock.colaboradores;

import chocostock.Endereco;

public class Empresa extends Colaborador {
    private int cnpj;
    private String site;
    public Empresa(String nome, String telefone, String email, Endereco endereco, int cnpj, String site) {
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
