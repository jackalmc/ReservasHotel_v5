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
        import javafx.scene.control.Button;
        import javafx.scene.control.MenuItem;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.TextField;
        import javafx.stage.Modality;
        import javafx.stage.Stage;
        import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
        import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
        import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
        import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
        import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
        import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

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
    @FXML    private TableColumn<?, ?> tcCheckIn;
    @FXML    private TableColumn<?, ?> tcCheckOut;
    @FXML    private TableColumn<?, ?> tcHabBanios;
    @FXML    private TableColumn<?, ?> tcHabCamDob;
    @FXML    private TableColumn<?, ?> tcHabCamInd;
    @FXML    private TableColumn<?, ?> tcHabId;
    @FXML    private TableColumn<?, ?> tcHabJacuzzi;
    @FXML    private TableColumn<?, ?> tcHabPrecio;
    @FXML    private TableColumn<?, ?> tcHabTipo;
    @FXML    private TableColumn<Huesped, String> tcHuCorreo;
    @FXML    private TableColumn<Huesped, String> tcHuDni;
    @FXML    private TableColumn<Huesped, String> tcHuFechaIni;
    @FXML    private TableColumn<Huesped, String> tcHuNombre;
    @FXML    private TableColumn<Huesped, String> tcHuTlf;
    @FXML    private TableColumn<?, ?> tcResDNI;
    @FXML    private TableColumn<?, ?> tcResFechaFin;
    @FXML    private TableColumn<?, ?> tcResFechaInic;
    @FXML    private TableColumn<?, ?> tcResId;
    @FXML    private TableColumn<?, ?> tcResNombre;
    @FXML    private TableColumn<?, ?> tcResRegimen;
    @FXML    private TableColumn<?, ?> tcResTipo;
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
    private ObservableList<Habitacion> obsHabitaciones;
    //Reservas
    @FXML    private TableView<Reserva> tvReservas;
    private FilteredList<Reserva> listaReservas;
    private ObservableList<Reserva> obsReservas;

    @FXML
    void initialize(){
        obsHuespedes.setAll(VistaGrafica.getInstancia().getControlador().getHuespedes());
        listaHuespedes = new FilteredList<>(obsHuespedes, huesped -> true);
        tvHuespedes.setItems(listaHuespedes);

        tcHuDni.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getDni()));
        tcHuCorreo.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getCorreo()));
        tcHuFechaIni.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getFechaNacimiento().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        tcHuTlf.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getTelefono()));
        tcHuNombre.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getNombre()));
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

    }

    @FXML
    void cmBorrarHuesped(ActionEvent event) {

    }

    @FXML
    void cmBorrarReserva(ActionEvent event) {

    }

    @FXML
    void cmInsertarHabitacion(ActionEvent event) {

    }

    @FXML
    void cmInsertarHuesped(ActionEvent event) {

    }

    @FXML
    void cmInsertarReserva(ActionEvent event) {

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

}


