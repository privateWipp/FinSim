package at.finsim.control;

import at.finsim.model.Geschaeftsjahr;
import at.finsim.model.Kontenplan;
import at.finsim.model.ModelException;
import at.finsim.model.Unternehmen;
import at.finsim.view.View;
import at.finsim.view.addUnternehmenDialog;
import at.finsim.view.unternehmenView;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Jonas Mader, Nikodem Marek
 * @version 0.1
 */

public class Controller {
    private final View view;

    /**
     * Konstruktor vom Main-Controller
     *
     * @param view
     */
    public Controller(View view) {
        this.view = view;
    }

    /**
     * Erstellt einen neuen Dialog, womit ein neues Unternehmen
     * kreiert, und zur ListView in der Main-View geaddet wird.
     */
    public void neuesUnternehmen() {
        addUnternehmenDialog addUnternehmenDialog = new addUnternehmenDialog();
        Optional<Unternehmen> u = addUnternehmenDialog.showAndWait();

        u.ifPresent(unternehmen -> {
            this.view.getUnternehmenListView().getItems().add(unternehmen);
            this.view.getUnternehmenListView().refresh();
        });
    }

    /**
     * Dient dazu, per Klick ein Unternehmen zu öffnen.
     * Es öffnet sich ein neues Fenster, indem auf verschiedene Art und Weise
     * die Daten des Unternehmens repräsentiert werden und gemeinsam im
     * "Konzert" arbeiten
     *
     * @param unternehmen
     */
    public void runUnternehmen(Unternehmen unternehmen) {
        Stage stage = new Stage();
        unternehmenView unternehmenView = new unternehmenView(unternehmen);
        stage.setTitle(unternehmen.getName() + " : Unternehmen");
        stage.setResizable(false);
        Scene scene = new Scene(unternehmenView, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    public void deleteUnternehmen(Unternehmen unternehmen) {
        this.view.getUnternehmenListView().getItems().remove(unternehmen);
        this.view.getUnternehmenListView().refresh();
    }

    /**
     * Ermöglicht das Importieren von Unternehmen im Startfenster per FileChooser
     */
    public void laden() {

        FileChooser fc = new FileChooser();
        fc.setTitle("Unternehmen laden");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fs-Datei", "*.fs");
        fc.getExtensionFilters().add(extFilter);

            File desktopDir = new File(System.getProperty("user.home"), "Desktop");
            fc.setInitialDirectory(desktopDir);

        File file = fc.showOpenDialog(this.view.getScene().getWindow());

        try {
            Unternehmen unternehmen = new Unternehmen("Name", 2019, LocalDate.now(), new ArrayList<Geschaeftsjahr>(), new Kontenplan(), 450000);
            unternehmen.laden(file);
            if (!(this.view.getUnternehmenListView().getItems().contains(unternehmen))) {
                this.view.getUnternehmenListView().getItems().add(unternehmen);
                this.view.getUnternehmenListView().refresh();
            } else {
                this.view.errorAlert("Importieren", "Das Unternehmen ist bereits in der Liste eingetragen!");
            }
        } catch (ModelException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void ladenAlle() {
        // Specify the path to your XML configuration file
        String xmlFilePath = System.getProperty("user.dir") + File.separator + "config.xml";

        // Get the default directory path from the XML file
        String defaultDirectoryPath = ConfigReader.getDefaultDirectoryPath(xmlFilePath);

        File defaultDir = new File(defaultDirectoryPath);

        if (defaultDir.exists() && defaultDir.isDirectory()) {
            File[] files = defaultDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".fs"));
            if (files != null) {
                for (File file : files) {
                    try {
                        Unternehmen unternehmen = new Unternehmen("Name", 2019, LocalDate.now(), new ArrayList<Geschaeftsjahr>(), new Kontenplan(), 450000);
                        unternehmen.laden(file);
                        if (!(this.view.getUnternehmenListView().getItems().contains(unternehmen))) {
                            this.view.getUnternehmenListView().getItems().add(unternehmen);
                        } else {
                            this.view.errorAlert("Importieren", "Das Unternehmen " + file.getName() + " ist bereits in der Liste eingetragen!");
                        }
                    } catch (ModelException | FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                this.view.getUnternehmenListView().refresh();
            }
        } else {
            this.view.errorAlert("Fehler", "Das Standardverzeichnis existiert nicht oder ist kein Verzeichnis.");
        }
    }
    }