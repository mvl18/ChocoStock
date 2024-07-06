package chocostock.interfaceGrafica;

import chocostock.auxiliar.Endereco;
import chocostock.auxiliar.Verifica;
import chocostock.colaboradores.Cliente;
import chocostock.colaboradores.Fornecedor;
import chocostock.colaboradores.Funcionario;
import chocostock.enums.Cargos;
import chocostock.enums.Estados;
import chocostock.interfaces.ValidadorInput;
import chocostock.loja.Loja;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class FormularioDeCadastro extends JPanel {

    private Font fonteTitulo = new Font("Tahoma", Font.BOLD, 48);
    private Font fontePequena = new Font("Tahome", Font.PLAIN, 26);
    //Componentes
    private JPanel painelRegistro;
    private ArrayList<String> labelsDosInputs;
    private ArrayList<JComponent> inputs;
    private Loja loja;
    private String tag;
    private boolean correto = true;

    public FormularioDeCadastro(String tag, Loja loja) {
        this.tag = tag;
        this.loja = loja;
        setLayout(new BorderLayout());
        inputs = new ArrayList<JComponent>();
        labelsDosInputs = new ArrayList<String>();

        painelRegistro = new JPanel();
        painelRegistro.setLayout(new GridLayout(0, 1, 5, 5));
        add(painelRegistro, BorderLayout.CENTER);
    }

    public Font getFonteTitulo() {
        return fonteTitulo;
    }

    public JPanel getPainelRegistro() {
        return painelRegistro;
    }

    public Font getFontePequena() {
        return fontePequena;
    }

    public ArrayList<JComponent> getInputs() {
        return inputs;
    }

    public ArrayList<String> getLabelsDosInputs() {
        return labelsDosInputs;
    }

    public void setPainelRegistro(JPanel panel){
        this.painelRegistro = panel;
    }

    public void addTitulo(String titulo) {
        JLabel lTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lTitulo.setFont(getFonteTitulo());
        add(lTitulo, BorderLayout.NORTH);
    }

    public void atualizarLayout(){
        painelRegistro.setLayout(new GridLayout(inputs.size()+1, 1, 5, 5));
    }

    public void addInputComponent(JComponent component, String nome){
        JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
        component.setFont(fontePequena);
        JLabel label = new JLabel(nome);
        label.setFont(fontePequena);
        getInputs().add(component);
        getLabelsDosInputs().add(nome);
        panel.add(label);
        panel.add(component);
        getPainelRegistro().add(panel);
    }

    public void validaCampo(int i, ValidadorInput.Validador validador) {
        if (validador.isValid(getDadosDosInputs().get(i)))
            getInputs().get(i).setForeground(Color.BLACK);
        else {
            getInputs().get(i).setForeground(Color.RED);
            correto = false;
        }
    }

    public void criaObjeto() {
        if (tag.equals("Cliente"))
            loja.addCliente(new Cliente(getDadosDosInputs().get(0),
                    getDadosDosInputs().get(1), getDadosDosInputs().get(2),
                    new Endereco(getDadosDosInputs().get(3),
                            Estados.parseEstado(getDadosDosInputs().get(4)),
                            getDadosDosInputs().get(5), getDadosDosInputs().get(6),
                            getDadosDosInputs().get(7),
                            Integer.parseInt(getDadosDosInputs().get(8))))); // Arrumar a recepção de estado;
        else if (tag.equals("Fornecedor"))
            loja.getEstoque().addFornecedor(new Fornecedor(getDadosDosInputs().get(0),
                    getDadosDosInputs().get(1), getDadosDosInputs().get(2),
                    new Endereco(getDadosDosInputs().get(3),
                            Estados.parseEstado(getDadosDosInputs().get(4)),
                            getDadosDosInputs().get(5), getDadosDosInputs().get(6),
                            getDadosDosInputs().get(7),
                            Integer.parseInt(getDadosDosInputs().get(8))), getDadosDosInputs().get(9), getDadosDosInputs().get(10)));
        else if (tag.equals("Funcionario"))
            loja.addFuncionario(new Funcionario(getDadosDosInputs().get(0),
                    getDadosDosInputs().get(1), getDadosDosInputs().get(2),
                    new Endereco(getDadosDosInputs().get(3),
                            Estados.parseEstado(getDadosDosInputs().get(4)),
                            getDadosDosInputs().get(5), getDadosDosInputs().get(6),
                            getDadosDosInputs().get(7),
                            Integer.parseInt(getDadosDosInputs().get(8))), Cargos.parseCargo(getDadosDosInputs().get(9)),
                    Float.parseFloat(getDadosDosInputs().get(10))));
    }

    public void addBotoes(){
        JPanel panelBotoes = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton cancelarBotao = new JButton("Cancelar");
        JButton registrarBotao = new JButton("Registrar");

        cancelarBotao.setFont(fontePequena);
        cancelarBotao.addActionListener(e -> {
            JOptionPane.showMessageDialog(getPainelRegistro(), "O registro foi cancelado.");
        });

        registrarBotao.addActionListener(e -> {
            correto = true;
            System.out.println(getDadosDosInputs());
            for (int i = 0; i < getLabelsDosInputs().size(); i++) {
                switch (getLabelsDosInputs().get(i)) {
                    case "Telefone" -> validaCampo(i, Verifica::isTelefone);
                    case "Email" -> validaCampo(i, Verifica::isEmail);
                    case "CNPJ" -> validaCampo(i, Verifica::isCnpj);
                    case "Site" -> validaCampo(i, Verifica::isSite);
                    case "CEP" -> validaCampo(i, Verifica::isCep);
                    case "Número" -> validaCampo(i, Verifica::isNatural);
                    case "Quantidade" -> validaCampo(i, Verifica::isNatural);
                    case "Quantos kg por Unidade" -> validaCampo(i, Verifica::isFloat);
                    case "Preço" -> validaCampo(i, Verifica::isFloat);
                    case "Data de Compra" -> validaCampo(i, Verifica::isData);
                    case "Data de Validade" -> validaCampo(i, Verifica::isDataFutura);
                    case "Preço por pacote" -> validaCampo(i, Verifica::isFloat);
                    case "Quantidade por pacote" -> validaCampo(i, Verifica::isNatural);
                }
            }
            if (correto) {
                criaObjeto();
                JOptionPane.showMessageDialog(getPainelRegistro(), "O registro foi concluído.");
            }
        });
        registrarBotao.setFont(fontePequena);

        panelBotoes.add(cancelarBotao);
        panelBotoes.add(registrarBotao);
        getPainelRegistro().add(panelBotoes);
    }

    public ArrayList<String> getDadosDosInputs(){
        ArrayList<String> dados = new ArrayList<String>();
        String s;
        for(JComponent comp : inputs){
            if(comp instanceof JTextField){
                s = ((JTextField) comp).getText();
                dados.add(s);
            }
            if(comp instanceof JComboBox<?>){
                s = ((JComboBox<?>) comp).getSelectedItem().toString();
                dados.add(s);
            }
            if(comp instanceof JRadioButton){
                boolean b = ((JRadioButton) comp).isSelected();
                dados.add(((Boolean)b).toString());
            }
        }
        return dados;
    }

}