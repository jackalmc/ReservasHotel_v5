package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.util.Objects;

public abstract class Habitacion {
    public final static double MIN_PRECIO_HABITACION=40.0;
    public final static double MAX_PRECIO_HABITACION=150.0;
    public final static int MIN_NUMERO_PUERTA=0;
    public final static int MAX_NUMERO_PUERTA=14;
    public final static int MIN_NUMERO_PLANTA=1;
    public final static int MAX_NUMERO_PLANTA=3;
    protected String identificador;
    protected int planta;
    protected int puerta;
    protected double precio;

    public String getIdentificador() {
        return identificador;
    }

    protected void setIdentificador(){
        identificador= "" +planta +puerta;
    }
    protected void setIdentificador(String identificador) {
        if (identificador == null)
            throw new NullPointerException("Identificador nulo");
        if (identificador.isBlank())
            throw new IllegalArgumentException("Identificador en blanco");

        this.identificador = identificador;
    }

    public int getPlanta() {
        return planta;
    }

    protected void setPlanta(int planta) {
        if (planta < MIN_NUMERO_PLANTA || planta > MAX_NUMERO_PLANTA)
            throw new IllegalArgumentException("ERROR: No se puede establecer como planta de una habitacion un valor menor que 1 ni mayor que 3.");

        this.planta = planta;
    }

    public int getPuerta() {
        return puerta;
    }

    protected void setPuerta(int puerta) {
        if (puerta < MIN_NUMERO_PUERTA || puerta > MAX_NUMERO_PUERTA)
            throw new IllegalArgumentException("ERROR: No se puede establecer como puerta de una habitacion un valor menor que 0 ni mayor que 14.");

        this.puerta = puerta;
    }

    public double getPrecio() {
        return precio;
    }

    protected void setPrecio(double precio) {
        if (precio < MIN_PRECIO_HABITACION || precio > MAX_PRECIO_HABITACION)
            throw new IllegalArgumentException("ERROR: No se puede establecer como precio de una habitacion un valor menor que 40.0 ni mayor que 150.0.");

        this.precio = precio;
    }

    public Habitacion(int planta, int puerta, double precio){
        setPlanta(planta);
        setPuerta(puerta);
        setPrecio(precio);
        setIdentificador();

    }

    public Habitacion(Habitacion habitacion){
        if (habitacion==null)
            throw new NullPointerException("ERROR: No es posible copiar una habitacion nula.");
        setPlanta(habitacion.getPlanta());
        setPuerta(habitacion.getPuerta());
        setPrecio(habitacion.getPrecio());
        setIdentificador(habitacion.getIdentificador());
    }

    public abstract int getNumeroMaximoPersonas();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Habitacion that = (Habitacion) o;
        return Objects.equals(identificador, that.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return "identificador="+identificador+" ("+planta+"-"+puerta+"), precio habitacion="+precio;
    }
}
