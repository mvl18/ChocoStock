package chocostock.itens.suprimentos;

import chocostock.colaboradores.Fornecedor;
import chocostock.itens.Item;

/**
 * A classe abstrata Suprimento representa um tipo de item
 * fornecido por um fornecedor específico. Podendo esse item
 * ser um ingrediente, uma embalagem ou um equipmento.
 */
public abstract class Suprimento extends Item {
    private Fornecedor fornecedor;
    public Suprimento(String nome, int quantidade, float preco, Fornecedor fornecedor) {
        super(nome, quantidade, preco);
        this.fornecedor = fornecedor;
    }
}
