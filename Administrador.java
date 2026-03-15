public class Administrador {

    private int idAdministrador;
    private String contrasena;
    private String tipo;

    public Administrador() {
    }

    public Administrador(int idAdministrador, String contrasena, String tipo) {
        this.idAdministrador = idAdministrador;
        this.contrasena = contrasena;
        this.tipo = tipo;
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
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
        return "Administrador{" +
                "idAdministrador=" + idAdministrador +
                ", contrasena='" + contrasena + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}