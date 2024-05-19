package chocostock.itens.suprimentos;

import chocostock.colaboradores.Fornecedor;
import chocostock.interfaces.Nomeavel;

import java.time.LocalDate;

public class Equipamento extends Suprimento implements Nomeavel {
    private static int id_equipamentos = 100000;
    private String marca;
    private String cnpj_fornecedor;
    private LocalDate garantia;

    public Equipamento(String nome, int quantidade, float preco, String marca, Fornecedor fornecedor, LocalDate garantia) {
        super(nome, quantidade, preco, fornecedor);
        this.setId(id_equipamentos++);
        this.marca = marca;
        this.garantia = garantia;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCnpj_fornecedor() {
        return cnpj_fornecedor;
    }

    public void setCnpj_fornecedor(String cnpj_fornecedor) {
        this.cnpj_fornecedor = cnpj_fornecedor;
    }

}
