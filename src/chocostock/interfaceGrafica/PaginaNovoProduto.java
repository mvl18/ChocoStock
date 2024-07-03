package chocostock.interfaceGrafica;

import chocostock.enums.TiposCaixas;
import chocostock.loja.Loja;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class PaginaNovoProduto extends FormularioDeCadastro{

    private String paginaAtual;
    private CardLayout cardLayout;                                //0 -> Inicio, 1 -> Barra, 2 -> Caixa
    private JLabel lTipo;
    private JPanel panelDosRadios, panelBarra, panelCaixa;
    private JRadioButton barraButton, caixaButton;
    private ButtonGroup radioGroup;

    public PaginaNovoProduto(Loja loja) {
        super("Novo Produto");
        cardLayout = new CardLayout();
        getPainelRegistro().setLayout(cardLayout);
        getPainelRegistro().add(createPanelInicio(), "Inicio");
        alterarPagina("Inicio");
    }

    public void alterarPagina(String chave){
        cardLayout.show(getPainelRegistro(), chave);
        paginaAtual = chave;
    }

    public JPanel createPanelInicio(){
        JPanel panelInicio = new JPanel(new GridLayout(1, 2, 10, 10));
        lTipo = new JLabel("Tipo do Produto");
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
        panelCaixa = new JPanel(new GridLayout(5, 1, 10, 10));
        JLabel tipoProduto = new JLabel("Produto escolhido: Caixa", SwingConstants.CENTER);
        tipoProduto.setFont(getFontePequena());
        panelCaixa.add(tipoProduto);
        //Tipo da Caixa;
        JPanel tipoCaixa = new JPanel(new GridLayout(1, 2, 5, 5));
        JLabel lTipoCaixa = new JLabel("Modelo de Caixa", SwingConstants.CENTER);
        JComboBox<String> cbTipoCaixa = new JComboBox<>(TiposCaixas.getTipos());
        lTipoCaixa.setFont(getFontePequena());
        cbTipoCaixa.setFont(getFontePequena());
        tipoCaixa.add(lTipoCaixa);
        tipoCaixa.add(cbTipoCaixa);
        //
        panelCaixa.add(tipoProduto);
        panelCaixa.add(tipoCaixa);

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