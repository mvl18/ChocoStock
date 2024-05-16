package chocostock.colaboradores;

import chocostock.auxiliar.Endereco;
import chocostock.auxiliar.Verifica;
import chocostock.enums.Estados;
import chocostock.interfaces.Escolhivel;
import chocostock.interfaces.Nomeavel;
import chocostock.interfaces.ValidadorInput;

import java.text.Normalizer;
import java.util.Scanner;

public class Colaborador implements Nomeavel, Escolhivel, ValidadorInput {
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
        return "nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", endereco=" + endereco +
                '}';
    }


    public String toString(boolean titulo) {
        return "Colaborador{" + this.toString();
    }

    public Endereco criaEndereco(Scanner scanner) {
        //Endereco(int numero, String cep, String rua, String bairro, String cidade, Estados estado)
        Endereco endereco = new Endereco();
        // CEP
        endereco.setCep(getInput(scanner, "CEP: ", "Insira um CEP válido!",
                                 Verifica::isCep).replaceAll("\\D", ""));
        // ESTADO
        endereco.achaEstado(endereco.getCep());
        if((Normalizer.normalize(getInput(scanner, endereco.getEstado().getNome() + " é o estado do endereço? (Sim ou Não)", "Por favor, insira uma resposta valida. ",
                input -> input.matches("sim|nao")).toLowerCase().replaceAll("\\s", ""),
                Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .equals("nao"))) {
            System.out.println("Estado: ");
            endereco.setEstado(escolheObjeto(scanner, Estados.values(),
                    "Estado invalido. Por favor, digite a sigla ou nome de um dos estados validos.",
                    "obrigatorio"));
        }
        // CIDADE
        endereco.setCidade(getInput(scanner, "Cidade: ", "Cidade invalida. Coloque um nome valido.", Verifica::isNome));
        // BAIRRO
        endereco.setCidade(getInput(scanner, "Bairro: ", "Bairro invalida. Coloque um nome valido.", Verifica::isNome));
        // RUA
        endereco.setCidade(getInput(scanner, "Rua: ", "Rua invalida. Coloque um nome valido.", Verifica::isNome));
        // NUMERO
        endereco.setNumero(Integer.parseInt(getInput(scanner, "Numero do endereco", "Numero invalido. Coloque um inteiro.", Verifica::isNatural)));

        return endereco;
    }

}
