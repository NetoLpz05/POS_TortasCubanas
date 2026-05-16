package Presentacion;

import DTO.DetalleVentaDTO;
import DTO.LineaVentaDTO;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;

public class DialogDetallePedido extends JDialog {

    public DialogDetallePedido(JFrame parent, DetalleVentaDTO detalle) {
        super(parent, "Detalle del Pedido #" + detalle.getIdPedido(), true);

        setSize(820, 560);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel header = new JPanel(new GridLayout(0, 2, 10, 8));
        header.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        header.setBackground(Color.WHITE);

        agregarDato(header, "Pedido", String.valueOf(detalle.getIdPedido()));
        agregarDato(header, "Fecha", detalle.getFechaPedido() == null
                ? "-"
                : detalle.getFechaPedido().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        agregarDato(header, "Cliente", valorODefault(detalle.getNombreCliente()));
        agregarDato(header, "Telefono", valorODefault(detalle.getTelefonoCliente()));
        agregarDato(header, "Direccion", valorODefault(detalle.getDireccionCliente()));
        agregarDato(header, "Tipo de Orden", valorODefault(detalle.getTipoOrden()));
        agregarDato(header, "Metodo de Pago", valorODefault(detalle.getMetodoPago()));
        agregarDato(header, "Estatus", valorODefault(detalle.getEstatus()));

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"Producto", "Detalles", "Cantidad", "Precio Unitario", "Subtotal"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (LineaVentaDTO linea : detalle.getLineas()) {
            modelo.addRow(new Object[]{
                    valorODefault(linea.getNombreProducto()),
                    valorODefault(linea.getDetalles()),
                    linea.getCantidad(),
                    String.format("$%.2f", linea.getPrecioUnitario()),
                    String.format("$%.2f", linea.getSubtotal())
            });
        }

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Productos"));

        JPanel resumen = new JPanel(new GridLayout(0, 1, 4, 4));
        resumen.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
        resumen.setBackground(Color.WHITE);
        resumen.add(crearResumen("Subtotal", detalle.getSubtotal()));
        resumen.add(crearResumen("IVA", detalle.getIva()));
        resumen.add(crearResumen("Total", detalle.getTotal()));
        resumen.add(crearResumen("Pago", detalle.getMontoPagado()));
        resumen.add(crearResumen("Propina", detalle.getPropina()));

        add(header, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(resumen, BorderLayout.SOUTH);
    }

    private void agregarDato(JPanel panel, String titulo, String valor) {
        JLabel label = new JLabel(titulo + ": " + valor, SwingConstants.LEFT);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(label);
    }

    private JLabel crearResumen(String titulo, double monto) {
        JLabel label = new JLabel(titulo + ": $" + String.format("%.2f", monto));
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private String valorODefault(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return "-";
        }
        return valor;
    }
}
