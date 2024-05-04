package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.io.IOException;

public class ControladorVentanaPrincipal {

    @FXML private Button btnAbrirVentanaInsertar;

    @FXML
    void actAbrirVentanaInsertar(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaInsertar.fxml"));
        ControladorInsertar c = fxmlLoader.getController();
        try {
            Parent raiz = fxmlLoader.load();

            Scene escena = new Scene(raiz, 1024, 720);
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
    void salir(ActionEvent event) {
        if (Dialogos.mostrarDialogoConfirmacion("DemoDAW", "Estas seguro que quieres salir de la aplicacion"))
        {
            System.exit(0);
        }
        else
            event.consume();
    }

}

