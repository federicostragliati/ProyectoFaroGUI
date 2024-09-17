package Controller;

import Model.Validaciones.Herramientas;
import dao.implementaciones.ChequeDAO;
import dominio.Cheque;
import dominio.enums.Destino;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;


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

}
