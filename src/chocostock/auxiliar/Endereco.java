package chocostock.auxiliar;


import chocostock.enums.Estados;
import chocostock.interfaces.Escolhivel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        this(0, "", "", "", "", Estados.AC); // seria bom criar um estado default talvez
    }

    public int getNumero() {
        return numero;
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

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
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

    public String toString(boolean bool) {
        return "CEP: " + cep +
                "\nRua " + rua + ", " + numero + " - Bairro " + bairro +
                "\n" + cidade + " (" + estado.getCodigo() + ")";
    }

    public void achaEstado(String CEP) {
        int id = Integer.parseInt(CEP)/100000;

        List<Integer> idsEstados = new ArrayList<Integer>();
        for (Estados estado : Estados.values()) {
            idsEstados.add(estado.getId());
        }

        Collections.sort(idsEstados);
        int i = 0;
        for (;i < idsEstados.size(); i++) {
            if (idsEstados.get(i) > id)
                break;
            continue;
        }
        for (Estados estado : Estados.values()) {
            if (estado.getId() == idsEstados.get(i-1))
                setEstado(estado);
        }
    }

    public String toString() {
        return " Rua " + rua +
                ", n° " + numero +
                " - Bairro " + bairro +
                ". CEP: " + cep +
                ". " + cidade +
                " - " + estado
                ;
    }
}


