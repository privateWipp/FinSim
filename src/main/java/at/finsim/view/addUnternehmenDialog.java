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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        // -----------------NAME-----------------
        HBox nameHBox = new HBox();
        Label name = new Label("Name des Unternehmens:");
        TextField nameTF = new TextField();
        nameTF.setPromptText("z.B.: Apple, Microsoft GmbH, ...");
        nameHBox.setPadding(new Insets(0, 0, 10, 10));
        nameHBox.setSpacing(10);
        nameHBox.getChildren().addAll(name, nameTF);

        // -----------------RECHTSFORM-----------------
        HBox rechtsformHBox = new HBox();
        Label rechtsform = new Label("Rechtsform:");
        ComboBox<String> rechtsformen = new ComboBox<String>();
        rechtsformen.getItems().add("Einzelunternehmen");
        rechtsformen.getItems().add("Personengesellschaft");
        rechtsformen.getItems().add("Kapitalgesellschaft");
        rechtsformHBox.setPadding(new Insets(0, 0, 10, 10));
        rechtsformHBox.setSpacing(10);
        rechtsformHBox.getChildren().addAll(rechtsform, rechtsformen);

        // -----------------GRÜNDUNGSJAHR-----------------
        HBox gruendungsjahrHBox = new HBox();
        Label gruendungsjahr = new Label("Gründungsjahr:");
        TextField gruendungsjahrTF = new TextField();
        gruendungsjahrTF.setPromptText("z.B.: 2017, 2003, 2024, ...");
        gruendungsjahrHBox.setPadding(new Insets(0, 0, 10, 10));
        gruendungsjahrHBox.setSpacing(10);
        gruendungsjahrHBox.getChildren().addAll(gruendungsjahr, gruendungsjahrTF);

        // -----------------BUDGET-----------------
        HBox budgetHBox = new HBox();
        Label budget = new Label("Budget:");
        TextField budgetTF = new TextField();
        budgetTF.setPromptText("z.B.: 450.000");
        Label eurSymbol = new Label("€");
        budgetHBox.setPadding(new Insets(0, 0, 10, 10));
        budgetHBox.setSpacing(10);
        budgetHBox.getChildren().addAll(budget, budgetTF, eurSymbol);

        flowPane.getChildren().addAll(nameHBox, rechtsformHBox, gruendungsjahrHBox, budgetHBox);
        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("Hinzufügen", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        /**
         * => wenn auf "Hinzufügen"-Button gedrückt wird...
         */
        this.setResultConverter(bt -> {
            if(bt == buttonType) {
                try {
                    int gruendungsJahr1 = Integer.parseInt(gruendungsjahrTF.getText());
                    float budget1 = Float.parseFloat(budgetTF.getText());
                    LocalDate now = LocalDate.now();
                    Kontenplan kontenplan = new Kontenplan();
                    ArrayList<Geschaeftsjahr> geschaeftsjahre = new ArrayList<Geschaeftsjahr>();
                    Unternehmen u = new Unternehmen(nameTF.getText(), rechtsformen.valueProperty().get(), gruendungsJahr1, now, geschaeftsjahre, kontenplan, budget1);
                    StringBuilder result = new StringBuilder();
                    int i = 0;
                    String replacement;
                    for(char c : nameTF.getText().toCharArray()) {
                        replacement = String.valueOf(nameTF.getText().charAt(i));
                        result.append(replacement);
                        i++;
                    }
                    u.setDateiname(result.toString());
                    speichernUnternehmenFirst(u);
                    return u;
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

    private void speichernUnternehmenFirst(Unternehmen unternehmen) {
        Path dataDir = Paths.get("data");
        if (!Files.exists(dataDir)) {
            try {
                Files.createDirectory(dataDir);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        String fileName = unternehmen.getName().replaceAll("\\s+", "_") + ".fs";
        Path filePath = dataDir.resolve(fileName);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
            oos.writeObject(unternehmen.getName());
            oos.writeObject(unternehmen.getRechtsform());
            oos.writeObject(unternehmen.getGruendungsjahr());
            oos.writeObject(unternehmen.getAktuellesDatum());
            oos.writeObject(unternehmen.getGeschaeftsjahre());
            oos.writeObject(unternehmen.getKontenplan());
            oos.writeObject(unternehmen.getBudget());
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
