package chocostock.auxiliar;

import chocostock.colaboradores.Colaborador;
import chocostock.colaboradores.Fornecedor;
import chocostock.enums.Estados;
import chocostock.colaboradores.Cliente;
import chocostock.loja.Loja;

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

    public static void addTudo(Loja loja) {
        Colaborador cliente1 = CriarTeste.Cliente("André Silva");
        Cliente cliente2 = CriarTeste.Cliente("José");

        loja.addCliente((Cliente) cliente1);
        loja.addCliente(cliente2);

        loja.addFornecedor(new Fornecedor("Aliexpress", "", "", new Endereco(), "", "https://www.aliexpress.com"));
        loja.addFornecedor(new Fornecedor("Ricapan", "1434223088", "ricapan.marilia@gmail.com",
                new Endereco(28, "17506190", "Bassan", "Bassan", "Marília", Estados.SP), "02996613000156", ""));
        loja.addFornecedor(new Fornecedor("Marília Embalagens"));
        loja.addFornecedor(new Fornecedor("Castelo dos Doces"));
        loja.addFornecedor(new Fornecedor("Sorvemix"));
        loja.addFornecedor(new Fornecedor("Kalunga"));


    }
}
