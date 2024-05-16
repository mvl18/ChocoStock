package chocostock.itens;

import chocostock.colaboradores.Fornecedor;
import chocostock.interfaces.Nomeavel;

public class Equipamento extends Item implements Nomeavel {
    private static int id_equipamentos = 100000;
    private String marca;
    private Fornecedor fornecedor;

    public Equipamento(String nome, int quantidade, float preco, String marca, Fornecedor fornecedor) {
        super(nome, quantidade, preco);
        this.setId(id_equipamentos++);
        this.marca = marca;
        this.fornecedor = fornecedor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

}
