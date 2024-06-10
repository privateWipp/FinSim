package at.finsim.view;

import at.finsim.model.Geschaeftsjahr;
import at.finsim.model.Unternehmen;
import at.finsim.model.konto.Konto;
import javafx.css.converter.StringConverter;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import at.finsim.control.unternehmenController;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class    unternehmenView extends TabPane {
    private Unternehmen model;
    private unternehmenController ctrl;
    private Text grundInfosText, geschaeftsjahreText, kontenplanText;
    private GridPane gp;
    private FlowPane kontenplanFP;
    private Button addKonto;
    private TreeView<String> tree;
    private TreeItem<String> rootItem, klasseItem;
    private TextArea kontenplanInfo;

    public unternehmenView(Unternehmen unternehmen) {
        this.model = unternehmen;
        this.ctrl = new unternehmenController(this, model);

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
        Button neueBuchung = new Button("neue Buchung..");

        speichernButton.setOnAction(e -> this.ctrl.unternehmenSpeichern());
        neueBuchung.setOnAction(e -> this.ctrl.neueBuchung());

        toolBar.getItems().addAll(speichernButton, neueBuchung);
        leftPane.getChildren().addAll(toolBar);

        dashboard.setLeft(leftPane);

        // -------------------------------------------------------------------

        // Grund-Infos: 1. Tab
        FlowPane grundInfosFP = new FlowPane();
        grundInfosFP.setOrientation(Orientation.VERTICAL);

        Label uebersicht = new Label("Gesamtübersicht der wichtigsten Informationen");
        uebersicht.setStyle("-fx-font-size: 24");

        VBox allgemein = new VBox();
        Label allgemeines = new Label("Allgemeines: \n");
        Label unternehmenNameGross = new Label("Firmenname: "+ this.model.getName());
        Label unternehmenRechtsform = new Label("Rechtsform: "+this.model.getRechtsform());
        Label unternehmenGruendungsJahr = new Label("Gründungsjahr: "+this.model.getGruendungsjahr());
        Label bestehtSeit = new Label("Unternehmen besteht seit: " + (LocalDate.now().getYear() - this.model.getGruendungsjahr()) + " Jahren");
        allgemein.getChildren().addAll(allgemeines,unternehmenNameGross,unternehmenRechtsform,unternehmenGruendungsJahr,bestehtSeit);

        VBox diagramm = new VBox();
        diagramm.getChildren().add(new Label("Die wichtigsten KPIs im Vorjahr"));

        if (model.getGeschaeftsjahre().size()>1) {
            // Beispiel für ein Tortendiagramm für den Überblick
            PieChart pieChart = new PieChart();
            pieChart.getData().add(new PieChart.Data("Umsatz", model.getGeschaeftsjahre().get(model.getGeschaeftsjahre().size() - 1).getSchlussbilanz().getUmsatz()));
            pieChart.getData().add(new PieChart.Data("Kosten", model.getGeschaeftsjahre().get(model.getGeschaeftsjahre().size() - 1).getSchlussbilanz().getKosten()));
            pieChart.getData().add(new PieChart.Data("Gewinn", model.getGeschaeftsjahre().get(model.getGeschaeftsjahre().size() - 1).getSchlussbilanz().getGewinn()));
            diagramm.getChildren().add(pieChart);
        } else {
            Label pieChartPlaceholder = new Label("Kein abgeschlossenes Geschäftsjahr vorhanden.");
            diagramm.getChildren().add(pieChartPlaceholder);
        }

        grundInfosFP.getChildren().addAll(uebersicht,allgemein,diagramm);

        grundInfosFP.setVgap(10);
        grundInfosFP.setHgap(10);

        // Statistiken: 2. Tab
        GridPane statistikenGP = new GridPane();

        // Geschäftsjahre: 3. Tab (Auflistung aller Geschäftsjahre, evtl. Bilanz usw.)
        FlowPane geschaeftsjahre = new FlowPane();

        VBox AdG = new VBox();
        AdG.getChildren().add(new Label("Analyse der Geschäftsjahre"));

        // Beispiel für ein Liniendiagramm für die Geschäftsjahre
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Jahr");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Betrag");


        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        for (Geschaeftsjahr geschaeftsjahr: model.getGeschaeftsjahre()) {
            if (!geschaeftsjahr.isAbgeschlossen()) continue;

            XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
            dataSeries.setName(String.valueOf(geschaeftsjahr.getJahr()));

            dataSeries.getData().add(new XYChart.Data<>("Umsatz", geschaeftsjahr.getSchlussbilanz().getUmsatz()));

            barChart.getData().add(dataSeries);
        }

        AdG.getChildren().add(barChart);

        geschaeftsjahre.getChildren().addAll(AdG);

        // Kontenplan: 4. Tab (Auflistung aller eingetragenen Konten inkl. Kontenklasse)
        this.kontenplanFP = new FlowPane();
        this.kontenplanFP.setOrientation(Orientation.VERTICAL);

        this.addKonto = new Button("Konto hinzufügen");
        this.addKonto.setOnAction(e -> this.ctrl.addKonto());
        this.kontenplanText = new Text("Kontenplan:");

        this.rootItem = new TreeItem<String>("Kontenplan");
        this.rootItem.setExpanded(true);

        for (Integer kontoklasse : this.model.getKontenplan().getKonten().keySet()) {
            TreeItem<String> klasseItem = new TreeItem<String>("Kontoklasse " + kontoklasse);
            this.rootItem.getChildren().add(klasseItem);
        }

        this.tree = new TreeView<String>(rootItem);

        this.kontenplanInfo = new TextArea();
        this.kontenplanInfo.setEditable(false);
        updateKontenplanInfo();

        this.kontenplanFP.getChildren().addAll(this.addKonto, this.kontenplanText, this.tree);

        updateKontenplan();

        // Bilanz: 5. Tab (Bilanz vom U.)
        GridPane bilanzGP = new GridPane();

        // -------------------------------------------------------------------

        this.gp = new GridPane();

        HBox grundInfosHBox = new HBox();
        this.grundInfosText = new Text(this.model.getName() + "\n" +
                this.model.getRechtsform() + "\n" +
                this.model.getGruendungsjahr() + "\n" +
                this.model.getBudget());
        grundInfosHBox.getChildren().addAll(this.grundInfosText);

        HBox statistikenHBox = new HBox();
        statistikenHBox.getChildren().addAll();

        HBox geschaeftsjahreHBox = new HBox();
        this.geschaeftsjahreText = new Text("Geschäftsjahre" + "\n" +
                "(akt. " + Integer.toString(this.model.getAktuellesGeschaeftsjahr()) + ")");
        geschaeftsjahreHBox.getChildren().addAll(this.geschaeftsjahreText);

        Button kontenplanButton = new Button("Kontenplan");

        HBox bilanzHBox = new HBox();
        Text bilanzText = new Text("akt. Bilanz");
        bilanzHBox.getChildren().addAll(bilanzText);

        grundInfosHBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!eTab("Grund-Infos")) {
                    Tab grundInfosTab = new Tab("Grund-Infos", grundInfosFP);
                    getTabs().addAll(grundInfosTab);
                }
            }
        });

        statistikenHBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!eTab("Statistiken")) {
                    Tab statistikenTab = new Tab("Statistiken", statistikenGP);
                    getTabs().addAll(statistikenTab);
                }
            }
        });

        geschaeftsjahreHBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!eTab("Geschäftsjahre")) {
                    Tab geschaeftsjahreTab = new Tab("Geschäftsjahre", geschaeftsjahre);
                    getTabs().addAll(geschaeftsjahreTab);
                }
            }
        });

        kontenplanButton.setOnAction(e -> {
            if (!eTab("Kontenplan")) {
                Tab kontenplanTab = new Tab("Kontenplan", kontenplanFP);
                getTabs().addAll(kontenplanTab);
                updateKontenplan();
            }
        });

        bilanzHBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!eTab("Bilanz")) {
                    Tab bilanzTab = new Tab("Bilanz", bilanzGP);
                    getTabs().addAll(bilanzTab);
                }
            }
        });

        this.gp.add(grundInfosHBox, 0, 0, 1, 1);
        this.gp.add(statistikenHBox, 1, 0, 1, 1);
        this.gp.add(geschaeftsjahreHBox, 2, 0, 1, 1);
        this.gp.add(kontenplanButton, 0, 1, 1, 1);
        this.gp.add(bilanzHBox, 20, 20, 1, 1);

        dashboard.setCenter(this.gp);

        // -------------------------------------------------------------------------------------------------

        Tab dashboardTab = new Tab("Dashboard", dashboard);
        dashboardTab.setClosable(false);
        this.getTabs().addAll(dashboardTab);
    }

    private boolean eTab(String name) {
        for (Tab tab : this.getTabs()) {
            if (tab.getText().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void updateKontenplan() {
        for (TreeItem<String> klasseItem : this.rootItem.getChildren()) {
            klasseItem.getChildren().clear();
        }

        for (Integer kontoklasse : this.model.getKontenplan().getKonten().keySet()) {
            TreeItem<String> klasseItem = this.rootItem.getChildren().get(kontoklasse);
            for (Konto konto : this.model.getKontenplan().getKonten().get(kontoklasse)) {
                TreeItem<String> kontoItem = new TreeItem<String>(konto.getKontonummer() + " : " + konto.getBezeichnung());
                klasseItem.getChildren().add(kontoItem);
            }
        }
    }

    public void updateKontenplanInfo() {
        this.tree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.kontenplanInfo.setText("");
            } else {
                this.kontenplanInfo.setText("");
            }
        });
    }

    public Text getGrundInfosText() {
        return this.grundInfosText;
    }

    public Text getGeschaeftsjahreText() {
        return this.geschaeftsjahreText;
    }

    public void errorAlert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
