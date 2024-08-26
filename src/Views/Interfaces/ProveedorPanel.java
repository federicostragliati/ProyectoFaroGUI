package Views.Interfaces;

import Controller.ProveedorController;
import Views.GeneralPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

public class ProveedorPanel extends GeneralPanel implements PanelInterface {

    private final ProveedorController proveedorController = new ProveedorController();

    public ProveedorPanel(String boton1, String boton2, String boton3, String boton4, String boton5) {
        super(boton1, boton2, boton3, boton4, boton5);

        super.getCreateButton().addActionListener(e -> showCreate());
        super.getModifyButton().addActionListener(e -> showModify());
        super.getConsultButton().addActionListener(e -> showGet());
        super.getListButton().addActionListener(e -> showList());
        super.getDeleteButton().addActionListener(e -> showDelete());
    }

    // Agregar Action Listeners en Create, Get y Modify //

    @Override
    public void showCreate() {

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Crear Proveedor", true);
        dialog.setBounds(100, 100, 320, 255); // Ajusta el tamaño del diálogo
        dialog.setLayout(null); // Usar diseño nulo para posicionar manualmente los componentes
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JLabel cuitLabel = new JLabel("CUIT:");
        cuitLabel.setBounds(10, 11, 80, 14);
        dialog.add(cuitLabel);

        JTextField campoCuit = new JTextField();
        campoCuit.setBounds(100, 8, 200, 20);
        dialog.add(campoCuit);

        JLabel razonLabel = new JLabel("Razón Social:");
        razonLabel.setBounds(10, 36, 80, 14);
        dialog.add(razonLabel);

        JTextField campoRazon = new JTextField();
        campoRazon.setBounds(100, 33, 200, 20);
        dialog.add(campoRazon);

        JLabel emailLabel = new JLabel("E-Mail:");
        emailLabel.setBounds(10, 61, 100, 14);
        dialog.add(emailLabel);

        JTextField campoEmail = new JTextField();
        campoEmail.setBounds(100, 58, 200, 20);
        dialog.add(campoEmail);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoLabel.setBounds(10, 86, 100, 14);
        dialog.add(telefonoLabel);

        JTextField campoTelefono = new JTextField();
        campoTelefono.setBounds(100, 83, 200, 20);
        dialog.add(campoTelefono);

        JLabel direccionLabel = new JLabel("Dirección:");
        direccionLabel.setBounds(10, 111, 80, 14);
        dialog.add(direccionLabel);

        JTextField campoDireccion = new JTextField();
        campoDireccion.setBounds(100, 107, 200, 20);
        dialog.add(campoDireccion);

        JLabel activoLabel = new JLabel("Activo:");
        activoLabel.setBounds(10,136,100,14);
        dialog.add(activoLabel);

        JCheckBox checkBoxActivo = new JCheckBox();
        checkBoxActivo.setBounds(95, 132, 20, 20);
        dialog.add(checkBoxActivo);

        // Crear un panel para los botones y configurar su posición
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null); // Usar diseño nulo para posicionar los botones
        buttonPanel.setBounds(10, 150, 280, 50); // Ajustar tamaño y posición del panel

        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setBounds(10, 10, 100, 30);
        buttonPanel.add(acceptButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBounds(170, 10, 100, 30);
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel);

        acceptButton.addActionListener( e -> {
            JOptionPane.showMessageDialog(null, proveedorController.crear(
                    campoCuit.getText(),
                    campoRazon.getText(),
                    campoEmail.getText(),
                    campoTelefono.getText(),
                    campoDireccion.getText(),
                    checkBoxActivo.isSelected()));
        });

        cancelButton.addActionListener(e -> dialog.dispose()); // Cierra el diálogo

        dialog.setVisible(true);


    }

    // Modificar tamaño ShowGet

    @Override
    public void showGet() {

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Consultar Proveedor", true);
        dialog.setBounds(100, 100, 320, 255); // Ajusta el tamaño del diálogo
        dialog.setLayout(null); // Usar diseño nulo para posicionar manualmente los componentes
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JLabel idLabel = new JLabel("ID Proveedor:");
        idLabel.setBounds(10, 11, 120, 14);
        dialog.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(100, 8, 200, 20);
        dialog.add(idField);

        JLabel cuitLabel = new JLabel("CUIT:");
        cuitLabel.setBounds(10, 36, 80, 14);
        dialog.add(cuitLabel);

        JTextField cuitField = new JTextField();
        cuitField.setBounds(100, 33, 200, 20);
        cuitField.setEditable(false); // Campo de solo lectura
        dialog.add(cuitField);

        JLabel razonLabel = new JLabel("Razón Social:");
        razonLabel.setBounds(10, 61, 80, 14);
        dialog.add(razonLabel);

        JTextField razonField = new JTextField();
        razonField.setBounds(100, 58, 200, 20);
        razonField.setEditable(false); // Campo de solo lectura
        dialog.add(razonField);

        JLabel emailLabel = new JLabel("E-Mail:");
        emailLabel.setBounds(10, 86, 120, 14);
        dialog.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(100, 83, 200, 20);
        emailField.setEditable(false); // Campo de solo lectura
        dialog.add(emailField);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoLabel.setBounds(10, 111, 120, 14);
        dialog.add(telefonoLabel);

        JTextField telefonoField = new JTextField();
        telefonoField.setBounds(100, 108, 200, 20);
        telefonoField.setEditable(false); // Campo de solo lectura
        dialog.add(telefonoField);

        JLabel direccionLabel = new JLabel("Dirección:");
        direccionLabel.setBounds(10, 136, 80, 14);
        dialog.add(direccionLabel);

        JTextField direccionField = new JTextField();
        direccionField.setBounds(100, 133, 200, 20);
        direccionField.setEditable(false);
        dialog.add(direccionField);

        JLabel activoLabel = new JLabel("Activo:");
        activoLabel.setBounds(10,161,100,14);
        dialog.add(activoLabel);

        JCheckBox checkBoxActivo = new JCheckBox();
        checkBoxActivo.setBounds(95, 158, 20, 20);
        checkBoxActivo.setEnabled(false);
        dialog.add(checkBoxActivo);
        // Crear un panel para los botones y configurar su posición
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null); // Usar diseño nulo para posicionar los botones
        buttonPanel.setBounds(10, 170, 280, 50); // Ajustar tamaño y posición del panel

        JButton buscarButton = new JButton("Buscar");
        buscarButton.setBounds(10, 10, 100, 30);
        buttonPanel.add(buscarButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBounds(170, 10, 100, 30);
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel);

        buscarButton.addActionListener(e -> {
            String[] datos = proveedorController.consultar(idField.getText());

            if (datos.length == 1) {
                JOptionPane.showMessageDialog(null, datos[0]);
            } else {
                cuitField.setText(datos[0]);
                razonField.setText(datos[1]);
                emailField.setText(datos[2]);
                telefonoField.setText(datos[3]);
                direccionField.setText(datos[4]);
                checkBoxActivo.setSelected(Boolean.parseBoolean(datos[5]));
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose()); // Cierra el diálogo

        dialog.setVisible(true);
    }

    @Override
    public void showModify() {

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modificar Proveedor", true);
        dialog.setBounds(100, 100, 360, 255); // Ajusta el tamaño del diálogo
        dialog.setLayout(null); // Usar diseño nulo para posicionar manualmente los componentes
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JLabel idLabel = new JLabel("ID Proveedor:");
        idLabel.setBounds(10, 11, 120, 14);
        dialog.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(100, 8, 200, 20);
        dialog.add(idField);

        JLabel cuitLabel = new JLabel("CUIT:");
        cuitLabel.setBounds(10, 36, 80, 14);
        dialog.add(cuitLabel);

        JTextField cuitField = new JTextField();
        cuitField.setBounds(100, 33, 200, 20);
        dialog.add(cuitField);

        JLabel razonLabel = new JLabel("Razón Social:");
        razonLabel.setBounds(10, 61, 80, 14);
        dialog.add(razonLabel);

        JTextField razonField = new JTextField();
        razonField.setBounds(100, 58, 200, 20);
        dialog.add(razonField);

        JLabel emailLabel = new JLabel("E-Mail:");
        emailLabel.setBounds(10, 86, 120, 14);
        dialog.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(100, 83, 200, 20);
        dialog.add(emailField);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoLabel.setBounds(10, 111, 120, 14);
        dialog.add(telefonoLabel);

        JTextField telefonoField = new JTextField();
        telefonoField.setBounds(100, 108, 200, 20);
        dialog.add(telefonoField);

        JLabel direccionLabel = new JLabel("Dirección:");
        direccionLabel.setBounds(10, 136, 80, 14);
        dialog.add(direccionLabel);

        JTextField direccionField = new JTextField();
        direccionField.setBounds(100, 133, 200, 20);
        dialog.add(direccionField);

        JLabel activoLabel = new JLabel("Activo:");
        activoLabel.setBounds(10,161,100,14);
        dialog.add(activoLabel);

        JCheckBox checkBoxActivo = new JCheckBox();
        checkBoxActivo.setBounds(95, 158, 20, 20);
        checkBoxActivo.setEnabled(false);
        dialog.add(checkBoxActivo);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null); // Usar diseño nulo para posicionar los botones
        buttonPanel.setBounds(0, 170, 360, 50); // Ajustar tamaño y posición del panel

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
            String[] datos = proveedorController.consultar(idField.getText());

            if (datos.length == 1) {
                JOptionPane.showMessageDialog(null, datos[0]);
            } else {
                cuitField.setText(datos[0]);
                razonField.setText(datos[1]);
                emailField.setText(datos[2]);
                telefonoField.setText(datos[3]);
                direccionField.setText(datos[4]);
                checkBoxActivo.setSelected(Boolean.parseBoolean(datos[5]));
            }
            idField.setEditable(false);
        });

        acceptButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(null, proveedorController.modificar(
                    idField.getText(),
                    cuitField.getText(),
                    razonField.getText(),
                    emailField.getText(),
                    telefonoField.getText(),
                    direccionField.getText())
            );
            idField.setEditable(true);
        });

        cancelButton.addActionListener(e -> dialog.dispose()); // Cierra el diálogo

        dialog.setVisible(true); // Muestra el diálogo

    }

    @Override
    public void showList() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Lista de Proveedores", true);
        dialog.setBounds(100, 100, 800, 600);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(true);
        dialog.setLayout(new BorderLayout()); // Cambiar a BorderLayout para facilitar la redimensión

        DefaultTableModel tableModel = proveedorController.listar();
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
        dialog.add(scrollPane, BorderLayout.CENTER); // Añadir el scrollPane en la región central

        // Crear un botón de cerrar
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH); // Añadir el panel del botón en la región sur

        dialog.setVisible(true);
    }


    @Override
    public void showDelete() {

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Baja Proveedor", true);
        dialog.setBounds(100, 100, 270, 144);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JLabel idLabel = new JLabel("ID Proveedor:");
        idLabel.setBounds(10, 20, 150, 20);
        dialog.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(100, 20, 151, 20);
        dialog.add(idField);

        JButton deleteButton = new JButton("Eliminar");
        deleteButton.setBounds(10, 51, 100, 30);
        dialog.add(deleteButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBounds(140, 51, 100, 30);
        dialog.add(cancelButton);

        deleteButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, proveedorController.eliminar(idField.getText()));
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);

    }
}
