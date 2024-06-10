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

import java.io.*;
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
    private String selectedRechtsform;

    public addUnternehmenDialog() {
        setTitle("neues Unternehmen");
        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setPrefSize(500, 300);

        // -----------------NAME-----------------
        HBox nameHBox = new HBox();
        Label name = new Label("Name des Unternehmens:");
        TextField nameTF = new TextField();
        nameTF.setPromptText("z.B.: Apple, Microsoft GmbH, ...");
        nameHBox.setPadding(new Insets(0, 0, 10, 10));
        nameHBox.setSpacing(10);
        nameHBox.getChildren().addAll(name, nameTF);

        flowPane.getChildren().addFirst(nameHBox);

        // -----------------RECHTSFORM-----------------
        HBox rechtsformHBox = new HBox();
        Label rechtsform = new Label("Rechtsform:");

        ComboBox<String> rechtsformen = new ComboBox<String>();
        rechtsformen.getItems().addAll("Einzelunternehmen", "Personengesellschaft", "Kapitalgesellschaft");

        rechtsformHBox.setPadding(new Insets(0, 0, 10, 10));
        rechtsformHBox.setSpacing(10);
        rechtsformHBox.getChildren().addAll(rechtsform, rechtsformen);

        flowPane.getChildren().add(1, rechtsformHBox);

        // -----------------RECHTSFORM 2-----------------
        HBox rechtsform2HBox = new HBox();
        Label rechtsform2 = new Label("Unterscheidung:");
        rechtsform2.setVisible(false);
        rechtsform2HBox.getChildren().add(rechtsform2);

        ComboBox<String> rechtsformenPersonenG = new ComboBox<String>();
        rechtsformenPersonenG.getItems().addAll("offene Gesellschaft", "Kommanditgesellschaft");
        rechtsformenPersonenG.setVisible(false);

        ComboBox<String> rechtsformenKapitalG = new ComboBox<String>();
        rechtsformenKapitalG.getItems().addAll("Gesellschaft mit beschränkter Haftung (GmbH)", "Aktiengesellschaft (AG)");
        rechtsformenKapitalG.setVisible(false);

        rechtsformen.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Einzelunternehmen")) {
                this.selectedRechtsform = newValue;
                flowPane.getChildren().remove(rechtsform2HBox);
                rechtsform2.setVisible(false);
                rechtsformenPersonenG.setVisible(false);
                rechtsformenKapitalG.setVisible(false);
            } else if (newValue.equals("Personengesellschaft")) {
                rechtsform2HBox.getChildren().removeAll(rechtsformenPersonenG, rechtsformenKapitalG);
                rechtsform2.setVisible(true);
                rechtsformenKapitalG.setVisible(false);
                rechtsformenPersonenG.setVisible(true);
                rechtsform2HBox.getChildren().add(rechtsformenPersonenG);
                rechtsformenPersonenG.getSelectionModel().selectedItemProperty().addListener((obs, oldV1, newV1) -> {
                    this.selectedRechtsform = newV1;
                });
                if (!flowPane.getChildren().contains(rechtsform2HBox)) {
                    flowPane.getChildren().add(2, rechtsform2HBox);
                }
            } else if (newValue.equals("Kapitalgesellschaft")) {
                rechtsform2HBox.getChildren().removeAll(rechtsformenKapitalG, rechtsformenPersonenG);
                rechtsform2.setVisible(true);
                rechtsformenPersonenG.setVisible(false);
                rechtsformenKapitalG.setVisible(true);
                rechtsform2HBox.getChildren().add(rechtsformenKapitalG);
                rechtsformenKapitalG.getSelectionModel().selectedItemProperty().addListener((obs, oldV1, newV1) -> {
                    this.selectedRechtsform = newV1;
                });
                if (!flowPane.getChildren().contains(rechtsform2HBox)) {
                    flowPane.getChildren().add(2, rechtsform2HBox);
                }
            }
        });

        rechtsform2HBox.setPadding(new Insets(0, 0, 10, 10));
        rechtsform2HBox.setSpacing(10);

        // -----------------GRÜNDUNGSJAHR-----------------
        HBox gruendungsjahrHBox = new HBox();
        Label gruendungsjahr = new Label("Gründungsjahr:");
        TextField gruendungsjahrTF = new TextField();
        gruendungsjahrTF.setPromptText("z.B.: 2017, 2003, 2024, ...");
        gruendungsjahrHBox.setPadding(new Insets(0, 0, 10, 10));
        gruendungsjahrHBox.setSpacing(10);
        gruendungsjahrHBox.getChildren().addAll(gruendungsjahr, gruendungsjahrTF);

        rechtsformen.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!flowPane.getChildren().contains(gruendungsjahrHBox)) {
                if (newValue.equals("Einzelunternehmen")) {
                    flowPane.getChildren().add(2, gruendungsjahrHBox);
                } else {
                    flowPane.getChildren().add(3, gruendungsjahrHBox);
                }
            }
        });

        // -----------------BUDGET-----------------
        HBox budgetHBox = new HBox();
        Label budget = new Label("Budget:");
        TextField budgetTF = new TextField();
        budgetTF.setPromptText("z.B.: 450.000");
        Label eurSymbol = new Label("€");
        budgetHBox.setPadding(new Insets(0, 0, 10, 10));
        budgetHBox.setSpacing(10);
        budgetHBox.getChildren().addAll(budget, budgetTF, eurSymbol);

        rechtsformen.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!flowPane.getChildren().contains(budgetHBox)) {
                if (newValue.equals("Einzelunternehmen")) {
                    flowPane.getChildren().add(3, budgetHBox);
                } else {
                    flowPane.getChildren().add(4, budgetHBox);
                }
            }
        });

        getDialogPane().setContent(flowPane);

        ButtonType buttonType = new ButtonType("Hinzufügen", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        /**
         * => wenn auf "Hinzufügen"-Button gedrückt wird...
         */
        this.setResultConverter(bt -> {
            if (bt == buttonType) {
                try {
                    int gruendungsJahr1 = Integer.parseInt(gruendungsjahrTF.getText());
                    float budget1 = Float.parseFloat(budgetTF.getText());
                    ArrayList<Geschaeftsjahr> geschaeftsjahre = new ArrayList<Geschaeftsjahr>();
                    Unternehmen u = new Unternehmen(nameTF.getText(), getSelectedRechtsform(), gruendungsJahr1, LocalDate.now(), geschaeftsjahre, new Kontenplan(), budget1);
                    if (u.getRechtsform().equals("Gesellschaft mit beschränkter Haftung (GmbH)") || u.getRechtsform().equals("Aktiengesellschaft (AG)")) {
                        if (u.getBudget() < 10000) {
                            showWarning("ACHTUNG: \n" + "Das GmbH-Mindeststammkapital beträgt 10.000€!");
                        }
                    }
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
        Path dataDirectory = Paths.get("data");
        Path filePath = dataDirectory.resolve(unternehmen.getDateiname() + ".fs");

        if (Files.exists(filePath)) {
            System.out.println("Datei existiert bereits: " + filePath);
        } else {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
                oos.writeObject(unternehmen.getName());
                oos.writeObject(unternehmen.getRechtsform());
                oos.writeObject(unternehmen.getGruendungsjahr());
                oos.writeObject(unternehmen.getAktuellesDatum());
                oos.writeObject(unternehmen.getGeschaeftsjahre());
                oos.writeObject(unternehmen.getKontenplan());
                oos.writeObject(unternehmen.getBudget());
                oos.writeObject(unternehmen.getDateiname());
                oos.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private String getSelectedRechtsform() {
        return this.selectedRechtsform;
    }

    private void showWarning(String text) {
        Alert warningAlert = new Alert(Alert.AlertType.WARNING);
        warningAlert.setTitle("Warnung!");
        warningAlert.setHeaderText("Budget");
        warningAlert.setContentText(text);
        warningAlert.showAndWait();
    }
}