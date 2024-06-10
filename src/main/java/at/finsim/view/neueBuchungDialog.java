package at.finsim.view;

import at.finsim.control.buchungController;
import at.finsim.model.Buchung;
import at.finsim.model.Eintrag;
import at.finsim.model.ModelException;
import at.finsim.model.Unternehmen;
import at.finsim.model.konto.Konto;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class neueBuchungDialog extends Dialog<Buchung> {
    private buchungController ctrl;
    private Unternehmen model;
    private ListView<Konto> soll;
    private ListView<Konto> haben;
    private TextArea infos;
    private unternehmenView view;

    public neueBuchungDialog(Unternehmen unternehmen, unternehmenView unternehmenView) {
        this.model = unternehmen;
        this.view = unternehmenView;
        this.ctrl = new buchungController(this.model, this.view);
        this.soll = new ListView<Konto>();
        Text an = new Text("an (/)");
        this.haben = new ListView<Konto>();
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

        HBox buchungButtons = new HBox();

        Button addSoll = new Button("+ Soll");
        Button removeSoll = new Button("- Soll");
        Button addHaben = new Button("+ Haben");
        Button removeHaben = new Button("- Haben");

        removeSoll.disableProperty().bind(this.soll.getSelectionModel().selectedItemProperty().isNull());
        removeHaben.disableProperty().bind(this.haben.getSelectionModel().selectedItemProperty().isNull());

        addSoll.setOnAction(e -> this.ctrl.addSoll(this.soll));
        removeSoll.setOnAction(e -> {
            Konto konto = this.soll.getSelectionModel().getSelectedItem();

            // Eintrag rauslöschen..?

            this.soll.getItems().remove(konto);
            this.soll.refresh();
        });
        addHaben.setOnAction(e -> this.ctrl.addHaben(this.haben));
        removeHaben.setOnAction(e -> {
            Konto konto = this.haben.getSelectionModel().getSelectedItem();

            // Eintrag rauslöschen..?

            this.haben.getItems().remove(konto);
            this.haben.refresh();
        });

        buchungButtons.getChildren().addAll(addSoll, removeSoll, addHaben, removeHaben);
        buchungButtons.setSpacing(10);
        buchungButtons.setPadding(new Insets(10, 10, 10, 10));

        bp.setTop(buchungButtons);
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
            if(bt == buttonType) {
                if(bilanzCheck() == 0) {
                    Buchung buchung = new eintragenDialog().getResult();
                    this.view.getBuchungen().getItems().add(buchung);
                    this.view.getBuchungen().refresh();
                    return buchung;
                } else {
                    this.view.errorAlert("unausgeglichene Bilanz", "Die Bilanz ist zu " + bilanzCheck() + "unausgeglichen (von Soll-Seite)!");
                }
            }
            return null;
        });
    }

    private float bilanzCheck() {
        float sollSeite = 0;
        float habenSeite = 0;

        for(Konto konto : this.soll.getItems()) {
            for(Eintrag eintrag : konto.getSoll()) {
                sollSeite += eintrag.getBetrag();
            }
        }

        for(Konto konto : this.haben.getItems()) {
            for(Eintrag eintrag : konto.getHaben()) {
                habenSeite += eintrag.getBetrag();
            }
        }

        return sollSeite - habenSeite;
    }
}
