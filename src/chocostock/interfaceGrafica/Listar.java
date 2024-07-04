package chocostock.interfaceGrafica;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

import chocostock.colaboradores.Cliente;
import chocostock.loja.Loja;

public class Listar<T> extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private ArrayList<T> dataList;

    public Listar(Loja loja, ArrayList<T> dataList) { // nao esta pegando as paradas do super de um classe
        this.dataList = dataList;
        //nomesColunas += {"Editar", "Remover"};
        String[] columnNames = getFieldNames(dataList.get(0));
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        // Adicionando dados à tabela
        refreshTable();

        table.getColumn("Editar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), loja, this, false));
        table.getColumn("Remover").setCellRenderer(new ButtonRenderer());
        table.getColumn("Remover").setCellEditor(new ButtonEditor(new JCheckBox(), loja, this, true));



        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        add(scrollPane, BorderLayout.CENTER);
    }

//    public <T> void refreshTable(ArrayList<T> listaObjeto) {
//        model.setRowCount(0); // Limpa os dados existentes
//        for (Object objeto : listaObjeto) {
//            Object[] row = {
//                cliente.getId(),
//                cliente.getNome(),
//                cliente.getTelefone(),
//                cliente.getEmail(),
//                cliente.getPedidos().toString(),
//                "Editar",
//                "❌"
//            };
//            model.addRow(row);
//        }
//    }

    public void refreshTable() {
        model.setRowCount(0); // Limpa os dados existentes
        for (T item : dataList) {
            Object[] row = getFieldValues(item);
            model.addRow(row);
        }
    }

    private String[] getFieldNames(T obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        ArrayList<String> nomes = new ArrayList<>();
        Class<T> clazz = (Class<T>) obj.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                nomes.add(field.getName());
            }
            clazz = (Class<T>) clazz.getSuperclass();
        }
        nomes.add("Editar");
        nomes.add("Remover");
        return nomes.toArray(new String[0]);
    }

    // Obtém os valores dos campos de um objeto, incluindo os da superclasse
    private Object[] getFieldValues(T obj) {
        ArrayList<Object> values = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        try {
            while (clazz != null) {
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    values.add(field.get(obj));
                }
                clazz = clazz.getSuperclass();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        values.add("Editar");
        values.add("❌");
        return values.toArray(new Object[0]);
    }










    // FALTA FAZER ESSES BOTOES FUNCIONAREM PRA QUALQUER LISTA DE COISA

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
