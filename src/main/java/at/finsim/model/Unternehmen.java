package at.finsim.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Unternehmen {
    private String name;
    private int gruendungsjahr;
    private LocalDate aktuellesDatum;
    private ArrayList<Geschaeftsjahr> geschaeftsjahre;
    private Kontenplan kontenplan;
    private float budget;

    public Unternehmen(String name, int gruendungsjahr, LocalDate aktuellesDatum, ArrayList<Geschaeftsjahr> geschaeftsjahre, Kontenplan kontenplan, float budget) throws ModelException {
        setName(name);
        setGruendungsjahr(gruendungsjahr);
        this.aktuellesDatum = LocalDate.now();
        this.geschaeftsjahre = new ArrayList<Geschaeftsjahr>();
        setKontenplan(kontenplan);
        setBudget(budget);
    }

    public void setName(String name) throws ModelException {
        if(name == null || name.isEmpty()) {
            throw new ModelException("Der Name des Unternehmens darf nicht leer bzw. null sein!");
        } else {
            this.name = name;
        }
    }

    public void setGruendungsjahr(int gruendungsjahr) throws ModelException {
        if(gruendungsjahr < 0) {
            throw new ModelException("Ungültiges Gründungsjahr!");
        } else {
            this.gruendungsjahr = gruendungsjahr;
        }
    }

    public void addGeschaeftsjahr(Geschaeftsjahr geschaeftsjahr) throws ModelException {
        if(geschaeftsjahr == null) {
            throw new ModelException("Ungültiges Geschäftsjahr!");
        } else {
            this.geschaeftsjahre.add(geschaeftsjahr);
        }
    }

    public void setKontenplan(Kontenplan kontenplan) throws ModelException {
        if(kontenplan == null) {
            throw new ModelException("Ungültiger Kontenplan!");
        } else {
            this.kontenplan = kontenplan;
        }
    }

    public void setBudget(float budget) throws ModelException {
        if(budget < 0) {
            throw new ModelException("Das Budget des Unternehmens muss mindestens gleich Null sein!");
        } else {
            this.budget = budget;
        }
    }

    public void printBilanz() {
        /**
         * Methode erfordert Besprechung mit Nikodem.
         * Da das eh eine void Methode ist lasse ich sie vorerst leer..
         */
    }

    public String getName() {
        return this.name;
    }

    public int getGruendungsjahr() {
        return this.gruendungsjahr;
    }

    public LocalDate getAktuellesDatum() {
        return this.aktuellesDatum;
    }

    public ArrayList<Geschaeftsjahr> getGeschaeftsjahre() {
        return this.geschaeftsjahre;
    }

    public Kontenplan getKontenplan() {
        return this.kontenplan;
    }



    public float getBudget() {
        return this.budget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unternehmen that = (Unternehmen) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Unternehmen: " + getName() + ",\n" +
                "Gründungsjahr: " + getGruendungsjahr() + ",\n" +
                "Budget: " + getBudget() + "€";
    }
}
