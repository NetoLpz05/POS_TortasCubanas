package Datos;

import Dominio.Cliente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Angel
 */
public class ClienteDAO implements IClienteDAO {

    private final List<Cliente> clientes = new ArrayList<>();

    public ClienteDAO() {
        clientes.add(new Cliente(1, "1234567890", "Juan", "direccion", "rfc", "correo"));
        clientes.add(new Cliente(2, "9876543210", "Maria", "direccion", "rfc", "correo"));
    }

    @Override
    public Cliente buscarCliente(String telefono) {
        for (Cliente c : clientes) {
            if (c.getTelefono().equals(telefono)) {
                return c;
            }
        }
        return null;
    }
}
