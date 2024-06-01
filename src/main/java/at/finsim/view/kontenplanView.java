package at.finsim.view;

import at.finsim.model.Unternehmen;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class kontenplanView {
    private Stage stage;
    private Unternehmen unternehmen;

    public kontenplanView(Unternehmen unternehmen) {
        this.stage = new Stage();
        this.unternehmen = unternehmen;

        VBox mainVBox = new VBox();

        Label kontenplanL = new Label("Kontenplan:");

        mainVBox.getChildren().addAll(kontenplanL);

        Scene scene = new Scene(mainVBox, 800, 600);
        this.stage.setTitle("Kontenplan von " + this.unternehmen.getName());
        this.stage.setScene(scene);
        this.stage.setResizable(true);
        this.stage.show();
    }
}
