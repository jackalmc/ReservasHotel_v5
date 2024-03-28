package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Huespedes implements IHuespedes {
    private final String COLECCION="huespedes";
    private List<Huesped> coleccionHuespedes;

    public Huespedes(){

    }
    @Override
    public List<Huesped> get(){
        /* Esto creo que está bien, pero lo cambio para usar el método específico de MongoDB, aunque esto estaría
        bien para ahorrar una llamada al cluster, no se actualizaría en caso de haber varias personas añadiendo datos.

        List<Huesped> copiaOrdenada = new ArrayList<>();

        for (Huesped huesped : coleccionHuespedes)
            copiaOrdenada.add(new Huesped(huesped));

        copiaOrdenada.sort(Comparator.comparing(Huesped::getDni));

        return copiaOrdenada; */

        List<Huesped> copiaOrdenada = new ArrayList<>();
        FindIterable<Document> documents = MongoDB.getBD().getCollection(COLECCION).find().sort(Sorts.ascending(MongoDB.DNI));

        for (Document document:documents)
            copiaOrdenada.add(MongoDB.getHuesped(document));

        return copiaOrdenada;
    }
    @Override
    public int getTamano(){
        return (int) MongoDB.getBD().getCollection(COLECCION).countDocuments();
    }
    @Override
    public void insertar(Huesped huesped){
        if (huesped == null)
            throw new NullPointerException("No se puede introducir un huésped nulo");
        if (MongoDB.getBD().getCollection(COLECCION).find(Filters.eq(MongoDB.DNI, huesped.getDni())).first() != null)
            throw new IllegalArgumentException("Ya existe ese huésped en la BD");

        Document documentoAIntroducir = MongoDB.getDocumento(huesped);
        MongoDB.getBD().getCollection(COLECCION).insertOne(documentoAIntroducir);
    }
    @Override
    public Huesped buscar(Huesped huesped){
        if (huesped == null)
            throw new NullPointerException("No se puede buscar un huésped nulo");

        return MongoDB.getHuesped(MongoDB.getBD().getCollection(COLECCION).find(Filters.eq(MongoDB.DNI, huesped.getDni())).first());
    }
    @Override
    public void borrar(Huesped huesped){
        if (huesped == null)
            throw new NullPointerException("No se puede borrar un huésped nulo");
        if (MongoDB.getBD().getCollection(COLECCION).find(Filters.eq(MongoDB.DNI, huesped.getDni())).first() == null)
            throw new IllegalArgumentException("No existe ese huésped en la BD");

        MongoDB.getBD().getCollection(COLECCION).deleteOne(MongoDB.getDocumento(huesped));
    }
    @Override
    public void comenzar(){
        FindIterable<Document> documents = MongoDB.getBD().getCollection(COLECCION).find();

        for (Document document : documents)
            coleccionHuespedes.add(MongoDB.getHuesped(document));
    }
    @Override
    public void terminar(){
        MongoDB.cerrarConexion();
    }
}
