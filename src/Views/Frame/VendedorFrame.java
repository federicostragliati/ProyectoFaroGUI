package Views.Frame;

import Views.Dialog.UsuariosDialog;
import Views.Panel.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VendedorFrame extends JFrame {

    private JPanel mainPanel;

    public VendedorFrame() {
        setTitle("Sistema de Gestión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 600);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 5, 5)); // Organizar botones en una columna
        contentPane.add(buttonPanel, BorderLayout.WEST);


        JButton botonClientes = new JButton("Clientes");
        buttonPanel.add(botonClientes);

        JButton botonVentas = new JButton("Ventas");
        buttonPanel.add(botonVentas);

        JButton botonReportes = new JButton("Reportes");
        buttonPanel.add(botonReportes);

        // Panel principal con CardLayout
        mainPanel = new JPanel(new CardLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);

        // Crear e inicializar las vistas específicas
        ClientePanel clientePanel = new ClientePanel("Crear Cliente","Consultar Cliente", "Modificar Cliente", "Listar Clientes", "Baja Clientes");
        VentaPanel ventaPanel = new VentaPanel("Nueva Venta", "Consultar Venta","Modificar Venta","Listar Ventas", "Baja Venta");
        ReportesPanel reportesPanel = new ReportesPanel();

        mainPanel.add(clientePanel, "clientePanel");
        mainPanel.add(ventaPanel,"ventaPanel");
        mainPanel.add(reportesPanel,"reportesPanel");

        // Agregar listeners para cambiar la vista cuando se hace clic en un botón
        botonClientes.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "clientePanel");
        });

        botonVentas.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel,"ventaPanel");
        });

        botonReportes.addActionListener( e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "reportesPanel");
        });

        setLocationRelativeTo(null);

    }
}
