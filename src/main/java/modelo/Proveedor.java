package modelo;

import java.io.Serializable;

/*
 * @author Nicolas Herrera <herrera.nicolas@correounivalle.edu.co>
 * @author Samuel Galindo Cuevas <samuel.galindo@correounivalle.edu.co>
 * @author Julian Rendon <julian.david.rendon@correounivalle.edu.co>
*/

public class Proveedor implements Serializable
{
    private static int numero;
    private final int id;
    private String nombre;
    private long nit;
    private long telefono;
    private String direccion;
    private String razonSocial;
    private static final long serialVersionUID =1L;

    public Proveedor(String auxNombre, long auxNit, long auxTelefono, String auxDireccion, String auxRazonSocial)
    {
        numero++;
        id= numero;
        this.nombre= auxNombre;
        this.nit= auxNit;
        this.telefono= auxTelefono;
        this.direccion= auxDireccion;
        this.razonSocial= auxRazonSocial;
    }

    public static void setNumeroProveedor(int auxNumero)
    {
        Proveedor.numero = auxNumero;
    }

    public int getId()
    {
        return id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String auxNombre)
    {
        this.nombre = auxNombre;
    }

    public long getNit()
    {
        return nit;
    }

    public void setNit(long auxNit)
    {
        this.nit = auxNit;
    }

    public long getTelefono()
    {
        return telefono;
    }

    public void setTelefono(long auxTelefono)
    {
        this.telefono = auxTelefono;
    }

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion(String auxDireccion)
    {
        this.direccion = auxDireccion;
    }

    public String getRazonSocial()
    {
        return razonSocial;
    }

    public void setRazonSocial(String auxRazonSocial)
    {
        this.razonSocial = auxRazonSocial;
    }

    public String toString()
    {
        return nombre;
    }
}
