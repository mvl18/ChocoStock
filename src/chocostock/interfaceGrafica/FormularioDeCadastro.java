package chocostock.interfaceGrafica;

import chocostock.auxiliar.Endereco;
import chocostock.auxiliar.Processa;
import chocostock.auxiliar.Verifica;
import chocostock.colaboradores.Cliente;
import chocostock.colaboradores.Fornecedor;
import chocostock.colaboradores.Funcionario;
import chocostock.enums.*;
import chocostock.interfaces.ValidadorInput;
import chocostock.itens.produtos.Pendente;
import chocostock.itens.produtos.Produto;
import chocostock.itens.suprimentos.Embalagem;
import chocostock.itens.suprimentos.Ingrediente;
import chocostock.loja.Loja;
import chocostock.loja.Pedido;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

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
        switch (tag) {
            case "Pedido" -> {
                // Cria uma lista dos produtos adicionados no pedido
                ArrayList<Pendente> pendenteList = new ArrayList<>();
                for (Component component : this.getInputs()) {
                    if (component instanceof JScrollPane pane) {
                        pendenteList = PainelProduto.getPainelProdutos(pane);
                        break;
                    }
                }
                Pedido pedido = new Pedido(loja.getCliente(getDadosDosInputs().get(0)).getId(),
                        LocalDate.parse(getDadosDosInputs().get(1), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        LocalDate.parse(getDadosDosInputs().get(2), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        Processa.parseBool(getDadosDosInputs().get(3)), Status.parseStatus(getDadosDosInputs().get(4)),
                        pendenteList, Float.parseFloat(getDadosDosInputs().get(6)));
                pedido = loja.getEstoque().retiraProdutosEstoque(pedido);
                loja.addPedido(pedido);
            }
            case "Produto" -> {
                loja.getEstoque().addProduto(new Produto(getDadosDosInputs().get(0),
                        Integer.parseInt(getDadosDosInputs().get(1)), Float.parseFloat(getDadosDosInputs().get(2)),
                        LocalDate.parse(getDadosDosInputs().get(3), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        Integer.parseInt(getDadosDosInputs().get(4)),
                        TiposEmbalagens.parseTipoEmbalagem(getDadosDosInputs().get(5))));
                // Assim que um novo produto é adicionado, o sistema verifica se pode completar algum pedido
                for (Pedido pedido : loja.getPedidos()) {
                    pedido = loja.getEstoque().retiraProdutosEstoque(pedido);
                    loja.atualizarPedido(pedido);
                }
            }
            case "Ingrediente" -> loja.getEstoque().addIngrediente(new Ingrediente(getDadosDosInputs().get(0),
                    Integer.parseInt(getDadosDosInputs().get(1)), Integer.parseInt(getDadosDosInputs().get(2)),
                    Float.parseFloat(getDadosDosInputs().get(3)),
                    LocalDate.parse(getDadosDosInputs().get(4), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    LocalDate.parse(getDadosDosInputs().get(5), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    Fornecedor.parseFornecedor(loja.getEstoque().getFornecedores(), getDadosDosInputs().get(6))));
            case "Embalagem" -> loja.getEstoque().addEmbalagem(new Embalagem(getDadosDosInputs().get(0),
                    TiposEmbalagens.parseTipoEmbalagem(getDadosDosInputs().get(0)), Integer.parseInt(getDadosDosInputs().get(1)),
                    Float.parseFloat(getDadosDosInputs().get(2)), Integer.parseInt(getDadosDosInputs().get(3)),
                    Fornecedor.parseFornecedor(loja.getEstoque().getFornecedores(), getDadosDosInputs().get(4))));
            case "Cliente" -> loja.addCliente(new Cliente(getDadosDosInputs().get(0),
                    getDadosDosInputs().get(1), getDadosDosInputs().get(2),
                    new Endereco(getDadosDosInputs().get(3),
                            Estados.parseEstado(getDadosDosInputs().get(4)),
                            getDadosDosInputs().get(5), getDadosDosInputs().get(6),
                            getDadosDosInputs().get(7),
                            Integer.parseInt(getDadosDosInputs().get(8)))));
            case "Fornecedor" -> loja.getEstoque().addFornecedor(new Fornecedor(getDadosDosInputs().get(0),
                    getDadosDosInputs().get(1), getDadosDosInputs().get(2),
                    new Endereco(getDadosDosInputs().get(3),
                            Estados.parseEstado(getDadosDosInputs().get(4)),
                            getDadosDosInputs().get(5), getDadosDosInputs().get(6),
                            getDadosDosInputs().get(7),
                            Integer.parseInt(getDadosDosInputs().get(8))),
                    getDadosDosInputs().get(9), getDadosDosInputs().get(10)));
            case "Funcionario" -> loja.addFuncionario(new Funcionario(getDadosDosInputs().get(0),
                    getDadosDosInputs().get(1), getDadosDosInputs().get(2),
                    new Endereco(getDadosDosInputs().get(3),
                            Estados.parseEstado(getDadosDosInputs().get(4)),
                            getDadosDosInputs().get(5), getDadosDosInputs().get(6),
                            getDadosDosInputs().get(7),
                            Integer.parseInt(getDadosDosInputs().get(8))),
                    Cargos.parseCargo(getDadosDosInputs().get(9)),
                    Float.parseFloat(getDadosDosInputs().get(10))));
        }
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
            for (int i = 0; i < getLabelsDosInputs().size(); i++) {
                switch (getLabelsDosInputs().get(i)) {
                    case "Telefone" -> validaCampo(i, Verifica::isTelefone);
                    case "Email" -> validaCampo(i, Verifica::isEmail);
                    case "CNPJ" -> validaCampo(i, Verifica::isCnpj);
                    case "Site" -> validaCampo(i, Verifica::isSite);
                    case "CEP" -> validaCampo(i, Verifica::isCep);
                    case "Número", "Peso", "Quantidade",
                            "Quantidade por pacote", "ID cliente" -> validaCampo(i, Verifica::isNatural);
                    case "Quantos kg por Unidade", "Preço",
                            "Preço por pacote", "Valor total" -> validaCampo(i, Verifica::isFloat);
                    case "Data de Compra", "Data de fabricação",
                            "Data de entrega", "Data do pedido" -> validaCampo(i, Verifica::isData);
                    case "Data de validade" -> validaCampo(i, Verifica::isDataFutura);
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
            if(comp instanceof JScrollPane){
                dados.add("");
            }
        }
        return dados;
    }
}