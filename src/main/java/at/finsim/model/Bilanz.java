package at.finsim.model;

import at.finsim.model.konto.Konto;
import at.finsim.model.konto.aktivesBestandskonto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Bilanz {
    private String bezeichnung;
    private LocalDate datum;
    private ArrayList<ArrayList<aktivesBestandskonto>> aktiva;
    private ArrayList<ArrayList<Konto>> passiva;
    private ArrayList<aktivesBestandskonto> anlagevermoegen;
    private ArrayList<aktivesBestandskonto> umlaufvermoegen;
    private ArrayList<Konto> eigenkapital;
    private ArrayList<Konto> fremdkapital;

    public Bilanz(String bezeichnung, LocalDate datum, ArrayList<ArrayList<aktivesBestandskonto>> aktiva, ArrayList<ArrayList<Konto>> passiva) throws ModelException{
        setBezeichnung(bezeichnung);
        setDatum(datum);

        this.aktiva = new ArrayList<ArrayList<aktivesBestandskonto>>();
            this.anlagevermoegen = new ArrayList<aktivesBestandskonto>();
            this.umlaufvermoegen = new ArrayList<aktivesBestandskonto>();
        this.aktiva.add(anlagevermoegen);
        this.aktiva.add(umlaufvermoegen);

        this.passiva = new ArrayList<ArrayList<Konto>>();
            this.eigenkapital = new ArrayList<Konto>();
            this.fremdkapital = new ArrayList<Konto>();
        this.passiva.add(eigenkapital);
        this.passiva.add(fremdkapital);
    }

    public void kontoHinzufuegen(Konto konto) throws ModelException {
        if(konto == null) {
            throw new ModelException("Das angegebene Konto ist ungültig!");
        } else {
            int klasse = konto.getKontoklasse();
            if(!(konto.berechneBestand() == 0)){
                if(konto.getKontoklasse() == 0 && konto instanceof aktivesBestandskonto) {
                    this.anlagevermoegen.add((aktivesBestandskonto) konto);
                } else if(konto.getKontoklasse() >= 1 && konto.getKontoklasse() <= 2 && konto instanceof aktivesBestandskonto) {
                    this.umlaufvermoegen.add((aktivesBestandskonto) konto);
                } else if (konto.getKontoklasse() == 3) {
                    this.fremdkapital.add(konto);
                } else if(konto.getKontoklasse() >= 4 && konto.getKontoklasse() <= 9) {
                    this.eigenkapital.add(konto);
                }
            }
        }
    }

    public void setBezeichnung(String bezeichnung) throws ModelException {
        if(bezeichnung == null || bezeichnung.isEmpty()) {
            throw new ModelException("Die Bezeichnung der Bilanz darf nicht leer bzw. null sein!");
        } else {
            this.bezeichnung = bezeichnung;
        }
    }

    public void setDatum(LocalDate datum) throws ModelException {
        if(datum == null) {
            throw new ModelException("Das Datum der Bilanz ist ungültig!");
        } else {
            this.datum = datum;
        }
    }

    public String getBezeichnung() {
        return this.bezeichnung;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public ArrayList<ArrayList<aktivesBestandskonto>> getAktiva() {
        return this.aktiva;
    }

    public ArrayList<ArrayList<Konto>> getPassiva() {
        return this.passiva;
    }

    public ArrayList<aktivesBestandskonto> getAnlagevermoegen() {
        return this.anlagevermoegen;
    }

    public ArrayList<aktivesBestandskonto> getUmlaufvermoegen() {
        return this.umlaufvermoegen;
    }

    public ArrayList<Konto> getEigenkapital() {
        return this.eigenkapital;
    }

    public ArrayList<Konto> getFremdkapital() {
        return this.fremdkapital;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bilanz bilanz = (Bilanz) o;
        return Objects.equals(datum, bilanz.datum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datum);
    }

    @Override
    public String toString() {
        return "Bilanzbezeichnung: " + getBezeichnung() + ",\n" +
                "Datum: " + getDatum();
    }
}
