package at.finsim.view;

import at.finsim.control.Controller;
import at.finsim.model.Buchung;
import at.finsim.model.Unternehmen;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Die erste View Klasse für das GUI des Hauptfensters
 *
 * @author Jonas & Nikodem
 * @version 0.1
 *
 */
public class View extends BorderPane {
    private Controller ctrl;

    public View() {
        //MVC-Referenzen
        this.ctrl = new Controller(this);

        //--------------
        initGUI();
    }

    /**
     * Eine Liste mit allen Unternehmen(s Projekten)
     */
    private ListView<Unternehmen> unternehmenListView;

    private void initGUI() {
        // Center: Fenster
        StackPane start = new StackPane();
        Text text = new Text("Herzlich Willkommen zu FinSim!\n" +
                "Nutzen Sie die Toolbar (zu Ihrer linken), um zu STARTEN!");
        start.getChildren().add(text);
        setCenter(start);

        // ----------------------------------------------------------------------------------------

        // Unternehmen Listview
        BorderPane unternehmen = new BorderPane();
        this.unternehmenListView = new ListView<Unternehmen>();

        HBox unternehmenTop = new HBox();
        TextField search = new TextField();
        search.setPromptText("Suche");
        Button neuesUnternehmen = new Button("neues Unternehmen");
        Button openUnternehmen = new Button("Unternehmen importieren");
        Button deleteUnternehmen = new Button("Unternehmen entfernen");
        unternehmenTop.getChildren().addAll(search, neuesUnternehmen, openUnternehmen, deleteUnternehmen);
        unternehmen.setTop(unternehmenTop);
        unternehmen.setCenter(this.unternehmenListView);

        neuesUnternehmen.setOnAction(e -> this.ctrl.neuesUnternehmen());
        openUnternehmen.setOnAction(e -> this.ctrl.unternehmenOeffnen(this));

        unternehmenTop.setSpacing(10);
        unternehmen.setPadding(new Insets(10, 20, 10 ,20));

        // ----------------------------------------------------------------------------------------

        //Left:L Tool-Bar
        VBox leftPane = new VBox();
        leftPane.setStyle("-fx-background-color: lightgray;");

        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.setStyle("-fx-background-color: lightgray;");
        toolBar.setPrefHeight(Integer.MAX_VALUE);

        Button unternehmenButton = new Button("Unternehmen");
        unternehmenButton.setOnAction(e -> setCenter(unternehmen));
        Button ueberUns = new Button("Über Uns");

        toolBar.getItems().addAll(unternehmenButton, ueberUns);
        leftPane.getChildren().addAll(toolBar);

        setLeft(leftPane);

        // ----------------------------------------------------------------------------------------


    }

    public void errorAlert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public ListView<Unternehmen> getUnternehmenListView() {
        return this.unternehmenListView;
    }
}
