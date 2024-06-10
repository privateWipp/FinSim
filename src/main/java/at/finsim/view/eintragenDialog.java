package at.finsim.view;

import at.finsim.model.Buchung;
import at.finsim.model.ModelException;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

public class eintragenDialog extends Dialog<Buchung> {
    public eintragenDialog() {
        setTitle("Buchung eintragen");
        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);

        // -----------------Bezeichnung-----------------
        HBox bezeichnungHBox = new HBox();
        Label bezeichnung = new Label("Bezeichnung:");
        TextField bezeichnungTF = new TextField();
        bezeichnungTF.setPromptText("z.B.: erste Buchung - [XX.XX.XXXX], ...");
        bezeichnungHBox.setPadding(new Insets(0, 0, 10, 10));
        bezeichnungHBox.setSpacing(10);
        bezeichnungHBox.getChildren().addAll(bezeichnung, bezeichnungTF);

        // -----------------Beleg-----------------
        HBox belegHBox = new HBox();
        Label beleg = new Label("Beleg:");
        TextField belegTF = new TextField();
        belegTF.setPromptText("z.B.: KB, Kontoauszug, Scheck, Postbeleg, ...");
        belegHBox.setPadding(new Insets(0, 0, 10, 10));
        belegHBox.setSpacing(10);
        belegHBox.getChildren().addAll(beleg, belegTF);

        flowPane.getChildren().addAll(bezeichnungHBox, belegHBox);
        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("OK", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        /**
         * => wenn auf "OK"-Button gedrückt wird...
         */
        this.setResultConverter(bt -> {
            if(bt == buttonType) {
                try {
                    String bezeichnungTFInput = bezeichnungTF.getText();
                    String belegTFInput = belegTF.getText();

                    return new Buchung(bezeichnungTFInput, belegTFInput, LocalDate.now());
                } catch (ModelException me) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Fehler");
                    alert.setHeaderText("ungültige Buchung");
                    alert.setContentText(me.getMessage());
                    alert.showAndWait();
                }
            }
            return null;
        });
    }
}
