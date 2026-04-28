import conexion.Conexion;
import java.sql.Connection;
import java.sql.SQLException;

import Datos.ProductoDAO;
import Datos.PedidoDAO;

import Dominio.Producto;
import Dominio.Pedido;
import Dominio.ProductoPedido;
import Dominio.Pago;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Scanner;

public class POS_TortasCubanas {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("==== TEST DAO DIRECTO (BD REAL) ====");

        try (Connection con = Conexion.obtenerConexion()) {

            if (con == null || con.isClosed()) {
                System.out.println("Error de conexión");
                return;
            }

            System.out.println("Conectado a BD\n");

            ProductoDAO productoDAO = new ProductoDAO();
            PedidoDAO pedidoDAO = new PedidoDAO();

            // ================== TIPO ORDEN ==================
            System.out.println("Tipo de orden:");
            System.out.println("1. Comer aqui");
            System.out.println("2. Para llevar");
//            int tipoOrdenId = sc.nextInt();

            // ================== PRODUCTO ==================
            Producto producto = productoDAO.buscarProducto(1);

            if (producto == null) {
                System.out.println("Producto no encontrado");
                return;
            }

            // ================== DETALLE ==================
            List<ProductoPedido> detalles = new ArrayList<>();

            ProductoPedido pp = new ProductoPedido();
            pp.setProductoIdProducto(producto.getIdProducto());
            pp.setPrecio(producto.getPrecioBase());
            pp.setDetalles("Prueba BD");
            pp.setCarritoIdCarrito(1);
            pp.setPedidoIdPedido(1);

            detalles.add(pp);

            // ================== TOTALES ==================
            double subtotal = producto.getPrecioBase();
            double iva = subtotal * 0.16;
            double total = subtotal + iva;

            // ================== PEDIDO ==================
            Pedido pedido = new Pedido();
            pedido.setIdPedido(1);
            pedido.setFecha(LocalDateTime.now());
            pedido.setSubtotal(subtotal);
            pedido.setIva(iva);
            pedido.setTotal(total);
            pedido.setEstadoPedidoIdEstadoPedido(1);
            pedido.setAdministradorIdAdministrador(1);
            pedido.setCajeroIdCajero(1);
            pedido.setClienteIdCliente(1);

            // 🔥 IMPORTANTE
//            pedido.setTipoOrden(tipoOrdenId);

            // ================== DEBUG ==================
            System.out.println("\n=== DEBUG PEDIDO ===");
            System.out.println(pedido);

            // ================== PAGO ==================
            Pago pago = new Pago();
            pago.setMonto(total);
            pago.setFecha(LocalDateTime.now());
            pago.setPropina(0);
            pago.setCajaIdCaja(1);

            // ================== INSERT DIRECTO ==================
            boolean resultado = pedidoDAO.registrarVentaCompleta(pedido, detalles, pago);

            // ================== RESULTADO ==================
            System.out.println("\n=== RESULTADO ===");
            System.out.println("¿Insert directo exitoso? " + resultado);

            if (resultado) {
                System.out.println("\nVerifica en BD con:");
                System.out.println("SELECT idPedido, TipoOrden_idTipoOrden FROM Pedido ORDER BY idPedido DESC;");
                System.out.println("SELECT * FROM ProductoPedido ORDER BY idPedido DESC;");
                System.out.println("SELECT * FROM Pago ORDER BY idPago DESC;");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}