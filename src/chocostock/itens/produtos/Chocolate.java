package chocostock.itens.produtos;

import chocostock.auxiliar.Processa;
import chocostock.auxiliar.Verifica;
import chocostock.enums.TiposComplementos;
import chocostock.enums.TiposChocolates;
import chocostock.enums.TiposEmbalagens;
import chocostock.interfaces.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * A classe Chocolate representa um tipo específico de produto
 * com base na classe Produto. Possuindo seu tipo, seu lote,
 * complementos do chocolate e a origem do cacau.
 */
public class Chocolate extends Produto implements AddRemovivel, Iteravel, Complementavel, Escolhivel, ValidadorInput {
    private int lote;
    private TiposChocolates tipo;
    private ArrayList<TiposComplementos> complementos;
    private String origem_cacau;

    public Chocolate(TiposChocolates tipo, int quantidade, float preco, LocalDate validade, int peso, TiposEmbalagens embalagem, int lote, String origem_cacau) {
        super(tipo.getNome(), quantidade, preco, validade, peso, embalagem);
        this.lote = lote;
        this.complementos = new ArrayList<>();
        this.origem_cacau = origem_cacau;
    }

    public Chocolate(TiposChocolates tipo, int quantidade, float preco, LocalDate validade, int peso, TiposEmbalagens embalagem, int lote, ArrayList<TiposComplementos> complementos, String origem_cacau) {
        this(tipo, quantidade, preco, validade, peso, embalagem, lote, origem_cacau);
        this.complementos = complementos;
    }

    public Chocolate() {
        this(TiposChocolates.CHOCOLATE_INTENSO, -1, -1F, null, -1, null, -1, null, null);
    }

    public int getLote() {
        return lote;
    }

    public void setLote(int lote) {
        this.lote = lote;
    }

    public TiposChocolates getTipo() {
        return tipo;
    }

    public void setTipo(TiposChocolates tipo) {
        this.tipo = tipo;
    }

    public String getOrigem_cacau() {
        return origem_cacau;
    }

    public void setOrigem_cacau(String origem_cacau) {
        this.origem_cacau = origem_cacau;
    }

    @Override
    public ArrayList<TiposComplementos> getComplementos() {
        return complementos;
    }

    public void setComplementos(ArrayList<TiposComplementos> complementos) {
        this.complementos = complementos;
    }

    public Chocolate selecionaBarra(Scanner scanner) {
        Chocolate produto = new Chocolate();
        for (TiposChocolates tipo : TiposChocolates.values()) {
            System.out.println("(" + tipo.getId() + ") - " + tipo.getNome());
        }
        // TIPO
        produto.setTipo(escolheObjeto(scanner, TiposChocolates.values(), "Por favor selecione um tipo válido.", "obrigatorio"));
        // NOME
        produto.setNome(produto.getTipo().getNome());
        // COMPLEMENTO
        if (Processa.normalizaString(getInput(scanner, "O produto tem algum adicional? Sim OU Não ", "Por favor, insira uma resposta válida. ",
                input -> input.matches("sim|nao|s|n"))).matches("sim|s")) {

            for (TiposComplementos complemento : TiposComplementos.values()) {
                System.out.println("(" + complemento.getId() + ") - " + complemento.getNome());
            }

            // Opção para sair da seleção de complementos
            System.out.println("(0) - Sair");
            System.out.println("Selecione até " + TiposComplementos.values().length + " complementos diferentes.");
            produto.setComplementos(escolheObjeto(scanner, TiposComplementos.values(), "Por favor, selecione um complemento válido.", "0",
                    TiposComplementos.values().length));
            produto.getComplementos().removeIf(Objects::isNull);
        }
        // ORIGEM CACAU
        produto.setOrigem_cacau(getInput(scanner, "Digite a origem do cacau: ", "Origem inválida, digite uma origem válida.", Verifica::isNome));
        // QUANTIDADE
        produto.setQuantidade(Integer.parseInt(getInput(scanner, "Quantidade de " + produto.getNome() + ": ",
                "Coloque um número inteiro maior que 0", Verifica::isNatural)));
        // PRECO
        produto.setPreco(Float.parseFloat(getInput(scanner, "Valor da unidade de " + produto.getNome() + ": ",
                "Coloque um valor válido", Verifica::isFloat)));
        // VALIDADE
        produto.setValidade(escolheDataFutura(scanner, "Qual a data de validade do produto? Digite uma data futura no formato DD/MM/YYYY: ",
                "Formato de data inválido. Por favor, insira uma data futura no formato DD/MM/YYYY."));
        // PESO
        produto.setPeso(Integer.parseInt(getInput(scanner, "Peso da unidade de " + produto.getNome() + " em quilos: ",
                "Coloque um valor decimal válido", Verifica::isNatural)));
        // EMBALAGEM
        System.out.println("Escolha um dos tipos de embalagem abaixo:");
        for (TiposEmbalagens tipo : TiposEmbalagens.values()) {
            System.out.println("(" + tipo.getId() + ") - " + tipo.getNome());
        }
        produto.setEmbalagem(escolheObjeto(scanner, TiposEmbalagens.values(), "Por favor selecione uma embalagem válida", "obrigatorio"));
        return produto;
    }
}
