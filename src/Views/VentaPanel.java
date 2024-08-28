package Views;

import Views.Interfaces.PanelInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class VentaPanel extends GeneralPanel implements PanelInterface {

    DetalleVentaPanel detalle = new DetalleVentaPanel();
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_3;
    private JTextField textField_5;
    private JTextField textField_6;


    public VentaPanel(String boton1, String boton2, String boton3, String boton4 , String boton5) {
        super(boton1, boton2, boton3, boton4, boton5);

        super.getCreateButton().addActionListener(e -> showCreate());
        super.getModifyButton().addActionListener(e -> showModify());
        super.consultButton.addActionListener(e -> showGet());
        super.getListButton().addActionListener(e -> showList());
        super.deleteButton.addActionListener(e -> showDelete());
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
        btnDetalleVenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
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

        JButton btnNewButton = new JButton("Aceptar");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnNewButton.setBounds(10, 333, 89, 23);
        dialog.add(btnNewButton);

        textField = new JTextField();
        textField.setBounds(158, 33, 230, 20);
        dialog.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(158, 58, 230, 20);
        dialog.add(textField_1);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(158, 108, 230, 20);
        dialog.add(textField_3);

        textField_5 = new JTextField();
        textField_5.setColumns(10);
        textField_5.setBounds(158, 158, 230, 20);
        dialog.add(textField_5);

        textField_6 = new JTextField();
        textField_6.setColumns(10);
        textField_6.setBounds(158, 217, 230, 20);
        dialog.add(textField_6);

        JButton btnNewButton_1 = new JButton("Cancelar");
        btnNewButton_1.setBounds(299, 333, 89, 23);
        dialog.add(btnNewButton_1);

        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(158, 82, 230, 22);
        dialog.add(comboBox);

        JComboBox comboBox_1 = new JComboBox();
        comboBox_1.setBounds(158, 7, 230, 22);
        dialog.add(comboBox_1);

        JComboBox comboBox_2 = new JComboBox();
        comboBox_2.setBounds(158, 132, 230, 22);
        dialog.add(comboBox_2);
        dialog.setVisible(true);



    }

    @Override
    public void showGet() {

    }

    @Override
    public void showModify() {

    }

    @Override
    public void showList() {

    }

    @Override
    public void showDelete() {

    }
}
