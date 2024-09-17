package Views.Panel;

import Controller.ChequeController;
import Controller.ProveedorController;
import Model.Validaciones.Herramientas;
import com.toedter.calendar.JDateChooser;
import dominio.Proveedor;
import dominio.enums.Destino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ContabilidadPanel extends JPanel {

    private boolean isAdjusting = false;
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


    public ContabilidadPanel() {

        setLayout(null);

        JLabel lblNewLabel = new JLabel("Cheques");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setBounds(76, 11, 126, 25);
        add(lblNewLabel);

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

        createButtonC.addActionListener(e -> showCreateCheque("","","", ""));// Paso vacios, en caso de que quiera crear de 0 (ver si existe el caso)
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

    public void showCreateCheque(String idTrans,String emisor, String monto, String destinatario) {

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
        //dialog.add(btnCancelar);


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

        JTextField fechaRecepciónField = new JTextField();
        fechaRecepciónField.setColumns(10);
        fechaRecepciónField.setBounds(121, 57, 287, 20);
        dialog.add(fechaRecepciónField);

        fechaRecepciónField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDateChooserDialog(fechaRecepciónField);
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

        JCheckBox chckbxEstado = new JCheckBox("");
        chckbxEstado.setBounds(117, 256, 97, 23);
        dialog.add(chckbxEstado);

        JCheckBox chckbxActivo = new JCheckBox("");
        chckbxActivo.setBounds(117, 281, 97, 23);
        dialog.add(chckbxActivo);

        JComboBox<Destino> comboBoxDestino = new JComboBox<>(Destino.values());
        comboBoxDestino.setBounds(121, 206, 287, 22);
        dialog.add(comboBoxDestino);

        JLabel lblIdTransaccin = new JLabel("ID Transacción:");
        lblIdTransaccin.setBounds(10, 11, 101, 14);
        dialog.add(lblIdTransaccin);

        JTextField transField = new JTextField(idTrans);
        transField.setColumns(10);
        transField.setBounds(121, 8, 287, 20);
        dialog.add(transField);



        btnAceptar.addActionListener( e -> {

            String mensaje = chequeController.crear(Integer.parseInt(idTrans),fechaRecepciónField.getText(),emisorField.getText(),bancoField.getText(),nroChequeField.getText(),monto,fechaChequeField.getText(),fechaCobroField.getText(),comboBoxDestino.getSelectedItem().toString(),destinatarioField.getText(),chckbxEstado.isSelected());
            if (mensaje.equalsIgnoreCase("Cheque Generado")) {
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(null,mensaje);
            }


        });



        dialog.setVisible(true);
    }


    public void showConsultCheque() {}
    public void showModifyCheque() {}
    public void showListCheque() {}
    public void showDeleteCheque() {}

    public void showCreateMetodo () {}
    public void showConsultMetodo () {}
    public void showListMetodo () {}
    public void showDeleteMetodo () {}

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