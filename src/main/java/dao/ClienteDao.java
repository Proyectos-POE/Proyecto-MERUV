package dao;

import modelo.Cliente;

import java.util.ArrayList;

/*
 * @author Nicolas Herrera <herrera.nicolas@correounivalle.edu.co>
 * @author Samuel Galindo Cuevas <samuel.galindo@correounivalle.edu.co>
 * @author Julian Rendon <julian.david.rendon@correounivalle.edu.co>
 */

public class ClienteDao
{
    private final ArrayList<Cliente> clientes;

    public ClienteDao()
    {
        this.clientes = new ArrayList<Cliente>();
    }

    public ArrayList<Cliente> getClientes()
    {
        return clientes;
    }

    public Cliente getCliente(int auxId)
    {
        Cliente auxCliente = null;
        for (Cliente cliente : clientes)
        {
            if (cliente.getId() == auxId)
            {
                auxCliente = cliente;
                break;
            }
        }
        return auxCliente;
    }

    public boolean agregarCliente(Cliente auxAfiliado)
    {
        clientes.add(auxAfiliado);
        return true;
    }

    public boolean editarCliente(Cliente auxCliente)
    {
        if(clientes.contains(auxCliente))
        {
            int pos = clientes.indexOf(auxCliente);
            clientes.set(pos, auxCliente);
            return true;
        }
        return false;
    }

    public boolean eliminarCliente(Cliente auxCliente)
    {
        clientes.remove(auxCliente);
        return true;
    }
}