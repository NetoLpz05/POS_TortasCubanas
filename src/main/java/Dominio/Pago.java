package Dominio;

import java.time.LocalDateTime;

/**
 *
 * @author
 */
public class Pago {

    private int idPago;
    private double monto;
    private LocalDateTime fecha;
    private double propina;
    private int pedidoIdPedido;
    private int cajaIdCaja;

    public Pago() {
    }

    public Pago(int idPago, double monto, LocalDateTime fecha, double propina, int pedidoIdPedido, int cajaIdCaja) {
        this.idPago = idPago;
        this.monto = monto;
        this.fecha = fecha;
        this.propina = propina;
        this.pedidoIdPedido = pedidoIdPedido;
        this.cajaIdCaja = cajaIdCaja;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getPropina() {
        return propina;
    }

    public void setPropina(double propina) {
        this.propina = propina;
    }

    public int getPedidoIdPedido() {
        return pedidoIdPedido;
    }

    public void setPedidoIdPedido(int pedidoIdPedido) {
        this.pedidoIdPedido = pedidoIdPedido;
    }

    public int getCajaIdCaja() {
        return cajaIdCaja;
    }

    public void setCajaIdCaja(int cajaIdCaja) {
        this.cajaIdCaja = cajaIdCaja;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Pago{"
                + "idPago=" + idPago
                + ", monto=" + monto
                + ", fecha=" + fecha
                + ", propina=" + propina
                + ", pedidoIdPedido=" + pedidoIdPedido
                + ", cajaIdCaja=" + cajaIdCaja
                + '}';
    }
}
