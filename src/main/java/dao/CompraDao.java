package dao;

import modelo.*;

import java.util.ArrayList;

/*
 * @author Nicolas Herrera <herrera.nicolas@correounivalle.edu.co>
 * @author Samuel Galindo Cuevas <samuel.galindo@correounivalle.edu.co>
 * @author Julian Rendon <julian.david.rendon@correounivalle.edu.co>
 */

public class CompraDao
{
    private final ArrayList<Compra> compras;

    public CompraDao()
    {
        compras = new ArrayList<Compra>();
    }

    public Compra getCompra(int auxId)
    {
        Compra auxCompra = null;
        for(Compra compra: compras)
        {
            if(compra.getId() == auxId)
            {
                auxCompra = compra;
                break;
            }
        }
        return  auxCompra;
    }

    public boolean anhadirCompra(Compra auxCompra)
    {
        compras.add(auxCompra);
        return true;
    }

    public boolean eliminarCompra(Compra auxCompra)
    {
        compras.remove(auxCompra);
        return true;
    }

    public boolean actualizarCompra(Compra auxCompra)
    {
        if(compras.contains(auxCompra))
        {
            int posicion = compras.indexOf(auxCompra);
            compras.set(posicion,auxCompra);
            return true;
        }
        return false;
    }

    public ArrayList<Compra> getCompras()
    {
        return compras;
    }
}


