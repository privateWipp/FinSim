package at.finsim.control;

import at.finsim.model.Unternehmen;
import at.finsim.view.kontenplanView;
import at.finsim.view.unternehmenView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;

public class unternehmenController {
    private unternehmenView view;
    private Unternehmen model;

    public unternehmenController(unternehmenView unternehmenView, Unternehmen unternehmen) {
        this.view = unternehmenView;
        this.model = unternehmen;
    }

    public void unternehmenSpeichern() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Unternehmen speichern");

        File file = fc.showSaveDialog(this.view.getScene().getWindow());

        try {
            this.model.speichern(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void unternehmenLaden() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Unternehmen laden");

        File file = fc.showOpenDialog(this.view.getScene().getWindow());

        try {
            this.model.laden(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void showKontenplan(Unternehmen unternehmen) {
        kontenplanView kontenplanView = new kontenplanView(unternehmen);
    }
}
