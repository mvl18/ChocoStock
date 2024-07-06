package chocostock.interfaceGrafica;

import chocostock.itens.produtos.Pendente;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PainelProduto extends JPanel {
    private Pendente produto;
    private JSpinner spinnerQuantidade;

    public PainelProduto(Pendente produto) {
        this.produto = produto;
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel labelNome = new JLabel(produto.getNome());
        spinnerQuantidade = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));

        add(labelNome);
        add(spinnerQuantidade);
    }

    public Pendente getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return (int) spinnerQuantidade.getValue();
    }
}
