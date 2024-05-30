package at.finsim.control;

import at.finsim.model.Unternehmen;
import at.finsim.view.View;
import at.finsim.view.addUnternehmenDialog;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Optional;

public class Controller {
    private View view;

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

    public void unternehmenOeffnen(View view) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Unternehmen importieren");

        File file = fc.showOpenDialog(view.getScene().getWindow());

        try {

        }
    }
}