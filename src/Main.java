import exception.DocumentIndisponibleException;
import model.Document;
import service.BibliothequeService;
import service.StatistiqueService;
import util.CSVReader;
import util.RapportWriter;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final String FICHIER_DOCUMENTS =
            "data/documents.csv";

    private static final String FICHIER_EXPORT =
            "data/documents_apres_operations.csv";

    private static final String FICHIER_RAPPORT =
            "rapports/rapport_bibliotheque.txt";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Lecture du fichier CSV
        CSVReader lecteur = new CSVReader();

        ArrayList<Document> documents =
                lecteur.chargerDocuments(FICHIER_DOCUMENTS);

        // Création des services
        BibliothequeService bibliotheque =
                new BibliothequeService(documents);

        StatistiqueService statistiqueService =
                new StatistiqueService(documents);

        RapportWriter rapportWriter =
                new RapportWriter();

        System.out.println(
                documents.size()
                        + " document(s) valide(s) chargé(s)."
        );

        int choix;

        do {

            afficherMenu();

            choix = lireEntier(
                    scanner,
                    "Votre choix : "
            );

            switch (choix) {

                case 1:
                    bibliotheque.afficherDocuments();
                    break;

                case 2:
                    rechercherDocument(
                            scanner,
                            bibliotheque
                    );
                    break;

                case 3:
                    emprunterDocument(
                            scanner,
                            bibliotheque
                    );
                    break;

                case 4:
                    retournerDocument(
                            scanner,
                            bibliotheque
                    );
                    break;

                case 5:
                    afficherStatistiques(
                            statistiqueService
                    );
                    break;

                case 6:
                    genererFichiers(
                            documents,
                            rapportWriter
                    );
                    break;

                case 0:
                    System.out.println(
                            "Fermeture de l'application."
                    );
                    break;

                default:
                    System.out.println(
                            "Choix invalide."
                    );
            }

        } while (choix != 0);

        scanner.close();
    }

    private static void afficherMenu() {

        System.out.println();
        System.out.println(
                "===== BIBLIOTHÈQUE NUMÉRIQUE ====="
        );

        System.out.println(
                "1. Afficher les documents"
        );

        System.out.println(
                "2. Rechercher un document"
        );

        System.out.println(
                "3. Emprunter un document"
        );

        System.out.println(
                "4. Retourner un document"
        );

        System.out.println(
                "5. Afficher les statistiques"
        );

        System.out.println(
                "6. Générer le CSV et le rapport"
        );

        System.out.println(
                "0. Quitter"
        );
    }

    private static void rechercherDocument(
            Scanner scanner,
            BibliothequeService bibliotheque
    ) {

        System.out.print(
                "Entrez le titre ou l'identifiant : "
        );

        String recherche =
                scanner.nextLine().trim();

        Document document =
                bibliotheque.rechercherDocument(
                        recherche
                );

        if (document != null) {

            System.out.println(
                    "Document trouvé : "
                            + document
            );

        } else {

            System.out.println(
                    "Aucun document trouvé."
            );
        }
    }

    private static void emprunterDocument(
            Scanner scanner,
            BibliothequeService bibliotheque
    ) {

        System.out.print(
                "Entrez l'identifiant du document : "
        );

        String id =
                scanner.nextLine().trim();

        try {

            bibliotheque.emprunterDocument(id);

            System.out.println(
                    "Document emprunté avec succès."
            );

        } catch (DocumentIndisponibleException e) {

            System.out.println(
                    "Erreur : "
                            + e.getMessage()
            );
        }
    }

    private static void retournerDocument(
            Scanner scanner,
            BibliothequeService bibliotheque
    ) {

        System.out.print(
                "Entrez l'identifiant du document : "
        );

        String id =
                scanner.nextLine().trim();

        bibliotheque.retournerDocument(id);

        System.out.println(
                "Opération de retour terminée."
        );
    }

    private static void afficherStatistiques(
            StatistiqueService statistiqueService
    ) {

        System.out.println();
        System.out.println(
                "===== STATISTIQUES ====="
        );

        System.out.println(
                "Nombre total de documents : "
                        + statistiqueService
                        .nombreTotalDocuments()
        );

        System.out.println(
                "Nombre de documents disponibles : "
                        + statistiqueService
                        .nombreDocumentsDisponibles()
        );

        System.out.println(
                "Nombre de documents empruntés : "
                        + statistiqueService
                        .nombreDocumentsEmpruntes()
        );

        Document documentPopulaire =
                statistiqueService
                        .documentLePlusEmprunte();

        if (documentPopulaire != null) {

            System.out.println(
                    "Document le plus emprunté : "
                            + documentPopulaire.getTitre()
                            + " ("
                            + documentPopulaire
                            .getNombreEmprunts()
                            + " emprunt(s))"
            );

        } else {

            System.out.println(
                    "Aucun document disponible pour les statistiques."
            );
        }
    }

    private static void genererFichiers(
            ArrayList<Document> documents,
            RapportWriter rapportWriter
    ) {

        rapportWriter.genererCSV(
                documents,
                FICHIER_EXPORT
        );

        rapportWriter.genererRapport(
                documents,
                FICHIER_RAPPORT
        );

        System.out.println(
                "CSV généré : "
                        + FICHIER_EXPORT
        );

        System.out.println(
                "Rapport généré : "
                        + FICHIER_RAPPORT
        );
    }

    private static int lireEntier(
            Scanner scanner,
            String message
    ) {

        while (true) {

            System.out.print(message);

            String saisie =
                    scanner.nextLine();

            try {

                return Integer.parseInt(
                        saisie
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Veuillez entrer un nombre valide."
                );
            }
        }
    }
}