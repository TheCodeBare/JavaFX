
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StageData {
    private int id;
    private String nomStructure;
    private String sujetStage;
    private String moisDebutStage;
    private int dureeStage;
    private String promotionEtudiant;

    // Constructeur
    public StageData(int id, String nomStructure, String sujetStage, String moisDebutStage, int dureeStage, String promotionEtudiant) {
        this.id = id;
        this.nomStructure = nomStructure;
        this.sujetStage = sujetStage;
        this.moisDebutStage = moisDebutStage;
        this.dureeStage = dureeStage;
        this.promotionEtudiant = promotionEtudiant;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNomStructure() {
        return nomStructure;
    }

    public String getSujetStage() {
        return sujetStage;
    }

    public String getMoisDebutStage() {
        return moisDebutStage;
    }

    public int getDureeStage() {
        return dureeStage;
    }

    public String getPromotionEtudiant() {
        return promotionEtudiant;
    }

    // Méthode pour récupérer les données des stages depuis la base de données
    public static ObservableList<StageData> getStagesFromDatabase(Connection conn) throws SQLException {
        ObservableList<StageData> stages = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Stage";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nomStructure = rs.getString("nom_structure");
                String sujetStage = rs.getString("sujet_stage");
                String moisDebutStage = rs.getString("mois_debut_stage");
                int dureeStage = rs.getInt("duree_stage");
                String promotionEtudiant = rs.getString("promotion_etudiant");

                StageData stage = new StageData(id, nomStructure, sujetStage, moisDebutStage, dureeStage, promotionEtudiant);
                stages.add(stage);
            }
        }
        return stages;
    }
}