package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControladorInsertar {

    @FXML    private Button btnInsertarHuesped;
    @FXML    private Button btnInsertarHuesped1;
    @FXML    private Button btnInsertarHuesped2;
    @FXML    private Button btnLimpiar;
    @FXML    private Button btnLimpiar1;
    @FXML    private Button btnLimpiar2;
    @FXML    private Button btnSalir;
    @FXML    private Button btnSalir1;
    @FXML    private Button btnSalir2;
    @FXML    private ChoiceBox<?> cbBanios;
    @FXML    private ChoiceBox<?> cbCamDob;
    @FXML    private ChoiceBox<?> cbCamInd;
    @FXML    private ComboBox<?> cbPlanta;
    @FXML    private ComboBox<?> cbPuerta;
    @FXML    private ChoiceBox<?> cbTipoHab;
    @FXML    private CheckBox chJacuzzi;
    @FXML    private DatePicker dpFechaNac;
    @FXML    private DatePicker dpFechaNac2;
    @FXML    private TableColumn<Huesped, String> tcDni;
    @FXML    private TableColumn<Huesped, String> tcNombre;
    @FXML    private TextField tfBuscarHab;
    @FXML    private TextField tfBuscarHuesped;
    @FXML    private TextField tfBuscarHuesped2;
    @FXML    private TextField tfCorreo;
    @FXML    private TextField tfCorreo2;
    @FXML    private TextField tfDni;
    @FXML    private TextField tfDni2;
    @FXML    private TextField tfNombre;
    @FXML    private TextField tfNombre2;
    @FXML    private TextField tfPrecio;
    @FXML    private TextField tfTelefono;
    @FXML    private TextField tfTelefono2;
    @FXML    private TableView<Huesped> tvHuespedes;
    @FXML    private TableView<Habitacion> tvHuespedes1;
    @FXML    private TableView<Reserva> tvHuespedes2;
    private List<Huesped> colHuespedes = new ArrayList<>();
    private ObservableList<Huesped> obsHuesped = FXCollections.observableArrayList();


    public void cargarDatos(){
        //colHuespedes.add(new Huesped("asdf","11111111H","asdf@fdsa.com","950262626", LocalDate.of(2024,10,10)));
        colHuespedes=VistaGrafica.getInstancia().getControlador().getHuespedes();
        //colHuespedes.addAll(VistaGrafica.getInstancia().getControlador().getHuespedes());
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



