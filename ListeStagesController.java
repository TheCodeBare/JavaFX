import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListeStagesController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Stage> stageTable;

    @FXML
    private TableColumn<Stage, String> nomColumn;

    @FXML
    private TableColumn<Stage, String> lieuColumn;

    // Autres colonnes et méthodes de votre contrôleur

    @FXML
    public void initialize() {
        // Initialisez votre tableau et chargez les données
        
        // Configurer les colonnes (supposons que vous avez des colonnes nom et lieu)
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        lieuColumn.setCellValueFactory(new PropertyValueFactory<>("lieu"));

        // Obtenir les données à partir de votre source de données (par exemple, une liste d'objets Stage)
        ObservableList<Stage> stageList = FXCollections.observableArrayList(...);

        // Créer un objet FilteredList pour filtrer les données en fonction de la recherche
        FilteredList<Stage> filteredList = new FilteredList<>(stageList, p -> true);

        // Lier la FilteredList à votre TableView
        stageTable.setItems(filteredList);

        // Ajouter un écouteur pour détecter les changements dans le texte saisi dans la barre de recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(stage -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Afficher tous les éléments si la barre de recherche est vide
                }

                // Vérifiez si le nom ou le lieu du stage contient le texte saisi
                String lowerCaseFilter = newValue.toLowerCase();
                if (stage.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Correspondance trouvée dans le nom
                } else if (stage.getLieu().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Correspondance trouvée dans le lieu
                }
                return false; // Aucune correspondance trouvée
            });
        });
    }
}