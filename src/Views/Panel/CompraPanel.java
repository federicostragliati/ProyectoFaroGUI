package Views.Panel;

import Controller.*;
import Model.Auxiliares.ListadoProductos;
import Model.Validaciones.Herramientas;
import Views.Dialog.DetalleCompraDialog;
import Views.Dialog.DetalleVentaDialog;
import Views.Interfaces.PanelInterface;
import com.toedter.calendar.JDateChooser;
import dominio.MetodoDePago;
import dominio.Proveedor;

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

public class CompraPanel extends GeneralPanel implements PanelInterface {

    private boolean isAdjusting = false;
    private List<ListadoProductos> listadoProductos = new ArrayList<>();
    private ProveedorController proveedorController = new ProveedorController();
    private MetodoPagoController metodoPagoController =new MetodoPagoController();
    private CompraController compraController = new CompraController();
    private DetalleCompraController detalleController = new DetalleCompraController();
    private ProductoController productoController = new ProductoController();

    public CompraPanel(String boton1, String boton2, String boton3, String boton4, String boton5) {
        super(boton1, boton2, boton3, boton4, boton5);

        super.getCreateButton().addActionListener(e -> showCreate());
        super.getModifyButton().addActionListener(e -> showModify());
        super.getConsultButton().addActionListener(e -> showGet());
        super.getListButton().addActionListener(e -> showList());
        super.getDeleteButton().addActionListener(e -> showDelete());
    }

    @Override
    public void showCreate() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Nueva Compra", true);
        dialog.setBounds(100, 100, 450, 470); // Ajusté el tamaño del diálogo
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        // Etiqueta y campo de proveedor
        JLabel lblProveedor = new JLabel("Proveedor:");
        lblProveedor.setBounds(10, 10, 150, 25);
        dialog.add(lblProveedor);

        JTextField proveedorField = new JTextField();
        proveedorField.setBounds(160, 10, 250, 25);
        dialog.add(proveedorField);

        JComboBox proveedorBox = new JComboBox();
        proveedorBox.setBounds(160, 10, 250, 25);
        proveedorBox.setVisible(false);
        dialog.add(proveedorBox);

        // Listener para autocompletar proveedores
        proveedorField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!isAdjusting) {
                    isAdjusting = true;
                    String busqueda = proveedorField.getText().toLowerCase();
                    proveedorBox.removeAllItems();

                    List<Proveedor> coincidencias = proveedorController.listado().stream()
                            .filter(proveedor -> (String.valueOf(proveedor.getId()).toLowerCase().contains(busqueda)|| proveedor.getCuit().toLowerCase().contains(busqueda)
                                    || proveedor.getRazonSocial().toLowerCase().contains(busqueda))
                                    && proveedor.isActivo())
                            .collect(Collectors.toList());

                    if (!coincidencias.isEmpty()) {
                        for (Proveedor proveedor : coincidencias) {
                            proveedorBox.addItem(proveedor.getId() + " - " + proveedor.getRazonSocial() + " - " + proveedor.getCuit());
                        }
                        proveedorBox.setVisible(true);
                        proveedorBox.showPopup();
                    } else {
                        proveedorBox.setVisible(false);
                    }

                    isAdjusting = false;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && proveedorBox.isVisible()) {
                    if (proveedorBox.getSelectedItem() != null) {
                        String proveedorSeleccionado = (String) proveedorBox.getSelectedItem();
                        proveedorField.setText(proveedorSeleccionado);
                        proveedorBox.setVisible(false);
                    }
                }
            }
        });

        proveedorBox.addActionListener(e -> {
            if (!isAdjusting && proveedorBox.getSelectedItem() != null) {
                String proveedorSeleccionado = (String) proveedorBox.getSelectedItem();
                proveedorField.setText(proveedorSeleccionado);
                proveedorBox.setVisible(false);
            }
        });

        proveedorField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                proveedorBox.setVisible(false);
            }
        });

        // Etiqueta y campo de fecha
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(10, 70, 150, 25);
        dialog.add(lblFecha);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        JTextField fechaField = new JTextField(dateFormat.format(date));
        fechaField.setBounds(160, 70, 250, 25);
        dialog.add(fechaField);

        fechaField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(fechaField);
            }
        });

        // Método de pago primario
        JLabel lblMetodoPrim = new JLabel("Método de Pago Primario:");
        lblMetodoPrim.setBounds(10, 110, 150, 25);
        dialog.add(lblMetodoPrim);

        JComboBox metodoPrimBox = new JComboBox();
        metodoPrimBox.setBounds(160, 110, 250, 25);
        for (MetodoDePago metodoDePago : metodoPagoController.listaMetodos()) {
            if (metodoDePago.isActivo()) {
                metodoPrimBox.addItem(metodoDePago.getId() + " - " + metodoDePago.getMetodo());
            }
        }
        dialog.add(metodoPrimBox);

        // Monto del pago primario
        JLabel lblMontoPrim = new JLabel("Monto Pago Primario:");
        lblMontoPrim.setBounds(10, 150, 150, 25);
        dialog.add(lblMontoPrim);


        JLabel pesosLabel1 = new JLabel("$");
        pesosLabel1.setBounds(150,150,220,25);
        dialog.add(pesosLabel1);
        JTextField montoPrimField = new JTextField();
        montoPrimField.setBounds(160, 150, 250, 25);
        dialog.add(montoPrimField);

        // Método de pago secundario
        JLabel lblMetodoSec = new JLabel("Método de Pago Secundario:");
        lblMetodoSec.setBounds(10, 190, 150, 25);
        dialog.add(lblMetodoSec);

        JComboBox metodoSecBox = new JComboBox();
        metodoSecBox.setBounds(160, 190, 250, 25);
        for (MetodoDePago metodoDePago : metodoPagoController.listaMetodos()) {
            if (metodoDePago.isActivo()) {
                metodoSecBox.addItem(metodoDePago.getId() + " - " + metodoDePago.getMetodo());
            }
        }
        metodoSecBox.setSelectedIndex(5);
        dialog.add(metodoSecBox);

        // Monto del pago secundario
        JLabel lblMontoSec = new JLabel("Monto Pago Secundario:");
        lblMontoSec.setBounds(10, 230, 150, 25);
        dialog.add(lblMontoSec);


        JLabel pesosLabel2 = new JLabel("$");
        pesosLabel2.setBounds(150,230,220,25);
        dialog.add(pesosLabel2);
        JTextField montoSecField = new JTextField();
        montoSecField.setBounds(160, 230, 250, 25);
        dialog.add(montoSecField);

        // Botón para productos
        JButton btnDetalleVenta = new JButton("Productos");
        btnDetalleVenta.setBounds(10, 270, 150, 30);
        dialog.add(btnDetalleVenta);

        // Monto total
        JLabel lblMontoTotal = new JLabel("Monto Total:");
        lblMontoTotal.setBounds(10, 310, 150, 25);
        dialog.add(lblMontoTotal);

        JLabel pesosLabel3 = new JLabel("$");
        pesosLabel3.setBounds(150,310,220,25);
        dialog.add(pesosLabel3);
        JTextField montoTotalField = new JTextField();
        montoTotalField.setBounds(160, 310, 250, 25);
        dialog.add(montoTotalField);

        // Checkboxes
        JCheckBox checkBoxPagada = new JCheckBox("Pagada");
        checkBoxPagada.setBounds(10, 350, 150, 25);
        dialog.add(checkBoxPagada);

        JCheckBox checkBoxEntregada = new JCheckBox("Recibida");
        checkBoxEntregada.setBounds(160, 350, 150, 25);
        dialog.add(checkBoxEntregada);

        // Botones finales
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 400, 100, 30);
        dialog.add(btnAceptar);

        JButton btnVerificar = new JButton("Actualizar");
        btnVerificar.setBounds(120, 400, 100, 30);
        //dialog.add(btnVerificar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(310, 400, 100, 30);
        dialog.add(btnCancelar);

        // Acción al presionar 'Productos'
        btnDetalleVenta.addActionListener(e -> {
            DetalleCompraDialog detalleDialog = new DetalleCompraDialog((Frame) SwingUtilities.getWindowAncestor(CompraPanel.this), listadoProductos);
            detalleDialog.setVisible(true);
            listadoProductos = detalleDialog.getListadoProductos();
            montoTotalField.setText(detalleController.totalCompra(listadoProductos));
        });

        // Acción al presionar 'Aceptar'
        btnAceptar.addActionListener(e -> {

            List<String> listadoVerificado = productoController.checkActivos(listadoProductos);

            if (listadoVerificado.get(0).equalsIgnoreCase("Productos Verificados Correctamente")) {

                String mensaje = compraController.crear(
                        proveedorField.getText(),
                        fechaField.getText(),
                        metodoPrimBox.getSelectedItem().toString(),
                        montoPrimField.getText(),
                        metodoSecBox.getSelectedItem().toString(),
                        montoSecField.getText(),
                        montoTotalField.getText(),
                        checkBoxPagada.isSelected(),
                        checkBoxEntregada.isSelected());

                if (checkBoxPagada.isSelected() && metodoPrimBox.getSelectedItem().toString().contains("Cheque")) {
                    ContabilidadPanel contabilidadPanel = new ContabilidadPanel();
                    contabilidadPanel.showCreateCheque(String.valueOf(compraController.ultimaCompra()),"",montoPrimField.getText(),proveedorField.getText(),true);

                }
                if (checkBoxPagada.isSelected() && metodoSecBox.getSelectedItem().toString().contains("Cheque")) {
                    ContabilidadPanel contabilidadPanel = new ContabilidadPanel();
                    contabilidadPanel.showCreateCheque(String.valueOf(compraController.ultimaCompra()),"",montoSecField.getText(),proveedorField.getText(),true);
                }

                if (mensaje.equalsIgnoreCase("Compra Generada")) {
                    JOptionPane.showMessageDialog(null,mensaje + " ID Compra: " + compraController.ultimaCompra());
                    detalleController.crear(listadoProductos, compraController.ultimaCompra());
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
            montoTotalField.setText(detalleController.totalCompra(listadoProductos));
        });

        // Acción al presionar 'Cancelar'
        btnCancelar.addActionListener(e -> {
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
        JLabel lblIDCompra = new JLabel("ID Compra:");
        lblIDCompra.setBounds(10, 11, 150, 14);
        dialog.add(lblIDCompra);

        JLabel lblProveedor = new JLabel("Proveedor:");
        lblProveedor.setBounds(10, 36, 150, 14);
        dialog.add(lblProveedor);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(10, 61, 150, 14);
        dialog.add(lblFecha);


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

        JTextField proveedorField = new JTextField();
        proveedorField.setBounds(170, 33, 220, 22);
        dialog.add(proveedorField);

        JTextField fechaField = new JTextField();
        fechaField.setBounds(170, 58, 220, 22);
        dialog.add(fechaField);

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
        pesosLabel1.setBounds(160,133,220,22);
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
        pesosLabel2.setBounds(160,183,220,22);
        dialog.add(pesosLabel2);
        JTextField montoSecField = new JTextField();
        montoSecField.setBounds(170, 183, 220, 22);
        dialog.add(montoSecField);

        JLabel pesosLabel3 = new JLabel("$");
        pesosLabel3.setBounds(160,241,220,22);
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

        JCheckBox checkBoxEntregada = new JCheckBox("Recibida");
        checkBoxEntregada.setBounds(10, 293, 97, 23);
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
            DetalleVentaDialog detalleDialog = new DetalleVentaDialog((Frame) SwingUtilities.getWindowAncestor(CompraPanel.this),"Consulta Detalle", listadoProductos);
            detalleDialog.setVisible(true);

        });

        btnBuscar.addActionListener(e -> {
            String datos [] = compraController.consultar(idField.getText());
            if(datos.length == 1 ) {
                idField.setText(datos[0]);
            } else {
                idField.setEditable(false);
                proveedorField.setText(datos[1]);
                proveedorField.setEditable(false);
                fechaField.setText(Herramientas.convertirFecha(datos[2]));
                fechaField.setEditable(false);
                metodoPrimBox.setSelectedIndex(Integer.parseInt(datos[3]) - 1);
                metodoPrimBox.setEditable(false);
                montoPrimField.setText(datos[4]);
                montoPrimField.setEditable(false);
                metodoSecBox.setSelectedIndex(Integer.parseInt(datos[5]) - 1);
                metodoSecBox.setEditable(false);
                montoSecField.setText(datos[6]);
                montoSecField.setEditable(false);
                montoTotalField.setText(datos[7]);
                montoTotalField.setEditable(false);
                checkBoxPagada.setSelected(Boolean.parseBoolean(datos[8]));
                checkBoxPagada.setEnabled(false);
                checkBoxEntregada.setSelected(Boolean.parseBoolean(datos[9]));
                checkBoxEntregada.setEnabled(false);
                checkBoxActivo.setSelected(Boolean.parseBoolean(datos[10]));
                checkBoxActivo.setEnabled(false);
            }
        });

        btnCancelar.addActionListener( e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    @Override
    public void showModify() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Consultar Venta", true);
        dialog.setBounds(100, 100, 450, 460); // Ajusté el tamaño para mayor espacio
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Etiquetas
        JLabel lblIDCompra = new JLabel("ID Compra:");
        lblIDCompra.setBounds(10, 15, 150, 20);
        dialog.add(lblIDCompra);

        JLabel lblProveedor = new JLabel("Proveedor:");
        lblProveedor.setBounds(10, 45, 150, 20);
        dialog.add(lblProveedor);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(10, 75, 150, 20);
        dialog.add(lblFecha);

        JLabel lblMetodoPrim = new JLabel("Método de Pago Primario:");
        lblMetodoPrim.setBounds(10, 105, 150, 20);
        dialog.add(lblMetodoPrim);

        JLabel lblMontoPrim = new JLabel("Monto Pago Primario:");
        lblMontoPrim.setBounds(10, 135, 150, 20);
        dialog.add(lblMontoPrim);

        JLabel lblMetodoSec = new JLabel("Método de Pago Secundario:");
        lblMetodoSec.setBounds(10, 165, 150, 20);
        dialog.add(lblMetodoSec);

        JLabel lblMontoSec = new JLabel("Monto Pago Secundario:");
        lblMontoSec.setBounds(10, 195, 150, 20);
        dialog.add(lblMontoSec);

        JLabel lblMontoTotal = new JLabel("Monto Total:");
        lblMontoTotal.setBounds(10, 255, 150, 20);
        dialog.add(lblMontoTotal);

        // Campos de texto
        JTextField idField = new JTextField();
        idField.setBounds(170, 15, 250, 22);
        dialog.add(idField);

        JTextField proveedorField = new JTextField();
        proveedorField.setBounds(170, 45, 250, 22);
        dialog.add(proveedorField);

        JTextField fechaField = new JTextField();
        fechaField.setBounds(170, 75, 250, 22);
        dialog.add(fechaField);

        fechaField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(fechaField);
            }
        });

        // Comboboxes
        JComboBox<String> metodoPrimBox = new JComboBox<>();
        metodoPrimBox.setBounds(170, 105, 250, 22);
        for (MetodoDePago metodoDePago : metodoPagoController.listaMetodos()) {
            if (metodoDePago.isActivo()) {
                metodoPrimBox.addItem(metodoDePago.getId() + " - " + metodoDePago.getMetodo());
            }
        }
        dialog.add(metodoPrimBox);

        JLabel signoPesosLabel1 = new JLabel("$");
        signoPesosLabel1.setBounds(160,135,250,22);
        dialog.add(signoPesosLabel1);
        JTextField montoPrimField = new JTextField();
        montoPrimField.setBounds(170, 135, 250, 22);
        dialog.add(montoPrimField);


        JComboBox<String> metodoSecBox = new JComboBox<>();
        metodoSecBox.setBounds(170, 165, 250, 22);
        for (MetodoDePago metodoDePago : metodoPagoController.listaMetodos()) {
            if (metodoDePago.isActivo()) {
                metodoSecBox.addItem(metodoDePago.getId() + " - " + metodoDePago.getMetodo());
            }
        }
        dialog.add(metodoSecBox);

        JLabel signoPesosLabel2 = new JLabel("$");
        signoPesosLabel2.setBounds(160,195,250,22);
        dialog.add(signoPesosLabel2);
        JTextField montoSecField = new JTextField();
        montoSecField.setBounds(170, 195, 250, 22);
        dialog.add(montoSecField);

        JLabel signoPesosLabel3 = new JLabel("$");
        signoPesosLabel3.setBounds(160,255,250,22);
        dialog.add(signoPesosLabel3);
        JTextField montoTotalField = new JTextField();
        montoTotalField.setBounds(170, 255, 250, 22);
        dialog.add(montoTotalField);


        // Botones y Checkboxes
        JButton btnDetalleVenta = new JButton("Productos");
        btnDetalleVenta.setBounds(10, 225, 150, 25); // Ajusté el tamaño del botón
        dialog.add(btnDetalleVenta);

        JCheckBox checkBoxPagada = new JCheckBox("Pagada");
        checkBoxPagada.setBounds(10, 285, 120, 25);
        dialog.add(checkBoxPagada);

        JCheckBox checkBoxEntregada = new JCheckBox("Recibida");
        checkBoxEntregada.setBounds(140, 285, 120, 25);
        dialog.add(checkBoxEntregada);

        JCheckBox checkBoxActivo = new JCheckBox("Activa");
        checkBoxActivo.setBounds(270, 285, 120, 25);
        dialog.add(checkBoxActivo);

        // Botones finales
        int buttonWidth = 100;
        int spacing = (450 - 4 * buttonWidth) / 5;
        int yPosition = 350;

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(spacing, yPosition, buttonWidth, 25);
        dialog.add(btnAceptar);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(spacing * 2 + buttonWidth, yPosition, buttonWidth, 25);
        dialog.add(btnVerificar);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(spacing * 3 + 2 * buttonWidth, yPosition, buttonWidth, 25);
        dialog.add(btnBuscar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(spacing * 4 + 3 * buttonWidth, yPosition, buttonWidth, 25);
        dialog.add(btnCancelar);

        // Acción al presionar 'Productos'
        btnDetalleVenta.addActionListener(e -> {
            listadoProductos = detalleController.getlistado(idField.getText());
            DetalleVentaDialog detalleDialog = new DetalleVentaDialog((Frame) SwingUtilities.getWindowAncestor(CompraPanel.this), "Consulta Detalle", listadoProductos);
            detalleDialog.setVisible(true);
        });



        // Acción al presionar 'Buscar'
        btnBuscar.addActionListener(e -> {
            String datos[] = compraController.consultar(idField.getText());
            if (datos.length == 1) {
                idField.setText(datos[0]);
            } else {
                idField.setEditable(false);
                proveedorField.setText(datos[1]);
                proveedorField.setEditable(false);
                fechaField.setText(Herramientas.convertirFecha(datos[2]));
                metodoPrimBox.setSelectedIndex(Integer.parseInt(datos[3]) - 1);
                montoPrimField.setText(datos[4]);
                metodoSecBox.setSelectedIndex(Integer.parseInt(datos[5]) - 1);
                montoSecField.setText(datos[6]);
                montoTotalField.setText(datos[7]);
                checkBoxPagada.setSelected(Boolean.parseBoolean(datos[8]));
                if (Boolean.parseBoolean(datos[8])) {
                    checkBoxPagada.setEnabled(false);
                }
                checkBoxEntregada.setSelected(Boolean.parseBoolean(datos[9]));
                if (Boolean.parseBoolean(datos[9])) {
                    checkBoxEntregada.setEnabled(false);
                }
                checkBoxActivo.setSelected(Boolean.parseBoolean(datos[10]));
                checkBoxActivo.setEnabled(false);

            }

        });

        // Acción al presionar 'Aceptar'
        btnAceptar.addActionListener(e -> {

            String datos[] = compraController.consultar(idField.getText());

            //Si la compra no estaba pagada y ahora esta pagada, creo cheque de metodo 1 (Si es que el metodo de pago es Cheque o E-Cheque)
            if (!Boolean.parseBoolean(datos[8]) && checkBoxPagada.isSelected() && metodoPrimBox.getSelectedItem().toString().contains("Cheque")) {
                ContabilidadPanel contabilidadPanel = new ContabilidadPanel();
                contabilidadPanel.showCreateCheque(String.valueOf(compraController.ultimaCompra()),"",montoPrimField.getText(),proveedorField.getText(),true);
            }

            //Si la compra no estaba pagada y ahora esta pagada, creo cheque de metodo 2 (Si es que el metodo de pago es Cheque o E-Cheque)
            if (!Boolean.parseBoolean(datos[8]) && checkBoxPagada.isSelected() && metodoSecBox.getSelectedItem().toString().contains("Cheque")) {
                ContabilidadPanel contabilidadPanel = new ContabilidadPanel();
                contabilidadPanel.showCreateCheque(String.valueOf(compraController.ultimaCompra()),"",montoPrimField.getText(),proveedorField.getText(),true);
            }

            JOptionPane.showMessageDialog(null, compraController.modificar(idField.getText(),
                    datos[0],
                    datos[1],
                    fechaField.getText(),
                    metodoPrimBox.getSelectedItem().toString(),
                    montoPrimField.getText(),
                    metodoSecBox.getSelectedItem().toString(),
                    montoSecField.getText(),
                    montoTotalField.getText(),
                    checkBoxPagada.isSelected(),
                    checkBoxEntregada.isSelected(),
                    checkBoxActivo.isSelected()));
        });

        btnCancelar.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    @Override
    public void showList() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Lista de Compras", true);
        dialog.setBounds(100, 100, 800, 600);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        DefaultTableModel tableModel = compraController.listar();
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
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Baja Compra", true);
        dialog.setBounds(100, 100, 267, 144);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JLabel idLabel = new JLabel("ID Compra:");
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
            JOptionPane.showMessageDialog(null, compraController.eliminar(idField.getText()));
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
