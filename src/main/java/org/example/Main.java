package org.example;

import controlador.ControladorMercado;
import modelo.Empresa;
import vista.VentanaMercado;

public class Main {
    public static void main(String[] args)
    {
        VentanaMercado ventanaMercado = new VentanaMercado();
        Empresa superMercadoUV = new Empresa("SuperMercado Univalle");
        ControladorMercado controladorMercado = new ControladorMercado(superMercadoUV, ventanaMercado);
    }
}