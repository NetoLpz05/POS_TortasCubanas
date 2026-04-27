/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

/**
 *
 * @author Usuario
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PersonalizarProducto extends JDialog {

    private boolean confirmado = false;
    private String detalles = "";
    private JTextArea notas;
    private JPanel opciones;
    private double precioExtra = 0;
    private java.util.Map<String, Double> preciosExtras = new java.util.HashMap<>();

    public PersonalizarProducto(JFrame parent, String producto, String categoria) {
        super(parent, true);
        setTitle("Personalizar");
        setSize(420, 380);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setUndecorated(true);

        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(20,20,20,20));
        content.setBackground(Color.WHITE);

        JLabel titulo = new JLabel(producto.toUpperCase());
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel subtitulo = new JLabel("Personaliza la orden");
        subtitulo.setForeground(Color.GRAY);

        JPanel header = new JPanel(new GridLayout(2,1));
        header.setBackground(Color.WHITE);
        header.add(titulo);
        header.add(subtitulo);

        opciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        opciones.setBackground(Color.WHITE);

        String[] extras;

        if (producto.equalsIgnoreCase("Orden de Quesadillas")) {
            preciosExtras.put("Extra carne", 15.0);
            preciosExtras.put("Sin frijol", 0.0);
            preciosExtras.put("Extra queso", 10.0);
            preciosExtras.put("Sin verdura", 0.0);

        } else if (producto.equalsIgnoreCase("Orden de Burritos de Machaca")) {
            preciosExtras.put("Sin frijol", 0.0);
            preciosExtras.put("Extra Carne", 10.0);
            preciosExtras.put("Sin verdura", 0.0);

        } else if (producto.equalsIgnoreCase("Taco de Pierna o Pollo")) {
            preciosExtras.put("Sin Verdura", 0.0);
            preciosExtras.put("Extra Carne", 15.0);
            preciosExtras.put("Extra Frijol", 10.0);
            preciosExtras.put("Pieza Extra Aguacate", 10.0);
            preciosExtras.put("Extra Queso", 15.0);

        } else if (producto.equalsIgnoreCase("Agua Fresca 1L") ||
                   producto.equalsIgnoreCase("Agua Fresca 1/2L")) {
            preciosExtras.put("Jamaica", 0.0);
            preciosExtras.put("Horchata", 0.0);
            preciosExtras.put("Cebada", 0.0);

        } else if (producto.equalsIgnoreCase("Refresco 355ml") ||
                   producto.equalsIgnoreCase("Refresco 600ml")) {
            preciosExtras.put("Coca-Cola", 0.0);
            preciosExtras.put("Sprite", 0.0);
            preciosExtras.put("Fanta de Naranja", 0.0);
            preciosExtras.put("Manzanita", 0.0);
            preciosExtras.put("Coca Light", 0.0);
            preciosExtras.put("Coca Zero", 0.0);
            preciosExtras.put("Fresca", 0.0);
            preciosExtras.put("Fanta de Fresa", 0.0);

        } else {
            switch (categoria) {
                case "TORTAS":
                    preciosExtras.put("Sin Chile", 0.0);
                    preciosExtras.put("Sin Queso", 0.0);
                    preciosExtras.put("Sin Jamón", 0.0);
                    preciosExtras.put("Sin Aguacate", 0.0);
                    preciosExtras.put("Semi-Dorada", 0.0);
                    preciosExtras.put("Partida", 0.0);
                    
                    preciosExtras.put("Extra Jamón", 10.0);
                    preciosExtras.put("Extra Queso", 10.0);
                    preciosExtras.put("Extra Jamón y Queso", 10.0);
                    preciosExtras.put("Pieza Extra Aguacate", 10.0);
                    preciosExtras.put("Extra Frijol", 10.0);
                    preciosExtras.put("Extra Mostaza", 10.0);
                    preciosExtras.put("Pieza Extra Tomate", 10.0);
                    break;
                case "EXTRAS":
                    preciosExtras.put("Agregar aparte", 10.0);
                    break;
                default:
                    extras = new String[]{};
            }
        }

        for (java.util.Map.Entry<String, Double> entry : preciosExtras.entrySet()) {
            String label = entry.getValue() > 0
                    ? entry.getKey() + " (+$" + entry.getValue() + ")"
                    : entry.getKey();
            JToggleButton btn = new JToggleButton(label);
            btn.putClientProperty("precioExtra", entry.getValue());
            opciones.add(btn);
        }

        notas = new JTextArea(3,20);
        notas.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));

        JButton cancelar = new JButton("VOLVER");
        cancelar.setBackground(Color.LIGHT_GRAY);

        cancelar.addActionListener(e -> {
            confirmado = false;
            dispose();
        });

        JButton agregar = new JButton("AGREGAR");
        agregar.setBackground(new Color(255,120,0));
        agregar.setForeground(Color.WHITE);

        agregar.addActionListener(e -> {
            confirmado = true;
            precioExtra = 0;

            StringBuilder sb = new StringBuilder();

            for (Component c : opciones.getComponents()) {
                if (c instanceof JToggleButton) {
                    JToggleButton btn = (JToggleButton) c;
                    if (btn.isSelected()) {
                        if (sb.length() > 0) sb.append(", ");
                        sb.append(btn.getText());

                        double costo = (double) btn.getClientProperty("precioExtra");
                        precioExtra += costo;
                    }
                }
            }

            if (!notas.getText().trim().isEmpty()) {
                if (sb.length() > 0) sb.append(" | ");
                sb.append(notas.getText().trim());
            }

            detalles = sb.toString();
            dispose();
        });

        JPanel botones = new JPanel(new GridLayout(1,2,10,0));
        botones.setBackground(Color.WHITE);
        botones.add(cancelar);
        botones.add(agregar);

        content.add(header, BorderLayout.NORTH);
        content.add(opciones, BorderLayout.CENTER);
        content.add(notas, BorderLayout.SOUTH);

        add(content, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    public boolean isConfirmado() { return confirmado; }

    public String getDetalles() { return detalles; }

    public boolean tieneDetalles() {
        return detalles != null && !detalles.trim().isEmpty();
    }
    
    public double getPrecioExtra() { 
        return precioExtra; 
    }
}