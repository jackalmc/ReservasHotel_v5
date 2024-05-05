package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Reserva {
    public static final int MAX_NUMERO_MESES_RESERVA = 6;
    public static final int MAX_HORAS_POSTERIOR_CHECKOUT = 12;
    public static final String FORMATO_FECHA_RESERVA = "dd/MM/yyyy";
    public static final String FORMATO_FECHA_HORA_RESERVA = "dd/MM/yyyy HH:mm:ss";
    private Huesped huesped;
    private Habitacion habitacion;
    private Regimen regimen;
    private LocalDate fechaInicioReserva;
    private LocalDate fechaFinReserva;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private double precio;
    private int numeroPersonas;

    public Huesped getHuesped() {
        return new Huesped(huesped);
    }

    public void setHuesped(Huesped huesped) {
        if (huesped == null)
            throw new NullPointerException("huesped nulo (set)");

        this.huesped = new Huesped(huesped);
    }

    public Habitacion getHabitacion() {

        if (habitacion instanceof Simple)
            return new Simple((Simple) habitacion);
        else if (habitacion instanceof Doble)
            return new Doble((Doble) habitacion);
        else if (habitacion instanceof Triple)
            return new Triple((Triple) habitacion);
        else
            return new Suite((Suite) habitacion);

    }

    public void setHabitacion(Habitacion habitacion) {
        if (habitacion==null)
            throw new NullPointerException("habitacion nula(set)");

        if (habitacion instanceof Simple)
            this.habitacion = new Simple((Simple) habitacion);
        if (habitacion instanceof Doble)
            this.habitacion = new Doble((Doble) habitacion);
        if (habitacion instanceof Triple)
            this.habitacion = new Triple((Triple) habitacion);
        if (habitacion instanceof Suite)
            this.habitacion = new Suite((Suite) habitacion);

    }

    public Regimen getRegimen() {
        return regimen;
    }

    public void setRegimen(Regimen regimen) {
        if (regimen==null)
            throw new NullPointerException("regimen nula(set)");
        this.regimen = regimen;
    }

    public LocalDate getFechaInicioReserva() {
        return fechaInicioReserva;
    }

    public void setFechaInicioReserva(LocalDate fechaInicioReserva) {
        if (fechaInicioReserva==null)
            throw new NullPointerException("ERROR: La fecha de inicio de una reserva no puede ser nula.");
        if (fechaInicioReserva.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser anterior al dia de hoy.");
        if (fechaInicioReserva.minusMonths(MAX_NUMERO_MESES_RESERVA).isAfter(LocalDate.now()))
            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser posterior a seis meses.");

        this.fechaInicioReserva = fechaInicioReserva;
    }

    public LocalDate getFechaFinReserva() {
        return fechaFinReserva;
    }

    public void setFechaFinReserva(LocalDate fechaFinReserva) {
        if (fechaFinReserva==null)
            throw new NullPointerException("ERROR: La fecha de fin de una reserva no puede ser nula.");
        if (fechaFinReserva.isBefore(fechaInicioReserva))
            throw new IllegalArgumentException("ERROR: La fecha de fin de la reserva debe ser posterior a la de inicio.");
        if (fechaFinReserva.isEqual(fechaInicioReserva))
            throw new IllegalArgumentException("ERROR: La fecha de fin de la reserva debe ser posterior a la de inicio.");

        this.fechaFinReserva = fechaFinReserva;
    }

    public LocalDateTime getCheckIn() {

        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        if (checkIn==null)
            throw new NullPointerException("ERROR: El checkin de una reserva no puede ser nulo.");
        if (checkIn.isBefore(fechaInicioReserva.atStartOfDay()))
            throw new IllegalArgumentException("ERROR: El checkin de una reserva no puede ser anterior a la fecha de inicio de la reserva.");

        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        if (checkOut==null)
            throw new NullPointerException("ERROR: El checkout de una reserva no puede ser nulo.");
        if (checkIn == null)
            throw new NullPointerException("No se puede realizar CheckOut antes de hacer CheckIn");
        if (checkOut.isBefore(checkIn))
            throw new IllegalArgumentException("ERROR: El checkout de una reserva no puede ser anterior al checkin.");
        if (fechaFinReserva.atStartOfDay().plusHours(MAX_HORAS_POSTERIOR_CHECKOUT).isBefore(checkOut))
            throw new IllegalArgumentException("ERROR: El checkout de una reserva puede ser como maximo 12 horas despues de la fecha de fin de la reserva.");

        this.checkOut = checkOut;
        setPrecio();
    }

    public double getPrecio() {
        return precio;
    }

    private void setPrecio() {

        //Si tiene checkin y checkout, calculamos por el periodo final de estancia. A las pruebas no les gusta esto, asi que lo comento.
        /* if (getCheckIn() != null && getCheckOut() != null) {
            Period period2 = Period.between(getCheckIn().toLocalDate(), getCheckOut().toLocalDate());
            precio = (habitacion.getPrecio() * period2.getDays()) + (regimen.getIncrementoPrecio() * numeroPersonas * period2.getDays());
        }else{ */
            Period period = Period.between(fechaInicioReserva, fechaFinReserva);
            precio = (habitacion.getPrecio() * period.getDays()) + (regimen.getIncrementoPrecio() * numeroPersonas * period.getDays());
        //}
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        if (numeroPersonas < 1)
            throw new IllegalArgumentException("ERROR: El numero de personas de una reserva no puede ser menor o igual a 0.");
        if (numeroPersonas > habitacion.getNumeroMaximoPersonas())
            throw new IllegalArgumentException("ERROR: El numero de personas de una reserva no puede superar al maximo de personas establacidas para el tipo de habitacion reservada."); //hay un pequenio tipo aqui, pero es lo que pide
        this.numeroPersonas = numeroPersonas;
    }

    public Reserva(Huesped huesped, Habitacion habitacion, Regimen regimen, LocalDate fechaInicioReserva, LocalDate fechaFinReserva, int numeroPersonas){
        if (huesped == null)
            throw new NullPointerException("ERROR: El huesped de una reserva no puede ser nulo.");
        if (habitacion==null)
            throw new NullPointerException("ERROR: La habitacion de una reserva no puede ser nula.");
        if (regimen==null)
            throw new NullPointerException("ERROR: El regimen de una reserva no puede ser nulo.");
        if (fechaInicioReserva==null)
            throw new NullPointerException("ERROR: La fecha de inicio de una reserva no puede ser nula.");
        if (fechaFinReserva==null)
            throw new NullPointerException("ERROR: La fecha de fin de una reserva no puede ser nula.");

        setHuesped(huesped);
        setHabitacion(habitacion);
        setRegimen(regimen);
        setFechaInicioReserva(fechaInicioReserva);
        setFechaFinReserva(fechaFinReserva);
        setNumeroPersonas(numeroPersonas);
        setPrecio();
    }

    public Reserva(Reserva reserva){ //Ineficiente volverlo a pasar por las validaciones???
        if (reserva==null)
            throw new NullPointerException("ERROR: No es posible copiar una reserva nula.");

        setHuesped(reserva.getHuesped());
        setHabitacion(reserva.getHabitacion());
        setRegimen(reserva.getRegimen());
        setFechaInicioReserva(reserva.getFechaInicioReserva());
        setFechaFinReserva(reserva.getFechaFinReserva());
        setNumeroPersonas(reserva.getNumeroPersonas());

        if (reserva.getCheckIn() == null)
            checkIn = null;
        else
            setCheckIn(reserva.getCheckIn());

        if (reserva.getCheckOut() == null)
            checkOut = null;
        else
            setCheckOut(reserva.getCheckOut());

        setPrecio();

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(habitacion, reserva.habitacion) && Objects.equals(fechaInicioReserva, reserva.fechaInicioReserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(habitacion, fechaInicioReserva);
    }

    @Override
    public String toString() {

        return String.format("Huesped: %s %s Habitacion:%s Fecha Inicio Reserva: %s Fecha Fin Reserva: %s Checkin: %s Checkout: %s Precio: %.2f Personas: %d",
                getHuesped().getNombre(),
                getHuesped().getDni(),
                getHabitacion(),
                getFechaInicioReserva().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA)),
                getFechaFinReserva().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA)),
                getCheckIn() != null ? getCheckIn().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA_RESERVA)) : "No registrado",
                getCheckOut() != null ? getCheckOut().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA_RESERVA)) : "No registrado",
                getPrecio(),
                getNumeroPersonas());

    }
}
