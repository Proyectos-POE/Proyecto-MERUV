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
    private final int id;
    private final Proveedor proveedor;
    private final double valorTotal;
    private final ArrayList<Producto> productosCompra;
    private final int cantidadProductos;
    private final Fecha fecha;
    public Compra(Proveedor auxProveedor, double auxValorTotal, ArrayList<Producto> auxProductosCompra, int auxCantidadProductos, Fecha auxFecha)
    {
        numero++;
        id=numero;
        this.proveedor = auxProveedor;
        this.valorTotal = auxValorTotal;
        this.productosCompra = auxProductosCompra;
        this.cantidadProductos = auxCantidadProductos;
        this.fecha = auxFecha;
    }

    public static void setNumeroCompra(int auxNumero)
    {
        Compra.numero = auxNumero;
    }

    public int getId()
    {
        return id;
    }

    public Proveedor getProveedor()
    {
        return proveedor;
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

    public String getNombresProductosCompra()
    {
        StringBuilder auxProductos;
        auxProductos = new StringBuilder();
        for (Producto producto : productosCompra)
        {
            auxProductos.append(producto.getNombre()).append("\n");
        }
        auxProductos = new StringBuilder(auxProductos.substring(0, auxProductos.length() - 1));
        return auxProductos.toString();
    }

    public String getPreciosProductosCompra()
    {
        StringBuilder auxProductos;
        auxProductos = new StringBuilder();
        for (Producto producto : productosCompra)
        {
            auxProductos.append(producto.getPrecioCompra()).append("\n");
        }
        auxProductos = new StringBuilder(auxProductos.substring(0, auxProductos.length() - 1));
        return auxProductos.toString();
    }

    public String getStockProductosCompra()
    {
        StringBuilder auxProductos;
        auxProductos = new StringBuilder();
        for (Producto producto : productosCompra)
        {
            auxProductos.append(producto.getStock()).append("\n");
        }
        auxProductos = new StringBuilder(auxProductos.substring(0, auxProductos.length() - 1));
        return auxProductos.toString();
    }
}
