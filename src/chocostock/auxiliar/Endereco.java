package chocostock.auxiliar;


import chocostock.enums.Estados;
import chocostock.interfaces.Escolhivel;
import chocostock.interfaces.ValidadorInput;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * A classe Endereco representa um endereço físico, incluindo informações detalhadas
 * como número, CEP, rua, bairro, cidade e estado. <br>
 * Implementa o método "achaEstado".
 **/
public class Endereco implements Escolhivel, Serializable {
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

    public Endereco(String cep, Estados estado, String cidade, String bairro, String rua, int numero) {
        this.numero = numero;
        this.cep = cep;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Endereco() {
        this(0, "", "", "", "", Estados.XX);
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

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
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

    /**
     * Determina e define o Estado correspondente a um CEP fornecido.
     */
    public void achaEstado(String CEP) {
        // Converte o CEP para um número inteiro e calcula o identificador do estado
        int id = Integer.parseInt(CEP)/100000;

        // Cria uma lista de IDs dos estados
        List<Integer> idsEstados = new ArrayList<>();
        for (Estados estado : Estados.values()) {
            idsEstados.add(estado.getId());
        }
        // Ordena a lista de IDs dos estados
        Collections.sort(idsEstados);

        // Encontra o índice apropriado na lista de IDs dos estados
        int i = 0;
        for (;i < idsEstados.size(); i++) {
            if (idsEstados.get(i) > id)
                break;
        }

        // Define o estado do objeto Endereco com base no ID encontrado
        for (Estados estado : Estados.values()) {
            if (estado.getId() == idsEstados.get(i-1))
                setEstado(estado);
        }
    }

    public String toString() {
        return "Rua " + rua +
                ", n° " + numero +
                " - Bairro " + bairro +
                ". CEP: " + cep +
                ". " + cidade +
                " - " + estado
                ;
    }

    /**
     * Cria um novo objeto de Endereco com base nas entradas do usuário.
     *
     * @param scanner O scanner usado para ler as entradas do usuário.
     * @return Um novo objeto de Endereco preenchido com os detalhes fornecidos pelo usuário.
     */
    public Endereco criaEndereco(Scanner scanner) {
        Endereco endereco = new Endereco();
        // CEP
        endereco.setCep(ValidadorInput.getInput(scanner, "CEP: ", "Insira um CEP válido!",
                Verifica::isCep).replaceAll("\\D", ""));
        // ESTADO
        endereco.achaEstado(endereco.getCep());
        String resposta = Normalizer.normalize(ValidadorInput.getInput(scanner, endereco.getEstado().getNome() + " é o estado do endereço? (Sim ou Não) ", "Por favor, insira uma resposta valida. ",
                        input -> input.matches("sim|nao|s|n")).toLowerCase().replaceAll("\\s", ""),
                Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        if (resposta.equals("nao") || resposta.equals("n")) {
            System.out.print("Estado: ");
            endereco.setEstado(Escolhivel.escolheObjeto(scanner, Estados.values(),
                    "Estado inválido. Por favor, digite a sigla ou nome de um dos estados válidos.",
                    "obrigatório"));
        }
        // CIDADE
        endereco.setCidade(ValidadorInput.getInput(scanner, "Cidade: ", "Cidade invalida. Coloque um nome valido.", Verifica::isNome));
        // BAIRRO
        endereco.setBairro(ValidadorInput.getInput(scanner, "Bairro: ", "Bairro invalida. Coloque um nome valido.", Verifica::isNome));
        // RUA
        endereco.setRua(ValidadorInput.getInput(scanner, "Rua: ", "Rua invalida. Coloque um nome valido.", input -> true));
        // NUMERO
        endereco.setNumero(Integer.parseInt(ValidadorInput.getInput(scanner, "Número do endereço: ", "Número inválido. Coloque um inteiro.", Verifica::isNatural)));

        return endereco;
    }
}