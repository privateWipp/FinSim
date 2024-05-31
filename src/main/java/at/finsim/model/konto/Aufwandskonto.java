package at.finsim.model.konto;

import at.finsim.model.Eintrag;
import at.finsim.model.ModelException;


public class Aufwandskonto extends Konto {
    /**
     * @param bezeichnung Name
     * @param kontonummer Kennzahl
     * @throws ModelException Wird von Setter geworfen
     */
    public Aufwandskonto(String bezeichnung, String kontonummer) throws ModelException {
        super(bezeichnung, kontonummer);
    }

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
