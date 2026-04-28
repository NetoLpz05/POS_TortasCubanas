package Negocio;

import Datos.AdministradorDAO;

/**
 * Implementación de la lógica de negocio para el Administrador.
 */
public class AdministradorBO implements IAdministradorBO {
    
    private AdministradorDAO administradorDAO;

    public AdministradorBO() {
        // Inicializamos el DAO
        this.administradorDAO = new AdministradorDAO();
    }

    @Override
    public boolean validarPassword(String password) {       
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        return administradorDAO.validarPassword(password);
    }
}