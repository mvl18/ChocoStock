package chocostock.interfaceGrafica;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import chocostock.auxiliar.Endereco;
import chocostock.auxiliar.Verifica;
import chocostock.colaboradores.Cliente;
import chocostock.enums.Cargos;
import chocostock.enums.TiposEmbalagens;
import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Identificavel;
import chocostock.interfaces.ValidadorInput;
import chocostock.itens.suprimentos.Embalagem;
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

public class Listar<T extends Identificavel> extends JPanel implements ValidadorInput, AddRemovivel {
    private JTable table;
    private DefaultTableModel model;
    private String nomeObjeto;
    private ArrayList<T> dataList;
    private String[] nomesColunasOficiais;
    private int[] colWidths;

    public Listar(Loja loja, String nomeObjeto, ArrayList<T> dataList, String[] nomesColunasOficiais, int[] colWidths) { // nao esta pegando as paradas do super de um classe
        this.nomeObjeto = nomeObjeto;
        this.dataList = dataList;
        this.nomesColunasOficiais = nomesColunasOficiais;
        this.colWidths = colWidths;  // Armazene as larguras das colunas
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

        setColumnOrder(nomesColunasOficiais, colWidths); // muda a ordem das colunas de acordo com nomesColunasOficiais BUG -> falta melhorar
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


    // BUG -> verificar se esse codigo funciona
    private void setColumnOrder(String[] order, int[] colWidths) {
        if (order.length == colWidths.length) {
            System.out.println("NORMAL");
        }
        TableColumnModel columnModel = table.getColumnModel();
        ArrayList<String> orderList = new ArrayList<>(Arrays.asList(order));

        // Adiciona "Editar" e "Remover" se não estiverem presentes
        if (!orderList.contains("Editar")) {
            orderList.add("Editar");
        }
        if (!orderList.contains("Remover")) {
            orderList.add("Remover");
        }

        // Remover colunas não desejadas
        for (int i = columnModel.getColumnCount() - 1; i >= 0; i--) {
            TableColumn column = columnModel.getColumn(i);
            if (!orderList.contains(column.getHeaderValue().toString())) {
                columnModel.removeColumn(column);
            }
        }

        // Reordenar as colunas e definir suas larguras
        for (int i = 0; i < orderList.size(); i++) {
            int index = columnModel.getColumnIndex(orderList.get(i));
            if (index != i) {
                columnModel.moveColumn(index, i);
            }
            // Define a largura da coluna
//            TableColumn column = columnModel.getColumn(i);
//            column.setMinWidth(colWidths[i]);
//            column.setPreferredWidth(colWidths[i]);
//            column.setMaxWidth(colWidths[i]);
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
                    int objectId = (int) model.getValueAt(modelRow, table.getColumn("id").getModelIndex());

                    if (isRemoveButton) {
                        removeObjetoPorId(objectId, dataList);
                        listarPanel.refreshTable();
                        JOptionPane.showMessageDialog(button, nomeObjeto + " removido: " + objectId);
                    } else {
                        T objeto = getObjetoPorId(objectId, dataList);
                        if (objeto != null) {
                            try {
                                Class<?> clazz = objeto.getClass();
                                while (clazz != null) {
                                    for (Field field : clazz.getDeclaredFields()) {
                                        field.setAccessible(true);
                                        Object oldValue = field.get(objeto);
                                        ArrayList<String> nomesColunasList = new ArrayList<String>(Arrays.asList(nomesColunasOficiais));
                                        if (nomesColunasList.contains(field.getName())) {
                                            String novoValor = JOptionPane.showInputDialog("Novo valor para " + field.getName() + ":", oldValue);

                                            boolean valido = false;
                                            while (!valido) {
                                                if (novoValor != null) {
                                                    switch (field.getName()) {
                                                        case "id", "quantidade_por_pacote", "quantidade":
                                                            valido = Verifica.isNatural(novoValor);
                                                            break;
                                                        case "nome":
                                                            valido = Verifica.isNome(novoValor);
                                                            break;
                                                        case "email":
                                                            valido = Verifica.isEmail(novoValor);
                                                            break;
                                                        case "telefone":
                                                            valido = Verifica.isTelefone(novoValor);
                                                            break;
                                                        case "cnpj", "cnpj_fornecedor":
                                                            valido = Verifica.isCnpj(novoValor);
                                                            break;
                                                        case "tipo_embalagem":
                                                            valido = Verifica.isEmbalagem(novoValor);
                                                            break;
                                                        case "cargo":
                                                            valido = Verifica.isCargo(novoValor);
                                                            break;
                                                        case "endereco":
                                                            valido = Verifica.isEndereco(novoValor); // falta criar
                                                            break;
                                                        case "data":
                                                            valido = Verifica.isData(novoValor);
                                                            break;
                                                        case "data_entrega":
                                                            valido = Verifica.isDataFutura(novoValor);
                                                            break;
                                                        case "preco_pacote", "preco_total", "salario":
                                                            valido = Verifica.isFloat(novoValor);
                                                            break;
                                                        default:
                                                            valido = true;
                                                    }
                                                }
                                                if (!valido) {
                                                    novoValor = JOptionPane.showInputDialog("Valor de " + field.getName() + " inválido. Digite novamente: ", oldValue);
                                                }
                                            }
                                            // Converter o valor da string para o tipo adequado
                                            Object typedValue = convertToFieldType(field, novoValor);
                                            field.set(objeto, typedValue);
                                        }
                                    }
                                    clazz = clazz.getSuperclass();
                                }
                                listarPanel.refreshTable();
                                JOptionPane.showMessageDialog(button,  nomeObjeto + " atualizado: " + objectId);
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
                ArrayList<String> list = new ArrayList<>(Arrays.asList(value.split(",")));
                return list;
            } else if (type == Endereco.class) {
                return Endereco.parseEndereco(value);
            } else if (type == LocalDate.class) {
                return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } else if (type == Embalagem.class) {
                return TiposEmbalagens.parseEmbalagem(value);
            } else if (type == Cargos.class) {
                return Cargos.parseCargo(value);
            }
            else {
                return value; // Trata outros tipos como string
            }
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
