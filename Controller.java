import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.application.Platform;

public class Controller {

    @FXML
    private Pane contentPane;

    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    public void initialize() {
        // Vous pouvez initialiser des éléments ici si nécessaire
    }

    @FXML
    public void handleSaisirUnStageButtonClick(ActionEvent event) {
        loadPage("Page_Ajout_Stage.fxml", "Page Ajout Stage");
    }

    @FXML
    public void handleListeStageButtonClick(ActionEvent event) {
        loadPage("Page 4 - Tableau de stage.fxml", "Page liste Stage");
    }

    private void loadPage(String fxmlFile, String title) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        try {
            Pane root = loader.load();
            contentPane.getChildren().setAll(root);
            primaryStage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void handleQuitterButtonClick(ActionEvent event) {
        // Fermer proprement l'application
        Platform.exit();
    }
    
}