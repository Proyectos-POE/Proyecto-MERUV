package org.example;

import controlador.ControladorMercado;
import modelo.Empresa;
import vista.VentanaMercado;

/*
 * @author Nicolas Herrera <herrera.nicolas@correounivalle.edu.co>
 * @author Samuel Galindo Cuevas <samuel.galindo@correounivalle.edu.co>
 * @author Julian Rendon <julian.david.rendon@correounivalle.edu.co>
 */

public class Main {
    public static void main(String[] args)
    {
        VentanaMercado ventanaMercado = new VentanaMercado();
        Empresa superMercadoUV = new Empresa("SuperMercado Univalle");
        ControladorMercado controladorMercado = new ControladorMercado(superMercadoUV, ventanaMercado);
        controladorMercado.recuperarDatos();
    }
}