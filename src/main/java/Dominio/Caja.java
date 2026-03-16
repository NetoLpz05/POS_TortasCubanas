package Dominio;

import java.time.LocalDateTime;

/**
 *
 * @author
 */
public class Caja {

    private int idCaja;
    private double montoInicial;
    private double saldoFinal;
    private double totalVentas;
    private LocalDateTime fechaApertura;
    private LocalDateTime fechaCierre;
    private int estadoCajaIdEstadoCaja;
    private int administradorIdAdministrador;

    public Caja() {
    }

    public Caja(int idCaja, double montoInicial, double saldoFinal, double totalVentas, LocalDateTime fechaApertura, LocalDateTime fechaCierre, int estadoCajaIdEstadoCaja, int administradorIdAdministrador) {
        this.idCaja = idCaja;
        this.montoInicial = montoInicial;
        this.saldoFinal = saldoFinal;
        this.totalVentas = totalVentas;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.estadoCajaIdEstadoCaja = estadoCajaIdEstadoCaja;
        this.administradorIdAdministrador = administradorIdAdministrador;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public double getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(double montoInicial) {
        this.montoInicial = montoInicial;
    }

    public double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public double getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(double totalVentas) {
        this.totalVentas = totalVentas;
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDateTime fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public int getEstadoCajaIdEstadoCaja() {
        return estadoCajaIdEstadoCaja;
    }

    public void setEstadoCajaIdEstadoCaja(int estadoCajaIdEstadoCaja) {
        this.estadoCajaIdEstadoCaja = estadoCajaIdEstadoCaja;
    }

    public int getAdministradorIdAdministrador() {
        return administradorIdAdministrador;
    }

    public void setAdministradorIdAdministrador(int administradorIdAdministrador) {
        this.administradorIdAdministrador = administradorIdAdministrador;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Caja{"
                + "idCaja=" + idCaja
                + ", montoInicial=" + montoInicial
                + ", saldoFinal=" + saldoFinal
                + ", totalVentas=" + totalVentas
                + ", fechaApertura=" + fechaApertura
                + ", fechaCierre=" + fechaCierre
                + ", estadoCajaIdEstadoCaja=" + estadoCajaIdEstadoCaja
                + ", administradorIdAdministrador=" + administradorIdAdministrador
                + '}';
    }
}
