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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ControladorInsertarHabitacion {

    @FXML    private Button btnHabInsertar;
    @FXML    private Button btnLimpiar1;
    @FXML    private Button btnSalir1;
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
    @FXML    private CheckBox chJacuzzi;
    @FXML    private TableColumn<Habitacion, String> tcHabId;
    @FXML    private TableColumn<Habitacion, String> tcHabPrecio;
    @FXML    private TableColumn<Habitacion, String> tcHabTipo;
    @FXML    private TextField tfHabPrecio;
    @FXML    private TableView<Habitacion> tvHabitaciones;
    private List<Habitacion> colHabitaciones = new ArrayList<>();
    private ObservableList<Habitacion> obsHabitaciones = FXCollections.observableArrayList();
    private ObservableList<TipoHabitacion> obsTipo = FXCollections.observableArrayList();

    public void cargarDatos(){
        colHabitaciones= VistaGrafica.getInstancia().getControlador().getHabitaciones();
        obsHabitaciones.setAll(colHabitaciones);

        obsPlanta.setAll(1,2,3);
        cbHabPlanta.setItems(obsPlanta);
        obsPuerta.setAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
        cbHabPuerta.setItems(obsPuerta);
        obsTipo.setAll(TipoHabitacion.values());
        cbHabTipo.setItems(obsTipo);
        obsBanios.setAll(1,2,3);
        cbHabBanios.setItems(obsBanios);
        obsCamInd.setAll(0,1,2,3);
        cbHabCamInd.setItems(obsCamInd);
        obsCamDob.setAll(0,1);
        cbCamDob.setItems(obsCamDob);

        actLimpiar(null);

    }
    @FXML
    private void initialize(){
        cargarDatos();

        tcHabId.setCellValueFactory(habitacion-> new SimpleStringProperty(habitacion.getValue().getIdentificador()));
        tcHabPrecio.setCellValueFactory(habitacion-> new SimpleStringProperty(Double.toString(habitacion.getValue().getPrecio())));
        tcHabTipo.setCellValueFactory(habitacion-> new SimpleStringProperty(habitacion.getValue().getClass().getSimpleName()));
        tvHabitaciones.setItems(obsHabitaciones);

    }
    @FXML
    void actInsertarHabitacion(ActionEvent event) {
        Habitacion habitacionAInsertar=null;

        if(Dialogos.mostrarDialogoConfirmacion("Insertar Habitacion", "Seguro que quiere introducir esta habitacion?")){
            try{
                switch (cbHabTipo.getSelectionModel().getSelectedItem()){
                    case SIMPLE -> habitacionAInsertar = new Simple(cbHabPlanta.getSelectionModel().getSelectedItem(),
                            cbHabPuerta.getSelectionModel().getSelectedItem(),
                            Double.parseDouble(tfHabPrecio.getText()));
                    case DOBLE -> habitacionAInsertar = new Doble(cbHabPlanta.getSelectionModel().getSelectedItem(),
                            cbHabPuerta.getSelectionModel().getSelectedItem(),
                            Double.parseDouble(tfHabPrecio.getText()),
                            cbHabCamInd.getSelectionModel().getSelectedItem(),
                            cbCamDob.getSelectionModel().getSelectedItem());
                    case TRIPLE -> habitacionAInsertar = new Triple(cbHabPlanta.getSelectionModel().getSelectedItem(),
                            cbHabPuerta.getSelectionModel().getSelectedItem(),
                            Double.parseDouble(tfHabPrecio.getText()),
                            cbHabBanios.getSelectionModel().getSelectedItem(),
                            cbHabCamInd.getSelectionModel().getSelectedItem(),
                            cbCamDob.getSelectionModel().getSelectedItem());
                    case SUITE -> habitacionAInsertar = new Suite(cbHabPlanta.getSelectionModel().getSelectedItem(),
                            cbHabPuerta.getSelectionModel().getSelectedItem(),
                            Double.parseDouble(tfHabPrecio.getText()),
                            cbHabBanios.getSelectionModel().getSelectedItem(),
                            chJacuzzi.isSelected());
                }
                VistaGrafica.getInstancia().getControlador().insertar(habitacionAInsertar);
                Dialogos.mostrarDialogoInformacion("Habitacion Insertada", "La habitacion ha sido insertada con exito!");

                ((Stage)btnSalir1.getScene().getWindow()).close();


            }catch(IllegalArgumentException | NullPointerException | OperationNotSupportedException e){
                Dialogos.mostrarDialogoError("Error al insertar", e.getMessage());
            }
        }else{
            Dialogos.mostrarDialogoInformacion("Insertar cancelado", "La introduccion de la habitacion ha sido cancelada");
            ((Stage)btnSalir1.getScene().getWindow()).close();
        }
    }

    @FXML
    void actLimpiar(ActionEvent event) {
        tfHabPrecio.clear();
        cbCamDob.getSelectionModel().selectFirst();
        cbHabCamInd.getSelectionModel().selectFirst();
        cbHabBanios.getSelectionModel().selectFirst();
        cbHabTipo.getSelectionModel().selectFirst();
        cbHabPuerta.getSelectionModel().selectFirst();
        cbHabPlanta.getSelectionModel().selectFirst();
    }

    @FXML
    void actSalir(ActionEvent event) {
        ((Stage)btnSalir1.getScene().getWindow()).close();
    }

}
