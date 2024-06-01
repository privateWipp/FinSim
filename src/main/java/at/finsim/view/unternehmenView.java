package at.finsim.view;

import at.finsim.control.Controller;
import at.finsim.model.Geschaeftsjahr;
import at.finsim.model.Unternehmen;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class unternehmenView extends BorderPane {
    private Stage stage;
    private Unternehmen unternehmen;
    private Controller ctrl;

    public unternehmenView(Controller controller, Unternehmen unternehmen) {
        this.stage = new Stage();
        this.ctrl = controller;
        this.unternehmen = unternehmen;

        initGUI();
    }

    private void initGUI() {
        VBox leftPane = new VBox();
        leftPane.setStyle("-fx-background-color: lightgray;");

        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.setStyle("-fx-background-color: lightgray;");
        toolBar.setPrefHeight(Integer.MAX_VALUE);

        Button unternehmenButton = new Button("/");
        // unternehmenButton.setOnAction(e -> setCenter(unternehmen));

        toolBar.getItems().addAll(unternehmenButton);
        leftPane.getChildren().addAll(toolBar);

        setLeft(leftPane);

        // -------------------------------------------------------------------

        TilePane tilePane = new TilePane();

        Button grundInfos = new Button(this.unternehmen.getName() + ",\n" +
                "Gründungsjahr: " + this.unternehmen.getGruendungsjahr() + ",\n" +
                this.unternehmen.getBudget());
        grundInfos.setOnAction(e -> this.ctrl.grundInfos(unternehmen));

        Button statistiken = new Button("Statistiken PLATZHALTER");
        statistiken.setOnAction(e -> this.ctrl.showStatistikenUnternehmen(unternehmen));

        Label aktuellesGeschaeftsjahr = new Label("aktuelles Geschäftsjahr:" + "\n" +
                getAktuellesGeschaeftsjahr());

        Label alteGeschaeftsjahre = new Label("alte Geschäftsjahre:" + "\n" +
                getAlteGeschaeftsjahre());

        Button kontenplanButton = new Button("Kontenplan");
        kontenplanButton.setOnAction(e -> this.ctrl.showKontenplan(this.unternehmen));

        Button aktuelleBilanz = new Button("aktuelle Bilanz");
        aktuelleBilanz.setOnAction(e -> this.ctrl.showAktuelleBilanz(unternehmen));

        tilePane.getChildren().addAll(grundInfos, aktuellesGeschaeftsjahr, alteGeschaeftsjahre, kontenplanButton, aktuelleBilanz);

        setCenter(tilePane);

        // -------------------------------------------------------------------

        this.stage.setTitle(this.unternehmen.getName() + " : Unternehmen");
        this.stage.setResizable(false);
        Scene scene = new Scene(this, 1280, 720);
        this.stage.setScene(scene);
        this.stage.show();
    }

    private int getAktuellesGeschaeftsjahr() {
        int aktuellesGeschaeftsjahr = 0;
        int aktuellesJahr = LocalDate.now().getYear();
        int kleinsteDifferenz = Integer.MIN_VALUE;

        for(Geschaeftsjahr tmp : this.unternehmen.getGeschaeftsjahre()) {
            if(tmp.getJahr() > kleinsteDifferenz) {
                kleinsteDifferenz = tmp.getJahr();
                aktuellesGeschaeftsjahr = tmp.getJahr();
            }
        }

        return aktuellesGeschaeftsjahr;
    }

    private String getAlteGeschaeftsjahre() {
        StringBuilder rueckgabe = new StringBuilder();
        int naechtesGeschaeftsjahr = getAktuellesGeschaeftsjahr();

        for (Geschaeftsjahr gj : this.unternehmen.getGeschaeftsjahre()) {
            if (!(gj.getJahr() == naechtesGeschaeftsjahr)) {
                rueckgabe.append(gj.getJahr()).append("\n");
            }
        }

        return rueckgabe.toString();
    }
}
