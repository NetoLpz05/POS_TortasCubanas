package Presentacion;

/**
 *
 * @author Usuario
 */
import Datos.ClienteDAO;
import Datos.ProductoDAO;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Dominio.*;
import Negocio.ServicioVenta;
import Utils.TicketGenerator;
import java.io.File;

public class Menu_Principal extends JFrame {

    private OverlayPanel overlay;

    private List<ProductoPedido> carrito = new ArrayList<>();
    private DefaultListModel<String> modeloOrden;
    private JList<String> listaOrden;
    private double total = 0;
    private JPanel productosPanel;
    private JLabel totalLabel;

    public Menu_Principal() {
        setTitle("Tortas Cubanas POS");
        setSize(1100, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        overlay = new OverlayPanel();
        setGlassPane(overlay);

        add(createTopBar(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);
        productosPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        add(new JScrollPane(productosPanel), BorderLayout.CENTER);
        add(createOrdenPanel(), BorderLayout.EAST);

        cargarProductos("TORTAS");
    }

    private JPanel createTopBar() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel usuarioLabel = new JLabel("Usuario: Cajero");
        usuarioLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JButton cambiarCuentaButton = new JButton("Cambiar cuenta");
        cambiarCuentaButton.addActionListener(e -> cambiarCuenta());

        panel.add(usuarioLabel, BorderLayout.WEST);
        panel.add(cambiarCuentaButton, BorderLayout.EAST);

        return panel;
    }

    private JPanel createSidebar() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setPreferredSize(new Dimension(120, 0));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(createMenuButton("TORTAS", "TORTAS"));
        panel.add(createMenuButton("ANTOJITOS", "ANTOJITOS"));
        panel.add(createMenuButton("BEBIDAS", "BEBIDAS"));
        panel.add(createMenuButton("EXTRAS", "EXTRAS"));

        return panel;
    }

    private JButton createMenuButton(String text, String categoria) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(120, 60));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(45, 45, 45));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.addActionListener(e -> cargarProductos(categoria));
        return btn;
    }

    private JPanel createCard(String nombre, double precio, int idProducto, String categoria) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        card.setPreferredSize(new Dimension(180, 120));

        JLabel name = new JLabel(nombre);
        name.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel price = new JLabel("$" + precio);
        price.setFont(new Font("Segoe UI", Font.BOLD, 14));
        price.setForeground(new Color(255, 120, 0));

        JPanel info = new JPanel(new GridLayout(2, 1));
        info.setBackground(Color.WHITE);
        info.add(name);
        info.add(price);

        card.add(info, BorderLayout.SOUTH);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                overlay.setVisible(true);

                PersonalizarProducto dialog = new PersonalizarProducto(Menu_Principal.this, nombre, categoria);

                dialog.setVisible(true);
                overlay.setVisible(false);

                if (dialog.isConfirmado()) {
                    double precioFinal = precio + dialog.getPrecioExtra();

                    ProductoPedido pp = new ProductoPedido();
                    pp.setPrecio(precioFinal);
                    pp.setDetalles(dialog.getDetalles());
                    pp.setProductoIdProducto(idProducto);

                    carrito.add(pp);
                    total += precioFinal;

                    actualizarOrden(nombre, precioFinal, dialog.getDetalles());
                }
            }
        });

        return card;
    }

    private JPanel createOrdenPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(280, 0));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("ORDEN ACTUAL");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setBorder(new EmptyBorder(15, 15, 15, 15));

        modeloOrden = new DefaultListModel<>();
        listaOrden = new JList<>(modeloOrden);

        listaOrden.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listaOrden.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listaOrden.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {

                JTextArea area = new JTextArea(value.toString());
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setOpaque(true);
                area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                area.setBorder(new EmptyBorder(5,5,5,5));
                
                area.setSize(list.getWidth(), Short.MAX_VALUE);

                if (isSelected) {
                    area.setBackground(list.getSelectionBackground());
                    area.setForeground(list.getSelectionForeground());
                } else {
                    area.setBackground(Color.WHITE);
                    area.setForeground(Color.BLACK);
                }

                return area;
            }
        });
        
        listaOrden.setFixedCellHeight(-1);
        
        JScrollPane scroll = new JScrollPane(listaOrden);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        listaOrden.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {

                    int index = listaOrden.getSelectedIndex();

                    if (index >= 0) {
                        int confirm = JOptionPane.showConfirmDialog(
                                Menu_Principal.this,
                                "¿Eliminar este producto?",
                                "Confirmar",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirm == JOptionPane.YES_OPTION) {

                            ProductoPedido eliminado = carrito.get(index);

                            total -= eliminado.getPrecio();

                            carrito.remove(index);
                            modeloOrden.remove(index);

                            totalLabel.setText("TOTAL: $" + String.format("%.2f", total));
                        }
                    }
                }
            }
        });

        totalLabel = new JLabel("TOTAL: $0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalLabel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JButton cobrar = new JButton("COBRAR");
        cobrar.setBackground(new Color(255, 120, 0));
        cobrar.setForeground(Color.WHITE);
        cobrar.setFont(new Font("Segoe UI", Font.BOLD, 18));

        cobrar.addActionListener(e -> procesarVenta());

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(totalLabel, BorderLayout.NORTH);
        bottom.add(cobrar, BorderLayout.SOUTH);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(new JScrollPane(listaOrden), BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private void actualizarOrden(String nombre, double precio, String detalles) {

        String texto = nombre + " - $" + String.format("%.2f", precio);

        if (detalles != null && !detalles.trim().isEmpty()) {
            texto += " (" + detalles + ")";
        }

        modeloOrden.addElement(texto);

        totalLabel.setText("TOTAL: $" + String.format("%.2f", total));
    }

    private void procesarVenta() {
        Pedido pedido = new Pedido();
        double totalFinal;
        double iva = 0;
        String nombreClienteTicket = "";
        String telefonoClienteTicket = "";

        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos en la orden", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String[] opciones = {"A Domicilio", "Para Comer Aquí", "Cancelar"};
        int seleccion = JOptionPane.showOptionDialog(this,
                "¿Cuál es el tipo de pedido?",
                "Tipo de Orden",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[1]);

        int idClienteFinal = 1;
        String nombreComedor = "";

        if (seleccion == 0) {
            pedido.setTipoOrden("A DOMICILIO");
            String telefono = JOptionPane.showInputDialog(this, "Ingrese el teléfono del cliente:");
            if (telefono == null || telefono.trim().isEmpty()) {
                return;
            }
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = clienteDAO.buscarPorTelefono(telefono.trim());

            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado. Debe registrarlo primero.");
                return;
            } else {
                idClienteFinal = cliente.getIdCliente();
                nombreClienteTicket = cliente.getNombre();
                telefonoClienteTicket = cliente.getTelefono();
                JOptionPane.showMessageDialog(this, "Cliente encontrado: " + cliente.getNombre());
            }

            iva = total * 0.16;
            totalFinal = total + iva;

        } else if (seleccion == 1) {
            pedido.setTipoOrden("PARA COMER AQUÍ");
            nombreComedor = JOptionPane.showInputDialog(this, "Nombre del cliente (para llamarlo):");
            if (nombreComedor == null || nombreComedor.trim().isEmpty()) {
                return;
            }

            nombreClienteTicket = nombreComedor.trim();
            totalFinal = total;
        } else {
            return;
        }

        overlay.setVisible(true);

        DialogoCobro cobroDialog = new DialogoCobro(this, totalFinal);
        cobroDialog.setVisible(true);
        overlay.setVisible(false);

        if (cobroDialog.isConfirmado()) {
            double montoRecibido = cobroDialog.getMontoRecibido();

            pedido.setFecha(LocalDateTime.now());
            pedido.setSubtotal(total);
            pedido.setIva(iva);
            pedido.setTotal(totalFinal);
            pedido.setEstadoPedidoIdEstadoPedido(1);
            pedido.setAdministradorIdAdministrador(1);
            pedido.setCajeroIdCajero(1);
            pedido.setClienteIdCliente(idClienteFinal);

            Pago pago = new Pago();
            pago.setMonto(montoRecibido);
            pago.setFecha(LocalDateTime.now());
            pago.setPropina(0);
            pago.setMetodoPago(cobroDialog.getMetodoPago());
            pago.setCajaIdCaja(1);

            ServicioVenta servicio = new ServicioVenta();
            boolean ok = servicio.procesarVenta(pedido, carrito, pago);

            if (ok) {

                int imprimir = JOptionPane.showConfirmDialog(this,
                        "Venta registrada con éxito.\n¿Desea imprimir una copia del ticket?",
                        "Venta Exitosa",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);

                if (imprimir == JOptionPane.YES_OPTION) {
                try {
                    File archivo = TicketGenerator.generarTicket(
                            pedido,
                            carrito,
                            pago,
                            nombreClienteTicket,
                            telefonoClienteTicket
                    );

                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(archivo);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error al generar o abrir el ticket",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }

                carrito.clear();
                total = 0;
                modeloOrden.clear();
                totalLabel.setText("TOTAL: $0.00");
            } else {
                JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarProductos(String categoria) {

        productosPanel.removeAll();

        ProductoDAO dao = new ProductoDAO();
        List<Producto> productos = dao.obtenerPorCategoria(categoria);

        for (Producto p : productos) {
            productosPanel.add(createCard(
                    p.getNombre(),
                    p.getPrecioBase(),
                    p.getIdProducto(),
                    p.getCategoria()
            ));
        }

        productosPanel.revalidate();
        productosPanel.repaint();
    }

    private void cambiarCuenta() {
        if (!carrito.isEmpty()) {
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "Hay una orden en proceso. Si cambia de cuenta, esa orden se cerrara.\nDesea continuar?",
                    "Cambiar cuenta",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }
        }

        JPasswordField passwordField = new JPasswordField();
        Object[] mensaje = {
            "Ingrese el PIN o clave de la cuenta a la que desea entrar:",
            passwordField
        };

        int opcion = JOptionPane.showConfirmDialog(
                this,
                mensaje,
                "Cambiar cuenta",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        String clave = new String(passwordField.getPassword());
        if (!AccesoHelper.abrirModuloDesdeClave(this, clave)) {
            JOptionPane.showMessageDialog(this, "PIN incorrecto");
        }
    }

    private void iniciarFlujoCancelacion() {
    String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID del Ticket / Pedido a cancelar:");
    if (idStr == null || idStr.trim().isEmpty()) return;

    int idPedido;
    try {
        idPedido = Integer.parseInt(idStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "ID inválido. Ingrese solo números.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    Datos.PedidoDAO pedidoDAO = new Datos.PedidoDAO();

    Dominio.Pedido pedidoEncontrado = pedidoDAO.buscarPorId(idPedido);
    if (pedidoEncontrado == null) {
        JOptionPane.showMessageDialog(this, "No se encontró ninguna venta con el ID: " + idPedido, "Búsqueda Fallida", JOptionPane.ERROR_MESSAGE);
        return; 
    }
    
    int ESTADO_CANCELADO = 2; 
    if (pedidoEncontrado.getEstadoPedidoIdEstadoPedido() == ESTADO_CANCELADO) {
        JOptionPane.showMessageDialog(this, "Acción denegada: Esta venta ya se encuentra cancelada.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    int confirmacion = JOptionPane.showConfirmDialog(this, 
            "Venta encontrada por un Total de: $" + pedidoEncontrado.getTotal() + "\n¿Está seguro de querer cancelarla?", 
            "Confirmar Cancelación", JOptionPane.YES_NO_OPTION);

    if (confirmacion == JOptionPane.YES_OPTION) {
        JPasswordField pwdField = new JPasswordField();
        Object[] mensaje = {"Para autorizar, ingrese la contraseña de Encargado/Administrador:", pwdField};
        
        int auth = JOptionPane.showConfirmDialog(this, mensaje, "Autorización Requerida", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (auth == JOptionPane.OK_OPTION) {
            String password = new String(pwdField.getPassword());            
            Negocio.IAdministradorBO adminBO = new Negocio.AdministradorBO();
            
            if (adminBO.validarPassword(password)) {                
                boolean exito = pedidoDAO.cambiarEstado(idPedido, ESTADO_CANCELADO);
                
                if (exito) {
                    JOptionPane.showMessageDialog(this, "La venta ha sido cancelada exitosamente.", "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar la base de datos.", "Error SQL", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Contraseña incorrecta. Autorización denegada.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo cargar FlatLaf");
        }
        SwingUtilities.invokeLater(() -> new Menu_Principal().setVisible(true));
    }
}
