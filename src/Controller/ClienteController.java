package Controller;

import Model.CustomTables.CustomTableModelId;
import Model.Validaciones.Herramientas;
import dao.implementaciones.ClienteDAO;
import dominio.Cliente;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

public class ClienteController {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    public String crear(String cuit, String nombre, String email, String telefono, boolean activo) {

        if (!Herramientas.validarCUIT(cuit)) {
            return "Error en el CUIT";
        } else if (!Herramientas.validarLongitud(nombre)) {
            return "Nombre demasiado largo";
        } else if (!Herramientas.validarEmail(email)) {
            return "Error en E-Mail";
        } else if (!Herramientas.esNumeroEntero(telefono)) {
            return "Error en Teléfono";
        } else if (!activo) {
            return "El cliente debe estar activo";
        }

        try {
            clienteDAO.addCliente(new Cliente(cuit, nombre, email, telefono, activo));
            return "Creacion Correcta";
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String[] consultar (String id) {
        Cliente cliente;

        if (!Herramientas.esNumeroEntero(id)) {
            return new String[]{"ID Invalido"};
        }

        try {
            cliente = clienteDAO.getCliente(Integer.parseInt(id));

            if (cliente == null ) {
                return new String[] {"ID no existe"};
            }
            Field[] campos = cliente.getClass().getDeclaredFields();
            String[] datos = new String[campos.length];

            for (int i = 1; i < campos.length; i++) {
                campos[i].setAccessible(true); // Permitir el acceso a atributos privados
                try {
                    Object valor = campos[i].get(cliente); // Obtener el valor del atributo
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

    public String modificar (String id, String cuit, String nombre, String email, String telefono) {

        Cliente cliente;

        try {
            cliente = clienteDAO.getCliente(Integer.parseInt(id));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        if (cliente.isActivo() == false) {
            return "El cliente a modificar no esta activo";
        } else if (Herramientas.validarLongitud(nombre) != true) {
            return "Error en el nombre";
        } else if (Herramientas.validarEmail(email) != true) {
            return "Error en el mail";
        } else if (Herramientas.esNumeroEntero(telefono) != true) {
            return "Error en el teléfono";
        }

        try {
            clienteDAO.updateCliente(new Cliente(Integer.parseInt(id),cuit,nombre,email,telefono,true));
            return "Cliente Modificado";
        } catch (SQLException | IOException | ClassNotFoundException e) {
            return "Error al modificar";
            // throw new RuntimeException(e);
        }

    }

    public DefaultTableModel listar() {

        String[] columnNames = {"ID", "CUIT", "Nombre", "E-Mail", "Teléfono", "Activo"};
        DefaultTableModel tableModel = new CustomTableModelId(new Object[][]{}, columnNames);

        try {
            java.util.List<Cliente> Clientes = clienteDAO.getClientes();
            for (Cliente cliente : Clientes) {
                Object[] rowData = {
                        cliente.getId(),
                        cliente.getCuitCliente(),
                        cliente.getNombre(),
                        cliente.getEmail(),
                        cliente.getTelefono(),
                        cliente.isActivo()
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
        if (!Herramientas.esNumeroEntero(id)) {
            return "ID Invalido";
        }

        boolean resultado;

        try {
            resultado = clienteDAO.deleteCliente(Integer.parseInt(id));
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        if (resultado) {
            return "Eliminado";
        } else {
            return "Error al eliminar";
        }
    }

    public List<Cliente> listado() {

        List<Cliente> listado;
        try {
            listado = clienteDAO.getClientes();
            return listado;
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
