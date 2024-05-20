package chocostock.colaboradores;

import chocostock.auxiliar.Endereco;
import chocostock.interfaces.Identificavel;

/**
 * A classe Funcionario representa um funcionário que herda de Colaborador.
 * Esta classe gerencia informações específicas do funcionário, como um
 * identificador único, cargo e salário. <br>
 * Implementa o método "defineSalario".
 */
public class Funcionario extends Colaborador implements Identificavel {
    private static int id_funcionarios = 100000;
    private final int id;
    private final String cargo;
    private final float salario;

    public Funcionario(String nome, String telefone, String email, Endereco endereco, String cargo, float salario) {
        super(nome, telefone, email, endereco);
        this.salario = salario;
        this.id = id_funcionarios++;
        this.cargo = cargo;
    }

    @Override
    public int getId() {
        return id;
    }

    public String toString() {
        return id + ". " + super.toString() +
                "\nCargo: " + cargo + " - R$" + salario +
                "\n";
    }
}
