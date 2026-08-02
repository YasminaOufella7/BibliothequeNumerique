package model;

public class Magazine extends Document {

    private int numero;

    public Magazine(String id,
                    String titre,
                    String auteur,
                    int annee,
                    int numero) {

        super(id, titre, auteur, annee);
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public String getCategorie() {
        return "Magazine";
    }

}