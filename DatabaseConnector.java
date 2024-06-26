
import java.sql.*;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseConnector {
    public static Connection connect() {
        try {
            String DB_URL = "jdbc:sqlite:C:/Users/Aymeric/AppData/Local/Temp/a8653164-5707-4ea8-9ba6-13dccfc0f668_PaintExample (4).zip.668/Projet IHM/BDDIHM.db";
            Connection conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connexion à la base de données SQLite établie !");
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static void disconnect(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Déconnexion de la base de données SQLite !");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
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
}
