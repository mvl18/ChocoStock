package chocostock.itens.suprimentos;

import chocostock.colaboradores.Fornecedor;
import chocostock.enums.TiposEmbalagens;

public class Embalagem extends Suprimento {
    private TiposEmbalagens tipo_embalagem;
    private float preco_pacote;
    private int quantidade_por_pacote;

    public Embalagem(String nome, Fornecedor fornecedor, TiposEmbalagens tipo_embalagem, float preco_pacote, int quantidade_por_pacote) {
        super(nome, 0, preco_pacote / quantidade_por_pacote, fornecedor);
        this.tipo_embalagem = tipo_embalagem;
        this.preco_pacote = preco_pacote;
        this.quantidade_por_pacote = quantidade_por_pacote;
    }

    public Embalagem(String nome, int quantidade, Fornecedor fornecedor, TiposEmbalagens tipo_embalagem, float preco_pacote, int quantidade_por_pacote) {
        this(nome, fornecedor, tipo_embalagem, preco_pacote, quantidade_por_pacote);
        super.setQuantidade(quantidade);
    }
}
