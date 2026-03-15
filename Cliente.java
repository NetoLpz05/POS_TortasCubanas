public class Cliente {

    private int idCliente;
    private String telefono;
    private String nombre;
    private String direccion;
    private String rfc;
    private String correo;

    public Cliente() {
    }

    public Cliente(int idCliente, String telefono, String nombre, String direccion, String rfc, String correo) {
        this.idCliente = idCliente;
        this.telefono = telefono;
        this.nombre = nombre;
        this.direccion = direccion;
        this.rfc = rfc;
        this.correo = correo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", telefono='" + telefono + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", rfc='" + rfc + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}