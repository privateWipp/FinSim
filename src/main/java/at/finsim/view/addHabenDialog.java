package at.finsim.view;

import at.finsim.model.Eintrag;
import at.finsim.model.ModelException;
import at.finsim.model.Unternehmen;
import at.finsim.model.konto.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.ArrayList;

public class addHabenDialog extends Dialog<KontoBetrag> {
    private Unternehmen model;

    public addHabenDialog(Unternehmen unternehmen) {
        this.model = unternehmen;

        setTitle("Haben-Seite bearbeiten");
        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);

        // -----------------Bezeichnung-----------------
        HBox bezeichnungHBox = new HBox();
        Label bezeichnung = new Label("Bezeichnung:");
        TextField bezeichnungTF = new TextField();
        bezeichnungTF.setPromptText("z.B.: Fahrrad kauf für Enkelin, ...");
        bezeichnungHBox.getChildren().addAll(bezeichnung, bezeichnungTF);
        bezeichnungHBox.setPadding(new Insets(0, 0, 10, 10));
        bezeichnungHBox.setSpacing(10);

        // -----------------Kontonummer-----------------
        HBox kontonummerHBox = new HBox();
        Label kontonummer = new Label("Kontonummer:");
        TextField kontonummerTF = new TextField();
        kontonummerTF.setPromptText("z.B.: 0630, 7380, 4400, ...");
        kontonummerHBox.getChildren().addAll(kontonummer, kontonummerTF);
        kontonummerHBox.setPadding(new Insets(0, 0, 10, 10));
        kontonummerHBox.setSpacing(10);

        // -----------------Betrag-----------------
        HBox betragHBox = new HBox();
        Label betrag = new Label("Betrag:");
        TextField betragTF = new TextField();
        betragTF.setPromptText("z.B.: 62500, 450, 8750, ...");
        betragHBox.getChildren().addAll(betrag, betragTF);
        betragHBox.setPadding(new Insets(0, 0, 10, 10));
        betragHBox.setSpacing(10);

        flowPane.getChildren().addAll(bezeichnungHBox, kontonummerHBox, betragHBox);
        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("+ Haben", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        /**
         * => wenn auf "+ Haben"-Button gedrückt wird...
         */
        this.setResultConverter(bt -> {
            if (bt == buttonType) {
                boolean vorhanden = false;
                Konto tmp = null;
                String bezeichnungTFInput = bezeichnungTF.getText();
                String kontonummerTFInput = kontonummerTF.getText();
                float betragTFInput = Float.parseFloat(betragTF.getText());
                for (ArrayList<Konto> konto : this.model.getKontenplan().getKonten().values()) {
                    for (Konto konto1 : konto) {
                        if (konto1.getKontonummer().equals(kontonummerTFInput)) {
                            vorhanden = true;
                            tmp = konto1;
                        }
                    }
                }

                try {
                    if (vorhanden) { // Wenn Konto schon im Kontenplan eingetragen ist...
                        Eintrag eintrag = new Eintrag(LocalDate.now(), bezeichnungTFInput, betragTFInput);
                        tmp.getHaben().add(eintrag);
                        return new KontoBetrag(tmp, betragTFInput);
                    } else { // Wenn nicht...
                        switch (getFirstDigit(kontonummerTFInput)) {
                            case 0, 1, 2:
                                return new KontoBetrag(tmp = new aktivesBestandskonto(bezeichnungTFInput, kontonummerTFInput, 0), betragTFInput);

                            case 3, 9:
                                return new KontoBetrag(tmp = new passivesBestandskonto(bezeichnungTFInput, kontonummerTFInput, 0), betragTFInput);

                            case 4:
                                return new KontoBetrag(tmp = new Ertragskonto(bezeichnungTFInput, kontonummerTFInput), betragTFInput);

                            case 5, 6, 7:
                                return new KontoBetrag(tmp = new Aufwandskonto(bezeichnungTFInput, kontonummerTFInput), betragTFInput);

                            //case 8:
                            //    return new KontoartSelectDialog(bezeichnungTFInput, kontonummerTFInput).getResult();
                        }
                        return new KontoBetrag(tmp, betragTFInput);
                    }
                } catch (ModelException me) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Fehler");
                    alert.setHeaderText("+ Haben erstellen");
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
