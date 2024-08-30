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

    public String crear(String cliente, String fechaVenta, String descuentos, String idMetodoPrim, String montoPrim, String idMetodoSec, String montoSec, String montoTotal,  boolean pagado, boolean completa, boolean entregada) {

        int desc;
        String id;
        BigDecimal montoPrimBig;
        BigDecimal montoSecBig;
        BigDecimal montoFinal = new BigDecimal(montoTotal);
        Date fechaFinal;
        id = Validador.extraerID(cliente);
        String idMetod1 = Validador.extraerID(idMetodoPrim);
        String idMetod2 = Validador.extraerID(idMetodoSec);
        String cuit = Validador.extraerCuit(cliente);


        if (Validador.esNumeroEntero(descuentos)) {
            desc = Integer.parseInt(descuentos);
        } else {
            return "Descuento no valido";
        }

        if (Validador.validarNumeroDecimal(montoPrim)){
            montoPrimBig = new BigDecimal(montoPrim);
        } else {
            return "Monto primario no valido";
        }

        if (Validador.esNumeroEntero(montoSec)) {
            montoSecBig = new BigDecimal(montoSec);
        } else {
            return "Monto secundario no valido";
        }

        if (Validador.esFormatoFechaValido(fechaVenta)) {
            fechaFinal = Validador.convertirStringADate(fechaVenta);
        } else {
            return "Error en la fecha";
        }

        if (!Validador.sumaDeMontos(montoPrimBig,montoSecBig,montoFinal)) {
            return "Los montos no coinciden con el monto final";
        }

        try {
            ventaDAO.createVenta(new Venta(Integer.parseInt(id),cuit,fechaFinal,desc,Integer.parseInt(idMetod1),montoPrimBig,Integer.parseInt(idMetod2),montoSecBig,montoFinal,pagado,completa,entregada,true));
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

        Venta venta;
        try {
            venta = ventaDAO.getLastVenta();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        return venta.getId();
    }
}
