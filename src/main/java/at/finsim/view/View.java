package at.finsim.view;

import at.finsim.control.Controller;
import at.finsim.model.Unternehmen;
import javafx.beans.binding.Bindings;
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
    private final Controller ctrl;

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
        this.unternehmenListView = new ListView<>();

        HBox unternehmenTop = new HBox();
        TextField search = new TextField();
        search.setPromptText("Suche");
        Button neuesUnternehmen = new Button("neues Unternehmen");
        Button runUnternehmen = new Button("Unternehmen öffnen");
        Button openUnternehmen = new Button("Unternehmen importieren");
        Button deleteUnternehmen = new Button("Unternehmen entfernen");
        unternehmenTop.getChildren().addAll(search, neuesUnternehmen, runUnternehmen, openUnternehmen, deleteUnternehmen);
        unternehmen.setTop(unternehmenTop);
        unternehmen.setCenter(this.unternehmenListView);

        neuesUnternehmen.setOnAction(e -> this.ctrl.neuesUnternehmen());
        runUnternehmen.disableProperty().bind(Bindings.isEmpty(this.unternehmenListView.getSelectionModel().getSelectedItems()));
        runUnternehmen.setOnAction(e -> {
            this.ctrl.runUnternehmen(this.unternehmenListView.getSelectionModel().getSelectedItem());
            // jetzige Stage closen (idk wie)
        });

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

        Text ueberUnsText = new Text("FinSim - ein buchhalterisches Programm, entwickelt von: \n" +
                "Jonas Mader & Nikodem Marek");

        ueberUns.setOnAction(e -> setCenter(ueberUnsText));

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
