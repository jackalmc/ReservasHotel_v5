package org.iesalandalus.programacion.reservashotel.vista.grafica;

import org.iesalandalus.programacion.reservashotel.vista.Vista;

public class VistaGrafica extends Vista {

    private VistaGrafica instancia;

    public VistaGrafica(){

    }

    public VistaGrafica getInstancia() {
        if (instancia==null)
            instancia=new VistaGrafica();

        return instancia;
    }

    @Override
    public void comenzar() {
        getInstancia();
    }

    @Override
    public void terminar() {
        getControlador().terminar();
    }
}
