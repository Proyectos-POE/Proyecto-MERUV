package modelo;

import java.io.Serializable;

/*
 * @author Nicolas Herrera <herrera.nicolas@correounivalle.edu.co>
 * @author Samuel Galindo Cuevas <samuel.galindo@correounivalle.edu.co>
 * @author Julian Rendon <julian.david.rendon@correounivalle.edu.co>
*/

public class Producto implements Serializable
{
    private int id;
    private final long codigo;
    private static int numero;
    private String nombre;
    private double precioVenta;
    private int stock;
    private double precioCompra;
    private String categoria;
    private static final long serialVersionUID = 1L;

    public Producto(long auxCodigo, String auxNombre, double auxPrecioCompra, double auxPrecioVenta, int auxStock, String auxCategoria)
    {
        numero++;
        id = numero;
        this.codigo = auxCodigo;
        this.nombre = auxNombre;
        this.precioCompra = auxPrecioCompra;
        this.precioVenta = auxPrecioVenta;
        this.stock = auxStock;
        this.categoria = auxCategoria;
    }

    public Producto(long auxCodigo, String auxNombre, double auxPrecioCompra, int auxStock)
    {
        this.codigo = auxCodigo;
        this.nombre = auxNombre;
        this.precioCompra = auxPrecioCompra;
        this.stock = auxStock;
    }
    
     public Producto(long auxCodigo, String auxNombre, int auxStock, double auxPrecioVenta)
    {
        this.codigo = auxCodigo;
        this.nombre = auxNombre;
        this.precioVenta = auxPrecioVenta;
        this.stock = auxStock;
    }
    
    public int getId()
    {
        return id;
    }

    public static void setNumeroProducto(int auxNumero)
    {
        Producto.numero = auxNumero;
    }
    
    public long getCodigo()
    {
        return codigo;
    }

    public String getNombre()
    {
        return nombre;
    }
    
    public void setNombre(String auxNombre)
    {
        this.nombre = auxNombre;
    }
    
    public double getPrecioVenta()
    {
        return precioVenta;
    }
    
    public void setPrecioVenta(double auxPrecioVenta)
    {
        this.precioVenta = auxPrecioVenta;
    }

    public double getPrecioCompra()
    {
        return precioCompra;
    }

    public int getStock()
    {
        return stock;
    }

    public String getCategoria() 
    {
        return categoria;
    }

    public String toString()
    {
        return nombre;
    }

    public void aumentarStock(int auxStock)
    {
        this.stock = stock + auxStock;
    }

    public void disminuirStock(int auxStock)
    {
        this.stock = stock - auxStock;
    }
}
