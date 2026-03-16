package Datos;

import Dominio.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Angel
 */
public class ProductoDAO implements IProductoDAO {

    private final List<Producto> productos = new ArrayList<>();

    public ProductoDAO() {

        productos.add(new Producto(1, "Torta Jamon", 45));
        productos.add(new Producto(2, "Torta Cubana", 60));
        productos.add(new Producto(3, "Refresco", 20));

    }

    @Override
    public Producto buscarProducto(int id) {

        for (Producto p : productos) {
            if (p.getIdProducto() == id) {
                return p;
            }
        }

        return null;
    }
}
