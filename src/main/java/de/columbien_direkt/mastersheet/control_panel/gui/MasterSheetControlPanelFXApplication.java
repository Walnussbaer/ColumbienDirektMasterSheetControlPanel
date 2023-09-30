package de.columbien_direkt.mastersheet.control_panel.gui;

import de.columbien_direkt.mastersheet.control_panel.core.MasterSheetControlPanelApplication;
import de.columbien_direkt.mastersheet.control_panel.core.exception.MasterSheetConnectionException;
import de.columbien_direkt.mastersheet.control_panel.core.exception.MasterSheetOperationException;
import de.columbien_direkt.mastersheet.control_panel.core.sample.MasterSheetSampleService;
import de.columbien_direkt.mastersheet.control_panel.core.service.MasterSheetConnectionService;
import de.columbien_direkt.mastersheet.control_panel.gui.event.StageReadyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.geometry.VerticalDirection;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MasterSheetControlPanelFXApplication extends Application {

  private ConfigurableApplicationContext applicationContext;

  @Override
  public void start(Stage stage) {
    Label label = new Label("POC MasterSheet ControlPanel");
    TextField inputField = new TextField();
    Button button = new Button("Mastersheet wählen");
    Button testConnection = new Button("Verbindung prüfen");
    Button sheetNameButton = new Button("Zeige Sheetnamen");
    ListView<String> sheetNamesList = new ListView<String>(
      FXCollections.observableArrayList()
    );

    FileChooser excelFileChooser = new FileChooser();

    button.setOnAction(event -> {
      File masterSheetFile = excelFileChooser.showOpenDialog(stage);

      if (masterSheetFile != null) {
        inputField.setText(masterSheetFile.getAbsolutePath());
      }

      getMasterSheetConnectionService().setMasterSheetFile(masterSheetFile);
    });

    sheetNameButton.setOnAction(event -> {
      try {
        List<String> sheetNames = getMasterSheetSampleService().getSheetNames();

        sheetNamesList.setItems(FXCollections.observableArrayList(sheetNames));
      } catch (MasterSheetOperationException masterSheetOperationException) {
        System.out.println(masterSheetOperationException.getMessage());
      }
    });

    VBox verticalBox = new VBox(
      label,
      button,
      inputField,
      testConnection,
      sheetNameButton,
      sheetNamesList
    );
    verticalBox.setAlignment(Pos.CENTER);

    Scene scene = new Scene(verticalBox, 640, 480);
    stage.setScene(scene);

    applicationContext.publishEvent(new StageReadyEvent(stage));
  }

  @Override
  public void init() {
    applicationContext =
      new SpringApplicationBuilder(MasterSheetControlPanelApplication.class)
        .run();
  }

  private MasterSheetSampleService getMasterSheetSampleService() {
    return this.applicationContext.getBean(MasterSheetSampleService.class);
  }

  private final MasterSheetConnectionService getMasterSheetConnectionService() {
    return this.applicationContext.getBean(MasterSheetConnectionService.class);
  }

  @Override
  public void stop() {
    applicationContext.close();
    Platform.exit();
  }
}
