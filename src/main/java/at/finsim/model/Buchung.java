package at.finsim.model;

import at.finsim.model.konto.Konto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Buchung {
    private String bezeichnung;
    private String beleg;
    private LocalDate datum;
    private ArrayList<Konto> soll;
    private ArrayList<Konto> haben;

    public Buchung(String bezeichnung, String beleg, LocalDate datum, ArrayList<Konto> soll, ArrayList<Konto> haben) throws ModelException {
        setBezeichnung(bezeichnung);
        setBeleg(beleg);
        setDatum(datum);
        this.soll = new ArrayList<Konto>();
        this.haben = new ArrayList<Konto>();
    }

    public void setBezeichnung(String bezeichnung) throws ModelException {
        if (bezeichnung == null || bezeichnung.isEmpty()) {
            throw new ModelException("Die Bezeichnung des Kontos darf nicht leer bzw. null sein!");
        } else {
            this.bezeichnung = bezeichnung;
        }
    }

    public void setBeleg(String beleg) throws ModelException {
        if(beleg == null || beleg.isEmpty()) {
            throw new ModelException("Die Art des Beleges der Buchung darf nicht leer bzw. null sein!");
        } else {
            this.beleg = beleg;
        }
    }

    public void setDatum(LocalDate datum) throws ModelException {
        if(datum == null) {
            throw new ModelException("Das Datum der zueintragenden Buchung darf nicht null sein!");
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

    public ArrayList<Konto> getSoll() {
        return this.soll;
    }

    public ArrayList<Konto> getHaben() {
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
