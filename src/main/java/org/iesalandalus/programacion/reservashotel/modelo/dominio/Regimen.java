package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum Regimen {
    SOLO_ALOJAMIENTO("Solo Alojamiento", 0),
    ALOJAMIENTO_DESAYUNO("Alojamiento y Desayuno", 15),
    MEDIA_PENSION("Media Pension", 30),
    PENSION_COMPLETA("Pension Completa", 50);

    private final String cadenaAMostrar;
    private final double incrementoPrecio;

    Regimen(String cadenaAMostrar, int incrementoPrecio){
        this.cadenaAMostrar=cadenaAMostrar;
        this.incrementoPrecio=incrementoPrecio;
    }

    public double getIncrementoPrecio() {
        return incrementoPrecio;
    }

    @Override
    public String toString() {
        return ordinal() + " .- " + cadenaAMostrar;
    }
}
