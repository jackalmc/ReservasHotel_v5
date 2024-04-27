package org.iesalandalus.programacion.reservashotel;


import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;
import org.iesalandalus.programacion.reservashotel.vista.FactoriaVista;
import org.iesalandalus.programacion.reservashotel.vista.Vista;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;


public class MainApp {
    public static void main(String[] args) {

        Vista vista = procesarArgumentosVista(args);
        Modelo modelo = procesarArgumentosFuenteDatos(args);
        Controlador controlador = new Controlador(vista, modelo);
        controlador.comenzar();
    }

    public static Modelo procesarArgumentosFuenteDatos(String[] args){
        Modelo modeloADevoler=null;
        try {
            if (args.length<=1 || args[0].isBlank()) {
                System.out.println("\n*********************************************");
                System.out.println("*** Necesito argumentos para funcionar!!! ***");
                System.out.println("*********************************************\n");
                System.exit(0);
            }
            if (args[0].equalsIgnoreCase("-fdmemoria") || args[1].equalsIgnoreCase("-fdmemoria"))
                modeloADevoler = new Modelo(FactoriaFuenteDatos.MEMORIA);
            else if (args[0].equalsIgnoreCase("-fdmongodb") || args[1].equalsIgnoreCase("-fdmongodb")) {
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
    public static Vista procesarArgumentosVista(String[] args){
        Vista vistaADevolver=null;
        try {
            if ( args.length<=1 || args[1].isBlank()) {
                System.out.println("\n*********************************************");
                System.out.println("*** Necesito argumentos para funcionar!!! ***");
                System.out.println("*********************************************\n");
                System.exit(0);
            }
            if (args[1].equalsIgnoreCase("-vTexto") || args[0].equalsIgnoreCase("-vTexto"))
                vistaADevolver = FactoriaVista.TEXTO.crear();
            else if (args[1].equalsIgnoreCase("-vGrafica") || args[0].equalsIgnoreCase("-vGrafica")) {
                MongoDB.establecerConexion(); //Esto hace que lo tenga que poner público. Mantiene la conexión abierta con la app.
                vistaADevolver = FactoriaVista.GRAFICA.crear();
            }
            //todo crear menu en caso de argumentos no válidos??.

            return vistaADevolver;

        } catch (ArrayIndexOutOfBoundsException|NullPointerException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }

        return vistaADevolver;
    }
}
