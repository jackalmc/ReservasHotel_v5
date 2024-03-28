package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Doble extends Habitacion{
    private final int NUM_MAXIMO_PERSONAS=2;
    static final int MIN_NUM_CAMAS_INDIVIDUALES=0;
    static final int MAX_NUM_CAMAS_INDIVIDUALES=2;
    static final int MIN_NUM_CAMAS_DOBLES=0;
    static final int MAX_NUM_CAMAS_DOBLES=1;
    private int numCamasIndividuales;
    private int numCamasDobles;

    public Doble (int planta, int puerta, double precio, int numCamasIndividuales, int numCamasDobles){
        super(planta, puerta, precio);
        setNumCamasIndividuales(numCamasIndividuales);
        setNumCamasDobles(numCamasDobles);
        validaNumCamas();
    }
    public Doble (Doble habitacionDoble){
        super(habitacionDoble);
        setNumCamasIndividuales(habitacionDoble.getNumCamasIndividuales());
        setNumCamasDobles(habitacionDoble.getNumCamasDobles());
        validaNumCamas();
    }

    public int getNumCamasIndividuales() {
        return numCamasIndividuales;
    }

    public void setNumCamasIndividuales(int numCamasIndividuales) {
        if (numCamasIndividuales < MIN_NUM_CAMAS_INDIVIDUALES || numCamasIndividuales > MAX_NUM_CAMAS_INDIVIDUALES)
            throw new IllegalArgumentException("ERROR: El número de camas individuales de una habitación doble no puede ser inferior a 0 ni mayor que 2");

        this.numCamasIndividuales = numCamasIndividuales;
    }

    public int getNumCamasDobles() {
        return numCamasDobles;
    }

    public void setNumCamasDobles(int numCamasDobles) {
        if (numCamasDobles < MIN_NUM_CAMAS_DOBLES || numCamasDobles > MAX_NUM_CAMAS_DOBLES)
            throw new IllegalArgumentException("ERROR: El número de camas dobles de una habitación doble no puede ser inferior a 0 ni mayor que 1");
        this.numCamasDobles = numCamasDobles;
    }

    private void validaNumCamas(){
        if (numCamasDobles == 0 && numCamasIndividuales != 2)
            throw new IllegalArgumentException("ERROR: La distribución de camas en una habitación doble tiene que ser 2 camas individuales y 0 doble o 0 camas individuales y 1 doble");
        if (numCamasDobles == 1 && numCamasIndividuales != 0)
            throw new IllegalArgumentException("ERROR: La distribución de camas en una habitación doble tiene que ser 2 camas individuales y 0 doble o 0 camas individuales y 1 doble");
    }
    @Override
    public int getNumeroMaximoPersonas(){
        return NUM_MAXIMO_PERSONAS;
    }

    @Override
    public String toString() {
        return String.format(super.toString()+", habitación doble, capacidad=%d personas, camas individuales=%d, camas dobles=%d",
                getNumeroMaximoPersonas(),getNumCamasIndividuales(), getNumCamasDobles());
    }
}
