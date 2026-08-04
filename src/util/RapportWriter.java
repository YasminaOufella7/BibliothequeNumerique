package util;

import model.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RapportWriter {

    public void genererCSV(
            ArrayList<Document> documents,
            String cheminFichier
    ) {

        creerDossierParent(cheminFichier);

        try (FileWriter writer = new FileWriter(cheminFichier)) {

            writer.write(
                    "id,titre,auteur,annee,categorie,disponible,nombreEmprunts\n"
            );

            for (Document document : documents) {

                writer.write(
                        document.getId() + ","
                                + document.getTitre() + ","
                                + document.getAuteur() + ","
                                + document.getCategorie() + ","
                                + document.isDisponible() + ","
                                + document.getNombreEmprunts()
                                + "\n"
                );
            }

            System.out.println(
                    "CSV généré : " + cheminFichier
            );

        } catch (IOException e) {

            System.out.println(
                    "Erreur lors de la création du CSV : "
                            + e.getMessage()
            );
        }
    }

    public void genererRapport(
            ArrayList<Document> documents,
            String cheminFichier
    ) {

        creerDossierParent(cheminFichier);

        try (FileWriter writer = new FileWriter(cheminFichier)) {

            writer.write(
                    "===== RAPPORT DE LA BIBLIOTHÈQUE =====\n\n"
            );

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
                                + " - Disponible : "
                                + document.isDisponible()
                                + " - Emprunts : "
                                + document.getNombreEmprunts()
                                + "\n"
                );

                if (document.getCategorie()
                        .equalsIgnoreCase("Livre")) {

                    livres++;

                } else if (document.getCategorie()
                        .equalsIgnoreCase("Magazine")) {

                    magazines++;

                } else {

                    numeriques++;
                }

                totalEmprunts +=
                        document.getNombreEmprunts();
            }

            writer.write(
                    "\n===== STATISTIQUES =====\n"
            );

            writer.write(
                    "Nombre total de documents : "
                            + documents.size()
                            + "\n"
            );

            writer.write(
                    "Nombre de livres : "
                            + livres
                            + "\n"
            );

            writer.write(
                    "Nombre de magazines : "
                            + magazines
                            + "\n"
            );

            writer.write(
                    "Nombre de livres numériques : "
                            + numeriques
                            + "\n"
            );

            writer.write(
                    "Nombre total d'emprunts : "
                            + totalEmprunts
                            + "\n"
            );

            System.out.println(
                    "Rapport généré : " + cheminFichier
            );

        } catch (IOException e) {

            System.out.println(
                    "Erreur lors de la création du rapport : "
                            + e.getMessage()
            );
        }
    }

    private void creerDossierParent(
            String cheminFichier
    ) {

        File fichier = new File(cheminFichier);
        File dossierParent = fichier.getParentFile();

        if (dossierParent != null
                && !dossierParent.exists()) {

            dossierParent.mkdirs();
        }
    }
}