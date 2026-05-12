package Presentacion;

import Datos.ClienteDAO;
import Dominio.Cliente;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Usuario
 */
public class DialogoCliente extends JDialog {

    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtDireccion;
    private JTextField txtRFC;
    private JTextField txtCorreo;

    private Cliente cliente;

    public DialogoCliente(JFrame parent, Cliente clienteEditar) {

        super(parent, true);

        this.cliente = clienteEditar;

        setTitle(cliente == null ? "Agregar Cliente" : "Editar Cliente");

        setSize(400, 350);

        setLocationRelativeTo(parent);

        setLayout(new GridLayout(6, 2, 10, 10));

        txtNombre = new JTextField();
        txtTelefono = new JTextField();
        txtDireccion = new JTextField();
        txtRFC = new JTextField();
        txtCorreo = new JTextField();

        add(new JLabel("Nombre"));
        add(txtNombre);

        add(new JLabel("Teléfono"));
        add(txtTelefono);

        add(new JLabel("Dirección"));
        add(txtDireccion);

        add(new JLabel("RFC"));
        add(txtRFC);

        add(new JLabel("Correo"));
        add(txtCorreo);

        JButton btnGuardar = new JButton("Guardar");

        add(new JLabel());
        add(btnGuardar);

        // SI ES EDICIÓN
        if (cliente != null) {

            txtNombre.setText(cliente.getNombre());
            txtTelefono.setText(cliente.getTelefono());
            txtDireccion.setText(cliente.getDireccion());
            txtRFC.setText(cliente.getRfc());
            txtCorreo.setText(cliente.getCorreo());
        }

        btnGuardar.addActionListener(e -> guardarCliente());
    }

    private void guardarCliente() {

        ClienteDAO dao = new ClienteDAO();

        if (cliente == null) {
            cliente = new Cliente();
        }

        cliente.setNombre(txtNombre.getText());
        cliente.setTelefono(txtTelefono.getText());
        cliente.setDireccion(txtDireccion.getText());
        cliente.setRfc(txtRFC.getText());
        cliente.setCorreo(txtCorreo.getText());

        boolean exito;

        if (cliente.getIdCliente() == 0) {
            exito = dao.agregar(cliente);
        } else {
            exito = dao.actualizar(cliente);
        }

        if (exito) {
            JOptionPane.showMessageDialog(this, "Cliente guardado correctamente");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar");
        }
    }
}