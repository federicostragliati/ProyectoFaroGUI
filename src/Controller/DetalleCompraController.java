package Controller;

import Model.Auxiliares.ListadoProductos;
import dao.implementaciones.DetalleCompraDAO;
import dominio.DetalleCompra;
import dominio.enums.Unidad;

import javax.swing.table.TableModel;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DetalleCompraController {

    private DetalleCompraDAO detalleCompraDAO = new DetalleCompraDAO();

    public DetalleCompraController() {
    }

    public void actualizarDatosProducto(int row, TableModel tableModel) {
        String idText = (String) tableModel.getValueAt(row, 0);

        // Usar Controller Producto
        if (!idText.isEmpty()) {
            ProductoController productoController = new ProductoController();
            String[] datos = productoController.consultarActivos(idText);

            if (datos != null && datos.length > 1) { // Mas de un dato para evitar problemas de indice
                // Asignar valores a las columnas correspondientes
                tableModel.setValueAt(datos[0], row, 1); // Nombre
                tableModel.setValueAt(datos[3], row, 2); // Unidad de Venta
            } else {
                // Limpiar columnas si el producto no es encontrado
                tableModel.setValueAt("", row, 1);
                tableModel.setValueAt("", row, 2);
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
            return "Producto Cargado";
        } catch (NumberFormatException ex) {
            tableModel.setValueAt("", row, 5);
            return "Número inválido para cantidad o costo unitario";
        }

    }

    public List<ListadoProductos> getListado(TableModel tableModel) {

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

    public String totalCompra (List<ListadoProductos> list) {
        BigDecimal resultado = BigDecimal.ZERO;
        for (int i = 0; i < list.size(); i++) {
            BigDecimal valor = new BigDecimal(list.get(i).getValorPorCantidad());
            resultado = resultado.add(valor);
        }
        return resultado.setScale(2, RoundingMode.HALF_UP).toString();
    }

    public void crear(List<ListadoProductos> list, int idCompra) {

        for (int i = 0; i < list.size() ; i++) {
            try {
                detalleCompraDAO.createDetalleCompra(new DetalleCompra(idCompra, Integer.parseInt(list.get(i).getId()),list.get(i).getDetalle(), Unidad.valueOf(list.get(i).getUnidad()),new BigDecimal(list.get(i).getCantidad()),new BigDecimal(list.get(i).getValor()), new BigDecimal(list.get(i).getValorPorCantidad())));
            } catch (SQLException | ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<ListadoProductos> getlistadoList(String id){

        try {
            List<DetalleCompra> detalleVentas = detalleCompraDAO.getDetalleCompras();
            return detalleVentas.stream()
                    .filter(detalle -> detalle.getIdCompra() == Integer.parseInt(id))  // Filtra por idVenta
                    .map(detalle -> new ListadoProductos(
                            String.valueOf(detalle.getIdProducto()),
                            detalle.getDetalle(),
                            detalle.getUnidad().toString(),
                            detalle.getCantidad().toString(),
                            detalle.getCostoUnitario().toString(),
                            detalle.getCostoPorCantidad().toString()))
                    .collect(Collectors.toList());  // Convierte el Stream en una lista
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
