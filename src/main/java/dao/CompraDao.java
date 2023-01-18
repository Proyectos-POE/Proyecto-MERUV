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

    public boolean anhadirCompra(Compra auxCompra)
    {
        compras.add(auxCompra);
        return true;
    }

    public ArrayList<Compra> getCompras()
    {
        return compras;
    }
}


