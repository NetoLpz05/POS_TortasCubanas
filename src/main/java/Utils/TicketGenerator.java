package Utils;

import Dominio.Pago;
import Dominio.ProductoPedido;
import Dominio.Pedido;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class TicketGenerator {

    public static File generarTicket(Pedido pedido, List<ProductoPedido> carrito, Pago pago) throws IOException {

        String nombreArchivo = "ticket_" + System.currentTimeMillis() + ".txt";
        File archivo = new File(nombreArchivo);

        try (FileWriter writer = new FileWriter(archivo)) {

            writer.write("====== TORTAS CUBANAS ======\n");
            writer.write("Fecha: " + pedido.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n");
            writer.write("Tipo: " + pedido.getTipoOrden() + "\n");
            writer.write("--------------------------------\n");

            for (ProductoPedido p : carrito) {
                writer.write(String.format("$%.2f\n", p.getPrecio()));

                if (p.getDetalles() != null && !p.getDetalles().isEmpty()) {
                    writer.write("  - " + p.getDetalles() + "\n");
                }

                writer.write("\n");
            }

            writer.write("--------------------------------\n");
            writer.write(String.format("SUBTOTAL: $%.2f\n", pedido.getSubtotal()));
            writer.write(String.format("IVA: $%.2f\n", pedido.getIva()));
            writer.write(String.format("TOTAL: $%.2f\n", pedido.getTotal()));
            writer.write(String.format("PAGO: $%.2f\n", pago.getMonto()));
            writer.write(String.format("CAMBIO: $%.2f\n", (pago.getMonto() - pedido.getTotal())));
            writer.write("================================\n");

            writer.flush();
        }

        System.out.println("Ticket generado: " + archivo.getAbsolutePath());
        return archivo;
    }
}
