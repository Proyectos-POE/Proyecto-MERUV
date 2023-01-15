package modelo;

import java.util.ArrayList;

public class Carrito
{
    private ArrayList<Producto> carrito;
    private double precioTotal;
    private int productosTotales;

    public Carrito()
    {
        this.carrito = new ArrayList<Producto>();
        this.precioTotal = 0;
        this.productosTotales = 0;
    }

    public boolean agregarProductoCarrito(Producto auxProducto)
    {
        carrito.add(auxProducto);
        return true;
    }

    public Producto getProductoCarrito(int auxId)
    {
        return carrito.get(auxId);
    }

    public boolean eliminarProductoCarrito(int auxProducto)
    {
        carrito.remove(auxProducto);
        return true;
    }

    public void calcularPrecioTotalCompra()
    {
        double auxPrecioTotalCompra;
        auxPrecioTotalCompra= 0;
        for(Producto producto : carrito)
        {
            auxPrecioTotalCompra = auxPrecioTotalCompra + (producto.getPrecioCompra() * producto.getStock());
        }
        precioTotal = auxPrecioTotalCompra;
    }

    public void calcularPrecioTotalVenta()
    {
        double auxPrecioTotalVenta;
        auxPrecioTotalVenta= 0;
        for(Producto producto : carrito)
        {
            auxPrecioTotalVenta = auxPrecioTotalVenta + (producto.getPrecioVenta() * producto.getStock());
        }
        precioTotal = auxPrecioTotalVenta;
    }

    public void calcularProductosTotales()
    {
        int auxProductosTotales;
        auxProductosTotales = 0;
        for(Producto producto : carrito)
        {
            auxProductosTotales = auxProductosTotales + producto.getStock();
        }
        productosTotales = auxProductosTotales;
    }

    public void vaciarCarrito()
    {
        carrito.clear();
        this.precioTotal = 0;
        this.productosTotales = 0;
    }

    public ArrayList<Producto> getCarrito()
    {
        return carrito;
    }

    public void setCarrito(ArrayList<Producto> carrito)
    {
        this.carrito = carrito;
    }

    public double getPrecioTotal()
    {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal)
    {
        this.precioTotal = precioTotal;
    }

    public int getProductosTotales()
    {
        return productosTotales;
    }

    public void setProductosTotales(int productosTotales)
    {
        this.productosTotales = productosTotales;
    }
}
