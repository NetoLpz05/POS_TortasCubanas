import java.time.LocalDateTime;

public class Carrito {

    private int idCarrito;
    private LocalDateTime ultimaActualizacion;
    private int clienteIdCliente;

    public Carrito() {
    }

    public Carrito(int idCarrito, LocalDateTime ultimaActualizacion, int clienteIdCliente) {
        this.idCarrito = idCarrito;
        this.ultimaActualizacion = ultimaActualizacion;
        this.clienteIdCliente = clienteIdCliente;
    }

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public LocalDateTime getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(LocalDateTime ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public int getClienteIdCliente() {
        return clienteIdCliente;
    }

    public void setClienteIdCliente(int clienteIdCliente) {
        this.clienteIdCliente = clienteIdCliente;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Carrito{" +
                "idCarrito=" + idCarrito +
                ", ultimaActualizacion=" + ultimaActualizacion +
                ", clienteIdCliente=" + clienteIdCliente +
                '}';
    }
}