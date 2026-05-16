package DTO;

import java.time.LocalDateTime;

public class VentaHistorialDTO {

    private int idPedido;
    private String nombreCliente;
    private LocalDateTime fecha;
    private double total;
    private String metodoPago;
    private String estatus;

    public VentaHistorialDTO() {
    }

    public VentaHistorialDTO(int idPedido, String nombreCliente, LocalDateTime fecha, double total, String metodoPago, String estatus) {
        this.idPedido = idPedido;
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
        this.total = total;
        this.metodoPago = metodoPago;
        this.estatus = estatus;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
