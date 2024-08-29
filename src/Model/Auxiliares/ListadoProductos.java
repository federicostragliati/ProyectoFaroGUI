package Model.Auxiliares;

public class ListadoProductos {

    private String id;
    private String detalle;
    private String unidad;
    private String cantidad;
    private String valor;
    private String valorPorCantidad;

    public ListadoProductos() {
    }

    public ListadoProductos(String id, String detalle, String unidad, String cantidad, String valor, String valorPorCantidad) {
        this.id = id;
        this.detalle = detalle;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.valor = valor;
        this.valorPorCantidad = valorPorCantidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValorPorCantidad() {
        return valorPorCantidad;
    }

    public void setValorPorCantidad(String valorPorCantidad) {
        this.valorPorCantidad = valorPorCantidad;
    }

    @Override
    public String toString() {
        return "ListadoProductos{" +
                "id='" + id + '\'' +
                ", detalle='" + detalle + '\'' +
                ", unidad='" + unidad + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", valor='" + valor + '\'' +
                ", valorPorCantidad='" + valorPorCantidad + '\'' +
                '}';
    }
}
