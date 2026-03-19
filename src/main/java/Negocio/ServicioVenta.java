package Negocio;

import Datos.PedidoDAO;
import Dominio.Pago;
import Dominio.Pedido;
import Dominio.ProductoPedido;
import java.util.List;

public class ServicioVenta {
    
    private PedidoDAO pedidoDAO;

    public ServicioVenta() {
        this.pedidoDAO = new PedidoDAO();
    }

    /**
     * Este es el metodo para procesarVentas usando el GUI, para irlo implementando, si no les sirve cambienlo o corrigan como
     * Lo vean mas adecuado.
     */
    public boolean procesarVenta(Pedido pedido, List<ProductoPedido> carrito, Pago pago) {
        // Aquí podrías agregar validaciones de negocio antes de guardar, por ejemplo:
        if (carrito == null || carrito.isEmpty()) {
            System.err.println("Error: El carrito está vacío.");
            return false;
        }
        if (pago.getMonto() < pedido.getTotal()) {
            System.err.println("Error: El pago es menor al total del pedido.");
            return false;
        }

        // Si todo está bien, mandamos a guardar a la base de datos
        return pedidoDAO.registrarVentaCompleta(pedido, carrito, pago);
    }
}