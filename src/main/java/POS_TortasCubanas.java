import Datos.PedidoDAO;
import Datos.ProductoDAO;
import Dominio.Pedido;
import Dominio.Producto;
import Dominio.ProductoPedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Knocmare
 */
public class POS_TortasCubanas {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ProductoDAO productoDAO = new ProductoDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();

        List<ProductoPedido> carrito = new ArrayList<>();
        int contadorPP = 1;

        System.out.println("=== NUEVA VENTA ===");

        int opcion;

        do {
            System.out.println("\nSelecciona un producto:");
            List<Producto> productos = productoDAO.obtenerProductos();

            for (int i = 0; i < productos.size(); i++) {
                System.out.println((i + 1) + ". " + productos.get(i).getNombre()
                        + " - $" + productos.get(i).getPrecioBase());
            }

            System.out.println("0. Terminar venta");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();

            if (opcion == 0) break;

            if (opcion < 1 || opcion > productos.size()) {
                System.out.println("Opción inválida.");
                continue;
            }

            Producto seleccionado = productos.get(opcion - 1);

            ProductoPedido pp = new ProductoPedido(
                    contadorPP++,
                    seleccionado.getPrecioBase(),
                    "Sin detalles",
                    0,
                    1,
                    seleccionado.getIdProducto()
            );

            carrito.add(pp);

            System.out.println("Agregado: " + seleccionado.getNombre());

        } while (true);

        if (carrito.isEmpty()) {
            System.out.println("Venta cancelada.");
            return;
        }

        double subtotal = 0;

        System.out.println("\n--- RESUMEN ---");
        for (ProductoPedido item : carrito) {
            Producto prod = productoDAO.buscarProducto(item.getProductoIdProducto());
            System.out.println(prod.getNombre() + " - $" + item.getPrecio());
            subtotal += item.getPrecio();
        }

        double iva = subtotal * 0.16;
        double total = subtotal + iva;

        System.out.println("Subtotal: $" + subtotal);
        System.out.println("IVA: $" + iva);
        System.out.println("TOTAL: $" + total);

        System.out.print("\nIngrese monto pagado: $");
        double pago = scanner.nextDouble();

        if (pago < total) {
            System.out.println("Pago insuficiente. Venta cancelada.");
            return;
        }

        double cambio = pago - total;

        Pedido pedido = new Pedido(
                pedidoDAO.obtenerPedidos().size() + 1,
                1, 1, 1, 1,
                total,
                iva,
                subtotal,
                LocalDateTime.now()
        );

        for (ProductoPedido item : carrito) {
            item.setPedidoIdPedido(pedido.getIdPedido());
        }

        pedidoDAO.guardarPedido(pedido);

        System.out.println("\n=== TICKET ===");
        for (ProductoPedido item : carrito) {
            Producto prod = productoDAO.buscarProducto(item.getProductoIdProducto());
            System.out.println(prod.getNombre() + " - $" + item.getPrecio());
        }

        System.out.println("-------------------");
        System.out.println("TOTAL: $" + total);
        System.out.println("PAGO: $" + pago);
        System.out.println("CAMBIO: $" + cambio);
        System.out.println("Gracias por su compra");

        scanner.close();
    }
}