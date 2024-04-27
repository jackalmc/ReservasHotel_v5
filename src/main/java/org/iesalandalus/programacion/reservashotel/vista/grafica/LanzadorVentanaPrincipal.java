package org.iesalandalus.programacion.reservashotel.vista.grafica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.io.IOException;

public class LanzadorVentanaPrincipal extends Application {

    public void start(Stage escenarioPrincipal) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaPrincipal.fxml"));
        Parent raiz=fxmlLoader.load();
        Scene scene = new Scene(raiz, 1024, 720);
        escenarioPrincipal.setTitle("Administrador Hotel IES Al-Andalus");
        escenarioPrincipal.setScene(scene);
        escenarioPrincipal.setOnCloseRequest(e->confirmarSalida(escenarioPrincipal,e));
        escenarioPrincipal.show();
    }

    public static void comenzar(){
        launch();
    }

    private void confirmarSalida(Stage escenarioPrincipal, WindowEvent e){
        if (Dialogos.mostrarDialogoConfirmacion("Cerrar Aplicacion", "Seguro que quieres salir")) {
            escenarioPrincipal.close();
        }else
            e.consume();
    }
}
