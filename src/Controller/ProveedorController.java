package Controller;

import Model.CustomTableModelId;
import Model.Validador;
import dao.implementaciones.ClienteDAO;
import dao.implementaciones.ProveedorDAO;
import dominio.Cliente;
import dominio.Producto;
import dominio.Proveedor;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;

public class ProveedorController {

    private final ProveedorDAO proveedorDAO = new ProveedorDAO();

    public String crear(String cuit, String razonSocial, String email, String telefono, String direccion, boolean activo) {

        if (!Validador.validarCUIT(cuit)) {
            return "Error en el CUIT";
        } else if (!Validador.validarLongitud(razonSocial)) {
            return "Nombre demasiado largo";
        } else if (!Validador.validarEmail(email)) {
            return "Error en E-Mail";
        } else if (!Validador.esNumeroEntero(telefono)) {
            return "Error en Teléfono";
        } else if (!activo) {
            return "El cliente debe estar activo";
        } else if (!Validador.validarLongitud(direccion)) {
            return "Error en Dirección";
        }

        try {
            proveedorDAO.createProveedor(new Proveedor(cuit, razonSocial, email, telefono, direccion, activo));
            return "Creacion Correcta";
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String[] consultar (String id) {
        Proveedor proveedor;

        if (!Validador.esNumeroEntero(id)) {
            return new String[]{"ID Invalido"};
        }

        try {
            proveedor = proveedorDAO.getProveedor(Integer.parseInt(id));

            if (proveedor == null ) {
                return new String[] {"ID no existe"};
            }
            Field[] campos = proveedor.getClass().getDeclaredFields();
            String[] datos = new String[campos.length];

            for (int i = 1; i < campos.length; i++) {
                campos[i].setAccessible(true); // Permitir el acceso a atributos privados
                try {
                    Object valor = campos[i].get(proveedor); // Obtener el valor del atributo
                    if (valor != null) {
                        datos[i - 1] = valor.toString(); // Convertir valor a String
                    } else {
                        datos[i - 1] = "null"; // Representar valores nulos como "null"
                    }
                } catch (IllegalAccessException e) {
                    datos[i - 1] = "Acceso no permitido";
                }
            }
            return datos;

        } catch (SQLException | ClassNotFoundException | IOException ex) {
            throw new RuntimeException();
        }
    }

    public String modificar (String id, String cuit, String nombre, String email,String direccion, String telefono) {

        Proveedor proveedor;

        try {
            proveedor = proveedorDAO.getProveedor(Integer.parseInt(id));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        if (proveedor.isActivo() == false) {
            return "El proveedor a modificar no esta activo";
        } else if (Validador.validarLongitud(nombre) != true) {
            return "Error en el nombre";
        } else if (Validador.validarEmail(email) != true) {
            return "Error en el mail";
        } else if (Validador.esNumeroEntero(telefono) != true) {
            return "Error en el teléfono";
        } else if (!Validador.validarLongitud(direccion)) {
            return "Error en Dirección";
        }


        try {
            proveedorDAO.updateProveedor(new Proveedor(Integer.parseInt(id),cuit,nombre,email,telefono,direccion,true));
            return "Cliente Modificado";
        } catch (SQLException | IOException | ClassNotFoundException e) {
            return "Error al modificar";
            // throw new RuntimeException(e);
        }

    }

    public DefaultTableModel listar() {

        String[] columnNames = {"ID", "CUIT", "Nombre", "E-Mail", "Teléfono", "Direccion", "Activo"};
        DefaultTableModel tableModel = new CustomTableModelId(new Object[][]{}, columnNames);

        try {
            java.util.List<Proveedor> proveedores = proveedorDAO.getProveedores();
            for (Proveedor proveedor : proveedores) {
                Object[] rowData = {
                        proveedor.getId(),
                        proveedor.getCuit(),
                        proveedor.getRazonSocial(),
                        proveedor.getEmail(),
                        proveedor.getTelefono(),
                        proveedor.getDireccion(),
                        proveedor.isActivo()
                };
                tableModel.addRow(rowData);
            }
            return tableModel;
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public String eliminar (String id) {
        // Validar que el ID sea un número entero
        if (!Validador.esNumeroEntero(id)) {
            return "ID Invalido";
        }

        boolean resultado;

        try {
            resultado = proveedorDAO.deleteProveedor(Integer.parseInt(id));
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        if (!resultado) {
            return "Eliminado";
        } else {
            return "Error al eliminar";
        }
    }
}
