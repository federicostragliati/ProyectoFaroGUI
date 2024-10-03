package Controller;

import dao.implementaciones.ReciboDAO;
import dao.implementaciones.RemitoDAO;

public class ComprobantesController {

    RemitoDAO remitoDAO = new RemitoDAO();
    ReciboDAO reciboDAO = new ReciboDAO();

    public String generarRemito(String id) {
        remitoDAO.generarRemito(Integer.parseInt(id));
        return "Remito generado";
    }

    public String generarRecibo(String id) {
        reciboDAO.generarRecibo(Integer.parseInt(id));
        return "Recibo generado";
    }

}
