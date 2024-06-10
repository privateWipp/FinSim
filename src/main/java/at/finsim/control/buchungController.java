package at.finsim.control;

import at.finsim.model.Unternehmen;
import at.finsim.view.addSollDialog;

public class buchungController {
    private Unternehmen unternehmen;

    public buchungController(Unternehmen unternehmen) {
        this.unternehmen = unternehmen;
    }

    public void addSoll() {
        addSollDialog addSollDialog = new addSollDialog(unternehmen);
    }
}
