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
           
            // Créer la scène et y mettre le fichier FXML chargé
            Scene scene = new Scene(root);
           
            // Configurer le stage et afficher
            primaryStage.setScene(scene);
            primaryStage.setTitle("Accueil");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
