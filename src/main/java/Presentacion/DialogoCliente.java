package Presentacion;

import Datos.ClienteDAO;
import Dominio.Cliente;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogoCliente extends JDialog {

    private final JTextField txtNombre;
    private final JTextField txtTelefono;
    private final JTextField txtDireccion;
    private final JTextField txtRFC;
    private final JTextField txtCorreo;
    private final Runnable onClienteActualizado;
    private Cliente cliente;

    public DialogoCliente(JFrame parent, Cliente clienteEditar) {
        this(parent, clienteEditar, null);
    }

    public DialogoCliente(JFrame parent, Cliente clienteEditar, Runnable onClienteActualizado) {
        super(parent, true);
        this.cliente = clienteEditar;
        this.onClienteActualizado = onClienteActualizado;

        setTitle(cliente == null ? "Agregar Cliente" : "Editar Cliente");
        setSize(420, 320);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        txtNombre = new JTextField();
        txtTelefono = new JTextField();
        txtDireccion = new JTextField();
        txtRFC = new JTextField();
        txtCorreo = new JTextField();

        formPanel.add(new JLabel("Nombre"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Telefono"));
        formPanel.add(txtTelefono);
        formPanel.add(new JLabel("Direccion"));
        formPanel.add(txtDireccion);
        formPanel.add(new JLabel("RFC"));
        formPanel.add(txtRFC);
        formPanel.add(new JLabel("Correo"));
        formPanel.add(txtCorreo);

        add(formPanel, BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);

        if (cliente != null) {
            txtNombre.setText(cliente.getNombre());
            txtTelefono.setText(cliente.getTelefono());
            txtDireccion.setText(cliente.getDireccion());
            txtRFC.setText(cliente.getRfc());
            txtCorreo.setText(cliente.getCorreo());
        }
    }

    private JPanel crearPanelBotones() {
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarCliente());

        if (cliente != null) {
            JButton btnEliminar = new JButton("Eliminar");
            btnEliminar.addActionListener(e -> eliminarCliente());
            botones.add(btnEliminar);
        }

        botones.add(btnCancelar);
        botones.add(btnGuardar);
        return botones;
    }

    private void guardarCliente() {
        ClienteDAO dao = new ClienteDAO();

        String nombre = txtNombre.getText().trim();
        String telefono = normalizarTelefono(txtTelefono.getText());
        String direccion = txtDireccion.getText().trim();
        String rfc = txtRFC.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.");
            return;
        }

        if (telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El telefono es obligatorio.");
            return;
        }

        if (!telefono.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "El telefono debe tener 10 digitos.");
            return;
        }

        Integer idClienteExcluir = null;
        if (cliente != null && cliente.getIdCliente() > 0) {
            idClienteExcluir = cliente.getIdCliente();
        }

        if (dao.existeTelefono(telefono, idClienteExcluir)) {
            JOptionPane.showMessageDialog(this, "Ya existe un cliente con ese telefono.");
            return;
        }

        if (cliente == null) {
            cliente = new Cliente();
        }

        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setDireccion(direccion);
        cliente.setRfc(rfc);
        cliente.setCorreo(correo);

        boolean exito;
        if (cliente.getIdCliente() == 0) {
            exito = dao.agregar(cliente);
        } else {
            exito = dao.actualizar(cliente);
        }

        if (exito) {
            notificarActualizacion();
            JOptionPane.showMessageDialog(this, "Cliente guardado correctamente.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el cliente.");
        }
    }

    private void eliminarCliente() {
        if (cliente == null || cliente.getIdCliente() <= 0) {
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "El cliente se inhabilitara del directorio. Desea continuar?",
                "Eliminar cliente",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        ClienteDAO dao = new ClienteDAO();
        boolean exito = dao.eliminar(cliente.getIdCliente());

        if (exito) {
            notificarActualizacion();
            JOptionPane.showMessageDialog(this, "Cliente inhabilitado correctamente.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No fue posible inhabilitar al cliente.");
        }
    }

    private void notificarActualizacion() {
        if (onClienteActualizado != null) {
            onClienteActualizado.run();
        }
    }

    private String normalizarTelefono(String telefono) {
        if (telefono == null) {
            return "";
        }
        return telefono.replaceAll("\\D", "").trim();
    }
}
