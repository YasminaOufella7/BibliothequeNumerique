package service;

import model.Document;

import java.util.ArrayList;

public class StatistiqueService {

    private ArrayList<Document> documents;

    public StatistiqueService(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public void afficherStatistiques() {

        int livres = 0;
        int magazines = 0;
        int numeriques = 0;
        int totalEmprunts = 0;

        Document documentPlusEmprunte = null;

        System.out.println("\n===== STATISTIQUES =====");

        for (Document document : documents) {

            if (document.getCategorie().equals("Livre")) {
                livres++;
            } else if (document.getCategorie().equals("Magazine")) {
                magazines++;
            } else if (document.getCategorie().equals("Livre numérique")) {
                numeriques++;
            }

            totalEmprunts =
                    totalEmprunts + document.getNombreEmprunts();

            if (documentPlusEmprunte == null ||
                    document.getNombreEmprunts()
                            > documentPlusEmprunte.getNombreEmprunts()) {

                documentPlusEmprunte = document;
            }
        }

        System.out.println("Nombre de livres : " + livres);
        System.out.println("Nombre de magazines : " + magazines);
        System.out.println(
                "Nombre de livres numériques : " + numeriques
        );

        System.out.println(
                "Nombre total d'emprunts : " + totalEmprunts
        );

        if (documentPlusEmprunte != null &&
                documentPlusEmprunte.getNombreEmprunts() > 0) {

            System.out.println(
                    "Document le plus emprunté : "
                            + documentPlusEmprunte.getTitre()
                            + " avec "
                            + documentPlusEmprunte.getNombreEmprunts()
                            + " emprunt(s)"
            );
        } else {

            System.out.println(
                    "Aucun document n'a encore été emprunté."
            );
        }

        System.out.println("\nDocuments jamais empruntés :");

        for (Document document : documents) {

            if (document.getNombreEmprunts() == 0) {

                System.out.println(
                        document.getId()
                                + " - "
                                + document.getTitre()
                );
            }
        }

        double tauxUtilisation = 0;

        if (!documents.isEmpty()) {

            tauxUtilisation =
                    (totalEmprunts * 100.0) / documents.size();
        }

        System.out.println(
                "\nTaux d'utilisation : "
                        + tauxUtilisation
                        + " %"
        );
    }
    public int nombreTotalDocuments() {
        return documents.size();
    }

    public int nombreDocumentsDisponibles() {

        int nombre = 0;

        for (Document document : documents) {

            if (document.isDisponible()) {
                nombre++;
            }
        }

        return nombre;
    }

    public int nombreDocumentsEmpruntes() {

        int nombre = 0;

        for (Document document : documents) {

            if (!document.isDisponible()) {
                nombre++;
            }
        }

        return nombre;
    }

    public Document documentLePlusEmprunte() {

        if (documents.isEmpty()) {
            return null;
        }

        Document plusEmprunte = documents.get(0);

        for (Document document : documents) {

            if (document.getNombreEmprunts()
                    > plusEmprunte.getNombreEmprunts()) {

                plusEmprunte = document;
            }
        }

        return plusEmprunte;
    }
}