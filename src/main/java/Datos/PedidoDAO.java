package Datos;

import DTO.DetalleVentaDTO;
import DTO.LineaVentaDTO;
import DTO.VentaHistorialDTO;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PedidoDAO {

    public boolean registrarVentaCompleta(Pedido pedido, List<ProductoPedido> detalles, Pago pago) {
        Connection con = null;
        
        String sqlPedido = "INSERT INTO Pedido (tipoOrden, fecha, subtotal, iva, total, EstadoPedido_idEstadoPedido, Cliente_idCliente, Administrador_idAdministrador, Cajero_idCajero) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO ProductoPedido (precio, detalles, Pedido_idPedido, Carrito_idCarrito, Producto_idProducto) VALUES (?, ?, ?, ?, ?)";
        String sqlPago = "INSERT INTO Pago (monto, fecha, propina, metodoPago, Pedido_idPedido, Caja_idCaja) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlActualizarCaja = "UPDATE Caja SET totalVentas = totalVentas + ? WHERE idCaja = ?";

        try {
            con = Conexion.obtenerConexion();
            con.setAutoCommit(false);

            PreparedStatement psPedido = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            
            psPedido.setString(1, pedido.getTipoOrden());
            psPedido.setTimestamp(2, Timestamp.valueOf(pedido.getFecha())); 
            psPedido.setDouble(3, pedido.getSubtotal());
            psPedido.setDouble(4, pedido.getIva());
            psPedido.setDouble(5, pedido.getTotal());
            psPedido.setInt(6, pedido.getEstadoPedidoIdEstadoPedido());
            
            if (pedido.getClienteIdCliente() > 0) {
                psPedido.setInt(7, pedido.getClienteIdCliente());
            } else {
                psPedido.setNull(7, java.sql.Types.INTEGER);
            }
            
            psPedido.setInt(8, pedido.getAdministradorIdAdministrador());
            
            if (pedido.getCajeroIdCajero() > 0) {
                psPedido.setInt(9, pedido.getCajeroIdCajero());
            } else {
                psPedido.setNull(9, java.sql.Types.INTEGER);
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
            psPago.setString(4, obtenerMetodoPago(pago));
            psPago.setInt(5, idPedidoGenerado);
            psPago.setInt(6, pago.getCajaIdCaja());
            
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
    
public Pedido buscarPorId(int idPedido) {
    String sql = "SELECT * FROM Pedido WHERE idPedido = ?";
    try (Connection con = Conexion.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setInt(1, idPedido);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("idPedido"));
                // Mapear los demás campos importantes...
                pedido.setTotal(rs.getDouble("total"));
                pedido.setEstadoPedidoIdEstadoPedido(rs.getInt("EstadoPedido_idEstadoPedido"));
                return pedido;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    public boolean cambiarEstado(int idPedido, int idEstadoNuevo) {
    String sql = "UPDATE Pedido SET EstadoPedido_idEstadoPedido = ? WHERE idPedido = ?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEstadoNuevo);
            ps.setInt(2, idPedido);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> consultarEstadosDisponibles() {
        List<String> estados = new ArrayList<>();
        estados.add("Pagado");
        estados.add("Pendiente");
        estados.add("Cancelado");

        return estados;
    }

    public List<VentaHistorialDTO> consultarHistorialVentas(String estatus, LocalDate fechaInicio, LocalDate fechaFin) {
        List<VentaHistorialDTO> ventas = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.idPedido, p.fecha, p.total, ");
        sql.append("COALESCE(c.nombre, 'Publico General') AS cliente, ");
        sql.append("COALESCE(pa.metodoPago, 'EFECTIVO') AS metodoPago, ");
        sql.append("CASE ");
        sql.append("WHEN UPPER(COALESCE(ep.nombre, '')) = 'CANCELADO' THEN 'Cancelado' ");
        sql.append("WHEN pa.idPago IS NOT NULL THEN 'Pagado' ");
        sql.append("ELSE 'Pendiente' ");
        sql.append("END AS estatus ");
        sql.append("FROM Pedido p ");
        sql.append("LEFT JOIN Cliente c ON c.idCliente = p.Cliente_idCliente ");
        sql.append("LEFT JOIN Pago pa ON pa.Pedido_idPedido = p.idPedido ");
        sql.append("LEFT JOIN EstadoPedido ep ON ep.idEstadoPedido = p.EstadoPedido_idEstadoPedido ");
        sql.append("WHERE 1 = 1 ");

        List<Object> parametros = new ArrayList<>();

        if (estatus != null && !estatus.trim().isEmpty()) {
            if ("Pagado".equalsIgnoreCase(estatus)) {
                sql.append("AND pa.idPago IS NOT NULL ");
                sql.append("AND UPPER(COALESCE(ep.nombre, '')) <> 'CANCELADO' ");
            } else if ("Cancelado".equalsIgnoreCase(estatus)) {
                sql.append("AND UPPER(COALESCE(ep.nombre, '')) = 'CANCELADO' ");
            } else if ("Pendiente".equalsIgnoreCase(estatus)) {
                sql.append("AND pa.idPago IS NULL ");
                sql.append("AND UPPER(COALESCE(ep.nombre, '')) <> 'CANCELADO' ");
            }
        }

        if (fechaInicio != null) {
            sql.append("AND DATE(p.fecha) >= ? ");
            parametros.add(java.sql.Date.valueOf(fechaInicio));
        }

        if (fechaFin != null) {
            sql.append("AND DATE(p.fecha) <= ? ");
            parametros.add(java.sql.Date.valueOf(fechaFin));
        }

        sql.append("ORDER BY p.fecha DESC, p.idPedido DESC");

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VentaHistorialDTO venta = new VentaHistorialDTO();
                    venta.setIdPedido(rs.getInt("idPedido"));
                    Timestamp fecha = rs.getTimestamp("fecha");
                    venta.setFecha(fecha == null ? null : fecha.toLocalDateTime());
                    venta.setNombreCliente(rs.getString("cliente"));
                    venta.setTotal(rs.getDouble("total"));
                    venta.setMetodoPago(rs.getString("metodoPago"));
                    venta.setEstatus(rs.getString("estatus"));
                    ventas.add(venta);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ventas;
    }

    public DetalleVentaDTO consultarDetalleVenta(int idPedido) {
        String sqlCabecera =
                "SELECT p.idPedido, p.fecha, p.subtotal, p.iva, p.total, p.tipoOrden, " +
                "CASE " +
                "WHEN UPPER(COALESCE(ep.nombre, '')) = 'CANCELADO' THEN 'Cancelado' " +
                "WHEN pa.idPago IS NOT NULL THEN 'Pagado' " +
                "ELSE 'Pendiente' " +
                "END AS estatus, " +
                "COALESCE(c.nombre, 'Publico General') AS cliente, c.telefono, c.direccion, " +
                "COALESCE(pa.metodoPago, 'EFECTIVO') AS metodoPago, " +
                "COALESCE(pa.monto, 0) AS montoPago, COALESCE(pa.propina, 0) AS propina " +
                "FROM Pedido p " +
                "LEFT JOIN Cliente c ON c.idCliente = p.Cliente_idCliente " +
                "LEFT JOIN Pago pa ON pa.Pedido_idPedido = p.idPedido " +
                "LEFT JOIN EstadoPedido ep ON ep.idEstadoPedido = p.EstadoPedido_idEstadoPedido " +
                "WHERE p.idPedido = ?";

        String sqlLineas =
                "SELECT pr.nombre, pp.detalles, pp.precio " +
                "FROM ProductoPedido pp " +
                "LEFT JOIN Producto pr ON pr.idProducto = pp.Producto_idProducto " +
                "WHERE pp.Pedido_idPedido = ? " +
                "ORDER BY pp.idProductoPedido";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement psCabecera = con.prepareStatement(sqlCabecera);
             PreparedStatement psLineas = con.prepareStatement(sqlLineas)) {

            psCabecera.setInt(1, idPedido);
            DetalleVentaDTO detalle = null;

            try (ResultSet rs = psCabecera.executeQuery()) {
                if (rs.next()) {
                    detalle = new DetalleVentaDTO();
                    detalle.setIdPedido(rs.getInt("idPedido"));
                    Timestamp fechaPedido = rs.getTimestamp("fecha");
                    detalle.setFechaPedido(fechaPedido == null ? null : fechaPedido.toLocalDateTime());
                    detalle.setSubtotal(rs.getDouble("subtotal"));
                    detalle.setIva(rs.getDouble("iva"));
                    detalle.setTotal(rs.getDouble("total"));
                    detalle.setTipoOrden(rs.getString("tipoOrden"));
                    detalle.setEstatus(rs.getString("estatus"));
                    detalle.setNombreCliente(rs.getString("cliente"));
                    detalle.setTelefonoCliente(rs.getString("telefono"));
                    detalle.setDireccionCliente(rs.getString("direccion"));
                    detalle.setMetodoPago(rs.getString("metodoPago"));
                    detalle.setMontoPagado(rs.getDouble("montoPago"));
                    detalle.setPropina(rs.getDouble("propina"));
                }
            }

            if (detalle == null) {
                return null;
            }

            psLineas.setInt(1, idPedido);
            try (ResultSet rs = psLineas.executeQuery()) {
                Map<String, LineaVentaDTO> agrupadas = new LinkedHashMap<>();

                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String detalles = rs.getString("detalles");
                    double precio = rs.getDouble("precio");
                    String clave = (nombre == null ? "" : nombre) + "|" + (detalles == null ? "" : detalles) + "|" + precio;

                    LineaVentaDTO linea = agrupadas.get(clave);
                    if (linea == null) {
                        linea = new LineaVentaDTO();
                        linea.setNombreProducto(nombre);
                        linea.setDetalles(detalles);
                        linea.setCantidad(0);
                        linea.setPrecioUnitario(precio);
                        linea.setSubtotal(0);
                        agrupadas.put(clave, linea);
                    }

                    linea.setCantidad(linea.getCantidad() + 1);
                    linea.setSubtotal(linea.getCantidad() * linea.getPrecioUnitario());
                }

                detalle.setLineas(new ArrayList<>(agrupadas.values()));
            }

            return detalle;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String obtenerMetodoPago(Pago pago) {
        if (pago == null) {
            return "EFECTIVO";
        }

        String metodoPago = pago.getMetodoPago();
        if (metodoPago == null || metodoPago.trim().isEmpty()) {
            return "EFECTIVO";
        }

        return metodoPago.trim().toUpperCase();
    }
}
