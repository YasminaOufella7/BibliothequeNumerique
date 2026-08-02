package service;

import exception.DocumentIndisponibleException;
import model.Document;

import java.util.ArrayList;

public class BibliothequeService {

    private ArrayList<Document> documents;

    public BibliothequeService(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public Document chercherDocument(String id) {

        for (Document document : documents) {

            if (document.getId().equalsIgnoreCase(id)) {
                return document;
            }
        }

        return null;
    }

    public void emprunterDocument(String id) {

        Document document = chercherDocument(id);

        if (document == null) {
            System.out.println("Document introuvable.");
            return;
        }

        try {

            document.emprunter();

            System.out.println(
                    "Le document \"" +
                            document.getTitre() +
                            "\" a été emprunté."
            );

        } catch (DocumentIndisponibleException e) {

            System.out.println(e.getMessage());
        }
    }

    public void retournerDocument(String id) {

        Document document = chercherDocument(id);

        if (document == null) {

            System.out.println("Document introuvable.");
            return;
        }

        document.retourner();

        System.out.println(
                "Le document \"" +
                        document.getTitre() +
                        "\" a été retourné."
        );
    }

}