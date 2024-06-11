package at.finsim.view;

import at.finsim.control.buchungController;
import at.finsim.model.Buchung;
import at.finsim.model.Eintrag;
import at.finsim.model.ModelException;
import at.finsim.model.Unternehmen;
import at.finsim.model.konto.Konto;
import at.finsim.model.konto.KontoBetrag;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;

public class neueBuchungDialog extends Dialog<Buchung> {
    private buchungController ctrl;
    private Unternehmen model;
    private ListView<KontoBetrag> soll;
    private ListView<KontoBetrag> haben;
    private TextArea infos;
    private unternehmenView view;
    private float betrag;

    public neueBuchungDialog(Unternehmen unternehmen, unternehmenView unternehmenView) {
        this.model = unternehmen;
        this.view = unternehmenView;
        this.ctrl = new buchungController(this.model, this.view, this);
        this.betrag = 0;
        this.soll = new ListView<KontoBetrag>();
        Text an = new Text("an (/)");
        this.haben = new ListView<KontoBetrag>();
        this.infos = new TextArea();
        this.infos.setEditable(false);
        this.infos.setText("links: Soll\n" +
                "an\n" +
                "rechts: Haben\n" +
                "------------------------------------------------\n" +
                "Buchungen werden NICHT überprüft!\n" +
                "Bitte nur SINNVOLLE Buchungen durchführen und eintragen.");

        setTitle("neue Buchung erstellen..");

        BorderPane bp = new BorderPane(); // Left, Center, Right

        VBox topBox = new VBox();
        HBox buchungButtons = new HBox();

        Button addSoll = new Button("+ Soll");
        Button removeSoll = new Button("- Soll");
        Button addHaben = new Button("+ Haben");
        Button removeHaben = new Button("- Haben");

        removeSoll.disableProperty().bind(this.soll.getSelectionModel().selectedItemProperty().isNull());
        removeHaben.disableProperty().bind(this.haben.getSelectionModel().selectedItemProperty().isNull());

        addSoll.setOnAction(e -> this.ctrl.addSoll());
        removeSoll.setOnAction(e -> {
            KontoBetrag kontoBetrag = this.soll.getSelectionModel().getSelectedItem();

            // Eintrag rauslöschen..?

            this.soll.getItems().remove(kontoBetrag);
            this.soll.refresh();
        });
        addHaben.setOnAction(e -> this.ctrl.addHaben());
        removeHaben.setOnAction(e -> {
            KontoBetrag kontoBetrag = this.haben.getSelectionModel().getSelectedItem();

            // Eintrag rauslöschen..?

            this.haben.getItems().remove(kontoBetrag);
            this.haben.refresh();
        });

        buchungButtons.getChildren().addAll(addSoll, removeSoll, addHaben, removeHaben);
        buchungButtons.setSpacing(10);
        buchungButtons.setPadding(new Insets(10, 10, 10, 10));

        // -----------------Bezeichnung-----------------
        HBox bezeichnungHBox = new HBox();
        Label bezeichnung = new Label("Bezeichnung:");
        TextField bezeichnungTF = new TextField();
        bezeichnungTF.setPromptText("z.B.: erste Buchung - [XX.XX.XXXX], ...");
        bezeichnungHBox.setPadding(new Insets(0, 0, 10, 10));
        bezeichnungHBox.setSpacing(10);
        bezeichnungHBox.getChildren().addAll(bezeichnung, bezeichnungTF);

        // -----------------Beleg-----------------
        HBox belegHBox = new HBox();
        Label beleg = new Label("Beleg:");
        TextField belegTF = new TextField();
        belegTF.setPromptText("z.B.: KB, Kontoauszug, Scheck, Postbeleg, ...");
        belegHBox.setPadding(new Insets(0, 0, 10, 10));
        belegHBox.setSpacing(10);
        belegHBox.getChildren().addAll(beleg, belegTF);

        topBox.getChildren().addAll(bezeichnungHBox, belegHBox, buchungButtons);

        bp.setTop(topBox);
        bp.setLeft(this.soll);
        bp.setCenter(an);
        bp.setRight(this.haben);
        bp.setBottom(this.infos);

        getDialogPane().setContent(bp);

        ButtonType buttonType = new ButtonType("Eintragen", ButtonBar.ButtonData.APPLY);
        getDialogPane().getButtonTypes().add(buttonType);

        /**
         * => wenn man auf "Eintragen"-Button gedrückt wird...
         */
        this.setResultConverter(bt -> {
            if (bt == buttonType) {
                try {
                    String bezeichnungTFInput = bezeichnungTF.getText();
                    String belegTFInput = belegTF.getText();
                    if (bilanzCheck() == 0) {
                        return new Buchung(bezeichnungTFInput, belegTFInput, LocalDate.now(), new ArrayList<KontoBetrag>(this.soll.getItems()), new ArrayList<KontoBetrag>(this.haben.getItems()));
                    } else {
                        this.view.errorAlert("unausgeglichene Bilanz", "Die Bilanz ist zu " + bilanzCheck() + "€ unausgeglichen (von Soll-Seite)!");
                    }
                } catch (ModelException me) {
                    this.view.errorAlert("ungültige Buchung", "Es trat ein Fehler beim Erstellen der Buchung auf!");
                }
            }
            return null;
        });
    }

    private float bilanzCheck() {
        float sollSeite = 0;
        float habenSeite = 0;

        for (KontoBetrag kontoBetrag : this.soll.getItems()) {
            sollSeite += kontoBetrag.getBetrag();
        }

        for (KontoBetrag kontoBetrag : this.haben.getItems()) {
            habenSeite += kontoBetrag.getBetrag();
        }

        return sollSeite - habenSeite;
    }

    public ListView<KontoBetrag> getSoll() {
        return this.soll;
    }

    public ListView<KontoBetrag> getHaben() {
        return this.haben;
    }

    public float getBetrag() {
        return this.betrag;
    }

    public void addBetrag(float betrag) {
        this.betrag += betrag;
    }

    public void subtractBetrag(float betrag) {
        this.betrag -= betrag;
    }
}
