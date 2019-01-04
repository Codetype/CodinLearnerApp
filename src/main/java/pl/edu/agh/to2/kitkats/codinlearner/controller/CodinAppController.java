package pl.edu.agh.to2.kitkats.codinlearner.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.edu.agh.to2.kitkats.codinlearner.model.Arena;

import java.io.IOException;

public class CodinAppController{

    private Stage primaryStage;

    public CodinAppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initRootLayout() {
        try {
            this.primaryStage.setTitle("Codin Learner JavaFX app");

            // load layout from FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CodinOverviewPane.fxml"));
            Parent rootLayout = (BorderPane) loader.load();

            // set initial data into controller
            CodinOverviewController controller = loader.getController();
            controller.setAppController(this);
            controller.setArena(new Arena(600.0f, 400.0f, 21f, 8f));
            controller.initializeLevels();
            controller.initializeProperties();
            controller.initializeCanvasManager();
            controller.initializeDrawing();
            controller.showLevelInfo();
            // add layout to a scene and show them all
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            // don't do this in common apps
            e.printStackTrace();
        }

    }

}
