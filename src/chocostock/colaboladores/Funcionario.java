package chocostock.colaboladores;

import chocostock.Endereco;

public class Funcionario extends Colaborador {
    private static int id_funcionarios = 100000;
    private final int id;
    private String cargo;
    private float salario;

    public Funcionario(String nome, String telefone, String email, Endereco endereco, String cargo) {
        super(nome, telefone, email, endereco);
        this.id = id_funcionarios++;
        this.cargo = cargo;
        defineSalario();
    }

    private void defineSalario() {
        switch (cargo) {
            case "gerente":
                this.salario = 5000.0F;
            case "chocolateiro":
                this.salario = 4000.0F;
            case "analista":
                this.salario = 4500.0F;
            case "assistente":
                this.salario = 3000.0F;
            case "estagiario":
                this.salario = 1500.0F;
            default:
                this.salario = 0.0F;
        }
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }
}
