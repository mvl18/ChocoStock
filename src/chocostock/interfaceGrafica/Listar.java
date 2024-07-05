package chocostock.interfaceGrafica;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import chocostock.colaboradores.Cliente;
import chocostock.loja.Loja;

public class Listar extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private Loja loja;

    public Listar(Loja loja) {
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

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
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
        private Listar listarPanel;
        private boolean isRemoveButton;
        private int row;

        public ButtonEditor(JCheckBox checkBox, Loja loja, Listar listarPanel, boolean isRemoveButton) {
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
        public Object getCellEditorValue() {
            if (isPushed) {
                int row = table.convertRowIndexToModel(this.row);
                if (row >= 0 && row < listarPanel.table.getRowCount()) {
                    int clientId = (int) listarPanel.table.getValueAt(row, 0);

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
                            return button.getText();
                        }
                    }
                }
            }
            isPushed = false;
            return new String("TESTANDO");
        }



        @Override
        public boolean stopCellEditing() {
            if (row >= 0 && row < listarPanel.table.getRowCount()) {
                return super.stopCellEditing();
            }
            return false;
        }


        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}
