package chocostock.itens.suprimentos;

import chocostock.colaboradores.Fornecedor;
import chocostock.interfaces.Nomeavel;

import java.time.LocalDate;

/**
 * A classe Equipamento representa um tipo específico de suprimento
 * que é utilizado como equipamento para a fábrica de chocolates.
 */
public class Equipamento extends Suprimento implements Nomeavel {
    private static int id_equipamentos = 100000;
    private final String marca;
    private final LocalDate garantia;

    public Equipamento(String nome, int quantidade, float preco, String marca, Fornecedor fornecedor, LocalDate garantia) {
        super(nome, quantidade, preco, fornecedor);
        this.setId(id_equipamentos++);
        this.marca = marca;
        this.garantia = garantia;
    }
}
