package Controller;

import Model.CustomTableModelId;
import Model.Validador;
import dao.implementaciones.DetalleVentaDAO;
import dao.implementaciones.VentaDAO;
import dominio.DetalleVenta;
import dominio.Proveedor;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

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
