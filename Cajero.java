public class Cajero {

    private int idCajero;
    private String contrasena;
    private String tipo;

    public Cajero() {
    }

    public Cajero(int idCajero, String contrasena, String tipo) {
        this.idCajero = idCajero;
        this.contrasena = contrasena;
        this.tipo = tipo;
    }

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Cajero{" +
                "idCajero=" + idCajero +
                ", contrasena='" + contrasena + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}