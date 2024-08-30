package Controller;

import Model.Validaciones.Validador;
import dao.implementaciones.ClienteDAO;
import dao.implementaciones.VentaDAO;
import dominio.Venta;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

public class VentaController {

    private final VentaDAO ventaDAO = new VentaDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    public String crear(String idCliente, String fechaVenta, String descuentos, String idMetodoPrim, String montoPrim, String idMetodoSec, String montoSec, String montoTotal,  boolean pagado, boolean completa, boolean entregada) {

        String id;
        String cuitCliente;
        BigDecimal montoPrimBig = BigDecimal.ZERO;
        BigDecimal montoSecBig = BigDecimal.ZERO;
        BigDecimal montoFinal = new BigDecimal(montoTotal);

        id = Validador.extraerNumerosInicio(idCliente);
        String idMetod1 = Validador.extraerNumerosInicio(idMetodoPrim);
        String idMetod2 = Validador.extraerNumerosInicio(idMetodoSec);

        try {
            //id = clienteDAO.getCliente(Integer.parseInt(idCliente)).getId();
            cuitCliente = clienteDAO.getCliente(Integer.parseInt(id)).getCuitCliente();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        int desc = 0;
        if (Validador.esNumeroEntero(descuentos)) {
            desc = Integer.parseInt(descuentos);
        }

        if (Validador.validarNumeroDecimal(montoPrim)){
            montoPrimBig = new BigDecimal(montoPrim);
        }

        if (Validador.esNumeroEntero(montoSec)) {
            montoSecBig = new BigDecimal(montoSec);
        }

        Date fechaFinal = Validador.convertirStringADate(fechaVenta);


        try {
            ventaDAO.createVenta(new Venta(Integer.parseInt(id),cuitCliente,fechaFinal,desc,Integer.parseInt(idMetod1),montoPrimBig,Integer.parseInt(idMetod2),montoSecBig,montoFinal,pagado,completa,entregada,true));
            return "Venta Generada";
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

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

    public int ultimaVenta () {

        Venta venta = null;
        try {
            venta = ventaDAO.getLastVenta();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        return venta.getId();
    }
}
