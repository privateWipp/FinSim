package at.finsim.control;

import at.finsim.model.Eintrag;
import at.finsim.model.ModelException;
import at.finsim.model.Unternehmen;
import at.finsim.model.konto.Konto;
import at.finsim.model.konto.KontoBetrag;
import at.finsim.view.addHabenDialog;
import at.finsim.view.addSollDialog;
import javafx.scene.control.ListView;
import at.finsim.view.unternehmenView;

import java.time.LocalDate;
import java.util.Optional;

public class buchungController {
    private Unternehmen model;
    private unternehmenView view;

    public buchungController(Unternehmen unternehmen, unternehmenView unternehmenView) {
        this.model = unternehmen;
        this.view = unternehmenView;
    }

    public void addSoll(ListView<Konto> soll) {
        addSollDialog addSollDialog = new addSollDialog(model);
        Optional<KontoBetrag> k = addSollDialog.showAndWait();

        k.ifPresent(konto -> {
            try {
                Eintrag eintrag = new Eintrag(LocalDate.now(), konto.getKonto().getBezeichnung(), konto.getBetrag());
                konto.getKonto().getSoll().add(eintrag);
                soll.getItems().add(konto.getKonto());
                soll.refresh();
            } catch (ModelException me) {
                this.view.errorAlert("+ Soll-Konto erstellen", "Das angegebene +Soll-Konto konnte nicht erstellt werden!");
            }
        });
    }

    public void addHaben(ListView<Konto> haben) {
        addHabenDialog addHabenDialog = new addHabenDialog(model);
        Optional<KontoBetrag> k = addHabenDialog.showAndWait();

        k.ifPresent(konto -> {
            try {
                Eintrag eintrag = new Eintrag(LocalDate.now(), konto.getKonto().getBezeichnung(), konto.getBetrag());
                konto.getKonto().getHaben().add(eintrag);
                haben.getItems().add(konto.getKonto());
                haben.refresh();
            } catch (ModelException me) {
                this.view.errorAlert("+ Haben-Konto erstellen", "Das angegebene +Haben-Konto konnte nicht erstellt werden!");
            }
        });
    }
}
