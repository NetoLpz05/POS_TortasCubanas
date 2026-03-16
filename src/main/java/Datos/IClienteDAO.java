package Datos;

import Dominio.Cliente;

/**
 *
 * @author Angel
 */
public interface IClienteDAO {
    
    Cliente buscarCliente(String telefono);
    
}
