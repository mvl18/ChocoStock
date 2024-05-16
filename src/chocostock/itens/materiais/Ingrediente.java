package chocostock.itens.materiais;

import chocostock.colaboradores.Colaborador;
import chocostock.enums.TiposIngredientes;

import java.time.LocalDate;
import java.util.Date;

public class Ingrediente extends Material {

    //Propriedades Herdadas:
    //ITEM: id, nome, quantidade, preco
    //MATERIAL:

    private TiposIngredientes tipo;
    private float unidade; // Em kg
    private LocalDate dataCompra;
    private LocalDate validade;
    private Colaborador fornecedor;

    public Ingrediente(String nome, int quantidade, float preco) {
        super(nome, quantidade, preco);
    }

    public Ingrediente() {
        this("", 0, 0.0f);
    }

    public TiposIngredientes getTipo() {
        return tipo;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public Colaborador getFornecedor() {
        return fornecedor;
    }

    public float getUnidade() {
        return unidade;
    }

    public void setTipo(TiposIngredientes tipo) {
        this.tipo = tipo;
    }

    public void setUnidade(float unidade) {
        this.unidade = unidade;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public void setFornecedor(Colaborador fornecedor) {
        this.fornecedor = fornecedor;
    }
}
