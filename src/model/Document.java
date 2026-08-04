package model;

import exception.DocumentIndisponibleException;
import interfaces.Empruntable;

public abstract class Document implements Empruntable {

    protected String id;
    protected String titre;
    protected String auteur;
    protected int annee;
    protected boolean disponible;
    protected int nombreEmprunts;

    public Document(String id,
                    String titre,
                    String auteur,
                    int annee) {

        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.annee = annee;

        disponible = true;
        nombreEmprunts = 0;
    }

    @Override
    public void emprunter() throws DocumentIndisponibleException {

        if (!disponible) {
            throw new DocumentIndisponibleException(
                    "Document déjà emprunté."
            );
        }

        disponible = false;
        nombreEmprunts++;
    }

    @Override
    public void retourner() {
        disponible = true;
    }

    @Override
    public boolean estDisponible() {
        return disponible;
    }

    public String getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public int getAnnee() {
        return annee;
    }

    public int getNombreEmprunts() {
        return nombreEmprunts;
    }

    public abstract String getCategorie();
    public boolean isDisponible() {
        return disponible;
    }

}