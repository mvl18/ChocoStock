package chocostock.itens.suprimentos;

import chocostock.colaboradores.Fornecedor;
import chocostock.enums.TiposEmbalagens;

public class Embalagem extends Suprimento {
    private TiposEmbalagens tipo_embalagem;
    private float preco_pacote;
    private int quantidade_por_pacote;
    private String cnpj_fornecedor;

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

    public Embalagem(){
        this("",0, null, null, 0, 10);
    }

    public void setTipo_embalagem(TiposEmbalagens tipo_embalagem) {
        this.tipo_embalagem = tipo_embalagem;
    }

    public TiposEmbalagens getTipo_embalagem() {
        return tipo_embalagem;
    }

    public void setPreco_pacote(float preco_pacote){
        this.preco_pacote = preco_pacote;
    }

    public void setQuantidade_por_pacote(int quantidade_por_pacote){
        this.quantidade_por_pacote = quantidade_por_pacote;
    }

    public int getQuantidade_por_pacote() {
        return quantidade_por_pacote;
    }

    public float getPreco_pacote() {
        return preco_pacote;
    }

    public void setCnpj_fornecedor(String cnpj_fornecedor) {
        this.cnpj_fornecedor = cnpj_fornecedor;
    }

}
