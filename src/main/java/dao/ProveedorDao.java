package dao;

import modelo.Proveedor;

import java.util.ArrayList;

/*
 * @author Nicolas Herrera <herrera.nicolas@correounivalle.edu.co>
 * @author Samuel Galindo Cuevas <samuel.galindo@correounivalle.edu.co>
 * @author Julian Rendon <julian.david.rendon@correounivalle.edu.co>
 */

public class ProveedorDao
{
    private final ArrayList<Proveedor> proveedores;

    public ProveedorDao()
    {
        proveedores = new ArrayList<Proveedor>();
    }

    public Proveedor getProveedor(int auxId)
    {
        Proveedor auxProveedor = null;
        for(Proveedor proveedor: proveedores)
        {
            if(proveedor.getId() == auxId)
            {
                auxProveedor = proveedor;
                break;
            }
        }
        return  auxProveedor;
    }

    public boolean anhadirProveedor(Proveedor auxProveedor)
    {
        proveedores.add(auxProveedor);
        return true;
    }

    public boolean eliminarProveedor(Proveedor auxProveedor)
    {
        proveedores.remove(auxProveedor);
        return true;
    }

    public boolean actualizarProveedor(Proveedor auxProveedor)
    {
        if(proveedores.contains(auxProveedor))
        {
            int posicion = proveedores.indexOf(auxProveedor);
            proveedores.set(posicion,auxProveedor);
            return true;
        }
        return false;
    }

    public ArrayList<Proveedor> getProveedores()
    {
        return proveedores;
    }
}
