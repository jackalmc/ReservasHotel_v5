package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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

public class ControladorInsertarReserva {

    @FXML    private Button btnResInsertar;
    @FXML    private Button btnResLimpiar;
    @FXML    private Button btnSalir2;
    @FXML    private ChoiceBox<Integer> cbResPlanta;
    private ObservableList<Integer> obsResPlanta = FXCollections.observableArrayList();

    @FXML    private ChoiceBox<Integer> cbResPuerta;
    private ObservableList<Integer> obsResPuerta = FXCollections.observableArrayList();


    @FXML    private ChoiceBox<Regimen> cbResRegimen;
    private ObservableList<Regimen> obsRegimen = FXCollections.observableArrayList();

    @FXML    private ChoiceBox<Integer> cbResPersonas;
    private ObservableList<Integer> obsPersonas = FXCollections.observableArrayList();

    @FXML    private DatePicker dpResFin;
    @FXML    private DatePicker dpResInicio;
    @FXML    private TableColumn<Reserva, String> tcResDni;
    @FXML    private TableColumn<Reserva, String> tcResFin;
    @FXML    private TableColumn<Reserva, String> tcResId;
    @FXML    private TableColumn<Reserva, String> tcResInicio;
    @FXML    private TextField tfResDni;
    @FXML    private TextField tfResFiltrar;
    @FXML    private TableView<Reserva> tvReservas;
    private List<Reserva> colReservas = new ArrayList<>();
    private ObservableList<Reserva> obsReservas = FXCollections.observableArrayList();

    public void cargarDatos() {
        colReservas= VistaGrafica.getInstancia().getControlador().getReservas();
        obsReservas.setAll(colReservas);
        obsPersonas.setAll(1,2,3,4);
        cbResPersonas.setItems(obsPersonas);
        obsRegimen.setAll(Regimen.values());
        cbResRegimen.setItems(obsRegimen);
        obsResPlanta.setAll(1,2,3);
        cbResPlanta.setItems(obsResPlanta);
        obsResPuerta.setAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
        cbResPuerta.setItems(obsResPuerta);

        actLimpiar(null);
    }
    @FXML
    private void initialize(){
        cargarDatos();

        tcResDni.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHuesped().getDni()));
        tcResFin.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getFechaFinReserva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        tcResId.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHabitacion().getIdentificador()));
        tcResInicio.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getFechaInicioReserva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

        tvReservas.setItems(obsReservas);

    }

    @FXML
    void actInsertarReservar(ActionEvent event) {

        if(Dialogos.mostrarDialogoConfirmacion("Insertar Reserva", "Seguro que quiere introducir esta reserva?")){
            try{
                VistaGrafica.getInstancia().getControlador().insertar(
                        new Reserva(VistaGrafica.getInstancia().getControlador().buscar(new Huesped("dummy",tfResDni.getText().trim().toUpperCase(),"asdf@fdsa.com","951161616", LocalDate.of(2000,10,10))),
                                VistaGrafica.getInstancia().getControlador().buscar(new Simple(cbResPlanta.getSelectionModel().getSelectedItem(),cbResPuerta.getSelectionModel().getSelectedItem(),80)),
                                cbResRegimen.getSelectionModel().getSelectedItem(),
                                dpResInicio.getValue(),
                                dpResFin.getValue(),
                                cbResPersonas.getSelectionModel().getSelectedItem()

                        ));
                Dialogos.mostrarDialogoInformacion("Reserva Insertada", "La reserva ha sido insertado con exito!");

                ((Stage)btnSalir2.getScene().getWindow()).close();


            }catch(IllegalArgumentException | NullPointerException | OperationNotSupportedException e){
                Dialogos.mostrarDialogoError("Error al insertar", e.getMessage());
            }
        }else{
            Dialogos.mostrarDialogoInformacion("Insertar cancelado", "La introduccion de la reserva ha sido cancelada");
            ((Stage)btnSalir2.getScene().getWindow()).close();
        }
    }

    @FXML
    void actLimpiar(ActionEvent event) {
        tfResDni.clear();
        cbResRegimen.getSelectionModel().selectFirst();
        cbResPersonas.getSelectionModel().selectFirst();
        cbResPlanta.getSelectionModel().selectFirst();
        cbResPuerta.getSelectionModel().selectFirst();
    }

    @FXML
    void actSalir(ActionEvent event) {
        ((Stage)btnSalir2.getScene().getWindow()).close();
    }

}
