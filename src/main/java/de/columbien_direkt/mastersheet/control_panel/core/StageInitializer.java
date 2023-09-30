package de.columbien_direkt.mastersheet.control_panel.core;

import de.columbien_direkt.mastersheet.control_panel.gui.event.StageReadyEvent;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

  @Override
  public void onApplicationEvent(StageReadyEvent event) {
    Stage stage = event.getStage();

    stage.show();
  }
}
