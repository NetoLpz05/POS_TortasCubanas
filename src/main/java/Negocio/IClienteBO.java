package Negocio;

import DTO.ClienteDTO;
import Dominio.Cliente;
import java.util.List;

/**
 *
 * @author Angel
 */
public interface IClienteBO {
    boolean agregar(ClienteDTO clienteDTO);
    boolean actualizar(ClienteDTO clienteDTO);
    boolean eliminar(int idCliente);
    Cliente consultar(int idCliente);
    List<ClienteDTO> consultarTodos();
}
