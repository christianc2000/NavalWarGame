/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceCliente;

/**
 *
 * @author Christian
 */
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LaunchGame extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Barcos - Lanzamiento");

        // Crear los botones
        Button btnNewGame = new Button("Nuevo Juego");
        Button btnLoadGame = new Button("Cargar Juego");
        Button btnExit = new Button("Salir");

        // Configurar los controladores de los botones
        btnNewGame.setOnAction(event -> {
            // Lógica para iniciar un nuevo juego
            System.out.println("Iniciar nuevo juego");
        });

        btnLoadGame.setOnAction(event -> {
            // Lógica para cargar un juego guardado
            System.out.println("Cargar juego guardado");
        });

        btnExit.setOnAction(event -> {
            // Lógica para salir de la aplicación
            primaryStage.close();
        });

        // Crear el contenedor principal y agregar los botones
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(btnNewGame, btnLoadGame, btnExit);

        // Crear la escena y mostrarla en la ventana principal
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

