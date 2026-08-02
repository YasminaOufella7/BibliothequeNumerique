package model;

public class LivreNumerique extends Document {

    private double tailleFichier;

    public LivreNumerique(String id,
                          String titre,
                          String auteur,
                          int annee,
                          double tailleFichier) {

        super(id, titre, auteur, annee);
        this.tailleFichier = tailleFichier;
    }

    public double getTailleFichier() {
        return tailleFichier;
    }

    @Override
    public String getCategorie() {
        return "Livre numérique";
    }

}