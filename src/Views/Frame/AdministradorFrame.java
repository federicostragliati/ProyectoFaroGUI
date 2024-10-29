package Views.Frame;

import Views.Dialog.UsuariosDialog;
import Views.Panel.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdministradorFrame extends JFrame {

    private JPanel mainPanel;

    public AdministradorFrame() {
        setTitle("Sistema de Gestión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 600);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        UsuariosDialog usuariosDialog = new UsuariosDialog();

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false); // Para que la barra no sea movible

        // Crear el botón de menú "Usuarios" y agregarlo a la barra de herramientas
        JMenu usuariosMenu = new JMenu("Usuarios");

        // Crear las opciones del menú de "Usuarios"
        JMenuItem crearUsuario = new JMenuItem("Crear Usuario");
        JMenuItem modificarUsuario = new JMenuItem("Modificar Usuario");
        JMenuItem eliminarUsuario = new JMenuItem("Eliminar Usuario");

        // Agregar opciones al menú "Usuarios"
        usuariosMenu.add(crearUsuario);
        usuariosMenu.add(modificarUsuario);
        usuariosMenu.add(eliminarUsuario);

        crearUsuario.addActionListener(e -> {usuariosDialog.CrearUsuario(this);});
        modificarUsuario.addActionListener(e -> {usuariosDialog.ModificarUsuario(this);});
        eliminarUsuario.addActionListener(e -> {usuariosDialog.EliminarUsuario(this);});

        // Crear una barra de menú y agregarle el menú "Usuarios"
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(usuariosMenu);

        // Agregar la barra de menú a la barra de herramientas
        toolBar.add(menuBar);

        // Agregar la barra de herramientas en la parte superior
        contentPane.add(toolBar, BorderLayout.NORTH);

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
        ProductoPanel productoPanel = new ProductoPanel("Crear Producto","Consultar Producto", "Modificar Producto", "Listar Productos", "Baja Producto");
        ClientePanel clientePanel = new ClientePanel("Crear Cliente","Consultar Cliente", "Modificar Cliente", "Listar Clientes", "Baja Clientes");
        ProveedorPanel proveedorPanel = new ProveedorPanel("Crear Proveedor", "Consultar Proveedor", "Modificar Proveedor", "Listar Proveedores", "Baja Proveedor");
        VentaPanel ventaPanel = new VentaPanel("Nueva Venta", "Consultar Venta","Modificar Venta","Listar Ventas", "Baja Venta");
        CompraPanel compraPanel = new CompraPanel("Nueva Compra","Consultar Comprar","Modificar Compra","Listar Compras", "Baja Compra");
        ContabilidadPanel contabilidadPanel = new ContabilidadPanel();
        ReportesPanel reportesPanel = new ReportesPanel();

        mainPanel.add(productoPanel, "productosPanel");
        mainPanel.add(clientePanel, "clientePanel");
        mainPanel.add(proveedorPanel, "proveedorPanel");
        mainPanel.add(ventaPanel,"ventaPanel");
        mainPanel.add(compraPanel,"compraPanel");
        mainPanel.add(contabilidadPanel,"contabilidadPanel");
        mainPanel.add(reportesPanel,"reportesPanel");

        // Agregar listeners para cambiar la vista cuando se hace clic en un botón
        botonProductos.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "productosPanel");
        });

        botonClientes.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "clientePanel");
        });

        botonProveedores.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel,"proveedorPanel");
        });

        botonVentas.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel,"ventaPanel");
        });

        botonCompras.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "compraPanel");
        });

        botonContabilidad.addActionListener( e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "contabilidadPanel");
        });

        botonReportes.addActionListener( e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "reportesPanel");
        });

        setLocationRelativeTo(null);


    }


}
