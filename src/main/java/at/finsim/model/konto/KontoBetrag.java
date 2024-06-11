package at.finsim.model.konto;

import java.io.Serializable;

public class KontoBetrag implements Serializable {
    private static final long serialVersionUID = 1L;
    private Konto konto;
    private float betrag;

    public KontoBetrag(Konto konto, float betrag) {
        this.konto = konto;
        this.betrag = betrag;
    }

    public Konto getKonto() {
        return this.konto;
    }

    public float getBetrag() {
        return this.betrag;
    }

    @Override
    public String toString() {
        return getKonto().getKontonummer() + " " + getKonto().getBezeichnung() + " | " + getBetrag();
    }
}
