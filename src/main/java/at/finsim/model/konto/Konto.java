package at.finsim.model.konto;

import at.finsim.model.Eintrag;
import at.finsim.model.ModelException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Das ist die abstrakte Klasse Konto, da es 4 Kontoarten gibt. Ein Konto enthält Einträge und ist ein aktiver Bestand einer Bilanz.
 *
 * @author Jonas & Nikodem
 * @version 0.1
 */
public abstract class Konto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Der Name des Kontos
     */
    private String bezeichnung;
    /**
     * Die Kontonummer bzw. die Kennzahl. Daraus ergibt sich die Kontoklasse.
     */
    private String kontonummer;

    /**
     * Eine ArrayList von Einträgen auf der SOLL Seite. Je nach Kontoart verringern oder vermehren diese den Betrag vom Konto.
     */
    private ArrayList<Eintrag> soll;
    /**
     * Eine ArrayList von Einträgen auf der HABEN Seite. Je nach Kontoart verringern oder vermehren diese den Betrag vom Konto.
     */
    private ArrayList<Eintrag> haben;

    /**
     * Der Default-Konstruktor:
     *
     * @param bezeichnung Name
     * @param kontonummer Kennzahl
     * @throws ModelException Exception für die Setter
     */
    public Konto(String bezeichnung, String kontonummer) throws ModelException {
        setBezeichnung(bezeichnung);
        setKontonummer(kontonummer);
        this.soll = new ArrayList<>();
        this.haben = new ArrayList<>();
    }

    /**
     * Überprüft, ob die übergebene Bezeichnung gültig ist -> nicht leer bzw. null
     *
     * @param bezeichnung
     * @throws ModelException
     */
    public void setBezeichnung(String bezeichnung) throws ModelException {
        if (bezeichnung == null || bezeichnung.isEmpty()) {
            throw new ModelException("Die Bezeichnung des Kontos darf nicht leer bzw. null sein!");
        } else {
            this.bezeichnung = bezeichnung;
        }
    }

    /**
     * Überprüft, ob die übergebene Kontonummer gültig ist → Eine vierstellige positive Zahl
     *
     * @param kontonummer Kennzahl
     * @throws  ModelException Wird bei einem ungültigen Parameter geworfen
     */
    public void setKontonummer(String kontonummer) throws ModelException {
        if (kontonummer == null || kontonummer.length() != 4) {
            throw new ModelException("Die Kontonummer muss immer vier Zeichen lang sein!");
        } else {
            try {
                int kontonummerZahl = Integer.parseInt(kontonummer);
                if (kontonummerZahl < 0) {
                    throw new ModelException("Die Kontonummer muss größer Null sein!");
                } else {
                    this.kontonummer = kontonummer;
                }
            } catch (NumberFormatException e) {
                throw new ModelException(e.getMessage());
            }
        }
    }

    public String getBezeichnung() {
        return this.bezeichnung;
    }

    public String getKontonummer() {
        return this.kontonummer;
    }

    public ArrayList<Eintrag> getSoll() {
        return this.soll;
    }

    public ArrayList<Eintrag> getHaben() {
        return this.haben;
    }

    /**
     * Holt sich aus der Kontonummer die Kontoklasse (first digit)
     *
     * @return Kontoklasse
     */
    public int getKontoklasse() {
        Integer kontonummer = Integer.parseInt(this.kontonummer);
        int length = String.valueOf(kontonummer).length();
        if (length < 4) {
            return 0;
        } else {
            return Integer.parseInt(this.kontonummer.substring(0, 1));
        }
    }

    /**
     * Die Methode, um einen Eintrag hinzuzufügen
     *
     * @param eintragArrayList Es muss übergeben werden, ob auf die Soll oder Haben Seite der Eintrag gehört.
     * @param eintrag Eintrag
     * @throws ModelException Wird bei ungültigen Parametern geworfen
     */
    public void eintragen(ArrayList<Eintrag> eintragArrayList, Eintrag eintrag) throws ModelException {
        if (!eintragArrayList.equals(this.soll) && !eintragArrayList.equals(this.haben)) {
            throw new ModelException("Ungültige ArrayList!");
        } else if (eintragArrayList.contains(eintrag)) {
            throw new ModelException("Der übergebene Eintrag ist bereits vorhanden!");
        } else {
            eintragArrayList.add(eintrag);
        }
    }

    /**
     * Diese Methode summiert die beiden Seiten des Kontos und differenziert die
     *
     * @return saldo: Der Endbestand des Kontos
     */
    public float berechneSaldo() {
        float saldo = 0;
        float sollSeite = 0;
        float habenSeite = 0;

        for (Eintrag tmp : this.soll) {
            sollSeite += tmp.getBetrag();
        }
        for (Eintrag tmp : this.haben) {
            habenSeite += tmp.getBetrag();
        }

        if (sollSeite > habenSeite) {
            saldo = sollSeite - habenSeite;
        } else if (habenSeite > sollSeite) {
            saldo = habenSeite - sollSeite;
        }

        return saldo;
    }

    /**
     * Berechnet den Bestand je nach Kontoart.
     *
     * @return Der aktuelle Bestand des Kontos
     */
    public float berechneBestand() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Konto konto = (Konto) o;
        return Objects.equals(bezeichnung, konto.bezeichnung) && Objects.equals(kontonummer, konto.kontonummer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bezeichnung, kontonummer);
    }

    @Override
    public String toString() {
        return "Bezeichnung: " + getBezeichnung() + ",\n" +
                "Kontonummer.: " + getKontonummer();
    }
}
