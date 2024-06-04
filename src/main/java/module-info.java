module at.finsim.finsim {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.xml;

    opens at.finsim to javafx.fxml;
    exports at.finsim;
    exports at.finsim.control;
    opens at.finsim.control to javafx.fxml;
}