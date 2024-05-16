package chocostock.itens.materiais;

import chocostock.colaboradores.Fornecedor;
import chocostock.enums.TiposEmbalagens;

public class Embalagem {
    private TiposEmbalagens tipo_embalagem;
    private Fornecedor fornecedor;
    private double preco_pacote;
    private int quantidade_por_pacote;
    private int quantidade_em_estoque;

    public Embalagem(TiposEmbalagens tipo_embalagem, Fornecedor fornecedor, double preco_pacote, int quantidade_por_pacote, int quantidade_em_estoque) {
        this.tipo_embalagem = tipo_embalagem;
        this.fornecedor = fornecedor;
        this.preco_pacote = preco_pacote;
        this.quantidade_por_pacote = quantidade_por_pacote;
        this.quantidade_em_estoque = quantidade_em_estoque;
    }
}
