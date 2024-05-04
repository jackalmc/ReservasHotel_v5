package org.iesalandalus.programacion.reservashotel.vista.grafica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

public class LanzadorVentanaPrincipal extends Application {

    @Override
    public void start(Stage escenarioPrincipal) {
        try {
            VBox raiz = FXMLLoader.load(LocalizadorRecursos.class.getResource("vistas/ventanaPrincipal.fxml"));
            Scene escena = new Scene(raiz);
            escenarioPrincipal.setTitle("Reservas Hotel");
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
        if (Dialogos.mostrarDialogoConfirmacion("Salir de la aplicacion", "Estas seguro que quieres salir")) {
            escenarioPrincipal.close();
        } else
            e.consume();
    }
}
