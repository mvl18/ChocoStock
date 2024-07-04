package chocostock.interfaceGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormularioDeCadastro extends JPanel {

    private Font fonteTitulo = new Font("Tahoma", Font.BOLD, 48);
    private Font fontePequena = new Font("Tahome", Font.PLAIN, 26);
    //Componentes
    private JPanel painelRegistro;
    private ArrayList<String> labelsDosInputs;
    private ArrayList<JComponent> inputs;

    public FormularioDeCadastro() {
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

    public void addBotoes(){
        JPanel panelBotoes = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton cancelarBotao = new JButton("Cancelar");
        JButton registrarBotao = new JButton("Registrar");

        cancelarBotao.setFont(fontePequena);
        cancelarBotao.addActionListener(e -> {
            JOptionPane.showMessageDialog(getPainelRegistro(), "O registro foi cancelado.");
        });

        registrarBotao.addActionListener(e -> {
            System.out.println(getDadosDosInputs());
            JOptionPane.showMessageDialog(getPainelRegistro(), "O registro foi concluído.");
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
