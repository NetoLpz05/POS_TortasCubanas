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
        if (pedido.getTotal() <= 0) {
            System.err.println("Error: Total inválido.");
            return false;
        }
        if (pago.getMonto() < pedido.getTotal()) {
            System.err.println("Error: El pago es menor al total del pedido.");
            return false;
        }
        if (pedido.getClienteIdCliente() <= 0) {
            pedido.setClienteIdCliente(1);
        }
        //venta
        boolean resultado = pedidoDAO.registrarVentaCompleta(pedido, carrito, pago);

        if (resultado) {
            System.out.println("Venta realizada");
        } else {
            System.err.println(" Error al registrar venta");
        }


        // se manda a base datos
        return resultado;
    }
}