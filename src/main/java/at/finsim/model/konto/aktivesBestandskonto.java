package at.finsim.model.konto;

import at.finsim.model.Eintrag;
import at.finsim.model.ModelException;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Ein aktives Bestandskonto hat ein Anfangsbestand und steigt im Soll
 */
public class aktivesBestandskonto extends Konto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Der default-Konstruktor
     * @param bezeichnung Name
     * @param kontonummer Kennzahl
     * @param anfangsbestand Der Anfangsbestand
     * @throws ModelException Wird bei Setter geworfen
     */
    public aktivesBestandskonto(String bezeichnung, String kontonummer, float anfangsbestand) throws ModelException {
        super(bezeichnung, kontonummer);
        eintragen(getSoll(), new Eintrag(LocalDate.now(), "Anfangsbestand", anfangsbestand));
        // DAS DATUM IST INKORREKT → Das Jahr sollte das von dem aktuellen Geschäftsjahr sein
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

        return sollSeite - habenSeite;
    }
}
