package org.iesalandalus.programacion.reservashotel;


import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;


public class MainApp {
    public static void main(String[] args) {

        VistaTexto vista = new VistaTexto();
        Modelo modelo = procesarArgumentosFuenteDatos(args);
        Controlador controlador = new Controlador(vista, modelo);
        controlador.comenzar();
    }

    public static Modelo procesarArgumentosFuenteDatos(String[] args){
        Modelo modeloADevoler=null;
        try {
            if (args[0].isBlank()) {
                System.out.println("\n*********************************************");
                System.out.println("*** Necesito argumentos para funcionar!!! ***");
                System.out.println("*********************************************\n");
                System.exit(0);
            }
            if (args[0].equalsIgnoreCase("-fdmemoria"))
                modeloADevoler = new Modelo(FactoriaFuenteDatos.MEMORIA);
            else if (args[0].equalsIgnoreCase("-fdmongodb")) {
                MongoDB.establecerConexion(); //Esto hace que lo tenga que poner público. Mantiene la conexión abierta con la app.
                modeloADevoler = new Modelo(FactoriaFuenteDatos.MONGODB);
            }
                //todo crear menu en caso de argumentos no válidos??.

            return modeloADevoler;

        } catch (ArrayIndexOutOfBoundsException|NullPointerException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }

        return modeloADevoler;
    }
}
