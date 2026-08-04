import model.Document;
import service.BibliothequeService;
import util.CSVReader;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final String FICHIER_DOCUMENTS =
            "data/documents.csv";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Lecture du fichier CSV
        CSVReader lecteur = new CSVReader();

        ArrayList<Document> documents =
                lecteur.chargerDocuments(FICHIER_DOCUMENTS);

        // Création du service de bibliothèque
        BibliothequeService bibliotheque =
                new BibliothequeService(documents);

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
                    emprunterDocument(
                            scanner,
                            bibliotheque
                    );
                    break;

                case 3:
                    retournerDocument(
                            scanner,
                            bibliotheque
                    );
                    break;

                case 0:
                    System.out.println(
                            "Fin du programme."
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

        System.out.println(
                "\n===== BIBLIOTHÈQUE NUMÉRIQUE ====="
        );

        System.out.println(
                "1. Afficher tous les documents"
        );

        System.out.println(
                "2. Emprunter un document"
        );

        System.out.println(
                "3. Retourner un document"
        );

        System.out.println(
                "0. Quitter"
        );
    }

    private static int lireEntier(
            Scanner scanner,
            String message
    ) {

        while (true) {

            System.out.print(message);

            String saisie =
                    scanner.nextLine().trim();

            try {

                return Integer.parseInt(saisie);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Veuillez entrer un nombre entier."
                );
            }
        }
    }

    private static String lireTexte(
            Scanner scanner,
            String message
    ) {

        while (true) {

            System.out.print(message);

            String texte =
                    scanner.nextLine().trim();

            if (!texte.isEmpty()) {
                return texte;
            }

            System.out.println(
                    "L'identifiant ne peut pas être vide."
            );
        }
    }

    private static void emprunterDocument(
            Scanner scanner,
            BibliothequeService bibliotheque
    ) {

        String id = lireTexte(
                scanner,
                "Identifiant du document à emprunter : "
        );

        bibliotheque.emprunterDocument(id);
    }

    private static void retournerDocument(
            Scanner scanner,
            BibliothequeService bibliotheque
    ) {

        String id = lireTexte(
                scanner,
                "Identifiant du document à retourner : "
        );

        bibliotheque.retournerDocument(id);
    }
}