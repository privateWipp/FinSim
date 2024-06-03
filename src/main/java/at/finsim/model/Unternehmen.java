package at.finsim.model;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Unternehmen implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name; // Bezeichnung/Name des Unternehmens
    private int gruendungsjahr; // wann wurde das Unternehmen gegründet?
    private LocalDate aktuellesDatum; // was ist das aktuelle Datum in der Simulation?
    private ArrayList<Geschaeftsjahr> geschaeftsjahre; // alle Geschäftsjahre des Unternehmens, dargestellt in einer ArrayList
    private Kontenplan kontenplan; // der Kontenplan des Unternehmens
    private float budget; // wie viel Budget hat das Unternehmen?

    /**
     * Konstruktor:
     *
     * @param name Bezeichnung
     * @param gruendungsjahr Jahr der Gründung
     * @param aktuellesDatum das aktuelle Datum
     * @param geschaeftsjahre Geschäftsjahre
     * @param kontenplan Kontenplan
     * @param budget Budget
     * @throws ModelException Wird von Setter geworfen
     */
    public Unternehmen(String name, int gruendungsjahr, LocalDate aktuellesDatum, ArrayList<Geschaeftsjahr> geschaeftsjahre, Kontenplan kontenplan, float budget) throws ModelException {
        setName(name);
        setGruendungsjahr(gruendungsjahr);
        this.aktuellesDatum = LocalDate.now();
        this.geschaeftsjahre = new ArrayList<>();
        Geschaeftsjahr geschaeftsjahr = new Geschaeftsjahr(gruendungsjahr, name, aktuellesDatum, kontenplan);
        this.geschaeftsjahre.add(geschaeftsjahr);
        setKontenplan(kontenplan);
        setBudget(budget);
    }

    /**
     * Überprüft, ob der übergebene Name/Bezeichnung des Unternehmens gültig ist
     * => nicht leer bzw. null!
     *
     * @param name
     * @throws ModelException
     */
    public void setName(String name) throws ModelException {
        if(name == null || name.isEmpty()) {
            throw new ModelException("Der Name des Unternehmens darf nicht leer bzw. null sein!");
        } else {
            this.name = name;
        }
    }

    /**
     * Überprüft, ob das übergebene Gründungsjahr gültig ist => GRÖßER 0!
     *
     * @param gruendungsjahr
     * @throws ModelException
     */
    public void setGruendungsjahr(int gruendungsjahr) throws ModelException {
        if(gruendungsjahr < 0) {
            throw new ModelException("Ungültiges Gründungsjahr!");
        } else {
            this.gruendungsjahr = gruendungsjahr;
        }
    }

    /**
     * Diese Methode dient dazu, ein neues Geschäftsjahr im Unternehmen anzugeben.
     *
     * @param geschaeftsjahr
     * @throws ModelException
     */
    public void addGeschaeftsjahr(Geschaeftsjahr geschaeftsjahr) throws ModelException {
        if(geschaeftsjahr == null) {
            throw new ModelException("Ungültiges Geschäftsjahr!");
        } else {
            this.geschaeftsjahre.add(geschaeftsjahr);
        }
    }

    /**
     * Ein Kontenplan für das jeweilige Unternehmen wird festgelegt.
     *
     * @param kontenplan
     * @throws ModelException
     */
    public void setKontenplan(Kontenplan kontenplan) throws ModelException {
        if(kontenplan == null) {
            throw new ModelException("Ungültiger Kontenplan!");
        } else {
            this.kontenplan = kontenplan;
        }
    }

    /**
     * Überprüft, ob das übergebene Budget gültig ist => GRÖßER 0!
     *
     * @param budget
     * @throws ModelException
     */
    public void setBudget(float budget) throws ModelException {
        if(budget < 0) {
            throw new ModelException("Das Budget des Unternehmens muss mindestens gleich Null sein!");
        } else {
            this.budget = budget;
        }
    }

    /**
     * Gibt die aktuelle Bilanz des Unternehmens aus.
     * Genaueres folgt noch...
     */
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

    public int getAktuellesGeschaeftsjahr() {
        return this.geschaeftsjahre.getLast().getJahr();
    }

    /**
     * Dient dazu das gesamte Unternehmen,
     * alles was dazu gehört,
     * abzuspeichern in ein eigenes File.
     *
     * @param file
     * @throws FileNotFoundException
     */
    public void speichern(File file) throws FileNotFoundException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(getName());
            oos.writeObject(getGruendungsjahr());
            oos.writeObject(getAktuellesDatum());
            oos.writeObject(getGeschaeftsjahre());
            oos.writeObject(getKontenplan());
            oos.writeObject(getBudget());
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Dient dazu ein ganzes Unternehmen von Außen in FinSim
     * reinzuladen...
     *
     * @param file
     * @throws FileNotFoundException
     */
    public void laden(File file) throws FileNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            this.name = (String) ois.readObject();
            this.gruendungsjahr = (Integer) ois.readObject();
            this.aktuellesDatum = (LocalDate) ois.readObject();
            this.geschaeftsjahre = (ArrayList<Geschaeftsjahr>) ois.readObject();
            this.kontenplan = (Kontenplan) ois.readObject();
            this.budget = (Float) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
