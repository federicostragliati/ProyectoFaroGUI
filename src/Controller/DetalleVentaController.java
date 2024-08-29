package Controller;

import Model.Auxiliares.ListadoProductos;
import Views.Dialog.DetalleVentaDialog;
import dao.implementaciones.DetalleVentaDAO;

import javax.swing.table.TableModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

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

    public List <ListadoProductos> getListado(TableModel tableModel) {

        List<ListadoProductos> listadoProductos = new ArrayList<>();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String id = (String) tableModel.getValueAt(i, 0);
            String detalle = (String) tableModel.getValueAt(i, 1);
            String unidadVenta = (String) tableModel.getValueAt(i, 2);
            String cantidad = (String) tableModel.getValueAt(i, 3);
            String precioUnitario = (String) tableModel.getValueAt(i, 4);
            String precioPorCantidad = (String) tableModel.getValueAt(i, 5);

            listadoProductos.add(new ListadoProductos(id, detalle,unidadVenta,cantidad,precioUnitario,precioPorCantidad));
        }

        return listadoProductos;

    }

    public void actualizarTable (DetalleVentaDialog jDialog, List<ListadoProductos> list) {

        Object[] fila = new Object[jDialog.getTableModel().getColumnCount()];
        for (int i = 0; i < list.size(); i++) {

            fila[0] = list.get(i).getId();
            fila[1] = list.get(i).getDetalle();
            fila[2] = list.get(i).getUnidad();
            fila[3] = list.get(i).getCantidad();
            fila[4] = list.get(i).getValor();
            fila[5] = list.get(i).getValorPorCantidad();

            jDialog.getTableModel().addRow(fila);
        }

    }

    public String totalVenta (List<ListadoProductos> list) {
        BigDecimal resultado = BigDecimal.ZERO;
            for (int i = 0; i < list.size(); i++) {
                BigDecimal valor = new BigDecimal(list.get(i).getValorPorCantidad());
                resultado = resultado.add(valor);
            }
        return resultado.setScale(2, RoundingMode.HALF_UP).toString();
    }

}
