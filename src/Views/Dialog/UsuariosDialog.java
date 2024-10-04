package Views.Dialog;

import Controller.UsuarioController;
import Model.Validaciones.Herramientas;
import dominio.Cliente;
import dominio.Usuario;
import dominio.enums.TipoUsuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UsuariosDialog {

    private UsuarioController usuarioController = new UsuarioController();
    private boolean isAdjusting = false;

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

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(10, 11, 127, 14);
        contentPanel.add(lblUsuario);


        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(10, 36, 127, 14);
        contentPanel.add(lblNombre);


        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setBounds(10, 61, 127, 14);
        contentPanel.add(lblContraseña);


        JLabel lblConfirmacion = new JLabel("Confirmar Contraseña:");
        lblConfirmacion.setBounds(10, 86, 127, 14);
        contentPanel.add(lblConfirmacion);


        JLabel lblTipoUsuario = new JLabel("Tipo Usuario:");
        lblTipoUsuario.setBounds(10, 111, 127, 14);
        contentPanel.add(lblTipoUsuario);


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
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(null, mensaje);
            }

        });
        btnCancelar.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    public void ModificarUsuario(Frame owner) {
        JTextField usuarioField;
        JTextField nombreField;
        JPasswordField passwordField;
        JPasswordField confirmacionField;
        JTextField busquedaField;

        JDialog dialog = new JDialog(owner, "Modificar Usuario", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setBounds(100, 100, 372, 300);
        dialog.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.setLayout(null);
        dialog.add(contentPanel, BorderLayout.CENTER);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(10, 39, 127, 14);
        contentPanel.add(lblUsuario);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(10, 64, 127, 14);
        contentPanel.add(lblNombre);

        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setBounds(10, 89, 127, 14);
        contentPanel.add(lblContraseña);

        JLabel lblConfirmacion = new JLabel("Confirmar Contraseña:");
        lblConfirmacion.setBounds(10, 114, 127, 14);
        contentPanel.add(lblConfirmacion);

        JLabel lblTipoUsuario = new JLabel("Tipo Usuario:");
        lblTipoUsuario.setBounds(10, 139, 127, 14);
        contentPanel.add(lblTipoUsuario);

        usuarioField = new JTextField();
        usuarioField.setBounds(136, 36, 215, 20);
        contentPanel.add(usuarioField);
        usuarioField.setColumns(10);

        nombreField = new JTextField();
        nombreField.setColumns(10);
        nombreField.setBounds(136, 61, 215, 20);
        contentPanel.add(nombreField);

        passwordField = new JPasswordField();
        passwordField.setBounds(136, 86, 215, 20);
        contentPanel.add(passwordField);

        confirmacionField = new JPasswordField();
        confirmacionField.setBounds(136, 111, 215, 20);
        contentPanel.add(confirmacionField);

        JComboBox<TipoUsuario> comboBox = new JComboBox<>(TipoUsuario.values());
        comboBox.setBounds(136, 135, 215, 22);
        contentPanel.add(comboBox);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 230, 89, 23);
        contentPanel.add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(262, 230, 89, 23);
        contentPanel.add(btnCancelar);

        JLabel lblBusqueda = new JLabel("Busqueda:");
        lblBusqueda.setBounds(10, 14, 127, 14);
        contentPanel.add(lblBusqueda);

        busquedaField = new JTextField();
        busquedaField.setColumns(10);
        busquedaField.setBounds(136, 11, 215, 20);
        contentPanel.add(busquedaField);

        JComboBox<String> busquedaBox = new JComboBox<>();
        busquedaBox.setBounds(136, 11, 215, 20);
        busquedaBox.setVisible(false);
        contentPanel.add(busquedaBox);

        busquedaField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!isAdjusting) {
                    isAdjusting = true;
                    String busqueda = busquedaField.getText().toLowerCase();
                    busquedaBox.removeAllItems();

                    List<Usuario> coincidencias = usuarioController.listar().stream()
                            .filter(usuario -> (String.valueOf(usuario.getId()).contains(busqueda) ||
                                    usuario.getNombre().toLowerCase().contains(busqueda) ||
                                    usuario.getNombreUsuario().toLowerCase().contains(busqueda))
                                    && usuario.isActivo())
                            .collect(Collectors.toList());

                    if (!coincidencias.isEmpty()) {
                        for (Usuario usuario : coincidencias) {
                            busquedaBox.addItem(usuario.getId() + " - " + usuario.getNombre() + " - " + usuario.getNombreUsuario());
                        }
                        busquedaBox.setVisible(true);
                        busquedaBox.showPopup();
                    } else {
                        busquedaBox.setVisible(false);
                    }

                    isAdjusting = false;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && busquedaBox.isVisible()) {
                    if (busquedaBox.getSelectedItem() != null) {
                        String clienteSeleccionado = (String) busquedaBox.getSelectedItem();
                        busquedaField.setText(clienteSeleccionado);
                        busquedaBox.setVisible(false);
                    }
                }
            }
        });

        busquedaBox.addActionListener(e -> {
            if (!isAdjusting && busquedaBox.getSelectedItem() != null) {
                String clienteSeleccionado = (String) busquedaBox.getSelectedItem();
                busquedaField.setText(clienteSeleccionado);
                busquedaBox.setVisible(false);
            }
        });

        busquedaField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                busquedaBox.setVisible(false);
            }
        });

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(136, 230, 89, 23);
        contentPanel.add(btnBuscar);

        btnBuscar.addActionListener(e -> {
            String datos[] = usuarioController.consultar(busquedaField.getText());
            if (datos.length == 1) {
                usuarioField.setText(datos[0]);
            } else {
                usuarioField.setText(datos[0]);
                nombreField.setText(datos[1]);
                passwordField.setText(datos[2]);
                confirmacionField.setText(datos[2]);
                comboBox.setSelectedItem(TipoUsuario.valueOf(datos[3]));
            }
        });

        btnAceptar.addActionListener(e -> {

            TipoUsuario tipoUsuarioSeleccionado = (TipoUsuario) comboBox.getSelectedItem();
            String mensaje = usuarioController.actualizar(busquedaField.getText(), usuarioField.getText(),nombreField.getText(),Arrays.toString(passwordField.getPassword()) , Arrays.toString(confirmacionField.getPassword()),  tipoUsuarioSeleccionado.name());
            if (mensaje.contains("Modificado")) {
                JOptionPane.showMessageDialog(null,mensaje);
            } else {
                JOptionPane.showMessageDialog(null, mensaje);
            }
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    public void EliminarUsuario(Frame owner) {

        JDialog dialog = new JDialog(owner, "Eliminar Usuario", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel contentPanel = new JPanel();

        dialog.setBounds(100, 100, 372, 209);
        dialog.getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        dialog.getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblBusqueda = new JLabel("Busqueda:");
        lblBusqueda.setBounds(10, 14, 127, 14);
        contentPanel.add(lblBusqueda);

        JTextField busquedaField = new JTextField();
        busquedaField.setColumns(10);
        busquedaField.setBounds(136, 11, 215, 20);
        contentPanel.add(busquedaField);

        JComboBox<String> busquedaBox = new JComboBox<>();
        busquedaBox.setBounds(136, 11, 215, 20);
        busquedaBox.setVisible(false);
        contentPanel.add(busquedaBox);

        busquedaField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!isAdjusting) {
                    isAdjusting = true;
                    String busqueda = busquedaField.getText().toLowerCase();
                    busquedaBox.removeAllItems();

                    List<Usuario> coincidencias = usuarioController.listar().stream()
                            .filter(usuario -> (String.valueOf(usuario.getId()).contains(busqueda) ||
                                    usuario.getNombre().toLowerCase().contains(busqueda) ||
                                    usuario.getNombreUsuario().toLowerCase().contains(busqueda))
                                    && usuario.isActivo())
                            .collect(Collectors.toList());

                    if (!coincidencias.isEmpty()) {
                        for (Usuario usuario : coincidencias) {
                            busquedaBox.addItem(usuario.getId() + " - " + usuario.getNombre() + " - " + usuario.getNombreUsuario());
                        }
                        busquedaBox.setVisible(true);
                        busquedaBox.showPopup();
                    } else {
                        busquedaBox.setVisible(false);
                    }

                    isAdjusting = false;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && busquedaBox.isVisible()) {
                    if (busquedaBox.getSelectedItem() != null) {
                        String clienteSeleccionado = (String) busquedaBox.getSelectedItem();
                        busquedaField.setText(clienteSeleccionado);
                        busquedaBox.setVisible(false);
                    }
                }
            }
        });

        busquedaBox.addActionListener(e -> {
            if (!isAdjusting && busquedaBox.getSelectedItem() != null) {
                String clienteSeleccionado = (String) busquedaBox.getSelectedItem();
                busquedaField.setText(clienteSeleccionado);
                busquedaBox.setVisible(false);
            }
        });

        busquedaField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                busquedaBox.setVisible(false);
            }
        });

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 140, 89, 23);
        contentPanel.add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(262, 140, 89, 23);
        contentPanel.add(btnCancelar);

        btnAceptar.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, usuarioController.eliminar(busquedaField.getText()));
            dialog.dispose();
        });

        btnCancelar.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }
}
