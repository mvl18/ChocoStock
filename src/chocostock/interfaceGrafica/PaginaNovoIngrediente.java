package chocostock.interfaceGrafica;

import chocostock.enums.TiposIngredientes;
import chocostock.loja.Loja;

import javax.swing.*;

public class PaginaNovoIngrediente extends FormularioDeCadastro {

    private final String[] atributosIngrediente = {
            "Tipo", "Quantidade", "Quantos kg por Unidade",
            "Preço", "Data de Compra", "Data de Validade", "Fornecedor"};

    public PaginaNovoIngrediente(Loja loja){
        super("Novo Ingrediente");
        addInputComponent(new JComboBox<String>(TiposIngredientes.getTipos()),
                "Tipo do Ingrediente");
        for(int i = 1; i < atributosIngrediente.length-1; i++){
            addInputComponent(new JTextField(), atributosIngrediente[i]);
        }
        String[] fornecedores = loja.getEstoque().getArrayFornecedores();
        addInputComponent(new JComboBox<String>(fornecedores), "Selecione o fornecedor:");
        addBotoes();
        atualizarLayout();
    }
}
