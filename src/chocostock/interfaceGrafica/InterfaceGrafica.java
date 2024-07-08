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
            public void windowClosing(WindowEvent e) {Persistencia.salvarLoja(loja); System.exit(0);
            }
        });
        setMinimumSize(new Dimension(960, 540));
        setMaximumSize(new Dimension(1200, 800));
        setSize(1300, 800);
        setTitle("ChocoStock");
        setLayout(new BorderLayout());
        ImageIcon imgIcon = new ImageIcon("imagens/logoChocostock.png");
        Image img = imgIcon.getImage();
        setIconImage(img);

        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);
        add(painelPrincipal, BorderLayout.CENTER);

        painelPrincipal.add(new Inicio(loja), "Inicio");
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
        switch (chavePagina) {
            case "Inicio" -> painelPrincipal.add(new Inicio(loja), chavePagina);
            case "AdicionarPedido" -> painelPrincipal.add(NovaPagina.novoPedido(loja), chavePagina);
            case "AdicionarProduto" -> painelPrincipal.add(NovaPagina.novoProduto(loja), chavePagina);
            case "AdicionarCliente" -> painelPrincipal.add(NovaPagina.novoCliente(loja), chavePagina);
            case "AdicionarFornecedor" ->  painelPrincipal.add(NovaPagina.novoFornecedor(loja), "AdicionarFornecedor");
            case "AdicionarFuncionario" -> painelPrincipal.add(NovaPagina.novoFuncionario(loja), "AdicionarFuncionario");
            case "AdicionarIngrediente" -> painelPrincipal.add(NovaPagina.novoIngrediente(loja), chavePagina);
            case "AdicionarEmbalagem" -> painelPrincipal.add(NovaPagina.novaEmbalagem(loja), chavePagina);
            case "ListarCliente" -> painelPrincipal.add(new Listar<>("Cliente", loja.getClientes(),
                    new String[]{"id", "nome", "telefone", "email", "endereco", "pedidos"}), chavePagina);
            case "ListarFuncionario" -> painelPrincipal.add(new Listar<>("Funcionário", loja.getFuncionarios(),
                    new String[]{"id", "nome", "cargo", "telefone", "email", "endereco", "salario"}), chavePagina);
            case "ListarFornecedor" -> painelPrincipal.add(new Listar<>("Fornecedor", loja.getEstoque().getFornecedores(),
                    new String[]{"id", "nome", "cnpj", "telefone", "email", "endereco", "site"}), chavePagina);
            case "ListarPedidos" -> painelPrincipal.add(new Listar<>("Pedido", loja.getPedidos(),
                    new String[]{"id", "id_cliente", "produtos", "produtos_pendentes", "status", "data_entrega", "preco_total"}), chavePagina);
            case "StatusIngrediente" -> painelPrincipal.add(new Listar<>("Ingrediente", loja.getEstoque().getIngredientes(),
                    new String[]{"id", "nome", "quantidade", "unidade", "preco", "dataCompra", "validade", "fornecedor"}), chavePagina);
            case "StatusEmbalagem" -> painelPrincipal.add(new Listar<>("Embalagem", loja.getEstoque().getEmbalagens(),
                    new String[]{"id", "nome", "quantidade", "tipo_embalagem", "quantidade_por_pacote", "preco_pacote"}), chavePagina);
            case "Status CaixasProduto" -> painelPrincipal.add(new Listar<>("Caixa", loja.getEstoque().getCaixas(),
                    new String[]{"id", "nome", "quantidade", "validade", "peso", "embalagem", "id_pedido"}), chavePagina);
            case "Status ChocolatesProduto" -> painelPrincipal.add(new Listar<>("Chococlate", loja.getEstoque().getChocolates(),
                    new String[]{"id", "nome", "quantidade", "validade", "peso", "embalagem", "id_pedido"}), chavePagina);
        }
    }

    private void ajustarLarguraMenuItens() {
        JMenuBar menuBar = getJMenuBar();
        for (int i = 0; i < menuBar.getComponentCount(); i++) {
            menuBar.getComponent(i).setPreferredSize(new Dimension(this.getWidth() / menuBar.getComponentCount(), 30));
        }
        menuBar.revalidate();
        menuBar.repaint();
    }

    private JMenuBar criarMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        // Adicionar o botão "Início"
        JButton inicioButton = new JButton("Início");
        inicioButton.addActionListener(e -> alterarPagina("Inicio"));
        menuBar.add(inicioButton);
        menuBar.add(criarMenu("Menu Pedidos", new String[]{"Adicionar Pedido", "Listar Pedidos"}));
        menuBar.add(criarMenuComSubMenus("Menu Estoque",
                new String[]{"Produto", "Ingrediente", "Embalagem"},
                new String[][]{{"Adicionar", "Status Chocolates", "Status Caixas"}, {"Adicionar", "Status"}, {"Adicionar", "Status"}}));
        menuBar.add(criarMenuComSubMenus("Menu Colaboradores",
                new String[]{"Cliente", "Fornecedor", "Funcionario"},
                new String[][]{{"Adicionar", "Listar"}, {"Adicionar", "Listar"}, {"Adicionar", "Listar"}}));
        return menuBar;
    }

    private JMenu criarMenu(String menuName, String[] itemNames) {
        JMenu menu = new JMenu(menuName);
        for (String itemName : itemNames) {
            JMenuItem menuItem = new JMenuItem(itemName);
            menu.add(menuItem);
            menuItem.addActionListener(e -> alterarPagina(itemName.replaceAll("\\s+", "")));
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
