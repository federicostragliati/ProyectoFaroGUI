package Views.Dialog;

import Controller.UsuarioController;
import dominio.enums.TipoUsuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class UsuariosDialog {

    private UsuarioController usuarioController = new UsuarioController();

    public UsuariosDialog() {

    }

    public void CrearUsuario(Frame owner) {

        JTextField usuarioField;
        JTextField nombreField;
        JPasswordField passwordField;
        JPasswordField confirmacionField;

        JDialog dialog = new JDialog(owner, "Crear Usuario", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel contentPanel = new JPanel();

        dialog.setBounds(100, 100, 372, 209);
        dialog.getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        dialog.getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JLabel lblUsuario = new JLabel("Usuario:");
            lblUsuario.setBounds(10, 11, 127, 14);
            contentPanel.add(lblUsuario);
        }
        {
            JLabel lblNombre = new JLabel("Nombre:");
            lblNombre.setBounds(10, 36, 127, 14);
            contentPanel.add(lblNombre);
        }
        {
            JLabel lblContraseña = new JLabel("Contraseña:");
            lblContraseña.setBounds(10, 61, 127, 14);
            contentPanel.add(lblContraseña);
        }
        {
            JLabel lblConfirmacion = new JLabel("Confirmar Contraseña:");
            lblConfirmacion.setBounds(10, 86, 127, 14);
            contentPanel.add(lblConfirmacion);
        }
        {
            JLabel lblTipoUsuario = new JLabel("Tipo Usuario:");
            lblTipoUsuario.setBounds(10, 111, 127, 14);
            contentPanel.add(lblTipoUsuario);
        }

        usuarioField = new JTextField();
        usuarioField.setBounds(136, 8, 215, 20);
        contentPanel.add(usuarioField);
        usuarioField.setColumns(10);

        nombreField = new JTextField();
        nombreField.setColumns(10);
        nombreField.setBounds(136, 33, 215, 20);
        contentPanel.add(nombreField);

        passwordField = new JPasswordField();
        passwordField.setBounds(136, 58, 215, 20);
        contentPanel.add(passwordField);

        confirmacionField = new JPasswordField();
        confirmacionField.setBounds(136, 83, 215, 20);
        contentPanel.add(confirmacionField);

        JComboBox<TipoUsuario> comboBox = new JComboBox<>(TipoUsuario.values());
        comboBox.setBounds(136, 107, 215, 22);
        contentPanel.add(comboBox);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 140, 89, 23);
        contentPanel.add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(262, 140, 89, 23);
        contentPanel.add(btnCancelar);

        btnAceptar.addActionListener( e -> {

            TipoUsuario tipoUsuarioSeleccionado = (TipoUsuario) comboBox.getSelectedItem();

            String mensaje = usuarioController.crear(usuarioField.getText(), nombreField.getText(), Arrays.toString(passwordField.getPassword()) , Arrays.toString(confirmacionField.getPassword()),  tipoUsuarioSeleccionado.name());

            if (mensaje.contains("Generado")) {
                JOptionPane.showMessageDialog(null, mensaje);
            } else {
                JOptionPane.showMessageDialog(null, mensaje);
            }

        });

        dialog.setVisible(true);
    }

    public void ModificarUsuario() {}

    public void EliminarUsuario() {}
}
