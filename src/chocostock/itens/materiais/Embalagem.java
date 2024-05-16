package chocostock.itens.materiais;

import chocostock.colaboradores.Fornecedor;
import chocostock.enums.TiposEmbalagens;

public class Embalagem {
    private TiposEmbalagens tipo_embalagem;
    private Fornecedor fornecedor;
    private int preco_embalagem;
    private int quantidade_embalagem;
    private int quantidade_estoque;

    Embalagem(TiposEmbalagens tipo_embalagem, Fornecedor fornecedor) {
        this.tipo_embalagem = tipo_embalagem;
        this.fornecedor = fornecedor;
    }
}
