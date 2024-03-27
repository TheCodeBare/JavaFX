import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class Controller {
    
    @FXML
    private Button myButton;
    
    @FXML
    public void handleButtonClick(ActionEvent event) {
        System.out.println("Bouton cliqué !");
        // Ajoutez ici le code pour gérer l'événement du bouton
    }
}