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
import java.awt.*;

public class MenuAdministrador extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MenuAdministrador() {

        setTitle("Administrador");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(10, 1));
        sidebar.setPreferredSize(new Dimension(200, 0));

        JButton btnPedidos = new JButton("Pedidos");
        JButton btnContactos = new JButton("Contactos");
        JButton btnProductos = new JButton("Productos");
        JButton btnFacturacion = new JButton("Facturación");

        sidebar.add(btnPedidos);
        sidebar.add(btnContactos);
        sidebar.add(btnProductos);
        sidebar.add(btnFacturacion);

        add(sidebar, BorderLayout.WEST);

        // CONTENT
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // PANELS
        //contentPanel.add(new PanelPedidos(), "PEDIDOS");
        contentPanel.add(new AgendaCliente(), "CONTACTOS");
        //contentPanel.add(new PanelProductos(), "PRODUCTOS");
        //contentPanel.add(new PanelFacturacion(), "FACTURACION");

        add(contentPanel, BorderLayout.CENTER);

        // EVENTOS
        btnPedidos.addActionListener(e ->
                cardLayout.show(contentPanel, "PEDIDOS"));

        btnContactos.addActionListener(e ->
                cardLayout.show(contentPanel, "CONTACTOS"));

        btnProductos.addActionListener(e ->
                cardLayout.show(contentPanel, "PRODUCTOS"));

        btnFacturacion.addActionListener(e ->
                cardLayout.show(contentPanel, "FACTURACION"));
    }
}