package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ControladorHuBuscarReservas {

    @FXML    private TableColumn<Reserva, String> tcCheckIn;
    @FXML    private TableColumn<Reserva, String> tcCheckOut;
    @FXML    private TableColumn<Reserva, String> tcResFechaFin;
    @FXML    private TableColumn<Reserva, String> tcResFechaInic;
    @FXML    private TableColumn<Reserva, String> tcResId;
    @FXML    private TableColumn<Reserva, String> tcResRegimen;
    @FXML    private TableColumn<Reserva, String> tcResTipo;
    @FXML    private TableView<Reserva> tvReservas;
    private List<Reserva> colReservas = new ArrayList<>();
    private ObservableList<Reserva> obsReservas = FXCollections.observableArrayList();
    private Huesped huespedABuscar = null;

    public void prepararHu(Huesped huesped) {
        if (huesped != null){
            huespedABuscar=huesped;
            colReservas = VistaGrafica.getInstancia().getControlador().getReservas(huespedABuscar);
            obsReservas.setAll(colReservas);

            tcResFechaFin.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getFechaFinReserva().format(DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_RESERVA))));
            tcResFechaInic.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getFechaInicioReserva().format(DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_RESERVA))));
            tcResId.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHabitacion().getIdentificador()));
            tcResRegimen.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getRegimen().name()));
            tcResTipo.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHabitacion().getClass().getSimpleName()));
            tcCheckIn.setCellValueFactory(reserva-> new SimpleStringProperty(lambdaCheckIn(reserva)));
            tcCheckOut.setCellValueFactory(reserva-> new SimpleStringProperty(lambdaCheckOut(reserva)));

            tvReservas.setItems(obsReservas);
        }
    }

    private String lambdaCheckIn(TableColumn.CellDataFeatures<Reserva, String> reserva) {
        if (reserva.getValue().getCheckIn() != null)
            return reserva.getValue().getCheckIn().format(DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_HORA_RESERVA));
        else return "---";
    }
    private String lambdaCheckOut(TableColumn.CellDataFeatures<Reserva, String> reserva) {
        if (reserva.getValue().getCheckOut() != null)
            return reserva.getValue().getCheckOut().format(DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_HORA_RESERVA));
        else return "---";
    }
}
