package chocostock.itens.materiais;

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
    private String cnpj_fornecedor;

    public Ingrediente(String nome, int quantidade, float preco, String cnpj_fornecedor) {
        super(nome, quantidade, preco, cnpj_fornecedor);
    }

    public Ingrediente() {
        this("", 0, 0.0f, "");
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

    public String getCnpj_fornecedor() {
        return cnpj_fornecedor;
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

    public void setCnpj_fornecedor(String cnpj_fornecedor) {
        this.cnpj_fornecedor = cnpj_fornecedor;
    }
}
