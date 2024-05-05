package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import java.util.ArrayList;
import java.util.List;

public class Huespedes implements IHuespedes {
    private final String COLECCION="huespedes";
    private List<Huesped> coleccionHuespedes; //aunque pongo coleccion, realmente no lo uso mucho, a favor de tirar mas de metodos especificos de MongoDB.

    public Huespedes(){
        comenzar();
    }
    @Override
    public List<Huesped> get(){
        /* Esto creo que esta bien, pero lo cambio para usar el metodo especifico de MongoDB, aunque esto estaria
        bien para ahorrar una llamada al cluster, no se actualizaria en caso de haber varias personas aniadiendo datos.

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
            throw new NullPointerException("No se puede introducir un huesped nulo");

        if (buscar(huesped) != null)
            throw new IllegalArgumentException("Ya existe ese huesped en la BD");

        MongoDB.getBD().getCollection(COLECCION).insertOne(MongoDB.getDocumento(huesped));
    }
    @Override
    public Huesped buscar(Huesped huesped){
        if (huesped == null)
            throw new NullPointerException("No se puede buscar un huesped nulo");

        Document encontrado = MongoDB.getBD().getCollection(COLECCION).find(Filters.eq(MongoDB.DNI, huesped.getDni())).first();

        if (encontrado==null)
            return null;

        else return MongoDB.getHuesped(encontrado);
    }
    @Override
    public void borrar(Huesped huesped){
        if (huesped == null)
            throw new NullPointerException("No se puede borrar un huesped nulo");
        if (buscar(huesped) == null)
            throw new IllegalArgumentException("No existe ese huesped en la BD");

        MongoDB.getBD().getCollection(COLECCION).deleteOne(Filters.eq(MongoDB.DNI, huesped.getDni()));
    }
    @Override
    public void comenzar(){
        coleccionHuespedes=new ArrayList<>();
        FindIterable<Document> documents = MongoDB.getBD().getCollection(COLECCION).find();

        for (Document document : documents)
            coleccionHuespedes.add(MongoDB.getHuesped(document));
    }
    @Override
    public void terminar(){
        MongoDB.cerrarConexion();
    }
}
