import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.SQLException;


public class Controller {
    private Connection conn;
    @FXML
    private Pane contentPane;

    private Stage primaryStage;
    
    @FXML
    private TextField promotionField;
    @FXML
    private TextField siegeField;
    @FXML
    private TextField sujetStageField;
    @FXML
    private TextField debutMoisField;
    @FXML
    private TextField dureeField;
    

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
        // Initialisation de conn en utilisant la méthode connect de DatabaseConnector
        conn = DatabaseConnector.connect();
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
    
    @FXML
    public void handleAideButtonClick(ActionEvent event) {
        loadPage("Page 5 - Aide.fxml", "Page Aide");
    }
    
    public void handleQuitterButtonClick(ActionEvent event) {
        // Fermer proprement l'application
        Platform.exit();
    }
    @FXML
    public void ajouterStageFromForm() {
        String promotion = promotionField.getText();
        String siege = siegeField.getText();
        String sujetStage = sujetStageField.getText();
        String debutMois = debutMoisField.getText();
        String duree = dureeField.getText();

        try {
            // Appel à la méthode pour ajouter le stage dans la base de données
            DatabaseConnector.ajouterStage(conn, siege, sujetStage, debutMois, Integer.parseInt(duree), promotion);

            // Vous pouvez ajouter ici un message de confirmation à l'utilisateur

            // Effacer les champs après l'ajout
            promotionField.clear();
            siegeField.clear();
            sujetStageField.clear();
            debutMoisField.clear();
            dureeField.clear();
        } catch (SQLException e) {
            e.printStackTrace(); // Gérez l'exception de manière appropriée
        }
    }

}
    
