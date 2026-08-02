package util;

import model.Document;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RapportWriter {

    public void genererRapport(ArrayList<Document> documents) {

        try {

            FileWriter writer = new FileWriter("rapport.txt");

            writer.write("===== RAPPORT DE LA BIBLIOTHÈQUE =====\n\n");

            int livres = 0;
            int magazines = 0;
            int numeriques = 0;
            int totalEmprunts = 0;

            for (Document document : documents) {

                writer.write(
                        document.getId()
                                + " - "
                                + document.getTitre()
                                + " - "
                                + document.getCategorie()
                                + "\n"
                );

                if (document.getCategorie().equals("Livre")) {
                    livres++;
                } else if (document.getCategorie().equals("Magazine")) {
                    magazines++;
                } else {
                    numeriques++;
                }

                totalEmprunts += document.getNombreEmprunts();
            }

            writer.write("\n===== STATISTIQUES =====\n");

            writer.write("Nombre de livres : " + livres + "\n");
            writer.write("Nombre de magazines : " + magazines + "\n");
            writer.write("Nombre de livres numériques : " + numeriques + "\n");
            writer.write("Nombre total d'emprunts : " + totalEmprunts + "\n");

            writer.close();

            System.out.println("\nRapport généré : rapport.txt");

        } catch (IOException e) {

            System.out.println("Erreur lors de la création du rapport.");
        }
    }
}