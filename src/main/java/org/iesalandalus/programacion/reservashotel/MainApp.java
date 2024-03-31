package org.iesalandalus.programacion.reservashotel;


import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.vista.Vista;


public class MainApp {
    public static void main(String[] args) {

        Vista vista = new Vista();
        Modelo modelo = procesarArgumentosFuenteDatos(args);
        Controlador controlador = new Controlador(vista, modelo);
        controlador.comenzar();
    }

    public static Modelo procesarArgumentosFuenteDatos(String[] args){
        if (args[1].isBlank())
            System.out.println("Este programa no se puede ejecutar sin parámetros de inicio.");
        if (args[1].equalsIgnoreCase("-fdmemoria"))
            return new Modelo(FactoriaFuenteDatos.MEMORIA);
        if (args[1].equalsIgnoreCase("-fdmongodb"))
            return new Modelo(FactoriaFuenteDatos.MONGODB);
        //todo crear menu en caso de argumentos no válidos.

        else{
            System.out.println("No hay argumentos");
            return null;
        }
    }
}
