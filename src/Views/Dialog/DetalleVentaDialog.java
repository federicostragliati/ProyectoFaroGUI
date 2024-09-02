package Views.Dialog;

import Controller.DetalleVentaController;
import Model.Auxiliares.ListadoProductos;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaDialog extends JDialog {

    private JTable table;
    private DefaultTableModel tableModel;
    private final DetalleVentaController controller = new DetalleVentaController();
    private List<ListadoProductos> listadoProductos = new ArrayList<>();

    public DetalleVentaDialog(Frame owner, List<ListadoProductos> listadoProductos) {
        super(owner, "Detalle de Venta", true);

        // Configurar el JDialog
        setSize(600, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        this.listadoProductos = listadoProductos; // Inicializar con los productos existentes si los hay

        // Crear el modelo de la tabla con columnas específicas
        tableModel = new DefaultTableModel(new Object[]{"ID Producto", "Nombre", "Unidad de Venta", "Cantidad", "Precio Unitario", "Precio por Cantidad"}, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 3; // Solo permite editar ID Producto y Cantidad
            }
        };

        // Cargar productos existentes en la tabla
        for (ListadoProductos producto : listadoProductos) {
            tableModel.addRow(new Object[]{
                    producto.getId(),
                    producto.getDetalle(),
                    producto.getUnidad(),
                    producto.getCantidad(),
                    producto.getValor(),
                    producto.getValorPorCantidad()
            });
        }

        // Agregar un TableModelListener para manejar cambios en la tabla
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    if (column == 0) { // ID Producto
                        controller.actualizarDatosProducto(row, tableModel);
                        if (tableModel.getValueAt(row,2).toString().isEmpty()) {
                            JOptionPane.showMessageDialog(null,"Producto Invalido");
                        } else {
                            SwingUtilities.invokeLater(() -> {
                                table.editCellAt(row, 3);
                                table.setColumnSelectionInterval(3, 3);
                                table.requestFocusInWindow();
                            });
                        }
                        // Mover el foco a la columna "Cantidad" (índice 3)

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
            listadoProductos.clear(); // Limpiar la lista actual
            listadoProductos.addAll(controller.getListado(tableModel)); // Añadir todos los elementos nuevos
            JOptionPane.showMessageDialog(this, "Productos confirmados");
            this.dispose(); // Cerrar el diálogo
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(agregarFilaButton);
        buttonPanel.add(confirmarButton);

        // Añadir componentes al JDialog
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public DetalleVentaDialog(Frame owner, String title, List<ListadoProductos> listadoProductos) {
        super(owner, title, true);  // Establece la modalidad a 'true'
        this.listadoProductos = listadoProductos;

        // Configurar el JDialog
        setSize(600, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        this.listadoProductos = listadoProductos; // Inicializar con los productos existentes si los hay

        // Crear el modelo de la tabla con columnas específicas
        tableModel = new DefaultTableModel(new Object[]{"ID Producto", "Nombre", "Unidad de Venta", "Cantidad", "Precio Unitario", "Precio por Cantidad"}, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 3; // Solo permite editar ID Producto y Cantidad
            }
        };

        // Cargar productos existentes en la tabla
        for (ListadoProductos producto : listadoProductos) {
            tableModel.addRow(new Object[]{
                    producto.getId(),
                    producto.getDetalle(),
                    producto.getUnidad(),
                    producto.getCantidad(),
                    producto.getValor(),
                    producto.getValorPorCantidad()
            });
        }

        // Agregar un TableModelListener para manejar cambios en la tabla
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    if (column == 0) { // ID Producto
                        controller.actualizarDatosProducto(row, tableModel);
                        if (tableModel.getValueAt(row,2).toString().isEmpty()) {
                            JOptionPane.showMessageDialog(null,"Producto Invalido");
                        } else {
                            SwingUtilities.invokeLater(() -> {
                                table.editCellAt(row, 3);
                                table.setColumnSelectionInterval(3, 3);
                                table.requestFocusInWindow();
                            });
                        }
                        // Mover el foco a la columna "Cantidad" (índice 3)

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



        // Botón para confirmar y procesar los productos seleccionados
        JButton confirmarButton = new JButton("Confirmar");
        confirmarButton.addActionListener(e -> {
            listadoProductos.clear(); // Limpiar la lista actual
            listadoProductos.addAll(controller.getListado(tableModel)); // Añadir todos los elementos nuevos
            JOptionPane.showMessageDialog(this, "Productos confirmados");
            this.dispose(); // Cerrar el diálogo
        });

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(confirmarButton);

        // Añadir componentes al JDialog
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public List<ListadoProductos> getListadoProductos() {
        return listadoProductos;
    }
}
