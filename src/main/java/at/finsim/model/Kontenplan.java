package at.finsim.model;

import at.finsim.model.konto.Konto;

import java.util.ArrayList;
import java.util.HashMap;

public class Kontenplan {
    private HashMap<Integer, ArrayList<Konto>> konten;

    public Kontenplan() {
        this.konten = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            this.konten.put(i, new ArrayList<>());
        }
    }

    public void kontoHinzufuegen(Konto konto) throws ModelException {
        if(konto == null) {
            throw new ModelException("Das Konto ist ungültig!");
        } else {
            if(this.konten.get(konto.getKontoklasse()).contains(konto)) {
               throw new ModelException("Das angegebene Konto ist bereits vorhanden!");
            } else {
                this.konten.get(konto.getKontoklasse()).add(konto);
            }
        }
    }
    public void kontoEntfernen(Konto konto) {
        this.konten.get(konto.getKontoklasse()).remove(konto);
    }
    public HashMap<Integer, ArrayList<Konto>> getKonten() {
        return this.konten;
    }
}