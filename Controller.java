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
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;


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
    
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField dureeTextField;
    @FXML
    private TextField promotionTextField;
    @FXML
    private TextField entrepriseTextField;
    @FXML
    private TextField sujetTextField;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
        // Initialisation de conn en utilisant la méthode connect de DatabaseConnector
        conn = DatabaseConnector.connect();
    }

    @FXML
    public void handleSaisirUnStageButtonClick(ActionEvent event) {
        loadPage("Page_Ajout_Stage.fxml", "Page Ajout Stage");
    }

    @FXML
    public void handleAideButtonClick(ActionEvent event) {
        loadPage("Page 5 - Aide.fxml", "Page Aide");
    }

    @FXML
    public void handleListeStageButtonClick(ActionEvent event) {
        loadPage("Page 4 - Tableau de stage.fxml", "Page Liste Stage");
        // Charger les données depuis la base de données après avoir chargé la page
    }

    @FXML
    private void handleAfficherStagesButtonClick(ActionEvent event) {
        // Charger les données depuis la base de données et mettre à jour la TableView
        loadDataFromDatabase();
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

    @FXML
private void initialize() {
    // Initialisation des colonnes de TableView (existante)
    initializeTableView();

    // Vérifier si vous êtes sur la page "Page 4 - Liste des stages.fxml"
    if (primaryStage.getTitle().equals("Page 4 - Tableau de stage.fxml")) {
        // Ajout d'un écouteur pour détecter les changements de sélection dans la TableView
        stageTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StageData>() {
            @Override
            public void changed(ObservableValue<? extends StageData> observable, StageData oldValue, StageData newValue) {
                if (newValue != null) {
                    // Lorsqu'un élément est sélectionné, afficher ses informations dans les champs textuels correspondants
                    sujetTextField.setText(newValue.getSujet());
                    entrepriseTextField.setText(newValue.getEntreprise());
                    promotionTextField.setText(newValue.getPromotion());
                    dureeTextField.setText(newValue.getDuree());
                    dateTextField.setText(newValue.getDateDebut());
                } else {
                    // Si aucun élément n'est sélectionné, effacer les champs textuels
                    sujetTextField.clear();
                    entrepriseTextField.clear();
                    promotionTextField.clear();
                    dureeTextField.clear();
                    dateTextField.clear();
                }
            }
        });
    }
}


    private void initializeTableView() {
        if (entrepriseColumn != null && sujetColumn != null && dureeColumn != null &&
            dateDebutColumn != null && promotionColumn != null) {
            System.out.println("Les colonnes de TableView sont initialisées.");

            // Initialise les colonnes de TableView
            entrepriseColumn.setCellValueFactory(cellData -> cellData.getValue().entrepriseProperty());
            sujetColumn.setCellValueFactory(cellData -> cellData.getValue().sujetProperty());
            dureeColumn.setCellValueFactory(cellData -> cellData.getValue().dureeProperty());
            dateDebutColumn.setCellValueFactory(cellData -> cellData.getValue().dateDebutProperty());
            promotionColumn.setCellValueFactory(cellData -> cellData.getValue().promotionProperty());

            // Vérification de l'association de la liste des stages à la TableView
            if (stageTableView != null) {
                System.out.println("La liste des stages est associée à la TableView.");
                // Associer la liste des stages à la TableView
                stageTableView.setItems(stageList);

                // Vérification du chargement des données depuis la base de données
                loadDataFromDatabase();
            } else {
                System.out.println("La TableView n'est pas initialisée.");
            }
        } else {
            System.out.println("Les colonnes de TableView ne sont pas correctement initialisées.");
        }
    }

    private void loadDataFromDatabase() {
        try {
            conn = DatabaseConnector.connect();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connexion à la base de données établie.");

                // Éxécutez votre requête SQL pour récupérer les données de la base de données
                ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Stage");

                // Effacer les anciennes données de la TableView
                stageList.clear();

                // Parcourir le résultat et ajouter les données à la TableView
                while (rs.next()) {
                    // Affichage des informations récupérées
                    System.out.println("Nom de la structure: " + rs.getString("nom_structure"));
                    System.out.println("Sujet du stage: " + rs.getString("sujet_stage"));
                    System.out.println("Durée du stage: " + rs.getString("duree_stage"));
                    System.out.println("Mois de début du stage: " + rs.getString("mois_debut_stage"));
                    System.out.println("Promotion de l'étudiant: " + rs.getString("promotion_etudiant"));

                    // Création de l'objet StageData
                    StageData stage = new StageData(
                            rs.getString("nom_structure"),
                            rs.getString("sujet_stage"),
                            rs.getString("duree_stage"),
                            rs.getString("mois_debut_stage"),
                            rs.getString("promotion_etudiant")
                    );

                    // Ajouter l'objet à la liste
                    stageList.add(stage);
                }

                // Vérifier si la TableView est initialisée
                if (stageTableView != null) {
                    // Mettre à jour les éléments de la TableView
                    stageTableView.setItems(stageList);
                } else {
                    System.out.println("TableView non initialisée.");
                }

                System.out.println("Données chargées depuis la base de données.");
            } else {
                System.out.println("La connexion à la base de données n'est pas établie.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
