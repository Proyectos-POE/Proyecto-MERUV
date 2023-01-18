package dao;

import modelo.Venta;

import java.util.ArrayList;

/*
 * @author Nicolas Herrera <herrera.nicolas@correounivalle.edu.co>
 * @author Samuel Galindo Cuevas <samuel.galindo@correounivalle.edu.co>
 * @author Julian Rendon <julian.david.rendon@correounivalle.edu.co>
*/

public class VentaDao 
{
    private final ArrayList<Venta> ventas;
    
    public VentaDao()
    {
        ventas = new ArrayList <Venta>();
    }

    public boolean anhadirVenta(Venta auxVenta)
    {
        ventas.add(auxVenta);
        return true;
    }

    public ArrayList<Venta> getVentas()
    {
        return ventas;
    }
}
