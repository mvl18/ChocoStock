package chocostock.interfaceGrafica;

import chocostock.enums.TiposCaixas;
import chocostock.enums.TiposEmbalagens;
import chocostock.loja.Loja;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaNovoProduto extends FormularioDeCadastro{

    private String[] tiposProduto = {"", "Barra", "Caixa"};

    public PaginaNovoProduto(Loja loja) {
        super();
        addTitulo("Novo Produto");

        JComboBox tipoProduto = new JComboBox<>(tiposProduto);
        addInputComponent(tipoProduto, "Tipo do Produto");

        tipoProduto.addActionListener(e -> {

        });
    }

    public JPanel novoPanel(){
        JPanel p = new JPanel(new GridLayout(1, 2, 5, 5));
        JLabel lTipo = new JLabel("Tipo de Produto:");
        lTipo.setFont(getFontePequena());
        p.add(lTipo);

        JPanel botoes = new JPanel(new GridLayout(1,2,5,5));
        JRadioButton botaoBarra = new JRadioButton("Barra");
        JRadioButton botaoCaixa = new JRadioButton("Caixa");
        ButtonGroup bg = new ButtonGroup();
        botaoCaixa.setFont(getFontePequena());
        botaoBarra.setFont(getFontePequena());
        bg.add(botaoBarra);
        bg.add(botaoCaixa);
        botoes.add(botaoBarra);
        botoes.add(botaoCaixa);
        p.add(botoes);

        botaoBarra.addActionListener(e -> {
            if(botaoBarra.isSelected()){
                botaoBarra.setEnabled(false);
                botaoCaixa.setEnabled(false);
                System.out.println("Barra Selecionado");
                getInputs().add(botaoBarra);
                System.out.println(getDadosDosInputs());
            }
        });
        botaoCaixa.addActionListener(e -> {
            if(botaoCaixa.isSelected()){
                botaoBarra.setEnabled(false);
                botaoCaixa.setEnabled(false);
                System.out.println("Caixa Selecionado");
                getInputs().add(botaoCaixa);
            }
        });
        return p;
    }

    private void createFormsCaixa() {
        getPainelRegistro().add(new JLabel("Teste"));
    }

    private void createFormsBarra() {
    }


    /*
    public void alterarPagina(String chave){
        cardLayout.show(this, chave);
        paginaAtual = chave;
    }

    public JPanel createPanelInicio(){
        panelInicio = new FormularioDeCadastro();
        panelDosRadios = new JPanel(new GridLayout(1,2, 5, 5));
        lTipo.setHorizontalAlignment(SwingConstants.CENTER);
        lTipo.setVerticalAlignment(SwingConstants.NORTH);
        barraButton = new JRadioButton("Barra");
        caixaButton = new JRadioButton("Caixa");
        radioGroup = new ButtonGroup();
        //Label
        lTipo.setFont(getFontePequena());
        lTipo.setHorizontalAlignment(SwingConstants.CENTER);
        lTipo.setVerticalAlignment(SwingConstants.NORTH);
        //Botoes
        barraButton.setFont(getFontePequena());
        caixaButton.setFont(getFontePequena());
        caixaButton.setVerticalAlignment(SwingConstants.NORTH);
        barraButton.setVerticalAlignment(SwingConstants.NORTH);
        barraButton.addActionListener(e -> {
            if(!paginaAtual.equals("Barra") && barraButton.isSelected()){
                getPainelRegistro().add(createPanelBarra(), "Barra");
                alterarPagina("Barra");
            }
        });
        caixaButton.addActionListener(e -> {
            if(!paginaAtual.equals("Caixa") && caixaButton.isSelected()){
                getPainelRegistro().add(createPanelCaixa(), "Caixa");
                alterarPagina("Caixa");
            }
        });
        radioGroup.add(barraButton); radioGroup.add(caixaButton);
        panelDosRadios.add(barraButton);
        panelDosRadios.add(caixaButton);
        panelInicio.add(lTipo);
        panelInicio.add(panelDosRadios);
        return panelInicio;
    }

    private JPanel createPanelCaixa() {
        panelCaixa = new JPanel(new GridLayout(10, 1, 5, 5));
        JLabel tipoProduto = new JLabel("Produto escolhido: Caixa", SwingConstants.CENTER);
        tipoProduto.setFont(getFontePequena());
        JPanel tipoCaixa = inputComboBox(TiposCaixas.getTipos(), "Modelo de Caixa");
        JPanel quantidade = inputTextField("Quantos Pacotes");
        JPanel preco = inputTextField("Valor de 1 Pacote");
        JPanel validade = inputTextField("Data de Validade");
        JPanel pesoPorPacote = inputTextField("Peso por Pacote");
        JPanel embalagem = inputComboBox(TiposEmbalagens.getTipos(), "Modelo de Embalagem");
        JPanel lote = inputTextField("Lote do Produto");
        panelCaixa.add(tipoProduto);
        panelCaixa.add(tipoCaixa);
        panelCaixa.add(quantidade);
        panelCaixa.add(preco);
        panelCaixa.add(validade);
        panelCaixa.add(pesoPorPacote);
        panelCaixa.add(embalagem);
        panelCaixa.add(lote);

        System.out.println("Caixa Selecionada");
        return panelCaixa;
    }

    private JPanel createPanelBarra() {
        panelBarra = new JPanel();
        System.out.println("Barra Selecionada");
        return panelBarra;
    }

    @Override
    public void atualizarLayout() {
        getPainelRegistro().setLayout(new GridLayout(getInputs().size()+2,2, 5, 5));
    }

    public JPanel inputComboBox(String[] escolhas, String nome){
        JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
        JLabel label = new JLabel(nome, SwingConstants.CENTER);
        JComboBox<String> comboBox = new JComboBox<>(escolhas);
        label.setFont(getFontePequena());
        comboBox.setFont(getFontePequena());
        panel.add(label);
        panel.add(comboBox);
        return panel;
    }

    public JPanel inputTextField(String nome){
        JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
        JLabel label = new JLabel(nome, SwingConstants.CENTER);
        JTextField textField = new JTextField();
        label.setFont(getFontePequena());
        textField.setFont(getFontePequena());
        panel.add(label);
        panel.add(textField);
        return panel;
    }*/
}

 /*
    Barra ou Caixa?

    Barra
        Tipo de Chocolate
        Adicional?
            Tipo de Adicional
        Origem
        quantidade
        valor da unidade
        peso da unidade
        validade
        Tipo Embalagem
    Caixa
        Tipo Caixa
        quantidade
        valor unidade
        validade
        peso unidade
        tipo embalagem
        lote
     */