package Presentacion;

import Datos.ClienteDAO;
import Dominio.Cliente;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class AgendaCliente extends JPanel {

    private JPanel panelClientes;

    public AgendaCliente() {
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Contactos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JButton agregar = new JButton("+ Añadir Cliente");
        top.add(titulo, BorderLayout.WEST);
        top.add(agregar, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        panelClientes = new JPanel(new FlowLayout());
        JScrollPane scroll = new JScrollPane(panelClientes);
        add(scroll, BorderLayout.CENTER);
        cargarClientes();

        agregar.addActionListener(e -> {
            DialogoCliente dialog = new DialogoCliente((JFrame) SwingUtilities.getWindowAncestor(this),null);
            dialog.setVisible(true);
            cargarClientes();
        });
    }

    private void cargarClientes() {
        panelClientes.removeAll();
        ClienteDAO dao = new ClienteDAO();
        List<Cliente> clientes = dao.consultarTodos();

        for (Cliente c : clientes) {
            JPanel card = crearCard(c);
            panelClientes.add(card);
        }

        revalidate();
        repaint();
    }

    private JPanel crearCard(Cliente cliente) {

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(240, 140));

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel nombre = new JLabel(cliente.getNombre());
        JLabel telefono = new JLabel(cliente.getTelefono());
        JLabel direccion = new JLabel(cliente.getDireccion());
        JButton editar = new JButton("Editar Información");

        editar.addActionListener(e -> {
            DialogoCliente dialog = new DialogoCliente((JFrame) SwingUtilities.getWindowAncestor(this),cliente);
            dialog.setVisible(true);
            cargarClientes();
        });

        card.add(nombre);
        card.add(telefono);
        card.add(direccion);
        card.add(Box.createVerticalGlue());
        card.add(editar);

        return card;
    }
}