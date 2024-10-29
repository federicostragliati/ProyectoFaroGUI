package Views.Panel;

import Controller.ClienteController;
import Controller.ProductoController;
import Controller.ProveedorController;
import Controller.ReportesController;
import com.toedter.calendar.JDateChooser;
import dominio.Cliente;
import dominio.Producto;
import dominio.Proveedor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReportesPanel extends JPanel {

    ReportesController reportesController = new ReportesController();
    ProductoController productoController = new ProductoController();
    ClienteController clienteController = new ClienteController();
    ProveedorController proveedorController = new ProveedorController();
    private boolean isAdjusting = false;

    public ReportesPanel() {

        setLayout(null);

        JLabel lblStock = new JLabel("Stock");
        lblStock.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblStock.setBounds(76, 11, 126, 25);
        add(lblStock);

        JLabel lblMtodosDePago = new JLabel("");
        lblMtodosDePago.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblMtodosDePago.setBounds(76, 158, 162, 25);
        add(lblMtodosDePago);

        JLabel lblVentas = new JLabel("Ventas");
        lblVentas.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblVentas.setBounds(284, 11, 126, 25);
        add(lblVentas);

        JButton btnStock1 = new JButton("Stock Vigente");
        btnStock1.setBounds(76, 47, 153, 23);
        add(btnStock1);

        JButton btnStock2 = new JButton("Stock Producto");
        btnStock2.setBounds(76, 81, 153, 23);
        add(btnStock2);

        JButton btnVentas1 = new JButton("Entre Fechas");
        btnVentas1.setBounds(284, 47, 153, 23);
        add(btnVentas1);

        JButton btnVentas2 = new JButton("Abiertas");
        btnVentas2.setBounds(284, 81, 153, 23);
        add(btnVentas2);

        JButton btnVentas3 = new JButton("Abiertas x Cliente");
        btnVentas3.setBounds(284, 115, 153, 23);
        add(btnVentas3);

        JButton btnVentas4 = new JButton("Cliente entre Fechas");
        btnVentas4.setBounds(284, 149, 153, 23);
        add(btnVentas4);

        JLabel lblCompras = new JLabel("Compras");
        lblCompras.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblCompras.setBounds(503, 11, 126, 25);
        add(lblCompras);

        JButton btnCompras1 = new JButton("Entre Fechas");
        btnCompras1.setBounds(503, 47, 153, 23);
        add(btnCompras1);

        JButton btnCompras2 = new JButton("Abiertas");
        btnCompras2.setBounds(503, 81, 153, 23);
        add(btnCompras2);

        JButton btnCompras3 = new JButton("Abiertas x Proveedor");
        btnCompras3.setBounds(503, 115, 153, 23);
        add(btnCompras3);

        JButton btnCompras4 = new JButton("Proveedor entre Fechas");
        btnCompras4.setBounds(503, 149, 153, 23);
        add(btnCompras4);

        JLabel lblListaDePrecios = new JLabel("Lista de Precios");
        lblListaDePrecios.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblListaDePrecios.setBounds(76, 206, 173, 25);
        add(lblListaDePrecios);

        JButton btnPrecios1 = new JButton("Listado Vigente");
        btnPrecios1.setBounds(76, 242, 153, 23);
        add(btnPrecios1);

        JButton btnPrecios2 = new JButton("Historico Producto");
        btnPrecios2.setBounds(76, 276, 153, 23);
        add(btnPrecios2);

        JLabel lblMovimientos = new JLabel("Movimientos");
        lblMovimientos.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblMovimientos.setBounds(284, 206, 126, 25);
        add(lblMovimientos);

        JButton btnMov1 = new JButton("Entradas");
        btnMov1.setBounds(284, 242, 153, 23);
        add(btnMov1);

        JButton btnMov2 = new JButton("Salidas");
        btnMov2.setBounds(284, 276, 153, 23);
        add(btnMov2);

        JLabel lblCheques = new JLabel("Cheques");
        lblCheques.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblCheques.setBounds(503, 206, 126, 25);
        add(lblCheques);

        JButton btnCheques = new JButton("Cheques a Vencer");
        btnCheques.setBounds(503, 242, 153, 23);
        add(btnCheques);

        btnStock1.addActionListener(e -> stockVigente());
        btnStock2.addActionListener(e -> stockProducto());

        btnVentas1.addActionListener(e -> ventaEntreFechas());
        btnVentas2.addActionListener(e -> ventasAbiertas());
        btnVentas3.addActionListener(e -> ventasAbiertasXCliente());
        btnVentas4.addActionListener(e -> ventasAbiertasXClienteEntreFechas());

        btnCompras1.addActionListener(e -> compraEntreFechas());
        btnCompras2.addActionListener(e -> comprasAbiertas());
        btnCompras3.addActionListener(e -> comprasAbiertasXProveedor());
        btnCompras4.addActionListener(e -> comprasAbiertasXProveedorEntreFechas());

        btnPrecios1.addActionListener(e -> listaDePrecios());
        btnPrecios2.addActionListener(e -> historicoPrecioProducto());

        btnMov1.addActionListener(e -> entradas());
        btnMov2.addActionListener(e -> salidas());

        btnCheques.addActionListener(e -> chequesAVencer());
    }
    //Stock
    public void stockVigente() {
        JOptionPane.showMessageDialog(null, reportesController.getStockVigente());
    }

    public void stockProducto() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Seleccionar Producto", true);
        dialog.setBounds(100, 100, 300, 180); // Ajustar tamaño del diálogo
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        // Etiqueta de proveedor
        JLabel lblProveedor = new JLabel("Producto:");
        lblProveedor.setBounds(10, 10, 80, 25); // Ajustar tamaño de la etiqueta
        dialog.add(lblProveedor);

        // Campo de texto para autocompletar
        JTextField productoField = new JTextField();
        productoField.setBounds(90, 10, 180, 25); // Mantener tamaño del campo de texto
        dialog.add(productoField);

        // ComboBox para mostrar coincidencias
        JComboBox productoBox = new JComboBox();
        productoBox.setBounds(90, 10, 180, 25); // Alinear el JComboBox con el campo de texto
        productoBox.setVisible(false);
        dialog.add(productoBox);

        // Listener para autocompletar productos
        productoField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!isAdjusting) {
                    isAdjusting = true;
                    String busqueda = productoField.getText().toLowerCase();
                    productoBox.removeAllItems();

                    List<Producto> coincidencias = productoController.listado().stream()
                            .filter(producto -> ((String.valueOf(producto.getId()).toLowerCase().contains(busqueda)|| producto.getDetalle().toLowerCase().contains(busqueda))
                                    && producto.isActivo())).collect(Collectors.toList());

                    if (!coincidencias.isEmpty()) {
                        for (Producto producto : coincidencias) {
                            productoBox.addItem(producto.getId() + " - " + producto.getDetalle());
                        }
                        productoBox.setVisible(true);
                        productoBox.showPopup();
                    } else {
                        productoBox.setVisible(false);
                    }

                    isAdjusting = false;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && productoBox.isVisible()) {
                    if (productoBox.getSelectedItem() != null) {
                        String proveedorSeleccionado = (String) productoBox.getSelectedItem();
                        productoField.setText(proveedorSeleccionado);
                        productoBox.setVisible(false);
                    }
                }
            }
        });

        // Acción para seleccionar un producto del ComboBox
        productoBox.addActionListener(e -> {
            if (!isAdjusting && productoBox.getSelectedItem() != null) {
                String productoSeleccionado = (String) productoBox.getSelectedItem();
                productoField.setText(productoSeleccionado);
                productoBox.setVisible(false);
            }
        });

        // Ocultar ComboBox cuando se pierde el foco
        productoField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                productoBox.setVisible(false);
            }
        });

        // Botón Aceptar
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(50, 100, 80, 30); // Ajustar posición de Aceptar
        dialog.add(btnAceptar);

        // Acción para mostrar mensaje con la selección del producto
        btnAceptar.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, reportesController.getStockProducto(productoField.getText()));
        });

        // Botón Cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(160, 100, 80, 30); // Ajustar posición de Cancelar
        dialog.add(btnCancelar);

        // Acción para cerrar el diálogo al hacer clic en Cancelar
        btnCancelar.addActionListener(e -> dialog.dispose());

        // Mostrar el diálogo
        dialog.setVisible(true);
    }

    //Ventas
    public void ventaEntreFechas() {

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Seleccionar Fechas", true);
        dialog.setBounds(100, 100, 300, 180); // Ajustar tamaño del diálogo
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JLabel idLabel_1 = new JLabel("Fecha Inicio:");
        idLabel_1.setBounds(10, 20, 100, 20);
        dialog.add(idLabel_1);

        JTextField inicioField = new JTextField();
        inicioField.setBounds(89, 20, 186, 20);
        dialog.add(inicioField);

        inicioField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(inicioField);
            }
        });

        JLabel idLabel = new JLabel("Fecha Fin:");
        idLabel.setBounds(10, 67, 100, 20);
        dialog.add(idLabel);

        JTextField finField = new JTextField();
        finField.setBounds(89, 67, 186, 20);
        dialog.add(finField);

        finField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(finField);
            }
        });

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 98, 100, 30);
        dialog.add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(175, 98, 100, 30);
        dialog.add(btnCancelar);

        btnAceptar.addActionListener( e -> {
            String mensaje = reportesController.getVentaEnFechas(inicioField.getText(), finField.getText());
            if (mensaje.contains("Generado")) {
                JOptionPane.showMessageDialog(null, mensaje);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(null, mensaje);
            }
        });

        btnCancelar.addActionListener( e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);

    }

    public void ventasAbiertas() {
        JOptionPane.showMessageDialog(null, reportesController.getVentasAbiertas());
    }

    public void ventasAbiertasXCliente() {

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Seleccionar Producto", true);
        dialog.setBounds(100, 100, 380, 180); // Ajustar tamaño del diálogo
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JLabel clienteLbl = new JLabel("Cliente: ");
        clienteLbl.setBounds(10, 11, 100, 20);
        dialog.add(clienteLbl);

        JTextField clienteField = new JTextField();
        clienteField.setBounds(89, 11, 265, 20);
        dialog.add(clienteField);

        JComboBox clienteBox = new JComboBox();
        clienteBox.setBounds(89, 11, 265, 20);
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

        JLabel inicioLbl = new JLabel("Fecha Inicio:");
        inicioLbl.setBounds(10, 42, 100, 20);
        //dialog.add(inicioLbl);

        JTextField inicioField = new JTextField();
        inicioField.setBounds(89, 42, 186, 20);
        //dialog.add(inicioField);

        JLabel finLbl = new JLabel("Fecha Fin:");
        finLbl.setBounds(10, 67, 100, 20);
        //dialog.add(finLbl);

        JTextField finField = new JTextField();
        finField.setBounds(89, 67, 186, 20);
        //dialog.add(finField);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 98, 100, 30);
        dialog.add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(254, 98, 100, 30);
        dialog.add(btnCancelar);

        btnAceptar.addActionListener( e -> {
           JOptionPane.showMessageDialog(null,reportesController.getVentasAbiertasXCliente(clienteField.getText()));
           dialog.dispose();
        });

        btnCancelar.addActionListener( e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);

    }

    public void ventasAbiertasXClienteEntreFechas() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Seleccionar Producto", true);
        dialog.setBounds(100, 100, 380, 180); // Ajustar tamaño del diálogo
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JLabel clienteLbl = new JLabel("Cliente: ");
        clienteLbl.setBounds(10, 11, 100, 20);
        dialog.add(clienteLbl);

        JTextField clienteField = new JTextField();
        clienteField.setBounds(89, 11, 265, 20);
        dialog.add(clienteField);

        JComboBox clienteBox = new JComboBox();
        clienteBox.setBounds(89, 11, 265, 20);
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

        JLabel inicioLbl = new JLabel("Fecha Inicio:");
        inicioLbl.setBounds(10, 42, 100, 20);
        dialog.add(inicioLbl);

        JTextField inicioField = new JTextField();
        inicioField.setBounds(89, 42, 186, 20);
        dialog.add(inicioField);

        inicioField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(inicioField);
            }
        });

        JLabel finLbl = new JLabel("Fecha Fin:");
        finLbl.setBounds(10, 67, 100, 20);
        dialog.add(finLbl);

        JTextField finField = new JTextField();
        finField.setBounds(89, 67, 186, 20);
        dialog.add(finField);

        finField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(finField);
            }
        });

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 98, 100, 30);
        dialog.add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(254, 98, 100, 30);
        dialog.add(btnCancelar);

        btnAceptar.addActionListener( e -> {
            JOptionPane.showMessageDialog(null,reportesController.getVentasAbiertasXClienteEntreFechas(inicioField.getText(),finField.getText(),clienteField.getText()));
            dialog.dispose();
        });

        btnCancelar.addActionListener( e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    //Compras
    public void compraEntreFechas() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Seleccionar Fechas", true);
        dialog.setBounds(100, 100, 300, 180); // Ajustar tamaño del diálogo
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JLabel idLabel_1 = new JLabel("Fecha Inicio:");
        idLabel_1.setBounds(10, 20, 100, 20);
        dialog.add(idLabel_1);

        JTextField inicioField = new JTextField();
        inicioField.setBounds(89, 20, 186, 20);
        dialog.add(inicioField);

        inicioField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(inicioField);
            }
        });

        JLabel idLabel = new JLabel("Fecha Fin:");
        idLabel.setBounds(10, 67, 100, 20);
        dialog.add(idLabel);

        JTextField finField = new JTextField();
        finField.setBounds(89, 67, 186, 20);
        dialog.add(finField);

        finField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(finField);
            }
        });

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 98, 100, 30);
        dialog.add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(175, 98, 100, 30);
        dialog.add(btnCancelar);

        btnAceptar.addActionListener( e -> {
            String mensaje = reportesController.getCompraEnFechas(inicioField.getText(), finField.getText());
            if (mensaje.contains("Generado")) {
                JOptionPane.showMessageDialog(null, mensaje);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(null, mensaje);
            }
        });

        btnCancelar.addActionListener( e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    public void comprasAbiertas() {
        JOptionPane.showMessageDialog(null, reportesController.getComprasAbiertas());
    }

    public void comprasAbiertasXProveedor() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Seleccionar Producto", true);
        dialog.setBounds(100, 100, 380, 180); // Ajustar tamaño del diálogo
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JLabel clienteLbl = new JLabel("Cliente: ");
        clienteLbl.setBounds(10, 11, 100, 20);
        dialog.add(clienteLbl);

        JTextField proveedorField = new JTextField();
        proveedorField.setBounds(89, 11, 265, 20);
        dialog.add(proveedorField);

        JComboBox proveedorBox = new JComboBox();
        proveedorBox.setBounds(89, 11, 265, 20);
        proveedorBox.setVisible(false);
        dialog.add(proveedorBox);
        dialog.add(proveedorField);

        proveedorField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!isAdjusting) {
                    isAdjusting = true;
                    String busqueda = proveedorField.getText().toLowerCase();
                    proveedorBox.removeAllItems();

                    List<Proveedor> coincidencias = proveedorController.listado().stream()
                            .filter(proveedor -> (String.valueOf(proveedor.getId()).contains(busqueda)|| proveedor.getCuit().toLowerCase().contains(busqueda)
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

                // Capturar la tecla Enter
                if (e.getKeyCode() == KeyEvent.VK_ENTER && proveedorBox.isVisible()) {
                    if (proveedorBox.getSelectedItem() != null) {
                        String clienteSeleccionado = (String) proveedorBox.getSelectedItem();
                        proveedorField.setText(clienteSeleccionado); //
                        proveedorBox.setVisible(false); // Ocultar el comboBox después de seleccionar
                    }
                }
            }
        });

        proveedorBox.addActionListener(e -> {
            if (!isAdjusting && proveedorBox.getSelectedItem() != null) {
                String clienteSeleccionado = (String) proveedorBox.getSelectedItem();
                proveedorField.setText(clienteSeleccionado);
                proveedorBox.setVisible(false);
            }
        });

        proveedorField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                proveedorBox.setVisible(false);
            }
        });

        JLabel inicioLbl = new JLabel("Fecha Inicio:");
        inicioLbl.setBounds(10, 42, 100, 20);
        //dialog.add(inicioLbl);

        JTextField inicioField = new JTextField();
        inicioField.setBounds(89, 42, 186, 20);
        //dialog.add(inicioField);

        JLabel finLbl = new JLabel("Fecha Fin:");
        finLbl.setBounds(10, 67, 100, 20);
        //dialog.add(finLbl);

        JTextField finField = new JTextField();
        finField.setBounds(89, 67, 186, 20);
        //dialog.add(finField);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 98, 100, 30);
        dialog.add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(254, 98, 100, 30);
        dialog.add(btnCancelar);

        btnAceptar.addActionListener( e -> {
            JOptionPane.showMessageDialog(null,reportesController.getComprasAbiertasXProveedor(proveedorField.getText()));
            dialog.dispose();
        });

        btnCancelar.addActionListener( e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    public void comprasAbiertasXProveedorEntreFechas() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Seleccionar Producto", true);
        dialog.setBounds(100, 100, 380, 180); // Ajustar tamaño del diálogo
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JLabel clienteLbl = new JLabel("Cliente: ");
        clienteLbl.setBounds(10, 11, 100, 20);
        dialog.add(clienteLbl);

        JTextField proveedorField = new JTextField();
        proveedorField.setBounds(89, 11, 265, 20);
        dialog.add(proveedorField);

        JComboBox proveedorBox = new JComboBox();
        proveedorBox.setBounds(89, 11, 265, 20);
        proveedorBox.setVisible(false);
        dialog.add(proveedorBox);
        dialog.add(proveedorField);

        proveedorField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!isAdjusting) {
                    isAdjusting = true;
                    String busqueda = proveedorField.getText().toLowerCase();
                    proveedorBox.removeAllItems();

                    List<Proveedor> coincidencias = proveedorController.listado().stream()
                            .filter(proveedor -> (String.valueOf(proveedor.getId()).contains(busqueda)|| proveedor.getCuit().toLowerCase().contains(busqueda)
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

                // Capturar la tecla Enter
                if (e.getKeyCode() == KeyEvent.VK_ENTER && proveedorBox.isVisible()) {
                    if (proveedorBox.getSelectedItem() != null) {
                        String clienteSeleccionado = (String) proveedorBox.getSelectedItem();
                        proveedorField.setText(clienteSeleccionado); //
                        proveedorBox.setVisible(false); // Ocultar el comboBox después de seleccionar
                    }
                }
            }
        });

        proveedorBox.addActionListener(e -> {
            if (!isAdjusting && proveedorBox.getSelectedItem() != null) {
                String clienteSeleccionado = (String) proveedorBox.getSelectedItem();
                proveedorField.setText(clienteSeleccionado);
                proveedorBox.setVisible(false);
            }
        });

        proveedorField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                proveedorBox.setVisible(false);
            }
        });

        JLabel inicioLbl = new JLabel("Fecha Inicio:");
        inicioLbl.setBounds(10, 42, 100, 20);
        dialog.add(inicioLbl);

        JTextField inicioField = new JTextField();
        inicioField.setBounds(89, 42, 186, 20);
        dialog.add(inicioField);

        inicioField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(inicioField);
            }
        });

        JLabel finLbl = new JLabel("Fecha Fin:");
        finLbl.setBounds(10, 67, 100, 20);
        dialog.add(finLbl);

        JTextField finField = new JTextField();
        finField.setBounds(89, 67, 186, 20);
        dialog.add(finField);

        finField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(finField);
            }
        });

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 98, 100, 30);
        dialog.add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(254, 98, 100, 30);
        dialog.add(btnCancelar);

        btnAceptar.addActionListener( e -> {
            JOptionPane.showMessageDialog(null,reportesController.getComprasAbiertasXProveedorEntreFechas(inicioField.getText(),finField.getText(),proveedorField.getText()));
            dialog.dispose();
        });

        btnCancelar.addActionListener( e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    //Lista de Precios
    public void listaDePrecios(){
        JOptionPane.showMessageDialog(null,reportesController.getListaDePrecios());
    }

    public void historicoPrecioProducto() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Seleccionar Producto", true);
        dialog.setBounds(100, 100, 300, 180); // Ajustar tamaño del diálogo
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        // Etiqueta de proveedor
        JLabel lblProveedor = new JLabel("Producto:");
        lblProveedor.setBounds(10, 10, 80, 25); // Ajustar tamaño de la etiqueta
        dialog.add(lblProveedor);

        // Campo de texto para autocompletar
        JTextField productoField = new JTextField();
        productoField.setBounds(90, 10, 180, 25); // Mantener tamaño del campo de texto
        dialog.add(productoField);

        // ComboBox para mostrar coincidencias
        JComboBox productoBox = new JComboBox();
        productoBox.setBounds(90, 10, 180, 25); // Alinear el JComboBox con el campo de texto
        productoBox.setVisible(false);
        dialog.add(productoBox);

        // Listener para autocompletar productos
        productoField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!isAdjusting) {
                    isAdjusting = true;
                    String busqueda = productoField.getText().toLowerCase();
                    productoBox.removeAllItems();

                    List<Producto> coincidencias = productoController.listado().stream()
                            .filter(producto -> ((String.valueOf(producto.getId()).toLowerCase().contains(busqueda)|| producto.getDetalle().toLowerCase().contains(busqueda))
                                    && producto.isActivo())).collect(Collectors.toList());

                    if (!coincidencias.isEmpty()) {
                        for (Producto producto : coincidencias) {
                            productoBox.addItem(producto.getId() + " - " + producto.getDetalle());
                        }
                        productoBox.setVisible(true);
                        productoBox.showPopup();
                    } else {
                        productoBox.setVisible(false);
                    }

                    isAdjusting = false;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && productoBox.isVisible()) {
                    if (productoBox.getSelectedItem() != null) {
                        String proveedorSeleccionado = (String) productoBox.getSelectedItem();
                        productoField.setText(proveedorSeleccionado);
                        productoBox.setVisible(false);
                    }
                }
            }
        });

        // Acción para seleccionar un producto del ComboBox
        productoBox.addActionListener(e -> {
            if (!isAdjusting && productoBox.getSelectedItem() != null) {
                String productoSeleccionado = (String) productoBox.getSelectedItem();
                productoField.setText(productoSeleccionado);
                productoBox.setVisible(false);
            }
        });

        // Ocultar ComboBox cuando se pierde el foco
        productoField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                productoBox.setVisible(false);
            }
        });

        // Botón Aceptar
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(50, 100, 80, 30); // Ajustar posición de Aceptar
        dialog.add(btnAceptar);

        // Acción para mostrar mensaje con la selección del producto
        btnAceptar.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, reportesController.getHistoricoPrecio(productoField.getText()));
        });

        // Botón Cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(160, 100, 80, 30); // Ajustar posición de Cancelar
        dialog.add(btnCancelar);

        // Acción para cerrar el diálogo al hacer clic en Cancelar
        btnCancelar.addActionListener(e -> dialog.dispose());

        // Mostrar el diálogo
        dialog.setVisible(true);
    }

    //Movimientos
    public void entradas() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Seleccionar Fechas", true);
        dialog.setBounds(100, 100, 300, 180); // Ajustar tamaño del diálogo
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JLabel idLabel_1 = new JLabel("Fecha Inicio:");
        idLabel_1.setBounds(10, 20, 100, 20);
        dialog.add(idLabel_1);

        JTextField inicioField = new JTextField();
        inicioField.setBounds(89, 20, 186, 20);
        dialog.add(inicioField);

        inicioField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(inicioField);
            }
        });

        JLabel idLabel = new JLabel("Fecha Fin:");
        idLabel.setBounds(10, 67, 100, 20);
        dialog.add(idLabel);

        JTextField finField = new JTextField();
        finField.setBounds(89, 67, 186, 20);
        dialog.add(finField);

        finField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(finField);
            }
        });

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 98, 100, 30);
        dialog.add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(175, 98, 100, 30);
        dialog.add(btnCancelar);

        btnAceptar.addActionListener( e -> {
            String mensaje = reportesController.getEntradas(inicioField.getText(), finField.getText());
            if (mensaje.contains("Generado")) {
                JOptionPane.showMessageDialog(null, mensaje);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(null, mensaje);
            }
        });

        btnCancelar.addActionListener( e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    public void salidas() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Seleccionar Fechas", true);
        dialog.setBounds(100, 100, 300, 180); // Ajustar tamaño del diálogo
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JLabel idLabel_1 = new JLabel("Fecha Inicio:");
        idLabel_1.setBounds(10, 20, 100, 20);
        dialog.add(idLabel_1);

        JTextField inicioField = new JTextField();
        inicioField.setBounds(89, 20, 186, 20);
        dialog.add(inicioField);

        inicioField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(inicioField);
            }
        });

        JLabel idLabel = new JLabel("Fecha Fin:");
        idLabel.setBounds(10, 67, 100, 20);
        dialog.add(idLabel);

        JTextField finField = new JTextField();
        finField.setBounds(89, 67, 186, 20);
        dialog.add(finField);

        finField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(finField);
            }
        });

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 98, 100, 30);
        dialog.add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(175, 98, 100, 30);
        dialog.add(btnCancelar);

        btnAceptar.addActionListener( e -> {
            String mensaje = reportesController.getSalidas(inicioField.getText(), finField.getText());
            if (mensaje.contains("Generado")) {
                JOptionPane.showMessageDialog(null, mensaje);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(null, mensaje);
            }
        });

        btnCancelar.addActionListener( e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    //Cheques

    public void chequesAVencer() {
        JOptionPane.showMessageDialog(null,reportesController.getChequesXVencer());
    }
    //Seleccionador de Fecha
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
