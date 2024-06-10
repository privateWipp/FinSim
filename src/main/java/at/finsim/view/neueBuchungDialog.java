package at.finsim.view;

import at.finsim.control.buchungController;
import at.finsim.model.Buchung;
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
    private Unternehmen unternehmen;
    private ListView<Konto> soll;
    private ListView<Konto> haben;
    private TextArea infos;

    public neueBuchungDialog(Unternehmen unternehmen) {
        this.unternehmen = unternehmen;
        this.ctrl = new buchungController(unternehmen);
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

        addSoll.setOnAction(e -> this.ctrl.addSoll());

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
                /*try {

                } catch (ModelException me) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Fehler");
                    alert.setHeaderText("Buchung erstellen");
                    alert.setContentText(me.getMessage());
                    alert.showAndWait();
                }**/
            }
            return null;
        });
    }
}
