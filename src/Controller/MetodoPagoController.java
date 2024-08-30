package Controller;

import dao.implementaciones.MetodoDePagoDAO;
import dominio.MetodoDePago;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MetodoPagoController {

    private final MetodoDePagoDAO metodoDePagoDAO = new MetodoDePagoDAO();

    public List<MetodoDePago> listaMetodos() {

        List<MetodoDePago> listado;
        try {
            listado = metodoDePagoDAO.getMetodoDePagos();
            return listado;
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
