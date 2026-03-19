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

            extras = new String[]{
                "Con carne", "Sin frijol", "Extra queso",
                "Tortilla de harina", "Sin verdura"
            };

        } else if (producto.equalsIgnoreCase("Orden de Burritos de Machaca")) {

            extras = new String[]{
                "Sin frijol", "Extra carne",
                "Con tortilla de harina", "Sin verdura"
            };

        } else if (producto.equalsIgnoreCase("Taco de Pierna o Pollo")) {

            extras = new String[]{
                "Con salsa", "Sin salsa", "Extra carne",
                "Sin verdura"
            };

        } else if (producto.equalsIgnoreCase("Agua Fresca 1L") ||
                   producto.equalsIgnoreCase("Agua Fresca 1/2L")) {

            extras = new String[]{
                "Jamaica", "Horchata", "Cebada"
            };

        } else if (producto.equalsIgnoreCase("Refresco 355ml") ||
                   producto.equalsIgnoreCase("Refresco 600ml")) {

            extras = new String[]{
                "Coca-Cola", "Sprite", "Fanta de Naranja",
                "Manzanita", "Coca Light", "Coca Zero", "Fresca", "Fanta de Fresa"
            };

        } else {
            switch (categoria) {

                case "TORTAS":
                    extras = new String[]{
                        "Sin Chile", "Sin Queso", "Sin Jamón",
                        "Sin Aguacate", "Semi-Dorada", "Partida"
                    };
                    break;

                case "EXTRAS":
                    extras = new String[]{
                        "Agregar aparte"
                    };
                    break;

                default:
                    extras = new String[]{};
            }
        }

        for (String e : extras) {
            opciones.add(new JToggleButton(e));
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

            StringBuilder sb = new StringBuilder();

            for (Component c : opciones.getComponents()) {
                if (c instanceof JToggleButton) {
                    JToggleButton btn = (JToggleButton) c;
                    if (btn.isSelected()) {
                        if (sb.length() > 0) {
                            sb.append(", ");
                        }
                        sb.append(btn.getText());
                    }
                }
            }
            
            cancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            agregar.setCursor(new Cursor(Cursor.HAND_CURSOR));

            if (!notas.getText().trim().isEmpty()) {
                if (sb.length() > 0) {
                    sb.append(" | ");
                }
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
}