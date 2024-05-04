package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ControladorInsertar {

    @FXML    private Button btnHabInsertar;
    @FXML    private Button btnHuInsertar;
    @FXML    private Button btnLimpiar;
    @FXML    private Button btnLimpiar1;
    @FXML    private Button btnResInsertar;
    @FXML    private Button btnResLimpiar;
    @FXML    private Button btnSalir;
    @FXML    private Button btnSalir1;
    @FXML    private Button btnSalir2;

    @FXML    private ChoiceBox<Integer> cbCamDob;
    private ObservableList<Integer> obsCamDob = FXCollections.observableArrayList();

    @FXML    private ChoiceBox<Integer> cbHabBanios;
    private ObservableList<Integer> obsBanios = FXCollections.observableArrayList();

    @FXML    private ChoiceBox<Integer> cbHabCamInd;
    private ObservableList<Integer> obsCamInd = FXCollections.observableArrayList();

    @FXML    private ComboBox<Integer> cbHabPlanta;
    private ObservableList<Integer> obsPlanta = FXCollections.observableArrayList();

    @FXML    private ComboBox<Integer> cbHabPuerta;
    private ObservableList<Integer> obsPuerta = FXCollections.observableArrayList();

    @FXML    private ChoiceBox<TipoHabitacion> cbHabTipo;
    @FXML    private ChoiceBox<Regimen> cbResRegimen;
    private ObservableList<Regimen> obsRegimen = FXCollections.observableArrayList();

    @FXML    private ChoiceBox<TipoHabitacion> cbResTipo;
    private ObservableList<TipoHabitacion> obsTipo = FXCollections.observableArrayList();


    @FXML    private ChoiceBox<Integer> cbResPersonas;
    private ObservableList<Integer> obsPersonas = FXCollections.observableArrayList();

    @FXML    private CheckBox chJacuzzi;
    private ObservableList<Boolean> obsJacuzzi = FXCollections.observableArrayList();

    @FXML    private DatePicker dpHuFechaNac;
    @FXML    private DatePicker dpResFin;
    @FXML    private DatePicker dpResInicio;
    @FXML    private TableColumn<Huesped, String> tcDni;
    @FXML    private TableColumn<Huesped, String> tcNombre;
    @FXML    private TableColumn<Habitacion, String> tcHabId;
    @FXML    private TableColumn<Habitacion, String> tcHabPrecio;
    @FXML    private TableColumn<Habitacion, String> tcHabTipo;
    @FXML    private TableColumn<Reserva, String> tcResDni;
    @FXML    private TableColumn<Reserva, String> tcResFin;
    @FXML    private TableColumn<Reserva, String> tcResId;
    @FXML    private TableColumn<Reserva, String> tcResInicio;
    @FXML    private TextField tfHabFiltrar;
    @FXML    private TextField tfHabPrecio;
    @FXML    private TextField tfHuCorreo;
    @FXML    private TextField tfHuDni;
    @FXML    private TextField tfHuFiltrar;
    @FXML    private TextField tfHuNombre;
    @FXML    private TextField tfHuTelefono;
    @FXML    private TextField tfResDni;
    @FXML    private TextField tfResFiltrar;

    //Habitaciones
    @FXML    private TableView<Habitacion> tvHabitaciones;
    private List<Habitacion> colHabitaciones = new ArrayList<>();
    private ObservableList<Habitacion> obsHabitaciones = FXCollections.observableArrayList();

    //Huespedes
    @FXML    private TableView<Huesped> tvHuespedes;
    private List<Huesped> colHuespedes = new ArrayList<>();
    private ObservableList<Huesped> obsHuespedes = FXCollections.observableArrayList();

    //Reservas
    @FXML    private TableView<Reserva> tvReservas;
    private List<Reserva> colReservas = new ArrayList<>();
    private ObservableList<Reserva> obsReservas = FXCollections.observableArrayList();

    public void cargarDatos(){
        colHuespedes= VistaGrafica.getInstancia().getControlador().getHuespedes();
        obsHuespedes.setAll(colHuespedes);
        colHabitaciones= VistaGrafica.getInstancia().getControlador().getHabitaciones();
        obsHabitaciones.setAll(colHabitaciones);
        colReservas=VistaGrafica.getInstancia().getControlador().getReservas();
        obsReservas.setAll(colReservas);

        //para estos observables se podria crear un metodo para que no sean a mano
        obsPlanta.setAll(1,2,3);
        cbHabPlanta.setItems(obsPlanta);
        obsPuerta.setAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
        cbHabPuerta.setItems(obsPuerta);
        obsTipo.setAll(TipoHabitacion.values());
        cbHabTipo.setItems(obsTipo);
        obsBanios.setAll(1,2,3);
        cbHabBanios.setItems(obsBanios);
        obsCamInd.setAll(1,2,3);
        cbHabCamInd.setItems(obsCamInd);
        obsCamDob.setAll(1,2);
        cbCamDob.setItems(obsCamDob);
        cbResTipo.setItems(obsTipo);
        obsPersonas.setAll(1,2,3,4);
        cbResPersonas.setItems(obsPersonas);
        obsRegimen.setAll(Regimen.values());
        cbResRegimen.setItems(obsRegimen);

    }
    @FXML
    private void initialize(){
        cargarDatos();

        tcDni.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getDni()));
        tcNombre.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getNombre()));

        tcHabId.setCellValueFactory(habitacion-> new SimpleStringProperty(habitacion.getValue().getIdentificador()));
        tcHabPrecio.setCellValueFactory(habitacion-> new SimpleStringProperty(Double.toString(habitacion.getValue().getPrecio())));
        tcHabTipo.setCellValueFactory(habitacion-> new SimpleStringProperty(habitacion.getValue().getClass().getSimpleName()));

        tcResDni.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHuesped().getDni()));
        tcResFin.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getFechaFinReserva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        tcResId.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHabitacion().getIdentificador()));
        tcResInicio.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getFechaInicioReserva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

        tvHuespedes.setItems(obsHuespedes);
        tvHabitaciones.setItems(obsHabitaciones);
        tvReservas.setItems(obsReservas);

    }

    @FXML
    void actBuscarHuesped(ActionEvent event) {

    }

    @FXML
    void actBuscarReserva(ActionEvent event) {

    }

    @FXML
    void actInsertarHabitacion(ActionEvent event) {
        if(Dialogos.mostrarDialogoConfirmacion("Insertar Habitacion", "Seguro que quiere introducir esta habitacion?")){
            try{
                //dividir segun tipo
                Dialogos.mostrarDialogoInformacion("Habitacion Insertada", "La habitacion ha sido insertada con exito!");

                colReservas= VistaGrafica.getInstancia().getControlador().getReservas();
                obsReservas.setAll(colReservas);

            }catch(IllegalArgumentException | NullPointerException  e){
                Dialogos.mostrarDialogoError("Error al insertar", e.getMessage());
            }
        }else{
            Dialogos.mostrarDialogoInformacion("Insertar cancelado", "La introduccion de la habitacion ha sido cancelada");
        }
    }

    @FXML
    void actInsertarHuesped(ActionEvent event) {
        if(Dialogos.mostrarDialogoConfirmacion("Insertar Huesped", "Seguro que quiere introducir este huesped?")){
            try{
                VistaGrafica.getInstancia().getControlador().insertar(
                        new Huesped(tfHuNombre.getText(),tfHuDni.getText(),tfHuCorreo.getText(),tfHuTelefono.getText(),dpHuFechaNac.getValue()));
                Dialogos.mostrarDialogoInformacion("Huesped Insertado", "El huesped ha sido insertado con exito!");

                colHuespedes= VistaGrafica.getInstancia().getControlador().getHuespedes();
                obsHuespedes.setAll(colHuespedes);

            }catch(IllegalArgumentException | NullPointerException | OperationNotSupportedException e){
                Dialogos.mostrarDialogoError("Error al insertar", e.getMessage());
            }
        }else{
            Dialogos.mostrarDialogoInformacion("Insertar cancelado", "La introduccion del Huesped ha sido cancelada");
        }
    }

    @FXML
    void actInsertarReservar(ActionEvent event) {
        cbResTipo.getValue();

        if(Dialogos.mostrarDialogoConfirmacion("Insertar Reserva", "Seguro que quiere introducir esta reserva?")){
            try{
                VistaGrafica.getInstancia().getControlador().insertar(
                        new Reserva(null));
                Dialogos.mostrarDialogoInformacion("Reserva Insertada", "La reserva ha sido insertado con exito!");

                colReservas= VistaGrafica.getInstancia().getControlador().getReservas();
                obsReservas.setAll(colReservas);

            }catch(IllegalArgumentException | NullPointerException | OperationNotSupportedException e){
                Dialogos.mostrarDialogoError("Error al insertar", e.getMessage());
            }
        }else{
            Dialogos.mostrarDialogoInformacion("Insertar cancelado", "La introduccion de la reserva ha sido cancelada");
        }
    }

    @FXML
    void actLimpiar(ActionEvent event) {
        tfHuNombre.clear();
        tfHuCorreo.clear();
        tfHuDni.clear();
        tfHuTelefono.clear();
        tfHabPrecio.clear();
        tfResDni.clear();
        cbResRegimen.getSelectionModel().clearSelection();
        cbResPersonas.getSelectionModel().clearSelection();
        cbResTipo.getSelectionModel().clearSelection();
        cbCamDob.getSelectionModel().clearSelection();
        cbHabCamInd.getSelectionModel().clearSelection();
        cbHabBanios.getSelectionModel().clearSelection();
        cbHabTipo.getSelectionModel().clearSelection();
        cbHabPuerta.getSelectionModel().clearSelection();
        cbHabPlanta.getSelectionModel().clearSelection();
    }

    @FXML
    void actSalir(ActionEvent event) {
        ((Stage)btnSalir.getScene().getWindow()).close();
    }

    @FXML
    void btnBuscarHabitacion(ActionEvent event) {

    }


}









