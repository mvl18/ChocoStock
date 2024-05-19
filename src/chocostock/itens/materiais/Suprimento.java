package chocostock.itens.materiais;

import chocostock.colaboradores.Fornecedor;
import chocostock.itens.Item;

public abstract class Suprimento extends Item {
    private Fornecedor fornecedor;
    public Suprimento(String nome, int quantidade, float preco, Fornecedor fornecedor) {
        super(nome, quantidade, preco);
        this.fornecedor = fornecedor;
    }
}
