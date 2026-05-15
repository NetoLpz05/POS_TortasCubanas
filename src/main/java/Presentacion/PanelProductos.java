/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;


import Datos.ProductoDAO;
import Dominio.Producto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class PanelProductos extends JPanel {

    private JPanel panelGrid;

    public PanelProductos() {
        setLayout(new BorderLayout());
        setBackground(new Color(245,245,245));
        add(createHeader(), BorderLayout.NORTH);
        panelGrid = new JPanel();
        panelGrid.setBackground(new Color(245,245,245));
        panelGrid.setLayout(new GridLayout(0, 3, 20, 20));

        JScrollPane scroll = new JScrollPane(panelGrid);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);
        cargarProductos();
    }

    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(20,20,20,20));
        JLabel titulo = new JLabel("Productos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        JButton agregar = new JButton("Añadir Producto");
        agregar.setBackground(new Color(255,120,0));
        agregar.setForeground(Color.WHITE);
        agregar.setFocusPainted(false);
        agregar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        agregar.addActionListener(e -> {

            DialogProducto dialog =
                    new DialogProducto(
                            (JFrame) SwingUtilities.getWindowAncestor(this),
                            null
                    );

            dialog.setVisible(true);
            cargarProductos();
        });

        header.add(titulo, BorderLayout.WEST);
        header.add(agregar, BorderLayout.EAST);
        return header;
    }

    private void cargarProductos() {
        panelGrid.removeAll();
        ProductoDAO dao = new ProductoDAO();
        List<Producto> productos = dao.obtenerProductos();

        for (Producto producto : productos) {
            panelGrid.add(crearCard(producto));
        }

        panelGrid.revalidate();
        panelGrid.repaint();
    }

    private JPanel crearCard(Producto producto) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(200,150));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                new EmptyBorder(15,15,15,15)
        ));

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        JLabel nombre = new JLabel(producto.getNombre());
        nombre.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel precio = new JLabel("$" + producto.getPrecioBase());
        precio.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        precio.setForeground(Color.GRAY);
        precio.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton editar = new JButton("Editar Información");
        editar.setAlignmentX(Component.CENTER_ALIGNMENT);
        editar.addActionListener(e -> {

            DialogProducto dialog =
                    new DialogProducto(
                            (JFrame) SwingUtilities.getWindowAncestor(this),
                            producto
                    );

            dialog.setVisible(true);

            cargarProductos();
        });

        card.add(Box.createVerticalGlue());
        card.add(nombre);
        card.add(Box.createVerticalStrut(10));
        card.add(precio);
        card.add(Box.createVerticalStrut(15));
        card.add(editar);
        card.add(Box.createVerticalGlue());
        return card;
    }
}
