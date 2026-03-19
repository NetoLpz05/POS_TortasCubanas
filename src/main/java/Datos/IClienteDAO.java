package Datos;

import Dominio.Cliente;
import java.util.List;

public interface IClienteDAO {
    boolean agregar(Cliente cliente);
    boolean actualizar(Cliente cliente);
    boolean eliminar(int idCliente);
    Cliente consultar(int idCliente);
    List<Cliente> consultarTodos();
}