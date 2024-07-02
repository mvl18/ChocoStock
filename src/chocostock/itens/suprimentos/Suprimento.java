package chocostock.itens.suprimentos;

import chocostock.colaboradores.Fornecedor;
import chocostock.interfaces.Identificavel;
import chocostock.itens.Item;

/**
 * A classe abstrata Suprimento representa um tipo de item
 * fornecido por um fornecedor específico. Podendo esse item
 * ser um ingrediente, uma embalagem ou um equipamento.
 */
public abstract class Suprimento extends Item implements Identificavel {
    private static int id_suprimentos = 100000;
    private final int id;
    private final Fornecedor fornecedor;
    public Suprimento(String nome, int quantidade, float preco, Fornecedor fornecedor) {
        super(nome, quantidade, preco);
        this.id = id_suprimentos++;
        this.fornecedor = fornecedor;
    }

    public int getId() {
        return id;
    }

    public static int getIdSuprimento() {
        return id_suprimentos;
    }

    public static void setIdSuprimento(int id) {
        id_suprimentos = id;
    }

}
