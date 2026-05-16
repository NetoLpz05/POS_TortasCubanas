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
        String sql = "INSERT INTO Cliente (telefono, nombre, direccion, rfc, correo, activo) VALUES (?, ?, ?, ?, ?, 1)";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, normalizarTelefono(cliente.getTelefono()));
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
        String sql = "UPDATE Cliente SET telefono = ?, nombre = ?, direccion = ?, rfc = ?, correo = ? WHERE idCliente = ?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, normalizarTelefono(cliente.getTelefono()));
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
        String sql = "UPDATE Cliente SET activo = 0 WHERE idCliente = ?";
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
        String sql = "SELECT * FROM Cliente WHERE idCliente = ?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cliente> consultarTodos() {
        String sql = "SELECT * FROM Cliente WHERE activo = 1 ORDER BY nombre";
        List<Cliente> lista = new ArrayList<>();
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Cliente buscarPorTelefono(String telefono) {
        String sql = "SELECT * FROM Cliente WHERE telefono = ? AND activo = 1";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, normalizarTelefono(telefono));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por telefono: " + e.getMessage());
        }
        return null;
    }

    public boolean existeTelefono(String telefono, Integer idClienteExcluir) {
        String sql = "SELECT COUNT(*) FROM Cliente WHERE telefono = ? AND activo = 1";
        if (idClienteExcluir != null) {
            sql += " AND idCliente <> ?";
        }

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, normalizarTelefono(telefono));
            if (idClienteExcluir != null) {
                ps.setInt(2, idClienteExcluir);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("idCliente"),
                rs.getString("telefono"),
                rs.getString("nombre"),
                rs.getString("direccion"),
                rs.getString("rfc"),
                rs.getString("correo")
        );
    }

    private String normalizarTelefono(String telefono) {
        if (telefono == null) {
            return "";
        }
        return telefono.replaceAll("\\D", "").trim();
    }
}
