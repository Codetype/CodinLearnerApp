package pl.edu.agh.to2.kitkats.codinlearner;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.edu.agh.to2.kitkats.codinlearner.controller.CodinAppController;

import java.awt.*;
import java.util.Arrays;

public class Main extends Application {

    private Stage primaryStage;

    private CodinAppController appController;

    @Override
    public void start(Stage primaryStage) {



        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Codin Learner");

        this.appController = new CodinAppController(primaryStage);
        this.appController.initRootLayout();

    }

    public static void main(String[] args) {
        launch(args);
    }


}
