import java.sql.*;
import java.util.Scanner;
public class DatabaseConnector {
    public static Connection connect() {
        try {
            String DB_URL = "jdbc:sqlite:BDDIHM.db";
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

    public static void main(String[] args) {
        Connection conn = connect();
        if (conn != null) {
            Scanner scanner = new Scanner(System.in);
            try {
                boolean continuer = true;
                while (continuer) {
                    System.out.println("Que souhaitez-vous faire ?");
                    System.out.println("1. Ajouter un stage");
                    System.out.println("2. Modifier un stage");
                    System.out.println("3. Supprimer un stage");
                    System.out.println("4. Afficher tous les stages");
                    System.out.println("5. Quitter");
                    int choix = scanner.nextInt();

                    switch (choix) {
                        case 1:
                            // Ajouter un stage
                            System.out.println("Ajout d'un nouveau stage :");
                            System.out.print("Nom de la structure : ");
                            String nom_structureAjout = scanner.next();
                            System.out.print("Sujet du stage : ");
                            String sujet_stageAjout = scanner.next();
                            System.out.print("Mois de début du stage : ");
                            String mois_debut_stageAjout = scanner.next();
                            System.out.print("Durée du stage (en mois) : ");
                            int duree_stageAjout = scanner.nextInt();
                            System.out.print("Promotion de l'étudiant : ");
                            String promotion_etudiantAjout = scanner.next();

                            Stage.ajouterStage(conn, nom_structureAjout, sujet_stageAjout, mois_debut_stageAjout, duree_stageAjout, promotion_etudiantAjout);
                            break;
                        case 2:
                            // Modifier un stage
                            System.out.println("Modification d'un stage :");
                            System.out.println("Voici tous les stages :");
                            Stage.afficherTousLesStages(conn);
                            System.out.print("ID du stage à modifier : ");
                            int idModification = scanner.nextInt();
                            System.out.print("Nouveau nom de la structure : ");
                            String nouveauNom_structure = scanner.next();
                            System.out.print("Nouveau sujet du stage : ");
                            String nouveauSujet_stage = scanner.next();
                            System.out.print("Nouveau mois de début du stage : ");
                            String nouveauMois_debut_stage = scanner.next();
                            System.out.print("Nouvelle durée du stage (en mois) : ");
                            int nouvelleDuree_stage = scanner.nextInt();
                            System.out.print("Nouvelle promotion de l'étudiant : ");
                            String nouvellePromotion_etudiant = scanner.next();

                            Stage.modifierStage(conn, idModification, nouveauNom_structure, nouveauSujet_stage, nouveauMois_debut_stage, nouvelleDuree_stage, nouvellePromotion_etudiant);
                            break;
                        case 3:
                            // Supprimer un stage
                            System.out.println("Suppression d'un stage :");
                            System.out.println("Voici tous les stages :");
                            Stage.afficherTousLesStages(conn);
                            System.out.print("ID du stage à supprimer : ");
                            int idSuppression = scanner.nextInt();
                            Stage.supprimerStage(conn, idSuppression);
                            break;
                        case 4:
                            // Afficher tous les stages
                            System.out.println("Voici tous les stages :");
                            Stage.afficherTousLesStages(conn);
                            break;
                        case 5:
                            // Quitter
                            continuer = false;
                            break;
                        default:
                            System.out.println("Choix invalide !");
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                disconnect(conn);
                scanner.close();
            }
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