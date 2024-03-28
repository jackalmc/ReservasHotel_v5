package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Huespedes implements IHuespedes {

    private List<Huesped> coleccionHuespedes;

    public Huespedes(){

        coleccionHuespedes = new ArrayList<>();
    }

    @Override
    public List<Huesped> get(){
        return copiaProfundaHuespedes();
    }

    private List<Huesped> copiaProfundaHuespedes(){
        if (coleccionHuespedes==null)
            throw new NullPointerException("La colección no ha sido creada aún");

        List<Huesped> copia = new ArrayList<>();

        Iterator <Huesped> huespedIterator = coleccionHuespedes.iterator();
        while (huespedIterator.hasNext())
            copia.add(new Huesped(huespedIterator.next()));

        return copia;
    }

    @Override
    public int getTamano() {
        return coleccionHuespedes.size();
    }

    @Override
    public void insertar(Huesped huesped) throws OperationNotSupportedException{
        if (huesped == null)
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        if (coleccionHuespedes.contains(huesped))
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");

        coleccionHuespedes.add(new Huesped(huesped));
    }

    @Override
    public Huesped buscar(Huesped huesped){
        if (huesped == null)
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");

        if (coleccionHuespedes.contains(huesped))
            return coleccionHuespedes.get(coleccionHuespedes.indexOf(huesped));
        else
            return null;

    }

    @Override
    public void borrar(Huesped huesped)throws OperationNotSupportedException{
        if (huesped == null)
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        if (!coleccionHuespedes.contains(huesped))
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");;

        coleccionHuespedes.remove(huesped);
    }

}
