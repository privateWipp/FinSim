package at.finsim.control;

import at.finsim.model.Eintrag;
import at.finsim.model.ModelException;
import at.finsim.model.Unternehmen;
import at.finsim.model.konto.KontoBetrag;
import at.finsim.view.addHabenDialog;
import at.finsim.view.addSollDialog;
import at.finsim.view.editBuchungDialog;
import javafx.scene.control.ListView;
import at.finsim.view.unternehmenView;

import java.time.LocalDate;
import java.util.Optional;

public class editBuchungController {
    private Unternehmen model;
    private unternehmenView view;
    private editBuchungDialog editBuchungDialog;

    public editBuchungController(Unternehmen unternehmen, unternehmenView unternehmenView, editBuchungDialog editBuchungDialog) {
        this.model = unternehmen;
        this.view = unternehmenView;
        this.editBuchungDialog = editBuchungDialog;
    }

    public void addSoll() {
        addSollDialog addSollDialog = new addSollDialog(this.model);
        Optional<KontoBetrag> k = addSollDialog.showAndWait();

        k.ifPresent(konto -> {
            try {
                Eintrag eintrag = new Eintrag(LocalDate.now(), konto.getKonto().getBezeichnung(), konto.getBetrag());
                this.editBuchungDialog.addBetrag(konto.getBetrag());
                //konto.getKonto().getSoll().add(eintrag);
                KontoBetrag sollKonto = new KontoBetrag(konto.getKonto(), konto.getBetrag());
                this.editBuchungDialog.getSoll().getItems().add(sollKonto);
                this.editBuchungDialog.getSoll().refresh();
            } catch (ModelException me) {
                this.view.errorAlert("+ Soll-Konto erstellen", "Das angegebene +Soll-Konto konnte nicht erstellt werden!");
            }
        });
    }

    public void addHaben() {
        addHabenDialog addHabenDialog = new addHabenDialog(this.model);
        Optional<KontoBetrag> k = addHabenDialog.showAndWait();

        k.ifPresent(konto -> {
            try {
                Eintrag eintrag = new Eintrag(LocalDate.now(), konto.getKonto().getBezeichnung(), konto.getBetrag());
                this.editBuchungDialog.addBetrag(konto.getBetrag());
                //konto.getKonto().getHaben().add(eintrag);
                KontoBetrag habenKonto = new KontoBetrag(konto.getKonto(), konto.getBetrag());
                this.editBuchungDialog.getHaben().getItems().add(habenKonto);
                this.editBuchungDialog.getHaben().refresh();
            } catch (ModelException me) {
                this.view.errorAlert("+ Haben-Konto erstellen", "Das angegebene +Haben-Konto konnte nicht erstellt werden!");
            }
        });
    }
}
