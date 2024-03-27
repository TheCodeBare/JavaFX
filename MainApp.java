import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.embed.swing.*;
import javafx.stage.*;
import javafx.scene.shape.*;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger le fichier FXML
            Parent root = FXMLLoader.load(getClass().getResource("Accueil.fxml"));
           
            // CrÃ©er la scÃ¨ne et y mettre le fichier FXML chargÃ©
            Scene scene = new Scene(root);
           
            // Configurer le stage et afficher
            primaryStage.setScene(scene);
            primaryStage.setTitle("Titre de votre application");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}