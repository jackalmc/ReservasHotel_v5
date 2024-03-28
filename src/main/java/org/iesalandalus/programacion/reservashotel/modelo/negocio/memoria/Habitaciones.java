package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;


import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Habitaciones implements IHabitaciones {

    private List<Habitacion> coleccionHabitaciones;

    public Habitaciones(){

        coleccionHabitaciones = new ArrayList<>();

    }

    @Override
    public List<Habitacion> get(){
        return copiaProfundaHabitaciones();
    }

    @Override
    public List<Habitacion> get(TipoHabitacion tipoHabitacion){
        if (coleccionHabitaciones==null)
            throw new NullPointerException("La colección no ha sido creada aún");

        List<Habitacion> copiaEspecial = new ArrayList<>();

        for (Habitacion habitacion : coleccionHabitaciones) {
            switch (tipoHabitacion) {
                case SIMPLE:
                    if (habitacion instanceof Simple)
                        copiaEspecial.add(new Simple((Simple) habitacion));
                    break;
                case DOBLE:
                    if (habitacion instanceof Doble)
                        copiaEspecial.add(new Doble((Doble) habitacion));
                    break;
                case TRIPLE:
                    if (habitacion instanceof Triple)
                        copiaEspecial.add(new Triple((Triple) habitacion));
                    break;
                case SUITE:
                    if (habitacion instanceof Suite)
                        copiaEspecial.add(new Suite((Suite) habitacion));
                    break;
            }
        }

        return copiaEspecial;
    }

    private List<Habitacion> copiaProfundaHabitaciones(){
        if (coleccionHabitaciones==null)
            throw new NullPointerException("La colección no ha sido creada aún");

        List<Habitacion> copia = new ArrayList<>();

        for (Habitacion habitacion : coleccionHabitaciones) {
            if (habitacion instanceof Simple)
                copia.add(new Simple((Simple) habitacion));
            if (habitacion instanceof Doble)
                copia.add(new Doble((Doble) habitacion));
            if (habitacion instanceof Triple)
                copia.add(new Triple((Triple) habitacion));
            if (habitacion instanceof Suite)
                copia.add(new Suite((Suite) habitacion));
        }

        return copia;
    }

    @Override
    public int getTamano() {
        return coleccionHabitaciones.size();
    }

    @Override
    public void insertar(Habitacion habitacion)throws OperationNotSupportedException{
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        if (coleccionHabitaciones.contains(habitacion))
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");

        if (habitacion instanceof Simple)
            coleccionHabitaciones.add(new Simple((Simple) habitacion));
        if (habitacion instanceof Doble)
            coleccionHabitaciones.add(new Doble((Doble) habitacion));
        if (habitacion instanceof Triple)
            coleccionHabitaciones.add(new Triple((Triple) habitacion));
        if (habitacion instanceof Suite)
            coleccionHabitaciones.add(new Suite((Suite) habitacion));

    }

    @Override
    public Habitacion buscar(Habitacion habitacion){
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");

        Habitacion habitacionEncontrada = null;
        for (Habitacion habitacion1 : coleccionHabitaciones){
            if (habitacion1.equals(habitacion)) {
                if (habitacion1 instanceof Simple)
                    habitacionEncontrada = new Simple((Simple) habitacion1);
                else if (habitacion1 instanceof Doble)
                    habitacionEncontrada = new Doble((Doble) habitacion1);
                else if (habitacion1 instanceof Triple)
                    habitacionEncontrada = new Triple((Triple) habitacion1);
                else if (habitacion1 instanceof Suite)
                    habitacionEncontrada = new Suite((Suite) habitacion1);
            }
        }

        return habitacionEncontrada;
    }

    @Override
    public void borrar(Habitacion habitacion)throws OperationNotSupportedException{
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        if (!coleccionHabitaciones.contains(habitacion))
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");


        coleccionHabitaciones.remove(habitacion);

    }
    @Override
    public void comenzar(){}
    @Override
    public void terminar(){}

}
