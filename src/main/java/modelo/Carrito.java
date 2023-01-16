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

    public boolean agregarProductoCarrito(Producto auxProducto, double auxPrecio)
    {
        this.precioTotal = precioTotal + (auxProducto.getStock() * auxPrecio);
        this.productosTotales = productosTotales + auxProducto.getStock();
        carrito.add(auxProducto);
        return true;
    }

    public Producto getProductoCarrito(int auxId)
    {
        return carrito.get(auxId);
    }

    public boolean eliminarProductoCarrito(Producto auxProducto, double auxPrecio)
    {
        this.precioTotal = precioTotal - (auxProducto.getStock() * auxPrecio);
        this.productosTotales = productosTotales - auxProducto.getStock();
        this.carrito.remove(auxProducto);
        return true;
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
