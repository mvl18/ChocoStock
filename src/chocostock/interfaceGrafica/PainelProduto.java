package chocostock.interfaceGrafica;

import chocostock.enums.TiposCaixas;
import chocostock.enums.TiposChocolates;
import chocostock.itens.produtos.Pendente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class PainelProduto extends JPanel {
    private String nome;
    private JSpinner spinnerQuantidade;

    public PainelProduto(String nome) {
        this.nome = nome;
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel labelNome = new JLabel(nome);
        spinnerQuantidade = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));

        add(labelNome);
        add(spinnerQuantidade);
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return (int) spinnerQuantidade.getValue();
    }

    public static ArrayList<Pendente> getPainelProdutos(JScrollPane painelProdutos) {
        ArrayList<Pendente> pendenteList = new ArrayList<>();

        if (painelProdutos.getViewport().getView() instanceof JPanel painelPrincipal) {
            // Percorre todos os componentes do painel principal
            for (Component componente : painelPrincipal.getComponents()) {
                if (componente instanceof JPanel linhaPainel) {
                    // Percorre todos os componentes dos painéis de linha
                    for (Component subComponente : linhaPainel.getComponents()) {
                        if (subComponente instanceof PainelProduto painelProduto) {
                            if (painelProduto.getQuantidade() > 0) {
                                pendenteList.add(new Pendente(painelProduto.getNome(), painelProduto.getQuantidade()));
                            }
                        }
                    }
                }
            }
        }
        return pendenteList;
    }

    public static JScrollPane novoEscolhaProdutos() {
        String[] nomesProdutos = Stream.concat(Arrays.stream(TiposCaixas.getTipos()),
                Arrays.stream(TiposChocolates.getTipos())).toArray(String[]::new);
        JPanel painelProdutos = new JPanel();
        painelProdutos.setLayout(new BoxLayout(painelProdutos, BoxLayout.Y_AXIS));
        int numProdutosPorLinha = 3;
        int numLinhas = (int) Math.floor(nomesProdutos.length / (double) numProdutosPorLinha);
        JPanel[] paineis = new JPanel[numLinhas];
        for (int i = 0; i < numLinhas; i++) {
            paineis[i] = new JPanel();
            paineis[i].setLayout(new BoxLayout(paineis[i], BoxLayout.X_AXIS));
            for (int j = 0; j < numProdutosPorLinha || (i == numLinhas-1 && i*numProdutosPorLinha + j < nomesProdutos.length); j++) {
                paineis[i].add(new PainelProduto(nomesProdutos[i*numProdutosPorLinha + j]));
            }
            painelProdutos.add(paineis[i]);
        }
        JScrollPane scrollPane = new JScrollPane(painelProdutos);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        return scrollPane;
    }
}
