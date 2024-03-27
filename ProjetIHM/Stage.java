package ProjetIHM;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Stage {

    // Méthode pour ajouter un stage
    public static void ajouterStage(Connection connexion, String nom_structure, String sujet_stage, String mois_debut_stage, int duree_stage, String promotion_etudiant) throws SQLException {
        String sql = "INSERT INTO Stage ( nom_structure, sujet_stage, mois_debut_stage, duree_stage, promotion_etudiant) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setString(1, nom_structure);
            statement.setString(2, sujet_stage);
            statement.setString(3, mois_debut_stage);
            statement.setInt(4, duree_stage);
            statement.setString(5, promotion_etudiant);
            statement.executeUpdate();
        }
    }

    // Méthode pour modifier un stage
    public static void modifierStage(Connection connexion, int id, String nom_structure, String sujet_stage, String mois_debut_stage, int duree_stage, String promotion_etudiant) throws SQLException {
        String sql = "UPDATE Stage SET nom_structure = ?, sujet_stage = ?, mois_debut_stage = ?, duree_stage = ?, promotion_etudiant = ? WHERE id = ?";
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setString(1, nom_structure);
            statement.setString(2, sujet_stage);
            statement.setString(3, mois_debut_stage);
            statement.setInt(4, duree_stage);
            statement.setString(5, promotion_etudiant);
            statement.setInt(6, id);
            statement.executeUpdate();
        }
    }

    // Méthode pour supprimer un stage
    public static void supprimerStage(Connection connexion, int id) throws SQLException {
        String sql = "DELETE FROM Stage WHERE id = ?";
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
    // Méthode pour afficher tous les stages
    public static void afficherTousLesStages(Connection connexion) throws SQLException {
        String sql = "SELECT * FROM Stage";
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom_structure = resultSet.getString("nom_structure");
                String sujet_stage = resultSet.getString("sujet_stage");
                String mois_debut_stage = resultSet.getString("mois_debut_stage");
                int duree_stage = resultSet.getInt("duree_stage");
                String promotion_etudiant = resultSet.getString("promotion_etudiant");

                System.out.printf("ID: %d, Nom de la structure: %s, Sujet du stage: %s, Mois de début du stage: %s, Durée du stage: %d, Promotion de l'étudiant: %s%n", id, nom_structure, sujet_stage, mois_debut_stage, duree_stage, promotion_etudiant);
            }
        }
    }
}
