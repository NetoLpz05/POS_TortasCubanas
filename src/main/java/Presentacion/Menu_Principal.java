package Presentacion;

/**
 *
 * @author Usuario
 */
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
    private JTextArea ordenArea;
    private double total = 0;
    private JPanel productosPanel;

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

    private JScrollPane createProductos() {
        JPanel grid = new JPanel(new GridLayout(0, 3, 20, 20));
        grid.setBorder(new EmptyBorder(20, 20, 20, 20));
        grid.setBackground(new Color(245, 245, 245));

        grid.add(createCard("Torta Cubana", 80, 1));
        grid.add(createCard("Torta Sencilla", 70, 2));
        grid.add(createCard("Torta Cubanita", 75, 3));
        grid.add(createCard("Torta Sencillota", 85, 4));

        return new JScrollPane(grid);
    }

    private JPanel createCard(String nombre, double precio, int idProducto) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220,220,220),1));
        card.setPreferredSize(new Dimension(180,120));

        JLabel name = new JLabel(nombre);
        name.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel price = new JLabel("$" + precio);
        price.setFont(new Font("Segoe UI", Font.BOLD, 14));
        price.setForeground(new Color(255,120,0));

        JPanel info = new JPanel(new GridLayout(2,1));
        info.setBackground(Color.WHITE);
        info.add(name);
        info.add(price);

        card.add(info, BorderLayout.SOUTH);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                overlay.setVisible(true);

                PersonalizarProducto dialog = new PersonalizarProducto(Menu_Principal.this, nombre);

                dialog.setVisible(true);
                overlay.setVisible(false);

                if (dialog.isConfirmado()) {

                    double precioFinal = precio;

                    if (dialog.tieneDetalles()) {
                        precioFinal += 10;
                    }

                    ProductoPedido pp = new ProductoPedido();
                    pp.setPrecio(precioFinal);
                    pp.setDetalles(dialog.getDetalles());
                    pp.setProductoIdProducto(idProducto);

                    carrito.add(pp);
                    total += precioFinal;

                    actualizarOrden(nombre, precioFinal);
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
        titulo.setBorder(new EmptyBorder(15,15,15,15));

        ordenArea = new JTextArea("Sin productos...");
        ordenArea.setEditable(false);

        JButton cobrar = new JButton("COBRAR");
        cobrar.setBackground(new Color(255,120,0));
        cobrar.setForeground(Color.WHITE);
        cobrar.setFont(new Font("Segoe UI", Font.BOLD, 18));

        cobrar.addActionListener(e -> procesarVenta());

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(new JScrollPane(ordenArea), BorderLayout.CENTER);
        panel.add(cobrar, BorderLayout.SOUTH);

        return panel;
    }

    private void actualizarOrden(String nombre, double precio) {

        if (ordenArea.getText().equals("Sin productos...")) {
            ordenArea.setText("");
        }

        ordenArea.append(nombre + " - $" + precio + "\n");
        ordenArea.append("------------------\n");
        ordenArea.append("TOTAL: $" + total + "\n\n");
    }

    private void procesarVenta() {

        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos");
            return;
        }

        double iva = total * 0.16;
        double totalFinal = total + iva;

        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now());
        pedido.setSubtotal(total);
        pedido.setIva(iva);
        pedido.setTotal(totalFinal);
        pedido.setEstadoPedidoIdEstadoPedido(1);
        pedido.setAdministradorIdAdministrador(1);
        pedido.setCajeroIdCajero(1);
        pedido.setClienteIdCliente(1);

        Pago pago = new Pago();
        pago.setMonto(totalFinal);
        pago.setFecha(LocalDateTime.now());
        pago.setPropina(0);
        pago.setCajaIdCaja(1);

        ServicioVenta servicio = new ServicioVenta();
        boolean ok = servicio.procesarVenta(pedido, carrito, pago);

        JOptionPane.showMessageDialog(this,
                "TOTAL: $" + totalFinal +
                "\nVenta: " + (ok ? "Exitosa" : "Error")
        );

        carrito.clear();
        total = 0;
        ordenArea.setText("Sin productos...");
    }
    
    private void cargarProductos(String categoria) {

        productosPanel.removeAll();

        ProductoDAO dao = new ProductoDAO();
        List<Producto> productos = dao.obtenerPorCategoria(categoria);

        for (Producto p : productos) {
            productosPanel.add(createCard(
                    p.getNombre(),
                    p.getPrecioBase(),
                    p.getIdProducto()
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