package chocostock.itens;

import chocostock.interfaces.Nomeavel;
import chocostock.colaboradores.Empresa;

public class Equipamento extends Item implements Nomeavel {
    private static int id_equipamentos = 100000;
    private String marca;
    private Empresa fornecedor;

    public Equipamento(String nome, int quantidade, float preco, String marca, Empresa fornecedor) {
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

    public Empresa getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Empresa fornecedor) {
        this.fornecedor = fornecedor;
    }

}
