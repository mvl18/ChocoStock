package chocostock.itens.produtos;

import chocostock.auxiliar.Verifica;
import chocostock.enums.TiposCaixas;
import chocostock.enums.TiposEmbalagens;
import chocostock.interfaces.Escolhivel;
import chocostock.interfaces.ValidadorInput;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * A classe Caixa representa um tipo específico de produto com base
 * na classe Produto. Possuindo seu tipo e seu lote.
 */
public class Caixa extends Produto implements ValidadorInput, Escolhivel {
    private TiposCaixas tipo;
    private int lote;

    public Caixa(TiposCaixas tipo, int quantidade, float preco, LocalDate validade, int peso, TiposEmbalagens embalagem, int lote) {
        super(tipo.getNome(), quantidade, preco, validade, peso, embalagem);
        this.tipo = tipo;
        this.lote = lote;
    }

    public Caixa() {
        this(TiposCaixas.CAIXA_ASORTI_P, -1, -1.0F, null, -1, null, -1);
    }

    public TiposCaixas getTipo() {
        return tipo;
    }

    public void setTipo(TiposCaixas tipo) {
        this.tipo = tipo;
    }

    public void setLote(int lote) {
        this.lote = lote;
    }

    public Caixa selecionaCaixa(Scanner scanner) {
        Caixa caixa = new Caixa();
        for (TiposCaixas tipo : TiposCaixas.values()) {
            System.out.println("(" + tipo.getId() + ") - " + tipo.getNome());
        }
        // TIPO
        caixa.setTipo(Escolhivel.escolheObjeto(scanner, TiposCaixas.values(), "Por favor selecione um tipo válido.", "obrigatorio"));
        // NOME
        caixa.setNome(caixa.getTipo().getNome());
        // QUANTIDADE
        caixa.setQuantidade(Integer.parseInt(ValidadorInput.getInput(scanner, "Quantidade de " + caixa.getNome() + ": ",
                "Coloque um número inteiro maior que 0", Verifica::isNatural)));
        // PRECO
        caixa.setPreco(Float.parseFloat(ValidadorInput.getInput(scanner, "Valor da unidade de " + caixa.getNome() + ": ",
                "Coloque um valor válido", Verifica::isFloat)));
        // VALIDADE
        caixa.setValidade(Escolhivel.escolheDataFutura(scanner, "Qual a data de validade do caixa? Digite uma data futura no formato DD/MM/YYYY: ",
                "Formato de data inválido. Por favor, insira uma data futura no formato DD/MM/YYYY."));
        // PESO UNITARIO
        caixa.setPeso(Integer.parseInt(ValidadorInput.getInput(scanner, "Peso da unidade de " + caixa.getNome() + " em quilos: ",
                "Coloque um valor decimal válido", Verifica::isNatural)));
        // EMBALAGEM
        System.out.println("Escolha um dos tipos de embalagem abaixo:");
        for (TiposEmbalagens tipo : TiposEmbalagens.values()) {
            System.out.println("(" + tipo.getId() + ") - " + tipo.getNome());
        }
        caixa.setEmbalagem(Escolhivel.escolheObjeto(scanner, TiposEmbalagens.values(), "Por favor selecione uma embalagem válida", "obrigatorio"));
        // LOTE
        caixa.setLote(Integer.parseInt(ValidadorInput.getInput(scanner, "Digite o lote de " + caixa.getNome() + ": ",
                "Coloque um número inteiro maior que 0", Verifica::isNatural)));

        return caixa;
    }


}
