package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoDatabase;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.bson.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MongoDB {

    public static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMATO_DIA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final String SERVIDOR = "kd0h77u.mongodb.net";
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
    private static MongoClient conexion = null; // static?

    private MongoDB(){

    }

    public static MongoDatabase getBD(){
        if (conexion == null)
            throw new NullPointerException("Conexion es nula");

        return conexion.getDatabase(BD);
    }

    public static void establecerConexion(){
        String connectionString = "mongodb+srv://"+ USUARIO+ ":" + CONTRASENA + "@"+ BD +"." + SERVIDOR +"/?retryWrites=true&w=majority";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        // Create a new client and connect to the server
       conexion = MongoClients.create(settings);

       if (conexion != null)
           System.out.println("Conexion establecida!");

    }

    public static void cerrarConexion(){
        conexion.close();
    }

    public static Document getDocumento(Huesped huesped){
        if (huesped == null)
            throw new NullPointerException("No se puede hacer un documento de un nulo (huesped)");

        return new Document()
                .append(NOMBRE, huesped.getNombre())
                .append(DNI, huesped.getDni())
                .append(TELEFONO, huesped.getTelefono())
                .append(CORREO, huesped.getCorreo())
                .append(FECHA_NACIMIENTO, huesped.getFechaNacimiento().format(FORMATO_DIA));
    }

    public static Huesped getHuesped(Document documentoHuesped){
        if (documentoHuesped == null)
            throw new NullPointerException("No se puede conseguir un huesped, de un documento nulo.");

        return new Huesped(
                documentoHuesped.getString(NOMBRE),
                documentoHuesped.getString(DNI),
                documentoHuesped.getString(CORREO),
                documentoHuesped.getString(TELEFONO),
                LocalDate.parse(documentoHuesped.getString(FECHA_NACIMIENTO), FORMATO_DIA)
        );
    }

    public static Document getDocumento(Habitacion habitacion){
        if (habitacion == null)
            throw new NullPointerException("No se puede hacer un documento de un nulo (habitacion)");

        if (habitacion instanceof Simple){
            return new Document()
                    .append(IDENTIFICADOR, habitacion.getIdentificador())
                    .append(TIPO, TIPO_SIMPLE)
                    .append(PRECIO, habitacion.getPrecio())
                    .append(NUMERO_PERSONAS, habitacion.getNumeroMaximoPersonas())
                    .append(PLANTA, habitacion.getPlanta())
                    .append(PUERTA, habitacion.getPuerta());
        }else if (habitacion instanceof Doble){
            return new Document()
                    .append(IDENTIFICADOR, habitacion.getIdentificador())
                    .append(TIPO, TIPO_DOBLE)
                    .append(PRECIO, habitacion.getPrecio())
                    .append(NUMERO_PERSONAS, habitacion.getNumeroMaximoPersonas())
                    .append(PLANTA, habitacion.getPlanta())
                    .append(PUERTA, habitacion.getPuerta())
                    .append(CAMAS_INDIVIDUALES, ((Doble) habitacion).getNumCamasIndividuales())
                    .append(CAMAS_DOBLES, ((Doble) habitacion).getNumCamasDobles());
        }else if (habitacion instanceof Triple){
            return new Document()
                    .append(IDENTIFICADOR, habitacion.getIdentificador())
                    .append(TIPO, TIPO_TRIPLE)
                    .append(PRECIO, habitacion.getPrecio())
                    .append(NUMERO_PERSONAS, habitacion.getNumeroMaximoPersonas())
                    .append(PLANTA, habitacion.getPlanta())
                    .append(PUERTA, habitacion.getPuerta())
                    .append(BANOS, ((Triple) habitacion).getNumBanos())
                    .append(CAMAS_INDIVIDUALES, ((Triple) habitacion).getNumCamasIndividuales())
                    .append(CAMAS_DOBLES, ((Triple) habitacion).getNumCamasDobles());

        }else if(habitacion instanceof Suite){
            return new Document()
                    .append(IDENTIFICADOR, habitacion.getIdentificador())
                    .append(TIPO, TIPO_SUITE)
                    .append(PRECIO, habitacion.getPrecio())
                    .append(NUMERO_PERSONAS, habitacion.getNumeroMaximoPersonas())
                    .append(PLANTA, habitacion.getPlanta())
                    .append(PUERTA, habitacion.getPuerta())
                    .append(BANOS, ((Suite) habitacion).getNumBanos())
                    .append(JACUZZI, ((Suite) habitacion).isTieneJacuzzi());
        }else return null;
    }

    public static Habitacion getHabitacion(Document documentoHabitacion){
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

    public static Document getDocumento(Reserva reserva){
        if (reserva == null)
            throw new NullPointerException("No se puede hacer un documento de un nulo (reserva)");

        return new Document()
                .append(HUESPED, getDocumento(reserva.getHuesped()))
                .append(HABITACION, getDocumento(reserva.getHabitacion()))
                .append(REGIMEN, reserva.getRegimen().name())
                .append(FECHA_INICIO_RESERVA, reserva.getFechaInicioReserva().format(FORMATO_DIA))
                .append(FECHA_FIN_RESERVA, reserva.getFechaFinReserva().format(FORMATO_DIA))
                .append(NUMERO_PERSONAS, reserva.getNumeroPersonas());

    }

    public static Reserva getReserva(Document documentoReserva){
        if (documentoReserva == null)
            throw new NullPointerException("No se puede sacar una reserva de un documento nulo");

        Reserva reservaADevolver = new Reserva(
                getHuesped((Document)documentoReserva.get(HUESPED)),
                getHabitacion((Document)documentoReserva.get(HABITACION)),
                Regimen.valueOf(documentoReserva.getString(REGIMEN)),
                LocalDate.parse(documentoReserva.getString(FECHA_INICIO_RESERVA), FORMATO_DIA),
                LocalDate.parse(documentoReserva.getString(FECHA_FIN_RESERVA), FORMATO_DIA),
                documentoReserva.getInteger(NUMERO_PERSONAS));

        if (documentoReserva.getString(CHECKIN) != null)
            reservaADevolver.setCheckIn(LocalDateTime.parse(documentoReserva.getString(CHECKIN), FORMATO_DIA_HORA));
        if (documentoReserva.getString(CHECKOUT) != null)
            reservaADevolver.setCheckOut(LocalDateTime.parse(documentoReserva.getString(CHECKOUT), FORMATO_DIA_HORA));


        return reservaADevolver;
    }

}
