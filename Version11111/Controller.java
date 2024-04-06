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
import java.sql.PreparedStatement;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;

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
        System.out.println("le stage est set");
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
        // Vérifier si tous les champs requis sont remplis
        if (promotion.isEmpty() || siege.isEmpty() || sujetStage.isEmpty() || debutMois.isEmpty() || duree.isEmpty()) {
            // Afficher un message d'erreur à l'utilisateur
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return; // Sortir de la méthode si un champ est vide
        }

        // Vérifier si la durée du stage contient uniquement des chiffres
        if (!duree.matches("\\d+")) {
            // Afficher un message d'erreur à l'utilisateur
            showAlert("Erreur", "La durée du stage doit contenir uniquement des chiffres.");
            return; // Sortir de la méthode si la durée n'est pas valide
        }

        // Vérifier si le mois de début de stage contient uniquement des lettres
        if (!debutMois.matches("[a-zA-Z]+")) {
            // Afficher un message d'erreur à l'utilisateur
            showAlert("Erreur", "Veuillez écrire le mois de début de stage en toutes lettres.");
            return; // Sortir de la méthode si le mois n'est pas valide
        }

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
private void initialize2() {
        // Ajout d'un écouteur pour détecter les changements de sélection dans la TableView
        System.out.println("interface2 appelée");
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



    @FXML
    private void initialize() {
    initializeTableView();
    if (stageTableView != null) {
        initialize2();
    }
}

@FXML
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
                    int id = rs.getInt("id");
                    
                    System.out.println("Nom de la structure: " + rs.getString("nom_structure"));
                    System.out.println("Sujet du stage: " + rs.getString("sujet_stage"));
                    System.out.println("Durée du stage: " + rs.getString("duree_stage"));
                    System.out.println("Mois de début du stage: " + rs.getString("mois_debut_stage"));
                    System.out.println("Promotion de l'étudiant: " + rs.getString("promotion_etudiant"));

                    // Création de l'objet StageData
                    StageData stage = new StageData(
                            id,
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
    
    
    @FXML
private void handleValiderButtonClick(ActionEvent event) {
    // Récupérer les données modifiées à partir des champs textuels
    String nouvelleEntreprise = entrepriseTextField.getText();
    String nouveauSujet = sujetTextField.getText();
    String nouvelleDuree = dureeTextField.getText();
    String nouvelleDateDebut = dateTextField.getText();
    String nouvellePromotion = promotionTextField.getText();

    // Récupérer le stage sélectionné dans la TableView
    StageData stageSelectionne = stageTableView.getSelectionModel().getSelectedItem();
    
    if (stageSelectionne != null) {
        // Mettre à jour les données du stage sélectionné
        stageSelectionne.setEntreprise(nouvelleEntreprise);
        stageSelectionne.setSujet(nouveauSujet);
        stageSelectionne.setDuree(nouvelleDuree);
        stageSelectionne.setDateDebut(nouvelleDateDebut);
        stageSelectionne.setPromotion(nouvellePromotion);

        // Mettre à jour la base de données
        updateStageDansBaseDeDonnees(stageSelectionne);
    } else {
        // Afficher un message d'erreur si aucun stage n'est sélectionné
        System.out.println("Aucun stage sélectionné.");
    }
}

private void updateStageDansBaseDeDonnees(StageData stage) {
    // Vérifier si tous les champs sont remplis
    if (stage.getEntreprise().isEmpty() || stage.getSujet().isEmpty() || stage.getDuree().isEmpty() || stage.getDateDebut().isEmpty() || stage.getPromotion().isEmpty()) {
        // Afficher un message d'erreur à l'utilisateur
        showAlert("Erreur", "Tous les champs doivent être remplis.");
        // Recharger les données depuis la base de données pour restaurer les valeurs dans la TableView
        loadDataFromDatabase();
        return; // Sortir de la méthode si un champ est vide
    }

    try {
        conn = DatabaseConnector.connect();
        if (conn != null && !conn.isClosed()) {
            System.out.println("Connexion à la base de données établie.");

            // Éxécutez votre requête SQL pour mettre à jour les données du stage dans la base de données
            String sql = "UPDATE Stage SET nom_structure=?, sujet_stage=?, duree_stage=?, mois_debut_stage=?, promotion_etudiant=? WHERE id=?"; // Supposons que 'id' est la clé primaire de la table Stage
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, stage.getEntreprise());
            statement.setString(2, stage.getSujet());
            statement.setString(3, stage.getDuree());
            statement.setString(4, stage.getDateDebut());
            statement.setString(5, stage.getPromotion());
            // Assurez-vous de remplacer 'id' par le nom de votre colonne de clé primaire
            statement.setInt(6, stage.getId()); // Utilisez getId() pour obtenir l'identifiant du stage
            
            // Exécuter la mise à jour seulement si aucun champ n'est vide
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Données du stage mises à jour dans la base de données.");
            } else {
                System.out.println("Aucune donnée mise à jour dans la base de données.");
            }
        } else {
            System.out.println("La connexion à la base de données n'est pas établie.");
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Gérer l'exception de manière appropriée
    } finally {
        DatabaseConnector.disconnect(conn);
    }
}


@FXML
private void handleSupprimerButton(ActionEvent event) {
    // Récupérer le stage sélectionné dans la TableView
    StageData stageSelectionne = stageTableView.getSelectionModel().getSelectedItem();
    
    // Vérifier si un stage est sélectionné
    if (stageSelectionne != null) {
        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce stage ?");
        alert.setContentText("Cette action est irréversible.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Si l'utilisateur confirme la suppression, appeler la méthode supprimerStage
            supprimerStage(stageSelectionne);
        }
    } else {
        // Si aucun stage n'est sélectionné, afficher un message d'erreur
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un stage à supprimer.");
        alert.showAndWait();
    }
}

private void supprimerStage(StageData stage) {
    try {
        conn = DatabaseConnector.connect();
        if (conn != null && !conn.isClosed()) {
            System.out.println("Connexion à la base de données établie.");

            // Exécuter votre requête SQL pour supprimer le stage de la base de données
            String sql = "DELETE FROM Stage WHERE id=?"; // Supposons que 'id' est la clé primaire de la table Stage
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, stage.getId()); // Assurez-vous d'avoir une méthode getId() dans votre classe StageData pour récupérer l'identifiant du stage
            statement.executeUpdate();

            // Supprimer le stage de la TableView
            stageList.remove(stage);

            System.out.println("Stage supprimé de la base de données et de la TableView.");
        } else {
            System.out.println("La connexion à la base de données n'est pas établie.");
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Gérer l'exception de manière appropriée
    } finally {
        DatabaseConnector.disconnect(conn);
    }
}

// Méthode pour afficher une boîte de dialogue (pop-up) avec un message d'erreur
private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
}
