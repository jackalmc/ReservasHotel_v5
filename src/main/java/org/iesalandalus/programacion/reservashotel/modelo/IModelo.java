package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.List;

public interface IModelo {

    public void comenzar();
    public void terminar();

    public List<Huesped> getHuespedes();
    public void insertar(Huesped huesped) throws OperationNotSupportedException;
    public Huesped buscar(Huesped huesped);
    public void borrar(Huesped huesped) throws OperationNotSupportedException;

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException;
    public Habitacion buscar(Habitacion habitacion);
    public void borrar(Habitacion habitacion) throws OperationNotSupportedException;
    public List<Habitacion> getHabitaciones();
    public List<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion);

    public void insertar(Reserva reserva) throws OperationNotSupportedException;
    public Reserva buscar(Reserva reserva);
    public void borrar(Reserva reserva) throws OperationNotSupportedException;
    public List<Reserva> getReservas();
    public List<Reserva> getReservas(Huesped huesped);
    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion);
    public List<Reserva> getReservas(Habitacion habitacion);
    public List<Reserva> getReservasFuturas(Habitacion habitacion);

    public void realizarCheckin(Reserva reserva, LocalDateTime fecha);
    public void realizarCheckout(Reserva reserva, LocalDateTime fecha);

}
