package Controller;

import Model.Auxiliares.ListadoProductos;
import Model.CustomTables.CustomTableModelId;
import Model.Validaciones.Validador;
import dao.implementaciones.ClienteDAO;
import dao.implementaciones.VentaDAO;
import dominio.Cliente;
import dominio.DetalleVenta;
import dominio.Venta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class VentaController {

    private final VentaDAO ventaDAO = new VentaDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final DetalleVentaController detalleVentaController = new DetalleVentaController();

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
        String cuit;

        try {
            cuit = clienteDAO.getCliente(Integer.parseInt(id)).getCuitCliente();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }


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

        if (Validador.validarNumeroDecimal(montoSec)) {
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
        Venta venta;

        if (!Validador.esNumeroEntero(id)) {
            return new String[]{"ID Invalido"};
        }

        try {
            venta = ventaDAO.getVenta(Integer.parseInt(id));

            if (venta == null ) {
                return new String[] {"ID Venta no Existe"};
            }
            Field[] campos = venta.getClass().getDeclaredFields();
            String[] datos = new String[campos.length];

            for (int i = 1; i < campos.length; i++) {
                campos[i].setAccessible(true); // Permitir el acceso a atributos privados
                try {
                    Object valor = campos[i].get(venta); // Obtener el valor del atributo
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

    public String modificar (String idVenta, String idCliente, String cuit, String fechaVenta, String descuentos, String idMetodoPrim, String montoPrim, String idMetodoSec, String montoSec, String montoTotal,  boolean pagado, boolean completa, boolean entregada) {

        int desc;
        String id;
        BigDecimal montoPrimBig;
        BigDecimal montoSecBig;
        BigDecimal montoFinal = new BigDecimal(montoTotal);
        Date fechaFinal;
        // id = Validador.extraerID(cliente);
        String idMetod1 = Validador.extraerID(idMetodoPrim);
        String idMetod2 = Validador.extraerID(idMetodoSec);
        //String cuit;

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

        if (Validador.validarNumeroDecimal(montoSec)) {
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


        Venta venta = new Venta(Integer.parseInt(idVenta), Integer.parseInt(idCliente), cuit, fechaFinal, Integer.parseInt(descuentos), Integer.parseInt(idMetod1), montoPrimBig, Integer.parseInt(idMetod2), montoSecBig,  montoFinal, pagado, completa, entregada,true);

        try {
            ventaDAO.updateVenta(venta);
        } catch (SQLException | ClassNotFoundException | IOException  e) {
            throw new RuntimeException(e);
        }


        return "Modificación Realizada";


    }

    public DefaultTableModel listar() {
        String[] columnNames = {"ID", "ID Cliente", "CUIT", "Fecha Venta", "Descuentos", "Metodo Primario", "Monto Primario", "Metodo Secundario", "Monto Secundario", "Monto Final", "Pagada", "Completa", "Entregada", "Activa"};
        DefaultTableModel tableModel = new CustomTableModelId(new Object[][]{}, columnNames);

        try {
            java.util.List<Venta> Ventas = ventaDAO.getVentas();
            for (Venta venta : Ventas) {
                Object[] rowData = {
                        venta.getId(),
                        venta.getIdCliente(),
                        venta.getCuitCliente(),
                        venta.getFechaVenta(),
                        venta.getDescuentos(),
                        venta.getMetodoDePagoPrimario(),
                        venta.getMontoDePagoPrimario(),
                        venta.getMetodoDePagoSecundario(),
                        venta.getMontoDePagoSecundario(),
                        venta.getMontoFinal(),
                        venta.isPagada(),
                        venta.isCompleta(),
                        venta.isEntregada(),
                        venta.isActivo()
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
            ventaDAO.deleteVenta(Integer.parseInt(id));
            return "Venta Eliminada";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

    public void calcularMontoTotal (JTextField campoDescuento, JTextField campoMontoTotal, List<ListadoProductos> listado) {

        BigDecimal total = new BigDecimal(detalleVentaController.totalVenta(listado));

        if (!campoDescuento.getText().isEmpty()) {
            BigDecimal descuento = new BigDecimal(campoDescuento.getText());
            BigDecimal valor1 = descuento.divide(new BigDecimal(100));
            BigDecimal valor2 = BigDecimal.ONE.subtract(valor1);
            BigDecimal montoConDescuento = total.multiply(valor2);
            campoMontoTotal.setText("$" + montoConDescuento);
        } else {
            campoMontoTotal.setText("$" + total);
        }
    }
}
