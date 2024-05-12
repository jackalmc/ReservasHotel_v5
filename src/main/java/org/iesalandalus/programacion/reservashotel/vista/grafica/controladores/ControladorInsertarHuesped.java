package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;

import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class ControladorInsertarHuesped {

    @FXML    private Button btnHuInsertar;
    @FXML    private Button btnLimpiar;
    @FXML    private Button btnSalir;
    @FXML    private DatePicker dpHuFechaNac;
    @FXML    private TableColumn<Huesped, String> tcDni;
    @FXML    private TableColumn<Huesped, String> tcNombre;
    @FXML    private TextField tfHuCorreo;
    @FXML    private TextField tfHuDni;
    @FXML    private TextField tfHuNombre;
    @FXML    private TextField tfHuTelefono;
    @FXML    private TableView<Huesped> tvHuespedes;
    private List<Huesped> colHuespedes = new ArrayList<>();
    private ObservableList<Huesped> obsHuespedes = FXCollections.observableArrayList();

    public void cargarDatos(){
        colHuespedes= VistaGrafica.getInstancia().getControlador().getHuespedes();
        obsHuespedes.setAll(colHuespedes);

        actLimpiar(null);

    }
    @FXML
    private void initialize(){
        cargarDatos();

        tcDni.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getDni()));
        tcNombre.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getNombre()));

        tvHuespedes.setItems(obsHuespedes);

    }
    @FXML
    void actInsertarHuesped(ActionEvent event) {
        if(Dialogos.mostrarDialogoConfirmacion("Insertar Huesped", "Seguro que quiere introducir este huesped?")){
            try{
                VistaGrafica.getInstancia().getControlador().insertar(
                        new Huesped(tfHuNombre.getText(),tfHuDni.getText(),tfHuCorreo.getText(),tfHuTelefono.getText(),dpHuFechaNac.getValue()));
                Dialogos.mostrarDialogoInformacion("Huesped Insertado", "El huesped ha sido insertado con exito!");

                ((Stage)btnSalir.getScene().getWindow()).close();

            }catch(IllegalArgumentException | NullPointerException | OperationNotSupportedException e){
                Dialogos.mostrarDialogoError("Error al insertar", e.getMessage());
            }
        }else{
            Dialogos.mostrarDialogoInformacion("Insertar cancelado", "La introduccion del Huesped ha sido cancelada");
            ((Stage)btnSalir.getScene().getWindow()).close();

        }
    }

    @FXML
    void actLimpiar(ActionEvent event) {
        tfHuNombre.clear();
        tfHuCorreo.clear();
        tfHuDni.clear();
        tfHuTelefono.clear();
    }

    @FXML
    void actSalir(ActionEvent event) {
        ((Stage)btnSalir.getScene().getWindow()).close();
    }

}