package Controller;

import Controller.Interface.ControllerInterface;
import Model.Validador;
import Model.CustomTableModelProducto;
import dao.implementaciones.ProductoDAO;
import dominio.Producto;
import dominio.enums.Unidad;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.SQLException;

public class ProductoController {

    private final ProductoDAO productoDAO = new ProductoDAO();

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
            productoDAO.createProducto(new Producto(detalle, cantidadDef, precioDef, Unidad.valueOf(unidad), activo));
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
            producto = productoDAO.getProducto(Integer.parseInt(id));

            if (producto == null) {
                return new String[]{"ID no Existe"};
            }

            // Obtener los atributos del producto
            Field[] campos = producto.getClass().getDeclaredFields();
            String[] datos = new String[campos.length];

            for (int i = 1; i < campos.length; i++) {
                campos[i].setAccessible(true); // Permitir el acceso a atributos privados
                try {
                    Object valor = campos[i].get(producto); // Obtener el valor del atributo
                    if (valor != null) {
                        datos[i - 1] = valor.toString(); // Convertir valor a String
                    } else {
                        datos[i - 1] = "null"; // Representar valores nulos como "null"
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
            java.util.List<Producto> productos = productoDAO.getProductos();
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

        boolean resultado = false;
        try {
            resultado = productoDAO.deleteProducto(Integer.parseInt(id));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        if (!resultado) {
            return "Eliminado";
        } else {
            return "Error al eliminar";
        }
    }

    public String modificar(String id, String detalle, String cantidad, String precio, String unidad) {

        Producto producto;

        try {
            producto = productoDAO.getProducto(Integer.parseInt(id));
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
            productoDAO.updateProducto(new Producto(Integer.parseInt(id), detalle, cantidadDef, precioDef, Unidad.valueOf(unidad), true));
            return "Producto modificado";
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            return "Error al modificar";
            //throw new RuntimeException(ex);
        }

    }

}
