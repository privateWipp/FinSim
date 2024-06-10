package at.finsim.control;

import at.finsim.model.Buchung;
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
     * (findet nur Anwendung, wenn ein Unternehmen geöffnet wird)
     *
     * @param unternehmenView
     * @param unternehmen
     */
    public unternehmenController(unternehmenView unternehmenView, Unternehmen unternehmen) {
        this.view = unternehmenView;
        this.model = unternehmen;
    }

    /**
     * Mithilfe eines FileChoosers (und der speichern(...)-Methode im model)
     * bekommt der Benutzer die Möglichkeit alles, was mit dem Unternehmen zutun hat,
     * in ein File abzuspeichern.
     */
    public void unternehmenSpeichern() {
        // Specify the path to your XML configuration file
        String xmlFilePath = System.getProperty("user.dir") + File.separator + "config.xml";

        // Get the default directory path from the XML file
        String defaultDirectoryPath = ConfigReader.getDefaultDirectoryPath(xmlFilePath);

        FileChooser fc = new FileChooser();
        fc.setTitle("Unternehmen speichern");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fs-Datei", "*.fs");
        fc.getExtensionFilters().add(extFilter);

        File defaultDir = new File(defaultDirectoryPath);
        if (defaultDir.exists() && defaultDir.isDirectory()) {
            fc.setInitialDirectory(defaultDir);
        } else {
            File desktopDir = new File(System.getProperty("user.home"), "Desktop");
            fc.setInitialDirectory(desktopDir);
        }

        File file = fc.showSaveDialog(this.view.getScene().getWindow());

        if (file != null) {
            try {
                this.model.setDateiname(file.getName());
                this.model.speichern(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addKonto() {
        addKontoDialog addKontoDialog = new addKontoDialog();
        Optional<Konto> k = addKontoDialog.showAndWait();

        k.ifPresent(konto -> {
            try {
                this.model.getKontenplan().kontoHinzufuegen(konto);
                this.view.updateKontenplan();
            } catch (ModelException me) {
                this.view.errorAlert("Konto erstellen", me.getMessage());
            }
        });
    }

    public void neueBuchung() {
        neueBuchungDialog neueBuchungDialog = new neueBuchungDialog(this.model);
        Optional<Buchung> b = neueBuchungDialog.showAndWait();

        b.ifPresent(buchung -> {
            /**try {

            } catch (ModelException me) {
                this.view.errorAlert("Buchung erstellen", me.getMessage());
            }**/
        });
    }
}
