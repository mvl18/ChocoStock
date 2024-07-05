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
                    new Endereco(Integer.parseInt(getDadosDosInputs().get(3)),
                            getDadosDosInputs().get(4), getDadosDosInputs().get(5),
                            getDadosDosInputs().get(6), getDadosDosInputs().get(7),
                            Estados.SP))); // Arrumar a recepção de estado;
        else if (tag.equals("Fornecedor"))
            loja.getEstoque().addFornecedor(new Fornecedor(getDadosDosInputs().get(0),
                    getDadosDosInputs().get(1), getDadosDosInputs().get(2),
                    new Endereco(Integer.parseInt(getDadosDosInputs().get(3)),
                            getDadosDosInputs().get(4), getDadosDosInputs().get(5),
                            getDadosDosInputs().get(6), getDadosDosInputs().get(7),
                            Estados.SP), getDadosDosInputs().get(9), getDadosDosInputs().get(10)));
        else if (tag.equals("Funcionario"))
            loja.addFuncionario(new Funcionario(getDadosDosInputs().get(0),
                    getDadosDosInputs().get(1), getDadosDosInputs().get(2),
                    new Endereco(Integer.parseInt(getDadosDosInputs().get(3)),
                            getDadosDosInputs().get(4), getDadosDosInputs().get(5),
                            getDadosDosInputs().get(6), getDadosDosInputs().get(7),
                            Estados.SP), Cargos.ASSISTENTE_DE_COZINHA,
                            Float.parseFloat(getDadosDosInputs().get(10))));
        // Arrumar coisas com Enum
    }

    public void addBotoes(){
        JPanel panelBotoes = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton cancelarBotao = new JButton("Cancelar");
        JButton registrarBotao = new JButton("Registrar");

        cancelarBotao.setFont(fontePequena);
        cancelarBotao.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(getPainelRegistro(), "Realmente deseja cancelar o registro?");
            if(resp == 0){
                limparInputs();
                JOptionPane.showMessageDialog(getPainelRegistro(), "O registro foi cancelado.");
            }
        });

        registrarBotao.addActionListener(e -> {
            correto = true;
            for (int i = 0; i < getLabelsDosInputs().size(); i++) {
                if (getLabelsDosInputs().get(i).equals("Telefone"))
                    validaCampo(i, Verifica::isTelefone);
                else if (getLabelsDosInputs().get(i).equals("Email"))
                    validaCampo(i, Verifica::isEmail);
                else if (getLabelsDosInputs().get(i).equals("CNPJ"))
                    validaCampo(i, Verifica::isCnpj);
                else if (getLabelsDosInputs().get(i).equals("Site"))
                    validaCampo(i, Verifica::isSite);
                else if (getLabelsDosInputs().get(i).equals("CEP"))
                    validaCampo(i, Verifica::isCep);
                else if (getLabelsDosInputs().get(i).equals("Número"))
                    validaCampo(i, Verifica::isNatural);
            }
            if (correto) {
                System.out.println(getDadosDosInputs());
                criaObjeto();
                limparInputs();
                JOptionPane.showMessageDialog(getPainelRegistro(), "O registro foi concluído.");
            }
        });
        registrarBotao.setFont(fontePequena);

        panelBotoes.add(cancelarBotao);
        panelBotoes.add(registrarBotao);
        getPainelRegistro().add(panelBotoes);
    }

    public void limparInputs() {
        for(JComponent comp : inputs){
            if(comp instanceof JTextField){
                ((JTextField) comp).setText("");
            }
            if(comp instanceof JComboBox<?>){
                ((JComboBox<?>) comp).setEnabled(true);
                ((JComboBox<?>) comp).setSelectedIndex(0);
            }
        }
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
            if(comp instanceof JMenu){
                //EU ESTOU DEFININDO O HIFEN AQUI
                //PEGA OS COMPONENTES DO MENU QUE TEM OS COMPLEMENTOS
                Component[] items = ((JMenu) comp).getMenuComponents();
                s = "";
                //PASSA POR TODOS OS ITEMS SELECIONADOS ADICINOANDO O TEXTO COM UM HIFEN
                for(Component c : items){
                    System.out.println("TESTANDO");
                    JMenuItem item = (JMenuItem)c;
                    if(item.isSelected()){
                        s += item.getText() + "-";
                    }
                }
                //ADICIONA A STRING FINAL NO ARRAY DE INPUTS
                dados.add(s);
            }
        }
        return dados;
    }
}
