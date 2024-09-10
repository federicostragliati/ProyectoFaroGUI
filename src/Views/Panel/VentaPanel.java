package Views.Panel;

import Controller.ClienteController;
import Controller.DetalleVentaController;
import Controller.MetodoPagoController;
import Controller.VentaController;
import Model.Auxiliares.ListadoProductos;
import Model.Validaciones.Validador;
import Views.Dialog.DetalleVentaDialog;
import Views.Interfaces.PanelInterface;
import dominio.Cliente;
import dominio.MetodoDePago;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VentaPanel extends GeneralPanel implements PanelInterface {

    private List<ListadoProductos> listadoProductos = new ArrayList<>();
    private JTextField clienteField;
    private JTextField descuentoField;
    private JTextField fechaField;
    private JTextField montoPrimField;
    private JTextField montoTotalField;
    private JTextField montoSecField;
    private DetalleVentaController detalleController = new DetalleVentaController();
    private MetodoPagoController metodoPagoController = new MetodoPagoController();
    private ClienteController clienteController = new ClienteController();
    private VentaController ventaController = new VentaController();
    private boolean isAdjusting = false;

    public VentaPanel(String boton1, String boton2, String boton3, String boton4, String boton5) {
        super(boton1, boton2, boton3, boton4, boton5);

        super.getCreateButton().addActionListener(e -> showCreate());
        super.getModifyButton().addActionListener(e -> showModify());
        super.getConsultButton().addActionListener(e -> showGet());
        super.getListButton().addActionListener(e -> showList());
        super.getDeleteButton().addActionListener(e -> showDelete());
    }

    @Override
    public void showCreate() {

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Nueva Venta", true);
        dialog.setBounds(100, 100, 410, 400);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JLabel lblNewLabel = new JLabel("Cliente:");
        lblNewLabel.setBounds(10, 11, 138, 14);
        dialog.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Fecha:");
        lblNewLabel_1.setBounds(10, 36, 138, 14);
        dialog.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Descuento:");
        lblNewLabel_2.setBounds(10, 61, 138, 14);
        dialog.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Metodo de Pago Primario:");
        lblNewLabel_3.setBounds(10, 86, 138, 14);
        dialog.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Monto de Pago Primario:");
        lblNewLabel_4.setBounds(10, 111, 138, 14);
        dialog.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("Metodo de Pago Secundario:");
        lblNewLabel_5.setBounds(10, 136, 138, 14);
        dialog.add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("Monto de Pago Secundario:");
        lblNewLabel_6.setBounds(10, 161, 138, 14);
        dialog.add(lblNewLabel_6);

        JButton btnDetalleVenta = new JButton("Productos");
        btnDetalleVenta.setBounds(10, 186, 138, 23);
        dialog.add(btnDetalleVenta);

        JLabel lblNewLabel_7 = new JLabel("Monto Total:");
        lblNewLabel_7.setBounds(10, 220, 138, 14);
        dialog.add(lblNewLabel_7);

        JCheckBox checkBoxPagada = new JCheckBox("Pagada");
        checkBoxPagada.setBounds(6, 241, 97, 23);
        dialog.add(checkBoxPagada);


        JCheckBox checkBoxEntregada = new JCheckBox("Entregada");
        checkBoxEntregada.setBounds(6, 293, 97, 23);
        dialog.add(checkBoxEntregada);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 333, 89, 23);
        dialog.add(btnAceptar);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(109, 333, 89, 23);
        dialog.add(btnVerificar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(289, 333, 89, 23);
        dialog.add(btnCancelar);

        clienteField = new JTextField();
        clienteField.setBounds(164, 7, 220, 22);
        JComboBox clienteBox = new JComboBox();
        clienteBox.setBounds(164, 29, 220, 22);
        //clienteBox.setBounds();
        clienteBox.setVisible(false);
        dialog.add(clienteBox);
        dialog.add(clienteField);

        clienteField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!isAdjusting) {
                    isAdjusting = true;
                    String busqueda = clienteField.getText().toLowerCase();
                    clienteBox.removeAllItems();

                    List<Cliente> coincidencias = clienteController.listado().stream()
                            .filter(cliente -> (cliente.getCuitCliente().toLowerCase().contains(busqueda)
                                    || cliente.getNombre().toLowerCase().contains(busqueda))
                                    && cliente.isActivo())
                            .collect(Collectors.toList());

                    if (!coincidencias.isEmpty()) {
                        for (Cliente cliente : coincidencias) {
                            clienteBox.addItem(cliente.getId() + " - " + cliente.getNombre() + " - " + cliente.getCuitCliente());
                        }
                        clienteBox.setVisible(true);
                        clienteBox.showPopup();
                    } else {
                        clienteBox.setVisible(false);
                    }

                    isAdjusting = false;
                }

                // Capturar la tecla Enter
                if (e.getKeyCode() == KeyEvent.VK_ENTER && clienteBox.isVisible()) {
                    if (clienteBox.getSelectedItem() != null) {
                        String clienteSeleccionado = (String) clienteBox.getSelectedItem();
                        clienteField.setText(clienteSeleccionado); //
                        clienteBox.setVisible(false); // Ocultar el comboBox después de seleccionar
                    }
                }
            }
        });

        clienteBox.addActionListener(e -> {
            if (!isAdjusting && clienteBox.getSelectedItem() != null) {
                String clienteSeleccionado = (String) clienteBox.getSelectedItem();
                clienteField.setText(clienteSeleccionado.split(" - ")[1]);
                clienteBox.setVisible(false);
            }
        });

        clienteField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                clienteBox.setVisible(false);
            }
        });

        fechaField = new JTextField();
        fechaField.setBounds(164, 33, 220, 20);
        dialog.add(fechaField);
        fechaField.setColumns(10);

        descuentoField = new JTextField();
        descuentoField.setBounds(164, 58, 220, 20);
        dialog.add(descuentoField);
        descuentoField.setColumns(10);

        JComboBox metodoPrimBox = new JComboBox();
        metodoPrimBox.setBounds(164, 82, 220, 22);
        for (MetodoDePago metodoDePago : metodoPagoController.listaMetodos()) {
            if (metodoDePago.isActivo()) {
                metodoPrimBox.addItem(metodoDePago.getId() + " - " + metodoDePago.getMetodo());
            }
        }
        dialog.add(metodoPrimBox);

        montoPrimField = new JTextField();
        montoPrimField.setBounds(164, 108, 220, 20);
        dialog.add(montoPrimField);
        montoPrimField.setColumns(10);

        JComboBox metodoSecBox = new JComboBox();
        metodoSecBox.setBounds(164, 132, 220, 22);
        for (MetodoDePago metodoDePago : metodoPagoController.listaMetodos()) {
            if (metodoDePago.isActivo()) {
                metodoSecBox.addItem(metodoDePago.getId() + " - " + metodoDePago.getMetodo());
            }
        }
        dialog.add(metodoSecBox);

        montoSecField = new JTextField();
        montoSecField.setBounds(164, 158, 220, 20);
        dialog.add(montoSecField);
        montoSecField.setColumns(10);

        montoTotalField = new JTextField();
        montoTotalField.setBounds(164, 217, 220, 20);
        dialog.add(montoTotalField);
        montoTotalField.setColumns(10);

        // Acción al presionar 'Productos'
        btnDetalleVenta.addActionListener(e -> {
            DetalleVentaDialog detalleDialog = new DetalleVentaDialog((Frame) SwingUtilities.getWindowAncestor(VentaPanel.this), listadoProductos);
            detalleDialog.setVisible(true);

            listadoProductos = detalleDialog.getListadoProductos();



            //Limpiar listadoProductos una vez que cree la venta o la cancele
        });

        btnAceptar.addActionListener( e -> {

            String mensaje = ventaController.crear(clienteField.getText(),
                    fechaField.getText(),
                    descuentoField.getText(),
                    metodoPrimBox.getSelectedItem().toString(),
                    montoPrimField.getText(),
                    metodoSecBox.getSelectedItem().toString(),
                    montoSecField.getText(),
                    montoTotalField.getText().substring(1),
                    checkBoxPagada.isSelected(),
                    checkBoxEntregada.isSelected());

            JOptionPane.showMessageDialog(null,mensaje);

            if (mensaje.equalsIgnoreCase("Venta Generada")) {
                detalleController.crear(listadoProductos,ventaController.ultimaVenta());
                listadoProductos.clear();
                dialog.dispose();
            }



        });

        // Acción al presionar 'Verificar'
        btnVerificar.addActionListener(e -> {
            ventaController.calcularMontoTotal(descuentoField,montoTotalField,listadoProductos);
        });

        // Acción al presionar 'Cancelar'
        btnCancelar.addActionListener(e -> {
            //Limpiar listadoProductos una vez que cree la venta o la cancele
            listadoProductos.clear();
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    @Override
    public void showModify() {

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modificar Venta", true);
        dialog.setBounds(100, 100, 410, 420);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Etiquetas
        JLabel lblIdVenta = new JLabel("ID Venta:");
        lblIdVenta.setBounds(10, 11, 150, 14);
        dialog.add(lblIdVenta);

        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(10, 36, 150, 14);
        dialog.add(lblCliente);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(10, 61, 150, 14);
        dialog.add(lblFecha);

        JLabel lblDescuento = new JLabel("Descuento:");
        lblDescuento.setBounds(10, 86, 150, 14);
        dialog.add(lblDescuento);

        JLabel lblMetodoPrim = new JLabel("Metodo de Pago Primario:");
        lblMetodoPrim.setBounds(10, 111, 150, 14);
        dialog.add(lblMetodoPrim);

        JLabel lblMontoPrim = new JLabel("Monto de Pago Primario:");
        lblMontoPrim.setBounds(10, 136, 150, 14);
        dialog.add(lblMontoPrim);

        JLabel lblMetodoSec = new JLabel("Metodo de Pago Secundario:");
        lblMetodoSec.setBounds(10, 161, 150, 14);
        dialog.add(lblMetodoSec);

        JLabel lblMontoSec = new JLabel("Monto de Pago Secundario:");
        lblMontoSec.setBounds(10, 186, 150, 14);
        dialog.add(lblMontoSec);

        JLabel lblMontoTotal = new JLabel("Monto Total:");
        lblMontoTotal.setBounds(10, 241, 150, 14);
        dialog.add(lblMontoTotal);

        // Campos de texto
        JTextField idField = new JTextField();
        idField.setBounds(170, 8, 220, 22);
        dialog.add(idField);

        JTextField clienteField = new JTextField();
        clienteField.setBounds(170, 33, 220, 22);
        dialog.add(clienteField);

        JTextField fechaField = new JTextField();
        fechaField.setBounds(170, 58, 220, 22);
        dialog.add(fechaField);

        JTextField descuentoField = new JTextField();
        descuentoField.setBounds(170, 83, 220, 22);
        dialog.add(descuentoField);

        // Comboboxes
        JComboBox<String> metodoPrimBox = new JComboBox<>();
        metodoPrimBox.setBounds(170, 108, 220, 22);
        for (MetodoDePago metodoDePago : metodoPagoController.listaMetodos()) {
            if (metodoDePago.isActivo()) {
                metodoPrimBox.addItem(metodoDePago.getId() + " - " + metodoDePago.getMetodo());
            }
        }
        dialog.add(metodoPrimBox);

        JTextField montoPrimField = new JTextField();
        montoPrimField.setBounds(170, 133, 220, 22);
        dialog.add(montoPrimField);

        JComboBox<String> metodoSecBox = new JComboBox<>();
        metodoSecBox.setBounds(170, 158, 220, 22);
        for (MetodoDePago metodoDePago : metodoPagoController.listaMetodos()) {
            if (metodoDePago.isActivo()) {
                metodoSecBox.addItem(metodoDePago.getId() + " - " + metodoDePago.getMetodo());
            }
        }
        dialog.add(metodoSecBox);

        JTextField montoSecField = new JTextField();
        montoSecField.setBounds(170, 183, 220, 22);
        dialog.add(montoSecField);

        JTextField montoTotalField = new JTextField();
        montoTotalField.setBounds(170, 241, 220, 22);
        dialog.add(montoTotalField);

        // Botones y Checkboxes
        JButton btnDetalleVenta = new JButton("Productos");
        btnDetalleVenta.setBounds(10, 210, 120, 23);
        dialog.add(btnDetalleVenta);

        JCheckBox checkBoxPagada = new JCheckBox("Pagada");
        checkBoxPagada.setBounds(10, 267, 97, 23);
        dialog.add(checkBoxPagada);

        JCheckBox checkBoxEntregada = new JCheckBox("Entregada");
        checkBoxEntregada.setBounds(10, 319, 97, 23);
        dialog.add(checkBoxEntregada);

// Calcular la posición de los botones para que queden centrados
        int buttonWidth = 89;
        int spacing = (410 - 4 * buttonWidth) / 5;
        int yPosition = 358;

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(spacing, yPosition, buttonWidth, 23);
        dialog.add(btnAceptar);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(spacing * 2 + buttonWidth, yPosition, buttonWidth, 23);
        dialog.add(btnVerificar);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(spacing * 3 + 2 * buttonWidth, yPosition, buttonWidth, 23);
        dialog.add(btnBuscar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(spacing * 4 + 3 * buttonWidth, yPosition, buttonWidth, 23);
        dialog.add(btnCancelar);

        // Acción al presionar 'Productos'
        btnDetalleVenta.addActionListener(e -> {
            listadoProductos = detalleController.getlistado(idField.getText());
            DetalleVentaDialog detalleDialog = new DetalleVentaDialog((Frame) SwingUtilities.getWindowAncestor(VentaPanel.this),"Consulta Detalle", listadoProductos);
            detalleDialog.setVisible(true);

        });

        btnBuscar.addActionListener(e -> {
            String datos [] = ventaController.consultar(idField.getText());
            idField.setEditable(false);
            clienteField.setText(datos[1]);
            clienteField.setEditable(false);
            fechaField.setText(Validador.convertirFecha(datos[2]));
            descuentoField.setText(datos[3]);
            metodoPrimBox.setSelectedIndex(Integer.parseInt(datos[4]) - 1);
            montoPrimField.setText(datos[5]);
            metodoSecBox.setSelectedIndex(Integer.parseInt(datos[6]) - 1);
            montoSecField.setText(datos[7]);
            montoTotalField.setText("$" + datos[8]);
            checkBoxPagada.setSelected(Boolean.parseBoolean(datos[9]));
            checkBoxEntregada.setSelected(Boolean.parseBoolean(datos[10]));
        });

        btnAceptar.addActionListener(e -> {

            String datos [] = ventaController.consultar(idField.getText());
            JOptionPane.showMessageDialog(null,ventaController.modificar(idField.getText(),
                    datos[0],
                    datos[1],
                    fechaField.getText(),
                    descuentoField.getText(),
                    metodoPrimBox.getSelectedItem().toString(),
                    montoPrimField.getText(),
                    metodoSecBox.getSelectedItem().toString(),
                    montoSecField.getText(),
                    montoTotalField.getText().substring(1),
                    checkBoxPagada.isSelected(),
                    checkBoxEntregada.isSelected()));
        });

        btnVerificar.addActionListener( e->{
            BigDecimal descuento  = new BigDecimal(descuentoField.getText());
            BigDecimal total = new BigDecimal(montoTotalField.getText().substring(1));
            BigDecimal valor1 = descuento.divide(BigDecimal.valueOf(100));
            BigDecimal valor2 = BigDecimal.ONE.subtract(valor1);
            BigDecimal montoConDescuento = total.multiply(valor2);
            montoTotalField.setText("$" + montoConDescuento);
        });

        btnCancelar.addActionListener( e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }


    @Override
    public void showGet() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Consultar Venta", true);
        dialog.setBounds(100, 100, 410, 420);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Etiquetas
        JLabel lblIdVenta = new JLabel("ID Venta:");
        lblIdVenta.setBounds(10, 11, 150, 14);
        dialog.add(lblIdVenta);

        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(10, 36, 150, 14);
        dialog.add(lblCliente);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(10, 61, 150, 14);
        dialog.add(lblFecha);

        JLabel lblDescuento = new JLabel("Descuento:");
        lblDescuento.setBounds(10, 86, 150, 14);
        dialog.add(lblDescuento);

        JLabel lblMetodoPrim = new JLabel("Metodo de Pago Primario:");
        lblMetodoPrim.setBounds(10, 111, 150, 14);
        dialog.add(lblMetodoPrim);

        JLabel lblMontoPrim = new JLabel("Monto de Pago Primario:");
        lblMontoPrim.setBounds(10, 136, 150, 14);
        dialog.add(lblMontoPrim);

        JLabel lblMetodoSec = new JLabel("Metodo de Pago Secundario:");
        lblMetodoSec.setBounds(10, 161, 150, 14);
        dialog.add(lblMetodoSec);

        JLabel lblMontoSec = new JLabel("Monto de Pago Secundario:");
        lblMontoSec.setBounds(10, 186, 150, 14);
        dialog.add(lblMontoSec);

        JLabel lblMontoTotal = new JLabel("Monto Total:");
        lblMontoTotal.setBounds(10, 241, 150, 14);
        dialog.add(lblMontoTotal);

        // Campos de texto
        JTextField idField = new JTextField();
        idField.setBounds(170, 8, 220, 22);
        dialog.add(idField);

        JTextField clienteField = new JTextField();
        clienteField.setBounds(170, 33, 220, 22);
        dialog.add(clienteField);

        JTextField fechaField = new JTextField();
        fechaField.setBounds(170, 58, 220, 22);
        dialog.add(fechaField);

        JTextField descuentoField = new JTextField();
        descuentoField.setBounds(170, 83, 220, 22);
        dialog.add(descuentoField);

        // Comboboxes
        JComboBox<String> metodoPrimBox = new JComboBox<>();
        metodoPrimBox.setBounds(170, 108, 220, 22);
        for (MetodoDePago metodoDePago : metodoPagoController.listaMetodos()) {
            if (metodoDePago.isActivo()) {
                metodoPrimBox.addItem(metodoDePago.getId() + " - " + metodoDePago.getMetodo());
            }
        }
        dialog.add(metodoPrimBox);

        JTextField montoPrimField = new JTextField();
        montoPrimField.setBounds(170, 133, 220, 22);
        dialog.add(montoPrimField);

        JComboBox<String> metodoSecBox = new JComboBox<>();
        metodoSecBox.setBounds(170, 158, 220, 22);
        for (MetodoDePago metodoDePago : metodoPagoController.listaMetodos()) {
            if (metodoDePago.isActivo()) {
                metodoSecBox.addItem(metodoDePago.getId() + " - " + metodoDePago.getMetodo());
            }
        }
        dialog.add(metodoSecBox);

        JTextField montoSecField = new JTextField();
        montoSecField.setBounds(170, 183, 220, 22);
        dialog.add(montoSecField);

        JTextField montoTotalField = new JTextField();
        montoTotalField.setBounds(170, 241, 220, 22);
        dialog.add(montoTotalField);

        // Botones y Checkboxes
        JButton btnDetalleVenta = new JButton("Productos");
        btnDetalleVenta.setBounds(10, 210, 120, 23);
        dialog.add(btnDetalleVenta);

        JCheckBox checkBoxPagada = new JCheckBox("Pagada");
        checkBoxPagada.setBounds(10, 267, 97, 23);
        dialog.add(checkBoxPagada);

        JCheckBox checkBoxEntregada = new JCheckBox("Entregada");
        checkBoxEntregada.setBounds(10, 319, 97, 23);
        dialog.add(checkBoxEntregada);

        JCheckBox checkBoxActivo = new JCheckBox("Activa");
        checkBoxActivo.setBounds(10, 345,97,23);
        dialog.add(checkBoxActivo);

        int buttonWidth = 89;
        int dialogWidth = dialog.getWidth();
        int buttonHeight = 23;
        int yPosition = 358;

        // Centrar los botones 'Buscar' y 'Cancelar'
        int totalButtonWidth = buttonWidth * 2 + 10; // 10 es el espacio entre los botones
        int xStartPosition = (dialogWidth - totalButtonWidth) / 2;

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(xStartPosition, yPosition, buttonWidth, buttonHeight);
        dialog.add(btnBuscar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(xStartPosition + buttonWidth + 10, yPosition, buttonWidth, buttonHeight);
        dialog.add(btnCancelar);

        // Acción al presionar 'Productos'
        btnDetalleVenta.addActionListener(e -> {
            listadoProductos = detalleController.getlistado(idField.getText());
            DetalleVentaDialog detalleDialog = new DetalleVentaDialog((Frame) SwingUtilities.getWindowAncestor(VentaPanel.this),"Consulta Detalle", listadoProductos);
            detalleDialog.setVisible(true);

        });

        btnBuscar.addActionListener(e -> {
            String datos [] = ventaController.consultar(idField.getText());
            idField.setEditable(false);
            clienteField.setText(datos[1]);
            clienteField.setEditable(false);
            fechaField.setText(Validador.convertirFecha(datos[2]));
            fechaField.setEditable(false);
            descuentoField.setText(datos[3]);
            descuentoField.setEditable(false);
            metodoPrimBox.setSelectedIndex(Integer.parseInt(datos[4]) - 1);
            metodoPrimBox.setEditable(false);
            montoPrimField.setText(datos[5]);
            montoPrimField.setEditable(false);
            metodoSecBox.setSelectedIndex(Integer.parseInt(datos[6]) - 1);
            metodoSecBox.setEditable(false);
            montoSecField.setText(datos[7]);
            montoSecField.setEditable(false);
            montoTotalField.setText("$" + datos[8]);
            montoTotalField.setEditable(false);
            checkBoxPagada.setSelected(Boolean.parseBoolean(datos[9]));
            checkBoxPagada.setEnabled(false);
            checkBoxEntregada.setSelected(Boolean.parseBoolean(datos[10]));
            checkBoxEntregada.setEnabled(false);
            checkBoxActivo.setSelected(Boolean.parseBoolean(datos[11]));
            checkBoxActivo.setEnabled(false);
        });



        btnCancelar.addActionListener( e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    @Override
    public void showList() {

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Lista de Ventas", true);
        dialog.setBounds(100, 100, 800, 600);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        DefaultTableModel tableModel = ventaController.listar();
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
        // Lógica para listar las ventas existentes
    }

    @Override
    public void showDelete() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Baja Venta", true);
        dialog.setBounds(100, 100, 267, 144);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JLabel idLabel = new JLabel("ID Venta:");
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
            JOptionPane.showMessageDialog(null, ventaController.eliminar(idField.getText()));
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
}
