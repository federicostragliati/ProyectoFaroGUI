package Views.Panel;

import Controller.ClienteController;
import Controller.DetalleVentaController;
import Controller.MetodoPagoController;
import Controller.VentaController;
import Model.Auxiliares.ListadoProductos;
import Views.Dialog.DetalleVentaDialog;
import Views.Interfaces.PanelInterface;
import dominio.Cliente;
import dominio.MetodoDePago;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private DetalleVentaController Detallecontroller = new DetalleVentaController();
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

        JCheckBox checkBoxCompleta = new JCheckBox("Completa");
        checkBoxCompleta.setBounds(6, 267, 97, 23);
        dialog.add(checkBoxCompleta);

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
            ventaController.crear(clienteField.getText(),
                    fechaField.getText(),
                    descuentoField.getText(),
                    metodoPrimBox.getSelectedItem().toString(),
                    montoPrimField.getText(),
                    metodoSecBox.getSelectedItem().toString(),
                    montoSecField.getText(),
                    Detallecontroller.totalVenta(listadoProductos),
                    checkBoxPagada.isSelected(),
                    checkBoxCompleta.isSelected(),
                    checkBoxCompleta.isSelected());

            Detallecontroller.crear(listadoProductos,ventaController.ultimaVenta());
        });

        // Acción al presionar 'Verificar'
        btnVerificar.addActionListener(e -> {
            //DetalleVentaDialog detalleDialog = new DetalleVentaDialog((Frame) SwingUtilities.getWindowAncestor(VentaPanel.this), listadoProductos);
            //listadoProductos = detalleDialog.getListadoProductos();
            BigDecimal total = new BigDecimal(Detallecontroller.totalVenta(listadoProductos));
            BigDecimal descuento;

            if (!descuentoField.getText().isEmpty()) {
                descuento = new BigDecimal(descuentoField.getText());
                BigDecimal valor1 = descuento.divide(new BigDecimal(100));
                BigDecimal valor2 = valor1.subtract(new BigDecimal(1));
                BigDecimal valor3 = valor2.multiply(new BigDecimal(-1));
                BigDecimal montoConDescuento = total.multiply(valor3);
                montoTotalField.setText("$" + montoConDescuento);
            } else {
                montoTotalField.setText("$" + total);
            }

            //Tengo que meter el aceptar en un JOptionPane para recibir los mensajes de alerta y solo agregar los detalle de venta cuando la venta fue creada correctamente.

            // montoTotalField.setText("$" + total);
            // Aquí puedes añadir la lógica para procesar la venta con los productos seleccionados
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
        // Lógica para modificar una venta existente
    }

    @Override
    public void showGet() {
        // Lógica para consultar una venta específica
    }

    @Override
    public void showList() {
        // Lógica para listar las ventas existentes
    }

    @Override
    public void showDelete() {
        // Lógica para eliminar una venta
    }
}
