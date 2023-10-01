package de.columbien_direkt.mastersheet.control_panel.gui.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WelcomeView extends VBox {

    public WelcomeView() {
        super();
        this.getChildren().addAll(new Label("Willkommen"));
        this.setAlignment(Pos.CENTER);
    }
}
