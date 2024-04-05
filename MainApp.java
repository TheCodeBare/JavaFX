import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Accueil.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur et lui transmettre la connexion
            Controller controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            // Créer la scène et y mettre le fichier FXML chargé
            Scene scene = new Scene(root);

            // Configurer le stage et afficher
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image("file:1584186491754.png"));
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
