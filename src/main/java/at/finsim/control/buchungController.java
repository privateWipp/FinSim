package at.finsim.control;

import at.finsim.model.Eintrag;
import at.finsim.model.ModelException;
import at.finsim.model.Unternehmen;
import at.finsim.model.konto.KontoBetrag;
import at.finsim.view.addHabenDialog;
import at.finsim.view.addSollDialog;
import javafx.scene.control.ListView;
import at.finsim.view.unternehmenView;
import at.finsim.view.neueBuchungDialog;

import java.time.LocalDate;
import java.util.Optional;

public class buchungController {
    private Unternehmen model;
    private unternehmenView view;
    private neueBuchungDialog neueBuchungDialog;

    public buchungController(Unternehmen unternehmen, unternehmenView unternehmenView, neueBuchungDialog neueBuchungDialog) {
        this.model = unternehmen;
        this.view = unternehmenView;
        this.neueBuchungDialog = neueBuchungDialog;
    }

    public void addSoll() {
        addSollDialog addSollDialog = new addSollDialog(model);
        Optional<KontoBetrag> k = addSollDialog.showAndWait();

        k.ifPresent(konto -> {
            try {
                Eintrag eintrag = new Eintrag(LocalDate.now(), konto.getKonto().getBezeichnung(), konto.getBetrag());
                this.neueBuchungDialog.addBetrag(konto.getBetrag());
                // konto.getKonto().getSoll().add(eintrag);
                KontoBetrag sollKonto = new KontoBetrag(konto.getKonto(), konto.getBetrag());
                this.neueBuchungDialog.getSoll().getItems().add(sollKonto);
                this.neueBuchungDialog.getSoll().refresh();
            } catch (ModelException me) {
                this.view.errorAlert("+ Soll-Konto erstellen", "Das angegebene +Soll-Konto konnte nicht erstellt werden!");
            }
        });
    }

    public void addHaben() {
        addHabenDialog addHabenDialog = new addHabenDialog(model);
        Optional<KontoBetrag> k = addHabenDialog.showAndWait();

        k.ifPresent(konto -> {
            try {
                Eintrag eintrag = new Eintrag(LocalDate.now(), konto.getKonto().getBezeichnung(), konto.getBetrag());
                this.neueBuchungDialog.addBetrag(konto.getBetrag());
                // konto.getKonto().getHaben().add(eintrag);
                KontoBetrag habenKonto = new KontoBetrag(konto.getKonto(), konto.getBetrag());
                this.neueBuchungDialog.getHaben().getItems().add(habenKonto);
                this.neueBuchungDialog.getHaben().refresh();
            } catch (ModelException me) {
                this.view.errorAlert("+ Haben-Konto erstellen", "Das angegebene +Haben-Konto konnte nicht erstellt werden!");
            }
        });
    }
}
