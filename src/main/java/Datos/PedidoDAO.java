package Datos;

import Dominio.Pago;
import Dominio.Pedido;
import Dominio.ProductoPedido;
import conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

public class PedidoDAO {

    public boolean registrarVentaCompleta(Pedido pedido, List<ProductoPedido> detalles, Pago pago) {
        Connection con = null;
        
        String sqlPedido = "INSERT INTO Pedido (fecha, subtotal, iva, total, EstadoPedido_idEstadoPedido, Cliente_idCliente, Administrador_idAdministrador, Cajero_idCajero) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO ProductoPedido (precio, detalles, Pedido_idPedido, Carrito_idCarrito, Producto_idProducto) VALUES (?, ?, ?, ?, ?)";
        String sqlPago = "INSERT INTO Pago (monto, fecha, propina, Pedido_idPedido, Caja_idCaja) VALUES (?, ?, ?, ?, ?)";
        String sqlActualizarCaja = "UPDATE Caja SET totalVentas = totalVentas + ? WHERE idCaja = ?";

        try {
            con = Conexion.obtenerConexion();
            con.setAutoCommit(false);

            PreparedStatement psPedido = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            
            psPedido.setTimestamp(1, Timestamp.valueOf(pedido.getFecha())); 
            psPedido.setDouble(2, pedido.getSubtotal());
            psPedido.setDouble(3, pedido.getIva());
            psPedido.setDouble(4, pedido.getTotal());
            psPedido.setInt(5, pedido.getEstadoPedidoIdEstadoPedido());
            
            if (pedido.getClienteIdCliente() > 0) {
                psPedido.setInt(6, pedido.getClienteIdCliente());
            } else {
                psPedido.setNull(6, java.sql.Types.INTEGER);
            }
            
            psPedido.setInt(7, pedido.getAdministradorIdAdministrador());
            
            if (pedido.getCajeroIdCajero() > 0) {
                psPedido.setInt(8, pedido.getCajeroIdCajero());
            } else {
                psPedido.setNull(8, java.sql.Types.INTEGER);
            }
                        
            int filasAfectadas = psPedido.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("Fallo al crear el pedido.");
            }

            int idPedidoGenerado = -1;
            try (ResultSet generatedKeys = psPedido.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idPedidoGenerado = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Fallo al obtener el ID del pedido.");
                }
            }

            PreparedStatement psDetalle = con.prepareStatement(sqlDetalle);
            for (ProductoPedido detalle : detalles) {
                psDetalle.setDouble(1, detalle.getPrecio());
                psDetalle.setString(2, detalle.getDetalles());
                psDetalle.setInt(3, idPedidoGenerado);

                psDetalle.setInt(4, 1);
                psDetalle.setInt(5, detalle.getProductoIdProducto());
                
                psDetalle.addBatch();
            }
            psDetalle.executeBatch();

            PreparedStatement psPago = con.prepareStatement(sqlPago);
            psPago.setDouble(1, pago.getMonto());
            psPago.setObject(2, pago.getFecha());
            psPago.setDouble(3, pago.getPropina());
            psPago.setInt(4, idPedidoGenerado);
            psPago.setInt(5, pago.getCajaIdCaja());
            
            psPago.executeUpdate();
            try (PreparedStatement psCaja = con.prepareStatement(sqlActualizarCaja)) {
                psCaja.setDouble(1, pedido.getTotal());
                psCaja.setInt(2, pago.getCajaIdCaja());
                psCaja.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
            
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}