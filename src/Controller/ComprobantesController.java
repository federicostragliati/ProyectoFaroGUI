package Controller;

import dao.implementaciones.ReciboDAO;
import dao.implementaciones.RemitoDAO;

import java.io.IOException;
import java.sql.SQLException;

public class ComprobantesController {

    RemitoDAO remitoDAO = new RemitoDAO();
    ReciboDAO reciboDAO = new ReciboDAO();

    public void updateRemito(String id){
        try {
            remitoDAO.updateRemito(Integer.parseInt(id));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void UpdateRecibo(String id, String factura){
        try {
            reciboDAO.updateRecibo(Integer.parseInt(id), factura);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String generarRemito(String id) {
        remitoDAO.generarRemito(Integer.parseInt(id));
        return "Remito generado";
    }

    public String generarRecibo(String id) {
        reciboDAO.generarRecibo(Integer.parseInt(id));
        return "Recibo generado";
    }

}
