package Presentacion;

import Datos.CajeroDAO;
import Dominio.Cajero;
import Negocio.AdministradorBO;
import Negocio.IAdministradorBO;
import javax.swing.JFrame;

public final class AccesoHelper {

    private AccesoHelper() {
    }

    public static boolean abrirModuloDesdeClave(JFrame ventanaActual, String clave) {
        if (clave == null || clave.trim().isEmpty()) {
            return false;
        }

        String claveLimpia = clave.trim();
        CajeroDAO cajeroDAO = new CajeroDAO();
        Cajero cajero = cajeroDAO.iniciarSesion(claveLimpia);
        IAdministradorBO adminBO = new AdministradorBO();

        if (cajero != null) {
            ventanaActual.dispose();
            new Menu_Principal().setVisible(true);
            return true;
        }

        if (adminBO.validarPassword(claveLimpia)) {
            ventanaActual.dispose();
            new MenuAdministrador().setVisible(true);
            return true;
        }

        return false;
    }
}
