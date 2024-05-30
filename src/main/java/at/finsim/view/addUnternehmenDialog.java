package at.finsim.view;

import at.finsim.model.ModelException;
import at.finsim.model.Unternehmen;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * Diese Klasse ist ein Dialog, der dem Nutzer dazu dient, per Klick
 * ein neues Unternehmen aufzunehmen.
 *
 * @author Jonas
 * @version 0.1
 */

public class addUnternehmenDialog extends Dialog<Unternehmen> {
    public addUnternehmenDialog() {
        setTitle("neues Unternehmen");
        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);

        HBox nameHBox = new HBox();
        Label name = new Label("Name des Unternehmens:");
        TextField nameTF = new TextField();
        nameTF.setPromptText("z.B.: Apple, Microsoft GmbH, ...");
        nameHBox.setPadding(new Insets(0, 0, 10, 10));
        nameHBox.setSpacing(10);
        nameHBox.getChildren().addAll(name, nameTF);

        HBox gruendungsJahrHBox = new HBox();
        Label gruendungsJahr = new Label("Gründungsjahr:");
        TextField gruendungsJahrTF = new TextField();
        gruendungsJahrTF.setPromptText("z.B.: 2017, 2003, 2024, ...");
        gruendungsJahrHBox.setPadding(new Insets(0, 0, 10, 10));
        gruendungsJahrHBox.setSpacing(10);
        gruendungsJahrHBox.getChildren().addAll(gruendungsJahr, gruendungsJahrTF);

        HBox budgetHBox = new HBox();
        Label budget = new Label("Budget:");
        TextField budgetTF = new TextField();
        budgetTF.setPromptText("z.B.: 450.000");
        Label eurSymbol = new Label("€");
        budgetHBox.setPadding(new Insets(0, 0, 10, 10));
        budgetHBox.setSpacing(10);
        budgetHBox.getChildren().addAll(budget, budgetTF, eurSymbol);

        flowPane.getChildren().addAll(nameHBox, gruendungsJahrHBox, budgetHBox);
        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("Hinzufühen", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(new Callback<ButtonType, Unternehmen>() {
            @Override
            public Unternehmen call(ButtonType bt) {
                if(bt == buttonType) {
                    try {
                        int gruendungsJahr = Integer.parseInt(gruendungsJahrTF.getText());
                        float budget = Float.parseFloat(budgetTF.getText());
                        return new Unternehmen(nameTF.getText(), gruendungsJahr, budget);
                    } catch (ModelException me) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Fehler");
                        alert.setHeaderText("Unternehmen erstellen");
                        alert.setContentText(me.getMessage());
                        alert.showAndWait();
                    }
                }
                return null;
            }
        });
    }
}
