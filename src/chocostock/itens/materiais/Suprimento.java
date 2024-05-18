package chocostock.itens.materiais;

import chocostock.colaboradores.Fornecedor;
import chocostock.itens.Item;

public class Suprimento extends Item {
    private String cpnj_fornecedor;
    public Suprimento(String nome, int quantidade, float preco, String cpnj_fornecedor) {
        super(nome, quantidade, preco);
        this.cpnj_fornecedor = cpnj_fornecedor;
    }
}
