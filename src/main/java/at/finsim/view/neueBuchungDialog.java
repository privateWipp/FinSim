package at.finsim.view;

import at.finsim.model.Buchung;
import javafx.geometry.Orientation;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class neueBuchungDialog extends Dialog<Buchung> {
    public neueBuchungDialog() {
        setTitle("Buchung erstellen");
        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);

        ComboBox<String> comboBoxZeilen = new ComboBox<String>();
        comboBoxZeilen.getItems().add("1 Zeilen");
        comboBoxZeilen.getItems().add("2 Zeilen");
        comboBoxZeilen.getItems().add("3 Zeilen");

        comboBoxZeilen.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                if(newValue.equals("1 Zeilen")) {
                    Text erklaerung = new Text("Syntax: \n" + "Kontonummer, Kontobezeichnung");

                    HBox buchungHBox = new HBox();
                    TextField sollKontonummer = new TextField();
                    TextField sollKontobezeichnung = new TextField();
                    Text an = new Text("/");
                    TextField habenKontonummer = new TextField();
                    TextField habenKontobezeichnung = new TextField();

                    buchungHBox.getChildren().addAll(sollKontonummer, sollKontobezeichnung, an, habenKontonummer, habenKontobezeichnung);

                    flowPane.getChildren().add(buchungHBox);
                } else if(newValue.equals("2 Zeilen")) {
                    Text erklaerung = new Text("Syntax: \n" + "Kontonummer, Kontobezeichnung");

                    // -----------------1. Zeile-----------------
                    HBox zeile1BuchungHBox = new HBox();
                    TextField sollKontonummer1 = new TextField();
                    TextField sollKontobezeichnung1 = new TextField();
                    Text an1 = new Text("/");
                    TextField habenKontonummer1 = new TextField();
                    TextField habenKontobezeichnung1 = new TextField();

                    // -----------------2. Zeile-----------------
                    HBox zeile2BuchungHBox = new HBox();
                    TextField sollKontonummer2 = new TextField();
                    TextField sollKontobezeichnung2 = new TextField();
                    Text an2 = new Text("/");
                    TextField habenKontonummer2 = new TextField();
                    TextField habenKontobezeichnung2 = new TextField();

                    zeile1BuchungHBox.getChildren().addAll(sollKontonummer1, sollKontobezeichnung1, an1, habenKontonummer1, habenKontobezeichnung1);
                    zeile2BuchungHBox.getChildren().addAll(sollKontonummer2, sollKontobezeichnung2, an2, habenKontonummer2, habenKontobezeichnung2);

                    flowPane.getChildren().addAll(zeile1BuchungHBox, zeile2BuchungHBox);
                } else {
                    Text erklaerung = new Text("Syntax: \n" + "Kontonummer, Kontobezeichnung");

                    // -----------------1. Zeile-----------------
                    HBox zeile1BuchungHBox = new HBox();
                    TextField sollKontonummer1 = new TextField();
                    TextField sollKontobezeichnung1 = new TextField();
                    Text an1 = new Text("/");
                    TextField habenKontonummer1 = new TextField();
                    TextField habenKontobezeichnung1 = new TextField();

                    // -----------------2. Zeile-----------------
                    HBox zeile2BuchungHBox = new HBox();
                    TextField sollKontonummer2 = new TextField();
                    TextField sollKontobezeichnung2 = new TextField();
                    Text an2 = new Text("/");
                    TextField habenKontonummer2 = new TextField();
                    TextField habenKontobezeichnung2 = new TextField();

                    // -----------------3. Zeile-----------------
                    HBox zeile3BuchungHBox = new HBox();
                    TextField sollKontonummer3 = new TextField();
                    TextField sollKontobezeichnung3 = new TextField();
                    Text an3 = new Text("/");
                    TextField habenKontonummer3 = new TextField();
                    TextField habenKontobezeichnung3 = new TextField();

                    zeile1BuchungHBox.getChildren().addAll(sollKontonummer1, sollKontobezeichnung1, an1, habenKontonummer1, habenKontobezeichnung1);
                    zeile2BuchungHBox.getChildren().addAll(sollKontonummer2, sollKontobezeichnung2, an2, habenKontonummer2, habenKontobezeichnung2);
                    zeile3BuchungHBox.getChildren().addAll(sollKontonummer3, sollKontobezeichnung3, an3, habenKontonummer3, habenKontobezeichnung3);

                    flowPane.getChildren().addAll(zeile1BuchungHBox, zeile2BuchungHBox, zeile3BuchungHBox);
                }
            }
        });

        flowPane.getChildren().addAll(comboBoxZeilen);
    }
}
