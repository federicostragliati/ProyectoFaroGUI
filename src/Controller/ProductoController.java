package Controller;

import dao.implementaciones.ProductoDAOImpMySQL;
import dominio.Producto;
import dominio.enums.Unidad;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoController {

    private final ProductoDAOImpMySQL productoDAOImpMySQL = new ProductoDAOImpMySQL();

    public String crearProductoValido(String detalle, String cantidad, String precio, String unidad, boolean activo) {

        if (Validador.validarLongitud45(detalle) != true) {
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

    /*public String [] realizarConsultaValida (String id) {
        Producto producto;

        if (Validador.esNumeroEntero(id) != true) {
            return new String[]{"ID Invalido"};
        }

        try {
            producto = productoDAOImpMySQL.getProducto(Integer.parseInt(id));
            Field[] campos = producto.getClass().getDeclaredFields();
            String[] datos = new String[campos.length];
            System.out.println(datos.length);
            System.out.println(campos.length);
            for (int i = 2; i < campos.length ; i++) {
                campos[i].setAccessible(true); // Permitir el acceso a atributos privados
                System.out.println(campos[i].getName());
                try {
                    Object valor = campos[i].get(producto); // Obtener el valor del atributo
                    if (valor != null) {
                        datos[i-2] = valor.toString(); // Convertir valor a String
                    } else {
                        datos[i-2] = "null"; // Representar valores nulos como "null"
                    }
                } catch (IllegalAccessException e) {
                    datos[i] = "Acceso no permitido";
                }
            }
            return datos;
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }*/

    public String[] realizarConsultaValida(String id) {
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


}
