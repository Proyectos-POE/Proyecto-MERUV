package modelo;

import java.util.ArrayList;
import java.io.Serializable;

/*
 * @author Nicolas Herrera <herrera.nicolas@correounivalle.edu.co>
 * @author Samuel Galindo Cuevas <samuel.galindo@correounivalle.edu.co>
 * @author Julian Rendon <julian.david.rendon@correounivalle.edu.co>
 */

public class Compra implements Serializable
{
    private static int numero;
    private int id;
    private Proveedor proveedor;
    private double valorTotal;
    private ArrayList<Producto> productosCompra;
    private int cantidadProductos;
    private Fecha fecha;
    Compra(Proveedor auxProveedor, double auxValorTotal, ArrayList<Producto> auxProductosCompra, int auxCantidadProductos, Fecha auxFecha)
    {
        numero++;
        id=numero;
        this.proveedor = auxProveedor;
        this.valorTotal = auxValorTotal;
        this.productosCompra = auxProductosCompra;
        this.cantidadProductos = auxCantidadProductos;
        this.fecha = auxFecha;
    }
    public int getId()
    {
        return id;
    }

    public void setId(int auxId)
    {
        this.id = auxId;
    }

    public Proveedor getProveedor()
    {
        return proveedor;
    }

    public void setProveedor(Proveedor auxpProveedor)
    {
        this.proveedor = auxpProveedor;
    }

    public double getValorTotal()
    {
        return valorTotal;
    }

    public void setValorTotal(double auxValorTotal)
    {
        this.valorTotal = auxValorTotal;
    }

    public ArrayList<Producto> getProductosCompra()
    {
        return productosCompra;
    }

    public void setProductosCompra(ArrayList<Producto> auxProductosCompra)
    {
        this.productosCompra = auxProductosCompra;
    }
    public int getCantidadProductos()
    {
        return cantidadProductos;
    }
    public void setCantidadProductos(int auxCantidadProductos)
    {
        this.cantidadProductos = auxCantidadProductos;
    }
    public Fecha getFecha()
    {
        return fecha;
    }
    public void setFecha(Fecha auxFecha)
    {
        this.fecha = auxFecha;
    }
}
