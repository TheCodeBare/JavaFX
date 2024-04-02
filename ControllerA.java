import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class ControllerA {
    
    @FXML
    private MenuBar menubarre;
    
    @FXML
    private Button saisirUnStageButton;
    @FXML
    private Button btnListeStages;
    
    @FXML
    public void initialize() {
        // Vous pouvez initialiser des éléments ici si nécessaire
    }

        @FXML
    public void handleButtonClickA(ActionEvent event) {
        if (event.getSource() == btnListeStages) {
            try {
                // Charger la vue "Page 4 - Tableau de stage.fxml"
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Page 4 - Tableau de stage.fxml"));
                Parent root = loader.load();
                
                // Créer une nouvelle scène avec la vue chargée
                Scene scene = new Scene(root);
                
                // Accéder au stage principal à partir de l'événement
                Stage stage = (Stage) btnListeStages.getScene().getWindow();
                
                // Définir la nouvelle scène sur le stage
                stage.setScene(scene);
                stage.setTitle("Page 4 - Tableau de stage");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


