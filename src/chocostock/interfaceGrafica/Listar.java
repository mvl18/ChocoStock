package chocostock.interfaceGrafica;

import chocostock.auxiliar.Endereco;
import chocostock.auxiliar.Verifica;
import chocostock.enums.Cargos;
import chocostock.enums.Status;
import chocostock.enums.TiposEmbalagens;
import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Identificavel;
import chocostock.interfaces.ValidadorInput;
import chocostock.itens.suprimentos.Embalagem;
import chocostock.loja.Loja;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Listar<T extends Identificavel> extends JPanel implements ValidadorInput, AddRemovivel {
    private final JTable table;
    private final DefaultTableModel model;
    private final String nomeObjeto;
    private final ArrayList<T> dataList;
    private final String[] nomesColunasOficiais;
    private final Map<String, Integer> largurasColunasMaximas;
    private final Map<String, Integer> largurasColunasMinimas;
    private final ArrayList<String> camposEditaveis;


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

    public Listar(Loja loja, String nomeObjeto, ArrayList<T> dataList, String[] nomesColunasOficiais) {
        this.nomeObjeto = nomeObjeto;
        this.dataList = dataList;
        this.nomesColunasOficiais = nomesColunasOficiais;
      
        this.largurasColunasMaximas = new HashMap<>();
        largurasColunasMaximas.put("id", 100);
        largurasColunasMaximas.put("id_cliente", 100);
        largurasColunasMaximas.put("id_pedido", 100);
        largurasColunasMaximas.put("Editar", 100);
        largurasColunasMaximas.put("Remover", 100);

        this.largurasColunasMinimas = new HashMap<>();
        largurasColunasMinimas.put("id", 50);
        largurasColunasMinimas.put("id_cliente", 50);
        largurasColunasMinimas.put("id_pedido", 50);
        largurasColunasMinimas.put("Editar", 100);
        largurasColunasMinimas.put("Remover", 100);

        this.camposEditaveis = new ArrayList<String>(Arrays.asList(
                "nome", "telefone", "email", "endereco", "cnpj", "site", "cargo", "salario", "preco", "quantidade",
                "tipo_embalagem", "quantidade_por_pacote", "preco_pacote", "dataCompra", "validade", // talvez tirar o fornecedor
                "peso", "embalagem", "id_pedido", "status", "data_entrega", "preco_total"
                )); // Exemplo de campos editáveis

        String[] columnNames;
        boolean vazia = false;
        try {
            columnNames = getFieldNames(dataList.get(0));
        } catch (IndexOutOfBoundsException e) {
            columnNames = nomesColunasOficiais;
            vazia = true;
        }
        model = new CustomTableModel(columnNames, 0);
        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);


        if (!vazia) {
            // Adicionando dados à tabela
            refreshTable();

            table.getColumn("Editar").setCellRenderer(new ButtonRenderer());
            table.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), loja, this, false));
            table.getColumn("Remover").setCellRenderer(new ButtonRenderer());
            table.getColumn("Remover").setCellEditor(new ButtonEditor(new JCheckBox(), loja, this, true));
        }

        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        add(scrollPane, BorderLayout.CENTER);

      
      
        if (!vazia)
            setColumnOrder(nomesColunasOficiais); // muda a ordem das colunas de acordo com nomesColunasOficiais BUG -> falta melhorar
      
        // Adiciona um listener para ajustar a largura das colunas quando a janela for redimensionada
        scrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setColumnWidths();
            }
        });
        setColumnWidths();
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

        // Reordenar as colunas
        for (int i = 0; i < orderList.size(); i++) {
            int index = columnModel.getColumnIndex(orderList.get(i));
            if (index != i) {
                columnModel.moveColumn(index, i);
            }
        }
    }

    private void setColumnWidths() {
        TableColumnModel columnModel = table.getColumnModel();

        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            String columnName = column.getHeaderValue().toString();
            Integer maxWidth = largurasColunasMaximas.get(columnName);
            Integer larguraMin = largurasColunasMinimas.get(columnName);

            if (maxWidth != null && maxWidth != -1) {
                column.setMaxWidth(maxWidth);
            } else {
                column.setMaxWidth(Integer.MAX_VALUE);
            }

            if (larguraMin != null && larguraMin != -1) {
                column.setMinWidth(larguraMin);
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
        private final JButton button;
        private boolean isPushed;
        private Loja loja;
        private final Listar listarPanel;
        private final boolean isRemoveButton;
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
                                        if (camposEditaveis.contains(field.getName())) {
                                            String novoValor = JOptionPane.showInputDialog("Novo valor para " + field.getName() + ":", oldValue);

                                            boolean valido = false;
                                            while (!valido) {
                                                if (novoValor != null) {
                                                    valido = switch (field.getName()) {
                                                        case "id", "quantidade_por_pacote", "quantidade" -> Verifica.isNatural(novoValor);
                                                        case "nome" -> Verifica.isNome(novoValor);
                                                        case "email" -> Verifica.isEmail(novoValor);
                                                        case "telefone" -> Verifica.isTelefone(novoValor);
                                                        case "cnpj", "cnpj_fornecedor" -> Verifica.isCnpj(novoValor);
                                                        case "tipo_embalagem" -> Verifica.isEmbalagem(novoValor);
                                                        case "cargo" -> Verifica.isCargo(novoValor);
                                                        case "endereco" -> Verifica.isEndereco(novoValor);
                                                        case "data" -> Verifica.isDataAmatongas(novoValor);
                                                        case "status" -> Verifica.isStatus(novoValor);
                                                        case "data_entrega", "data_validade" -> Verifica.isDataFuturaAmatongas(novoValor);
                                                        case "preco_pacote", "preco_total", "salario" -> Verifica.isFloat(novoValor);
                                                        default -> true;
                                                    };
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
            } else if (type == float.class || type == Float.class) {
                return Float.parseFloat(value);
            } else if (type == double.class || type == Double.class) {
                return Double.parseDouble(value);
            } else if (type == boolean.class || type == Boolean.class) {
                return Boolean.parseBoolean(value);
            } else if (type == ArrayList.class) {
                return new ArrayList<>(Arrays.asList(value.replaceAll("[|]", "").split(",")));
            } else if (type == Endereco.class) {
                return Endereco.parseEndereco(value);
            } else if (type == LocalDate.class) {
                return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-dd-MM"));
            } else if (type == Embalagem.class) {
                return TiposEmbalagens.parseTipoEmbalagem(value);
            } else if (type == Cargos.class) {
                return Cargos.parseCargo(value);
            } else if (type == Status.class) {
                return Status.parseStatus(value);
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
            try { // solução amatongas
                super.fireEditingStopped();
            } catch (Exception e) {
                System.out.println("Erro " + e);
            }
        }
    }
}