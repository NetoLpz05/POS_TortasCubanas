package Datos;

import Dominio.Producto;
import java.util.List;

/**
 *
 * @author Angel
 */
interface IProductoDAO {
    Producto buscarProducto(int id);
    List<Producto> obtenerProductos();
}
