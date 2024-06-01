package at.finsim.view;

import at.finsim.model.Geschaeftsjahr;
import at.finsim.model.Kontenplan;
import at.finsim.model.ModelException;
import at.finsim.model.Unternehmen;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.ArrayList;

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

        HBox gruendungsjahrHBox = new HBox();
        Label gruendungsjahr = new Label("Gründungsjahr:");
        TextField gruendungsjahrTF = new TextField();
        gruendungsjahrTF.setPromptText("z.B.: 2017, 2003, 2024, ...");
        gruendungsjahrHBox.setPadding(new Insets(0, 0, 10, 10));
        gruendungsjahrHBox.setSpacing(10);
        gruendungsjahrHBox.getChildren().addAll(gruendungsjahr, gruendungsjahrTF);

        HBox geschaeftsjahrHBox = new HBox();
        Label geschaeftsjahr = new Label("Geschäftsjahr:");
        TextField geschaeftsjahrTF = new TextField();
        geschaeftsjahrTF.setPromptText("z.B.: 2024, 2023, 2022, ...");
        geschaeftsjahrHBox.setPadding(new Insets(0, 0, 10, 10));
        geschaeftsjahrHBox.setSpacing(10);
        geschaeftsjahrHBox.getChildren().addAll(geschaeftsjahr, geschaeftsjahrTF);

        HBox budgetHBox = new HBox();
        Label budget = new Label("Budget:");
        TextField budgetTF = new TextField();
        budgetTF.setPromptText("z.B.: 450.000");
        Label eurSymbol = new Label("€");
        budgetHBox.setPadding(new Insets(0, 0, 10, 10));
        budgetHBox.setSpacing(10);
        budgetHBox.getChildren().addAll(budget, budgetTF, eurSymbol);

        flowPane.getChildren().addAll(nameHBox, gruendungsjahrHBox, geschaeftsjahrHBox, budgetHBox);
        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("Hinzufügen", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        this.setResultConverter(bt -> {
            if(bt == buttonType) {
                try {
                    int gruendungsJahr1 = Integer.parseInt(gruendungsjahrTF.getText());
                    float budget1 = Float.parseFloat(budgetTF.getText());
                    int nowYear = LocalDate.now().getYear();
                    LocalDate now = LocalDate.now();
                    Kontenplan kontenplan = new Kontenplan();
                    ArrayList<Geschaeftsjahr> geschaeftsjahre = new ArrayList<Geschaeftsjahr>();
                    Geschaeftsjahr geschaeftsjahr1 = new Geschaeftsjahr(nowYear, nameTF.getText(), now, kontenplan);
                    geschaeftsjahre.add(geschaeftsjahr1);
                    return new Unternehmen(nameTF.getText(), gruendungsJahr1, now, geschaeftsjahre, kontenplan, budget1);
                } catch (ModelException me) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Fehler");
                    alert.setHeaderText("Unternehmen erstellen");
                    alert.setContentText(me.getMessage());
                    alert.showAndWait();
                }
            }
            return null;
        });
    }
}
