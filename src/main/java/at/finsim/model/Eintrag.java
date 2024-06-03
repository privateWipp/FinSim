package at.finsim.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Eine Buchung ist ein einzelner Eintrag im paginierten Konto.
 *
 * @author Jonas & Nikodem
 * @version 0.1
 */
public class Eintrag implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Das Datum der Buchung
     */
    private LocalDate datum;

    /**
     * Der Text der Buchung: eine Bezeichnung um den Betrag zu verknüpfen
     */
    private String text;

    /**
     * Der Geldbetrag
     */
    private float betrag;

    public Eintrag(LocalDate datum, String text, float betrag) throws ModelException {
        setDatum(datum);
        setText(text);
        setBetrag(betrag);
    }

    /**
     * Überprüft, ob das übergebene Datum gültig ist => nicht null!
     *
     * @param datum
     * @throws ModelException
     */
    public void setDatum(LocalDate datum) throws ModelException {
        if (datum != null) {
            this.datum = datum;
        } else {
            throw new ModelException("Das Datum der Buchung darf nicht null sein!");
        }
    }

    /**
     * Überprüft, ob der übergebene Text gültig ist => nicht leer bzw. null!
     *
     * @param text
     * @throws ModelException
     */
    public void setText(String text) throws ModelException {
        if (text == null || text.isEmpty()) {
            throw new ModelException("Der Text der Buchung darf nicht leer bzw. null sein!");
        } else {
            this.text = text;
        }
    }

    /**
     * Überprüft, ob der übergebene Betrag des Eintrags gültig ist => GRÖßer 0!
     *
     * @param betrag
     * @throws ModelException
     */
    public void setBetrag(float betrag) throws ModelException {
        if(betrag < 0) {
            throw new ModelException("Der Betrag der Buchung darf nicht kleiner als Null sein!");
        } else {
            this.betrag = betrag;
        }
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public String getText() {
        return this.text;
    }

    public float getBetrag() {
        return this.betrag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Eintrag eintrag = (Eintrag) o;
        return Objects.equals(datum, eintrag.datum) && Objects.equals(text, eintrag.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datum, text);
    }

    @Override
    public String toString() {
        return "Eintrag: " + getText() + ",\n" +
                "Datum: " + getDatum() + ",\n" +
                "Betrag:" + getBetrag();
    }
}