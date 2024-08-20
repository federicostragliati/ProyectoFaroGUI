package Views;

import Views.ProductoPanel;
import javax.swing.*;
import java.awt.*;

public class SistemaDeGestion extends JFrame {

    private JPanel mainPanel;

    public SistemaDeGestion() {
        setTitle("Sistema de Gestión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Establece un tamaño inicial, pero se maximizará al abrir
        setLocationRelativeTo(null);

        // Maximizar la ventana al abrir
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Panel de menú lateral
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(8, 1)); // 8 botones en una columna

        // Crear los botones del menú lateral
        JButton productosButton = new JButton("Productos");
        // Agregar otros botones para otras funcionalidades

        menuPanel.add(productosButton);
        // Agregar otros botones al menú

        // Panel principal con CardLayout
        mainPanel = new JPanel(new CardLayout());

        // Crear e inicializar las vistas específicas
        ProductoPanel productoPanel = new ProductoPanel();

        mainPanel.add(productoPanel, "productosPanel");

        // Agregar listeners para cambiar la vista cuando se hace clic en un botón
        productosButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "productosPanel");
        });

        // Agregar otros listeners para otros botones

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(menuPanel, BorderLayout.WEST);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaDeGestion sistemaDeGestion = new SistemaDeGestion();
            sistemaDeGestion.setVisible(true);
        });
    }
}
