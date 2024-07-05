package chocostock.interfaceGrafica;

import chocostock.bancodeDados.Persistencia;
import chocostock.loja.Loja;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfaceGrafica extends JFrame {
    private final Loja loja;
    private final JPanel painelPrincipal;
    private final CardLayout cardLayout;

    public InterfaceGrafica(Loja loja) {
        this.loja = loja;
        setJMenuBar(criarMenuBar());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Adiciona um WindowListener para capturar o evento de fechamento
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Executa a função personalizada
                Persistencia.salvarLoja(loja);

                // Fecha a aplicação
                System.exit(0);
            }
        });
        setSize(600, 600);
        setTitle("ChocoStock");
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);
        add(painelPrincipal, BorderLayout.CENTER);

        adicionarPaginas();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ajustarLarguraMenuItens();
            }
        });
        setVisible(true);
    }

    public void alterarPagina(String chavePagina){
        atualizarPagina(chavePagina);
        this.cardLayout.show(painelPrincipal, chavePagina);
    }
    public void atualizarPagina(String chavePagina){
        if("Inicio".equals(chavePagina)){
            painelPrincipal.add(new Inicio(loja), chavePagina);
        }
        else if("ListarCliente".equals(chavePagina)){
            painelPrincipal.add(new Listar<>(loja, "Cliente", loja.getClientes(), new String[]{"id", "nome", "telefone", "email", "pedidos"}), chavePagina);
        }
        else if("ListarFuncionario".equals(chavePagina)){
            //painelPrincipal.add(new Listar<>(loja, "Funcionário", loja.getFuncionarios()), chavePagina);
        }
        else if("ListarFornecedor".equals(chavePagina)){
            //painelPrincipal.add(new Listar<>(loja, "Fornecedor", loja.getEstoque().getFornecedores()), chavePagina);
        }
//        else if("Listar Pedidos".equals(chavePagina)){
//            painelPrincipal.add(new Listar<>(loja, loja.getPedidos()), chavePagina);
//        }
        else if("AdicionarIngrediente".equals(chavePagina)){
            painelPrincipal.add(new PaginaNovoIngrediente(loja), chavePagina);
        }
//        else if("StatusIngrediente".equals(chavePagina)){
//            painelPrincipal.add(new Listar<>(loja, loja.getEstoque().getIngredientes()), chavePagina);
//        }
        else if("AdicionarEmbalagem".equals(chavePagina)){
            painelPrincipal.add(new PaginaNovaEmbalagem(loja), chavePagina);
        }
        else if("StatusEmbalagem".equals(chavePagina)){
            //painelPrincipal.add(new Listar<>(loja, "Embalagem", loja.getEstoque().getEmbalagens()), chavePagina);
        }
//        else if("AdicionarProduto".equals(chavePagina)){
//            painelPrincipal.add(new PaginaNovoProduto(loja), chavePagina);
//        }
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
        // painelPrincipal.add(new ListarCliente(loja), "ListarCliente");
        painelPrincipal.add(new Listar<>(loja, "Cliente", loja.getClientes(), new String[]{"id", "nome", "telefone", "email", "pedidos"}), "ListarCliente");
        // painelPrincipal.add(new Listar<>(loja, loja.getPedidos()), "Listar Pedidos");
//        painelPrincipal.add(new Listar<>(loja, "Funcionário", loja.getFuncionarios()), "ListarFuncionario");
//        painelPrincipal.add(new Listar<>(loja, "Fornecedor", loja.getEstoque().getFornecedores()), "ListarFornecedor");
        painelPrincipal.add(new PaginaNovoIngrediente(loja), "AdicionarIngrediente");
        // painelPrincipal.add(new Listar<>(loja, loja.getEstoque().getIngredientes()), "StatusIngrediente"); // DA ERRO PQ N TEM INGREDIENTES
        painelPrincipal.add(new PaginaNovaEmbalagem(loja), "AdicionarEmbalagem");
//        painelPrincipal.add(new Listar<>(loja, "Embalagem", loja.getEstoque().getEmbalagens()), "StatusEmbalagem");
        //painelPrincipal.add(new PaginaNovoProduto(loja), "AdicionarProduto");

        //Novo Cliente
        String[] atributosCliente = new String[]{"Nome", "Telefone", "Email", "Endereço"};
        FormularioDeCadastro fNovoCliente = new FormularioDeCadastro();
        fNovoCliente.addTitulo("Novo Cliente");
        for(String atributo : atributosCliente){
            fNovoCliente.addInputComponent(new JTextField(), atributo);
        }
        fNovoCliente.addBotoes();
        fNovoCliente.atualizarLayout();
        painelPrincipal.add(fNovoCliente, "AdicionarCliente");

        //Novo Fornecedor
        String[] atributosFornecedor = new String[]{"Nome", "Telefone", "Email", "Endereço", "CNPJ", "Site"};
        FormularioDeCadastro fNovoFornecedor = new FormularioDeCadastro();
        fNovoFornecedor.addTitulo("Novo Fornecedor");
        for(String atributo : atributosFornecedor){
            fNovoFornecedor.addInputComponent(new JTextField(), atributo);
        }
        fNovoFornecedor.addBotoes();
        fNovoFornecedor.atualizarLayout();
        painelPrincipal.add(fNovoFornecedor, "AdicionarFornecedor");

        //Novo Funcionário
        String[] atributosFuncionario = {"Nome", "Telefone", "Email", "Endereço", "Cargo", "Salario"};
        String[] cargos = {"Cargo1", "Cargo2", "Cargo3"};
        FormularioDeCadastro fNovoFuncionario = new FormularioDeCadastro();
        fNovoFuncionario.addTitulo("Novo Funcionário");
        for(String atributo : atributosFuncionario){
            if(atributo.equals("Cargo")){
                fNovoFuncionario.addInputComponent(new JComboBox<>(cargos), atributo);
            }
            else{
                fNovoFuncionario.addInputComponent(new JTextField(), atributo);
            }
        }
        fNovoFuncionario.addBotoes();
        fNovoFuncionario.atualizarLayout();
        painelPrincipal.add(fNovoFuncionario, "AdicionarFuncionario");
    }


    private JMenuBar criarMenuBar() {
        JMenuBar menuBar = new JMenuBar();
//        menuBar.add(criarMenuComAcao("Início", "Inicio"));

        // Adicionar o botão "Início"
        JButton inicioButton = new JButton("Início");
        inicioButton.addActionListener(e -> alterarPagina("Inicio"));
        menuBar.add(inicioButton);

        menuBar.add(criarMenu("Menu Pedidos", new String[]{"Novo Pedido", "Listar Pedidos", "Atualizar Pedido"}));
        menuBar.add(criarMenuComSubMenus("Menu Estoque",
                new String[]{"Produto", "Ingrediente", "Embalagem"},
                new String[][]{{"Adicionar", "Status"}, {"Adicionar", "Status"}, {"Adicionar", "Status"}}));
        menuBar.add(criarMenuComSubMenus("Menu Colaboradores",
                new String[]{"Cliente", "Fornecedor", "Funcionario"},
                new String[][]{{"Adicionar", "Listar"}, {"Adicionar", "Listar"}, {"Adicionar", "Listar"}}));
        return menuBar;
    }

    private JMenu criarMenuComAcao(String nome, String acao) {
        JMenu menu = new JMenu(nome);
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                alterarPagina(acao);
            }
        });
        return menu;
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
                int finalI = i;
                menuItem.addActionListener(e -> alterarPagina(itemName + subMenuNames[finalI]));
            }
            menu.add(subMenu);
        }
        return menu;
    }
}
