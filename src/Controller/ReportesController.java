package Controller;

import Model.Validaciones.Herramientas;
import dao.implementaciones.ReportesDAO;

import java.util.Date;

public class ReportesController {

    private final ReportesDAO reportesDAO = new ReportesDAO();

    public String getStockVigente () {
        reportesDAO.getReporteStock();
        return "Reporte Generado";
    }

    public String getStockProducto (String id) {
        reportesDAO.getHistoricoDeStock(Integer.parseInt(Herramientas.extraerID(id)));
        return "Reporte Generado";
    }

    public String getVentaEnFechas(String inicio, String fin) {

        Date fecha1 = Herramientas.convertirStringADate(inicio);
        Date fecha2 = Herramientas.convertirStringADate(fin);

        if (fecha1.after(fecha2)) {
            return "La fecha inicial no puede ser posterior a la fecha final";
        }

        reportesDAO.getVentasEntreDosFechas(fecha1,fecha2);
        return "Reporte Generado";
    }

    public String getVentasAbiertas() {
        reportesDAO.getVentasPorCerrar();
        return "Reporte Generado";
    }

    public String getVentasAbiertasXCliente(String id) {
        reportesDAO.getVentasPorCerrarPorCliente(Integer.parseInt(Herramientas.extraerID(id)));
        return "Reporte Generado";
    }

    public String getVentasAbiertasXClienteEntreFechas (String id, String inicio, String fin) {

        Date fecha1 = Herramientas.convertirStringADate(inicio);
        Date fecha2 = Herramientas.convertirStringADate(fin);

        if (fecha1.after(fecha2)) {
            return "La fecha inicial no puede ser posterior a la fecha final";
        }

        reportesDAO.getVentaEntreDosFechasPorCliente(fecha1,fecha2,Integer.parseInt(Herramientas.extraerID(id)));
        return "Reporte Generado";
    }


}
