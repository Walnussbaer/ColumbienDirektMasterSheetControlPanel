package de.columbien_direkt.mastersheet.control_panel.gui.view;

import de.columbien_direkt.mastersheet.control_panel.core.exception.MasterSheetOperationException;
import de.columbien_direkt.mastersheet.control_panel.core.sample.MasterSheetSampleService;
import de.columbien_direkt.mastersheet.control_panel.core.service.MasterSheetConnectionService;
import java.io.File;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class MasterSheetConnectionConfigurationView extends VBox {

    private final MasterSheetConnectionService connectionService;

    private final MasterSheetSampleService masterSheetSampleService;

    public MasterSheetConnectionConfigurationView(
        MasterSheetConnectionService connectionService,
        MasterSheetSampleService masterSheetSampleService
    ) {
        super();
        this.connectionService = connectionService;
        this.masterSheetSampleService = masterSheetSampleService;

        TextField fileNameInput = new TextField(
            this.connectionService.getMasterSheetFile().isPresent()
                ? this.connectionService.getMasterSheetFile()
                    .get()
                    .getAbsolutePath()
                : ""
        );
        Button pickFileButton = new Button("Mastersheet wählen");
        Button testConnection = new Button("Verbindung prüfen");
        Button sheetNameButton = new Button("Zeige Sheetnamen");
        ListView<String> sheetNamesList = new ListView<>(
            FXCollections.observableArrayList()
        );

        FileChooser excelFileChooser = new FileChooser();

        pickFileButton.setOnAction(event -> {
            File masterSheetFile = excelFileChooser.showOpenDialog(
                this.getScene().getWindow()
            );

            if (masterSheetFile != null) {
                fileNameInput.setText(masterSheetFile.getAbsolutePath());
            }

            this.connectionService.setMasterSheetFile(masterSheetFile);
        });

        testConnection.setOnAction(event -> {
            var canConnect = this.connectionService.testMasterSheetConnection();
            // TODO display small dialog that shows the result

        });

        sheetNameButton.setOnAction(event -> {
            try {
                List<String> sheetNames =
                    this.masterSheetSampleService.getSheetNames();

                sheetNamesList.setItems(
                    FXCollections.observableArrayList(sheetNames)
                );
            } catch (
                MasterSheetOperationException masterSheetOperationException
            ) {
                System.out.println(masterSheetOperationException.getMessage());
            }
        });

        this.getChildren()
            .addAll(pickFileButton, fileNameInput, testConnection);
        this.setAlignment(Pos.CENTER);
    }
}
