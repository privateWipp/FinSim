package at.finsim.control;

import at.finsim.model.Unternehmen;
import at.finsim.view.View;
import at.finsim.view.addUnternehmenDialog;
import at.finsim.view.kontenplanView;
import at.finsim.view.unternehmenView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Optional;

public class Controller {
    private final View view;

    public Controller(View view) {
        this.view = view;
    }

    public void neuesUnternehmen() {
        addUnternehmenDialog addUnternehmenDialog = new addUnternehmenDialog();
        Optional<Unternehmen> u = addUnternehmenDialog.showAndWait();

        u.ifPresent(unternehmen -> {
            this.view.getUnternehmenListView().getItems().add(unternehmen);
            this.view.getUnternehmenListView().refresh();
        });
    }

    public void unternehmenOeffnen() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Unternehmen importieren");

        File file = fc.showOpenDialog(this.view.getScene().getWindow());
    }

    public void runUnternehmen(Unternehmen unternehmen) {
        unternehmenView unternehmenView = new unternehmenView(this, unternehmen);
    }

    public void showKontenplan(Unternehmen unternehmen) {
        kontenplanView kontenplanView = new kontenplanView(unternehmen);
    }

    public void grundInfos(Unternehmen unternehmen) {

    }

    public void showStatistikenUnternehmen(Unternehmen unternehmen) {

    }

    public void showAktuelleBilanz(Unternehmen unternehmen) {

    }
}