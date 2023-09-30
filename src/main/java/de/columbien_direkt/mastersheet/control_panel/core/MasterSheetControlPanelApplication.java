package de.columbien_direkt.mastersheet.control_panel.core;

import de.columbien_direkt.mastersheet.control_panel.gui.MasterSheetControlPanelFXApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MasterSheetControlPanelApplication {

  public static void main(String[] args) {
    Application.launch(MasterSheetControlPanelFXApplication.class, args);
  }
}
