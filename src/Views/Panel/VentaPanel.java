package Views.Panel;

import Controller.DetalleVentaController;
import Model.Auxiliares.ListadoProductos;
import Views.Dialog.DetalleVentaDialog;
import Views.Interfaces.PanelInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentaPanel extends GeneralPanel implements PanelInterface {

    private List<ListadoProductos> listadoProductos = new ArrayList<>();
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_3;
    private JTextField textField_5;
    private JTextField textField_6;
    private DetalleVentaController controller = new DetalleVentaController();

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

        JCheckBox chckbxNewCheckBox = new JCheckBox("Pagada");
        chckbxNewCheckBox.setBounds(6, 241, 97, 23);
        dialog.add(chckbxNewCheckBox);

        JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Completa");
        chckbxNewCheckBox_1.setBounds(6, 267, 97, 23);
        dialog.add(chckbxNewCheckBox_1);

        JCheckBox chckbxNewCheckBox_2 = new JCheckBox("Entregada");
        chckbxNewCheckBox_2.setBounds(6, 293, 97, 23);
        dialog.add(chckbxNewCheckBox_2);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 333, 89, 23);
        dialog.add(btnAceptar);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(109, 333, 89, 23);
        dialog.add(btnVerificar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(289, 333, 89, 23);
        dialog.add(btnCancelar);


        JComboBox comboBox_1 = new JComboBox();
        comboBox_1.setBounds(164, 7, 220, 22);
        dialog.add(comboBox_1);

        textField_1 = new JTextField();
        textField_1.setBounds(164, 33, 220, 20);
        dialog.add(textField_1);
        textField_1.setColumns(10);

        JTextField textField_2 = new JTextField();
        textField_2.setBounds(164, 58, 220, 20);
        dialog.add(textField_2);
        textField_2.setColumns(10);

        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(164, 82, 220, 22);
        dialog.add(comboBox);

        JTextField textField_4 = new JTextField();
        textField_4.setBounds(164, 108, 220, 20);
        dialog.add(textField_4);
        textField_4.setColumns(10);

        JComboBox comboBox_2 = new JComboBox();
        comboBox_2.setBounds(164, 132, 220, 22);
        dialog.add(comboBox_2);

        textField_6 = new JTextField();
        textField_6.setBounds(164, 158, 220, 20);
        dialog.add(textField_6);
        textField_6.setColumns(10);

        JTextField textField_7 = new JTextField();
        textField_7.setBounds(164, 217, 220, 20);
        dialog.add(textField_7);
        textField_7.setColumns(10);

        // Acción al presionar 'Productos'
        btnDetalleVenta.addActionListener(e -> {
            DetalleVentaDialog detalleDialog = new DetalleVentaDialog((Frame) SwingUtilities.getWindowAncestor(VentaPanel.this), listadoProductos);
            detalleDialog.setVisible(true);
            listadoProductos = detalleDialog.getListadoProductos();

            //Limpiar listadoProductos una vez que cree la venta o la cancele
        });

        // Acción al presionar 'Verificar'
        btnVerificar.addActionListener(e -> {
            //DetalleVentaDialog detalleDialog = new DetalleVentaDialog((Frame) SwingUtilities.getWindowAncestor(VentaPanel.this), listadoProductos);
            //listadoProductos = detalleDialog.getListadoProductos();

            textField_7.setText("$" + controller.totalVenta(listadoProductos));
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
