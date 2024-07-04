package chocostock.interfaceGrafica;

import chocostock.enums.TiposEmbalagens;
import chocostock.loja.Loja;

import javax.swing.*;

public class PaginaNovaEmbalagem extends FormularioDeCadastro{

    private String[] atributosEmbalagem = {
            "Tipo", "Quantidade", "Preco por pacote",
            "Quantidade por Pacote", "Fornecedor"};

    public PaginaNovaEmbalagem(Loja loja){
        super();
        addTitulo("Nova Embalagem");
        addInputComponent(new JComboBox<>(TiposEmbalagens.getTipos()), "Tipo da Embalagem");
        for(int i = 1; i < atributosEmbalagem.length-1; i++){
            addInputComponent(new JTextField(), atributosEmbalagem[i]);
        }
        addInputComponent(new JComboBox<>(loja.getEstoque().getArrayFornecedores()), "Fornecedor");
        addBotoes();
        atualizarLayout();
    }
}
