package Presentacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DialogoCobro extends JDialog {

    private boolean confirmado = false;
    private double montoRecibido = 0;
    private final double totalCobrar;
    private JTextField txtRecibido;
    private JLabel lblCambio;
    private JComboBox<String> comboMetodoPago;
    private JButton btnConfirmar;

    public DialogoCobro(JFrame parent, double totalCobrar) {
        super(parent, true);
        this.totalCobrar = totalCobrar;

        setTitle("Cobrar Orden");
        setSize(380, 340);
        setLocationRelativeTo(parent);
        setUndecorated(true);

        JPanel content = new JPanel(new GridLayout(5, 1, 10, 10));
        content.setBorder(new EmptyBorder(20, 20, 20, 20));
        content.setBackground(Color.WHITE);

        JLabel lblTotal = new JLabel("TOTAL A PAGAR: $" + String.format("%.2f", totalCobrar));
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTotal.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panelRecibido = new JPanel(new FlowLayout());
        panelRecibido.setBackground(Color.WHITE);
        panelRecibido.add(new JLabel("Monto Recibido: $"));
        txtRecibido = new JTextField(10);
        txtRecibido.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        panelRecibido.add(txtRecibido);

        JPanel panelMetodoPago = new JPanel(new FlowLayout());
        panelMetodoPago.setBackground(Color.WHITE);
        panelMetodoPago.add(new JLabel("Metodo de Pago:"));
        comboMetodoPago = new JComboBox<>(new String[]{"EFECTIVO", "TARJETA", "TRANSFERENCIA"});
        comboMetodoPago.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panelMetodoPago.add(comboMetodoPago);

        lblCambio = new JLabel("Cambio: $0.00");
        lblCambio.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblCambio.setForeground(new Color(0, 150, 0));
        lblCambio.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBackground(Color.LIGHT_GRAY);
        btnCancelar.addActionListener(e -> dispose());

        btnConfirmar = new JButton("CONFIRMAR PAGO");
        btnConfirmar.setBackground(new Color(255, 120, 0));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setEnabled(false);

        txtRecibido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                recalcularCambio();
            }
        });

        comboMetodoPago.addActionListener(e -> actualizarSegunMetodoPago());

        btnConfirmar.addActionListener(e -> {
            confirmado = true;
            dispose();
        });

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnConfirmar);

        content.add(lblTotal);
        content.add(panelRecibido);
        content.add(panelMetodoPago);
        content.add(lblCambio);
        content.add(panelBotones);

        add(content);
        actualizarSegunMetodoPago();
    }

    private void actualizarSegunMetodoPago() {
        String metodoPago = getMetodoPago();
        boolean esEfectivo = "EFECTIVO".equals(metodoPago);

        txtRecibido.setEnabled(esEfectivo);

        if (esEfectivo) {
            txtRecibido.setText("");
            lblCambio.setText("Cambio: $0.00");
            btnConfirmar.setEnabled(false);
            montoRecibido = 0;
            return;
        }

        montoRecibido = totalCobrar;
        txtRecibido.setText(String.format("%.2f", totalCobrar));
        lblCambio.setText("Cambio: $0.00");
        btnConfirmar.setEnabled(true);
    }

    private void recalcularCambio() {
        if (!"EFECTIVO".equals(getMetodoPago())) {
            return;
        }

        try {
            double recibido = Double.parseDouble(txtRecibido.getText());
            double cambio = recibido - totalCobrar;

            if (cambio >= 0) {
                lblCambio.setText("Cambio: $" + String.format("%.2f", cambio));
                btnConfirmar.setEnabled(true);
                montoRecibido = recibido;
            } else {
                lblCambio.setText("Falta dinero...");
                btnConfirmar.setEnabled(false);
            }
        } catch (NumberFormatException ex) {
            lblCambio.setText("Monto invÃ¡lido");
            btnConfirmar.setEnabled(false);
        }
    }

    public boolean isConfirmado() { return confirmado; }
    public double getMontoRecibido() { return montoRecibido; }
    public String getMetodoPago() { return comboMetodoPago.getSelectedItem().toString(); }
}
