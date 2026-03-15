public class EstadoPedido {

    private int idEstadoPedido;
    private String nombre;

    public EstadoPedido() {
    }

    public EstadoPedido(int idEstadoPedido, String nombre) {
        this.idEstadoPedido = idEstadoPedido;
        this.nombre = nombre;
    }

    public int getIdEstadoPedido() {
        return idEstadoPedido;
    }

    public void setIdEstadoPedido(int idEstadoPedido) {
        this.idEstadoPedido = idEstadoPedido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "EstadoPedido{" +
                "idEstadoPedido=" + idEstadoPedido +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}