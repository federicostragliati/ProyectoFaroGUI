package Views;

import Controller.ClienteController;
import Views.Interfaces.PanelInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

public class ClientePanel extends JPanel implements PanelInterface {

    private JPanel originalPanel;
    private final ClienteController clienteController = new ClienteController();

    public ClientePanel () {

        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));

        JButton createButton = new JButton("Crear Cliente");
        JButton consultButton = new JButton("Consultar Cliente");
        JButton modifyButton = new JButton("Modificar Cliente");
        JButton listButton = new JButton("Consultar Listado");
        JButton deleteButton = new JButton("Baja Cliente");

        buttonPanel.add(createButton);
        buttonPanel.add(consultButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(listButton);
        buttonPanel.add(deleteButton);

        // Agregar el panel de botones al panel principal
        add(buttonPanel, BorderLayout.WEST);

        // Crear el área para mostrar la información relacionada // ¿Que es esto? Comentarlo para probar
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoArea);
        add(scrollPane, BorderLayout.CENTER);
        // Por el momento hasta que cree las funciones
        createButton.addActionListener(e -> showCreate());
        modifyButton.addActionListener(e -> showModify());
        consultButton.addActionListener(e -> showGet());
        listButton.addActionListener(e -> showList(this));
        deleteButton.addActionListener(e -> showDelete());
    }

    @Override
    public void showCreate() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Crear Cliente", true);
        dialog.setSize(500, 500);
        dialog.setLayout(new GridLayout(6, 2));
        dialog.setLocationRelativeTo(this);

        dialog.add(new JLabel("CUIT:"));
        JTextField campoCuit = new JTextField();
        dialog.add(campoCuit);

        dialog.add(new JLabel("Nombre:"));
        JTextField campoNombre = new JTextField();
        dialog.add(campoNombre);

        dialog.add(new JLabel("Email:"));
        JTextField campoEmail = new JTextField();
        dialog.add(campoEmail);

        dialog.add(new JLabel("Teléfono:"));
        JTextField campoTelefono = new JTextField();
        dialog.add(campoTelefono);

        dialog.add(new JLabel("Activo:"));
        JCheckBox checkBoxActivo = new JCheckBox();
        dialog.add(checkBoxActivo);

        JPanel buttonPanel = new JPanel();
        JButton acceptButton = new JButton("Aceptar");
        JButton cancelButton = new JButton("Cancelar");
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel);

        acceptButton.addActionListener( e -> {
            JOptionPane.showMessageDialog(null, clienteController.crear(campoCuit.getText(), campoNombre.getText(), campoEmail.getText(),campoTelefono.getText(),checkBoxActivo.isSelected()));
        });

        cancelButton.addActionListener(e -> dialog.dispose()); // Cierra el diálogo

        dialog.setVisible(true); // Muestra el diálogo

    }

    @Override
    public void showGet() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Consultar Producto", true);
        dialog.setSize(400, 400); // Tamaño ajustado
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes

        // Fila 0
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        dialog.add(new JLabel("ID del Cliente:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField idField = new JTextField(20); // Ajustado el ancho
        dialog.add(idField, gbc);

        // Fila 1
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        dialog.add(new JLabel("CUIT:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea cuitField = new JTextArea(2, 20); // Ajustado el tamaño
        cuitField.setEditable(false);
        JScrollPane detalleScroll = new JScrollPane(cuitField); // Añadido JScrollPane para el texto
        dialog.add(detalleScroll, gbc);

        // Fila 2
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        dialog.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea nombreField = new JTextArea(2, 20); // Ajustado el tamaño
        nombreField.setEditable(false);
        JScrollPane cantidadScroll = new JScrollPane(nombreField); // Añadido JScrollPane para el texto
        dialog.add(cantidadScroll, gbc);

        // Fila 3
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        dialog.add(new JLabel("E-Mail:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea eMailField = new JTextArea(2, 20); // Ajustado el tamaño
        eMailField.setEditable(false);
        JScrollPane precioScroll = new JScrollPane(eMailField); // Añadido JScrollPane para el texto
        dialog.add(precioScroll, gbc);

        // Fila 4
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        dialog.add(new JLabel("Teléfono:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea telefonoField = new JTextArea(2, 20); // Ajustado el tamaño
        telefonoField.setEditable(false);
        JScrollPane unidadScroll = new JScrollPane(telefonoField); // Añadido JScrollPane para el texto
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

        buscarButton.addActionListener( e -> {
            String datos [] = clienteController.consultar(idField.getText());
            if ( datos.length == 1 ) {
                JOptionPane.showMessageDialog(null,datos[0]);
            } else {
                cuitField.setText(datos[0]);
                nombreField.setText(datos[1]);
                eMailField.setText(datos[2]);
                telefonoField.setText(datos[3]);
                activoField.setText(datos[4]);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose()); // Cierra el diálogo

        dialog.setLocationRelativeTo(this); // Centra el diálogo respecto al JFrame padre
        dialog.setVisible(true);
    }

    @Override
    public void showModify() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modificar Producto", true);
        dialog.setSize(500, 500);
        dialog.setLayout(new GridLayout(7, 2));
        dialog.setLocationRelativeTo(this);

        dialog.add(new JLabel("ID del Cliente a Modificar:"));
        JTextField idField = new JTextField();
        dialog.add(idField);

        dialog.add(new JLabel("CUIT:"));
        JTextField cuitField = new JTextField();
        dialog.add(cuitField);

        dialog.add(new JLabel("Nombre:"));
        JTextField nombreField = new JTextField();
        dialog.add(nombreField);

        dialog.add(new JLabel("Email:"));
        JTextField eMailField = new JTextField();
        dialog.add(eMailField);

        dialog.add(new JLabel("Telefono:"));
        JTextField telefonoField = new JTextField();
        dialog.add(telefonoField);

        JPanel buttonPanel = new JPanel();
        JButton buscarButton = new JButton("Buscar");
        JButton acceptButton = new JButton("Aceptar");
        JButton cancelButton = new JButton("Cancelar");
        buttonPanel.add(buscarButton);
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel);

        String id = idField.getText();

        buscarButton.addActionListener( e -> {
            String [] datos = clienteController.consultar(idField.getText());
            if (datos.length == 1) {
                JOptionPane.showMessageDialog(null, datos [0]);
            } else {
                cuitField.setText(datos [0]);
                nombreField.setText(datos[1]);
                eMailField.setText(datos[2]);
                telefonoField.setText(datos[3]);
            }
        });

        acceptButton.addActionListener( e -> {

            String cuit = cuitField.getText();
            String nombre = nombreField.getText();
            String email = eMailField.getText();
            String telefono = telefonoField.getText();

            JOptionPane.showMessageDialog(null, clienteController.modificar(idField.getText(), cuit, nombre, email, telefono));

        });

        cancelButton.addActionListener(e -> dialog.dispose()); // Cierra el diálogo

        dialog.setVisible(true); // Muestra el diálogo
    }

    @Override
    public void showList(JPanel parentPanel) {

        if (originalPanel == null) {
            // Guardar una copia del panel original
            originalPanel = new JPanel(new BorderLayout());
            for (Component comp : parentPanel.getComponents()) {
                originalPanel.add(comp);
            }
        }

        DefaultTableModel tableModel = clienteController.listar();
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

    @Override
    public void showDelete() {
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

            JOptionPane.showMessageDialog(null,clienteController.eliminar(idField.getText()));

        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
}
