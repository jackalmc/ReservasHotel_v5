package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;

public class FuenteDatosMongoDB implements IFuenteDatos {

    @Override
    public IHuespedes crearHuespedes(){ //todo quizás query a la BD por la base de datos?
        return new Huespedes();
    }
    @Override
    public IHabitaciones crearHabitaciones(){
        return new Habitaciones();
    }
    @Override
    public IReservas crearReservas(){
        return new Reservas();
    }

}
