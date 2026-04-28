package Datos;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministradorDAO {

    /**
     * Valida si la contraseña ingresada pertenece a un Administrador registrado.
     * @param password La contraseña a validar.
     * @return true si la contraseña es correcta y existe un admin con ella, false en caso contrario.
     */
    public boolean validarPassword(String password) {
        // Buscamos si hay al menos un registro en la tabla con esa contraseña
        String sql = "SELECT COUNT(*) FROM Administrador WHERE contrasena = ?";
        
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int coincidencias = rs.getInt(1);
                    return coincidencias > 0; // Retorna true si encontró 1 o más
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al validar contraseña de administrador: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}