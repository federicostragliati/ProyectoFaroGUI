package Views.Panel;

import Controller.*;
import Model.Auxiliares.ListadoProductos;
import Model.Validaciones.Herramientas;
import Views.Dialog.DetalleVentaDialog;
import Views.Interfaces.PanelInterface;
import com.toedter.calendar.JDateChooser;
import dominio.Cliente;
import dominio.MetodoDePago;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private ProductoController productoController = new ProductoController();
    private ComprobantesController comprobantesController = new ComprobantesController();
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

        JButton btnVerificar = new JButton("Actualizar");
        btnVerificar.setBounds(109, 333, 89, 23);
        dialog.add(btnVerificar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(289, 333, 89, 23);
        dialog.add(btnCancelar);

        clienteField = new JTextField();
        clienteField.setBounds(164, 7, 220, 22);
        JComboBox clienteBox = new JComboBox();
        clienteBox.setBounds(164, 29, 220, 22);
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
                            .filter(cliente -> (String.valueOf(cliente.getId()).contains(busqueda)|| cliente.getCuitCliente().toLowerCase().contains(busqueda)
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
                clienteField.setText(clienteSeleccionado);
                clienteBox.setVisible(false);
            }
        });

        clienteField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                clienteBox.setVisible(false);
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        fechaField = new JTextField(dateFormat.format(date));
        fechaField.setBounds(164, 31, 220, 20); // Ajusta las coordenadas y tamaño según sea necesario
        dialog.add(fechaField);


        // Mostrar el JDateChooser cuando se hace clic en el JTextField
        fechaField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(fechaField);
            }
        });



        JLabel porcentajeLabel = new JLabel("%");
        porcentajeLabel.setBounds(154,58,220,20);
        dialog.add(porcentajeLabel);
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

        JLabel pesosLabel1 = new JLabel("$");
        pesosLabel1.setBounds(154, 108, 220, 20);
        dialog.add(pesosLabel1);
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


        JLabel pesosLabel2 = new JLabel("$");
        pesosLabel2.setBounds(154, 158, 220, 20);
        dialog.add(pesosLabel2);
        montoSecField = new JTextField();
        montoSecField.setBounds(164, 158, 220, 20);
        dialog.add(montoSecField);
        montoSecField.setColumns(10);


        JLabel pesosLabel3 = new JLabel("$");
        pesosLabel3.setBounds(154, 217, 220, 20);
        dialog.add(pesosLabel3);
        montoTotalField = new JTextField();
        montoTotalField.setBounds(164, 217, 220, 20);
        dialog.add(montoTotalField);
        montoTotalField.setColumns(10);

        // Acción al presionar 'Productos'
        btnDetalleVenta.addActionListener(e -> {
            DetalleVentaDialog detalleDialog = new DetalleVentaDialog((Frame) SwingUtilities.getWindowAncestor(VentaPanel.this), listadoProductos);
            detalleDialog.setVisible(true);
            listadoProductos = detalleDialog.getListadoProductos();
            ventaController.calcularMontoTotal(descuentoField,montoTotalField,listadoProductos);
            //Limpiar listadoProductos una vez que cree la venta o la cancele
        });

        btnAceptar.addActionListener( e -> {



            List<String> listadoVerificado = productoController.checkActivos(listadoProductos);

            if (listadoVerificado.get(0).equalsIgnoreCase("Productos Verificados Correctamente")) {

                String mensaje = ventaController.crear(clienteField.getText(),
                        fechaField.getText(),
                        descuentoField.getText(),
                        metodoPrimBox.getSelectedItem().toString(),
                        montoPrimField.getText(),
                        metodoSecBox.getSelectedItem().toString(),
                        montoSecField.getText(),
                        montoTotalField.getText(),
                        checkBoxPagada.isSelected(),
                        checkBoxEntregada.isSelected());


                //Si la venta esta pagada y el metodo de pago 1 es Cheque o E-Cheque, se crea el cheque
                if (checkBoxPagada.isSelected() && metodoPrimBox.getSelectedItem().toString().contains("Cheque")) {
                    ContabilidadPanel contabilidadPanel = new ContabilidadPanel();
                    contabilidadPanel.showCreateCheque(String.valueOf(ventaController.ultimaVenta()),clienteField.getText(),montoPrimField.getText(),"",true);

                }

                //Si la venta esta pagada y el metodo de pago 2 es Cheque o E-Cheque, se crea el cheque
                if (checkBoxPagada.isSelected() && metodoSecBox.getSelectedItem().toString().contains("Cheque")) {
                    ContabilidadPanel contabilidadPanel = new ContabilidadPanel();
                    contabilidadPanel.showCreateCheque(String.valueOf(ventaController.ultimaVenta()),clienteField.getText(),montoPrimField.getText(),"",true);
                }

                if (mensaje.equalsIgnoreCase("Venta Generada")) {
                    JOptionPane.showMessageDialog(null,mensaje + " ID Venta: " + ventaController.ultimaVenta());
                    detalleController.crear(listadoProductos,ventaController.ultimaVenta());
                    listadoProductos.clear();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(null,mensaje );
                }
            } else {
                JOptionPane.showMessageDialog(null, "Productos no activos: " + listadoVerificado);
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

        JLabel porcentajeLabel = new JLabel("%");
        porcentajeLabel.setBounds(160, 83, 220, 20);
        dialog.add(porcentajeLabel);

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

        JLabel pesosLabel1 = new JLabel("$");
        pesosLabel1.setBounds(160, 133, 220, 22);
        dialog.add(pesosLabel1);

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

        JLabel pesosLabel2 = new JLabel("$");
        pesosLabel2.setBounds(160, 183, 220, 22);
        dialog.add(pesosLabel2);

        JTextField montoSecField = new JTextField();
        montoSecField.setBounds(170, 183, 220, 22);
        dialog.add(montoSecField);

        JLabel pesosLabel3 = new JLabel("$");
        pesosLabel3.setBounds(160, 241, 220, 22);
        dialog.add(pesosLabel3);

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
        checkBoxActivo.setBounds(10, 345, 97, 23);
        dialog.add(checkBoxActivo);

        int buttonWidth = 89;
        int dialogWidth = dialog.getWidth();
        int buttonHeight = 23;
        int yPosition = 358;

        // Centrar los botones 'Buscar' y 'Cancelar'
        int totalButtonWidth = buttonWidth * 4 + 30; // 30 es el espacio entre los botones
        int xStartPosition = (dialogWidth - totalButtonWidth) / 2;

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(xStartPosition, yPosition, buttonWidth, buttonHeight);
        dialog.add(btnBuscar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(xStartPosition + buttonWidth + 10, yPosition, buttonWidth, buttonHeight);
        dialog.add(btnCancelar);

        // Botón "Recibo"
        JButton btnRecibo = new JButton("Recibo");
        btnRecibo.setBounds(xStartPosition + (buttonWidth + 10) * 2, yPosition, buttonWidth, buttonHeight);
        dialog.add(btnRecibo);

        // Botón "Remito"
        JButton btnRemito = new JButton("Remito");
        btnRemito.setBounds(xStartPosition + (buttonWidth + 10) * 3, yPosition, buttonWidth, buttonHeight);
        dialog.add(btnRemito);

        // Acción al presionar 'Productos'
        btnDetalleVenta.addActionListener(e -> {
            listadoProductos = detalleController.getlistado(idField.getText());
            DetalleVentaDialog detalleDialog = new DetalleVentaDialog((Frame) SwingUtilities.getWindowAncestor(VentaPanel.this), "Consulta Detalle", listadoProductos);
            detalleDialog.setVisible(true);
        });

        btnBuscar.addActionListener(e -> {
            String datos[] = ventaController.consultar(idField.getText());
            idField.setEditable(false);
            clienteField.setText(datos[1]);
            clienteField.setEditable(false);
            fechaField.setText(Herramientas.convertirFecha(datos[2]));
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
            montoTotalField.setText(datos[8]);
            montoTotalField.setEditable(false);
            checkBoxPagada.setSelected(Boolean.parseBoolean(datos[9]));
            checkBoxPagada.setEnabled(false);
            checkBoxEntregada.setSelected(Boolean.parseBoolean(datos[10]));
            checkBoxEntregada.setEnabled(false);
            checkBoxActivo.setSelected(Boolean.parseBoolean(datos[11]));
            checkBoxActivo.setEnabled(false);
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        btnRecibo.addActionListener(e -> {
            comprobantesController.generarRecibo(idField.getText());
        });

        btnRemito.addActionListener(e -> {
            comprobantesController.generarRemito(idField.getText());
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

        fechaField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(fechaField);
            }
        });

        JLabel porcentajeLabel = new JLabel("%");
        porcentajeLabel.setBounds(160,83,220,20);
        dialog.add(porcentajeLabel);

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

        JLabel pesosLabel1 = new JLabel("$");
        pesosLabel1.setBounds(160, 133, 220, 22);
        dialog.add(pesosLabel1);

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

        JLabel pesosLabel2 = new JLabel("$");
        pesosLabel2.setBounds(160, 183, 220, 22);
        dialog.add(pesosLabel2);

        JTextField montoSecField = new JTextField();
        montoSecField.setBounds(170, 183, 220, 22);
        dialog.add(montoSecField);

        JLabel pesosLabel3 = new JLabel("$");
        pesosLabel3.setBounds(160, 241, 220, 22);
        dialog.add(pesosLabel3);

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

        JButton btnVerificar = new JButton("Actualizar");
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
            if (datos.length == 1) {
                idField.setText(datos[0]);
            } else {
                idField.setEditable(false);
                clienteField.setText(datos[1]);
                clienteField.setEditable(false);
                fechaField.setText(Herramientas.convertirFecha(datos[2]));
                descuentoField.setText(datos[3]);
                metodoPrimBox.setSelectedIndex(Integer.parseInt(datos[4]) - 1);
                montoPrimField.setText(datos[5]);
                metodoSecBox.setSelectedIndex(Integer.parseInt(datos[6]) - 1);
                montoSecField.setText(datos[7]);
                montoTotalField.setText(datos[8]);
                checkBoxPagada.setSelected(Boolean.parseBoolean(datos[9]));
                checkBoxEntregada.setSelected(Boolean.parseBoolean(datos[10]));
            }
        });

        btnAceptar.addActionListener(e -> {

            String datos [] = ventaController.consultar(idField.getText());

            //Si la venta no estaba pagada y ahora esta pagada, creo cheque de metodo 1 (Si es que el metodo de pago es Cheque o E-Cheque)
            if (!Boolean.parseBoolean(datos[8]) && checkBoxPagada.isSelected() && metodoPrimBox.getSelectedItem().toString().contains("Cheque")) {
                ContabilidadPanel contabilidadPanel = new ContabilidadPanel();
                contabilidadPanel.showCreateCheque(String.valueOf(ventaController.ultimaVenta()),clienteField.getText(),montoPrimField.getText(),"",true);
            }

            //Si la venta no estaba pagada y ahora esta pagada, creo cheque de metodo 2 (Si es que el metodo de pago es Cheque o E-Cheque)
            if (!Boolean.parseBoolean(datos[8]) && checkBoxPagada.isSelected() && metodoSecBox.getSelectedItem().toString().contains("Cheque")) {
                ContabilidadPanel contabilidadPanel = new ContabilidadPanel();
                contabilidadPanel.showCreateCheque(String.valueOf(ventaController.ultimaVenta()),"",montoPrimField.getText(),"",true);
            }

            JOptionPane.showMessageDialog(null,ventaController.modificar(idField.getText(),
                    fechaField.getText(),
                    descuentoField.getText(),
                    metodoPrimBox.getSelectedItem().toString(),
                    montoPrimField.getText(),
                    metodoSecBox.getSelectedItem().toString(),
                    montoSecField.getText(),
                    montoTotalField.getText(),
                    checkBoxPagada.isSelected(),
                    checkBoxEntregada.isSelected()));

            idField.setEditable(true);
        });

        btnVerificar.addActionListener( e->{
            listadoProductos = detalleController.getlistado(idField.getText());
            ventaController.calcularMontoTotal(descuentoField,montoTotalField,listadoProductos);
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

    private void showDateChooserDialog(JTextField fechaField) {
        // Crear un JDateChooser y configurar su formato
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDate(new Date()); // Establecer la fecha actual
        dateChooser.setDateFormatString("dd/MM/yyyy");

        // Ajustar el tamaño del JDateChooser
        dateChooser.setPreferredSize(new Dimension(150, 30)); // Cambiar las dimensiones del campo de fecha

        // Crear un JPanel para contener el JDateChooser
        JPanel panel = new JPanel();
        panel.add(dateChooser);

        // Mostrar el JOptionPane con el JPanel
        int option = JOptionPane.showOptionDialog(
                this,
                panel,
                "Seleccionar Fecha",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null
        );

        // Actualizar el JTextField si se selecciona una fecha y se presiona OK
        if (option == JOptionPane.OK_OPTION && dateChooser.getDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            fechaField.setText(dateFormat.format(dateChooser.getDate()));
        }
    }



}
