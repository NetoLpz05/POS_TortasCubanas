package Presentacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DialogoCobro extends JDialog {

    private boolean confirmado = false;
    private double montoRecibido = 0;
    private double totalCobrar;
    private JTextField txtRecibido;
    private JLabel lblCambio;

    public DialogoCobro(JFrame parent, double totalCobrar) {
        super(parent, true);
        this.totalCobrar = totalCobrar;
        
        setTitle("Cobrar Orden");
        setSize(350, 300);
        setLocationRelativeTo(parent);
        setUndecorated(true);

        JPanel content = new JPanel(new GridLayout(4, 1, 10, 10));
        content.setBorder(new EmptyBorder(20, 20, 20, 20));
        content.setBackground(Color.WHITE);

        // Título con el Total
        JLabel lblTotal = new JLabel("TOTAL A PAGAR: $" + String.format("%.2f", totalCobrar));
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTotal.setHorizontalAlignment(SwingConstants.CENTER);

        // Input para el dinero recibido
        JPanel panelRecibido = new JPanel(new FlowLayout());
        panelRecibido.setBackground(Color.WHITE);
        panelRecibido.add(new JLabel("Monto Recibido: $"));
        txtRecibido = new JTextField(10);
        txtRecibido.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        panelRecibido.add(txtRecibido);

        // Etiqueta para la feria (cambio)
        lblCambio = new JLabel("Cambio: $0.00");
        lblCambio.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblCambio.setForeground(new Color(0, 150, 0)); // Color verde
        lblCambio.setHorizontalAlignment(SwingConstants.CENTER);

        // Botones
        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBackground(Color.LIGHT_GRAY);
        btnCancelar.addActionListener(e -> dispose());

        JButton btnConfirmar = new JButton("CONFIRMAR PAGO");
        btnConfirmar.setBackground(new Color(255, 120, 0));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setEnabled(false); // Desactivado hasta que ingrese un monto válido

        // Lógica para calcular cambio en tiempo real
        txtRecibido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
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
                    lblCambio.setText("Monto inválido");
                    btnConfirmar.setEnabled(false);
                }
            }
        });

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
        content.add(lblCambio);
        content.add(panelBotones);

        add(content);
    }

    public boolean isConfirmado() { return confirmado; }
    public double getMontoRecibido() { return montoRecibido; }
}