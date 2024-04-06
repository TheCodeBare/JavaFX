
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StageData {
    private final StringProperty entreprise;
    private final StringProperty sujet;
    private final StringProperty duree;
    private final StringProperty dateDebut;
    private final StringProperty promotion;
    private final int id;
    
    public StageData(int id,String entreprise, String sujet, String duree, String dateDebut, String promotion) {
        this.id = id;
        this.entreprise = new SimpleStringProperty(entreprise);
        this.sujet = new SimpleStringProperty(sujet);
        this.duree = new SimpleStringProperty(duree);
        this.dateDebut = new SimpleStringProperty(dateDebut);
        this.promotion = new SimpleStringProperty(promotion);
    }

    // Getters et Setters pour chaque attribut
    public int getId(){
        return id;
    }
    public String getEntreprise() {
        return entreprise.get();
    }

    public void setEntreprise(String entreprise) {
        this.entreprise.set(entreprise);
    }

    public StringProperty entrepriseProperty() {
        return entreprise;
    }

    public String getSujet() {
        return sujet.get();
    }

    public void setSujet(String sujet) {
        this.sujet.set(sujet);
    }

    public StringProperty sujetProperty() {
        return sujet;
    }

    public String getDuree() {
        return duree.get();
    }

    public void setDuree(String duree) {
        this.duree.set(duree);
    }

    public StringProperty dureeProperty() {
        return duree;
    }

    public String getDateDebut() {
        return dateDebut.get();
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut.set(dateDebut);
    }

    public StringProperty dateDebutProperty() {
        return dateDebut;
    }

    public String getPromotion() {
        return promotion.get();
    }

    public void setPromotion(String promotion) {
        this.promotion.set(promotion);
    }

    public StringProperty promotionProperty() {
        return promotion;
    }
}