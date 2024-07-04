package chocostock.interfaceGrafica;

import javax.swing.*;
import java.awt.*;

public class Registrar extends JPanel {
    private JTextField tfNome;
    private JTextField tfTelefone;
    private JTextField tfEmail;
    private JTextField tfEndereco;
    private JButton button1;
    private JPanel painelRegistro;

    public Registrar() {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Novo cliente", SwingConstants.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 48));
        add(titulo, BorderLayout.NORTH);

        painelRegistro = new JPanel();
        painelRegistro.setLayout(new GridLayout(5, 2, 5, 5)); // 5x2 grid with gaps

        Font textoPeq = new Font("Tahome", Font.PLAIN, 26);

        JLabel lblNome = new JLabel("Nome");
        lblNome.setFont(textoPeq);
        tfNome = new JTextField();
        tfNome.setFont(textoPeq);
        painelRegistro.add(lblNome);
        painelRegistro.add(tfNome);

        JLabel lblTelefone = new JLabel("Telefone");
        lblTelefone.setFont(textoPeq);
        tfTelefone = new JTextField();
        tfTelefone.setFont(textoPeq);
        painelRegistro.add(lblTelefone);
        painelRegistro.add(tfTelefone);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(textoPeq);
        tfEmail = new JTextField();
        tfEmail.setFont(textoPeq);
        painelRegistro.add(lblEmail);
        painelRegistro.add(tfEmail);

        JLabel lblEndereco = new JLabel("Endereço");
        lblEndereco.setFont(textoPeq);
        tfEndereco = new JTextField();
        tfEndereco.setFont(textoPeq);
        painelRegistro.add(lblEndereco);
        painelRegistro.add(tfEndereco);

        button1 = new JButton("Registrar");
        button1.setFont(textoPeq);
        painelRegistro.add(new JLabel()); // label vazio para preencher a célula
        painelRegistro.add(button1);

        add(painelRegistro, BorderLayout.CENTER);
    }
}
