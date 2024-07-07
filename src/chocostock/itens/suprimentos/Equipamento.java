package chocostock.itens.suprimentos;

import chocostock.colaboradores.Fornecedor;
import chocostock.interfaces.Nomeavel;


/**
 * A classe Equipamento representa um tipo específico de suprimento
 * que é utilizado como equipamento para a fábrica de chocolates.
 */
public class Equipamento extends Suprimento implements Nomeavel {
    public Equipamento(String nome, int quantidade, float preco, Fornecedor fornecedor) {
        super(nome, quantidade, preco, fornecedor);
    }
}
