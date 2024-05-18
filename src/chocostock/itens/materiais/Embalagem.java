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

    public Embalagem() {
        this.fornecedor = new Fornecedor();
        this.preco_pacote = 0.0f;
        this.quantidade_por_pacote = 0;
        this.quantidade_em_estoque = 0;
    }

    public void setTipo(TiposEmbalagens tipo){
        tipo_embalagem = tipo;
    }

    public void setPreco_pacote(float preco){
        preco_pacote = preco;
    }

    public void setQuantidade_por_pacote(int quantidade){
        quantidade_por_pacote = quantidade;
    }

    public void setQuantidade_em_estoque(int quantidade){
        quantidade_em_estoque = quantidade;
    }

    public TiposEmbalagens getTipo_embalagem() {
        return tipo_embalagem;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public double getPreco_pacote() {
        return preco_pacote;
    }

    public int getQuantidade_por_pacote() {
        return quantidade_por_pacote;
    }

    public int getQuantidade_em_estoque(){
        return quantidade_em_estoque;
    }

    @Override
    public String toString(){
        String out = "";
        out += tipo_embalagem.toString() + "\n" +
                "Fornecedor: " + fornecedor + "\n" +
                "Quantidade por pacote: " + quantidade_por_pacote + "\n" +
                "Quantidade em estoque: " + quantidade_em_estoque;
        return out;
    }
}
