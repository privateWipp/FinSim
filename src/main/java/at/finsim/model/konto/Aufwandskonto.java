package at.finsim.model.konto;

import at.finsim.model.Eintrag;
import at.finsim.model.ModelException;

import java.util.ArrayList;

public class Aufwandskonto extends Konto {
    /**
     * @param bezeichnung
     * @param kontonummer
     * @param soll
     * @param haben
     * @throws ModelException
     */
    public Aufwandskonto(String bezeichnung, String kontonummer, ArrayList<Eintrag> soll, ArrayList<Eintrag> haben) throws ModelException {
        super(bezeichnung, kontonummer, soll, haben);
    }

    @Override
    float berechneBestand() {
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
