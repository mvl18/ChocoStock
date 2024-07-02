package chocostock.interfaceGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class InterfaceGrafica extends JFrame {
    private JPanel painelPrincipal;
    private CardLayout cardLayout;

    public InterfaceGrafica() {
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
        // painelPrincipal.add(new Listar(), "Listar");

        setVisible(true);
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
                if ("Cliente".equals(itemName)) {
                    menuItem.addActionListener(e -> cardLayout.show(painelPrincipal, "RegistrarCliente"));
                }
            }
            menu.add(subMenu);
        }
        return menu;
    }

    public static void main(String[] args) {
        new InterfaceGrafica();
    }
}