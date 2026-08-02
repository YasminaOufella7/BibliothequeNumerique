package interfaces;

import exception.DocumentIndisponibleException;

public interface Empruntable {

    void emprunter() throws DocumentIndisponibleException;

    void retourner();

    boolean estDisponible();
}