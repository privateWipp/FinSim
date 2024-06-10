package at.finsim.view;

import at.finsim.model.ModelException;
import at.finsim.model.Unternehmen;
import at.finsim.model.konto.Konto;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class addSollDialog extends Dialog<Konto> {
    private Unternehmen model;

    public addSollDialog(Unternehmen unternehmen) {
        this.model = unternehmen;

        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);

        // -----------------Kontonummer-----------------
        HBox kontonummerHBox = new HBox();
        Label kontonummer = new Label("Kontonummer:");
        TextField kontonummerTF = new TextField();
        kontonummerTF.setPromptText("z.B.: 0620, 5010, 4000, ...");
        kontonummerHBox.getChildren().addAll(kontonummer, kontonummerTF);

        // -----------------Betrag-----------------
        HBox betragHBox = new HBox();
        Label betrag = new Label("Betrag:");
        TextField betragTF = new TextField();
        betragTF.setPromptText("z.B.: 62500, 450, 8750, ...");
        betragHBox.getChildren().addAll(betrag, betragTF);

        flowPane.getChildren().addAll(kontonummerHBox, betragHBox);
        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("+ Soll", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        /**
         * => wenn auf "+ Soll"-Button gedrückt wird...
         */
        this.setResultConverter(bt -> {
            if(bt == buttonType) {
                try {
                    boolean vorhanden = false;
                    String kontonummerTFInput = kontonummerTF.getText();
                    float betragTFInput = Float.parseFloat(betragTF.getText());
                    for(ArrayList<Konto> konto : this.model.getKontenplan().getKonten().values()) {
                        for(Konto konto1 : konto) {
                            if(konto1.getKontonummer().equals(kontonummerTFInput)) {
                                vorhanden = true;
                            }
                        }
                    }

                    if(vorhanden) {

                    } else {

                    }
                } catch (ModelException me) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Fehler");
                    alert.setHeaderText("Konto erstellen");
                    alert.setContentText(me.getMessage());
                    alert.showAndWait();
                }
            }
            return null;
        });
    }
}
