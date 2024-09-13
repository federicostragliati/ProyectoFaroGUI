package Controller;

import Model.CustomTables.CustomTableModelId;
import Model.Validaciones.Validador;
import dao.implementaciones.*;
import dominio.Compra;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

public class CompraController {

    private final CompraDAO compraDAO = new CompraDAO();
    private final ProveedorDAO proveedorDAO = new ProveedorDAO();
    private final DetalleCompraController detalleCompraController = new DetalleCompraController();
    private final ProductoDAO productoDAO = new ProductoDAO();

    public String crear(String proveedor, String fechaCompra, String idMetodoPrim, String montoPrim, String idMetodoSec, String montoSec, String montoTotal, boolean pagado, boolean entregada) {

        String id;
        BigDecimal montoPrimBig;
        BigDecimal montoSecBig;
        BigDecimal montoFinal = new BigDecimal(montoTotal);
        Date fechaFinal;
        id = Validador.extraerID(proveedor);
        String idMetod1 = Validador.extraerID(idMetodoPrim);
        String idMetod2 = Validador.extraerID(idMetodoSec);
        String cuit;

        try {
            cuit = proveedorDAO.getProveedor(Integer.parseInt(id)).getCuit();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        if (Validador.validarNumeroDecimal(montoPrim)){
            montoPrimBig = new BigDecimal(montoPrim);
        } else {
            return "Monto primario no valido";
        }

        if (Validador.validarNumeroDecimal(montoSec) && !montoSec.isEmpty()) {
            montoSecBig = new BigDecimal(montoSec);
        } else if (montoSec.isEmpty()) {
            montoSecBig = BigDecimal.ZERO;
        } else {
            return "Monto secundario no valido";
        }

        if (Validador.esFormatoFechaValido(fechaCompra)) {
            fechaFinal = Validador.convertirStringADate(fechaCompra);
        } else {
            return "Error en la fecha";
        }

        if (!Validador.sumaDeMontos(montoPrimBig,montoSecBig,montoFinal)) {
            return "Los montos no coinciden con el monto final";
        }

        try {
            compraDAO.createCompra(new Compra(Integer.parseInt(id),cuit,fechaFinal,Integer.parseInt(idMetod1),montoPrimBig,Integer.parseInt(idMetod2),montoSecBig,montoFinal,pagado,entregada,true));
            return "Compra Generada";
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String[] consultar (String id) {
        Compra compra;

        if (!Validador.esNumeroEntero(id) || id.isEmpty()) {
            return new String[]{"ID Invalido"};
        }

        try {
            compra = compraDAO.getCompra(Integer.parseInt(id));

            if (compra == null ) {
                return new String[] {"ID Venta no Existe"};
            }
            Field[] campos = compra.getClass().getDeclaredFields();
            String[] datos = new String[campos.length];

            for (int i = 1; i < campos.length; i++) {
                campos[i].setAccessible(true); // Permitir el acceso a atributos privados
                try {
                    Object valor = campos[i].get(compra); // Obtener el valor del atributo
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

    public String modificar (String idCompra, String idProveedor, String cuit, String fechaVenta, String idMetodoPrim, String montoPrim, String idMetodoSec, String montoSec, String montoTotal, boolean pagado, boolean entregada, boolean activo) {
        //Si la compra fue dada de baja no tengo que permitir que la active nuevamente
        String id;
        BigDecimal montoPrimBig;
        BigDecimal montoSecBig;
        BigDecimal montoFinal = new BigDecimal(montoTotal);
        Date fechaFinal;
        // id = Validador.extraerID(cliente);
        String idMetod1 = Validador.extraerID(idMetodoPrim);
        String idMetod2 = Validador.extraerID(idMetodoSec);
        //String cuit;

        if (activo == false) {
            return "La compra ya fue dada de baja";
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


        Compra compra = new Compra(Integer.parseInt(idCompra), Integer.parseInt(idProveedor), cuit, fechaFinal, Integer.parseInt(idMetod1), montoPrimBig, Integer.parseInt(idMetod2), montoSecBig,  montoFinal, pagado, entregada,activo);

        try {
            compraDAO.updateCompra(compra);
        } catch (SQLException | ClassNotFoundException | IOException  e) {
            throw new RuntimeException(e);
        }


        return "Modificación Realizada";


    }

    public DefaultTableModel listar() {
        String[] columnNames = {"ID", "ID Proveedor", "CUIT", "Fecha Compra", "Metodo Primario", "Monto Primario", "Metodo Secundario", "Monto Secundario", "Monto Final", "Pagada", "Entregada", "Activa"};
        DefaultTableModel tableModel = new CustomTableModelId(new Object[][]{}, columnNames);

        try {
            java.util.List<Compra> compras = compraDAO.getCompras();
            for (Compra compra : compras) {
                Object[] rowData = {
                        compra.getId(),
                        compra.getIdProveedor(),
                        compra.getCuitProveedor(),
                        compra.getFechaCompra(),
                        compra.getMetodoDePagoPrimario(),
                        compra.getMontoDePagoPrimario(),
                        compra.getMetodoDePagoSecundario(),
                        compra.getMontoDePagoSecundario(),
                        compra.getMontoFinal(),
                        compra.isPagada(),
                        compra.isEntregada(),
                        compra.isActivo()
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
            compraDAO.deleteCompra(Integer.parseInt(id));
            return "Venta Eliminada";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public int ultimaCompra () {

        Compra compra;
        try {
            compra = compraDAO.getLastCompra();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        return compra.getId();
    }


}
