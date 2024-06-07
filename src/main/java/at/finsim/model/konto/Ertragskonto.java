package at.finsim.model.konto;

import at.finsim.model.Eintrag;
import at.finsim.model.ModelException;

import java.io.Serializable;

/**
 * Ertragskonten sind erfolgs erhöhend
 */
public class Ertragskonto extends Konto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *Der Default-Konstruktor:
     *
     * @param bezeichnung Name
     * @param kontonummer Kennzahl
     * @throws ModelException Wird von Setter geworfen.
     */
    public Ertragskonto(String bezeichnung, String kontonummer) throws ModelException {
        super(bezeichnung, kontonummer);
    }

    /**
     * Diese Methode dient dazu, den aktuellen Bestand der Bilanz
     * des Unternehmens zu berechnen, um eventuelle Unebenheiten auszugleichen..
     * (oder für Backend-Gründe)
     *
     * @author Jonas Mader
     * @return sollSeite - habenSeite
     */
    @Override
    public float berechneBestand() {
        float sollSeite = 0;
        float habenSeite = 0;

        for(Eintrag tmp : this.getSoll()) {
            sollSeite += tmp.getBetrag();
        }
        for(Eintrag tmp : this.getHaben()) {
            habenSeite += tmp.getBetrag();
        }

        return habenSeite - sollSeite;
    }
}
