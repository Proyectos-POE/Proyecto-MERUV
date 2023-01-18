package modelo;

import java.io.Serializable;

/*
 * @author Nicolas Herrera <herrera.nicolas@correounivalle.edu.co>
 * @author Samuel Galindo Cuevas <samuel.galindo@correounivalle.edu.co>
 * @author Julian Rendon <julian.david.rendon@correounivalle.edu.co>
 */

public class Cliente implements Serializable
{
    private static int numero;
    private final int id;
    private String nombre;
    private long NIT;
    private long telefono;
    private String direccion;
    private static final long serialVersionUID = 1L;

    public Cliente(long auxNIT, String auxNombre, long auxTelefono, String auxDireccion)
    {
        numero++;
        this.id = numero;
        this.NIT = auxNIT;
        this.nombre = auxNombre;
        this.telefono = auxTelefono;
        this.direccion = auxDireccion;
    }

    public static void setNumeroCliente(int auxNumero)
    {
        Cliente.numero = auxNumero;
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

    public long getNIT()
    {
        return NIT;
    }

    public void setNIT(long auxNIT)
    {
        this.NIT = auxNIT;
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

    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }

    public String toString()
    {
        return nombre;
    }
}
