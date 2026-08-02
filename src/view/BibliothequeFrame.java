package view;

import model.Document;
import service.BibliothequeService;
import service.StatistiqueService;
import util.RapportWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class BibliothequeFrame extends JFrame {

    private ArrayList<Document> documents;
    private BibliothequeService bibliothequeService;

    private JTable tableau;
    private DefaultTableModel modeleTableau;
    private JTextField champRecherche;
    private JLabel messageBas;

    public BibliothequeFrame(ArrayList<Document> documents) {

        this.documents = documents;
        this.bibliothequeService =
                new BibliothequeService(documents);

        setTitle("Bibliothèque numérique");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        creerInterface();
        remplirTableau();
    }

    private void creerInterface() {

        setLayout(new BorderLayout());

        // ===== TITRE =====
        JLabel titre = new JLabel(
                "BIBLIOTHÈQUE NUMÉRIQUE",
                SwingConstants.CENTER
        );

        titre.setFont(new Font("Arial", Font.BOLD, 24));
        titre.setBorder(
                BorderFactory.createEmptyBorder(15, 10, 15, 10)
        );

        // ===== RECHERCHE =====
        JLabel labelRecherche =
                new JLabel("Rechercher un titre : ");

        champRecherche = new JTextField(20);

        JButton boutonRechercher =
                new JButton("Rechercher");

        JButton boutonActualiser =
                new JButton("Actualiser");

        JPanel panneauRecherche = new JPanel();

        panneauRecherche.add(labelRecherche);
        panneauRecherche.add(champRecherche);
        panneauRecherche.add(boutonRechercher);
        panneauRecherche.add(boutonActualiser);

        JPanel panneauHaut = new JPanel(
                new BorderLayout()
        );

        panneauHaut.add(titre, BorderLayout.NORTH);
        panneauHaut.add(
                panneauRecherche,
                BorderLayout.SOUTH
        );

        // ===== TABLEAU =====
        String[] colonnes = {
                "ID",
                "Titre",
                "Auteur",
                "Catégorie",
                "Disponible",
                "Nombre d'emprunts"
        };

        modeleTableau = new DefaultTableModel(
                colonnes,
                0
        ) {
            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        tableau = new JTable(modeleTableau);

        tableau.setRowHeight(25);
        tableau.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        tableau.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(tableau);

        // ===== BOUTONS =====
        JButton boutonEmprunter =
                new JButton("Emprunter");

        JButton boutonRetourner =
                new JButton("Retourner");

        JButton boutonStatistiques =
                new JButton("Statistiques");

        JButton boutonRapport =
                new JButton("Générer rapport");

        JButton boutonQuitter =
                new JButton("Quitter");

        JPanel panneauBoutons = new JPanel();

        panneauBoutons.add(boutonEmprunter);
        panneauBoutons.add(boutonRetourner);
        panneauBoutons.add(boutonStatistiques);
        panneauBoutons.add(boutonRapport);
        panneauBoutons.add(boutonQuitter);

        messageBas =
                new JLabel(
                        "Nombre de documents : "
                                + documents.size()
                );

        messageBas.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        10,
                        5,
                        10
                )
        );

        JPanel panneauBas = new JPanel(
                new BorderLayout()
        );

        panneauBas.add(
                panneauBoutons,
                BorderLayout.CENTER
        );

        panneauBas.add(
                messageBas,
                BorderLayout.SOUTH
        );

        add(panneauHaut, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panneauBas, BorderLayout.SOUTH);

        // ===== ACTIONS =====
        boutonEmprunter.addActionListener(e ->
                emprunterDocument()
        );

        boutonRetourner.addActionListener(e ->
                retournerDocument()
        );

        boutonStatistiques.addActionListener(e ->
                afficherStatistiques()
        );

        boutonRapport.addActionListener(e ->
                genererRapport()
        );

        boutonQuitter.addActionListener(e ->
                System.exit(0)
        );

        boutonRechercher.addActionListener(e ->
                rechercherDocument()
        );

        boutonActualiser.addActionListener(e -> {
            champRecherche.setText("");
            remplirTableau();

            messageBas.setText(
                    "Nombre de documents : "
                            + documents.size()
            );
        });
    }

    private void remplirTableau() {

        modeleTableau.setRowCount(0);

        for (Document document : documents) {
            ajouterDocumentDansTableau(document);
        }
    }

    private void ajouterDocumentDansTableau(
            Document document
    ) {

        String disponibilite;

        if (document.estDisponible()) {
            disponibilite = "Oui";
        } else {
            disponibilite = "Non";
        }

        Object[] ligne = {
                document.getId(),
                document.getTitre(),
                document.getAuteur(),
                document.getCategorie(),
                disponibilite,
                document.getNombreEmprunts()
        };

        modeleTableau.addRow(ligne);
    }

    private void rechercherDocument() {

        String recherche =
                champRecherche.getText().trim();

        if (recherche.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Entrez un titre à rechercher."
            );

            return;
        }

        modeleTableau.setRowCount(0);

        int nombreResultats = 0;

        for (Document document : documents) {

            if (document.getTitre()
                    .toLowerCase()
                    .contains(
                            recherche.toLowerCase()
                    )) {

                ajouterDocumentDansTableau(
                        document
                );

                nombreResultats++;
            }
        }

        messageBas.setText(
                "Résultats trouvés : "
                        + nombreResultats
        );

        if (nombreResultats == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Aucun document trouvé."
            );
        }
    }

    private void emprunterDocument() {

        int ligneSelectionnee =
                tableau.getSelectedRow();

        if (ligneSelectionnee == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Sélectionnez un document."
            );

            return;
        }

        String id =
                modeleTableau.getValueAt(
                        ligneSelectionnee,
                        0
                ).toString();

        Document document =
                bibliothequeService
                        .chercherDocument(id);

        if (document == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Document introuvable."
            );

            return;
        }

        if (!document.estDisponible()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ce document est déjà emprunté."
            );

            return;
        }

        bibliothequeService
                .emprunterDocument(id);

        remplirTableau();

        messageBas.setText(
                "Document emprunté : "
                        + document.getTitre()
        );

        JOptionPane.showMessageDialog(
                this,
                "Le document a été emprunté."
        );
    }

    private void retournerDocument() {

        int ligneSelectionnee =
                tableau.getSelectedRow();

        if (ligneSelectionnee == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Sélectionnez un document."
            );

            return;
        }

        String id =
                modeleTableau.getValueAt(
                        ligneSelectionnee,
                        0
                ).toString();

        Document document =
                bibliothequeService
                        .chercherDocument(id);

        if (document == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Document introuvable."
            );

            return;
        }

        if (document.estDisponible()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ce document n'est pas emprunté."
            );

            return;
        }

        bibliothequeService
                .retournerDocument(id);

        remplirTableau();

        messageBas.setText(
                "Document retourné : "
                        + document.getTitre()
        );

        JOptionPane.showMessageDialog(
                this,
                "Le document a été retourné."
        );
    }

    private void afficherStatistiques() {

        int livres = 0;
        int magazines = 0;
        int numeriques = 0;
        int totalEmprunts = 0;

        for (Document document : documents) {

            if (document.getCategorie()
                    .equals("Livre")) {

                livres++;

            } else if (document.getCategorie()
                    .equals("Magazine")) {

                magazines++;

            } else {

                numeriques++;
            }

            totalEmprunts +=
                    document.getNombreEmprunts();
        }

        String message =
                "Nombre de livres : "
                        + livres
                        + "\n"
                        + "Nombre de magazines : "
                        + magazines
                        + "\n"
                        + "Nombre de livres numériques : "
                        + numeriques
                        + "\n"
                        + "Nombre total d'emprunts : "
                        + totalEmprunts;

        JOptionPane.showMessageDialog(
                this,
                message,
                "Statistiques",
                JOptionPane.INFORMATION_MESSAGE
        );

        StatistiqueService statistiques =
                new StatistiqueService(documents);

        statistiques.afficherStatistiques();
    }

    private void genererRapport() {

        RapportWriter writer =
                new RapportWriter();

        writer.genererRapport(documents);

        messageBas.setText(
                "Le rapport a été généré."
        );

        JOptionPane.showMessageDialog(
                this,
                "Le fichier rapport.txt a été généré."
        );
    }
}