package Controller;

import Model.Validador;
import Model.CustomTableModelProducto;
import dao.implementaciones.ProductoDAOImpMySQL;
import dominio.Producto;
import dominio.enums.Unidad;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.SQLException;

public class ProductoController {

    private final ProductoDAOImpMySQL productoDAOImpMySQL = new ProductoDAOImpMySQL();

    public String crear(String detalle, String cantidad, String precio, String unidad, boolean activo) {

        if (Validador.validarLongitud(detalle) != true) {
          return "Error en el detalle";
        } else if (Validador.validarNumeroDecimal(cantidad) != true) {
            return "Error en la cantidad";
        } else if (Validador.validarNumeroDecimal(precio) != true) {
            return "Error en el precio";
        }

        BigDecimal cantidadDef = new BigDecimal(cantidad);
        BigDecimal precioDef = new BigDecimal(precio);

        try {
            productoDAOImpMySQL.createProducto(new Producto(detalle, cantidadDef, precioDef, Unidad.valueOf(unidad), activo));
            return "Creación Correcta";
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String[] consultar(String id) {
        Producto producto;

        // Validar que el ID sea un número entero
        if (!Validador.esNumeroEntero(id)) {
            return new String[]{"ID Invalido"};
        }

        try {
            // Obtener el producto por ID
            producto = productoDAOImpMySQL.getProducto(Integer.parseInt(id));

            if (producto == null) {
                return new String[]{"ID no Existe"};
            }

            // Obtener los atributos del producto
            Field[] campos = producto.getClass().getDeclaredFields();
            String[] datos = new String[campos.length];

            for (int i = 2; i < campos.length; i++) {
                campos[i].setAccessible(true); // Permitir el acceso a atributos privados
                try {
                    Object valor = campos[i].get(producto); // Obtener el valor del atributo
                    if (valor != null) {
                        datos[i - 2] = valor.toString(); // Convertir valor a String
                    } else {
                        datos[i - 2] = "null"; // Representar valores nulos como "null"
                    }
                } catch (IllegalAccessException e) {
                    return new String[]{"ID no Existe"};
                    // datos[i - 2] = "Acceso no permitido";
                }
            }
            return datos;

        } catch (SQLException | ClassNotFoundException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public DefaultTableModel listar() {

        String[] columnNames = {"ID", "Detalle", "Cantidad", "Precio Unitario", "Unidad", "Activo"};
        DefaultTableModel tableModel = new CustomTableModelProducto(new Object[][]{}, columnNames);

        try {
            java.util.List<Producto> productos = productoDAOImpMySQL.getProductos();
            for (Producto producto : productos) {
                Object[] rowData = {
                        producto.getId(),
                        producto.getDetalle(),
                        producto.getCantidad(),
                        producto.getPrecioUnitario(),
                        producto.getUnidad(),
                        producto.isActivo()
                };
                tableModel.addRow(rowData);
            }
            return tableModel;
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String eliminar(String id) {
        // Validar que el ID sea un número entero
        if (!Validador.esNumeroEntero(id)) {
            return "ID Invalido";
        }

        boolean resultado = productoDAOImpMySQL.deleteProducto(Integer.parseInt(id));

        if (resultado) {
            return "Eliminado";
        } else {
            return "Error al eliminar";
        }
    }

    public String modificar(String id, String detalle, String cantidad, String precio, String unidad) {

        Producto producto;

        try {
            producto = productoDAOImpMySQL.getProducto(Integer.parseInt(id));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        if (producto.isActivo() == false) {
            return "El producto a modificar no esta activo";
        } else if (Validador.validarLongitud(detalle) != true) {
            return "Error en el detalle";
        } else if (Validador.validarNumeroDecimal(cantidad) != true) {
            return "Error en la cantidad";
        } else if (Validador.validarNumeroDecimal(precio) != true) {
            return "Error en el precio";
        }

        BigDecimal cantidadDef = new BigDecimal(cantidad);
        BigDecimal precioDef = new BigDecimal(precio);

        try {
            productoDAOImpMySQL.updateProducto(new Producto(Integer.parseInt(id), detalle, cantidadDef, precioDef, Unidad.valueOf(unidad), true));
            return "Producto modificado";
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            return "Error al modificar";
            //throw new RuntimeException(ex);
        }

    }

}
