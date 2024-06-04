package at.finsim.view;

import at.finsim.model.ModelException;
import at.finsim.model.konto.Aufwandskonto;
import at.finsim.model.konto.Ertragskonto;
import at.finsim.model.konto.Konto;
import at.finsim.model.konto.passivesBestandskonto;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class KontoartSelectDialog extends Dialog<Konto> {
    private ComboBox<String> comboBox;
    private String kontobezeichnung;
    private String kontonummer;

    public KontoartSelectDialog(String kontobezeichnung, String kontonummer) {
        // Übergabeparameter
        this.kontobezeichnung = kontobezeichnung;
        this.kontonummer = kontonummer;

        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);
        setTitle("8er-Konto Zuweisung");

        // -----------------ComboBox-Select-----------------
        HBox comboBoxSelect = new HBox();
        Text userInfoText = new Text("Wählen Sie unten aus, ob das zu-" + "\n" +
                "erstellende 8er-Konto ein Ertrags- oder Aufwandskonto sein soll:" + "\n");
        this.comboBox = new ComboBox<String>();
        this.comboBox.getItems().add("Ertragskonto");
        this.comboBox.getItems().add("Aufwandskonto");
        comboBoxSelect.getChildren().addAll(userInfoText, this.comboBox);

        flowPane.getChildren().add(comboBoxSelect);

        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("OK", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        /**
         * => wenn auf "Hinzufügen"-Button gedrückt wird...
         */
        this.setResultConverter(bt -> {
            if (bt == buttonType) {
                try {
                    String comboBoxInput = this.comboBox.getSelectionModel().getSelectedItem();
                    if (comboBoxInput.equals("Ertragskonto")) {
                        return new Ertragskonto(this.kontobezeichnung, this.kontonummer);
                    } else if (comboBoxInput.equals("Aufwandskonto")) {
                        return new Aufwandskonto(this.kontobezeichnung, this.kontonummer);
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
