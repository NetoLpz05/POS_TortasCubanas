package Presentacion;

import Datos.ClienteDAO;
import java.awt.Color;
import java.awt.Component;
import Dominio.Cliente;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class AgendaCliente extends JPanel {

    private final JPanel panelClientes;

    public AgendaCliente() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(new EmptyBorder(10, 15, 10, 15));
        JLabel titulo = new JLabel("Contactos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JButton agregar = new JButton("+ Anadir Cliente");
        top.add(titulo, BorderLayout.WEST);
        top.add(agregar, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        panelClientes = new JPanel(new GridLayout(0, 3, 15, 15));
        panelClientes.setBackground(new Color(245, 245, 245));
        panelClientes.setBorder(new EmptyBorder(15, 15, 15, 15));
        JScrollPane scroll = new JScrollPane(panelClientes);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(new Color(245, 245, 245));
        add(scroll, BorderLayout.CENTER);

        cargarClientes();

        agregar.addActionListener(e -> {
            DialogoCliente dialog = new DialogoCliente(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    null,
                    this::cargarClientes
            );
            dialog.setVisible(true);
        });
    }

    private void cargarClientes() {
        panelClientes.removeAll();

        ClienteDAO dao = new ClienteDAO();
        List<Cliente> clientes = dao.consultarTodos();

        for (Cliente cliente : clientes) {
            panelClientes.add(crearCard(cliente));
        }

        panelClientes.revalidate();
        panelClientes.repaint();
    }

    private JPanel crearCard(Cliente cliente) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(240, 170));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(14, 14, 14, 14)
        ));

        JLabel nombre = new JLabel(cliente.getNombre());
        nombre.setFont(new Font("Segoe UI", Font.BOLD, 17));
        nombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel telefono = new JLabel(cliente.getTelefono());
        telefono.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        telefono.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel direccion = new JLabel("<html>" + valorODefault(cliente.getDireccion()) + "</html>");
        direccion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        direccion.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton editar = new JButton("Editar Informacion");
        editar.setAlignmentX(Component.LEFT_ALIGNMENT);

        editar.addActionListener(e -> {
            DialogoCliente dialog = new DialogoCliente(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    cliente,
                    this::cargarClientes
            );
            dialog.setVisible(true);
        });

        card.add(nombre);
        card.add(Box.createVerticalStrut(6));
        card.add(telefono);
        card.add(Box.createVerticalStrut(6));
        card.add(direccion);
        card.add(Box.createVerticalStrut(12));
        card.add(editar);
        card.add(Box.createVerticalGlue());

        return card;
    }

    private String valorODefault(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return "Sin direccion";
        }
        return valor;
    }
}
