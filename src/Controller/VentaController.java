package Controller;

import dao.implementaciones.VentaDAO;

import javax.swing.table.DefaultTableModel;

public class VentaController {

    private final VentaDAO ventaDAO = new VentaDAO();

    public String crear(String idCliente, String cuitCliente, String fechaVenta, String Descuentos, String idMetodoPrim, String montoPrim, String idMetodoSec, String montoSec, boolean pagado, boolean completa, boolean entregada, boolean activo) {
        return null;

    }

    public String[] consultar (String id) {
        return null;

    }

    public String modificar (String id, String cuit, String nombre, String email, String telefono, String direccion) {
        return null;



    }

    public DefaultTableModel listar() {
        return null;



    }

    public String eliminar (String id) {
        return null;

    }
}
