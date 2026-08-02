package model;

public class Livre extends Document {

    private int nombrePages;

    public Livre(String id,
                 String titre,
                 String auteur,
                 int annee,
                 int nombrePages) {

        super(id, titre, auteur, annee);
        this.nombrePages = nombrePages;
    }

    public int getNombrePages() {
        return nombrePages;
    }

    @Override
    public String getCategorie() {
        return "Livre";
    }

}