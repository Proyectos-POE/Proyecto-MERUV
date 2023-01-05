package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/* 
 * @author Nicolas Herrera <herrera.nicolas@correounivalle.edu.co>
 * @author Samuel Galindo Cuevas <samuel.galindo@correounivalle.edu.co>
 * @author Julian Rendon <julian.david.rendon@correounivalle.edu.co>
 */

public class Fecha implements Serializable
{
    private LocalDate fecha;
    private static final long serialVersionUID = 1L;

    public Fecha(LocalDate auxFecha)
    {
        this.fecha = auxFecha;
        formatearFecha();
    }

    public void formatearFecha()
    {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        fecha.format(formato);
    }

    public LocalDate getFecha()
    {
        return fecha;
    }

    public void setFecha(LocalDate auxFecha)
    {
        this.fecha = auxFecha;
    }
}
