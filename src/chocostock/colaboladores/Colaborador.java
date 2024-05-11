package chocostock.colaboladores;

import chocostock.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Colaborador implements Nomeavel, Escolhivel {
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
        System.out.println("CEP: ");
        endereco.setCep(scanner.nextLine());
        // ESTADO
        System.out.println("Estado: ");
        ArrayList<Estados> listaEstados = new ArrayList<>(Arrays.asList(Estados.values()));
        endereco.setEstado(escolheObjeto(scanner, listaEstados));
        // CIDADE
        System.out.println("Cidade: ");
        endereco.setCidade(scanner.nextLine());
        // BAIRRO
        System.out.println("Bairro: ");
        endereco.setBairro(scanner.nextLine());
        // RUA
        System.out.println("Rua: ");
        endereco.setRua(scanner.nextLine());
        // NUMERO
        System.out.println("Numero do endereco: ");
        endereco.setNumero(Integer.parseInt(scanner.nextLine()));

        return endereco;
    }

}
