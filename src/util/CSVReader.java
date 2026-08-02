package util;

import exception.DonneeInvalideException;
import model.Document;
import model.Livre;
import model.LivreNumerique;
import model.Magazine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVReader {

    public ArrayList<Document> chargerDocuments(String cheminFichier) {

        ArrayList<Document> documents = new ArrayList<>();

        try {

            File fichier = new File(cheminFichier);
            Scanner lecteur = new Scanner(fichier);

            if (lecteur.hasNextLine()) {
                lecteur.nextLine();
            }

            int numeroLigne = 1;

            while (lecteur.hasNextLine()) {

                numeroLigne++;

                String ligne = lecteur.nextLine();
                String[] donnees = ligne.split(",");

                try {

                    if (donnees.length != 6) {
                        throw new DonneeInvalideException(
                                "La ligne ne contient pas 6 données."
                        );
                    }

                    String type = donnees[0];
                    String id = donnees[1];
                    String titre = donnees[2];
                    String auteur = donnees[3];
                    int annee = Integer.parseInt(donnees[4]);
                    String information = donnees[5];

                    if (id.isEmpty() || titre.isEmpty() || auteur.isEmpty()) {
                        throw new DonneeInvalideException(
                                "Une information obligatoire est vide."
                        );
                    }

                    if (annee <= 0) {
                        throw new DonneeInvalideException(
                                "L'année ne peut pas être négative ou égale à zéro."
                        );
                    }

                    Document document;

                    if (type.equalsIgnoreCase("Livre")) {

                        int nombrePages = Integer.parseInt(information);

                        document = new Livre(
                                id,
                                titre,
                                auteur,
                                annee,
                                nombrePages
                        );

                    } else if (type.equalsIgnoreCase("Magazine")) {

                        int numero = Integer.parseInt(information);

                        document = new Magazine(
                                id,
                                titre,
                                auteur,
                                annee,
                                numero
                        );

                    } else if (type.equalsIgnoreCase("LivreNumerique")) {

                        double tailleFichier =
                                Double.parseDouble(information);

                        document = new LivreNumerique(
                                id,
                                titre,
                                auteur,
                                annee,
                                tailleFichier
                        );

                    } else {

                        throw new DonneeInvalideException(
                                "Type de document inconnu : " + type
                        );
                    }

                    documents.add(document);

                } catch (DonneeInvalideException |
                         NumberFormatException e) {

                    System.out.println(
                            "Erreur à la ligne "
                                    + numeroLigne
                                    + " : "
                                    + e.getMessage()
                    );
                }
            }

            lecteur.close();

        } catch (FileNotFoundException e) {

            System.out.println(
                    "Le fichier CSV est introuvable : "
                            + cheminFichier
            );
        }

        return documents;
    }
}