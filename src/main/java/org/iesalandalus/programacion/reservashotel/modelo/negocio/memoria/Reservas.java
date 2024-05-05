package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Reservas implements IReservas {

    private List<Reserva> coleccionReservas;

    public Reservas(){

        coleccionReservas = new ArrayList<>();

    }

    @Override
    public List<Reserva> get(){
        return copiaProfundaReservas();
    }

    private List<Reserva> copiaProfundaReservas(){
        if (coleccionReservas==null)
            throw new NullPointerException("La coleccion no ha sido creada aun");

        List<Reserva> copia = new ArrayList<>();

        Iterator<Reserva> reservaIterator = coleccionReservas.iterator();
        while (reservaIterator.hasNext())
            copia.add(new Reserva(reservaIterator.next()));

        return copia;
    }

    @Override
    public int getTamano() {
        return coleccionReservas.size();
    }

    @Override
    public void insertar(Reserva reserva) throws OperationNotSupportedException{
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        if (coleccionReservas.contains(reserva))
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");

        coleccionReservas.add(new Reserva(reserva));

    }

    @Override
    public Reserva buscar(Reserva reserva){
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");

        if (coleccionReservas.contains(reserva))
            return coleccionReservas.get(coleccionReservas.indexOf(reserva));
        else
            return null;

    }

    @Override
    public void borrar(Reserva reserva) throws OperationNotSupportedException{
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        if (!coleccionReservas.contains(reserva))
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");


        coleccionReservas.remove(reserva);
    }

    @Override
    public List<Reserva> getReservas(Huesped huesped){
        if (coleccionReservas==null)
            throw new NullPointerException("La coleccion no ha sido creada aun");
        if (huesped == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huesped nulo.");

        List<Reserva> copiaEspecial = new ArrayList<>();

        Iterator<Reserva> reservaIterator = coleccionReservas.iterator();
        Reserva token;
        while (reservaIterator.hasNext()){
            token = reservaIterator.next();
            if (token.getHuesped().equals(huesped))
                copiaEspecial.add(new Reserva(token));
        }

        return copiaEspecial;

    }
    @Override
    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion){
        if (coleccionReservas==null)
            throw new NullPointerException("La coleccion no ha sido creada aun");
        if (tipoHabitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitacion nula.");

        List<Reserva> copiaEspecial = new ArrayList<>();

        for (Reserva reserva : coleccionReservas) {
            switch (tipoHabitacion) {
                case SIMPLE:
                    if (reserva.getHabitacion() instanceof Simple)
                        copiaEspecial.add(new Reserva(reserva));
                    break;
                case DOBLE:
                    if (reserva.getHabitacion() instanceof Doble)
                        copiaEspecial.add(new Reserva(reserva));
                    break;
                case TRIPLE:
                    if (reserva.getHabitacion() instanceof Triple)
                        copiaEspecial.add(new Reserva(reserva));
                    break;
                case SUITE:
                    if (reserva.getHabitacion() instanceof Suite)
                        copiaEspecial.add(new Reserva(reserva));
                    break;
            }
        }

        return copiaEspecial;
    }
    @Override
    public List<Reserva> getReservas(Habitacion habitacion){
        if (coleccionReservas==null)
            throw new NullPointerException("La coleccion no ha sido creada aun");
        if (habitacion==null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitacion nula.");

        List<Reserva> copiaEspecial = new ArrayList<>();

        for (Reserva reserva: coleccionReservas)
            if (reserva.getHabitacion().equals(habitacion))
                copiaEspecial.add(reserva);

        return copiaEspecial;
    }
    @Override
    public List<Reserva> getReservasFuturas(Habitacion habitacion){
        if (coleccionReservas==null)
            throw new NullPointerException("La coleccion no ha sido creada aun");
        if (habitacion==null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitacion nula.");

        List<Reserva> copiaEspecial = new ArrayList<>();

        Iterator<Reserva> reservaIterator = coleccionReservas.iterator();
        Reserva token;
        while (reservaIterator.hasNext()){
            token = reservaIterator.next();
            if (token.getFechaInicioReserva().isAfter(LocalDate.now()) && token.getHabitacion().equals(habitacion))
                copiaEspecial.add(new Reserva(token));
        }

        return copiaEspecial;
    }

    @Override
    public void realizarCheckin(Reserva reserva, LocalDateTime fecha){
        if (reserva == null)
            throw new NullPointerException("La reserva es nula (Checkin)");
        if (fecha == null)
            throw new NullPointerException("La fecha es nula (Checkin)");
        if (fecha.isBefore(reserva.getFechaInicioReserva().atStartOfDay()))
            throw new IllegalArgumentException("El CheckIn no puede realizarse antes de la fecha de inicio de la reserva");
        if (fecha.isAfter(reserva.getFechaFinReserva().atStartOfDay()))
            throw new IllegalArgumentException("No puede hacerse un CheckIn despues de la fecha fin de reserva");


        reserva.setCheckIn(fecha);
    }

    @Override
    public void realizarCheckout(Reserva reserva, LocalDateTime fecha){
        if (reserva == null)
            throw new NullPointerException("La reserva es nula (CheckOut)");
        if (fecha == null)
            throw new NullPointerException("La fecha es nula (CheckOut)");
        if (reserva.getCheckIn() == null)
            throw new NullPointerException("No se puede realizar CheckOut antes de hacer CheckIn");
        if (fecha.isBefore(reserva.getCheckIn()))
            throw new IllegalArgumentException("No se puede hacer un CheckOut antes de la fecha del CheckIn");
        if (fecha.isAfter(reserva.getFechaFinReserva().atStartOfDay().plusHours(Reserva.MAX_HORAS_POSTERIOR_CHECKOUT)))
            throw new IllegalArgumentException("El Checkout no puede hacerse despues del periodo maximo permitido");

        reserva.setCheckOut(fecha);
    }
    @Override
    public void comenzar(){}
    @Override
    public void terminar(){}
}
