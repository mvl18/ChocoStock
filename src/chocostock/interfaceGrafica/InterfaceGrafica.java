package chocostock.interfaceGrafica;

import chocostock.bancodeDados.Persistencia;
import chocostock.enums.Cargos;
import chocostock.enums.Estados;
import chocostock.loja.Loja;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfaceGrafica extends JFrame {
    private Loja loja;
    private JPanel painelPrincipal;
    private CardLayout cardLayout;

    public InterfaceGrafica(Loja loja) {
        this.loja = loja;
        setJMenuBar(criarMenuBar());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Adiciona um WindowListener para capturar o evento de fechamento
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {Persistencia.salvarLoja(loja); System.exit(0);
            }
        });

        setSize(600, 600);
        setTitle("ChocoStock");
        setLayout(new BorderLayout());
        ImageIcon imgIcon = new ImageIcon("imagens/logoChocostock.png");
        Image img = imgIcon.getImage();
        setIconImage(img);

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

        // ############# LISTAR ##############
        else if("ListarCliente".equals(chavePagina)){
            painelPrincipal.add(new Listar<>(loja, "Cliente", loja.getClientes(),
                    new String[]{"id", "nome", "telefone", "email", "endereco", "pedidos"},
                    new int[]{50, 100, 200, 150, 100, 100}), chavePagina);
        }
        else if("ListarFuncionario".equals(chavePagina)){
            painelPrincipal.add(new Listar<>(loja, "Funcionário", loja.getFuncionarios(),
                    new String[]{"id", "nome", "cargo", "telefone", "email", "endereco", "salario"},
                    new int[]{50, 100, 200, 150, 100, 100, 100}), chavePagina);
        }
        else if("ListarFornecedor".equals(chavePagina)) {
            painelPrincipal.add(new Listar<>(loja, "Fornecedor", loja.getEstoque().getFornecedores(),
                    new String[]{"id", "nome", "cnpj", "telefone", "email", "endereco", "site"},
                    new int[]{50, 120, 105, 150, 100, 100, 100}), chavePagina);
        }
        else if("Listar Pedidos".equals(chavePagina)){
            painelPrincipal.add(new Listar<>(loja, "Pedido", loja.getPedidos(),
                    new String[]{"id", "id_cliente", "produtos", "produtos_pendentes", "status", "data_entrega", "preco_total"},
                    new int[]{50, 100, 200, 150, 100, 100, 100}), chavePagina);
        }

        // ############# STATUS ##############
//        else if("StatusIngrediente".equals(chavePagina)){
//            painelPrincipal.add(new Listar<>(loja, loja.getEstoque().getIngredientes()), chavePagina);
//        }
        else if("StatusEmbalagem".equals(chavePagina)) {
            painelPrincipal.add(new Listar<>(loja, "Embalagem", loja.getEstoque().getEmbalagens(),
                    new String[]{"id", "nome", "quantidade", "tipo_embalagem", "quantidade_por_pacote", "preco_pacote"},
                    new int[]{50, 100, 200, 150, 100, 100}), chavePagina);
        }
        else if("StatusProduto".equals(chavePagina)) {
            painelPrincipal.add(new Listar<>(loja, "Produto", loja.getEstoque().getProdutos(),
                    new String[]{"id", "nome", "quantidade", "validade", "peso", "embalagem", "id_pedido"},
                    new int[]{50, 100, 200, 150, 100, 100, 100}), chavePagina);
        }

        // ############# ADICIIONAR ##############
        else if("AdicionarIngrediente".equals(chavePagina)){
            painelPrincipal.add(new PaginaNovoIngrediente(loja), chavePagina);
        }
        else if("AdicionarEmbalagem".equals(chavePagina)){
            painelPrincipal.add(new PaginaNovaEmbalagem(loja), chavePagina);
            }
//        else if("AdicionarProduto".equals(chavePagina)){
//            painelPrincipal.add(new PaginaNovoProduto(loja), chavePagina);
//        }
    }

    private void ajustarLarguraMenuItens() {
        JMenuBar menuBar = getJMenuBar();
        for (int i = 0; i < menuBar.getComponentCount(); i++) {
            menuBar.getComponent(i).setPreferredSize(new Dimension(this.getWidth() / menuBar.getComponentCount(), 30));
        }
        menuBar.revalidate();
        menuBar.repaint();
    }

    public void adicionarPaginas(){
        painelPrincipal.add(new Inicio(loja), "Inicio");
        // painelPrincipal.add(new ListarCliente(loja), "ListarCliente");
        painelPrincipal.add(new Listar<>(loja, "Cliente", loja.getClientes(),
                new String[]{"id", "nome", "telefone", "email", "endereco", "pedidos"},
                new int[]{50, 100, 200, 150, 100, 100}), "ListarCliente");

        //painelPrincipal.add(new Listar<>(loja, loja.getPedidos()), "Listar Pedidos");

        painelPrincipal.add(new Listar<>(loja, "Funcionário", loja.getFuncionarios(),
                new String[]{"id", "nome", "cargo", "telefone", "email", "endereco", "salario"},
                new int[]{50, 100, 200, 150, 100, 100, 100}), "ListarFuncionario");
        painelPrincipal.add(new Listar<>(loja, "Fornecedor", loja.getEstoque().getFornecedores(),
                new String[]{"id", "nome", "cnpj", "telefone", "email", "endereco", "site"},
                new int[]{50, 120, 105, 150, 100, 100, 100}), "ListarFornecedor");
        painelPrincipal.add(new Listar<>(loja, "Embalagem", loja.getEstoque().getEmbalagens(),
                new String[]{"id", "nome", "quantidade", "tipo_embalagem", "quantidade_por_pacote", "preco_pacote"},
                new int[]{50, 100, 200, 150, 100, 100}), "StatusEmbalagem");
        painelPrincipal.add(new Listar<>(loja, "Produto", loja.getEstoque().getProdutos(),
                new String[]{"id", "nome", "quantidade", "validade", "peso", "embalagem", "id_pedido"},
                new int[]{50, 100, 200, 150, 100, 100, 100}), "StatusProduto");
        painelPrincipal.add(new Listar<>(loja, "Pedido", loja.getPedidos(),
                new String[]{"id", "id_cliente", "produtos", "produtos_pendentes", "status", "data_entrega", "preco_total"},
                new int[]{50, 100, 200, 150, 100, 100, 100}), "Listar Pedidos");
        // painelPrincipal.add(new Listar<>(loja, loja.getEstoque().getIngredientes()), "StatusIngrediente"); // DA ERRO PQ N TEM INGREDIENTES




        painelPrincipal.add(new PaginaNovoIngrediente(loja), "AdicionarIngrediente");
        painelPrincipal.add(new PaginaNovaEmbalagem(loja), "AdicionarEmbalagem");
//        painelPrincipal.add(new PaginaNovoCliente(loja), "AdicionarCliente");
        painelPrincipal.add(new PaginaNovoProduto(loja), "AdicionarProduto");
//        painelPrincipal.add(new PaginaNovoFuncionario(loja), "AdicionarFuncionario");
        painelPrincipal.add(new PaginaNovoFornecedor(loja), "AdicionarFornecedor");



        //Novo Cliente
        String[] atributosCliente = new String[]{"Nome", "Telefone", "Email",
                "CEP", "Estado", "Cidade", "Bairro", "Rua", "Número"};
        FormularioDeCadastro fNovoCliente = new FormularioDeCadastro("Cliente", loja);
        fNovoCliente.addTitulo("Novo Cliente");
        for(String atributo : atributosCliente){
            if (atributo.equals("Estado"))
                fNovoCliente.addInputComponent(new JComboBox<>(Estados.getTipos()), atributo);
            else
                fNovoCliente.addInputComponent(new JTextField(), atributo);
        }
        fNovoCliente.addBotoes();
        fNovoCliente.atualizarLayout();
        painelPrincipal.add(fNovoCliente, "AdicionarCliente");
        //Novo Fornecedor
        String[] atributosFornecedor = new String[]{"Nome", "Telefone", "Email",
                "CEP", "Estado", "Cidade", "Bairro", "Rua", "Número", "CNPJ", "Site"};
        FormularioDeCadastro fNovoFornecedor = new FormularioDeCadastro("Fornecedor", loja);
        fNovoFornecedor.addTitulo("Novo Fornecedor");
        for(String atributo : atributosFornecedor){
            if (atributo.equals("Estado"))
                fNovoFornecedor.addInputComponent(new JComboBox<>(Estados.getTipos()), atributo);
            else
                fNovoFornecedor.addInputComponent(new JTextField(), atributo);
        }
        fNovoFornecedor.addBotoes();
        fNovoFornecedor.atualizarLayout();
        painelPrincipal.add(fNovoFornecedor, "AdicionarFornecedor");
        //Novo Funcionário
        String[] atributosFuncionario = {"Nome", "Telefone", "Email",
                "CEP", "Estado", "Cidade", "Bairro", "Rua", "Número", "Cargo", "Salario"};
        FormularioDeCadastro fNovoFuncionario = new FormularioDeCadastro("Funcionario", loja);
        fNovoFuncionario.addTitulo("Novo Funcionário");
        for(String atributo : atributosFuncionario){
            if(atributo.equals("Cargo")){
                fNovoFuncionario.addInputComponent(new JComboBox<>(Cargos.getTipos()), atributo);
            }
            else if (atributo.equals("Estado"))
                fNovoFuncionario.addInputComponent(new JComboBox<>(Estados.getTipos()), atributo);
            else
                fNovoFuncionario.addInputComponent(new JTextField(), atributo);
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
            menuItem.addActionListener(e -> alterarPagina(itemName));
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
