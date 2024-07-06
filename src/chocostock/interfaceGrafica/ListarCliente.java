package chocostock.interfaceGrafica;

import chocostock.colaboradores.Cliente;
import chocostock.loja.Loja;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ListarCliente extends JPanel {
    private final JTable table;
    private final DefaultTableModel model;
    private final Loja loja;

    public ListarCliente(Loja loja) {
        this.loja = loja;
        String[] columnNames = {"ID", "Nome", "Telefone", "Email", "Pedidos", "Editar", "Remover"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        // Adicionando dados à tabela
        for (Cliente cliente : loja.getClientes()) {
            Object[] row = {
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getPedidos().toString(),
                "Editar", // Representa o botão de editar
                "❌"  // Representa o botão de remover
            };
            model.addRow(row);
        }

        table.getColumn("Editar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), loja, this, false));
        table.getColumn("Remover").setCellRenderer(new ButtonRenderer());
        table.getColumn("Remover").setCellEditor(new ButtonEditor(new JCheckBox(), loja, this, true));



        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTable() {
        model.setRowCount(0); // Limpa os dados existentes
        for (Cliente cliente : loja.getClientes()) {
            Object[] row = {
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getPedidos().toString(),
                "Editar",
                "❌"
            };
            model.addRow(row);
        }
    }

    // Renderer for the buttons
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean isPushed;
        private Loja loja;
        private ListarCliente listarPanel;
        private boolean isRemoveButton;
        private int row;

        public ButtonEditor(JCheckBox checkBox, Loja loja, ListarCliente listarPanel, boolean isRemoveButton) {
            super(checkBox);
            this.loja = loja;
            this.listarPanel = listarPanel;
            this.isRemoveButton = isRemoveButton;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            button.setText((value == null) ? "" : value.toString());
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() { // BUG ao tirar o ultimo elemento da lista
            if (isPushed) {
                int modelRow = table.convertRowIndexToModel(this.row);
                if (modelRow >= 0 && modelRow < model.getRowCount()) {
                    int clientId = (int) model.getValueAt(modelRow, 0);
                    if (isRemoveButton) {
                        loja.removeClientePorId(clientId);
                        listarPanel.refreshTable();
                        JOptionPane.showMessageDialog(button, "Cliente removido: " + clientId);
                    } else {
                        Cliente cliente = loja.getClientePorId(clientId);
                        if (cliente != null) {
                            String novoNome = JOptionPane.showInputDialog("Novo Nome:", cliente.getNome());
                            String novoTelefone = JOptionPane.showInputDialog("Novo Telefone:", cliente.getTelefone());
                            String novoEmail = JOptionPane.showInputDialog("Novo Email:", cliente.getEmail());

                            cliente.setNome(novoNome);
                            cliente.setTelefone(novoTelefone);
                            cliente.setEmail(novoEmail);

                            listarPanel.refreshTable();
                            JOptionPane.showMessageDialog(button, "Cliente atualizado: " + clientId);
                        }
                    }
                }
            }
            isPushed = false;
            return button.getText();
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}
