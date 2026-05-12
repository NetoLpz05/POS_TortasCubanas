package Datos;

import Dominio.Cajero;
import conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Usuario
 */
public class CajeroDAO {

    public Cajero iniciarSesion(String contrasena) {
        String sql = "SELECT * FROM Cajero WHERE contrasena = ?";

        try (Connection con = Conexion.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, contrasena);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Cajero(
                        rs.getInt("idCajero"),
                        rs.getString("contrasena"),
                        rs.getString("tipo"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}