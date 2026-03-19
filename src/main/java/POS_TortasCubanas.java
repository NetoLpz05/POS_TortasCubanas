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

        try (Connection con = Conexion.obtenerConexion()) {

            if (con != null && !con.isClosed()) {

                System.out.println("Conexion a MySQL establecida.\n");

                ProductoDAO productoDAO = new ProductoDAO();

                List<Producto> productos = productoDAO.obtenerProductos();

                System.out.println("=== LISTA DE PRODUCTOS ===");
                for (Producto p : productos) {
                    System.out.println(p);
                }

                System.out.println("\n=== GENERANDO VENTA AUTOMATICA (Por temas de eficiencia, luego se cambiará) ===");
                List<ProductoPedido> carrito = new ArrayList<>();

                Producto p1 = productoDAO.buscarProducto(1);
                Producto p2 = productoDAO.buscarProducto(2);

                ProductoPedido d1 = new ProductoPedido();
                double precio1 = calcularPrecioConDetalles(p1, "Sin Queso");
                d1.setPrecio(precio1);
                d1.setDetalles("Sin Queso");

                //Caso en el que el cliente decida no añadir nada extra a su pedido
                ProductoPedido d2 = new ProductoPedido();
                double precio2 = calcularPrecioConDetalles(p2, "");
                d2.setPrecio(precio2);
                d2.setDetalles("");

                carrito.add(d1);
                carrito.add(d2);

                double subtotal = 0;
                for (ProductoPedido item : carrito) {
                    subtotal += item.getPrecio();
                }

                double iva = subtotal * 0.16;
                double total = subtotal + iva;

                Pedido pedido = new Pedido();
                pedido.setFecha(LocalDateTime.now());
                pedido.setSubtotal(subtotal);
                pedido.setIva(iva);
                pedido.setTotal(total);
                pedido.setEstadoPedidoIdEstadoPedido(1);
                pedido.setAdministradorIdAdministrador(1);
                pedido.setCajeroIdCajero(1);
                pedido.setClienteIdCliente(1);

                Pago pago = new Pago();
                pago.setMonto(200); // dinero recibido
                pago.setFecha(LocalDateTime.now());
                pago.setPropina(0);
                pago.setCajaIdCaja(1);

                ServicioVenta servicioVenta = new ServicioVenta();
                boolean resultado = servicioVenta.procesarVenta(pedido, carrito, pago);

                System.out.println("\n=== RESULTADO ===");
                System.out.println("Subtotal: $" + subtotal);
                System.out.println("IVA: $" + iva);
                System.out.println("Total: $" + total);
                System.out.println("Pago: $" + pago.getMonto());
                System.out.println("Cambio: $" + (pago.getMonto() - total));
                System.out.println("¿Venta exitosa? " + resultado);

            } else {
                System.err.println("Conexión nula");
            }

        } catch (SQLException e) {
            System.err.println("ERROR CRITICO: No se pudo conectar");
            e.printStackTrace();
        }
    }
    
    private static double calcularPrecioConDetalles(Producto producto, String detalles) {
        double precio = producto.getPrecioBase();

        if (detalles != null && !detalles.isEmpty()) {
            precio += 10;
        }

        return precio;
    }
}