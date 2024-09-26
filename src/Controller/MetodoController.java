package Controller;

import Model.CustomTables.CustomTableModelId;
import Model.Validaciones.Herramientas;
import dao.implementaciones.MetodoDePagoDAO;
import dominio.MetodoDePago;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;

public class MetodoController {

    private final MetodoDePagoDAO metodoDePagoDAO = new MetodoDePagoDAO();

    public String crear(String nombreMetodo) {
        if (!Herramientas.validarLongitud(nombreMetodo)) {
            return "Error en el nombre";
        }

        try {
            metodoDePagoDAO.createMetodoDePago(new MetodoDePago(nombreMetodo,true));
            return "Método de Pago Creado";
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] consultar (String id) {
        MetodoDePago metodo;

        if (!Herramientas.esNumeroEntero(id) || id.isEmpty()) {
            return new String[]{"ID Invalido"};
        }

        try {
            metodo = metodoDePagoDAO.getMetodoDePago(Integer.parseInt(id));

            if (metodo == null ) {
                return new String[] {"ID Cheque no Existe"};
            }
            Field[] campos = metodo.getClass().getDeclaredFields();
            String[] datos = new String[campos.length];

            for (int i = 1; i < campos.length; i++) {
                campos[i].setAccessible(true); // Permitir el acceso a atributos privados
                try {
                    Object valor = campos[i].get(metodo); // Obtener el valor del atributo
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

    public DefaultTableModel listar () {
        String[] columnNames = {"ID", "Método de Pago", "Activo"};
        DefaultTableModel tableModel = new CustomTableModelId(new Object[][]{}, columnNames);

        try {
            java.util.List<MetodoDePago> Metodos = metodoDePagoDAO.getMetodoDePagos();
            for (MetodoDePago Metodo : Metodos) {
                Object[] rowData = {
                        Metodo.getId(),
                        Metodo.getMetodo(),
                        Metodo.isActivo()
                };
                tableModel.addRow(rowData);
            }
            return tableModel;
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String eliminar (String id) {

        try {
            metodoDePagoDAO.deleteMetodoDePago(Integer.parseInt(id));
            return "Método Eliminado";
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
