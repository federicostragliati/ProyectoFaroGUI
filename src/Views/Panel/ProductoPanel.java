package Views.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;


import Controller.ProductoController;
import Views.Interfaces.PanelInterface;
import dominio.enums.Unidad;

public class ProductoPanel extends GeneralPanel implements PanelInterface {

    private final ProductoController productoController = new ProductoController();

    public ProductoPanel (String boton1, String boton2, String boton3, String boton4, String boton5) {
        super(boton1, boton2, boton3, boton4, boton5);

        super.getCreateButton().addActionListener(e -> showCreate());
        super.getModifyButton().addActionListener(e -> showModify());
        super.getConsultButton().addActionListener(e -> showGet());
        super.getListButton().addActionListener(e -> showList());
        super.getDeleteButton().addActionListener(e -> showDelete());

    }

    @Override
    public void showCreate() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Crear Producto", true);
        dialog.setBounds(100, 100, 320, 250); // Ajusta el tamaño del diálogo
        dialog.setLayout(null); // Usar diseño nulo para posicionar manualmente los componentes
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JLabel detalleLabel = new JLabel("Detalle:");
        detalleLabel.setBounds(10, 11, 80, 14);
        dialog.add(detalleLabel);

        JTextField campoDetalle = new JTextField();
        campoDetalle.setBounds(100, 8, 200, 20);
        dialog.add(campoDetalle);

        JLabel cantidadLabel = new JLabel("Cantidad:");
        cantidadLabel.setBounds(10, 36, 80, 14);
        dialog.add(cantidadLabel);

        JTextField campoCantidad = new JTextField();
        campoCantidad.setBounds(100, 33, 200, 20);
        dialog.add(campoCantidad);

        JLabel precioLabel = new JLabel("Precio Unitario:");
        precioLabel.setBounds(10, 61, 100, 14);
        dialog.add(precioLabel);

        JTextField campoPrecio = new JTextField();
        campoPrecio.setBounds(100, 58, 200, 20);
        dialog.add(campoPrecio);

        JLabel unidadLabel = new JLabel("Unidad:");
        unidadLabel.setBounds(10, 86, 100, 14);
        dialog.add(unidadLabel);

        JComboBox<Unidad> campoUnidad = new JComboBox<>(Unidad.values());
        campoUnidad.setBounds(100, 83, 200, 20);
        dialog.add(campoUnidad);

        JLabel activoLabel = new JLabel("Activo:");
        activoLabel.setBounds(10, 111, 80, 14);
        dialog.add(activoLabel);

        JCheckBox checkBoxActivo = new JCheckBox();
        checkBoxActivo.setBounds(100, 107, 20, 20);
        dialog.add(checkBoxActivo);

        // Crear un panel para los botones y configurar su posición
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null); // Usar diseño nulo para posicionar los botones
        buttonPanel.setBounds(10, 140, 280, 50); // Ajustar tamaño y posición del panel

        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setBounds(10, 10, 100, 30);
        buttonPanel.add(acceptButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBounds(170, 10, 100, 30);
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel);

        acceptButton.addActionListener(e -> {

            Unidad unidadSeleccionada = (Unidad) campoUnidad.getSelectedItem();
            assert unidadSeleccionada != null;
            JOptionPane.showMessageDialog(null, productoController.crear(
                    campoDetalle.getText(),
                    campoCantidad.getText(),
                    campoPrecio.getText(),
                    unidadSeleccionada.name(),
                    checkBoxActivo.isSelected()));
        });

        cancelButton.addActionListener(e -> dialog.dispose()); // Cierra el diálogo

        dialog.setVisible(true); // Muestra el diálogo
    }

    @Override
    public void showGet() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Consultar Producto", true);
        dialog.setBounds(100, 100, 310, 255); // Ajusta el tamaño del diálogo
        dialog.setLayout(null); // Usar diseño nulo para posicionar manualmente los componentes
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JLabel idLabel = new JLabel("ID del Producto:");
        idLabel.setBounds(10, 11, 120, 14);
        dialog.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(100, 8, 170, 20); // Ajustado el tamaño
        dialog.add(idField);

        JLabel detalleLabel = new JLabel("Detalle:");
        detalleLabel.setBounds(10, 36, 80, 14);
        dialog.add(detalleLabel);

        JTextField detalleField = new JTextField();
        detalleField.setBounds(100, 33, 170, 20);
        detalleField.setEditable(false); // Campo de solo lectura
        dialog.add(detalleField);

        JLabel cantidadLabel = new JLabel("Cantidad:");
        cantidadLabel.setBounds(10, 61, 80, 14);
        dialog.add(cantidadLabel);

        JTextField cantidadField = new JTextField();
        cantidadField.setBounds(100, 58, 170, 20);
        cantidadField.setEditable(false); // Campo de solo lectura
        dialog.add(cantidadField);

        JLabel precioLabel = new JLabel("Precio Unitario:");
        precioLabel.setBounds(10, 86, 120, 14);
        dialog.add(precioLabel);

        JTextField precioField = new JTextField();
        precioField.setBounds(100, 83, 170, 20);
        precioField.setEditable(false); // Campo de solo lectura
        dialog.add(precioField);

        JLabel unidadLabel = new JLabel("Unidad:");
        unidadLabel.setBounds(10, 111, 120, 14);
        dialog.add(unidadLabel);

        JTextField unidadField = new JTextField();
        unidadField.setBounds(100, 108, 170, 20);
        unidadField.setEditable(false); // Campo de solo lectura
        dialog.add(unidadField);

        JLabel activoLabel = new JLabel("Activo:");
        activoLabel.setBounds(10, 136, 80, 14);
        dialog.add(activoLabel);

        JCheckBox checkBoxActivo = new JCheckBox();
        checkBoxActivo.setBounds(96, 132, 20, 20);
        checkBoxActivo.setEnabled(false);
        dialog.add(checkBoxActivo);

        // Crear un panel para los botones y configurar su posición
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null); // Usar diseño nulo para posicionar los botones
        buttonPanel.setBounds(10, 170, 266, 34); // Ajustar tamaño y posición del panel

        JButton buscarButton = new JButton("Buscar");
        buscarButton.setBounds(10, 0, 100, 30);
        buttonPanel.add(buscarButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBounds(163, 0, 100, 30);
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel);

        buscarButton.addActionListener(e -> {
            String[] datos = productoController.consultar(idField.getText());

            if (datos.length == 1) {
                JOptionPane.showMessageDialog(null, datos[0]);
            } else {
                detalleField.setText(datos[0]);
                cantidadField.setText(datos[1]);
                precioField.setText(datos[2]);
                unidadField.setText(datos[3]);
                checkBoxActivo.setSelected(Boolean.parseBoolean(datos[4]));
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose()); // Cierra el diálogo

        dialog.setVisible(true); // Muestra el diálogo
    }

    @Override
    public void showModify() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modificar Producto", true);
        dialog.setBounds(100, 100, 350, 260); // Ajusta el tamaño del diálogo
        dialog.setLayout(null); // Usar diseño nulo para posicionar manualmente los componentes
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JLabel idLabel = new JLabel("ID del Producto:");
        idLabel.setBounds(10, 11, 120, 14);
        dialog.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(100, 8, 200, 20);
        dialog.add(idField);

        JLabel detalleLabel = new JLabel("Detalle:");
        detalleLabel.setBounds(10, 36, 80, 14);
        dialog.add(detalleLabel);

        JTextField detalleField = new JTextField();
        detalleField.setBounds(100, 33, 200, 20);
        dialog.add(detalleField);

        JLabel cantidadLabel = new JLabel("Cantidad:");
        cantidadLabel.setBounds(10, 61, 80, 14);
        dialog.add(cantidadLabel);

        JTextField cantidadField = new JTextField();
        cantidadField.setBounds(100, 58, 200, 20);
        dialog.add(cantidadField);

        JLabel precioLabel = new JLabel("Precio Unitario:");
        precioLabel.setBounds(10, 86, 100, 14);
        dialog.add(precioLabel);

        JTextField precioField = new JTextField();
        precioField.setBounds(100, 83, 200, 20);
        dialog.add(precioField);

        JLabel unidadLabel = new JLabel("Unidad:");
        unidadLabel.setBounds(10, 111, 120, 14);
        dialog.add(unidadLabel);

        // Crear JComboBox con los valores del enum
        JComboBox<Unidad> unidadField = new JComboBox<>(Unidad.values());
        unidadField.setBounds(100, 107, 160, 20);
        dialog.add(unidadField);

        JLabel activoLabel = new JLabel("Activo:");
        activoLabel.setBounds(10, 136, 80, 14);
        dialog.add(activoLabel);

        JCheckBox checkBoxActivo = new JCheckBox();
        checkBoxActivo.setBounds(100, 130, 20, 20);
        dialog.add(checkBoxActivo);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null); // Usar diseño nulo para posicionar los botones
        buttonPanel.setBounds(0, 157, 337, 50); // Ajustar tamaño y posición del panel

        JButton buscarButton = new JButton("Buscar");
        buscarButton.setBounds(10, 10, 100, 30);
        buttonPanel.add(buscarButton);

        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setBounds(120, 10, 100, 30);
        buttonPanel.add(acceptButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBounds(230, 10, 100, 30);
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel);

        buscarButton.addActionListener(e -> {
            String[] datos = productoController.consultar(idField.getText());
            if (datos.length == 1) {
                JOptionPane.showMessageDialog(null, datos[0]);
            } else {
                detalleField.setText(datos[0]);
                cantidadField.setText(datos[1]);
                precioField.setText(datos[2]);
                unidadField.setSelectedItem(Unidad.valueOf(datos[3])); // Establece la opción seleccionada en el JComboBox
                checkBoxActivo.setSelected(Boolean.parseBoolean(datos[4])); // Asume que datos[4] es un valor booleano como String
            }
            idField.setEditable(false);
        });

        acceptButton.addActionListener(e -> {
            Unidad unidadSeleccionada = (Unidad) unidadField.getSelectedItem();
            JOptionPane.showMessageDialog(null, productoController.modificar(
                    idField.getText(),
                    detalleField.getText(),
                    cantidadField.getText(),
                    precioField.getText(),
                    unidadSeleccionada.name() // Convertir a String si el controlador espera un String
                    ));
            idField.setEditable(true);
        });

        cancelButton.addActionListener(e -> dialog.dispose()); // Cierra el diálogo

        dialog.setVisible(true); // Muestra el diálogo
    }

    @Override
    public void showList() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Lista de Clientes", true);
        dialog.setBounds(100, 100, 800, 600);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        DefaultTableModel tableModel = productoController.listar();
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
        scrollPane.setBounds(10, 10, 760, 500);

        // Crear un botón de cerrar
        JButton closeButton = new JButton("Cerrar");
        closeButton.setBounds(350, 520, 100, 30);
        closeButton.addActionListener(e -> dialog.dispose());

        dialog.add(scrollPane);
        dialog.add(closeButton);

        dialog.setVisible(true);
    }

    @Override
    public void showDelete() {
        // Crear un JDialog modal para la eliminación de un producto
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Baja Producto", true);
        dialog.setBounds(100, 100, 267, 144);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JLabel idLabel = new JLabel("ID Producto:");
        idLabel.setBounds(10, 20, 150, 20);
        dialog.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(80, 20, 151, 20);
        dialog.add(idField);

        JButton deleteButton = new JButton("Eliminar");
        deleteButton.setBounds(10, 51, 100, 30);
        dialog.add(deleteButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBounds(131, 51, 100, 30);
        dialog.add(cancelButton);

        deleteButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, productoController.eliminar(idField.getText()));
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

}