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

    public PersonalizarProducto(JFrame parent, String producto) {
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

        JPanel opciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        opciones.setBackground(Color.WHITE);

        String[] extras = {"Sin Chile", "Sin Queso", "Sin Jamón"};

        for (String e : extras) {
            opciones.add(new JToggleButton(e));
        }

        notas = new JTextArea(3,20);

        JButton agregar = new JButton("AGREGAR");
        agregar.addActionListener(e -> {
            confirmado = true;
            detalles = notas.getText();
            dispose();
        });

        content.add(header, BorderLayout.NORTH);
        content.add(opciones, BorderLayout.CENTER);
        content.add(notas, BorderLayout.SOUTH);

        add(content, BorderLayout.CENTER);
        add(agregar, BorderLayout.SOUTH);
    }

    public boolean isConfirmado() { return confirmado; }

    public String getDetalles() { return detalles; }

    public boolean tieneDetalles() {
        return detalles != null && !detalles.trim().isEmpty();
    }
}