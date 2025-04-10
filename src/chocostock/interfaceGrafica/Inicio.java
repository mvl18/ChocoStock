package chocostock.interfaceGrafica;

import chocostock.itens.produtos.Produto;
import chocostock.itens.suprimentos.Ingrediente;
import chocostock.loja.Loja;
import chocostock.loja.Pedido;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Inicio extends JPanel {
    public Inicio(Loja loja) {
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());

        // Título e subtítulo
        JPanel painelTitulo = new JPanel();
        painelTitulo.setLayout(new BoxLayout(painelTitulo, BoxLayout.Y_AXIS));
        ImageIcon logo = new ImageIcon("imagens/logoChocostock.png");
        logo.setImage(logo.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH)) ;
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(logoLabel, BorderLayout.WEST);

        JLabel titulo = new JLabel("Chocostock", SwingConstants.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 36));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelTitulo.add(titulo);
        painelTitulo.add(Box.createVerticalStrut(2));

        JLabel subtitulo = new JLabel("O doce controle de vendas! ", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Tahoma", Font.ITALIC, 28));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelTitulo.add(subtitulo);
        topPanel.add(painelTitulo, BorderLayout.CENTER);

        // Painel de avisos
        ArrayList<String> avisosIngredientes = new ArrayList<>();
        int valorMinimo = 2;
        for (Ingrediente ingrediente : loja.getEstoque().getIngredientes()) {
            if (ingrediente.getQuantidade() <= valorMinimo) {
                avisosIngredientes.add(ingrediente.getNome() + " tem somente " +
                        ingrediente.getQuantidade() + " unidade" +
                        (ingrediente.getQuantidade() == 1 ? "!" : "s!"));
            }
        }
        ArrayList<String> avisosProdutos = new ArrayList<>();
        valorMinimo = 4;
        for (Produto produto : loja.getEstoque().getProdutos()) {
            if (produto.getQuantidade() <= valorMinimo) {
                avisosProdutos.add(produto.getNome() + " tem somente " +
                        produto.getQuantidade() + " unidade" +
                        (produto.getQuantidade() == 1 ? "!" : "s!"));
            }
        }
        JPanel painelIngredientes = criarListaComTitulo("Ingredientes", avisosIngredientes, Color.RED);
        JPanel painelProdutos = criarListaComTitulo("Produtos", avisosProdutos, Color.RED);

        JPanel informacoes = criarListaComTitulo("Informações",
                new String[]{loja.getClientes().size() + " clientes atendidos",
                        loja.getPedidos().size() + " pedidos já realizados"}, Color.BLACK);

        JPanel esquerda = new JPanel();
        esquerda.setLayout(new BoxLayout(esquerda, BoxLayout.Y_AXIS));
        esquerda.add(painelIngredientes);
        esquerda.add(painelProdutos);
        esquerda.add(informacoes);

        ArrayList<String> pedidosPendentes = new ArrayList<>();
        for (Pedido pedido : loja.getPedidos()) {
            if (pedido.getStatus().getNome().equals("Pendente")) {
                pedidosPendentes.add(pedido.getId() + " - Data: " + pedido.getData_entrega());
            }
        }
        JPanel painelPedidos = criarListaComTitulo("Pedidos Pendentes", pedidosPendentes, Color.BLACK);

        JPanel direita = new JPanel();
        direita.setLayout(new BoxLayout(direita, BoxLayout.Y_AXIS));
        direita.add(painelPedidos);

        JPanel caixasInfo = new JPanel(new GridLayout());
        caixasInfo.add(esquerda);
        caixasInfo.add(direita);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(caixasInfo, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel criarListaComTitulo(String nomeLista, String[] itens, Color cor) {
        JPanel painel = new JPanel(new GridBagLayout());
//        painelMensagens.setLayout(new BoxLayout(painelMensagens, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(1),
                BorderFactory.createEmptyBorder(10, 10, 10 ,10)));
        JLabel titulo = new JLabel(nomeLista, SwingConstants.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(titulo, gbc);

        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        for (String item : itens) {
            JLabel aviso = new JLabel(item);
            aviso.setAlignmentX(Component.CENTER_ALIGNMENT);
            aviso.setFont(new Font("Tahoma", Font.PLAIN, 18));
            aviso.setForeground(cor);
            lista.add(aviso);
        }
        painel.add(lista);

        return painel;
    }

    private JPanel criarListaComTitulo(String nomeLista, ArrayList<String> itens, Color cor) {
        String[] array = new String[itens.size()];
        array = itens.toArray(array);
        return criarListaComTitulo(nomeLista, array, cor);
    }
}
