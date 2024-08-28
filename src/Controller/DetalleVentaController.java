package Controller;

import dao.implementaciones.DetalleVentaDAO;
import javax.swing.table.TableModel;
import java.math.BigDecimal;

public class DetalleVentaController {

    private final DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAO();

    public DetalleVentaController() {
    }

    public void actualizarDatosProducto(int row, TableModel tableModel) {
        String idText = (String) tableModel.getValueAt(row, 0);

        // Usar Controller Producto
        if (!idText.isEmpty()) {
            ProductoController productoController = new ProductoController();
            String[] datos = productoController.consultar(idText);

            if (datos != null && datos.length > 0) {
                // Asignar valores a las columnas correspondientes
                tableModel.setValueAt(datos[0], row, 1); // Nombre
                tableModel.setValueAt(datos[3], row, 2); // Unidad de Venta
                tableModel.setValueAt(datos[2], row, 4); // Precio Unitario
            } else {
                System.out.println("No se encontraron datos para el ID: " + idText);

                // Limpiar columnas si el producto no es encontrado
                tableModel.setValueAt("", row, 1);
                tableModel.setValueAt("", row, 2);
                tableModel.setValueAt("", row, 4);
            }
        }
    }

    public String calcularPrecioPorCantidad(int row, TableModel tableModel) {
        String cantidadText = (String) tableModel.getValueAt(row, 3);
        String precioUnitarioText = (String) tableModel.getValueAt(row, 4);

        try {
            BigDecimal cantidad = new BigDecimal(cantidadText.isEmpty() ? "0" : cantidadText);
            BigDecimal precioUnitario = new BigDecimal(precioUnitarioText.isEmpty() ? "0" : precioUnitarioText);
            BigDecimal precioPorCantidad = cantidad.multiply(precioUnitario);
            // Asignar valores a las columnas correspondientes
            tableModel.setValueAt(precioPorCantidad.toString(), row, 5);
            return "Ok";
        } catch (NumberFormatException ex) {
            tableModel.setValueAt("", row, 5);
            return "Número inválido para cantidad o precio unitario";
        }

    }


}
