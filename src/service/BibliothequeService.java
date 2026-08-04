package service;

import exception.DocumentIndisponibleException;
import model.Document;

import java.util.ArrayList;

public class BibliothequeService {

    private final ArrayList<Document> documents;

    public BibliothequeService(ArrayList<Document> documents) {

        if (documents == null) {
            this.documents = new ArrayList<>();
        } else {
            this.documents = documents;
        }
    }

    /*
     * Retourne la liste de tous les documents.
     */
    public ArrayList<Document> getDocuments() {
        return documents;
    }

    /*
     * Affiche tous les documents.
     */
    public void afficherDocuments() {

        if (documents.isEmpty()) {

            System.out.println(
                    "Aucun document dans la bibliothèque."
            );

            return;
        }

        System.out.println(
                "\n===== LISTE DES DOCUMENTS ====="
        );

        for (Document document : documents) {
            System.out.println(document);
        }

        System.out.println(
                "Nombre total de documents : "
                        + documents.size()
        );
    }

    /*
     * Recherche un document grâce à son identifiant.
     */
    public Document chercherDocument(String id) {

        if (id == null || id.trim().isEmpty()) {
            return null;
        }

        for (Document document : documents) {

            if (document.getId().equalsIgnoreCase(
                    id.trim()
            )) {

                return document;
            }
        }

        return null;
    }

    /*
     * Même méthode avec le nom utilisé dans certains Main.
     */
    public Document rechercherDocumentParId(String id) {
        return chercherDocument(id);
    }

    /*
     * Recherche des documents par leur titre.
     */
    public ArrayList<Document> rechercherDocumentsParTitre(
            String titre
    ) {

        ArrayList<Document> resultats =
                new ArrayList<>();

        if (titre == null || titre.trim().isEmpty()) {
            return resultats;
        }

        String recherche =
                titre.trim().toLowerCase();

        for (Document document : documents) {

            if (document.getTitre()
                    .toLowerCase()
                    .contains(recherche)) {

                resultats.add(document);
            }
        }

        return resultats;
    }

    /*
     * Emprunte un document.
     */
    public void emprunterDocument(String id) {

        Document document =
                chercherDocument(id);

        if (document == null) {

            System.out.println(
                    "Document introuvable avec l'identifiant : "
                            + id
            );

            return;
        }

        try {

            document.emprunter();

            System.out.println(
                    "Le document \""
                            + document.getTitre()
                            + "\" a été emprunté avec succès."
            );

        } catch (DocumentIndisponibleException e) {

            System.out.println(
                    "Erreur : "
                            + e.getMessage()
            );
        }
    }

    /*
     * Retourne un document.
     */
    public void retournerDocument(String id) {

        Document document =
                chercherDocument(id);

        if (document == null) {

            System.out.println(
                    "Document introuvable avec l'identifiant : "
                            + id
            );

            return;
        }

        document.retourner();

        System.out.println(
                "Le document \""
                        + document.getTitre()
                        + "\" a été retourné avec succès."
        );
    }
}