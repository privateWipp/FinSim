package at.finsim.model;

import java.util.Objects;

public class Unternehmen {
    private String name;
    private int gruendungsJahr;
    private float budget;

    public Unternehmen(String name, int gruendungsJahr, float budget) throws ModelException {
        setName(name);
        setGruendungsJahr(gruendungsJahr);
        setBudget(budget);
    }

    public void setName(String name) throws ModelException {
        if(name == null || name.isEmpty()) {
            throw new ModelException("Der Name des Unternehmens darf nicht leer bzw. null sein!");
        } else {
            this.name = name;
        }
    }

    public void setGruendungsJahr(int gruendungsJahr) throws ModelException {
        if(gruendungsJahr < 0) {
            throw new ModelException("Ungültiges Gründungsjahr!");
        } else {
            this.gruendungsJahr = gruendungsJahr;
        }
    }

    public void setBudget(float budget) throws ModelException {
        if(budget < 0) {
            throw new ModelException("Das Budget des Unternehmens muss mindestens gleich Null sein!");
        } else {
            this.budget = budget;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getGruendungsJahr() {
        return this.gruendungsJahr;
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
                "Gründungsjahr: " + getGruendungsJahr() + ",\n" +
                "Budget: " + getBudget() + "€";
    }
}
