package modelo;

import java.io.Serializable;
import modelo.Producto;
import dao.ProductoDao;
import java.util.ArrayList;

/*
 * @author Nicolas Herrera <herrera.nicolas@correounivalle.edu.co>
 * @author Samuel Galindo Cuevas <samuel.galindo@correounivalle.edu.co>
 * @author Julian Rendon <julian.david.rendon@correounivalle.edu.co>
 */


public class Venta implements Serializable
{
    private int id;
    private static int numero;
    private Cliente cliente;
    private double valorTotal;
    private int cantidadProductos;
    private ArrayList<Producto> productosVenta;
    private Fecha fecha;
    private static final long serialVersionUID = 1L;
    
    public Venta(Cliente auxCliente, double auxValorTotal, int auxCantidadProductos, ArrayList<Producto> auxProductosVenta, Fecha fecha) 
    {
        numero++;
        id = numero;
        this.cliente = auxCliente;
        this.valorTotal = auxValorTotal;
        this.cantidadProductos = auxCantidadProductos;
        this.productosVenta = auxProductosVenta;
        this.fecha = fecha;
    }
    
    public static int getNumero()
    {
        return numero;
    }
    
    public static void setNumeroVenta(int auxNumero)
    {
        Venta.numero = auxNumero;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int auxId)
    {
        this.id = auxId;
    }
    
    public Cliente getCliente()
    {
        return cliente;
    }
    
    public void setCliente(Cliente auxCliente)
    {
        this.cliente = auxCliente;
    }
    
    public double getValorTotal()
    {
        return valorTotal;
    }
    
    public void setValorTotal(double auxValorTotal)
    {
        this.valorTotal = auxValorTotal;
    }

    public int getCantidadProductos()
    {
        return cantidadProductos;
    }
    
    public void setCantidadProductos(int auxCantidadProductos)
    {
        this.cantidadProductos = auxCantidadProductos;
    }
    
    public ArrayList<Producto> getProductosVenta()
    {
        return productosVenta;
    }
    
    public void setProductosVenta(ArrayList<Producto> auxProductosVenta)
    {
        this.productosVenta = auxProductosVenta;
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