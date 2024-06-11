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

    public void addSoll(ListView<KontoBetrag> soll) {
        addSollDialog addSollDialog = new addSollDialog(model);
        Optional<KontoBetrag> k = addSollDialog.showAndWait();

        k.ifPresent(konto -> {
            try {
                Eintrag eintrag = new Eintrag(LocalDate.now(), konto.getKonto().getBezeichnung(), konto.getBetrag());
                this.editBuchungDialog.addBetrag(konto.getBetrag());
                konto.getKonto().getSoll().add(eintrag);
                KontoBetrag sollKonto = new KontoBetrag(konto.getKonto(), konto.getBetrag());
                soll.getItems().add(sollKonto);
                soll.refresh();
            } catch (ModelException me) {
                this.view.errorAlert("+ Soll-Konto erstellen", "Das angegebene +Soll-Konto konnte nicht erstellt werden!");
            }
        });
    }

    public void addHaben(ListView<KontoBetrag> haben) {
        addHabenDialog addHabenDialog = new addHabenDialog(model);
        Optional<KontoBetrag> k = addHabenDialog.showAndWait();

        k.ifPresent(konto -> {
            try {
                Eintrag eintrag = new Eintrag(LocalDate.now(), konto.getKonto().getBezeichnung(), konto.getBetrag());
                this.editBuchungDialog.addBetrag(konto.getBetrag());
                konto.getKonto().getHaben().add(eintrag);
                KontoBetrag habenKonto = new KontoBetrag(konto.getKonto(), konto.getBetrag());
                haben.getItems().add(habenKonto);
                haben.refresh();
            } catch (ModelException me) {
                this.view.errorAlert("+ Haben-Konto erstellen", "Das angegebene +Haben-Konto konnte nicht erstellt werden!");
            }
        });
    }
}
