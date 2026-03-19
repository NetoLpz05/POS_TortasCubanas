package Dominio;

/**
 *
 * @author
 */
public class Producto {

    private int idProducto;
    private String nombre;
    private double precioBase;
    private String categoria;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, double precioBase) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precioBase = precioBase;
    }

    public Producto(int idProducto, String nombre, double precioBase, String categoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.categoria = categoria;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Producto{"
                + "idProducto=" + idProducto
                + ", nombre='" + nombre + '\''
                + ", precioBase=" + precioBase
                + '}';
    }
}
