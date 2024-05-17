package chocostock.itens.materiais;

import chocostock.colaboradores.Colaborador;
import chocostock.colaboradores.Fornecedor;
import chocostock.enums.TiposIngredientes;

import java.time.LocalDate;

public class Ingrediente extends Suprimento {

    //Propriedades Herdadas:
    //ITEM: id, nome, quantidade, preco
    //SUPRIMENTO: fornecedor

    private TiposIngredientes tipo;
    private float unidade; // Em kg
    private LocalDate dataCompra;
    private LocalDate validade;
    private Colaborador fornecedor;

    public Ingrediente(String nome, int quantidade, float preco, Fornecedor fornecedor) {
        super(nome, quantidade, preco, fornecedor);
    }

    public Ingrediente() {
        this("", 0, 0.0f, new Fornecedor());
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
