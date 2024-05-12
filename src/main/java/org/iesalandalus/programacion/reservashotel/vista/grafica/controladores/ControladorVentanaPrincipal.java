package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

        import javafx.beans.property.SimpleStringProperty;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.collections.transformation.FilteredList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.stage.Modality;
        import javafx.stage.Stage;
        import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
        import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
        import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
        import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

        import javax.naming.OperationNotSupportedException;
        import java.io.IOException;
        import java.time.format.DateTimeFormatter;

public class ControladorVentanaPrincipal {

    @FXML    private Button btnHabBuscar;
    @FXML    private Button btnHuBuscar;
    @FXML    private Button btnResBuscar;
    @FXML    private Button btnSimpleBorrar;
    @FXML    private Button btnSimpleInsertar;
    @FXML    private MenuItem menuBorrar;
    @FXML    private MenuItem menuBorrarReserva;
    @FXML    private MenuItem menuHabBorrar;
    @FXML    private MenuItem menuHabInsertar;
    @FXML    private MenuItem menuHuBorrar;
    @FXML    private MenuItem menuHuInsertar;
    @FXML    private MenuItem menuInsertar;
    @FXML    private MenuItem menuInsertarReserva;
    @FXML    private MenuItem menuSalir;
    @FXML    private MenuItem menuSobre;
    @FXML    private TableColumn<Reserva, String> tcCheckIn;
    @FXML    private TableColumn<Reserva, String> tcCheckOut;
    @FXML    private TableColumn<Habitacion, String> tcHabBanios;
    @FXML    private TableColumn<Habitacion, String> tcHabCamDob;
    @FXML    private TableColumn<Habitacion, String> tcHabCamInd;
    @FXML    private TableColumn<Habitacion, String> tcHabId;
    @FXML    private TableColumn<Habitacion, String> tcHabJacuzzi;
    @FXML    private TableColumn<Habitacion, String> tcHabPrecio;
    @FXML    private TableColumn<Habitacion, String> tcHabTipo;
    @FXML    private TableColumn<Huesped, String> tcHuCorreo;
    @FXML    private TableColumn<Huesped, String> tcHuDni;
    @FXML    private TableColumn<Huesped, String> tcHuFechaIni;
    @FXML    private TableColumn<Huesped, String> tcHuNombre;
    @FXML    private TableColumn<Huesped, String> tcHuTlf;
    @FXML    private TableColumn<Reserva, String> tcResDNI;
    @FXML    private TableColumn<Reserva, String> tcResFechaFin;
    @FXML    private TableColumn<Reserva, String> tcResFechaInic;
    @FXML    private TableColumn<Reserva, String> tcResId;
    @FXML    private TableColumn<Reserva, String> tcResNombre;
    @FXML    private TableColumn<Reserva, String> tcResRegimen;
    @FXML    private TableColumn<Reserva, String> tcResTipo;
    @FXML    private TextField tfHabBuscar;
    @FXML    private TextField tfHabFiltrado;
    @FXML    private TextField tfHuBuscar;
    @FXML    private TextField tfHuFiltrado;
    @FXML    private TextField tfResBuscar;
    @FXML    private TextField tfResFiltrado;

    // Huespedes
    @FXML    private TableView<Huesped> tvHuespedes;
    private FilteredList<Huesped> listaHuespedes;
    private ObservableList<Huesped> obsHuespedes= FXCollections.observableArrayList();

    //Habitaciones
    @FXML    private TableView<Habitacion> tvHabitaciones;
    private FilteredList<Habitacion> listaHabitaciones;
    private ObservableList<Habitacion> obsHabitaciones= FXCollections.observableArrayList();

    //Reservas
    @FXML    private TableView<Reserva> tvReservas;
    private FilteredList<Reserva> listaReservas;
    private ObservableList<Reserva> obsReservas= FXCollections.observableArrayList();

    @FXML
    void initialize(){
        //Huespedes
        obsHuespedes.setAll(VistaGrafica.getInstancia().getControlador().getHuespedes());
        listaHuespedes = new FilteredList<>(obsHuespedes, huesped -> true);
        tvHuespedes.setItems(listaHuespedes);

        tcHuDni.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getDni()));
        tcHuCorreo.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getCorreo()));
        tcHuFechaIni.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getFechaNacimiento().format(DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_RESERVA))));
        tcHuTlf.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getTelefono()));
        tcHuNombre.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getNombre()));

        //Habitaciones
        obsHabitaciones.setAll(VistaGrafica.getInstancia().getControlador().getHabitaciones());
        listaHabitaciones = new FilteredList<>(obsHabitaciones, habitacion -> true);
        tvHabitaciones.setItems(listaHabitaciones);

        tcHabBanios.setCellValueFactory(habitacion-> new SimpleStringProperty(lambdaHabBanios(habitacion)));
        tcHabCamDob.setCellValueFactory(habitacion-> new SimpleStringProperty(lambdaHabCamDob(habitacion)));
        tcHabCamInd.setCellValueFactory(habitacion-> new SimpleStringProperty(lambdaHabCamInd(habitacion)));
        tcHabId.setCellValueFactory(habitacion-> new SimpleStringProperty(habitacion.getValue().getIdentificador()));
        tcHabPrecio.setCellValueFactory(habitacion -> new SimpleStringProperty(Double.toString(habitacion.getValue().getPrecio())));
        tcHabJacuzzi.setCellValueFactory(habitacion-> new SimpleStringProperty(lambdaHabJacuzzi(habitacion)));
        tcHabTipo.setCellValueFactory(habitacion-> new SimpleStringProperty(habitacion.getValue().getClass().getSimpleName()));

        //Reservas
        obsReservas.setAll(VistaGrafica.getInstancia().getControlador().getReservas());
        listaReservas = new FilteredList<>(obsReservas, reserva -> true);
        tvReservas.setItems(listaReservas);

        tcResDNI.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHuesped().getDni()));
        tcResFechaFin.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getFechaFinReserva().format(DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_RESERVA))));
        tcResFechaInic.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getFechaInicioReserva().format(DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_RESERVA))));
        tcResId.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHabitacion().getIdentificador()));
        tcResNombre.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHuesped().getNombre()));
        tcResRegimen.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getRegimen().name()));
        tcResTipo.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHabitacion().getClass().getSimpleName()));
        tcCheckIn.setCellValueFactory(reserva-> new SimpleStringProperty(lambdaCheckIn(reserva)));
        tcCheckOut.setCellValueFactory(reserva-> new SimpleStringProperty(lambdaCheckOut(reserva)));
    }

    private String lambdaCheckOut(TableColumn.CellDataFeatures<Reserva, String> reserva) {
        if (reserva.getValue().getCheckOut() != null)
            return reserva.getValue().getCheckOut().format(DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_HORA_RESERVA));
        else return "---";
    }

    private String lambdaCheckIn(TableColumn.CellDataFeatures<Reserva, String> reserva) {
        if (reserva.getValue().getCheckIn() != null)
            return reserva.getValue().getCheckIn().format(DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_HORA_RESERVA));
        else return "---";
    }

    private String lambdaHabJacuzzi(TableColumn.CellDataFeatures<Habitacion, String> habitacion) {
        if (habitacion.getValue() instanceof Suite)
            if (((Suite) habitacion.getValue()).isTieneJacuzzi())
                return "Si";
            else return "No";
        else return "No";
    }

    private String lambdaHabBanios(TableColumn.CellDataFeatures<Habitacion, String> habitacion) {
        if (habitacion.getValue() instanceof Triple){
            return Integer.toString(((Triple) habitacion.getValue()).getNumBanos());
        } else if (habitacion.getValue() instanceof Suite){
            return Integer.toString(((Suite) habitacion.getValue()).getNumBanos());
        } else{
            return "n/a";
        }
    }

    private String lambdaHabCamInd(TableColumn.CellDataFeatures<Habitacion, String> habitacion) {
        if (habitacion.getValue() instanceof Doble)
            return Integer.toString(((Doble) habitacion.getValue()).getNumCamasIndividuales());
        else if (habitacion.getValue() instanceof Triple)
            return Integer.toString(((Triple) habitacion.getValue()).getNumCamasIndividuales());
        else if (habitacion.getValue() instanceof Simple)
            return "1";
        else
            return "0";
    }

    private String lambdaHabCamDob(TableColumn.CellDataFeatures<Habitacion, String> habitacion) {
        if (habitacion.getValue() instanceof Doble)
            return Integer.toString(((Doble) habitacion.getValue()).getNumCamasDobles());
        else if (habitacion.getValue() instanceof Triple)
            return Integer.toString(((Triple) habitacion.getValue()).getNumCamasDobles());
        else if (habitacion.getValue() instanceof Simple)
            return "0";
        else
            return "1";
    }




    @FXML
    void actAbrirBorrar(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaBorrar.fxml"));
        try {
            Parent raiz = fxmlLoader.load();

            Scene escena = new Scene(raiz);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Formulario para borrar registros");
            escenario.setResizable(false);
            escenario.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void actAbrirInsertar(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaInsertar.fxml"));
        try {
            Parent raiz = fxmlLoader.load();

            Scene escena = new Scene(raiz);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Formulario para insertar datos");
            escenario.setResizable(false);
            escenario.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void actBuscar(ActionEvent event) {

    }

    @FXML
    void actBuscarHabitaciones(ActionEvent event) {

    }

    @FXML
    void actBuscarReservas(ActionEvent event) {

    }

    @FXML
    void actSobre(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaSobre.fxml"));
        try {
            Parent raiz = fxmlLoader.load();
            Scene escena = new Scene(raiz);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.NONE);
            escenario.setTitle("Sobre la app");
            escenario.setResizable(false);
            escenario.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void cmBorrarHabitacion(ActionEvent event) {
        Habitacion habitacionABorrar = tvHabitaciones.getSelectionModel().getSelectedItem();
        if (habitacionABorrar == null)
            Dialogos.mostrarDialogoError("Borrar habitacion vacia","No se puede borrar una habitacion inexistente.");
        else{
            try{
                if (Dialogos.mostrarDialogoConfirmacion("Seguro que quiere eliminar", "Eliminar: " + habitacionABorrar)){
                    VistaGrafica.getInstancia().getControlador().borrar(habitacionABorrar);
                    Dialogos.mostrarDialogoInformacion("Habitacion eliminada", "Habitacion eliminada correctamente.");

                    obsHabitaciones.setAll(VistaGrafica.getInstancia().getControlador().getHabitaciones());
                    tvHabitaciones.setItems(listaHabitaciones);

                }else{
                    event.consume();
                }
            } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
                Dialogos.mostrarDialogoError("Error al borrar", e.getMessage());
            }
        }
    }

    @FXML
    void cmBorrarHuesped(ActionEvent event) {
        Huesped huespedABorrar = tvHuespedes.getSelectionModel().getSelectedItem();
        if (huespedABorrar == null)
            Dialogos.mostrarDialogoError("Borrar huesped vacio","No se puede borrar un huesped inexistente.");
        else{
            try{
                if (Dialogos.mostrarDialogoConfirmacion("Seguro que quiere eliminar", "Eliminar: " + huespedABorrar)){
                    VistaGrafica.getInstancia().getControlador().borrar(huespedABorrar);
                    Dialogos.mostrarDialogoInformacion("Huesped eliminada", "Huesped eliminado correctamente.");

                    obsHuespedes.setAll(VistaGrafica.getInstancia().getControlador().getHuespedes());
                    tvHuespedes.setItems(listaHuespedes);

                }else{
                    event.consume();
                }
            } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
                Dialogos.mostrarDialogoError("Error al borrar", e.getMessage());
            }
        }
    }

    @FXML
    void cmBorrarReserva(ActionEvent event) {
        Reserva reservaABorrar = tvReservas.getSelectionModel().getSelectedItem();
        if (reservaABorrar == null)
            Dialogos.mostrarDialogoError("Borrar reserva vacia","No se puede borrar una reserva inexistente.");
        else{
            try{
                if (Dialogos.mostrarDialogoConfirmacion("Seguro que quiere eliminar", "Eliminar: " + reservaABorrar)){
                    VistaGrafica.getInstancia().getControlador().borrar(reservaABorrar);
                    Dialogos.mostrarDialogoInformacion("Reserva eliminada", "Reserva eliminada correctamente.");

                    obsReservas.setAll(VistaGrafica.getInstancia().getControlador().getReservas());
                    tvReservas.setItems(listaReservas);

                }else{
                    event.consume();
                }
            } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
                Dialogos.mostrarDialogoError("Error al borrar", e.getMessage());
            }
        }
    }

    @FXML
    void cmInsertarHabitacion(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaInsertarHabitacion.fxml"));
        try {
            Parent raiz = fxmlLoader.load();

            Scene escena = new Scene(raiz);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Insertar Habitacion");
            escenario.setResizable(false);
            escenario.showAndWait();

            obsHabitaciones.setAll(VistaGrafica.getInstancia().getControlador().getHabitaciones());
            tvHabitaciones.setItems(listaHabitaciones);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void cmInsertarHuesped(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaInsertarHuesped.fxml"));
        try {
            Parent raiz = fxmlLoader.load();

            Scene escena = new Scene(raiz);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Insertar Huesped");
            escenario.setResizable(false);
            escenario.showAndWait();

            obsHuespedes.setAll(VistaGrafica.getInstancia().getControlador().getHuespedes());
            tvHuespedes.setItems(listaHuespedes);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void cmInsertarReserva(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaInsertarReserva.fxml"));
        try {
            Parent raiz = fxmlLoader.load();

            Scene escena = new Scene(raiz);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Insertar Reserva");
            escenario.setResizable(false);
            escenario.showAndWait();

            obsReservas.setAll(VistaGrafica.getInstancia().getControlador().getReservas());
            tvReservas.setItems(listaReservas);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void salir(ActionEvent event) {
        if (Dialogos.mostrarDialogoConfirmacion("Salir de la Aplicacion", "Estas seguro que quieres salir"))
        {
            System.exit(0);
        }
        else
            event.consume();
    }

    @FXML
    void cmReservaCheckIn(ActionEvent event) {
        Reserva reserva = tvReservas.getSelectionModel().getSelectedItem();
        if (reserva == null)
            Dialogos.mostrarDialogoError("Reserva no existe", "No se puede hacer check in de una reserva inexistente");
        else{
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaCheckIn.fxml"));
            try {
                Parent raiz = fxmlLoader.load();
                ControladorCheckIn c = fxmlLoader.getController();
                c.prepararVentana(tvReservas.getSelectionModel().getSelectedItem());

                Scene escena = new Scene(raiz);
                Stage escenario = new Stage();
                escenario.setScene(escena);
                escenario.initModality(Modality.APPLICATION_MODAL);
                escenario.setTitle("Realizar Check-in");
                escenario.setResizable(false);
                escenario.showAndWait();

                obsReservas.setAll(VistaGrafica.getInstancia().getControlador().getReservas());
                tvReservas.setItems(listaReservas);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void cmReservaCheckOut(ActionEvent event) {
        Reserva reserva = tvReservas.getSelectionModel().getSelectedItem();
        if (reserva == null)
            Dialogos.mostrarDialogoError("Reserva no existe", "No se puede hacer check in de una reserva inexistente");
        else{
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaCheckOut.fxml"));
            try {
                Parent raiz = fxmlLoader.load();
                ControladorCheckOut c = fxmlLoader.getController();
                c.prepararVentana(tvReservas.getSelectionModel().getSelectedItem());

                Scene escena = new Scene(raiz);
                Stage escenario = new Stage();
                escenario.setScene(escena);
                escenario.initModality(Modality.APPLICATION_MODAL);
                escenario.setTitle("Realizar Check-in");
                escenario.setResizable(false);
                escenario.showAndWait();

                obsReservas.setAll(VistaGrafica.getInstancia().getControlador().getReservas());
                tvReservas.setItems(listaReservas);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}


