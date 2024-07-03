package chocostock.colaboradores;

import chocostock.auxiliar.Endereco;
import chocostock.auxiliar.Processa;
import chocostock.auxiliar.Verifica;
import chocostock.interfaces.Identificavel;
import chocostock.interfaces.ValidadorInput;

import java.util.Scanner;

/**
 * A classe Fornecedor representa um fornecedor que herda de Colaborador.
 * Esta classe gerencia informações específicas do fornecedor, como um
 * identificador único, CNPJ e site.
 */
public class Fornecedor extends Colaborador implements Identificavel {
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

    public int getId() {
        return id;
    }

    public static int getIdFornecedor() {
        return id_fornecedores;
    }

    public static void setIdFornecedor(int id) {
        id_fornecedores = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    /**
     * Método para criar um novo fornecedor com dados fornecidos pelo usuário.
     * Solicita ao usuário que insira as informações do fornecedor via console.
     */
    public static Fornecedor novoFornecedor(Scanner scanner) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(ValidadorInput.getInput(scanner, "Nome do fornecedor: ", "Nome invalido. Insira novamente.", Verifica::isNome));
        fornecedor.setTelefone(ValidadorInput.getInput(scanner, "Telefone do fornecedor: ","Telefone inválido. Insira novamente.", Verifica::isTelefone));
        fornecedor.setEmail(ValidadorInput.getInput(scanner, "Email do fornecedor:", "Email inválido. Insira novamente.", Verifica::isEmail));
        fornecedor.setEndereco(new Endereco().criaEndereco(scanner));
        fornecedor.setCnpj(Processa.normalizaNumero(ValidadorInput.getInput(scanner, "CNPJ do fornecedor:", "CNPJ inválido. Insira novamente.", Verifica::isCnpj)));
        fornecedor.setSite(ValidadorInput.getInput(scanner, "Site do fornecedor:", "Site inválido. Insira novamente.", Verifica::isSite));
        return fornecedor;
    }
}
