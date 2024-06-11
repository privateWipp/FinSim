package at.finsim.model;

import at.finsim.model.konto.Konto;
import at.finsim.model.konto.KontoBetrag;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Die Klasse Buchung wird hauptsächlich zu Visualisierungszwecken verwendet.
 */
public class Buchung implements Serializable {
    private static final long serialVersionUID = 1L;
    private String bezeichnung; // quasi der Name für die Buchung, Bsp.: Verkauf von Fahrrädern an Nachbar
    private String beleg; // Art des Belegs, Bsp.: Kassabeleg, Eingangsrechnung, Kontoauszug
    private LocalDate datum; // wann war diese Buchung?
    private ArrayList<KontoBetrag> soll; // alle Konten der Buchung der Soll-Seite
    private ArrayList<KontoBetrag> haben; // alle Konten der Buchung der Haben-Seite

    /**
     * Der Default-Konstruktor:
     *
     * @param bezeichnung Name
     * @param beleg Belegart
     * @param datum Datum der Buchung
     * @throws ModelException Wird von Setter geworfen
     */
    public Buchung(String bezeichnung, String beleg, LocalDate datum, ArrayList<KontoBetrag> soll, ArrayList<KontoBetrag> haben) throws ModelException {
        setBezeichnung(bezeichnung);
        setBeleg(beleg);
        setDatum(datum);
        this.soll = soll;
        this.haben = soll;
    }

    /**
     * Überprüft, ob die übergebene Bezeichnung gültig ist => nicht leer bzw. null!
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
     * Überprüft, ob die übergebene Art des Belegs gültig ist => nicht leer bzw. null!
     *
     * @param beleg
     * @throws ModelException
     */
    public void setBeleg(String beleg) throws ModelException {
        if(beleg == null || beleg.isEmpty()) {
            throw new ModelException("Die Art des Beleges der Buchung darf nicht leer bzw. null sein!");
        } else {
            this.beleg = beleg;
        }
    }

    /**
     * Überprüft, ob das übergebene Datum gültig ist => nicht null!
     *
     * @param datum
     * @throws ModelException
     */
    public void setDatum(LocalDate datum) throws ModelException {
        if(datum == null) {
            throw new ModelException("Das Datum der zu eintragenden Buchung darf nicht null sein!");
        } else {
            this.datum = datum;
        }
    }

    public String getBezeichnung() {
        return this.bezeichnung;
    }

    public String getBeleg() {
        return this.beleg;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public ArrayList<KontoBetrag> getSoll() {
        return this.soll;
    }

    public ArrayList<KontoBetrag> getHaben() {
        return this.haben;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buchung buchung = (Buchung) o;
        return Objects.equals(bezeichnung, buchung.bezeichnung) && Objects.equals(beleg, buchung.beleg) && Objects.equals(datum, buchung.datum) && Objects.equals(soll, buchung.soll) && Objects.equals(haben, buchung.haben);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bezeichnung, beleg, datum, soll, haben);
    }

    @Override
    public String toString() {
        return "Buchung: " + getBezeichnung() + ",\n" +
                "Art des Belegs: " + getBeleg() + ",\n" +
                "Datum: " + getDatum();
    }
}
