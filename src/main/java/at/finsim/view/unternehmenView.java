package at.finsim.view;

import at.finsim.model.Unternehmen;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import at.finsim.control.unternehmenController;
import javafx.scene.text.Text;

public class unternehmenView extends TabPane {
    private Unternehmen model;
    private unternehmenController ctrl;

    public unternehmenView(Unternehmen unternehmen) {
        this.ctrl = new unternehmenController(this, model);
        this.model = unternehmen;

        initGUI();
    }

    private void initGUI() {
        // Dashboard: Main Tab
        BorderPane dashboard = new BorderPane();

        VBox leftPane = new VBox();
        leftPane.setStyle("-fx-background-color: lightgray;");

        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.setStyle("-fx-background-color: lightgray;");
        toolBar.setPrefHeight(Integer.MAX_VALUE);

        Button speichernButton = new Button("Speichern");
        Button ladenButton = new Button("Laden");

        speichernButton.setOnAction(e -> this.ctrl.unternehmenSpeichern());
        ladenButton.setOnAction(e -> this.ctrl.unternehmenLaden());

        toolBar.getItems().addAll(speichernButton, ladenButton);
        leftPane.getChildren().addAll(toolBar);

        dashboard.setLeft(leftPane);

        // -------------------------------------------------------------------

        // Grund-Infos: 1. Tab
        GridPane grundInfosGP = new GridPane();

        // -------------------------------------------------------------------

        GridPane gp = new GridPane();

        HBox grundInfosHBox = new HBox();
        Text grundInfosText = new Text(this.model.getName() + "\n" +
                this.model.getGruendungsjahr() + "\n" +
                this.model.getBudget());
        grundInfosHBox.getChildren().addAll(grundInfosText);

        HBox statistikenHBox = new HBox();
        statistikenHBox.getChildren().addAll();

        HBox aktuellesGeschaeftsjahrHBox = new HBox();
        Text aktuellesGeschaeftsjahrText = new Text("Geschäftsjahre" + "\n" +
                "(akt. " + Integer.toString(this.model.getAktuellesGeschaeftsjahr()) + ")");
        aktuellesGeschaeftsjahrHBox.getChildren().addAll(aktuellesGeschaeftsjahrText);

        HBox kontenplanHBox = new HBox();
        Button kontenplanButton = new Button("Kontenplan");
        kontenplanButton.setOnAction(e -> this.ctrl.showKontenplan(this.model));
        kontenplanHBox.getChildren().addAll(kontenplanButton);

        HBox bilanzHBox = new HBox();
        Text bilanzText = new Text("akt. Bilanz");
        bilanzHBox.getChildren().addAll(bilanzText);

        grundInfosHBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });

        gp.add(grundInfosHBox, 0, 0, 1, 1);
        gp.add(statistikenHBox, 1, 0, 1, 1);
        gp.add(aktuellesGeschaeftsjahrHBox, 2, 0, 1, 1);
        gp.add(kontenplanHBox, 0,1, 1, 1);
        gp.add(bilanzHBox, 1, 1, 1, 1);

        dashboard.setCenter(gp);

        // -------------------------------------------------------------------------------------------------

        Tab dashboardTab = new Tab("Dashboard", dashboard);
        dashboardTab.setClosable(false);
        this.getTabs().addAll(dashboardTab);


    }
}
