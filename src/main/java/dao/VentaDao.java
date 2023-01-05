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
    
    public Venta getVenta(int auxId)
    {
        Venta auxVenta = null;
        for(Venta venta: ventas)
        {
            if(venta.getId() == auxId)
            {
                auxVenta = venta;
                break;
            }
        }
        return auxVenta;
    }
    
    public boolean anhadirVenta(Venta auxVenta)
    {
        ventas.add(auxVenta);
        return true;
    }
    
    public boolean eliminarVenta(Venta auxVenta)
    {
        ventas.remove(auxVenta);
        return true;
    }
    
    public boolean actualizarVenta(Venta auxVenta)
    {
        if(ventas.contains(auxVenta))
        {
            int pos = ventas.indexOf(auxVenta);
            ventas.set(pos, auxVenta);
            return true; 
        }
        return false;
    }
    
    public ArrayList<Venta> getVentas()
    {
        return ventas;
    }
}
