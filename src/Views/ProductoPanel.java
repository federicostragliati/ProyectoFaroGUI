package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

import Controller.ProductoController;
import dao.implementaciones.ProductoDAOImpMySQL;
import dominio.Producto;
import dominio.enums.Unidad;

public class ProductoPanel extends JPanel {

    private JPanel originalPanel; // Variable para almacenar el panel original
    private final ProductoController productoController = new ProductoController();


    public ProductoPanel() {
        setLayout(new BorderLayout());

        // Crear el panel para los botones de productos
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1)); // 5 botones en una columna

        // Crear y agregar botones
        JButton createButton = new JButton("Crear Producto");
        JButton modifyButton = new JButton("Modificar Producto");
        JButton consultButton = new JButton("Consultar Producto");
        JButton listButton = new JButton("Consultar Listado");
        JButton deleteButton = new JButton("Baja Producto");

        buttonPanel.add(createButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(consultButton);
        buttonPanel.add(listButton);
        buttonPanel.add(deleteButton);

        // Agregar el panel de botones al panel principal
        add(buttonPanel, BorderLayout.WEST);

        // Crear el área para mostrar la información relacionada // ¿Que es esto? Comentarlo para probar
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoArea);
        add(scrollPane, BorderLayout.CENTER);

        // Agregar acciones a los botones
        createButton.addActionListener(e -> showCreateProductDialog());
        modifyButton.addActionListener(e -> showModifyProductDialog());
        consultButton.addActionListener(e -> showGetProductDialog());
        listButton.addActionListener(e -> showListaProductos(this));
        deleteButton.addActionListener(e -> showBajaProducto());
    }

    private void showCreateProductDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Crear Producto", true);
        dialog.setSize(500, 500);
        dialog.setLayout(new GridLayout(6, 2));
        dialog.setLocationRelativeTo(this);

        dialog.add(new JLabel("Detalle:"));
        JTextField campoDetalle = new JTextField();
        dialog.add(campoDetalle);

        dialog.add(new JLabel("Cantidad:"));
        JTextField campoCantidad = new JTextField();
        dialog.add(campoCantidad);

        dialog.add(new JLabel("Precio Unitario:"));
        JTextField campoPrecio = new JTextField();
        dialog.add(campoPrecio);

        dialog.add(new JLabel("Unidad de Venta:"));
        JTextField campoUnidad = new JTextField();
        dialog.add(campoUnidad);

        dialog.add(new JLabel("Activo:"));
        JCheckBox checkBoxActivo = new JCheckBox();
        dialog.add(checkBoxActivo);

        JPanel buttonPanel = new JPanel();
        JButton acceptButton = new JButton("Aceptar");
        JButton cancelButton = new JButton("Cancelar");
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel);

        acceptButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,productoController.crearProductoValido(campoDetalle.getText(), campoCantidad.getText(),
                    campoPrecio.getText(), campoUnidad.getText(), checkBoxActivo.isSelected()));
        });

        cancelButton.addActionListener(e -> dialog.dispose()); // Cierra el diálogo

        dialog.setVisible(true); // Muestra el diálogo
    }

    private void showModifyProductDialog() {
        AtomicReference<Integer> idOriginal = new AtomicReference<>(new Integer(0));

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modificar Producto", true);
        dialog.setSize(500, 500);
        dialog.setLayout(new GridLayout(7, 2));
        dialog.setLocationRelativeTo(this);

        dialog.add(new JLabel("ID del producto a Modificar:"));
        JTextField idField = new JTextField();
        dialog.add(idField);

        dialog.add(new JLabel("Detalle:"));
        JTextField detailField = new JTextField();
        dialog.add(detailField);

        dialog.add(new JLabel("Cantidad:"));
        JTextField quantityField = new JTextField();
        dialog.add(quantityField);

        dialog.add(new JLabel("Precio Unitario:"));
        JTextField priceField = new JTextField();
        dialog.add(priceField);

        dialog.add(new JLabel("Unidad de Venta:"));
        JTextField unitField = new JTextField();
        dialog.add(unitField);

        JPanel buttonPanel = new JPanel();
        JButton buscarButton = new JButton("Buscar");
        JButton acceptButton = new JButton("Aceptar");
        JButton cancelButton = new JButton("Cancelar");
        buttonPanel.add(buscarButton);
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel);

        buscarButton.addActionListener(e -> {
            String id = idField.getText();
            ProductoDAOImpMySQL productoDAOImpMySQL = new ProductoDAOImpMySQL();
            Producto producto;
            try {
                producto = productoDAOImpMySQL.getProducto(Integer.parseInt(id));
            } catch (SQLException | ClassNotFoundException | IOException ex) {
                throw new RuntimeException(ex);
            }

            idOriginal.set(producto.getId());
            detailField.setText(producto.getDetalle());
            quantityField.setText(String.valueOf(producto.getCantidad()));
            priceField.setText(String.valueOf(producto.getPrecioUnitario()));
            unitField.setText(String.valueOf(producto.getUnidad()));
        });

        acceptButton.addActionListener(e -> {
            String detail = detailField.getText();
            String quantity = quantityField.getText();
            String price = priceField.getText();
            String unit = unitField.getText();

            ProductoDAOImpMySQL productoDAOImpMySQL = new ProductoDAOImpMySQL();
            BigDecimal cantidad = new BigDecimal(quantity);
            BigDecimal precio = new BigDecimal(price);

            try {
                productoDAOImpMySQL.updateProducto(new Producto(idOriginal.get(), detail, cantidad, precio, Unidad.valueOf(unit), true));
            } catch (SQLException | ClassNotFoundException | IOException ex) {
                throw new RuntimeException(ex);
            }
            dialog.dispose(); // Cierra el diálogo
        });

        cancelButton.addActionListener(e -> dialog.dispose()); // Cierra el diálogo

        dialog.setVisible(true); // Muestra el diálogo
    }

    private void showGetProductDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Consultar Producto", true);
        dialog.setSize(400, 400); // Tamaño ajustado
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes

        // Fila 0
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        dialog.add(new JLabel("ID del Producto:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField idField = new JTextField(20); // Ajustado el ancho
        dialog.add(idField, gbc);

        // Fila 1
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        dialog.add(new JLabel("Detalle:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea detalleField = new JTextArea(2, 20); // Ajustado el tamaño
        detalleField.setEditable(false);
        JScrollPane detalleScroll = new JScrollPane(detalleField); // Añadido JScrollPane para el texto
        dialog.add(detalleScroll, gbc);

        // Fila 2
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        dialog.add(new JLabel("Cantidad:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea cantidadField = new JTextArea(2, 20); // Ajustado el tamaño
        cantidadField.setEditable(false);
        JScrollPane cantidadScroll = new JScrollPane(cantidadField); // Añadido JScrollPane para el texto
        dialog.add(cantidadScroll, gbc);

        // Fila 3
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        dialog.add(new JLabel("Precio Unitario:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea precioField = new JTextArea(2, 20); // Ajustado el tamaño
        precioField.setEditable(false);
        JScrollPane precioScroll = new JScrollPane(precioField); // Añadido JScrollPane para el texto
        dialog.add(precioScroll, gbc);

        // Fila 4
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        dialog.add(new JLabel("Unidad de Venta:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea unidadField = new JTextArea(2, 20); // Ajustado el tamaño
        unidadField.setEditable(false);
        JScrollPane unidadScroll = new JScrollPane(unidadField); // Añadido JScrollPane para el texto
        dialog.add(unidadScroll, gbc);

        // Fila 5
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        dialog.add(new JLabel("Activo:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea activoField = new JTextArea(2, 20); // Ajustado el tamaño
        activoField.setEditable(false);
        JScrollPane activoScroll = new JScrollPane(activoField); // Añadido JScrollPane para el texto
        dialog.add(activoScroll, gbc);

        // Fila 6 (botones)
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        JButton buscarButton = new JButton("Buscar");
        JButton cancelButton = new JButton("Cancelar");
        buttonPanel.add(buscarButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, gbc);

        buscarButton.addActionListener(e -> {

            String [] datos = productoController.realizarConsultaValida(idField.getText());

            if ( datos.length == 1 ) {
                JOptionPane.showMessageDialog(null,datos[0]);
            } else {
                detalleField.setText(datos[0]);
                cantidadField.setText(datos[1]);
                precioField.setText(datos[2]);
                unidadField.setText(datos[3]);
                activoField.setText(datos[4]);
            }


        });

        cancelButton.addActionListener(e -> dialog.dispose()); // Cierra el diálogo

        dialog.setLocationRelativeTo(this); // Centra el diálogo respecto al JFrame padre
        dialog.setVisible(true);
    }

    private void showListaProductos(JPanel parentPanel) {
        if (originalPanel == null) {
            // Guardar una copia del panel original
            originalPanel = new JPanel(new BorderLayout());
            for (Component comp : parentPanel.getComponents()) {
                originalPanel.add(comp);
            }
        }

        ProductoDAOImpMySQL productoDAOImpMySQL = new ProductoDAOImpMySQL();
        String[] columnNames = {"ID", "Detalle", "Cantidad", "Precio Unitario", "Unidad", "Activo"};
        DefaultTableModel tableModel = new CustomTableModelProducto(new Object[][]{}, columnNames);

        try {
            java.util.List<Producto> productos = productoDAOImpMySQL.getProductos();
            for (Producto producto : productos) {
                Object[] rowData = {
                        producto.getId(),
                        producto.getDetalle(),
                        producto.getCantidad(),
                        producto.getPrecioUnitario(),
                        producto.getUnidad(),
                        producto.isActivo()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            throw new RuntimeException(ex);
        }

        JTable productTable = new JTable(tableModel);
        productTable.setDefaultEditor(Object.class, null); // Hace la tabla no editable
        productTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Permite selección múltiple de filas y columnas
        productTable.setAutoCreateRowSorter(true); // Habilita el ordenamiento en todas las columnas

        // Permite copiar los datos al portapapeles
        Action copyAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                StringBuilder sb = new StringBuilder();
                int[] selectedRows = table.getSelectedRows();
                int[] selectedColumns = table.getSelectedColumns();

                for (int row : selectedRows) {
                    for (int col : selectedColumns) {
                        sb.append(table.getValueAt(row, col)).append("\t");
                    }
                    sb.append("\n");
                }

                StringSelection stringSelection = new StringSelection(sb.toString());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, stringSelection);
            }
        };

        KeyStroke copyKeyStroke = KeyStroke.getKeyStroke("ctrl C");
        productTable.getInputMap().put(copyKeyStroke, "copy");
        productTable.getActionMap().put("copy", copyAction);

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        // Crear un botón de cerrar
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> {
            parentPanel.removeAll();
            // Restaurar el panel original
            parentPanel.add(originalPanel);
            parentPanel.revalidate();
            parentPanel.repaint();
        });

        // Reemplazar el panel con la tabla de productos y el botón de cerrar

        parentPanel.removeAll();
        parentPanel.setLayout(new BorderLayout());
        parentPanel.add(scrollPane, BorderLayout.CENTER);
        parentPanel.add(closeButton, BorderLayout.SOUTH);
        parentPanel.revalidate();
        parentPanel.repaint();
    }

    private void showBajaProducto() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Baja Producto", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new GridLayout(3, 2));
        dialog.setLocationRelativeTo(this);

        dialog.add(new JLabel("ID del Producto:"));
        JTextField idField = new JTextField();
        dialog.add(idField);

        JButton deleteButton = new JButton("Eliminar");
        JButton cancelButton = new JButton("Cancelar");
        dialog.add(deleteButton);
        dialog.add(cancelButton);

        deleteButton.addActionListener(e -> {
            ProductoDAOImpMySQL productoDAOImpMySQL = new ProductoDAOImpMySQL();
            productoDAOImpMySQL.deleteProducto(Integer.parseInt(idField.getText()));
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }


}