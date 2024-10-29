package Controller;

import Model.CustomTables.CustomTableModelId;
import Model.Validaciones.Herramientas;
import dao.implementaciones.ChequeDAO;
import dominio.Cheque;
import dominio.enums.Destino;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;


public class ChequeController {

    private final ChequeDAO chequeDAO = new ChequeDAO();

    public String crear(int idTransacción, String fechaRecepcion, String emisor, String banco, String nroCheque, String importe, String fechaCheque, String fechaCobro,String destino, String destinatario, boolean estado) {

        if (Herramientas.validarLongitud(nroCheque)) {
            if (!Herramientas.esNumeroEntero(nroCheque)){
                return "Error en el Nro. de Cheque";
            }
        } else {
            return "Error en el Nro. de Cheque";
        }

        String datosEmisor [] = Herramientas.dividirTexto(emisor);
        String datosDestinatario [] = Herramientas.dividirTexto(destinatario);
        System.out.println(datosDestinatario[2]);

        try {
            chequeDAO.createCheque(new Cheque(idTransacción,Herramientas.convertirStringADate(fechaRecepcion),datosEmisor[2], datosEmisor[1], banco,nroCheque,new BigDecimal(importe),Herramientas.convertirStringADate(fechaCheque),Herramientas.convertirStringADate(fechaCobro), Destino.valueOf(destino),datosDestinatario[2],estado,true));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        return "Cheque Generado";
    }

    public String[] consultar(String id) {
        Cheque cheque;

        if (!Herramientas.esNumeroEntero(id) || id.isEmpty()) {
            return new String[]{"ID Invalido"};
        }

        try {
            cheque = chequeDAO.getCheque(Integer.parseInt(id));

            if (cheque == null ) {
                return new String[] {"ID Cheque no Existe"};
            }
            Field[] campos = cheque.getClass().getDeclaredFields();
            String[] datos = new String[campos.length];

            for (int i = 1; i < campos.length; i++) {
                campos[i].setAccessible(true); // Permitir el acceso a atributos privados
                try {
                    Object valor = campos[i].get(cheque); // Obtener el valor del atributo
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

    public String modificar (String idCheque, String fechaRecepcion, String banco, String fechaCheque, String fechaCobro, boolean estado) {

        Cheque cheque;

        try {
            cheque = chequeDAO.getCheque(Integer.parseInt(idCheque));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        if (!cheque.isActivo()) {
            return "El cheque no esta activo";
        }

        if (!Herramientas.validarLongitud(banco)) {
            return "Error en el Banco";
        }

        Date fechaRecepcionDate = Herramientas.convertirStringADate(fechaRecepcion);
        Date fechaChequeDate = Herramientas.convertirStringADate(fechaCheque);
        Date fechaCobroDate = Herramientas.convertirStringADate(fechaCobro);

        cheque.setFechaRecepcion(fechaRecepcionDate);
        cheque.setFechaCheque(fechaChequeDate);
        cheque.setFechaCobro(fechaCobroDate);
        cheque.setBancoProcedencia(banco);
        cheque.setEstado(estado);

        try {
            chequeDAO.updateCheque(cheque);
            return "Cheque Actualizado";
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public DefaultTableModel listar() {
        String[] columnNames = {"ID", "ID Transacción","Fecha Recepción", "CUIT Emisor", "Nombre Emisor ", "Banco", "Nro Cheque", "Importe", "Fecha Cheque", "Fecha Cobro", "Destino", "CUIT Destino", "Estado", "Activo"};
        DefaultTableModel tableModel = new CustomTableModelId(new Object[][]{}, columnNames);

        try {
            java.util.List<Cheque> Cheques = chequeDAO.getCheques();
            for (Cheque cheque : Cheques) {
                Object[] rowData = {
                        cheque.getId(),
                        cheque.getIdTransaccion(),
                        cheque.getFechaRecepcion(),
                        cheque.getCuitEmisor(),
                        cheque.getNombreEmisor(),
                        cheque.getBancoProcedencia(),
                        cheque.getNroCheque(),
                        cheque.getImporte(),
                        cheque.getFechaCheque(),
                        cheque.getFechaCobro(),
                        cheque.getDestino(),
                        cheque.getCuitDestino(),
                        cheque.isEstado(),
                        cheque.isActivo()
                };
                tableModel.addRow(rowData);
            }
            return tableModel;
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String eliminar (String idCheque) {

        try {
            chequeDAO.deleteCheque(Integer.parseInt(idCheque));
            return "Cheque Eliminado";
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }


    }
}
