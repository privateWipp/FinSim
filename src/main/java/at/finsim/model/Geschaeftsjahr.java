package at.finsim.model;

import java.time.LocalDate;

/**
 * Ein Geschaeftsjahr mit zwei Bilanzen.
 * @author Nikodem
 * @version 0.1
 */
public class Geschaeftsjahr {
    private int jahr;
    private final Bilanz eroeffnungsbilanz;
    private Bilanz schlussbilanz;
    private boolean abgeschlossen;

    public Geschaeftsjahr(int jahr, String Firmenbezeichnung, LocalDate aktuellesDatum, Kontenplan kontenplan) throws ModelException {
        this.jahr = jahr;
        String bilanzBezeichnung = "Eröffnungsbilanz für " + Firmenbezeichnung +" per " + aktuellesDatum.toString();
        this.eroeffnungsbilanz = new Bilanz(bilanzBezeichnung, aktuellesDatum, kontenplan);
    }

    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) throws ModelException {
        if (jahr<0){
            throw new ModelException("Das Geschäftsjahr darf nicht vor Christus sein!");
        }
        this.jahr = jahr;
    }

    public Bilanz getEroeffnungsbilanz() {
        return eroeffnungsbilanz;
    }

    public Bilanz getSchlussbilanz() {
        return schlussbilanz;
    }

    public void abschliessen(String Firmenbezeichnung, LocalDate aktuellesDatum, Kontenplan kontenplan) throws ModelException {
        String bilanzBezeichnung = "Schlussbilanz für " + Firmenbezeichnung +" per " + aktuellesDatum.toString();
        this.schlussbilanz = new Bilanz(bilanzBezeichnung, aktuellesDatum, kontenplan);
        setAbgeschlossen(true);
    }

    public boolean isAbgeschlossen() {
        return abgeschlossen;
    }

    public void setAbgeschlossen(boolean abgeschlossen) {
        this.abgeschlossen = abgeschlossen;
    }
}
