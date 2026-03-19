package Datos;

import Dominio.Cliente;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IClienteDAO {

    @Override
    public boolean agregar(Cliente cliente) {
        String sql = "INSERT INTO Cliente (telefono, nombre, direccion, rfc, correo) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, cliente.getTelefono());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getDireccion());
            ps.setString(4, cliente.getRfc());
            ps.setString(5, cliente.getCorreo());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE Cliente SET telefono=?, nombre=?, direccion=?, rfc=?, correo=? WHERE idCliente=?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, cliente.getTelefono());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getDireccion());
            ps.setString(4, cliente.getRfc());
            ps.setString(5, cliente.getCorreo());
            ps.setInt(6, cliente.getIdCliente());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int idCliente) {
        String sql = "DELETE FROM Cliente WHERE idCliente=?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idCliente);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Cliente consultar(int idCliente) {
        String sql = "SELECT * FROM Cliente WHERE idCliente=?";
        Cliente cliente = null;
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("telefono"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("rfc"),
                        rs.getString("correo")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    @Override
    public List<Cliente> consultarTodos() {
        String sql = "SELECT * FROM Cliente";
        List<Cliente> lista = new ArrayList<>();
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("idCliente"),
                    rs.getString("telefono"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("rfc"),
                    rs.getString("correo")
                );
                lista.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}