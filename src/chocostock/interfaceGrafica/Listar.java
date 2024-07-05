package chocostock.interfaceGrafica;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import chocostock.auxiliar.Endereco;
import chocostock.colaboradores.Cliente;
import chocostock.loja.Loja;

// Classe personalizada de DefaultTableModel
class CustomTableModel extends DefaultTableModel {
    public CustomTableModel(String[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // Permitir edição apenas nas colunas "Editar" e "Remover"
        String columnName = getColumnName(column);
        return columnName.equals("Editar") || columnName.equals("Remover");
    }
}

public class Listar<T> extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private String nomeObjeto;
    private ArrayList<T> dataList;

    public Listar(Loja loja, String nomeObjeto, ArrayList<T> dataList, String[] nomesColunasOficiais) { // nao esta pegando as paradas do super de um classe
        this.nomeObjeto = nomeObjeto;
        this.dataList = dataList;
        //nomesColunas += {"Editar", "Remover"};
        String[] columnNames = getFieldNames(dataList.get(0));
        model = new CustomTableModel(columnNames, 0);
        table = new JTable(model);

        table.getTableHeader().setReorderingAllowed(false);


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

        setColumnOrder(nomesColunasOficiais); // muda a ordem das colunas de acordo com nomesColunasOficiais BUG -> falta melhorar
    }

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

    private void setColumnOrder(String[] order) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < order.length; i++) {
            int index = columnModel.getColumnIndex(order[i]);
            if (index != i) {
                columnModel.moveColumn(index, i);
            }
        }
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
        public Object getCellEditorValue() {
            if (isPushed) {
                int modelRow = table.convertRowIndexToModel(this.row);
                if (modelRow >= 0 && modelRow < model.getRowCount()) {
                    // Supondo que o ID é sempre a primeira coluna
                    int objectId = (int) model.getValueAt(modelRow, 1);

                    if (isRemoveButton) {
                        // loja.removeClientePorId(objectId); // BUG -> precisa criar um removeObjetoPorId para cada objeto listavel
                        listarPanel.refreshTable();
                        JOptionPane.showMessageDialog(button, nomeObjeto + " removido: " + objectId);
                    } else {
                        T objeto = (T) loja.getClientePorId(objectId); // BUG -> precisa criar um getObjetoPorId para cada objeto listavel
                        if (objeto != null) {
                            try {
                                Class<?> clazz = objeto.getClass();
                                while (clazz != null) {
                                    for (Field field : clazz.getDeclaredFields()) {
                                        field.setAccessible(true);
                                        Object oldValue = field.get(objeto);
                                        String novoValor = JOptionPane.showInputDialog("Novo valor para " + field.getName() + ":", oldValue);
                                        if (novoValor != null) {
                                            // Converter o valor da string para o tipo adequado
                                            Object typedValue = convertToFieldType(field, novoValor);
                                            field.set(objeto, typedValue);
                                        }
                                    }
                                    clazz = clazz.getSuperclass();
                                }
                                listarPanel.refreshTable();
                                JOptionPane.showMessageDialog(button, "Objeto atualizado: " + objectId);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            isPushed = false;
            return button.getText();
        }

        private Object convertToFieldType(Field field, String value) {
            Class<?> type = field.getType();
            if (type == int.class || type == Integer.class) {
                return Integer.parseInt(value);
            } else if (type == long.class || type == Long.class) {
                return Long.parseLong(value);
            } else if (type == double.class || type == Double.class) {
                return Double.parseDouble(value);
            } else if (type == boolean.class || type == Boolean.class) {
                return Boolean.parseBoolean(value);
            } else if (type == ArrayList.class) {
                ArrayList<String> list = new ArrayList<>(Arrays.asList(value.split(","))); // transforma lista em array
                return list;
            } else if (type == Endereco.class) { // BUG -> como converter string em endereço?
                // return Endereco.parseEndereco(value);
                return value; // BUGADO APENAS PARA NAO DAR ERRO NO CODIGO
            }

            else {
                return value; // Trata outros tipos como string
            }
        }


//        @Override
//        public Object getCellEditorValue() { // BUG ao tirar o ultimo elemento da lista
//            if (isPushed) {
//                int modelRow = table.convertRowIndexToModel(this.row);
//                if (modelRow >= 0 && modelRow < model.getRowCount()) {
//                    int clientId = (int) model.getValueAt(modelRow, 0);
//
//                    if (isRemoveButton) {
//                        loja.removeClientePorId(clientId);
//                        listarPanel.refreshTable();
//                        JOptionPane.showMessageDialog(button, "Cliente removido: " + clientId);
//                    } else {
//                        Cliente cliente = loja.getClientePorId(clientId);
//                        if (cliente != null) {
//                            String novoNome = JOptionPane.showInputDialog("Novo Nome:", cliente.getNome());
//                            String novoTelefone = JOptionPane.showInputDialog("Novo Telefone:", cliente.getTelefone());
//                            String novoEmail = JOptionPane.showInputDialog("Novo Email:", cliente.getEmail());
//
//                            cliente.setNome(novoNome);
//                            cliente.setTelefone(novoTelefone);
//                            cliente.setEmail(novoEmail);
//
//                            listarPanel.refreshTable();
//                            JOptionPane.showMessageDialog(button, "Cliente atualizado: " + clientId);
//                        }
//                    }
//                }
//            }
//            isPushed = false;
//            return button.getText();
//        }

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
