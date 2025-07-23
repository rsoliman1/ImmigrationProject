package edu.gmu.cs321;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static Stage mainStage; // Shared stage for switching scenes

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        loadScene("/edu/gmu/cs321/PetitionFormUI.fxml", "Petition Form");
    }

    public static void loadScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            mainStage.setScene(scene);
            mainStage.setTitle(title);
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading scene: " + fxmlPath);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}