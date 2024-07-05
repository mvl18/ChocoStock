package chocostock.interfaceGrafica;


import chocostock.enums.Cargos;
import chocostock.loja.Loja;

import javax.swing.*;

public class PaginaNovoFuncionario extends FormularioDeCadastro{

	String[] atributosFuncionario = {"Nome", "Telefone", "Email",
			"CEP", "Estado", "Cidade", "Bairro", "Rua", "Número", "Cargo", "Salario"};

	public PaginaNovoFuncionario(Loja loja){
		super("Funcionario", loja);
		addTitulo("Novo Funcionário");
		for(String atributo : atributosFuncionario){
			if(atributo.equals("Cargo")){
				addInputComponent(new JComboBox<>(Cargos.getTipos()), atributo);
			}
			else{
				addInputComponent(new JTextField(), atributo);
			}
		}
		addBotoes();
		atualizarLayout();
	}

}
