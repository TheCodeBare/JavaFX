import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class Controller {
    
    @FXML
    private MenuBar menubarre;
    @FXML
    private Button saisirUnStageButton;
    @FXML
    private Button btnListeStages;
    @FXML
    private Button btnHelp;
    
    @FXML
    public void initialize() {
        // Vous pouvez initialiser des éléments ici si nécessaire
    }




    
    @FXML
    public void handleSaisirUnStageButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Page_Ajout_Stage.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Page Ajout Stage");
            stage.show();

            // Fermer la fenêtre actuelle (Accueil)
            Stage accueilStage = (Stage) saisirUnStageButton.getScene().getWindow();
            accueilStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void handleListeStageButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Page 4 - Tableau de stage.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Page liste Stage");
            stage.show();

            // Fermer la fenêtre actuelle (Accueil)
            Stage accueilStage = (Stage) btnListeStages.getScene().getWindow();
            accueilStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void handleHelpButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Page 5 - Aide.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Page liste Stage");
            stage.show();

            // Fermer la fenêtre actuelle (Accueil)
            Stage accueilStage = (Stage) btnHelp.getScene().getWindow();
            accueilStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
