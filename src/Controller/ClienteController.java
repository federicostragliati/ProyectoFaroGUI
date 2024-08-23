package Controller;

import Model.CustomTableModelCliente;
import Model.Validador;
import dao.implementaciones.ClienteDAOImpMySQL;
import dominio.Cliente;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;

public class ClienteController {

    private final ClienteDAOImpMySQL clienteDAOImpMySQL = new ClienteDAOImpMySQL();

    public String crear(String cuit, String nombre, String email, String telefono, boolean activo) {

        if (!Validador.validarCUIT(cuit)) {
            return "Error en el CUIT";
        } else if (!Validador.validarLongitud(nombre)) {
            return "Nombre demasiado largo";
        } else if (!Validador.validarEmail(email)) {
            return "Error en E-Mail";
        } else if (!Validador.esNumeroEntero(telefono)) {
            return "Error en Teléfono";
        } else if (!activo) {
            return "El cliente debe estar activo";
        }

        try {
            clienteDAOImpMySQL.addCliente(new Cliente(cuit, nombre, email, telefono, activo));
            return "Creacion Correcta";
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String[] consultar (String id) {
        Cliente cliente;

        if (!Validador.esNumeroEntero(id)) {
            return new String[]{"ID Invalido"};
        }

        try {
            cliente = clienteDAOImpMySQL.getCliente(Integer.parseInt(id));

            if (cliente == null ) {
                return new String[] {"ID no existe"};
            }
            Field[] campos = cliente.getClass().getDeclaredFields();
            String[] datos = new String[campos.length];

            for (int i = 2; i < campos.length; i++) {
                campos[i].setAccessible(true); // Permitir el acceso a atributos privados
                try {
                    Object valor = campos[i].get(cliente); // Obtener el valor del atributo
                    if (valor != null) {
                        datos[i - 2] = valor.toString(); // Convertir valor a String
                    } else {
                        datos[i - 2] = "null"; // Representar valores nulos como "null"
                    }
                } catch (IllegalAccessException e) {
                    datos[i - 2] = "Acceso no permitido";
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
            cliente = clienteDAOImpMySQL.getCliente(Integer.parseInt(id));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        if (cliente.isActivo() == false) {
            return "El cliente a modificar no esta activo";
        } else if (Validador.validarLongitud(nombre) != true) {
            return "Error en el nombre";
        } else if (Validador.validarEmail(email) != true) {
            return "Error en el mail";
        } else if (Validador.esNumeroEntero(telefono) != true) {
            return "Error en el teléfono";
        }

        try {
            clienteDAOImpMySQL.updateCliente(new Cliente(Integer.parseInt(id),cuit,nombre,email,telefono,true));
            return "Cliente Modificado";
        } catch (SQLException | IOException | ClassNotFoundException e) {
            return "Error al modificar";
            // throw new RuntimeException(e);
        }

    }

    public DefaultTableModel listar() {

        String[] columnNames = {"ID", "CUIT", "Nombre", "E-Mail", "Teléfono", "Activo"};
        DefaultTableModel tableModel = new CustomTableModelCliente(new Object[][]{}, columnNames);

        try {
            java.util.List<Cliente> Clientes = clienteDAOImpMySQL.getClientes();
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
        if (!Validador.esNumeroEntero(id)) {
            return "ID Invalido";
        }

        boolean resultado = clienteDAOImpMySQL.deleteCliente(Integer.parseInt(id));

        if (!resultado) {
            return "Eliminado";
        } else {
            return "Error al eliminar";
        }
    }
}
