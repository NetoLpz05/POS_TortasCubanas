package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    // Cambia "tortas_cubanas_db" por el nombre real de tu base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/tortas_cubanas_db?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root"; // User
    private static final String PASSWORD = "1234"; // Password

    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos.");
            e.printStackTrace();
        }
        return conexion;
    }
}