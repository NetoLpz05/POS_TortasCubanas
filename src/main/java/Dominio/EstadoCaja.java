package Dominio;

/**
 *
 * @author
 */
public class EstadoCaja {

    private int idEstadoCaja;
    private String nombre;

    public EstadoCaja() {
    }

    public EstadoCaja(int idEstadoCaja, String nombre) {
        this.idEstadoCaja = idEstadoCaja;
        this.nombre = nombre;
    }

    public int getIdEstadoCaja() {
        return idEstadoCaja;
    }

    public void setIdEstadoCaja(int idEstadoCaja) {
        this.idEstadoCaja = idEstadoCaja;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "EstadoCaja{"
                + "idEstadoCaja=" + idEstadoCaja
                + ", nombre='" + nombre + '\''
                + '}';
    }
}
