package chocostock.auxiliar;

import chocostock.colaboradores.Colaborador;
import chocostock.colaboradores.Fornecedor;
import chocostock.enums.Estados;
import chocostock.colaboradores.Cliente;
import chocostock.enums.TiposEmbalagens;
import chocostock.interfaces.Escolhivel;
import chocostock.itens.suprimentos.Embalagem;
import chocostock.loja.Loja;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CriarTeste implements Escolhivel {
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

        loja.getEstoque().addFornecedor(new Fornecedor("Aliexpress", "", "", new Endereco(), "", "https://www.aliexpress.com"));
        loja.getEstoque().addFornecedor(new Fornecedor("Ricapan", "1434223088", "ricapan.marilia@gmail.com",
                new Endereco(28, "17506190", "Bassan", "Bassan", "Marília", Estados.SP), "02996613000156", ""));
        loja.getEstoque().addFornecedor(new Fornecedor("Marília Embalagens"));
        loja.getEstoque().addFornecedor(new Fornecedor("Castelo dos Doces"));
        loja.getEstoque().addFornecedor(new Fornecedor("Sorvemix"));
        loja.getEstoque().addFornecedor(new Fornecedor("Kalunga"));

        ArrayList<Float> valores = new ArrayList<>(Arrays.asList(1.80f, 3.40f, 3.85f, 4.40f, 6.50f, 4.50f, 12.80f, 9.90f, 5.20f, 4.25f, 4.15f, 7.35f, 9.90f, 18.90f));
        ArrayList<Integer> quantidades = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 50, 300, 300, 50, 100, 1, 1, 10, 50));

        for (TiposEmbalagens embalagem : TiposEmbalagens.values()) {
            loja.getEstoque().addEmbalagem(new Embalagem(embalagem.getNome(), loja.getEstoque().getFornecedores().get(0),
                    embalagem, valores.remove(0), quantidades.remove(0)));
        }
    }
}
