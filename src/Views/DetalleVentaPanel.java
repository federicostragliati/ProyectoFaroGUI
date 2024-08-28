package Views;

import Controller.DetalleVentaController;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DetalleVentaPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private final DetalleVentaController controller = new DetalleVentaController();

    public DetalleVentaPanel() {
        // Crear el modelo de la tabla con columnas específicas
        tableModel = new DefaultTableModel(new Object[]{"ID Producto", "Nombre", "Unidad de Venta", "Cantidad", "Precio Unitario", "Precio por Cantidad"}, 0);
        table = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 3; // Solo permite editar ID Producto y Cantidad
            }
        };

        // Agregar un TableModelListener para manejar cambios en la tabla
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    if (column == 0) { // ID Producto
                        controller.actualizarDatosProducto(row, tableModel);

                        // Mover el foco a la columna "Cantidad" (índice 3)
                        SwingUtilities.invokeLater(() -> {
                            table.editCellAt(row, 3);
                            table.setColumnSelectionInterval(3, 3);
                            table.requestFocusInWindow();
                        });
                    } else if (column == 3) { // Cantidad
                        controller.calcularPrecioPorCantidad(row, tableModel);
                    }
                }
            }
        });

        // Agregar un KeyListener para mover el foco a la celda de la derecha al presionar Enter
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int row = table.getSelectedRow();
                    int column = table.getSelectedColumn();

                    // Mueve el foco a la celda de la derecha
                    int newColumn = column + 1;
                    if (newColumn < table.getColumnCount()) {
                        table.editCellAt(row, newColumn);
                        table.setColumnSelectionInterval(newColumn, newColumn);
                    }
                    e.consume(); // Consume el evento para evitar que la acción predeterminada se ejecute
                }
            }
        });

        // Botón para agregar una nueva fila
        JButton agregarFilaButton = new JButton("Agregar Fila");
        agregarFilaButton.addActionListener(e -> {
            tableModel.addRow(new Object[]{"", "", "", "", ""});
            int lastRow = tableModel.getRowCount() - 1;
            table.editCellAt(lastRow, 0); // Poner el foco en la primera celda de la nueva fila
            table.setRowSelectionInterval(lastRow, lastRow);
            table.setColumnSelectionInterval(0, 0);
            table.requestFocusInWindow();
        });

        table.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK), "agregarFila");
        table.getActionMap().put("agregarFila", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Object[]{"", "", "", "", ""});
                int lastRow = tableModel.getRowCount() - 1;
                table.editCellAt(lastRow, 0); // Poner el foco en la primera celda de la nueva fila
                table.setRowSelectionInterval(lastRow, lastRow);
                table.setColumnSelectionInterval(0, 0);
                table.requestFocusInWindow();
            }
        });

        // Botón para confirmar y procesar los productos seleccionados
        JButton confirmarButton = new JButton("Confirmar");
        confirmarButton.addActionListener(e -> {
            // Aquí puedes procesar los productos seleccionados
            JOptionPane.showMessageDialog(this, "Productos confirmados");
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(agregarFilaButton);
        buttonPanel.add(confirmarButton);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
