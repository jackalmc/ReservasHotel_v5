package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControladorInsertar {

    @FXML    private Button btnInsertarHuesped;
    @FXML    private Button btnInsertarHabitacion;
    @FXML    private Button btnInsertarReserva;
    @FXML    private Button btnLimpiar;
    @FXML    private Button btnLimpiar1;
    @FXML    private Button btnLimpiar2;
    @FXML    private Button btnSalir;
    @FXML    private Button btnSalir1;
    @FXML    private Button btnSalir2;
    @FXML    private DatePicker dpFechaNac;
    @FXML    private DatePicker dpFechaNac2;
    @FXML    private TextField tfBuscarHuesped;
    @FXML    private TextField tfBuscarHabitacion;
    @FXML    private TextField tfBuscarReserva;
    @FXML    private TextField tfCorreo;
    @FXML    private TextField tfCorreo2;
    @FXML    private TextField tfDni;
    @FXML    private TextField tfDni2;
    @FXML    private TextField tfNombre;
    @FXML    private TextField tfNombre2;
    @FXML    private TextField tfTelefono;
    @FXML    private TextField tfTelefono2;
    @FXML    private TableView<Huesped> tvHuespedes;
    @FXML    private TableView<Habitacion> tvHabitaciones;
    @FXML    private TableView<Reserva> tvReservas;
    @FXML    private TableColumn<Huesped, String> tcDni;
    @FXML    private TableColumn<Huesped, String> tcNombre;
    private List<Huesped> colHuespedes = new ArrayList<>();
    private ObservableList<Huesped> obsHuesped = FXCollections.observableArrayList();


    public void cargarDatos(){
        colHuespedes.add(new Huesped("asdf","11111111H","asdf@fdsa.com","950262626", LocalDate.of(2024,10,10)));
        obsHuesped.setAll(colHuespedes);
    }
    @FXML
    private void initialize(){
        cargarDatos();
        tcDni.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getDni()));
        tcNombre.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getNombre()));
        tvHuespedes.setItems(obsHuesped);

    }

    @FXML
    void actBuscarHuesped(ActionEvent event) {

    }

    @FXML
    void actInsertarHuesped(ActionEvent event) {

    }

    @FXML
    void actLimpiar(ActionEvent event) {

    }

    @FXML
    void actSalir(ActionEvent event) {

    }

}
