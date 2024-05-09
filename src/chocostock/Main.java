package chocostock;

import chocostock.colaboladores.Cliente;
import chocostock.colaboladores.Colaborador;
import chocostock.colaboladores.Funcionario;

public class Main {
    public static void main(String[] args) {

        System.out.println("Bem-vindo ao ChocoStock! O doce controle de vendas e estoque");

        // TESTES INICIAIS MARAOLT
        Endereco endereco1 = new Endereco("17154-1283", "Rua Alves Brito", "Barao Geraldo", "Campinas", "SP");
        System.out.println(endereco1);
        Colaborador cliente1 = new Cliente("Andre", "1923949394", "andre.andre@gmail.com", endereco1);
        Cliente cliente2 = new Cliente("Jose", "1132932843", "jose.jose@gmail.com", endereco1);
        System.out.println(cliente1);
        System.out.println(cliente2);
    }
}