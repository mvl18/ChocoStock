package chocostock.auxiliar;

import chocostock.Endereco;
import chocostock.enuns.Estados;
import chocostock.colaboradores.Cliente;

import java.util.Random;

public class CriarTeste {
    public static Cliente Cliente() {
        return new Cliente("Michelangelo", "11964729106", "mike@gmail.com", CriarTeste.Endereco());
    }

    public static Cliente Cliente(String nome) {
        Random rand = new Random();
        int numero = rand.nextInt(99999999);
        return new Cliente(nome, "119" + String.valueOf(numero), nome.replaceAll("\\s", "").toLowerCase() + "@gmail.com", CriarTeste.Endereco());
    }

    public static Endereco Endereco() {
        Random rand = new Random();
        int numero = rand.nextInt(99999) + 1;
        return new Endereco(numero,"1403128", "Pitágoras", "Limoeiro", "Gotham", Estados.SP);
    }
}
