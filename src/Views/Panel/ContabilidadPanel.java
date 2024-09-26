package Views.Panel;

import Controller.ChequeController;
import Controller.MetodoController;
import Controller.ProveedorController;
import Model.Validaciones.Herramientas;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.toedter.calendar.JDateChooser;
import dominio.Proveedor;
import dominio.enums.Destino;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ContabilidadPanel extends JPanel {

    private boolean isAdjusting = false;
    private boolean isAdjusting2 = false;
    private JButton createButtonC;
    private JButton consultButtonC;
    private JButton modifyButtonC;
    private JButton listButtonC;
    private JButton deleteButtonC;
    private JButton createButtonM;
    private JButton consultButtonM;
    private JButton listButtonM;
    private JButton deleteButtonM;
    private ChequeController chequeController = new ChequeController();
    private ProveedorController proveedorController = new ProveedorController();
    private MetodoController metodoController = new MetodoController();


    public ContabilidadPanel() {

        setLayout(null);

        JLabel lblCheques = new JLabel("Cheques");
        lblCheques.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblCheques.setBounds(76, 11, 126, 25);
        add(lblCheques);

        createButtonC = new JButton("Crear Cheque");
        createButtonC.setBounds(76, 46, 151, 44);
        add(createButtonC);

        consultButtonC = new JButton("Consultar Cheque");
        consultButtonC.setBounds(237, 46, 151, 44);
        add(consultButtonC);

        modifyButtonC = new JButton("Modificar Cheque");
        modifyButtonC.setBounds(398, 46, 151, 44);
        add(modifyButtonC);

        listButtonC = new JButton("Listar Cheques");
        listButtonC.setBounds(559, 46, 151, 44);
        add(listButtonC);

        deleteButtonC = new JButton("Eliminar Cheque");
        deleteButtonC.setBounds(720, 46, 155, 44);
        add(deleteButtonC);

        createButtonC.addActionListener(e -> showCreateCheque("","","", "", false));// Paso vacios, en caso de que quiera crear de 0 (ver si existe el caso)
        consultButtonC.addActionListener(e -> showConsultCheque());
        modifyButtonC.addActionListener(e -> showModifyCheque());
        listButtonC.addActionListener(e -> showListCheque());
        deleteButtonC.addActionListener(e -> showDeleteCheque());

        JLabel lblMtodosDePago = new JLabel("Métodos de Pago");
        lblMtodosDePago.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblMtodosDePago.setBounds(76, 158, 162, 25);
        add(lblMtodosDePago);

        createButtonM = new JButton("Crear Método");
        createButtonM.setBounds(76, 194, 151, 44);
        add(createButtonM);

        consultButtonM = new JButton("Consultar Método");
        consultButtonM.setBounds(237, 194, 151, 44);
        add(consultButtonM);

        listButtonM = new JButton("Listar Métodos");
        listButtonM.setBounds(398, 194, 151, 44);
        add(listButtonM);

        deleteButtonM = new JButton("Eliminar Método");
        deleteButtonM.setBounds(559, 194, 151, 44);
        add(deleteButtonM);

        createButtonM.addActionListener(e -> showCreateMetodo());
        consultButtonM.addActionListener(e -> showConsultMetodo());
        listButtonM.addActionListener(e -> showListMetodo());
        deleteButtonM.addActionListener(e -> showDeleteMetodo());

    }

    public void showCreateCheque(String idTrans,String emisor, String monto, String destinatario, boolean heredado) {

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Crear Cheque", true);
        dialog.setBounds(100, 100, 434, 412);
        dialog.setLayout(null); // Usar diseño nulo para posicionar manualmente los componentes
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JLabel lblEmisor = new JLabel("Emisor:");
        lblEmisor.setBounds(10, 35, 101, 14);
        dialog.add(lblEmisor);


        JLabel lblFechaRecepcion = new JLabel("Fecha Recepción:");
        lblFechaRecepcion.setBounds(10, 60, 101, 14);
        dialog.add(lblFechaRecepcion);


        JLabel lblBancoProcedencia = new JLabel("Banco Procedencia:");
        lblBancoProcedencia.setBounds(10, 85, 101, 14);
        dialog.add(lblBancoProcedencia);


        JLabel lblNroDeCheque = new JLabel("Nro. de Cheque:");
        lblNroDeCheque.setBounds(10, 110, 101, 14);
        dialog.add(lblNroDeCheque);

        JLabel lblImporte = new JLabel("Importe:");
        lblImporte.setBounds(10, 135, 101, 14);
        dialog.add(lblImporte);


        JLabel lblFechaCheque = new JLabel("Fecha Cheque:");
        lblFechaCheque.setBounds(10, 160, 101, 14);
        dialog.add(lblFechaCheque);


        JLabel lblFechaCobro = new JLabel("Fecha Cobro:");
        lblFechaCobro.setBounds(10, 185, 101, 14);
        dialog.add(lblFechaCobro);


        JLabel lblDestino = new JLabel("Destino");
        lblDestino.setBounds(10, 210, 101, 14);
        dialog.add(lblDestino);


        JLabel lblDestinatario = new JLabel("Destinatario:");
        lblDestinatario.setBounds(10, 235, 101, 14);
        dialog.add(lblDestinatario);


        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(10, 260, 101, 14);
        dialog.add(lblEstado);


        JLabel lblActivo = new JLabel("Activo:");
        lblActivo.setBounds(10, 285, 101, 14);
        dialog.add(lblActivo);


        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 329, 89, 23);
        dialog.add(btnAceptar);


        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(319, 329, 89, 23);
        if (!heredado) {
            dialog.add(btnCancelar);
        }



        JTextField emisorField = new JTextField(emisor);
        emisorField.setBounds(121, 32, 287, 20);
        dialog.add(emisorField);
        emisorField.setColumns(10);

        JComboBox proveedorBox = new JComboBox();
        proveedorBox.setBounds(121, 32, 287, 20);
        proveedorBox.setVisible(false);
        dialog.add(proveedorBox);

        // Listener para autocompletar proveedores
        emisorField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!isAdjusting) {
                    isAdjusting = true;
                    String busqueda = emisorField.getText().toLowerCase();
                    proveedorBox.removeAllItems();

                    List<Proveedor> coincidencias = proveedorController.listado().stream()
                            .filter(proveedor -> ( String.valueOf(proveedor.getId()).contains(busqueda)|| proveedor.getCuit().toLowerCase().contains(busqueda)
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
                        emisorField.setText(proveedorSeleccionado);
                        proveedorBox.setVisible(false);
                    }
                }
            }
        });

        proveedorBox.addActionListener(e -> {
            if (!isAdjusting && proveedorBox.getSelectedItem() != null) {
                String proveedorSeleccionado = (String) proveedorBox.getSelectedItem();
                emisorField.setText(proveedorSeleccionado);
                proveedorBox.setVisible(false);
            }
        });

        emisorField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                proveedorBox.setVisible(false);
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        JTextField fechaRecepcionField = new JTextField(dateFormat.format(date));
        fechaRecepcionField.setColumns(10);
        fechaRecepcionField.setBounds(121, 57, 287, 20);
        dialog.add(fechaRecepcionField);

        fechaRecepcionField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(fechaRecepcionField);
            }
        });

        JTextField bancoField = new JTextField();
        bancoField.setColumns(10);
        bancoField.setBounds(121, 82, 287, 20);
        dialog.add(bancoField);

        JTextField nroChequeField = new JTextField();
        nroChequeField.setColumns(10);
        nroChequeField.setBounds(121, 107, 287, 20);
        dialog.add(nroChequeField);

        JLabel pesosLabel = new JLabel("$");
        pesosLabel.setBounds(111,130,287,20);
        dialog.add(pesosLabel);

        JTextField importeField = new JTextField(monto);
        importeField.setColumns(10);
        importeField.setBounds(121, 132, 287, 20);
        dialog.add(importeField);

        JTextField fechaChequeField = new JTextField();
        fechaChequeField.setColumns(10);
        fechaChequeField.setBounds(121, 157, 287, 20);
        dialog.add(fechaChequeField);

        fechaChequeField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(fechaChequeField);
            }
        });

        JTextField fechaCobroField = new JTextField();
        fechaCobroField.setColumns(10);
        fechaCobroField.setBounds(121, 182, 287, 20);
        dialog.add(fechaCobroField);

        fechaCobroField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(fechaCobroField);
            }
        });

        JTextField destinatarioField = new JTextField(destinatario); // En compras = cuit comprador / En ventas = mi Cuit
        destinatarioField.setColumns(10);
        destinatarioField.setBounds(121, 232, 287, 20);
        dialog.add(destinatarioField);

        JComboBox destinatarioBox = new JComboBox();
        destinatarioBox.setBounds(121, 232, 287, 20);
        destinatarioBox.setVisible(false);
        dialog.add(destinatarioBox);

        // Listener para autocompletar proveedores
        destinatarioField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!isAdjusting2) {
                    isAdjusting2 = true;
                    String busqueda = destinatarioField.getText().toLowerCase();
                    destinatarioBox.removeAllItems();

                    List<Proveedor> coincidencias = proveedorController.listado().stream()
                            .filter(proveedor -> ( String.valueOf(proveedor.getId()).contains(busqueda)|| proveedor.getCuit().toLowerCase().contains(busqueda)
                                    || proveedor.getRazonSocial().toLowerCase().contains(busqueda))
                                    && proveedor.isActivo())
                            .collect(Collectors.toList());

                    if (!coincidencias.isEmpty()) {
                        for (Proveedor proveedor : coincidencias) {
                            destinatarioBox.addItem(proveedor.getId() + " - " + proveedor.getRazonSocial() + " - " + proveedor.getCuit());
                        }
                        destinatarioBox.setVisible(true);
                        destinatarioBox.showPopup();
                    } else {
                        destinatarioBox.setVisible(false);
                    }

                    isAdjusting2 = false;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && destinatarioBox.isVisible()) {
                    if (destinatarioBox.getSelectedItem() != null) {
                        String proveedorSeleccionado = (String) destinatarioBox.getSelectedItem();
                        destinatarioField.setText(proveedorSeleccionado);
                        destinatarioBox.setVisible(false);
                    }
                }
            }
        });

        destinatarioBox.addActionListener(e -> {
            if (!isAdjusting2 && destinatarioBox.getSelectedItem() != null) {
                String proveedorSeleccionado = (String) destinatarioBox.getSelectedItem();
                destinatarioField.setText(proveedorSeleccionado);
                destinatarioBox.setVisible(false);
            }
        });

        destinatarioField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                destinatarioBox.setVisible(false);
            }
        });

        JCheckBox chckbxEstado = new JCheckBox("");
        chckbxEstado.setBounds(117, 256, 97, 23);
        dialog.add(chckbxEstado);

        JCheckBox chckbxActivo = new JCheckBox("");
        chckbxActivo.setBounds(117, 281, 97, 23);
        //dialog.add(chckbxActivo);

        JComboBox<Destino> comboBoxDestino = new JComboBox<>(Destino.values());
        comboBoxDestino.setBounds(121, 206, 287, 22);
        dialog.add(comboBoxDestino);

        JLabel lblIdTransaccion = new JLabel("ID Transacción:");
        lblIdTransaccion.setBounds(10, 11, 101, 14);
        dialog.add(lblIdTransaccion);

        JTextField transField = new JTextField(idTrans);
        transField.setColumns(10);
        transField.setBounds(121, 8, 287, 20);
        if (heredado) {
            transField.setEditable(false);
        }
        dialog.add(transField);

        btnAceptar.addActionListener( e -> {

            String mensaje = chequeController.crear(Integer.parseInt(idTrans),fechaRecepcionField.getText(),emisorField.getText(),bancoField.getText(),nroChequeField.getText(),monto,fechaChequeField.getText(),fechaCobroField.getText(),comboBoxDestino.getSelectedItem().toString(),destinatarioField.getText(),chckbxEstado.isSelected());
            if (mensaje.equalsIgnoreCase("Cheque Generado")) {
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(null,mensaje);
            }


        });

        dialog.setVisible(true);
    }
    public void showConsultCheque() {

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Crear Cheque", true);
        dialog.setBounds(100, 100, 434, 400);  // Aumenta el tamaño para acomodar todos los componentes
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        // ID Label y TextField agregados al principio
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(10, 10, 101, 14);  // Posicionado al principio
        dialog.add(lblId);

        JTextField idField = new JTextField();
        idField.setBounds(121, 7, 287, 20);  // Debajo del ID Label
        dialog.add(idField);

        // ID Transacción Label y TextField agregados debajo de ID
        JLabel lblIdTransaccion = new JLabel("ID Transacción:");
        lblIdTransaccion.setBounds(10, 35, 101, 14);  // Debajo del ID
        dialog.add(lblIdTransaccion);

        JTextField idTransField = new JTextField();
        idTransField.setBounds(121, 32, 287, 20);
        dialog.add(idTransField);


        JLabel lblEmisor = new JLabel("Emisor:");
        lblEmisor.setBounds(10, 60, 101, 14);
        dialog.add(lblEmisor);

        JTextField emisorField = new JTextField();
        emisorField.setBounds(121, 57, 287, 20);
        dialog.add(emisorField);

        JLabel lblFechaRecepcion = new JLabel("Fecha Recepción:");
        lblFechaRecepcion.setBounds(10, 85, 101, 14);
        dialog.add(lblFechaRecepcion);

        JTextField fechaRecepciónField = new JTextField();
        fechaRecepciónField.setBounds(121, 82, 287, 20);
        dialog.add(fechaRecepciónField);

        JLabel lblBancoProcedencia = new JLabel("Banco Procedencia:");
        lblBancoProcedencia.setBounds(10, 110, 101, 14);
        dialog.add(lblBancoProcedencia);

        JTextField bancoField = new JTextField();
        bancoField.setBounds(121, 107, 287, 20);
        dialog.add(bancoField);

        JLabel lblNroDeCheque = new JLabel("Nro. de Cheque:");
        lblNroDeCheque.setBounds(10, 135, 101, 14);
        dialog.add(lblNroDeCheque);

        JTextField nroChequeField = new JTextField();
        nroChequeField.setBounds(121, 132, 287, 20);
        dialog.add(nroChequeField);

        JLabel lblImporte = new JLabel("Importe:");
        lblImporte.setBounds(10, 160, 101, 14);
        dialog.add(lblImporte);

        JLabel pesosLabel = new JLabel("$");
        pesosLabel.setBounds(111, 157, 10, 20);  // Ajuste pequeño para el símbolo de moneda
        dialog.add(pesosLabel);

        JTextField importeField = new JTextField();
        importeField.setBounds(121, 157, 287, 20);
        dialog.add(importeField);

        JLabel lblFechaCheque = new JLabel("Fecha Cheque:");
        lblFechaCheque.setBounds(10, 185, 101, 14);
        dialog.add(lblFechaCheque);

        JTextField fechaChequeField = new JTextField();
        fechaChequeField.setBounds(121, 182, 287, 20);
        dialog.add(fechaChequeField);

        JLabel lblFechaCobro = new JLabel("Fecha Cobro:");
        lblFechaCobro.setBounds(10, 210, 101, 14);
        dialog.add(lblFechaCobro);

        JTextField fechaCobroField = new JTextField();
        fechaCobroField.setBounds(121, 207, 287, 20);
        dialog.add(fechaCobroField);

        JLabel lblDestino = new JLabel("Destino:");
        lblDestino.setBounds(10, 235, 101, 14);
        dialog.add(lblDestino);

        JComboBox<Destino> comboBoxDestino = new JComboBox<>(Destino.values());
        comboBoxDestino.setBounds(121, 232, 287, 22);
        dialog.add(comboBoxDestino);

        JLabel lblDestinatario = new JLabel("Destinatario:");
        lblDestinatario.setBounds(10, 260, 101, 14);
        dialog.add(lblDestinatario);

        JTextField destinatarioField = new JTextField();
        destinatarioField.setBounds(121, 257, 287, 20);
        dialog.add(destinatarioField);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(10, 285, 101, 14);
        dialog.add(lblEstado);

        JCheckBox chckbxEstado = new JCheckBox();
        chckbxEstado.setBounds(121, 281, 97, 23);
        dialog.add(chckbxEstado);

        JLabel lblActivo = new JLabel("Activo:");
        lblActivo.setBounds(10, 310, 101, 14);
        dialog.add(lblActivo);

        JCheckBox chckbxActivo = new JCheckBox();
        chckbxActivo.setBounds(121, 306, 97, 23);
        dialog.add(chckbxActivo);

        JButton btnAceptar = new JButton("Buscar");
        btnAceptar.setBounds(10, 340, 89, 23);
        dialog.add(btnAceptar);

        JButton btnCancelar = new JButton("Salir");
        btnCancelar.setBounds(319, 340, 89, 23);
        dialog.add(btnCancelar);

        btnAceptar.addActionListener(e -> {
            String datos [] = chequeController.consultar(idField.getText());

            if(datos.length == 1 ) {
                idField.setText(datos[0]);
                idTransField.setText("");
                idTransField.setEditable(false);
                fechaRecepciónField.setText("");
                fechaRecepciónField.setEditable(false);
                emisorField.setText("");
                emisorField.setEditable(false);
                bancoField.setText("");
                bancoField.setEditable(false);
                nroChequeField.setText("");
                nroChequeField.setEditable(false);
                importeField.setText("");
                importeField.setEditable(false);
                fechaChequeField.setText("");
                fechaChequeField.setEditable(false);
                fechaCobroField.setText("");
                fechaCobroField.setEditable(false);
                destinatarioField.setText("");
                destinatarioField.setEditable(false);
                chckbxEstado.setSelected(false);
                chckbxEstado.setEnabled(false);
                chckbxActivo.setSelected(false);
                chckbxActivo.setEnabled(false);
            } else {
                idTransField.setText(datos[0]);
                idTransField.setEditable(false);
                fechaRecepciónField.setText(datos[1] );
                fechaRecepciónField.setEditable(false);
                emisorField.setText(datos[2] + " - " + datos [3]);
                emisorField.setEditable(false);
                bancoField.setText(datos[4]);
                bancoField.setEditable(false);
                nroChequeField.setText(datos[5]);
                nroChequeField.setEditable(false);
                importeField.setText(datos[6]);
                importeField.setEditable(false);
                fechaChequeField.setText(datos[7]);
                fechaChequeField.setEditable(false);
                fechaCobroField.setText(datos[8]);
                fechaCobroField.setEditable(false);
                comboBoxDestino.setSelectedItem(Destino.valueOf(datos[9]));
                comboBoxDestino.setEditable(false);
                destinatarioField.setText(datos[10]);
                destinatarioField.setEditable(false);
                chckbxEstado.setSelected(Boolean.parseBoolean(datos[11]));
                chckbxEstado.setEnabled(false);
                chckbxActivo.setSelected(Boolean.parseBoolean(datos[12]));
                chckbxActivo.setEnabled(false);
            }
        });
        btnCancelar.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }
    public void showModifyCheque() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Crear Cheque", true);
        dialog.setBounds(100, 100, 434, 400);  // Aumenta el tamaño para acomodar todos los componentes
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        // ID Label y TextField agregados al principio
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(10, 10, 101, 14);  // Posicionado al principio
        dialog.add(lblId);

        JTextField idField = new JTextField();
        idField.setBounds(121, 7, 287, 20);  // Debajo del ID Label
        dialog.add(idField);

        // ID Transacción Label y TextField agregados debajo de ID
        JLabel lblIdTransaccion = new JLabel("ID Transacción:");
        lblIdTransaccion.setBounds(10, 35, 101, 14);  // Debajo del ID
        dialog.add(lblIdTransaccion);

        JTextField idTransField = new JTextField();
        idTransField.setBounds(121, 32, 287, 20);
        dialog.add(idTransField);


        JLabel lblEmisor = new JLabel("Emisor:");
        lblEmisor.setBounds(10, 60, 101, 14);
        dialog.add(lblEmisor);

        JTextField emisorField = new JTextField();
        emisorField.setBounds(121, 57, 287, 20);
        dialog.add(emisorField);

        JLabel lblFechaRecepcion = new JLabel("Fecha Recepción:");
        lblFechaRecepcion.setBounds(10, 85, 101, 14);
        dialog.add(lblFechaRecepcion);

        JTextField fechaRecepcionField = new JTextField();
        fechaRecepcionField.setBounds(121, 82, 287, 20);
        dialog.add(fechaRecepcionField);

        JLabel lblBancoProcedencia = new JLabel("Banco Procedencia:");
        lblBancoProcedencia.setBounds(10, 110, 101, 14);
        dialog.add(lblBancoProcedencia);

        JTextField bancoField = new JTextField();
        bancoField.setBounds(121, 107, 287, 20);
        dialog.add(bancoField);

        JLabel lblNroDeCheque = new JLabel("Nro. de Cheque:");
        lblNroDeCheque.setBounds(10, 135, 101, 14);
        dialog.add(lblNroDeCheque);

        JTextField nroChequeField = new JTextField();
        nroChequeField.setBounds(121, 132, 287, 20);
        dialog.add(nroChequeField);

        JLabel lblImporte = new JLabel("Importe:");
        lblImporte.setBounds(10, 160, 101, 14);
        dialog.add(lblImporte);

        JLabel pesosLabel = new JLabel("$");
        pesosLabel.setBounds(111, 157, 10, 20);  // Ajuste pequeño para el símbolo de moneda
        dialog.add(pesosLabel);

        JTextField importeField = new JTextField();
        importeField.setBounds(121, 157, 287, 20);
        dialog.add(importeField);

        JLabel lblFechaCheque = new JLabel("Fecha Cheque:");
        lblFechaCheque.setBounds(10, 185, 101, 14);
        dialog.add(lblFechaCheque);

        JTextField fechaChequeField = new JTextField();
        fechaChequeField.setBounds(121, 182, 287, 20);
        dialog.add(fechaChequeField);

        JLabel lblFechaCobro = new JLabel("Fecha Cobro:");
        lblFechaCobro.setBounds(10, 210, 101, 14);
        dialog.add(lblFechaCobro);

        JTextField fechaCobroField = new JTextField();
        fechaCobroField.setBounds(121, 207, 287, 20);
        dialog.add(fechaCobroField);

        JLabel lblDestino = new JLabel("Destino:");
        lblDestino.setBounds(10, 235, 101, 14);
        dialog.add(lblDestino);

        JComboBox<Destino> comboBoxDestino = new JComboBox<>(Destino.values());
        comboBoxDestino.setBounds(121, 232, 287, 22);
        dialog.add(comboBoxDestino);

        JLabel lblDestinatario = new JLabel("Destinatario:");
        lblDestinatario.setBounds(10, 260, 101, 14);
        dialog.add(lblDestinatario);

        JTextField destinatarioField = new JTextField();
        destinatarioField.setBounds(121, 257, 287, 20);
        dialog.add(destinatarioField);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(10, 285, 101, 14);
        dialog.add(lblEstado);

        JCheckBox chckbxEstado = new JCheckBox();
        chckbxEstado.setBounds(121, 281, 97, 23);
        dialog.add(chckbxEstado);

        JLabel lblActivo = new JLabel("Activo:");
        lblActivo.setBounds(10, 310, 101, 14);
        dialog.add(lblActivo);

        JCheckBox chckbxActivo = new JCheckBox();
        chckbxActivo.setBounds(121, 306, 97, 23);
        dialog.add(chckbxActivo);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 340, 89, 23);
        dialog.add(btnAceptar);

        JButton btnCancelar = new JButton("Salir");
        btnCancelar.setBounds(319, 340, 89, 23);
        dialog.add(btnCancelar);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(132,340,89,23);
        dialog.add(btnBuscar);

        fechaRecepcionField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(fechaRecepcionField);
            }
        });

        fechaChequeField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(fechaChequeField);
            }
        });

        fechaCobroField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(fechaCobroField);
            }
        });

        btnBuscar.addActionListener( e -> {
            String datos [] = chequeController.consultar(idField.getText());
            if(datos.length == 1 ) {
                idField.setText(datos[0]);
                idTransField.setText("");
                fechaRecepcionField.setText("");
                emisorField.setText("");
                bancoField.setText("");
                nroChequeField.setText("");
                importeField.setText("");
                fechaChequeField.setText("");
                fechaCobroField.setText("");
                destinatarioField.setText("");
                chckbxEstado.setSelected(false);
                chckbxActivo.setSelected(false);
            } else {
                idField.setEditable(false);
                idTransField.setText(datos[0]);
                idTransField.setEditable(false);
                fechaRecepcionField.setText(Herramientas.convertirFecha(datos[1]));
                //fechaRecepciónField.setEditable(false);
                emisorField.setText(datos[2] + " - " + datos [3]);
                emisorField.setEditable(false);
                bancoField.setText(datos[4]);
                //bancoField.setEditable(false);
                nroChequeField.setText(datos[5]);
                nroChequeField.setEditable(false);
                importeField.setText(datos[6]);
                importeField.setEditable(false);
                fechaChequeField.setText(Herramientas.convertirFecha(datos[7]));
                //fechaChequeField.setEditable(false);
                fechaCobroField.setText(Herramientas.convertirFecha(datos[8]));
                //fechaCobroField.setEditable(false);
                comboBoxDestino.setSelectedItem(Destino.valueOf(datos[9]));
                comboBoxDestino.setEditable(false);
                destinatarioField.setText(datos[10]);
                destinatarioField.setEditable(false);
                chckbxEstado.setSelected(Boolean.parseBoolean(datos[11]));
                //chckbxEstado.setEnabled(false);
                chckbxActivo.setSelected(Boolean.parseBoolean(datos[12]));
                chckbxActivo.setEnabled(false);
            }
        });

        btnAceptar.addActionListener(e -> {
            String mensaje = chequeController.modificar(idField.getText(),fechaRecepcionField.getText(), bancoField.getText(), fechaChequeField.getText(), fechaCobroField.getText(), chckbxEstado.isSelected());
            JOptionPane.showMessageDialog(null, mensaje);
            if (mensaje.contains("Actualizado")) {
                dialog.dispose();
            }
        });

        btnCancelar.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }
    public void showListCheque() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Lista de Ventas", true);
        dialog.setBounds(100, 100, 800, 600);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        DefaultTableModel tableModel = chequeController.listar();
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
    public void showDeleteCheque() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Baja Cheque", true);
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
            JOptionPane.showMessageDialog(null, chequeController.eliminar(idField.getText()));
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    public void showCreateMetodo () {

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Crear Método", true);

        dialog.setBounds(100, 100, 323, 176);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JLabel lblIdMetodo = new JLabel("ID Método:");
        lblIdMetodo.setBounds(10, 11, 123, 14);
        //dialog.add(lblIdMetodo);

        JLabel lblMtodoDePago = new JLabel("Método de Pago:");
        lblMtodoDePago.setBounds(10, 36, 123, 14);
        dialog.add(lblMtodoDePago);

        //JLabel lblActivo = new JLabel("Activo:");
        //lblActivo.setBounds(10, 61, 123, 14);
        //dialog.add(lblActivo);

        JTextField idField = new JTextField();
        idField.setBounds(143, 8, 155, 20);
        //dialog.add(idField);
        idField.setColumns(10);

        JTextField metodoField = new JTextField();
        metodoField.setColumns(10);
        metodoField.setBounds(143, 33, 155, 20);
        dialog.add(metodoField);

        //JCheckBox chckbxActivo = new JCheckBox("");
        //chckbxActivo.setBounds(141, 57, 97, 23);
        //dialog.add(chckbxActivo);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 106, 89, 23);
        dialog.add(btnAceptar);

        //JButton btnBuscar = new JButton("Buscar");
        //btnBuscar.setBounds(110, 106, 89, 23);
        //dialog.add(btnBuscar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(209, 106, 89, 23);
        dialog.add(btnCancelar);

        btnAceptar.addActionListener(e -> {
            String mensaje = metodoController.crear(metodoField.getText());
            JOptionPane.showMessageDialog(null, mensaje);
            if (mensaje.contains("Creado")) {
                dialog.dispose();
            }
        });

        btnCancelar.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }
    public void showConsultMetodo () {

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Consultar Método", true);

        dialog.setBounds(100, 100, 323, 176);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JLabel lblIdMetodo = new JLabel("ID Método:");
        lblIdMetodo.setBounds(10, 11, 123, 14);
        dialog.add(lblIdMetodo);

        JLabel lblMtodoDePago = new JLabel("Método de Pago:");
        lblMtodoDePago.setBounds(10, 36, 123, 14);
        dialog.add(lblMtodoDePago);

        JLabel lblActivo = new JLabel("Activo:");
        lblActivo.setBounds(10, 61, 123, 14);
        dialog.add(lblActivo);

        JTextField idField = new JTextField();
        idField.setBounds(143, 8, 155, 20);
        dialog.add(idField);
        idField.setColumns(10);

        JTextField metodoField = new JTextField();
        metodoField.setColumns(10);
        metodoField.setBounds(143, 33, 155, 20);
        dialog.add(metodoField);

        JCheckBox chckbxActivo = new JCheckBox("");
        chckbxActivo.setBounds(141, 57, 97, 23);
        dialog.add(chckbxActivo);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 106, 89, 23);
        //dialog.add(btnAceptar);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(10, 106, 89, 23);
        dialog.add(btnBuscar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(209, 106, 89, 23);
        dialog.add(btnCancelar);

        btnBuscar.addActionListener(e -> {
            String datos [] = metodoController.consultar(idField.getText());
            if(datos.length == 1 ) {
                idField.setText(datos[0]);
                metodoField.setText("");
                metodoField.setEditable(false);
                chckbxActivo.setSelected(false);
                chckbxActivo.setEnabled(false);
            } else {
                metodoField.setText(datos[0]);
                metodoField.setEditable(false);
                chckbxActivo.setSelected(Boolean.parseBoolean(datos[1]));
                chckbxActivo.setEnabled(false);
            }
        });

        btnCancelar.addActionListener( e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }
    public void showListMetodo () {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Lista de Ventas", true);
        dialog.setBounds(100, 100, 800, 600);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        DefaultTableModel tableModel = metodoController.listar();
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
    public void showDeleteMetodo () {
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
            JOptionPane.showMessageDialog(null, metodoController.eliminar(idField.getText()));
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