package org.iesalandalus.programacion.reservashotel.controlador;

import org.iesalandalus.programacion.reservashotel.modelo.IModelo;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.vista.Vista;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;


import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.List;

public class Controlador {

    private IModelo modelo;
    private Vista vista;

    public Controlador(Vista vista, Modelo modelo){

        if (vista==null)
            System.out.println("Vista no puede ser nula");
        if (modelo==null)
            System.out.println("Modelo no puede ser nulo");

        this.vista=vista;
        this.modelo=modelo;
        vista.setControlador(this);

    }
    public void comenzar(){
        vista.comenzar();
        modelo.comenzar();
    }

    public void terminar(){
        vista.terminar();
        modelo.terminar();
        System.out.println("*** Controlador ha pasado a mejor vida! ***");
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        modelo.insertar(huesped);
    }

    public Huesped buscar(Huesped huesped){
        return modelo.buscar(huesped);
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        modelo.borrar(huesped);
    }

    public List<Huesped> getHuespedes(){
        return modelo.getHuespedes();
    }

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        modelo.insertar(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion){
        return modelo.buscar(habitacion);
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        modelo.borrar(habitacion);
    }

    public List<Habitacion> getHabitaciones(){
        return modelo.getHabitaciones();
    }

    public List<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion){
        return modelo.getHabitaciones(tipoHabitacion);
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        modelo.insertar(reserva);
    }

    public Reserva buscar(Reserva reserva){
        return modelo.buscar(reserva);
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        modelo.borrar(reserva);
    }

    public List<Reserva> getReservas(){
        return modelo.getReservas();
    }

    public List<Reserva> getReservas(Huesped huesped){
        return modelo.getReservas(huesped);
    }

    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion){
        return modelo.getReservas(tipoHabitacion);
    }

    public List<Reserva> getReservas(Habitacion habitacion){
        return modelo.getReservas(habitacion);
    }

    public List<Reserva> getReservasFuturas(Habitacion habitacion){
        return modelo.getReservasFuturas(habitacion);
    }

    public void realizarCheckin(Reserva reserva, LocalDateTime fecha){
        modelo.realizarCheckin(reserva,fecha);
    }

    public void realizarCheckout(Reserva reserva, LocalDateTime fecha){
        modelo.realizarCheckout(reserva,fecha);
    }
}
