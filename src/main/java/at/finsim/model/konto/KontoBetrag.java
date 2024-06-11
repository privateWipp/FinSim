package at.finsim.model.konto;

public class KontoBetrag {
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
