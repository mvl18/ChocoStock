package chocostock.auxiliar;


import chocostock.enums.Estados;
import chocostock.interfaces.Escolhivel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A classe Endereco representa um endereço físico, incluindo informações detalhadas
 * como número, CEP, rua, bairro, cidade e estado. <br>
 * Implementa o método "achaEstado".
**/
public class Endereco implements Escolhivel {
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
}


