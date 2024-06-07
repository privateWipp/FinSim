package at.finsim.control;

import at.finsim.model.ModelException;
import at.finsim.model.Unternehmen;
import at.finsim.model.konto.Konto;
import at.finsim.view.addKontoDialog;
import at.finsim.view.neueBuchungDialog;
import at.finsim.view.unternehmenView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * @author Jonas Mader, Nikodem Marek
 * @version 0.1
 */

public class unternehmenController {
    private unternehmenView view;
    private Unternehmen model;

    /**
     * Konstruktor zum 2.-Controller
     * (findet nur Anwendung wenn ein Unternehmen geöffnet wird)
     *
     * @param unternehmenView
     * @param unternehmen
     */
    public unternehmenController(unternehmenView unternehmenView, Unternehmen unternehmen) {
        this.view = unternehmenView;
        this.model = unternehmen;
    }

    /**
     * Mit Hilfe eines FileChoosers (und der speichern(...)-Methode im model)
     * bekommt der Benutzer die Möglichkeit alles, was mit dem Unternehmen zutun hat,
     * in ein File abzuspeichern.
     */
    public void unternehmenSpeichern() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Unternehmen speichern");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("finsim-Datei", "*.finsim");
        fc.getExtensionFilters().add(extFilter);

        File desktopDir = new File(System.getProperty("user.home"), "Desktop");
        fc.setInitialDirectory(desktopDir);

        File file = fc.showSaveDialog(this.view.getScene().getWindow());

        try {
            this.model.speichern(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ebenfalls mit Hilfe eines FileChoosers (und der laden(...)-Methode im model)
     * bekommt der Benutzer die Möglichkeit andere Unternehmen, die nicht gerade im selben Fenster
     * geöffnet sind, zu öffnen und eben zu "laden"
     */
    public void unternehmenLaden() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Unternehmen laden");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("finsim-Datei", "*.finsim");
        fc.getExtensionFilters().add(extFilter);

        File desktopDir = new File(System.getProperty("user.home"), "Desktop");
        fc.setInitialDirectory(desktopDir);

        File file = fc.showOpenDialog(this.view.getScene().getWindow());

        try {
            this.model.laden(file);
            this.view.refreshData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addKonto() {
        addKontoDialog addKontoDialog = new addKontoDialog();
        Optional<Konto> k = addKontoDialog.showAndWait();

        k.ifPresent(konto -> {
            try {
                this.model.getKontenplan().kontoHinzufuegen(konto);
                this.view.updateKontenplan();
            } catch (ModelException e) {
                this.view.errorAlert("Konto erstellen", e.getMessage());
            }
        });
    }

    public void neueBuchung() {
        neueBuchungDialog neueBuchungDialog = new neueBuchungDialog();
    }
}
