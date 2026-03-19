import conexion.Conexion;
import java.sql.Connection;
import java.sql.SQLException;

import Datos.ProductoDAO;

import Dominio.Producto;
import Dominio.Pedido;
import Dominio.ProductoPedido;
import Dominio.Pago;

import Negocio.ServicioVenta;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

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
                ProductoDAO productoDAO = new ProductoDAO();


                List<Producto> productos = productoDAO.obtenerProductos();
                System.out.println("Productos cargados: " + productos.size());

                System.out.println("=== LISTA DE PRODUCTOS ===");

                for (Producto p : productos) {
                    System.out.println(p);
                }
                System.out.println("\n=== PRUEBA DE VENTA ===");

                ServicioVenta servicioVenta = new ServicioVenta();


                Pedido pedido = new Pedido();
                pedido.setFecha(LocalDateTime.now());
                pedido.setSubtotal(85);
                pedido.setIva(13.6);
                pedido.setTotal(98.6);
                pedido.setEstadoPedidoIdEstadoPedido(1);
                pedido.setAdministradorIdAdministrador(1);
                pedido.setCajeroIdCajero(1);
                pedido.setClienteIdCliente(1);

                List<ProductoPedido> carrito = new ArrayList<>();

                ProductoPedido detalle = new ProductoPedido();
                detalle.setPrecio(85);
                detalle.setDetalles("Sin cebolla");
                detalle.setProductoIdProducto(1);

                carrito.add(detalle);

                Pago pago = new Pago();
                pago.setMonto(100);
                pago.setFecha(LocalDateTime.now());
                pago.setPropina(0);
                pago.setCajaIdCaja(1);

                boolean resultado = servicioVenta.procesarVenta(pedido, carrito, pago);

                System.out.println("¿Venta exitosa? " + resultado);



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