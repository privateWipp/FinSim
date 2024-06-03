package at.finsim.model;

import at.finsim.model.konto.Konto;
import at.finsim.model.konto.aktivesBestandskonto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Jonas Mader, Nikodem Marek
 * @version 0.1
 */

public class Bilanz implements Serializable {
    private static final long serialVersionUID = 1L;
    private String bezeichnung;
    private LocalDate datum; // das Datum der Bilanz AN SICH!
    private final ArrayList<ArrayList<aktivesBestandskonto>> aktiva; // aktiva-Seite der Bilanz (aktive Bestandskonten)
    private final ArrayList<ArrayList<Konto>> passiva; // passiva-Seite der Bilanz (sonst. Konten)
    private ArrayList<aktivesBestandskonto> anlagevermoegen; // im Aktiva => 0
    private ArrayList<aktivesBestandskonto> umlaufvermoegen; // im Aktiva => 1, 2
    private ArrayList<Konto> eigenkapital; // im Passiva => 9
    private ArrayList<Konto> fremdkapital; // im Passiva => 3

    /**
     * Konstruktor:
     *
     * @param bezeichnung
     * @param datum
     * @param kontenplan
     * @throws ModelException
     */
    public Bilanz(String bezeichnung, LocalDate datum, Kontenplan kontenplan) throws ModelException{
        setBezeichnung(bezeichnung);
        setDatum(datum);

        this.aktiva = new ArrayList<>();
            this.anlagevermoegen = new ArrayList<>();
            this.umlaufvermoegen = new ArrayList<>();
        this.aktiva.add(anlagevermoegen);
        this.aktiva.add(umlaufvermoegen);

        this.passiva = new ArrayList<>();
            this.eigenkapital = new ArrayList<>();
            this.fremdkapital = new ArrayList<>();
        this.passiva.add(eigenkapital);
        this.passiva.add(fremdkapital);

        for (ArrayList<Konto> konten:
             kontenplan.getKonten().values()) {
            for (Konto konto:
                 konten) {
                kontoHinzufuegen(konto);
            }
        }
    }

    /**
     * Eine Methode, die basierend auf der Kontoklasse (konto.getKontoklasse())
     * das Konto, dem richtigen "Teil" der Bilanz zuweist
     *
     * @param konto
     * @throws ModelException
     */
    public void kontoHinzufuegen(Konto konto) throws ModelException {
        if(konto == null) {
            throw new ModelException("Das angegebene Konto ist ungültig!");
        } else {
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

    /**
     * Überprüft, ob die übergebene Bezeichnung gültig ist => nicht leer bzw. null!
     *
     * @param bezeichnung
     * @throws ModelException
     */
    public void setBezeichnung(String bezeichnung) throws ModelException {
        if(bezeichnung == null || bezeichnung.isEmpty()) {
            throw new ModelException("Die Bezeichnung der Bilanz darf nicht leer bzw. null sein!");
        } else {
            this.bezeichnung = bezeichnung;
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
