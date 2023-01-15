package dao;

import modelo.Producto;

import java.util.ArrayList;

/*
 * @author Nicolas Herrera <herrera.nicolas@correounivalle.edu.co>
 * @author Samuel Galindo Cuevas <samuel.galindo@correounivalle.edu.co>
 * @author Julian Rendon <julian.david.rendon@correounivalle.edu.co>
 */

public class ProductoDao 
{
    private final ArrayList<Producto> productos;
    
    public ProductoDao()
    {
        productos = new ArrayList <Producto>();
    }
    
    public Producto getProducto(int auxId)
    {
        Producto auxProducto = null;
        for(Producto producto: productos)
        {
            if(producto.getId()== auxId)
            {
                auxProducto = producto;
                break;
            }
        }
        return auxProducto;
    }
    
    public boolean anhadirProducto(Producto auxProducto)
    {
        productos.add(auxProducto);
        return true;
    }
    
    public boolean eliminarProducto(Producto auxProducto)
    {
        productos.remove(auxProducto);
        return true;
    }
    
    public boolean actualizarProducto(Producto auxProducto)
    {
        if(productos.contains(auxProducto))
        {
            int pos = productos.indexOf(auxProducto);
            productos.set(pos, auxProducto);
            return true; 
        }
        return false;
    }

    public Producto getProductoCodigo(long auxCodigo)
    {
        Producto auxProducto = null;
        for (Producto producto : productos) {
            if (producto.getCodigo() == auxCodigo) {
                auxProducto = producto;
                break;
            }
        }
        return auxProducto;
    }
    
    public ArrayList<Producto> getProductos()
    {
        return productos;
    }
}
