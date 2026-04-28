package Negocio;

/**
 * Interfaz para definir las operaciones de negocio del Administrador.
 */
public interface IAdministradorBO {
    /**
     * Valida si la contraseña ingresada es correcta.
     * @param password Contraseña a validar.
     * @return true si es válida, false en caso contrario.
     */
    boolean validarPassword(String password);
}