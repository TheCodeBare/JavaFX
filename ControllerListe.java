
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.SQLException;
import javafx.stage.Stage;

public class ControllerListe {

    @FXML
    private TableView<StageData> stageTable;
    @FXML
    private TableColumn<StageData, Integer> idColumn;
    @FXML
    private TableColumn<StageData, String> nomStructureColumn;
    @FXML
    private TableColumn<StageData, String> sujetStageColumn;
    @FXML
    private TableColumn<StageData, String> moisDebutStageColumn;
    @FXML
    private TableColumn<StageData, Integer> dureeStageColumn;
    @FXML
    private TableColumn<StageData, String> promotionEtudiantColumn;

    private Connection conn;
    private Stage primaryStage;
    
    public void setPrimaryStage(Stage primarystage) {
        this.primaryStage = primarystage;
        // Initialisation de conn en utilisant la méthode connect de DatabaseConnector
        conn = DatabaseConnector.connect();

        // Afficher les données des stages dans la table lors du chargement de la page
        afficherStagesDansTable();
    }

    @FXML
    public void initialize() {
        // Associer les colonnes de la table aux attributs de StageData
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomStructureColumn.setCellValueFactory(new PropertyValueFactory<>("nomStructure"));
        sujetStageColumn.setCellValueFactory(new PropertyValueFactory<>("sujetStage"));
        moisDebutStageColumn.setCellValueFactory(new PropertyValueFactory<>("moisDebutStage"));
        dureeStageColumn.setCellValueFactory(new PropertyValueFactory<>("dureeStage"));
        promotionEtudiantColumn.setCellValueFactory(new PropertyValueFactory<>("promotionEtudiant"));
    }

    private void afficherStagesDansTable() {
        try {
            // Récupérer les données des stages depuis la base de données
            ObservableList<StageData> stages = StageData.getStagesFromDatabase(conn);

            // Afficher les données dans la table
            stageTable.setItems(stages);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}