package Presentacion;

import DTO.DetalleVentaDTO;
import DTO.VentaHistorialDTO;
import Datos.PedidoDAO;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PanelPedidos extends JPanel {

    private final PedidoDAO pedidoDAO;
    private final JComboBox<String> comboEstatus;
    private final JCheckBox chkFechaInicio;
    private final JCheckBox chkFechaFin;
    private final JSpinner spnFechaInicio;
    private final JSpinner spnFechaFin;
    private final DefaultTableModel modeloTabla;
    private final JTable tablaPedidos;
    private final JLabel lblMensaje;
    private List<VentaHistorialDTO> ventasActuales;

    public PanelPedidos() {
        this.pedidoDAO = new PedidoDAO();
        this.ventasActuales = new ArrayList<>();

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel("Historial de Ventas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));

        comboEstatus = new JComboBox<>();
        chkFechaInicio = new JCheckBox("Fecha Inicio");
        chkFechaFin = new JCheckBox("Fecha Fin");
        spnFechaInicio = crearSpinnerFecha();
        spnFechaFin = crearSpinnerFecha();
        spnFechaInicio.setEnabled(false);
        spnFechaFin.setEnabled(false);

        JButton btnLimpiar = new JButton("Limpiar Filtros");
        JButton btnVerDetalle = new JButton("Ver Detalle");

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));
        top.add(titulo, BorderLayout.NORTH);
        top.add(crearPanelFiltros(btnLimpiar, btnVerDetalle), BorderLayout.CENTER);

        modeloTabla = new DefaultTableModel(
                new Object[]{"Pedido", "Cliente", "Fecha y Hora", "Total", "Metodo de Pago", "Estatus"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.setRowHeight(30);
        tablaPedidos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaPedidos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaPedidos.getColumnModel().getColumn(5).setCellRenderer(new EstatusRenderer());
        tablaPedidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    abrirDetalleSeleccionado();
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tablaPedidos);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMensaje.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(lblMensaje, BorderLayout.SOUTH);

        cargarEstatus();
        registrarEventos(btnLimpiar, btnVerDetalle);
        cargarVentas();
    }

    private JPanel crearPanelFiltros(JButton btnLimpiar, JButton btnVerDetalle) {
        JPanel filtros = new JPanel(new GridBagLayout());
        filtros.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;

        gbc.gridx = 0;
        filtros.add(new JLabel("Estatus:"), gbc);

        gbc.gridx = 1;
        comboEstatus.setPreferredSize(new java.awt.Dimension(180, 28));
        filtros.add(comboEstatus, gbc);

        gbc.gridx = 2;
        filtros.add(chkFechaInicio, gbc);

        gbc.gridx = 3;
        spnFechaInicio.setPreferredSize(new java.awt.Dimension(150, 28));
        filtros.add(spnFechaInicio, gbc);

        gbc.gridx = 4;
        filtros.add(chkFechaFin, gbc);

        gbc.gridx = 5;
        spnFechaFin.setPreferredSize(new java.awt.Dimension(150, 28));
        filtros.add(spnFechaFin, gbc);

        gbc.gridx = 6;
        filtros.add(btnLimpiar, gbc);

        gbc.gridx = 7;
        filtros.add(btnVerDetalle, gbc);

        return filtros;
    }

    private JSpinner crearSpinnerFecha() {
        JSpinner spinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd");
        spinner.setEditor(editor);
        return spinner;
    }

    private void registrarEventos(JButton btnLimpiar, JButton btnVerDetalle) {
        comboEstatus.addActionListener(e -> cargarVentas());

        chkFechaInicio.addActionListener(e -> {
            spnFechaInicio.setEnabled(chkFechaInicio.isSelected());
            cargarVentas();
        });

        chkFechaFin.addActionListener(e -> {
            spnFechaFin.setEnabled(chkFechaFin.isSelected());
            cargarVentas();
        });

        ChangeListener refrescarFechas = e -> {
            if (chkFechaInicio.isSelected() || chkFechaFin.isSelected()) {
                cargarVentas();
            }
        };

        spnFechaInicio.addChangeListener(refrescarFechas);
        spnFechaFin.addChangeListener(refrescarFechas);

        btnLimpiar.addActionListener(e -> limpiarFiltros());
        btnVerDetalle.addActionListener(e -> abrirDetalleSeleccionado());
    }

    private void cargarEstatus() {
        comboEstatus.removeAllItems();
        comboEstatus.addItem("Todos");

        for (String estatus : pedidoDAO.consultarEstadosDisponibles()) {
            comboEstatus.addItem(estatus);
        }
    }

    private void limpiarFiltros() {
        comboEstatus.setSelectedItem("Todos");
        chkFechaInicio.setSelected(false);
        chkFechaFin.setSelected(false);
        spnFechaInicio.setEnabled(false);
        spnFechaFin.setEnabled(false);
        cargarVentas();
    }

    private void cargarVentas() {
        String estatus = (String) comboEstatus.getSelectedItem();
        if ("Todos".equals(estatus)) {
            estatus = null;
        }

        LocalDate fechaInicio = chkFechaInicio.isSelected() ? obtenerFecha(spnFechaInicio) : null;
        LocalDate fechaFin = chkFechaFin.isSelected() ? obtenerFecha(spnFechaFin) : null;

        ventasActuales = pedidoDAO.consultarHistorialVentas(estatus, fechaInicio, fechaFin);

        modeloTabla.setRowCount(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (VentaHistorialDTO venta : ventasActuales) {
            modeloTabla.addRow(new Object[]{
                    venta.getIdPedido(),
                    valorODefault(venta.getNombreCliente()),
                    venta.getFecha() == null ? "-" : venta.getFecha().format(formatter),
                    String.format("$%.2f", venta.getTotal()),
                    valorODefault(venta.getMetodoPago()),
                    valorODefault(venta.getEstatus())
            });
        }

        boolean hayFiltros = estatus != null || chkFechaInicio.isSelected() || chkFechaFin.isSelected();

        if (ventasActuales.isEmpty()) {
            if (hayFiltros) {
                lblMensaje.setText("No hay registros disponibles para el filtro seleccionado.");
            } else {
                lblMensaje.setText("No hay pedidos registrados.");
            }
        } else {
            lblMensaje.setText("Mostrando " + ventasActuales.size() + " pedido(s).");
        }
    }

    private LocalDate obtenerFecha(JSpinner spinner) {
        Date fecha = (Date) spinner.getValue();
        return Instant.ofEpochMilli(fecha.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private void abrirDetalleSeleccionado() {
        int fila = tablaPedidos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para ver el detalle.");
            return;
        }

        VentaHistorialDTO venta = ventasActuales.get(fila);
        DetalleVentaDTO detalle = pedidoDAO.consultarDetalleVenta(venta.getIdPedido());

        if (detalle == null) {
            JOptionPane.showMessageDialog(this, "No fue posible cargar el detalle del pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DialogDetallePedido dialog = new DialogDetallePedido(
                (javax.swing.JFrame) SwingUtilities.getWindowAncestor(this),
                detalle
        );
        dialog.setVisible(true);
    }

    private String valorODefault(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return "-";
        }
        return valor;
    }

    private static class EstatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            String estatus = value == null ? "" : value.toString().trim().toUpperCase();

            if (isSelected) {
                return label;
            }

            label.setForeground(Color.BLACK);

            if (estatus.contains("CANCEL")) {
                label.setBackground(new Color(255, 205, 210));
            } else if (estatus.contains("PEND")) {
                label.setBackground(new Color(255, 249, 196));
            } else if (estatus.contains("ENTREG") || estatus.contains("PAG")) {
                label.setBackground(new Color(200, 230, 201));
            } else if (estatus.contains("PREPAR")) {
                label.setBackground(new Color(255, 224, 178));
            } else {
                label.setBackground(Color.WHITE);
            }

            return label;
        }
    }
}
