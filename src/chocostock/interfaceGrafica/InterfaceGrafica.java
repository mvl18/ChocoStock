package chocostock.interfaceGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import chocostock.loja.Loja;

public class InterfaceGrafica extends JFrame {
    private JPanel painelPrincipal;
    private CardLayout cardLayout;

    public InterfaceGrafica(Loja loja) {
        setJMenuBar(criarMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setTitle("ChocoStock");
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);
        add(painelPrincipal, BorderLayout.CENTER);

        painelPrincipal.add(new JPanel(), "Inicio");
        painelPrincipal.add(new Registrar(), "RegistrarCliente");
        painelPrincipal.add(new Listar(loja), "ListarCliente");

        setVisible(true);
    }

    private JMenuBar criarMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(criarMenu("Menu Pedidos", new String[]{"Novo Pedido", "Listar Pedidos", "Atualizar Pedido"}));
        menuBar.add(criarMenuComSubMenus("Menu Estoque",
                new String[]{"Produto", "Ingrediente", "Embalagem"},
                new String[][]{{"Adicionar", "Status"}, {"Adicionar", "Status"}, {"Adicionar", "Status"}}));
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
                //
                if ("Cliente".equals(subMenuNames[i])) {
                    if ("Adicionar".equals(itemName)) {
                        menuItem.addActionListener(e -> cardLayout.show(painelPrincipal, "RegistrarCliente"));
                    }
                }
                if ("Cliente".equals(subMenuNames[i])) {
                    if ("Listar".equals(itemName)) {
                        menuItem.addActionListener(e -> cardLayout.show(painelPrincipal, "ListarCliente"));
                    }
                }
            }
            menu.add(subMenu);
        }
        return menu;
    }
}
