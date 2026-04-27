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

        add(createSidebar(), BorderLayout.WEST);
        productosPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        add(new JScrollPane(productosPanel), BorderLayout.CENTER);
        add(createOrdenPanel(), BorderLayout.EAST);

        cargarProductos("TORTAS");
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

        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos en la orden", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // 1. Preguntar el tipo de pedido
        String[] opciones = {"A Domicilio", "Para Comer Aquí", "Cancelar"};
        int seleccion = JOptionPane.showOptionDialog(this,
                "¿Cuál es el tipo de pedido?",
                "Tipo de Orden",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[1]);

        int idClienteFinal = 1; // ID por defecto para "Público General" cambienla si no cala
        String nombreComedor = "";

        //Lógica según el tipo de pedido
        if (seleccion == 0) {
            // --- FLUJO: A DOMICILIO ---
            pedido.setTipoOrdenIdTipoOrden(2);
            String telefono = JOptionPane.showInputDialog(this, "Ingrese el teléfono del cliente:");
            if (telefono == null || telefono.trim().isEmpty()) {
                return; // El usuario canceló
            }
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = clienteDAO.buscarPorTelefono(telefono.trim());

            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado. Debe registrarlo primero.");
                return;
            } else {
                idClienteFinal = cliente.getIdCliente();
                JOptionPane.showMessageDialog(this, "Cliente encontrado: " + cliente.getNombre());
            }

            iva = total * 0.16;
            totalFinal = total + iva;

        } else if (seleccion == 1) {
            // --- FLUJO: PARA COMER AQUÍ ---
            pedido.setTipoOrdenIdTipoOrden(1);
            nombreComedor = JOptionPane.showInputDialog(this, "Nombre del cliente (para llamarlo):");
            if (nombreComedor == null || nombreComedor.trim().isEmpty()) {
                return; // El usuario canceló
            }

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

            // Preparar objetos para BD
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
                    System.out.println("Enviando ticket a la impresora..."); // Lógica futura de impresión
                }

                // Limpiar sistema para la siguiente orden
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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo cargar FlatLaf");
        }
        SwingUtilities.invokeLater(() -> new Menu_Principal().setVisible(true));
    }
}
