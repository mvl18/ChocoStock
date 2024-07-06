package chocostock.colaboradores;

import chocostock.auxiliar.Endereco;
import chocostock.auxiliar.Verifica;
import chocostock.enums.Cargos;
import chocostock.interfaces.Escolhivel;
import chocostock.interfaces.Identificavel;
import chocostock.interfaces.ValidadorInput;

import java.util.Scanner;

/**
 * A classe Funcionario representa um funcionário que herda de Colaborador.
 * Esta classe gerencia informações específicas do funcionário, como um
 * identificador único, cargo e salário. <br>
 * Implementa o método "defineSalario".
 */
public class Funcionario extends Colaborador implements Identificavel {
    private static int id_funcionarios = 100000;
    private final int id;
    private Cargos cargo;
    private float salario;

    public Funcionario(String nome, String telefone, String email, Endereco endereco, Cargos cargo, float salario) {
        super(nome, telefone, email, endereco);
        this.salario = salario;
        this.id = id_funcionarios++;
        this.cargo = cargo;
    }

    public Funcionario() {
        this("", "","", new Endereco(), null, -1);
    }

    @Override
    public int getId() {
        return id;
    }

    public static int getIdFuncionario() {
        return id_funcionarios;
    }

    public static void setIdFuncionario(int id) {
        id_funcionarios = id;
    }

    public String toString() {
        return id + ". " + super.toString() +
                "Cargo: " + cargo.getNome() + " - R$" + salario +
                "\n";
    }

    public void setCargo(Cargos cargo) {
        this.cargo = cargo;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }


    public static Funcionario novoFuncionario(Scanner scanner) {
        Funcionario funcionario = new Funcionario();
        System.out.println("Cadastrando novo funcionário: ");
        // Solicitação do nome do funcionário
        funcionario.setNome(ValidadorInput.getInput(scanner, "Nome do funcionário: ", "Nome inválido.", Verifica::isNome));
        // Solicitação do telefone do funcionário
        funcionario.setTelefone(ValidadorInput.getInput(scanner, "Telefone do funcionário: ", "Insira um número válido, não esqueça o DDD!",
                Verifica::isTelefone).replaceAll("\\D", ""));
        // Solicitação do email do funcionário
        funcionario.setEmail(ValidadorInput.getInput(scanner, "Email do funcionário: ", "Insira um email válido!", Verifica::isEmail));
        // Solicitação do endereço do funcionário
        System.out.println("Criando endereço: ");
        Endereco endereco = new Endereco();
        funcionario.setEndereco(endereco.criaEndereco(scanner));
        // Solicitação do cargo do funcionário
        System.out.println("Escolha um cargo dentre os abaixo:");
        for (Cargos cargo : Cargos.values()) {
            System.out.println("(" + cargo.getId() + ") - " + cargo.getNome());
        }
        funcionario.setCargo(Escolhivel.escolheObjeto(scanner, Cargos.values(), "Cargo inválido. Digite um número válido ou o nome do cargo.", "obrigatorio"));
        // Solicitação de salário do funcionário
        String salario = ValidadorInput.getInput(scanner, "Salário do funcionário: ", "Insira um salário válido, indicando os centavos!", Verifica::isFloat);
        funcionario.setSalario(Float.parseFloat(String.format("%.2f", Float.parseFloat(salario))));

        System.out.println("Novo funcionário adicionado: " + funcionario);

        return funcionario;
    }
}
