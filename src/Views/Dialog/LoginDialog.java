package Views.Dialog;
import Controller.UsuarioController;
import Views.Frame.AdministradorFrame;
import Views.Frame.VendedorFrame;
import dominio.Usuario;
import dominio.enums.TipoUsuario;

import java.awt.BorderLayout;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JPasswordField passwordField;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private Usuario usuario;
    private UsuarioController usuarioController = new UsuarioController();

    /**
     * Launch the application.
     */

    public LoginDialog() {

        setTitle("Login Sistema de Gestión Faro");
        setResizable(false);
        setBounds(100, 100, 257, 149);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(10, 14, 46, 14);
        contentPanel.add(lblUsuario);


        JTextField usuarioField = new JTextField();
        usuarioField.setBounds(76, 11, 145, 20);
        contentPanel.add(usuarioField);
        usuarioField.setColumns(10);


        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(10, 42, 69, 14);
        contentPanel.add(lblPassword);


        passwordField = new JPasswordField();
        passwordField.setBounds(76, 39, 145, 20);
        contentPanel.add(passwordField);


        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 80, 89, 23);
        contentPanel.add(btnAceptar);


        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(132, 80, 89, 23);
        contentPanel.add(btnCancelar);


        btnAceptar.addActionListener(e -> {
            usuario = usuarioController.autenticar(usuarioField.getText(), Arrays.toString(passwordField.getPassword()));
            if (usuario == null) {
                JOptionPane.showMessageDialog(null, "Usuario o Contraseña Incorrecta");
            } else {
                dispose();
                if (usuario != null && usuario.getTipoUsuario().equals(TipoUsuario.Administrador)) {
                    SwingUtilities.invokeLater(() -> {
                        AdministradorFrame administradorFrame = new AdministradorFrame();
                        administradorFrame.setVisible(true);
                    });
                } else if (usuario != null && usuario.getTipoUsuario().equals(TipoUsuario.Vendedor)) {
                    SwingUtilities.invokeLater(() -> {
                        VendedorFrame vendedorFrame = new VendedorFrame();
                        vendedorFrame.setVisible(true);
                    });
                }
            }
        });

        btnCancelar.addActionListener(e -> {
            dispose();
        });

    }

}
