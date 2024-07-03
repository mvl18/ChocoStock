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
    private JLabel titulo;
    private JPanel painelRegistro;
    private ArrayList<String> labelsDosInputs;
    private ArrayList<JComponent> inputs;

    public FormularioDeCadastro() {
        setLayout(new BorderLayout());
        inputs = new ArrayList<JComponent>();
        labelsDosInputs = new ArrayList<String>();

        titulo = new JLabel("", SwingConstants.CENTER);
        titulo.setFont(fonteTitulo);
        add(titulo, BorderLayout.NORTH);

        painelRegistro = new JPanel();
        painelRegistro.setLayout(new GridLayout(0, 2, 5, 5));
        add(painelRegistro, BorderLayout.CENTER);
    }

    public FormularioDeCadastro(String tituloForms) {
        this();
        titulo.setText(tituloForms);
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

    public void atualizarLayout(){
        painelRegistro.setLayout(new GridLayout(inputs.size()+1, 2, 5, 5));
    }

    public void addInputComponent(JComponent component, String nome){
        component.setFont(fontePequena);
        JLabel label = new JLabel(nome);
        label.setFont(fontePequena);
        inputs.add(component);
        labelsDosInputs.add(nome);
        painelRegistro.add(label);
        painelRegistro.add(component);
    }

    public void addBotoes(){
        JButton cancelarBotao = new JButton("Cancelar");
        cancelarBotao.setFont(fontePequena);
        cancelarBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(painelRegistro,
                        "O registro foi cancelado.");
            }
        });
        JButton registrarBotao = new JButton("Registrar");
        registrarBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(getDadosDosInputs());
                JOptionPane.showMessageDialog(painelRegistro,
                        "O registro foi concluído.");
            }
        });
        registrarBotao.setFont(fontePequena);
        painelRegistro.add(cancelarBotao);
        painelRegistro.add(registrarBotao);
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
        }
        return dados;
    }

}
