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
    private final ArrayList<T> listaDados;
    private final Map<String, Integer> largurasColunasMaximas;
    private final Map<String, Integer> largurasColunasMinimas;
    private final ArrayList<String> camposEditaveis;


    // Classe personalizada de DefaultTableModel
    static class CustomTableModel extends DefaultTableModel {
        public CustomTableModel(String[] nomesColunas, int rowCount) {
            super(nomesColunas, rowCount);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            // Permitir edição apenas nas colunas "Editar" e "Remover"
            String columnName = getColumnName(column);
            return columnName.equals("Editar") || columnName.equals("Remover");
        }
    }

    public Listar(String nomeObjeto, ArrayList<T> listaDados, String[] nomesColunasOficiais) {
        this.nomeObjeto = nomeObjeto;
        this.listaDados = listaDados;

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

        this.camposEditaveis = new ArrayList<>(Arrays.asList(
                "nome", "telefone", "email", "endereco", "cnpj", "site", "cargo", "salario", "preco", "quantidade",
                "quantidade_por_pacote", "preco_pacote", "dataCompra", "validade", // talvez tirar o fornecedor
                "peso", "id_pedido", "status", "data_entrega", "preco_total"
                )); // Exemplo de campos editáveis

        String[] nomesColunas;
        boolean vazia = false;
        try {
            nomesColunas = getNomesCampos(listaDados.get(0));
        } catch (IndexOutOfBoundsException e) {
            nomesColunas = nomesColunasOficiais;
            vazia = true;
        }
        model = new CustomTableModel(nomesColunas, 0);
        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);


        if (!vazia) {
            // Adicionando dados à tabela
            refreshTable();

            table.getColumn("Editar").setCellRenderer(new ButtonRenderer());
            table.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), this, false));
            table.getColumn("Remover").setCellRenderer(new ButtonRenderer());
            table.getColumn("Remover").setCellEditor(new ButtonEditor(new JCheckBox(), this, true));
        }

        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        add(scrollPane, BorderLayout.CENTER);

      
      
        if (!vazia)
            setOrdemColunas(nomesColunasOficiais); // muda a ordem das colunas de acordo com nomesColunasOficiais BUG -> falta melhorar
      
        // Adiciona um listener para ajustar a largura das colunas quando a janela for redimensionada
        scrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setLargurasColunas();
            }
        });
        setLargurasColunas();
    }

    public void refreshTable() {
        model.setRowCount(0); // Limpa os dados existentes
        for (T item : listaDados) {
            Object[] row = getValoresCampos(item);
            model.addRow(row);
        }
    }

    private String[] getNomesCampos(T obj) {
        ArrayList<String> nomes = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                nomes.add(field.getName());
            }
            clazz = clazz.getSuperclass();
        }
        nomes.add("Editar");
        nomes.add("Remover");
        return nomes.toArray(new String[0]);
    }

    // Obtém os valores dos campos de um objeto, incluindo os da superclasse
    private Object[] getValoresCampos(T obj) {
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
            System.err.println("Erro: " + e);
        }
        values.add("Editar");
        values.add("❌");
        return values.toArray(new Object[0]);
    }

    private void setOrdemColunas(String[] order) {
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

    private void setLargurasColunas() {
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

    // Renderer for the buttons
    static class ButtonRenderer extends JButton implements TableCellRenderer {
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
        private final Listar<T> listarPanel;
        private final boolean isRemoveButton;
        private int row;

        public ButtonEditor(JCheckBox checkBox, Listar<T> listarPanel, boolean isRemoveButton) {
            super(checkBox);
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
                        removeObjetoPorId(objectId, listaDados);
                        listarPanel.refreshTable();
                        JOptionPane.showMessageDialog(button, nomeObjeto + " removido: " + objectId);
                    } else {
                        T objeto = getObjetoPorId(objectId, listaDados);
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
                                                        case "data" -> Verifica.isDataEUA(novoValor);
                                                        case "status" -> Verifica.isStatus(novoValor);
                                                        case "data_entrega", "data_validade" -> Verifica.isDataFuturaEUA(novoValor);
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
                                System.err.println("Erro: " + e);
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
                return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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