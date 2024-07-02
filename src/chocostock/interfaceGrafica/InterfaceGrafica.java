package chocostock.interfaceGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceGrafica extends JFrame {
    private JPanel painelPrincipal;
    private CardLayout cardLayout;

    public InterfaceGrafica(){
        JMenuBar menuBar = gerarMenu();

        setJMenuBar(menuBar);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setTitle("ChocoStock");
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);
        add(painelPrincipal, BorderLayout.CENTER);

        painelPrincipal.add(new JPanel(), "Inicio");
        painelPrincipal.add(new Registrar(), "RegistrarCliente");

        setVisible(true);
    }

    public JMenuBar gerarMenu() {
        JMenuBar m = new JMenuBar();

        //menuPedidos
        JMenu menuPedidos = new JMenu("Menu Pedidos");
        JMenuItem itemNovoPedido = new JMenuItem("Novo Pedido");
        JMenuItem itemListarPedidos = new JMenuItem("Listar Pedidos");
        JMenuItem itemAtualizarPedidos = new JMenuItem("Atualizar Pedido");
        menuPedidos.add(itemNovoPedido);
        menuPedidos.add(itemListarPedidos);
        menuPedidos.add(itemAtualizarPedidos);
        m.add(menuPedidos);

        //menuEstoque
        JMenu menuEstoque = new JMenu("Menu Estoque");

        JMenu subMenuAdicionarEstoque = new JMenu("Adicionar");
        JMenuItem itemAddProduto = new JMenuItem("Produto");
        JMenuItem itemAddIngrediente = new JMenuItem("Ingrediente");
        JMenuItem itemAddEmbalagem = new JMenuItem("Embalagem");
        subMenuAdicionarEstoque.add(itemAddProduto);
        subMenuAdicionarEstoque.add(itemAddIngrediente);
        subMenuAdicionarEstoque.add(itemAddEmbalagem);
        menuEstoque.add(subMenuAdicionarEstoque);

        JMenu subMenuStatusEstoque = new JMenu("Status");
        JMenuItem itemStatusProduto = new JMenuItem("Produto");
        JMenuItem itemStatusIngrediente = new JMenuItem("Ingrediente");
        JMenuItem itemStatusEmbalagem = new JMenuItem("Embalagem");
        subMenuStatusEstoque.add(itemStatusProduto);
        subMenuStatusEstoque.add(itemStatusIngrediente);
        subMenuStatusEstoque.add(itemStatusEmbalagem);
        menuEstoque.add(subMenuStatusEstoque);

        m.add(menuEstoque);

        JMenu menuColaboradores = new JMenu("Menu Colaboradores");

        JMenu subMenuAdicionarColaborador = new JMenu("Adicionar");
        JMenuItem itemAddCliente = new JMenuItem("Cliente");
        JMenuItem itemAddFornecedor = new JMenuItem("Fornecedor");
        JMenuItem itemAddFuncionario = new JMenuItem("Funcionario");
        subMenuAdicionarColaborador.add(itemAddCliente);
        subMenuAdicionarColaborador.add(itemAddFornecedor);
        subMenuAdicionarColaborador.add(itemAddFuncionario);
        menuColaboradores.add(subMenuAdicionarColaborador);

        JMenu subMenuListarColaborador = new JMenu("Listar");
        JMenuItem itemListarCliente = new JMenuItem("Cliente");
        JMenuItem itemListarFornecedor = new JMenuItem("Fornecedor");
        JMenuItem itemListarFuncionario = new JMenuItem("Funcionario");
        subMenuListarColaborador.add(itemListarCliente);
        subMenuListarColaborador.add(itemListarFornecedor);
        subMenuListarColaborador.add(itemListarFuncionario);
        menuColaboradores.add(subMenuListarColaborador);

        m.add(menuColaboradores);

        itemAddCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(painelPrincipal, "RegistrarCliente");
            }
        });

        return m;
    }
}
