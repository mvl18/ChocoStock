package chocostock.colaboradores;

import chocostock.auxiliar.Endereco;
import chocostock.interfaces.Escolhivel;
import chocostock.interfaces.Nomeavel;
import chocostock.interfaces.ValidadorInput;

/**
 * A classe abstrata Colaborador serve como classe base para representar
 * um colaborador (cliente, fornecedor e funcionário) com informações
 * básicas como nome, telefone, email e endereço.
 */
public abstract class Colaborador implements Nomeavel, Escolhivel, ValidadorInput {
    private String nome;
    private String telefone;
    private String email;
    private Endereco endereco;

    public Colaborador(String nome, String telefone, String email, Endereco endereco) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return nome +
                "\nTelefone: " + telefone +
                "\nEmail: " + email +
                "\nEndereço: " + endereco +
                "\n";
    }


    public String toString(boolean titulo) {
        return "Colaborador{" + this.toString();
    }
}
