package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Huesped {

    private final String ER_TELEFONO = "[0-9]{9}";
    private final String ER_CORREO = ".+[@][A-Z a-z]+[.][A-Z a-z]+";
    private final String ER_DNI = "([0-9]{8})([A-Z a-z])";
    public static final String FORMATO_FECHA = "dd/MM/yyyy";
    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;

    private String formateaNombre(String nombre){
        if (nombre==null)
            throw new NullPointerException("ERROR: El nombre de un huesped no puede ser nulo.");
        if (nombre.isBlank())
            throw new IllegalArgumentException("ERROR: El nombre de un huesped no puede estar vacio.");

        String nombreFormateado = "";
        String nombreTemporal;

        //Cojo el nombre, lo limpio de espacios y lo divido en minuscula
        nombre = nombre.trim().toLowerCase();
        String [] nombreDividido = nombre.split("\\s+");

        //Rota por el array y va concatenando con la primera en mayuscula
        for (int i=0; i<nombreDividido.length; i++){
            nombreTemporal = nombreDividido[i];
            nombreFormateado += nombreTemporal.substring(0,1).toUpperCase() + nombreTemporal.substring(1);
            if (i!=nombreDividido.length-1) //Aniade espacio si no es el final
                nombreFormateado += " ";

        }

        return nombreFormateado;
    }

    private Boolean comprobarLetraDni(String dni){
        if (dni==null)
            throw new NullPointerException("ERROR: El dni de un huesped no puede ser nulo.");
        if (dni.isBlank())
            throw new IllegalArgumentException("ERROR: El dni del huesped no tiene un formato valido.");

        Pattern p = Pattern.compile(ER_DNI);
        Matcher m;
        m = p.matcher(dni);

        if (!m.matches())
            throw new IllegalArgumentException("ERROR: El dni del huesped no tiene un formato valido.");

        int numerosDni = Integer.parseInt(m.group(1));
        int restoDivisionDni = numerosDni%23;
        String letraDni = m.group(2).toUpperCase();
        String [] conversionLetra = {"T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E"};

        //Al principio lo tenia en un switch, pero asi reduzco mucho codigo
        return letraDni.equals(conversionLetra[restoDivisionDni]);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = formateaNombre(nombre);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) { //Validado null, blank, patron. No tratado
        if (telefono==null)
            throw new NullPointerException("ERROR: El telefono de un huesped no puede ser nulo.");


        telefono = telefono.trim();
        Pattern p = Pattern.compile(ER_TELEFONO);
        Matcher m;
        m = p.matcher(telefono);

        if (!m.matches())
            throw new IllegalArgumentException("ERROR: El telefono del huesped no tiene un formato valido.");

        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) { //Validado null, blank, patron. No tratado
        if (correo==null)
            throw new NullPointerException("ERROR: El correo de un huesped no puede ser nulo.");
        if (correo.isBlank())
            throw new IllegalArgumentException("ERROR: El correo del huesped no tiene un formato valido.");

        correo = correo.trim();
        Pattern p = Pattern.compile(ER_CORREO);
        Matcher m;
        m = p.matcher(correo);

        if (!m.matches())
            throw new IllegalArgumentException("ERROR: El correo del huesped no tiene un formato valido.");

        this.correo = correo;
    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni) {
        if (comprobarLetraDni(dni)) //null, blank y patron validado en este metodo
            this.dni = dni;
        if (!comprobarLetraDni(dni))
            throw new IllegalArgumentException("ERROR: La letra del dni del huesped no es correcta.");
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    private void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento==null)
            throw new NullPointerException("ERROR: La fecha de nacimiento de un huesped no puede ser nula.");

        //DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(FORMATO_FECHA); //GUARDADO PARA OTRAS FECHAS

        //Validacion en caso de fecha de nacimiento rara
        if (fechaNacimiento.isBefore(LocalDate.of(1900,1,1)) || fechaNacimiento.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("La fecha de nacimiento se sale del rango permitido (Anio 1900-Fecha de hoy).");


        this.fechaNacimiento = fechaNacimiento;
    }

    private String getIniciales(){ //ya esta validado en el setNombre
        String iniciales = "";
        String [] nombre = getNombre().split(" ");
        for (int i=0; i<nombre.length; i++)
            iniciales += nombre[i].charAt(0);

        return iniciales;
    }

    public Huesped(String nombre, String dni, String correo, String telefono, LocalDate fechaNacimiento){
        setNombre(nombre);
        setDni(dni);
        setCorreo(correo);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);
    }

    public Huesped(Huesped huesped){ //No se si hace falta volver a pasarlo por las validaciones, ya que para crear un objeto debe pasarlos ya pero por si acaso. Quizas sea muy ineficiente??
        if (huesped==null)
            throw new NullPointerException("ERROR: No es posible copiar un huesped nulo.");

        setNombre(huesped.getNombre());
        setDni(huesped.getDni());
        setCorreo(huesped.getCorreo());
        setTelefono(huesped.getTelefono());
        setFechaNacimiento(huesped.getFechaNacimiento());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Huesped huesped = (Huesped) o;
        return Objects.equals(dni, huesped.dni);
    }


    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return "nombre="+nombre+ " ("+ getIniciales() +"), DNI="+dni+", correo="+correo+", telefono="+telefono+", fecha nacimiento="+fechaNacimiento.format(DateTimeFormatter.ofPattern(FORMATO_FECHA));
    }
}
