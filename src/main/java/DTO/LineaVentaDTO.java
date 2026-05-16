package DTO;

public class LineaVentaDTO {

    private String nombreProducto;
    private String detalles;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public LineaVentaDTO() {
    }

    public LineaVentaDTO(String nombreProducto, String detalles, int cantidad, double precioUnitario, double subtotal) {
        this.nombreProducto = nombreProducto;
        this.detalles = detalles;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
