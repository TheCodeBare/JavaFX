import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Controller {
    private Connection conn;
    private Stage primaryStage;
private ObservableList<StageData> stageList = FXCollections.observableArrayList();
    @FXML
    private TableView<StageData> stageTableView;
    @FXML
    private TableColumn<StageData, String> entrepriseColumn;
    @FXML
    private TableColumn<StageData, String> sujetColumn;
    @FXML
    private TableColumn<StageData, String> dureeColumn;
    @FXML
    private TableColumn<StageData, String> dateDebutColumn;
    @FXML
    private TableColumn<StageData, String> promotionColumn;

    @FXML
    private Pane contentPane;
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
    public void handleSaisirUnStageButtonClick(ActionEvent event) {
        loadPage("Page_Ajout_Stage.fxml", "Page Ajout Stage");
    }

public void handleListeStageButtonClick(ActionEvent event) {
    loadPage("Page 4 - Tableau de stage.fxml", "Page Liste Stage");
    // Charger les données depuis la base de données après avoir chargé la page
    loadDataFromDatabase();
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
    private void handleAfficherStagesButtonClick(ActionEvent event) {
    // Charger les données depuis la base de données et mettre à jour la TableView
    loadDataFromDatabase();
    }
    
    @FXML
    public void ajouterStageFromForm() {
        String promotion = promotionField.getText();
        String siege = siegeField.getText();
        String sujetStage = sujetStageField.getText();
        String debutMois = debutMoisField.getText();
        String duree = dureeField.getText();
        conn = DatabaseConnector.connect();

        try {
            // Appel à la méthode pour ajouter le stage dans la base de données
            DatabaseConnector.ajouterStage(conn, siege, sujetStage, debutMois, Integer.parseInt(duree), promotion);

            // Vous pouvez ajouter ici un message de confirmation à l'utilisateur

            // Effacer les champs après l'ajout
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace(); // Gérez l'exception de manière appropriée
        }
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

    private void clearFields() {
        promotionField.clear();
        siegeField.clear();
        sujetStageField.clear();
        debutMoisField.clear();
        dureeField.clear();
    }



private void loadListeStagePage() {
    // Charger le fichier FXML de la page Liste des Stages
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Page 4 - Liste des stages.fxml"));
    try {
        Pane root = loader.load();
        contentPane.getChildren().setAll(root);

        // Récupérer le contrôleur de la page Liste des Stages
        Controller controller = loader.getController();

        // Initialiser les colonnes de TableView
        controller.entrepriseColumn.setCellValueFactory(cellData -> cellData.getValue().entrepriseProperty());
        controller.sujetColumn.setCellValueFactory(cellData -> cellData.getValue().sujetProperty());
        controller.dureeColumn.setCellValueFactory(cellData -> cellData.getValue().dureeProperty());
        controller.dateDebutColumn.setCellValueFactory(cellData -> cellData.getValue().dateDebutProperty());
        controller.promotionColumn.setCellValueFactory(cellData -> cellData.getValue().promotionProperty());

        // Charger les données depuis la base de données
        loadDataFromDatabase();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    
    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableView();
        loadDataFromDatabase();
    }

    private void initializeTableView() {
        entrepriseColumn.setCellValueFactory(cellData -> cellData.getValue().entrepriseProperty());
        sujetColumn.setCellValueFactory(cellData -> cellData.getValue().sujetProperty());
        dureeColumn.setCellValueFactory(cellData -> cellData.getValue().dureeProperty());
        dateDebutColumn.setCellValueFactory(cellData -> cellData.getValue().dateDebutProperty());
        promotionColumn.setCellValueFactory(cellData -> cellData.getValue().promotionProperty());

        // Associer la liste des stages à la TableView
        stageTableView.setItems(stageList);
    }

    private void loadDataFromDatabase() {
    try {
        // Vérifier si la connexion est établie
        if (conn != null && !conn.isClosed()) {
            System.out.println("Connexion à la base de données établie.");

            // Éxécutez votre requête SQL pour récupérer les données de la base de données
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Stage");

            // Parcourir le résultat et ajouter les données à la TableView
            while (rs.next()) {
                StageData stage = new StageData(
                    rs.getString("nom_structure"),
                    rs.getString("sujet_stage"),
                    rs.getString("duree_stage"),
                    rs.getString("mois_debut_stage"),
                    rs.getString("promotion_etudiant")
                );
                stageTableView.getItems().add(stage);
            }

            System.out.println("Données chargées depuis la base de données.");
        } else {
            System.out.println("La connexion à la base de données n'est pas établie.");
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Gérer l'exception de manière appropriée
    }
}


}
    
    

