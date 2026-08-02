import model.Document;
import service.BibliothequeService;
import service.StatistiqueService;
import util.CSVReader;
import util.RapportWriter;
import java.util.ArrayList;
import view.BibliothequeFrame;

public class Main {

    public static void main(String[] args) {

        // Charger les documents
        CSVReader lecteur = new CSVReader();

        ArrayList<Document> documents =
                lecteur.chargerDocuments("data/documents.csv");
        BibliothequeFrame fenetre =
                new BibliothequeFrame(documents);

        fenetre.setVisible(true);

        // Afficher le nombre de documents
        System.out.println("Nombre de documents chargés : " + documents.size());

        // Afficher les documents
        for (Document document : documents) {

            System.out.println(
                    document.getId()
                            + " - "
                            + document.getTitre()
                            + " - "
                            + document.getCategorie()
            );
        }

        // Gestion des emprunts
        BibliothequeService bibliotheque =
                new BibliothequeService(documents);

       // bibliotheque.emprunterDocument("L001");
        //bibliotheque.emprunterDocument("L001");
       // bibliotheque.retournerDocument("L001");
        //bibliotheque.emprunterDocument("L001");

        // Afficher les statistiques
        StatistiqueService statistiques =
                new StatistiqueService(documents);

        statistiques.afficherStatistiques();
        RapportWriter writer = new RapportWriter();

        writer.genererRapport(documents);
    }
}