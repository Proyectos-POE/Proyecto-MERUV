package modelo;

import java.io.Serializable;
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
    private final Cliente cliente;
    private final double valorTotal;
    private final int cantidadProductos;
    private final ArrayList<Producto> productosVenta;
    private final Fecha fecha;
    private static final long serialVersionUID = 1L;
    
    public Venta(Cliente auxCliente, double auxValorTotal, ArrayList<Producto> auxProductosVenta, int auxCantidadProductos,  Fecha fecha) 
    {
        numero++;
        id = numero;
        this.cliente = auxCliente;
        this.valorTotal = auxValorTotal;
        this.cantidadProductos = auxCantidadProductos;
        this.productosVenta = auxProductosVenta;
        this.fecha = fecha;
    }

    public static void setNumeroVenta(int auxNumero)
    {
        Venta.numero = auxNumero;
    }
    
    public int getId()
    {
        return id;
    }

    public Cliente getCliente()
    {
        return cliente;
    }
    
    public double getValorTotal()
    {
        return valorTotal;
    }

    public int getCantidadProductos()
    {
        return cantidadProductos;
    }

    public Fecha getFecha()
    {
        return fecha;
    }
    
    public String getNombresProductosVenta()
    {
        StringBuilder auxProductos;
        auxProductos = new StringBuilder();
        for (Producto producto : productosVenta)
        {
            auxProductos.append(producto.getNombre()).append("\n");
        }
        auxProductos = new StringBuilder(auxProductos.substring(0, auxProductos.length() - 1));
        return auxProductos.toString();
    }

    public String getPreciosProductosVenta()
    {
        StringBuilder auxProductos;
        auxProductos = new StringBuilder();
        for (Producto producto : productosVenta)
        {
            auxProductos.append(producto.getPrecioVenta()).append("\n");
        }
        auxProductos = new StringBuilder(auxProductos.substring(0, auxProductos.length() - 1));
        return auxProductos.toString();
    }

    public String getStockProductosVenta()
    {
        StringBuilder auxProductos;
        auxProductos = new StringBuilder();
        for (Producto producto : productosVenta)
        {
            auxProductos.append(producto.getStock()).append("\n");
        }
        auxProductos = new StringBuilder(auxProductos.substring(0, auxProductos.length() - 1));
        return auxProductos.toString();
    }
}