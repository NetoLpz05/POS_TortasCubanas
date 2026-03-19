package Negocio;

import DTO.ClienteDTO;
import Datos.IClienteDAO;
import Dominio.Cliente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Angel
 */
public class ClienteBO implements IClienteBO {
    private final IClienteDAO clienteDAO;

    public ClienteBO(IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @Override
    public boolean agregar(ClienteDTO clienteDTO) {
        if (!validarCliente(clienteDTO)) {
            System.out.println("Datos de cliente inválidos");
            return false;
        }

        Cliente cliente = convertirAEntidad(clienteDTO);

        return clienteDAO.agregar(cliente);
    }

    @Override
    public boolean actualizar(ClienteDTO clienteDTO) {

        if (!validarCliente(clienteDTO) || clienteDTO.getIdCliente()<= 0) {
            System.out.println("Datos inválidos para actualizar");
            return false;
        }

        Cliente cliente = convertirAEntidad(clienteDTO);

        return clienteDAO.actualizar(cliente);
    }
    
    @Override
    public boolean eliminar(int idCliente) {

        if (idCliente <= 0) {
            System.out.println("ID inválido");
            return false;
        }

        return clienteDAO.eliminar(idCliente);
    }
    
    @Override
    public Cliente consultar(int idCliente) {

        if (idCliente <= 0) {
            System.out.println("ID inválido");
            return null;
        }

        return clienteDAO.consultar(idCliente);
    }
    
    @Override
    public List<ClienteDTO> consultarTodos() {

        List<Cliente> clientes = clienteDAO.consultarTodos();
        List<ClienteDTO> listaDTO = new ArrayList<>();

        for (Cliente c : clientes) {
            listaDTO.add(convertirADTO(c));
        }

        return listaDTO;
    }
    
    private boolean validarCliente(ClienteDTO dto) {

        if (dto == null) return false;

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            return false;
        }

        if (dto.getTelefono() == null || dto.getTelefono().trim().isEmpty()) {
            return false;
        }

        return true;
    }
    
    private Cliente convertirAEntidad(ClienteDTO dto) {

        Cliente cliente = new Cliente();

        cliente.setIdCliente(dto.getIdCliente());
        cliente.setNombre(dto.getNombre());
        cliente.setTelefono(dto.getTelefono());

        return cliente;
    }
    
    private ClienteDTO convertirADTO(Cliente cliente) {

        ClienteDTO dto = new ClienteDTO();

        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombre(cliente.getNombre());
        dto.setTelefono(cliente.getTelefono());

        return dto;
    }
}
