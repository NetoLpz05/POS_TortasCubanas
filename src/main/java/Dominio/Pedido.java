package Dominio;

import java.time.LocalDateTime;

/**
 *
 * @author
 */
public class Pedido {

    private int idPedido;
    private LocalDateTime fecha;
    private double subtotal;
    private double iva;
    private double total;
    private int estadoPedidoIdEstadoPedido;
    private int clienteIdCliente;
    private int administradorIdAdministrador;
    private int cajeroIdCajero;
    private int tipoOrdenIdTipoOrden;

    public Pedido() {
    }

    public Pedido(int idPedido, int cajeroIdCajero, int administradorIdAdministrador, int clienteIdCliente, int estadoPedidoIdEstadoPedido, int tipoOrdenIdTipoOrden, double total, double iva, double subtotal, LocalDateTime fecha) {
        this.idPedido = idPedido;
        this.cajeroIdCajero = cajeroIdCajero;
        this.administradorIdAdministrador = administradorIdAdministrador;
        this.clienteIdCliente = clienteIdCliente;
        this.estadoPedidoIdEstadoPedido = estadoPedidoIdEstadoPedido;
        this.tipoOrdenIdTipoOrden = tipoOrdenIdTipoOrden;
        this.total = total;
        this.iva = iva;
        this.subtotal = subtotal;
        this.fecha = fecha;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getCajeroIdCajero() {
        return cajeroIdCajero;
    }

    public void setCajeroIdCajero(int cajeroIdCajero) {
        this.cajeroIdCajero = cajeroIdCajero;
    }

    public int getAdministradorIdAdministrador() {
        return administradorIdAdministrador;
    }

    public void setAdministradorIdAdministrador(int administradorIdAdministrador) {
        this.administradorIdAdministrador = administradorIdAdministrador;
    }

    public int getClienteIdCliente() {
        return clienteIdCliente;
    }

    public void setClienteIdCliente(int clienteIdCliente) {
        this.clienteIdCliente = clienteIdCliente;
    }

    public int getEstadoPedidoIdEstadoPedido() {
        return estadoPedidoIdEstadoPedido;
    }

    public void setEstadoPedidoIdEstadoPedido(int estadoPedidoIdEstadoPedido) {
        this.estadoPedidoIdEstadoPedido = estadoPedidoIdEstadoPedido;
    }

    public int getTipoOrdenIdTipoOrden() {
        return tipoOrdenIdTipoOrden;
    }

    public void setTipoOrdenIdTipoOrden(int tipoOrdenIdTipoOrden) {
        this.tipoOrdenIdTipoOrden = tipoOrdenIdTipoOrden;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Pedido{"
                + "idPedido=" + idPedido
                + ", fecha=" + fecha
                + ", subtotal=" + subtotal
                + ", iva=" + iva
                + ", total=" + total
                + ", estadoPedidoIdEstadoPedido=" + estadoPedidoIdEstadoPedido
                + ", clienteIdCliente=" + clienteIdCliente
                + ", administradorIdAdministrador=" + administradorIdAdministrador
                + ", cajeroIdCajero=" + cajeroIdCajero
                + ", tipoOrdenIdTipoOrden=" + tipoOrdenIdTipoOrden + '}';
    }
}
