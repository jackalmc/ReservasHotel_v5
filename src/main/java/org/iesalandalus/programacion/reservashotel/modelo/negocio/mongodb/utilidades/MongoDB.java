package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades;

import com.mongodb.client.*;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;

import org.bson.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.mongodb.client.model.Filters.eq;

public class MongoDB {

    public static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter FORMATO_DIA_HORA = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private static final String SERVIDOR = "mongodb+srv://<username>:<password>@reservashotel.kd0h77u.mongodb.net/?retryWrites=true&w=majority&appName=reservashotel";

    private static final int PUERTO = 27017;
    private static final String BD = "reservashotel";
    private static final String USUARIO = "reservashotel";
    private static final String CONTRASENA = "reservashotel-2024";

    public static final String HUESPED = "huesped";
    public static final String NOMBRE = "nombre";
    public static final String DNI = "dni";
    public static final String TELEFONO = "telefono";
    public static final String CORREO = "correo";
    public static final String FECHA_NACIMIENTO = "fecha_nacimiento";
    public static final String HUESPED_DNI = HUESPED + "." + DNI;
    public static final String HABITACION = "habitacion";
    public static final String IDENTIFICADOR = "identificador";
    public static final String PLANTA = "planta";
    public static final String PUERTA = "puerta";
    public static final String PRECIO = "precio";
    public static final String HABITACION_IDENTIFICADOR = HABITACION + "." + IDENTIFICADOR;
    public static final String TIPO = "tipo";
    public static final String HABITACION_TIPO = HABITACION + "." + TIPO;
    public static final String TIPO_SIMPLE = "SIMPLE";
    public static final String TIPO_DOBLE = "DOBLE";
    public static final String TIPO_TRIPLE = "TRIPLE";
    public static final String TIPO_SUITE = "SUITE";
    public static final String CAMAS_INDIVIDUALES = "camas_individuales";
    public static final String CAMAS_DOBLES = "camas_dobles";
    public static final String BANOS = "banos";
    public static final String JACUZZI = "jacuzzi";
    public static final String REGIMEN = "regimen";
    public static final String FECHA_INICIO_RESERVA = "fecha_inicio_reserva";
    public static final String FECHA_FIN_RESERVA = "fecha_fin_reserva";
    public static final String CHECKIN = "checkin";
    public static final String CHECKOUT = "checkout";
    public static final String PRECIO_RESERVA = "precio_reserva";
    public static final String NUMERO_PERSONAS = "numero_personas";
    private MongoClient conexion = null; // static?

    private MongoDB(){
        establecerConexion();
    }

    public MongoDatabase getBD(){
        if (conexion == null)
            throw new NullPointerException("Conexi�n es nula");

        return conexion.getDatabase(BD);
    }

    private void establecerConexion(){
        conexion = MongoClients.create(SERVIDOR);
    }

    public void cerrarconexion(){
        conexion.close();
    }

    public Document getDocumento(Huesped huesped){
        /* (Document) getBD().getCollection("huespedes").
                find(eq(DNI, huesped.getDni())).first() */
        return new Document("_id", new ObjectId())
                .append(NOMBRE, huesped.getNombre())
                .append(DNI, huesped.getDni())
                .append(TELEFONO, huesped.getTelefono())
                .append(CORREO, huesped.getCorreo())
                .append(FECHA_NACIMIENTO, huesped.getFechaNacimiento());
    }

    public Huesped getHuesped(Document documentoHuesped){
        return new Huesped(
                documentoHuesped.getString(NOMBRE),
                documentoHuesped.getString(DNI),
                documentoHuesped.getString(CORREO),
                documentoHuesped.getString(TELEFONO),
                LocalDate.parse(documentoHuesped.getString(FECHA_NACIMIENTO), FORMATO_DIA)
        );
    }

    public Document getDocumento(Habitacion habitacion){
        /* (Document) getBD().getCollection("habitaciones").
                find(eq(IDENTIFICADOR, habitacion.getIdentificador())).first() */

        if (habitacion instanceof Simple){
            return new Document("_id",new ObjectId())
                    .append(IDENTIFICADOR, habitacion.getIdentificador())
                    .append(TIPO, TIPO_SIMPLE)
                    .append(PRECIO, habitacion.getPrecio())
                    .append(NUMERO_PERSONAS, habitacion.getNumeroMaximoPersonas())
                    .append(PLANTA, habitacion.getPlanta())
                    .append(PUERTA, habitacion.getPuerta());
        }else if (habitacion instanceof Doble){
            return new Document("_id",new ObjectId())
                    .append(IDENTIFICADOR, habitacion.getIdentificador())
                    .append(TIPO, TIPO_DOBLE)
                    .append(PRECIO, habitacion.getPrecio())
                    .append(NUMERO_PERSONAS, habitacion.getNumeroMaximoPersonas())
                    .append(PLANTA, habitacion.getPlanta())
                    .append(PUERTA, habitacion.getPuerta())
                    .append(CAMAS_INDIVIDUALES, ((Doble) habitacion).getNumCamasIndividuales())
                    .append(CAMAS_DOBLES, ((Doble) habitacion).getNumCamasDobles());
        }else if (habitacion instanceof Triple){
            return new Document("_id",new ObjectId())
                    .append(IDENTIFICADOR, habitacion.getIdentificador())
                    .append(TIPO, TIPO_TRIPLE)
                    .append(PRECIO, habitacion.getPrecio())
                    .append(NUMERO_PERSONAS, habitacion.getNumeroMaximoPersonas())
                    .append(PLANTA, habitacion.getPlanta())
                    .append(PUERTA, habitacion.getPuerta())
                    .append(BANOS, ((Triple) habitacion).getNumBanos());
        }else if(habitacion instanceof Suite){
            return new Document("_id",new ObjectId())
                    .append(IDENTIFICADOR, habitacion.getIdentificador())
                    .append(TIPO, TIPO_SUITE)
                    .append(PRECIO, habitacion.getPrecio())
                    .append(NUMERO_PERSONAS, habitacion.getNumeroMaximoPersonas())
                    .append(PLANTA, habitacion.getPlanta())
                    .append(PUERTA, habitacion.getPuerta())
                    .append(JACUZZI, ((Suite) habitacion).isTieneJacuzzi());
        }else return null;
    }

    public Habitacion getHabitacion(Document documentoHabitacion){
        Habitacion habitacion = null;

        switch (documentoHabitacion.getString(TIPO)){
            case TIPO_SIMPLE -> habitacion = new Simple(
                        documentoHabitacion.getInteger(PLANTA),
                        documentoHabitacion.getInteger(PUERTA),
                        documentoHabitacion.getDouble(PRECIO)
                );
            case TIPO_DOBLE -> habitacion = new Doble(
                    documentoHabitacion.getInteger(PLANTA),
                    documentoHabitacion.getInteger(PUERTA),
                    documentoHabitacion.getDouble(PRECIO),
                    documentoHabitacion.getInteger(CAMAS_INDIVIDUALES),
                    documentoHabitacion.getInteger(CAMAS_DOBLES)
            );
            case TIPO_TRIPLE -> habitacion = new Triple(
                    documentoHabitacion.getInteger(PLANTA),
                    documentoHabitacion.getInteger(PUERTA),
                    documentoHabitacion.getDouble(PRECIO),
                    documentoHabitacion.getInteger(BANOS),
                    documentoHabitacion.getInteger(CAMAS_INDIVIDUALES),
                    documentoHabitacion.getInteger(CAMAS_DOBLES)
            );
            case TIPO_SUITE -> habitacion = new Suite(
                    documentoHabitacion.getInteger(PLANTA),
                    documentoHabitacion.getInteger(PUERTA),
                    documentoHabitacion.getDouble(PRECIO),
                    documentoHabitacion.getInteger(BANOS),
                    documentoHabitacion.getBoolean(JACUZZI)
            );
        }
        return habitacion;
    }

    public Document getDocumento(Reserva reserva){
        /* (Document) getBD().getCollection("reservas").
                find(
                        Filters.and(
                        eq(HABITACION_IDENTIFICADOR, reserva.getHabitacion().getIdentificador()),
                        eq(FECHA_INICIO_RESERVA, reserva.getFechaInicioReserva())
                        )
                ).first() */

        return new Document("_id", new ObjectId())
                .append(HUESPED, getDocumento(reserva.getHuesped()))
                .append(HABITACION, getDocumento(reserva.getHabitacion()))
                .append(REGIMEN, reserva.getRegimen())
                .append(FECHA_INICIO_RESERVA, reserva.getFechaInicioReserva())
                .append(FECHA_FIN_RESERVA, reserva.getFechaFinReserva())
                .append(NUMERO_PERSONAS, reserva.getNumeroPersonas());

    }

    public Reserva getReserva(Document documentoReserva){
        return new Reserva(
                (Huesped) documentoReserva.get(HUESPED),
                (Habitacion) documentoReserva.get(HABITACION),
                Regimen.valueOf(documentoReserva.getString(REGIMEN)),
                LocalDate.parse(documentoReserva.getString(FECHA_INICIO_RESERVA)),
                LocalDate.parse(documentoReserva.getString(FECHA_FIN_RESERVA)),
                documentoReserva.getInteger(NUMERO_PERSONAS)
        );
    }

}