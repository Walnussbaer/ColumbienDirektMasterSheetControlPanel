package de.columbien_direkt.mastersheet.control_panel.gui;

import de.columbien_direkt.mastersheet.control_panel.core.MasterSheetControlPanelApplication;
import de.columbien_direkt.mastersheet.control_panel.core.sample.MasterSheetSampleService;
import de.columbien_direkt.mastersheet.control_panel.core.service.MasterSheetConnectionService;
import de.columbien_direkt.mastersheet.control_panel.gui.event.StageReadyEvent;
import de.columbien_direkt.mastersheet.control_panel.gui.view.MasterSheetConnectionConfigurationView;
import de.columbien_direkt.mastersheet.control_panel.gui.view.WelcomeView;
import jakarta.annotation.Nonnull;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MasterSheetControlPanelFXApplication extends Application {

    @Nonnull
    private final ConfigurableApplicationContext applicationContext;

    @Nonnull
    private final VBox mainLayout;

    @Nonnull
    private Node activeView;

    public MasterSheetControlPanelFXApplication() {
        this.activeView = new WelcomeView();

        applicationContext =
            new SpringApplicationBuilder(
                MasterSheetControlPanelApplication.class
            )
                .run();

        this.mainLayout = new VBox();
    }

    @Override
    public void start(Stage stage) {
        Label titleLabel = new Label("MasterSheet ControlPanel");
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setStyle("-fx-font-weight: bold");

        /* first menu */
        Menu mainMenu = new Menu("Datei");

        MenuItem homeMenuItem = new MenuItem("Startseite");
        homeMenuItem.setOnAction(event -> {
            changeActiveView(new WelcomeView());
        });

        MenuItem configureMasterSheetMenuItem = new MenuItem(
            "Mastersheet konfigurieren"
        );
        configureMasterSheetMenuItem.setOnAction(event -> {
            changeActiveView(
                new MasterSheetConnectionConfigurationView(
                    getMasterSheetConnectionService(),
                    getMasterSheetSampleService()
                )
            );
        });
        mainMenu.getItems().addAll(homeMenuItem, configureMasterSheetMenuItem);

        /* second menu */
        Menu actionsMenu = new Menu("Aktionen");
        actionsMenu
            .getItems()
            .addAll(new MenuItem("Transaktionsdaten generieren"));

        MenuBar mainMenuBar = new MenuBar(mainMenu, actionsMenu);

        this.mainLayout.getChildren()
            .addAll(titleLabel, mainMenuBar, this.activeView);
        this.mainLayout.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(this.mainLayout, 640, 480);
        stage.setScene(scene);

        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }

    private void changeActiveView(Node newView) {
        this.mainLayout.getChildren().remove(this.activeView);
        this.mainLayout.getChildren().add(newView);
        this.activeView = newView;
    }

    private MasterSheetConnectionService getMasterSheetConnectionService() {
        return this.applicationContext.getBean(
                MasterSheetConnectionService.class
            );
    }

    private MasterSheetSampleService getMasterSheetSampleService() {
        return this.applicationContext.getBean(MasterSheetSampleService.class);
    }
}
