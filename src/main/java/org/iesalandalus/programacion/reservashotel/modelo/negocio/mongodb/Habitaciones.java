package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import java.util.ArrayList;
import java.util.List;

public class Habitaciones implements IHabitaciones {
    private final String COLECCION="habitaciones";
    private List<Habitacion> coleccionHabitaciones;

    public Habitaciones(){
        comenzar();
    }
    @Override
    public List<Habitacion> get(){

        List<Habitacion> copiaOrdenada = new ArrayList<>();
        FindIterable<Document> documents = MongoDB.getBD().getCollection(COLECCION).find().sort(Sorts.ascending(MongoDB.IDENTIFICADOR));

        for (Document document:documents)
            copiaOrdenada.add(MongoDB.getHabitacion(document));

        return copiaOrdenada;
    }
    public List<Habitacion> get(TipoHabitacion tipoHabitacion){
        List<Habitacion> copiaOrdenada = new ArrayList<>();
        FindIterable<Document> documents = MongoDB.getBD().getCollection(COLECCION).find().sort(Sorts.ascending(MongoDB.IDENTIFICADOR));

        for (Document document : documents) {
            switch (tipoHabitacion) {
                case SIMPLE:
                    if (MongoDB.getHabitacion(document) instanceof Simple)
                        copiaOrdenada.add(MongoDB.getHabitacion(document));
                    break;
                case DOBLE:
                    if (MongoDB.getHabitacion(document) instanceof Doble)
                        copiaOrdenada.add(MongoDB.getHabitacion(document));
                    break;
                case TRIPLE:
                    if (MongoDB.getHabitacion(document) instanceof Triple)
                        copiaOrdenada.add(MongoDB.getHabitacion(document));
                    break;
                case SUITE:
                    if (MongoDB.getHabitacion(document) instanceof Suite)
                        copiaOrdenada.add(MongoDB.getHabitacion(document));
                    break;
            }
        }
        return copiaOrdenada;
    }
    @Override
    public int getTamano(){
        return (int) MongoDB.getBD().getCollection(COLECCION).countDocuments();
    }
    @Override
    public void insertar(Habitacion habitacion){
        if (habitacion == null)
            throw new NullPointerException("No se puede introducir una habitacion nula");
        if (buscar(habitacion) != null)
            throw new IllegalArgumentException("Ya existe esa habitacion en la BD");

        Document documentoAIntroducir = MongoDB.getDocumento(habitacion);
        MongoDB.getBD().getCollection(COLECCION).insertOne(documentoAIntroducir); //funciona bien aunque diga que no.
    }
    @Override
    public Habitacion buscar(Habitacion habitacion){
        if (habitacion == null)
            throw new NullPointerException("No se puede buscar una habitacion nula");

        Document encontrado = MongoDB.getBD().getCollection(COLECCION).find(Filters.eq(MongoDB.IDENTIFICADOR,habitacion.getIdentificador())).first();

        if (encontrado==null)
            return null;
        else return MongoDB.getHabitacion(encontrado);

    }
    @Override
    public void borrar(Habitacion habitacion){
        if (habitacion == null)
            throw new NullPointerException("No se puede borrar un huesped nulo");
        if (buscar(habitacion) == null)
            throw new IllegalArgumentException("No existe ese huesped en la BD");

        MongoDB.getBD().getCollection(COLECCION).deleteOne(Filters.eq(MongoDB.IDENTIFICADOR,habitacion.getIdentificador()));

    }
    @Override
    public void comenzar(){
        coleccionHabitaciones = new ArrayList<>();
        FindIterable<Document> documents = MongoDB.getBD().getCollection(COLECCION).find();

        for (Document document : documents)
            coleccionHabitaciones.add(MongoDB.getHabitacion(document));
    }
    @Override
    public void terminar(){
        MongoDB.cerrarConexion();
        coleccionHabitaciones=null;
    }
}
