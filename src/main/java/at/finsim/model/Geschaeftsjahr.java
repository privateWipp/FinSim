package at.finsim.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Ein Geschaeftsjahr mit zwei Bilanzen.
 *
 * @author Nikodem
 * @version 0.1
 */
public class Geschaeftsjahr implements Serializable {
    private static final long serialVersionUID = 1L;
    private int jahr; // welches (Geschäfts-)Jahr
    private final Bilanz eroeffnungsbilanz; // die Eröffnungsbilanz
    private Bilanz schlussbilanz; // die Schlussbilanz
    private boolean abgeschlossen; // ist das Geschäftsjahr bereits abgeschlossen?

    /**
     * Konstruktor:
     *
     * @param jahr
     * @param Firmenbezeichnung
     * @param aktuellesDatum
     * @param kontenplan
     * @throws ModelException
     */
    public Geschaeftsjahr(int jahr, String Firmenbezeichnung, LocalDate aktuellesDatum, Kontenplan kontenplan) throws ModelException {
        this.jahr = jahr;
        String bilanzBezeichnung = "Eröffnungsbilanz für " + Firmenbezeichnung + " per " + aktuellesDatum.toString();
        this.eroeffnungsbilanz = new Bilanz(bilanzBezeichnung, aktuellesDatum, kontenplan);
    }

    public int getJahr() {
        return jahr;
    }

    /**
     * Überprüft, ob das übergebene Jahr gültig ist => GRÖßER 0!
     *
     * @param jahr
     * @throws ModelException
     */
    public void setJahr(int jahr) throws ModelException {
        if (jahr < 0) {
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

    /**
     * Diese Methode dient dazu das Abschließen eines Geschäftsjahres zu simulieren.
     *
     * @param Firmenbezeichnung
     * @param aktuellesDatum
     * @param kontenplan
     * @throws ModelException
     */
    public void abschliessen(String Firmenbezeichnung, LocalDate aktuellesDatum, Kontenplan kontenplan) throws ModelException {
        String bilanzBezeichnung = "Schlussbilanz für " + Firmenbezeichnung + " per " + aktuellesDatum.toString();
        this.schlussbilanz = new Bilanz(bilanzBezeichnung, aktuellesDatum, kontenplan);
        setAbgeschlossen(true);
    }

    public boolean isAbgeschlossen() {
        return this.abgeschlossen;
    }

    public void setAbgeschlossen(boolean abgeschlossen) {
        this.abgeschlossen = abgeschlossen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geschaeftsjahr that = (Geschaeftsjahr) o;
        return jahr == that.jahr;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jahr);
    }

    @Override
    public String toString() {
        return "Geschäftsjahr: " + getJahr() + ",\n" +
                "abgeschlossen: " + (isAbgeschlossen() ? "Ja" : "Nein");
    }
}
