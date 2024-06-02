package at.finsim.control;

import at.finsim.model.Unternehmen;
import at.finsim.view.View;
import at.finsim.view.addUnternehmenDialog;
import at.finsim.view.kontenplanView;
import at.finsim.view.unternehmenView;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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

    public void runUnternehmen(Unternehmen unternehmen) {
        Stage stage = new Stage();
        unternehmenView unternehmenView = new unternehmenView(unternehmen);
        stage.setTitle(unternehmen.getName() + " : Unternehmen");
        stage.setResizable(false);
        Scene scene = new Scene(this.view, 1280, 720);
        stage.setScene(scene);
        stage.show();

    }
}