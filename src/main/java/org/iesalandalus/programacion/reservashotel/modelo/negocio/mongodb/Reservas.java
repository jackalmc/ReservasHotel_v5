package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class Reservas implements IReservas {
    private final String COLECCION="reservas";
    private List<Reserva> coleccionReservas;

    public Reservas(){
        comenzar();
    }
    @Override
    public List<Reserva> get(){

        List<Reserva> copiaOrdenada = new ArrayList<>();
        FindIterable<Document> documents = MongoDB.getBD().getCollection(COLECCION).find().sort(Sorts.descending(MongoDB.FECHA_INICIO_RESERVA));

        for (Document document:documents)
            copiaOrdenada.add(MongoDB.getReserva(document));

        return copiaOrdenada;
    }
    @Override
    public List<Reserva> getReservas(Huesped huesped){
        if (huesped==null)
            throw new NullPointerException("Un huésped nulo no tiene reservas");

        List<Reserva> copiaOrdenada = new ArrayList<>();
        FindIterable<Document> documents = MongoDB.getBD().getCollection(COLECCION).find().sort(Sorts.descending(MongoDB.FECHA_INICIO_RESERVA));

        for (Document document:documents)
            if (document.getString(MongoDB.HUESPED_DNI).equals(huesped.getDni()))
                copiaOrdenada.add(MongoDB.getReserva(document));

        return copiaOrdenada;
    }
    @Override
    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion){
        if (tipoHabitacion == null)
            throw new NullPointerException("TipoHabitacion nula");

        List<Reserva> copiaOrdenada = new ArrayList<>();
        FindIterable<Document> documents = MongoDB.getBD().getCollection(COLECCION).find().sort(Sorts.descending(MongoDB.FECHA_INICIO_RESERVA));

        for (Document document:documents)
            switch (tipoHabitacion) {
                case SIMPLE:
                    if (document.getString(MongoDB.HABITACION_TIPO).equals(MongoDB.TIPO_SIMPLE))
                        copiaOrdenada.add(MongoDB.getReserva(document));
                    break;
                case DOBLE:
                    if (document.getString(MongoDB.HABITACION_TIPO).equals(MongoDB.TIPO_DOBLE))
                        copiaOrdenada.add(MongoDB.getReserva(document));
                    break;
                case TRIPLE:
                    if (document.getString(MongoDB.HABITACION_TIPO).equals(MongoDB.TIPO_TRIPLE))
                        copiaOrdenada.add(MongoDB.getReserva(document));
                    break;
                case SUITE:
                    if (document.getString(MongoDB.HABITACION_TIPO).equals(MongoDB.TIPO_SUITE))
                        copiaOrdenada.add(MongoDB.getReserva(document));
                    break;
            }

        return copiaOrdenada;
    }
    @Override
    public List<Reserva> getReservas(Habitacion habitacion){
        if (habitacion == null)
            throw new NullPointerException("No hay reservas de una habitación nula");

        List<Reserva> copiaOrdenada = new ArrayList<>();
        FindIterable<Document> documents = MongoDB.getBD().getCollection(COLECCION).find().sort(Sorts.descending(MongoDB.FECHA_INICIO_RESERVA));

        for (Document document:documents)
            if (MongoDB.getHabitacion((Document) document.get(MongoDB.HABITACION)).equals(habitacion))
                copiaOrdenada.add(MongoDB.getReserva(document));

        return copiaOrdenada;
    }
    @Override
    public List<Reserva> getReservasFuturas(Habitacion habitacion){
        if (habitacion == null)
            throw new NullPointerException("No hay reservas de una habitación nula");

        List<Reserva> copiaOrdenada = new ArrayList<>();
        FindIterable<Document> documents = MongoDB.getBD().getCollection(COLECCION).find().sort(Sorts.descending(MongoDB.FECHA_INICIO_RESERVA));

        for (Document document:documents)
            if (MongoDB.getHabitacion((Document) document.get(MongoDB.HABITACION)).equals(habitacion)
            && LocalDate.parse(document.getString(MongoDB.FECHA_INICIO_RESERVA), MongoDB.FORMATO_DIA).isAfter(LocalDate.now()))
                copiaOrdenada.add(MongoDB.getReserva(document));

        return copiaOrdenada;
    }

    @Override
    public int getTamano(){
        return (int) MongoDB.getBD().getCollection(COLECCION).countDocuments();
    }
    @Override
    public void insertar(Reserva reserva){
        if (reserva == null)
            throw new NullPointerException("No se puede introducir una reserva nula");
        if (buscar(reserva) != null)
            throw new IllegalArgumentException("Ya existe esa reserva en la BD");

        Document documentoAIntroducir = MongoDB.getDocumento(reserva);
        MongoDB.getBD().getCollection(COLECCION).insertOne(documentoAIntroducir);
    }
    @Override
    public Reserva buscar(Reserva reserva){
        if (reserva == null)
            throw new NullPointerException("No se puede buscar una reserva nula");

        return MongoDB.getReserva(MongoDB.getBD().getCollection(COLECCION).find(
                Filters.and(
                        eq(MongoDB.HABITACION_IDENTIFICADOR, reserva.getHabitacion().getIdentificador()),
                        eq(MongoDB.FECHA_INICIO_RESERVA, reserva.getFechaInicioReserva())
                )).first());
    }
    @Override
    public void borrar(Reserva reserva){
        if (reserva == null)
            throw new NullPointerException("No se puede borrar una reserva nula");
        if (buscar(reserva) == null)
            throw new IllegalArgumentException("No existe esa reserva en la BD");

        MongoDB.getBD().getCollection(COLECCION).deleteOne(MongoDB.getDocumento(reserva));
    }
    @Override
    public void comenzar(){
        coleccionReservas = new ArrayList<>();
        FindIterable<Document> documents = MongoDB.getBD().getCollection(COLECCION).find();

        for (Document document : documents)
            coleccionReservas.add(MongoDB.getReserva(document));
    }
    @Override
    public void terminar(){
        MongoDB.cerrarConexion();
    }
    public void realizarCheckin(Reserva reserva, LocalDateTime fecha){
        if (reserva == null)
            throw new NullPointerException("La reserva es nula (Checkin)");
        if (fecha == null)
            throw new NullPointerException("La fecha es nula (Checkin)");
        if (fecha.isBefore(reserva.getFechaInicioReserva().atStartOfDay()))
            throw new IllegalArgumentException("El CheckIn no puede realizarse antes de la fecha de inicio de la reserva");
        if (fecha.isAfter(reserva.getFechaFinReserva().atStartOfDay()))
            throw new IllegalArgumentException("No puede hacerse un CheckIn después de la fecha fin de reserva");


        MongoDB.getBD().getCollection(COLECCION).updateOne(
                Filters.and(
                        eq(MongoDB.HABITACION_IDENTIFICADOR, reserva.getHabitacion().getIdentificador()),
                        eq(MongoDB.FECHA_INICIO_RESERVA, reserva.getFechaInicioReserva())
                ), Updates.set(MongoDB.CHECKIN, fecha));

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
            throw new IllegalArgumentException("El Checkout no puede hacerse después del periodo máximo permitido");

        MongoDB.getBD().getCollection(COLECCION).updateOne(
                Filters.and(
                        eq(MongoDB.HABITACION_IDENTIFICADOR, reserva.getHabitacion().getIdentificador()),
                        eq(MongoDB.FECHA_INICIO_RESERVA, reserva.getFechaInicioReserva())
                ), Updates.set(MongoDB.CHECKOUT, fecha));
    }
}
