package Datos;

import Dominio.Producto;
import conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements IProductoDAO {

    @Override
    public Producto buscarProducto(int id) {
        String sql = "SELECT * FROM Producto WHERE idProducto = ?";
        Producto producto = null;

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                producto = new Producto(
                        rs.getInt("idProducto"),
                        rs.getString("nombre"),
                        rs.getDouble("precioBase")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return producto;
    }

    @Override
    public List<Producto> obtenerProductos() {
        String sql = "SELECT * FROM Producto";
        List<Producto> lista = new ArrayList<>();

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("idProducto"),
                        rs.getString("nombre"),
                        rs.getDouble("precioBase")
                );
                lista.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}