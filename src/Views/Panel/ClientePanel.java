package Views.Panel;

import Controller.ClienteController;
import Views.Interfaces.PanelInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

public class ClientePanel extends GeneralPanel implements PanelInterface {

    private JPanel originalPanel;
    private final ClienteController clienteController = new ClienteController();

    public ClientePanel(String boton1, String boton2, String boton3, String boton4, String boton5) {
        super(boton1, boton2, boton3,  boton4,  boton5);


        /* // Crear el área para mostrar la información relacionada // ¿Que es esto? Comentarlo para probar
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoArea);
        add(scrollPane, BorderLayout.CENTER); */

        super.getCreateButton().addActionListener(e -> showCreate());
        super.getModifyButton().addActionListener(e -> showModify());
        super.consultButton.addActionListener(e -> showGet());
        super.getListButton().addActionListener(e -> showList());
        super.deleteButton.addActionListener(e -> showDelete());
    }

    @Override
    public void showCreate()  {

        JDialog contentPanel = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Crear Cliente", true);
        contentPanel.setBounds(100, 100, 320, 210);
        contentPanel.setLayout(null);
        contentPanel.setLocationRelativeTo(null);
        contentPanel.setResizable(false);

        JTextField campoCuit;
        JTextField campoNombre;
        JTextField campoEmail;
        JTextField campoTelefono;


        //getContentPane().setLayout(new BorderLayout());
        //contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        //getContentPane().add(contentPanel, BorderLayout.CENTER);

        JLabel cuit = new JLabel("CUIT:");
        cuit.setBounds(10, 11, 67, 14);
        contentPanel.add(cuit);

        JLabel nombre = new JLabel("Nombre:");
        nombre.setBounds(10, 36, 67, 14);
        contentPanel.add(nombre);

        JLabel email = new JLabel("E-Mail:");
        email.setBounds(10, 61, 67, 14);
        contentPanel.add(email);

        JLabel telefono = new JLabel("Teléfono:");
        telefono.setBounds(10, 86, 67, 14);
        contentPanel.add(telefono);

        JLabel activo = new JLabel("Activo: ");
        activo.setBounds(10, 111, 67, 19);
        contentPanel.add(activo);

        JCheckBox checkBoxActivo = new JCheckBox("");
        checkBoxActivo.setBounds(73, 107, 97, 23);
        contentPanel.add(checkBoxActivo);

        campoCuit = new JTextField();
        campoCuit.setBounds(76, 8, 217, 20);
        contentPanel.add(campoCuit);
        campoCuit.setColumns(10);

        campoNombre = new JTextField();
        campoNombre.setColumns(10);
        campoNombre.setBounds(76, 33, 217, 20);
        contentPanel.add(campoNombre);

        campoEmail = new JTextField();
        campoEmail.setColumns(10);
        campoEmail.setBounds(76, 58, 217, 20);
        contentPanel.add(campoEmail);

        campoTelefono = new JTextField();
        campoTelefono.setColumns(10);
        campoTelefono.setBounds(76, 83, 217, 20);
        contentPanel.add(campoTelefono);

        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setBounds(10, 136, 89, 23);
        contentPanel.add(acceptButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBounds(204, 136, 89, 23);
        contentPanel.add(cancelButton);

        acceptButton.addActionListener( e -> {
            JOptionPane.showMessageDialog(null, clienteController.crear(campoCuit.getText(), campoNombre.getText(), campoEmail.getText(),campoTelefono.getText()));
        });

        cancelButton.addActionListener(e -> contentPanel.dispose()); // Cierra el diálogo

        contentPanel.setVisible(true); // Muestra el diálogo

    }

    @Override
    public void showGet() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Consultar Cliente", true);
        dialog.setBounds(100, 100, 400, 300); // Tamaño y posición ajustada
        dialog.setLayout(null);
        dialog.setResizable(false); // Hacer que el diálogo no sea redimensionable

        JLabel idLabel = new JLabel("ID del Cliente:");
        idLabel.setBounds(10, 10, 100, 20);
        dialog.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(120, 10, 250, 20);
        dialog.add(idField);

        JLabel cuitLabel = new JLabel("CUIT:");
        cuitLabel.setBounds(10, 40, 100, 20);
        dialog.add(cuitLabel);

        JTextField cuitField = new JTextField();
        cuitField.setBounds(120, 40, 250, 20);
        cuitField.setEditable(false);
        dialog.add(cuitField);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setBounds(10, 70, 100, 20);
        dialog.add(nombreLabel);

        JTextField nombreField = new JTextField();
        nombreField.setBounds(120, 70, 250, 20);
        nombreField.setEditable(false);
        dialog.add(nombreField);

        JLabel eMailLabel = new JLabel("E-Mail:");
        eMailLabel.setBounds(10, 100, 100, 20);
        dialog.add(eMailLabel);

        JTextField eMailField = new JTextField();
        eMailField.setBounds(120, 100, 250, 20);
        eMailField.setEditable(false);
        dialog.add(eMailField);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoLabel.setBounds(10, 130, 100, 20);
        dialog.add(telefonoLabel);

        JTextField telefonoField = new JTextField();
        telefonoField.setBounds(120, 130, 250, 20);
        telefonoField.setEditable(false);
        dialog.add(telefonoField);

        JLabel activoLabel = new JLabel("Activo:");
        activoLabel.setBounds(10, 160, 100, 20);
        dialog.add(activoLabel);

        JCheckBox checkBoxActivo = new JCheckBox();
        checkBoxActivo.setBounds(120, 160, 20, 20);
        checkBoxActivo.setEnabled(false);
        dialog.add(checkBoxActivo);

       /* JTextField activoField = new JTextField();
        activoField.setBounds(120, 160, 250, 20);
        activoField.setEditable(false);
        dialog.add(activoField);*/

        JButton buscarButton = new JButton("Buscar");
        buscarButton.setBounds(120, 200, 100, 25);
        dialog.add(buscarButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBounds(230, 200, 100, 25);
        dialog.add(cancelButton);

        buscarButton.addActionListener(e -> {
            String[] datos = clienteController.consultar(idField.getText());
            if (datos.length == 1) {
                JOptionPane.showMessageDialog(null, datos[0]);
            } else {
                cuitField.setText(datos[0]);
                nombreField.setText(datos[1]);
                eMailField.setText(datos[2]);
                telefonoField.setText(datos[3]);
                checkBoxActivo.setSelected(Boolean.parseBoolean(datos[4]));
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose()); // Cierra el diálogo

        dialog.setLocationRelativeTo(this); // Centra el diálogo respecto al JFrame padre
        dialog.setVisible(true);
    }

    @Override
    public void showModify() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modificar Cliente", true);
        dialog.setBounds(100, 100, 320, 210);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(10, 11, 150, 14);
        dialog.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(76, 8, 217, 20);
        dialog.add(idField);

        JLabel cuitLabel = new JLabel("CUIT:");
        cuitLabel.setBounds(10, 36, 67, 14);
        dialog.add(cuitLabel);

        JTextField cuitField = new JTextField();
        cuitField.setBounds(76, 33, 217, 20);
        dialog.add(cuitField);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setBounds(10, 61, 67, 14);
        dialog.add(nombreLabel);

        JTextField nombreField = new JTextField();
        nombreField.setBounds(76, 58, 217, 20);
        dialog.add(nombreField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 86, 67, 14);
        dialog.add(emailLabel);

        JTextField eMailField = new JTextField();
        eMailField.setBounds(76, 83, 217, 20);
        dialog.add(eMailField);

        JLabel telefonoLabel = new JLabel("Telefono:");
        telefonoLabel.setBounds(10, 111, 67, 14);
        dialog.add(telefonoLabel);

        JTextField telefonoField = new JTextField();
        telefonoField.setBounds(76, 108, 217, 20);
        dialog.add(telefonoField);

        JButton buscarButton = new JButton("Buscar");
        buscarButton.setBounds(10, 136, 89, 23);
        dialog.add(buscarButton);

        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setBounds(110, 136, 89, 23);
        dialog.add(acceptButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBounds(210, 136, 89, 23);
        dialog.add(cancelButton);

        buscarButton.addActionListener(e -> {


            String[] datos = clienteController.consultar(idField.getText());
            if (datos.length == 1) {
                JOptionPane.showMessageDialog(null, datos[0]);
            } else {
                cuitField.setText(datos[0]);
                nombreField.setText(datos[1]);
                eMailField.setText(datos[2]);
                telefonoField.setText(datos[3]);
            }
            idField.setEditable(false);
        });



        acceptButton.addActionListener(e -> {
            String cuit = cuitField.getText();
            String nombre = nombreField.getText();
            String email = eMailField.getText();
            String telefono = telefonoField.getText();

            JOptionPane.showMessageDialog(null, clienteController.modificar(idField.getText(), cuit, nombre, email, telefono));

            idField.setEditable(true);
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    @Override
    public void showList() {
        // Crear un nuevo JDialog para mostrar la lista
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Lista de Clientes", true);
        dialog.setBounds(100, 100, 800, 600);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

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
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Baja Cliente", true);
        dialog.setBounds(100, 100, 267, 144);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JLabel idLabel = new JLabel("ID Cliente:");
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
            JOptionPane.showMessageDialog(null, clienteController.eliminar(idField.getText()));
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

}
