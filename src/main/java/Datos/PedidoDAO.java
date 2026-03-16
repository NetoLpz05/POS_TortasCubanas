package Datos;

import Dominio.Pedido;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Angel
 */
public class PedidoDAO implements IPedidoDAO {

    private final List<Pedido> pedidos = new ArrayList<>();

    @Override
    public void guardarPedido(Pedido pedido) {

        pedidos.add(pedido);

        System.out.println("Venta registrada correctamente");
    }
}
