package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

import dao.implementaciones.*;
import dominio.*;
import dominio.enums.Unidad;

public class SistemaDeGestion extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public SistemaDeGestion() {
        // Configuración básica del JFrame
        setTitle("Sistema de Gestión Faro");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear el panel de menú lateral
        JPanel sideMenu = new JPanel();
        sideMenu.setLayout(new GridLayout(8, 1)); // 8 botones en una columna
        String[] menuItems = {"Inicio", "Ventas", "Compras", "Productos", "Proveedores", "Contabilidad", "Clientes", "Reportes"};
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.addActionListener(new MenuActionListener(item));
            sideMenu.add(button);
        }

        // Crear el panel principal con CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Agregar las diferentes vistas al panel principal
        mainPanel.add(createPanel("Vista de Inicio"), "Inicio");
        mainPanel.add(createPanel("Vista de Ventas"), "Ventas");
        mainPanel.add(createPanel("Vista de Compras"), "Compras");
        mainPanel.add(createProductPanel(), "Productos"); // Vista de Productos con botones
        mainPanel.add(createPanel("Vista de Proveedores"), "Proveedores");
        mainPanel.add(createPanel("Vista de Contabilidad"), "Contabilidad");
        mainPanel.add(createPanel("Vista de Clientes"), "Clientes");
        mainPanel.add(createPanel("Vista de Reportes"), "Reportes");

        // Agregar los paneles al JFrame
        add(sideMenu, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        // Mostrar la vista inicial
        cardLayout.show(mainPanel, "Inicio");
    }

    // Método para crear un panel básico con un título y un buscador
    private JPanel createPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Crear el panel para el buscador
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Buscar");
        searchPanel.add(new JLabel("Buscar:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Crear el área para mostrar los resultados (se puede personalizar más adelante)
        JTextArea resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsArea);

        // Agregar componentes al panel principal
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Agregar acción al botón de búsqueda
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                // Aquí puedes agregar la lógica para realizar la búsqueda y mostrar resultados
                resultsArea.setText("Resultados de búsqueda para: " + query);
            }
        });

        return panel;
    }


    // Método para crear el panel de productos con botones
    private JPanel createProductPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Crear el panel para los botones de productos
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1)); // 5 botones en una columna

        // Crear y agregar botones
        JButton createButton = new JButton("Crear Producto");
        JButton modifyButton = new JButton("Modificar Producto");
        JButton consultButton = new JButton("Consultar Producto");
        JButton listButton = new JButton("Consultar Listado");
        JButton deleteButton = new JButton("Baja Producto");

        buttonPanel.add(createButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(consultButton);
        buttonPanel.add(listButton);
        buttonPanel.add(deleteButton);

        // Agregar el panel de botones al panel principal
        panel.add(buttonPanel, BorderLayout.WEST);

        // Crear el área para mostrar la información relacionada
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Agregar acciones a los botones
        createButton.addActionListener(e -> showCreateProductDialog());
        modifyButton.addActionListener(e -> showModifyProductDialog());
        consultButton.addActionListener(e -> infoArea.setText("Acción: Consultar Producto"));
        listButton.addActionListener(e -> infoArea.setText("Acción: Consultar Listado"));
        deleteButton.addActionListener(e -> infoArea.setText("Acción: Baja Producto"));

        return panel;
    }


    private void showModifyProductDialog () {

        AtomicReference<Integer> idOriginal = new AtomicReference<>(new Integer(0));

        JDialog dialog = new JDialog(this, "Modificar Producto", true);
        dialog.setSize(400, 400);
        dialog.setLayout(new GridLayout(7, 2));
        dialog.setLocationRelativeTo(this);

        dialog.add(new JLabel("ID del producto a Modificar:"));
        JTextField idField = new JTextField();
        dialog.add(idField);

        dialog.add(new JLabel("Detalle:"));
        JTextField detailField = new JTextField();
        dialog.add(detailField);

        dialog.add(new JLabel("Cantidad:"));
        JTextField quantityField = new JTextField();
        dialog.add(quantityField);

        dialog.add(new JLabel("Precio Unitario:"));
        JTextField priceField = new JTextField();
        dialog.add(priceField);

        dialog.add(new JLabel("Unidad de Venta:"));
        JTextField unitField = new JTextField();
        dialog.add(unitField);

        JPanel buttonPanel = new JPanel();
        JButton buscarButton = new JButton("Buscar");
        JButton acceptButton = new JButton("Aceptar");
        JButton cancelButton = new JButton("Cancelar");
        buttonPanel.add(buscarButton);
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel);

        buscarButton.addActionListener(e -> {


            String id = idField.getText();

            ProductoDAOImpMySQL productoDAOImpMySQL = new ProductoDAOImpMySQL();
            Producto producto = null;
            try {
                producto = productoDAOImpMySQL.getProducto(Integer.parseInt(id));
            } catch (SQLException | ClassNotFoundException | IOException ex) {
                throw new RuntimeException(ex);
            }

            idOriginal.set(producto.getId());

            detailField.setText(producto.getDetalle());
            quantityField.setText(String.valueOf(producto.getCantidad()));
            priceField.setText(String.valueOf(producto.getPrecioUnitario()));
            unitField.setText(String.valueOf(producto.getUnidad()));

        });

        acceptButton.addActionListener(e -> {
            String detail = detailField.getText();
            String quantity = quantityField.getText();
            String price = priceField.getText();
            String unit = unitField.getText();

            ProductoDAOImpMySQL productoDAOImpMySQL = new ProductoDAOImpMySQL();

            BigDecimal cantidad = new BigDecimal(quantity);
            BigDecimal precio = new BigDecimal(price);

            try {
                productoDAOImpMySQL.updateProducto(new Producto(idOriginal.get(), detail, cantidad,precio, Unidad.valueOf(unit),true));
            } catch (SQLException | ClassNotFoundException | IOException ex) {
                throw new RuntimeException(ex);
            }

            // Aquí puedes agregar la lógica para procesar los datos del nuevo producto
            System.out.println("Detalle: " + detail);
            System.out.println("Cantidad: " + quantity);
            System.out.println("Precio Unitario: " + price);
            System.out.println("Unidad de Venta: " + unit);
            System.out.println("Activo: " + true);
            dialog.dispose(); // Cierra el diálogo


        });

        cancelButton.addActionListener(e -> dialog.dispose()); // Cierra el diálogo

        dialog.setVisible(true); // Muestra el diálogo
    }
    // Método para mostrar el diálogo de creación de producto
    private void showCreateProductDialog() {
        JDialog dialog = new JDialog(this, "Crear Producto", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(6, 2));
        dialog.setLocationRelativeTo(this);

        // Crear los campos de texto y etiquetas
        dialog.add(new JLabel("Detalle:"));
        JTextField detailField = new JTextField();
        dialog.add(detailField);

        dialog.add(new JLabel("Cantidad:"));
        JTextField quantityField = new JTextField();
        dialog.add(quantityField);

        dialog.add(new JLabel("Precio Unitario:"));
        JTextField priceField = new JTextField();
        dialog.add(priceField);

        dialog.add(new JLabel("Unidad de Venta:"));
        JTextField unitField = new JTextField();
        dialog.add(unitField);

        dialog.add(new JLabel("Activo:"));
        JCheckBox activeCheckBox = new JCheckBox();
        dialog.add(activeCheckBox);

        // Crear botones de aceptar y cancelar
        JPanel buttonPanel = new JPanel();
        JButton acceptButton = new JButton("Aceptar");
        JButton cancelButton = new JButton("Cancelar");
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel);

        // Acción del botón Aceptar
        acceptButton.addActionListener(e -> {
            String detail = detailField.getText();
            String quantity = quantityField.getText();
            String price = priceField.getText();
            String unit = unitField.getText();
            boolean isActive = activeCheckBox.isSelected();
            ProductoDAOImpMySQL productoDAOImpMySQL = new ProductoDAOImpMySQL();

            BigDecimal cantidad = new BigDecimal(quantity);
            BigDecimal precio = new BigDecimal(price);

            try {
                productoDAOImpMySQL.createProducto(new Producto(detail, cantidad,precio, Unidad.valueOf(unit),isActive));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            // Aquí puedes agregar la lógica para procesar los datos del nuevo producto
            System.out.println("Detalle: " + detail);
            System.out.println("Cantidad: " + quantity);
            System.out.println("Precio Unitario: " + price);
            System.out.println("Unidad de Venta: " + unit);
            System.out.println("Activo: " + isActive);
            dialog.dispose(); // Cierra el diálogo
        });

        // Acción del botón Cancelar
        cancelButton.addActionListener(e -> dialog.dispose()); // Cierra el diálogo

        dialog.setVisible(true); // Muestra el diálogo
    }

    private void showGetProductDialog() {
        JDialog dialog = new JDialog(this, "Consultar Producto", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(8, 2));
        dialog.setLocationRelativeTo(this);

        dialog.add(new JLabel("ID del Producto:"));
        JTextField idField = new JTextField();
        dialog.add(idField);

        dialog.add(new JLabel("Detalle:"));
        JTextArea detalleField = new JTextArea();



    }

    // ActionListener para manejar los clics en los botones del menú
    private class MenuActionListener implements ActionListener {
        private String panelName;

        public MenuActionListener(String panelName) {
            this.panelName = panelName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, panelName);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaDeGestion frame = new SistemaDeGestion();
            frame.setVisible(true);
        });
    }
}
