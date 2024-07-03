package chocostock.interfaceGrafica;

import chocostock.enums.TiposIngredientes;
import chocostock.loja.Loja;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class InterfaceGrafica extends JFrame {
    private Loja loja;
    private JPanel painelPrincipal;
    private CardLayout cardLayout;

    public InterfaceGrafica(Loja loja) {
        this.loja = loja;
        setJMenuBar(criarMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setTitle("ChocoStock");
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);
        add(painelPrincipal, BorderLayout.CENTER);

        adicionarPaginas();
        // painelPrincipal.add(new Listar(), "Listar");
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ajustarLarguraMenuItens();
            }
        });
        setVisible(true);
    }

    public void alterarPagina(String chavePagina){
        this.cardLayout.show(painelPrincipal, chavePagina);
    }


    private void ajustarLarguraMenuItens() {
        JMenuBar menuBar = getJMenuBar();
        for (int i = 0; i < menuBar.getComponentCount(); i++)
            menuBar.getComponent(i).setPreferredSize(new Dimension(this.getWidth()/menuBar.getComponentCount(), 30));
        menuBar.revalidate();
        menuBar.repaint();
    }

    public void adicionarPaginas(){
        painelPrincipal.add(new Inicio(loja), "Inicio");
        painelPrincipal.add(new Listar(loja), "ListarCliente");
        //Novo Cliente
        String[] atributosCliente = new String[]{"Nome", "Telefone", "Email", "Endereço"};
        FormularioDeCadastro fNovoCliente = new FormularioDeCadastro("Novo Cliente");
        for(String atributo : atributosCliente){
            fNovoCliente.addInputComponent(new JTextField(), atributo);
        }
        fNovoCliente.addBotoes();
        fNovoCliente.atualizarLayout();
        painelPrincipal.add(fNovoCliente, "RegistrarCliente");

        //Novo Fornecedor
        String[] atributosFornecedor = new String[]{"Nome", "Telefone", "Email", "Endereço", "CNPJ", "Site"};
        FormularioDeCadastro fNovoFornecedor = new FormularioDeCadastro("Novo Fornecedor");
        for(String atributo : atributosFornecedor){
            fNovoFornecedor.addInputComponent(new JTextField(), atributo);
        }
        fNovoFornecedor.addBotoes();
        fNovoFornecedor.atualizarLayout();
        painelPrincipal.add(fNovoFornecedor, "RegistrarFornecedor");

        //Novo Funcionário
        String[] atributosFuncionario = {"Nome", "Telefone", "Email", "Endereço", "Cargo", "Salario"};
        String[] cargos = {"Cargo1", "Cargo2", "Cargo3"};
        FormularioDeCadastro fNovoFuncionario = new FormularioDeCadastro("Novo Funcionário");

        for(String atributo : atributosFuncionario){
            if(atributo.equals("Cargo")){
                fNovoFuncionario.addInputComponent(new JComboBox<String>(cargos), atributo);
            }
            else{
                fNovoFuncionario.addInputComponent(new JTextField(), atributo);
            }
        }
        fNovoFuncionario.addBotoes();
        fNovoFuncionario.atualizarLayout();
        painelPrincipal.add(fNovoFuncionario, "RegistrarFuncionario");
        System.out.println(Arrays.toString(TiposIngredientes.values()));
    }

    private JMenuBar criarMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(criarMenu("Menu Pedidos", new String[]{"Novo Pedido", "Listar Pedidos", "Atualizar Pedido"}));
        menuBar.add(criarMenuComSubMenus("Menu Estoque",
                new String[]{"Adicionar", "Status"},
                new String[][]{{"Produto", "Ingrediente", "Embalagem"}, {"Produto", "Ingrediente", "Embalagem"}}));
        menuBar.add(criarMenuComSubMenus("Menu Colaboradores",
                new String[]{"Adicionar", "Listar"},
                new String[][]{{"Cliente", "Fornecedor", "Funcionario"}, {"Cliente", "Fornecedor", "Funcionario"}}));
        return menuBar;
    }

    private JMenu criarMenu(String menuName, String[] itemNames) {
        JMenu menu = new JMenu(menuName);
        for (String itemName : itemNames) {
            JMenuItem menuItem = new JMenuItem(itemName);
            menu.add(menuItem);
        }
        return menu;
    }

    private JMenu criarMenuComSubMenus(String menuName, String[] subMenuNames, String[][] itemNames) {
        JMenu menu = new JMenu(menuName);
        for (int i = 0; i < subMenuNames.length; i++) {
            JMenu subMenu = new JMenu(subMenuNames[i]);
            for (String itemName : itemNames[i]) {
                JMenuItem menuItem = new JMenuItem(itemName);
                subMenu.add(menuItem);
                if("Adicionar".equals(subMenuNames[i])){
                    if ("Cliente".equals(itemName)) {
                        menuItem.addActionListener(e -> alterarPagina("RegistrarCliente"));
                    }
                    if ("Fornecedor".equals(itemName)) {
                        menuItem.addActionListener(e -> alterarPagina("RegistrarFornecedor"));
                    }
                    if ("Funcionario".equals(itemName)) {
                        menuItem.addActionListener(e -> alterarPagina("RegistrarFuncionario"));
                    }
                    if ("Ingrediente".equals(itemName)) {
                        menuItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                painelPrincipal.add(new PaginaNovoIngrediente(loja), "RegistrarIngrediente");
                                alterarPagina("RegistrarIngrediente");
                            }
                        });
                    }
                    if ("Embalagem".equals(itemName)) {
                        menuItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                painelPrincipal.add(new PaginaNovaEmbalagem(loja), "RegistrarEmbalagem");
                                alterarPagina("RegistrarEmbalagem");
                            }
                        });
                    }
                }

            }
            menu.add(subMenu);
        }
        return menu;
    }
}
