package Views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VistaPrincipalAdministrador extends JFrame {

    private JPanel mainPanel;

    public VistaPrincipalAdministrador() {
        setTitle("Sistema de Gestión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 600);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1, 5, 5)); // Organizar botones en una columna
        contentPane.add(buttonPanel, BorderLayout.WEST);

        JButton botonProductos = new JButton("Productos");
        buttonPanel.add(botonProductos);

        JButton botonClientes = new JButton("Clientes");
        buttonPanel.add(botonClientes);

        JButton botonProveedores = new JButton("Proveedores");
        buttonPanel.add(botonProveedores);

        JButton botonCompras = new JButton("Compras");
        buttonPanel.add(botonCompras);

        JButton botonVentas = new JButton("Ventas");
        buttonPanel.add(botonVentas);

        JButton botonContabilidad = new JButton("Contabilidad");
        buttonPanel.add(botonContabilidad);

        JButton botonReportes = new JButton("Reportes");
        buttonPanel.add(botonReportes);

        // Panel principal con CardLayout
        mainPanel = new JPanel(new CardLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);

        // Crear e inicializar las vistas específicas
        ProductoPanel productoPanel = new ProductoPanel("Crear Producto","Consultar Producto", "Modificar Producto", "Listar Producto", "Baja Producto");
        ClientePanel clientePanel = new ClientePanel("Crear Cliente","Consultar Cliente", "Modificar Cliente", "Listar Clientes", "Baja Clientes");

        mainPanel.add(productoPanel, "productosPanel");
        mainPanel.add(clientePanel, "clientePanel");

        // Agregar listeners para cambiar la vista cuando se hace clic en un botón
        botonProductos.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "productosPanel");
        });

        botonClientes.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "clientePanel");
        });

        // Agregar otros listeners para otros botones (aún no implementados)
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaPrincipalAdministrador vistaPrincipalAdministrador = new VistaPrincipalAdministrador();
            vistaPrincipalAdministrador.setVisible(true);
        });
    }
}