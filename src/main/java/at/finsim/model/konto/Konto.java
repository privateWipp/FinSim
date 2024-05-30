package at.finsim.model.konto;

import at.finsim.model.Eintrag;
import at.finsim.model.ModelException;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Das ist die abstrakte Klasse Konto, da es 4 Kontoarten gibt. Ein Konto enthält Einträge und ist ein aktiver Bestand einer Bilanz.
 *
 * @author Jonas & Nikodem
 * @version  0.1
 */
public abstract class Konto {
    private String bezeichnung;
    private String kontonummer;
    private ArrayList<Eintrag> soll;
    private ArrayList<Eintrag> haben;

    /**
     * @param bezeichnung, kontonummer, soll, haben
     * @throws ModelException
     */
    public Konto(String bezeichnung, String kontonummer, ArrayList<Eintrag> soll, ArrayList<Eintrag> haben) throws ModelException {
        setBezeichnung(bezeichnung);
        setKontonummer(kontonummer);
        this.soll = new ArrayList<Eintrag>();
        this.haben = new ArrayList<Eintrag>();
    }

    public void setBezeichnung(String bezeichnung) throws ModelException {
        if (bezeichnung == null || bezeichnung.isEmpty()) {
            throw new ModelException("Die Bezeichnung des Kontos darf nicht leer bzw. null sein!");
        } else {
            this.bezeichnung = bezeichnung;
        }
    }

    public void setKontonummer(String kontonummer) throws ModelException {
        if (kontonummer == null || kontonummer.length() < 4) {
            throw new ModelException("Die Kontonummer muss immer größer Null sein und vier Zeichen lang!");
        } else {
            int kontonummerZahl = Integer.parseInt(kontonummer);
            if (kontonummerZahl < 0 || kontonummerZahl >= 10000) {
                throw new ModelException("Die Kontonummer muss größer Null und 10.000 sein (=entspricht nicht der Konvention)!");
            } else {
                this.kontonummer = kontonummer;
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

    public void eintragen(ArrayList<Eintrag> eintragArrayList, Eintrag eintrag) throws ModelException {
        if (!eintragArrayList.equals(this.soll) && !eintragArrayList.equals(this.haben)){
            throw new ModelException("Ungültige ArrayList!");
        } else if(eintragArrayList.contains(eintrag)) {
            throw new ModelException("Der übergebene Eintrag ist bereits vorhanden!");
        } else {
            eintragArrayList.add(eintrag);
        }
    }

    /**
     * Diese Methode summiert die beiden Seiten des Kontos und differenziert die
     * @return saldo: Der Endbestand des Kontos
     */
    public float berechneSaldo() {
        float saldo = 0;
        float sollSeite = 0;
        float habenSeite = 0;

        for(Eintrag tmp : this.soll) {
            sollSeite += tmp.getBetrag();
        }
        for(Eintrag tmp : this.haben) {
            habenSeite += tmp.getBetrag();
        }

        if(sollSeite > habenSeite) {
            saldo = sollSeite - habenSeite;
        } else if(habenSeite > sollSeite) {
            saldo = habenSeite - sollSeite;
        }

        return saldo;
    }

    abstract float berechneBestand();

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
                "Kontonr.: " + getKontonummer();
    }
}
