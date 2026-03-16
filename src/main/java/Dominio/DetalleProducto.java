package Dominio;

/**
 *
 * @author
 */
public class DetalleProducto {

    private int idDetalleProducto;
    private String detalle;
    private int productoIdProducto;

    public DetalleProducto() {
    }

    public DetalleProducto(int idDetalleProducto, String detalle, int productoIdProducto) {
        this.idDetalleProducto = idDetalleProducto;
        this.detalle = detalle;
        this.productoIdProducto = productoIdProducto;
    }

    public int getIdDetalleProducto() {
        return idDetalleProducto;
    }

    public void setIdDetalleProducto(int idDetalleProducto) {
        this.idDetalleProducto = idDetalleProducto;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getProductoIdProducto() {
        return productoIdProducto;
    }

    public void setProductoIdProducto(int productoIdProducto) {
        this.productoIdProducto = productoIdProducto;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "DetalleProducto{"
                + "idDetalleProducto=" + idDetalleProducto
                + ", detalle='" + detalle + '\''
                + ", productoIdProducto=" + productoIdProducto
                + '}';
    }
}
