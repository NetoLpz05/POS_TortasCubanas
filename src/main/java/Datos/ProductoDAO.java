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
        productos.add(new Producto(1, "Torta Cubana", 85));
        productos.add(new Producto(1, "Torta Cubanita", 80));
        productos.add(new Producto(2, "Torta Sencilla", 75));
        productos.add(new Producto(1, "Orden de Quesadillas", 85));
        productos.add(new Producto(1, "Orden de Burritos de Machaca", 95));
        productos.add(new Producto(1, "Taco de Pierna o Pollo", 35));
        productos.add(new Producto(1, "Agua Fresca 1L", 37));
        productos.add(new Producto(1, "Agua Fresca 1/2L", 32));
        productos.add(new Producto(3, "Refresco 355ml", 27));
        productos.add(new Producto(3, "Refresco 600ml", 30));
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
    
    @Override
    public List<Producto> obtenerProductos() {
        return productos;
    }
}
