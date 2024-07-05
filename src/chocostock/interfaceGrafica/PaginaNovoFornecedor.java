package chocostock.interfaceGrafica;

import chocostock.loja.Loja;

import javax.swing.*;

public class PaginaNovoFornecedor extends FormularioDeCadastro {

	String[] atributosFornecedor = new String[]{"Nome", "Telefone", "Email",
			"CEP", "Estado", "Cidade", "Bairro", "Rua", "Número", "CNPJ", "Site"};

	public PaginaNovoFornecedor(Loja loja){
		super("Fornecedor", loja);
		addTitulo("Novo Fornecedor");
		for(String atributo : atributosFornecedor){
			addInputComponent(new JTextField(), atributo);
		}
		addBotoes();
		atualizarLayout();
	}

}
