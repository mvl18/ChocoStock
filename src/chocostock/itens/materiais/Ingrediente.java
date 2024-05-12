package chocostock.itens.materiais;

import chocostock.colaboladores.Colaborador;
import chocostock.itens.Material;

import java.util.Date;

public class Ingrediente extends Material {

    //Propriedades Herdadas:
    //ITEM: id, nome, quantidade, preco
    //MATERIAL:

    private TiposIngredientes tipo;
    private Date dataCompra;
    private Date validade;
    private Colaborador fornecedor;

    public Ingrediente(String nome, int quantidade, float preco) {
        super(nome, quantidade, preco);
    }

    public TiposIngredientes getTipo() {
        return tipo;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public Date getValidade() {
        return validade;
    }

    public Colaborador getFornecedor() {
        return fornecedor;
    }

    public void setTipo(TiposIngredientes tipo) {
        this.tipo = tipo;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public void setFornecedor(Colaborador fornecedor) {
        this.fornecedor = fornecedor;
    }
}
