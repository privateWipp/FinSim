package at.finsim.view;

import at.finsim.model.ModelException;
import at.finsim.model.konto.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class addKontoDialog extends Dialog<Konto> {
    public addKontoDialog() {
        setTitle("Konto hinzufügen");
        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);

        // -----------------Kontonummer-----------------
        HBox kontonummerHBox = new HBox();
        Label kontonummer = new Label("Kontonummer:");
        TextField kontonummerTF = new TextField();
        kontonummerTF.setPromptText("z.B.: 2500, 9600, ...");
        kontonummerHBox.setPadding(new Insets(0, 0, 10, 10));
        kontonummerHBox.setSpacing(10);
        kontonummerHBox.getChildren().addAll(kontonummer, kontonummerTF);

        // -----------------Kontobezeichnung-----------------
        HBox kontobezeichnungHBox = new HBox();
        Label kontobezeichnung = new Label("Kontobezeichnung:");
        TextField kontobezeichnungTF = new TextField();
        kontobezeichnungTF.setPromptText("z.B.: VSt., Privat, ...");
        kontobezeichnungHBox.setPadding(new Insets(0, 0, 10, 10));
        kontobezeichnungHBox.setSpacing(10);
        kontobezeichnungHBox.getChildren().addAll(kontobezeichnung, kontobezeichnungTF);

        flowPane.getChildren().addAll(kontonummerHBox, kontobezeichnungHBox);
        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("Hinzufügen", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        /**
         * => wenn auf "Hinzufügen"-Button gedrückt wird...
         */
        this.setResultConverter(bt -> {
            if (bt == buttonType) {
                try {
                    String kontonummerTFInput = kontonummerTF.getText();
                    String kontobezeichnungTFInput = kontobezeichnungTF.getText();
                    switch (getFirstDigit(kontonummerTFInput)) {
                        case 0, 1, 2:
                            return new aktivesBestandskonto(kontobezeichnungTFInput, kontonummerTFInput, 0);

                        case 3, 9:
                            return new passivesBestandskonto(kontobezeichnungTFInput, kontonummerTFInput, 0);

                        case 4:
                            return new Ertragskonto(kontobezeichnungTFInput, kontonummerTFInput);

                        case 5, 6, 7:
                            return new Aufwandskonto(kontobezeichnungTFInput, kontonummerTFInput);

                        //case 8:
                        //    return new KontoartSelectDialog(kontobezeichnungTFInput, kontonummerTFInput).getResult();
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

    private int getFirstDigit(String number) {
        char firstChar = number.charAt(0);
        return Character.getNumericValue(firstChar);
    }
}
