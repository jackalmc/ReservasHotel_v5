package org.iesalandalus.programacion.reservashotel.vista.grafica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.iesalandalus.programacion.reservashotel.vista.grafica.controladores.ControladorInsertar;
import org.iesalandalus.programacion.reservashotel.vista.grafica.controladores.ControladorVentanaPrincipal;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.io.IOException;

public class LanzadorVentanaPrincipal extends Application {

    @Override
    public void start(Stage escenarioPrincipal) {
        try {
            VBox raiz = FXMLLoader.load(LocalizadorRecursos.class.getResource("vistas/ventanaPrincipal.fxml"));
            Scene escena = new Scene(raiz);
            escenarioPrincipal.setTitle("Ejemplos JavaFX");
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.setOnCloseRequest(e->confirmarSalida(escenarioPrincipal,e));
            escenarioPrincipal.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void comenzar(){
        launch();
    }

    private void confirmarSalida(Stage escenarioPrincipal, WindowEvent e) {
        if (Dialogos.mostrarDialogoConfirmacion("DemoDAW", "Estas seguro que quieres salirte de la aplicacion")) {
            escenarioPrincipal.close();
        } else
            e.consume();
    }
}
