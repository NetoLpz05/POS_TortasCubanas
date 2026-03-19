import conexion.Conexion;
import java.sql.Connection;
import java.sql.SQLException;

public class POS_TortasCubanas {

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("  Iniciando Backend - POS Tortas Cubanas  ");
        System.out.println("==========================================");
        System.out.println("Verificando el estado de la base de datos...\n");

        // Utilizamos try-with-resources para que la conexión de prueba se cierre sola
        try (Connection con = Conexion.obtenerConexion()) {
            
            if (con != null && !con.isClosed()) {
                System.out.println("¡Éxito! Conexión a MySQL establecida correctamente.");
                System.out.println("El backend está listo y esperando peticiones de la interfaz gráfica.");
                
                // Aquí podrian abrir la pantall del GUI, nomas revisen bien el resto del codigo pls
                
            } else {
                System.err.println("Advertencia: La conexión devolvió nulo. Revisa la clase Conexion.java.");
            }
            
        } catch (SQLException e) {
            System.err.println("ERROR CRÍTICO: No se pudo conectar a la base de datos.");
            System.err.println("Por favor, verifica lo siguiente:");
            System.err.println(" 1. Que el servidor MySQL (XAMPP, Workbench, etc.) esté encendido.");
            System.err.println(" 2. Que la base de datos 'tortas_cubanas_db' exista.");
            System.err.println(" 3. Que el usuario y contraseña en Conexion.java sean correctos.");
            System.err.println("\nDetalles técnicos del error:");
            e.printStackTrace();
        }
    }
}