package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ControladorCheckIn {

    @FXML    private Button btnAceptar;
    @FXML    private Button btnCancelar;
    @FXML    private DatePicker dpFecha;
    @FXML    private TextField tfHora;
    private Reserva reservaACheckIn;

    public void prepararVentana(Reserva reserva){
        if (reserva != null)
            reservaACheckIn = reserva;
    }

    @FXML
    void actCancelar(ActionEvent event) {
        ((Stage)btnCancelar.getScene().getWindow()).close();
    }

    @FXML
    void actRealizarCheckIn(ActionEvent event) {
        if (dpFecha.getValue() == null || tfHora.getText().isEmpty())
            Dialogos.mostrarDialogoError("Opciones nulas","Valores no rellenados");
        else {
            VistaGrafica.getInstancia().getControlador().realizarCheckin(reservaACheckIn, LocalDateTime.of(dpFecha.getValue(), LocalTime.parse(tfHora.getText())));
            Dialogos.mostrarDialogoInformacion("CheckIn realizado", "Se ha realizado el CheckIn correctamente.");
        }

        ((Stage)btnCancelar.getScene().getWindow()).close();

    }

}

