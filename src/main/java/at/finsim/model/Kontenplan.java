package at.finsim.model;

import at.finsim.model.konto.Konto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Kontenplan implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Eine HashMap, die als Key Integer, und als Value eine ArrayList von Konten hat.
     * Die Integer repräsentieren hierbei die verschiedenen Kontenklassen (0-9).
     * In der ArrayList<Konto> ist jedes Konto, das jemals in Verbindung mit diesem
     * Unternehmen verwendet wurde, eingetragen.
     */
    private HashMap<Integer, ArrayList<Konto>> konten;

    /**
     * Default-Konstruktor:
     * Initialisiert die HashMap<Integer, ArrayList<Konto>> konten,
     * und befüllt von 0-9 alle Keys (Integer) mit frischen ArrayListen vom Typ "Konto".
     */
    public Kontenplan() {
        this.konten = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            this.konten.put(i, new ArrayList<>());
        }
    }

    /**
     * Mit dieser Methode wird ein Konto zum Kontenplan hinzugefügt.
     * Hierbei wird überprüft ob das übergebene Konto gültig ist => not null!
     * Weiters wird sicher gestellt, dass solch ein Konto, 1:1, wie es übergeben wurde,
     * nicht bereits im Kontenplan ersichtbar bzw. existent ist.
     * Erst nach all den Anforderungen wird es in den Kontenplan aufgenommen.
     *
     * @param konto
     * @throws ModelException
     */
    public void kontoHinzufuegen(Konto konto) throws ModelException {
        if(konto == null) {
            throw new ModelException("Das Konto ist ungültig!");
        } else {
            if(this.konten.get(konto.getKontoklasse()).contains(konto)) {
               throw new ModelException("Das angegebene Konto ist bereits vorhanden!");
            } else {
                this.konten.get(konto.getKontoklasse()).add(konto);
            }
        }
    }

    /**
     * Das übergebene Konto wird aus dem Kontenplan entfernt.
     *
     * @param konto
     */
    public void kontoEntfernen(Konto konto) {
        this.konten.get(konto.getKontoklasse()).remove(konto);
    }

    public HashMap<Integer, ArrayList<Konto>> getKonten() {
        return this.konten;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kontenplan that = (Kontenplan) o;
        return Objects.equals(konten, that.konten);
    }

    @Override
    public int hashCode() {
        return Objects.hash(konten);
    }
}