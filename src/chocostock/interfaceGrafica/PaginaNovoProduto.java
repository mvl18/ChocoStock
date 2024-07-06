package chocostock.interfaceGrafica;

import chocostock.enums.TiposCaixas;
import chocostock.enums.TiposComplementos;
import chocostock.enums.TiposEmbalagens;
import chocostock.loja.Loja;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaNovoProduto extends FormularioDeCadastro implements ActionListener {

    private String[] tiposProduto = {"", "Barra", "Caixa"};
    private String[] atributosCaixa = {"Quantidade", "Preco", "Validade", "Peso", "Embalagem", "Lote"};
    private String[] atrbutosBarra = {"Complemento", "Origem", "Quantidade", "Preço", "Validade", "Peso", "Embalagem"};

    private JComboBox tipoProduto;

    public PaginaNovoProduto(Loja loja) {
        super("Produto", loja);
        addTitulo("Novo Produto");
        createFormsInicial();
    }

    private void createFormsInicial() {
        getPainelRegistro().setLayout(new GridLayout(7, 1, 5, 5));
        tipoProduto = new JComboBox<>(tiposProduto);
        addInputComponent(tipoProduto, "Tipo");
        tipoProduto.addActionListener(this);
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
        addInputComponent(tipoProduto, "Tipo");
        addInputComponent(new JTextField(), "Quantidade");
        addInputComponent(new JTextField(), "Preço");
        addInputComponent(new JTextField(), "Validade");
        addInputComponent(new JTextField(), "Peso");
        addInputComponent(new JComboBox<>(TiposEmbalagens.getTipos()), "Embalagem");
        addInputComponent(new JTextField(), "Lote");
        addBotoes();
        atualizarLayout();
    }

    private void createFormsBarra() {
        addInputComponent(tipoProduto, "Tipo");
        createInputComplemento();
        addInputComponent(new JTextField(), "Origem");
        addInputComponent(new JTextField(), "Quantidade");
        addInputComponent(new JTextField(), "Preço");
        addInputComponent(new JTextField(), "Validade");
        addInputComponent(new JTextField(), "Peso");
        addInputComponent(new JComboBox<>(TiposEmbalagens.getTipos()), "Embalagem");
        addBotoes();
        atualizarLayout();
    }

    private void createInputComplemento() {
        JPanel inputComplemento = new JPanel(new GridLayout(1, 2));
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Adicionar");
        JLabel lNome = new JLabel("Complementos");
        menu.setPreferredSize(new Dimension(getPainelRegistro().getWidth()/2, menubar.getHeight()));
        menubar.add(menu);
        menu.setFont(getFontePequena());
        menu.setHorizontalAlignment(SwingConstants.CENTER);
        lNome.setFont(getFontePequena());
        menu.add(new JCheckBoxMenuItem("Teste"));
        for(int i = 0; i < TiposComplementos.values().length; i++){
            System.out.println("Teste");
            String tipo = TiposComplementos.values()[i].getNome();
            JCheckBoxMenuItem check = new JCheckBoxMenuItem(tipo);
            check.setPreferredSize(new Dimension(getPainelRegistro().getWidth()/2, check.getHeight()));
            menu.add(new JCheckBoxMenuItem(tipo));
        }
        inputComplemento.add(lNome);
        inputComplemento.add(menubar);
        getPainelRegistro().add(inputComplemento);
        getInputs().add(menu);
        getLabelsDosInputs().add("Complementos");
    }

    private void limparPagina() {
        getPainelRegistro().removeAll();
        getInputs().clear();
        getLabelsDosInputs().clear();
    }

//    @Override
//    public void limparInputs() {
//        limparPagina();
//        createFormsInicial();
//        getPainelRegistro().revalidate();
//        getPainelRegistro().repaint();
//    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Evento de ação");
        if(e.getSource() == tipoProduto){
            System.out.println(tipoProduto.getSelectedItem());
            if(tipoProduto.getSelectedItem().equals("Barra")){
                limparPagina();
                createFormsBarra();
                tipoProduto.setEnabled(false);
                getPainelRegistro().revalidate();
                getPainelRegistro().repaint();
            }
            if (tipoProduto.getSelectedItem().equals("Caixa")) {
                limparPagina();
                createFormsCaixa();
                tipoProduto.setEnabled(false);
                getPainelRegistro().revalidate();
                getPainelRegistro().repaint();
            }
        }
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