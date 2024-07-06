package chocostock.interfaceGrafica;

import chocostock.loja.Loja;

import javax.swing.*;

public class PaginaNovoCliente extends FormularioDeCadastro{

	String[] atributosCliente = new String[]{"Nome", "Telefone", "Email",
			"CEP", "Estado", "Cidade", "Bairro", "Rua", "Número"};

	public PaginaNovoCliente(Loja loja) {
		super("Cliente", loja);
		addTitulo("Novo Cliente");
		for(String atributo : atributosCliente){
			addInputComponent(new JTextField(), atributo);
		}
		addBotoes();
		atualizarLayout();
	}
}
