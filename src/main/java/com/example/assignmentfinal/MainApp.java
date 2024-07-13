package com.example.assignmentfinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp extends Application {
    private static final Logger LOGGER = Logger.getLogger(MainApp.class.getName());

    @Override
    public void start(Stage stage) {
        try {
            Scene scene = createScene(loadFXML("main_page.fxml"));
            stage.setScene(scene);
            stage.setTitle("PUZZLE MANIA");
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load the FXML file.", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An unexpected error occurred.", e);
        }
    }

    private Scene createScene(Parent root) throws IOException {
        Scene scene = new Scene(root);
        String css = loadResource("styles/styles.css");
        scene.getStylesheets().add(css);
        return scene;
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlUrl = getClass().getResource(fxml);
        if (fxmlUrl == null) {
            throw new IOException("Cannot load resource: " + fxml);
        }
        loader.setLocation(fxmlUrl);
        return loader.load();
    }

    private String loadResource(String resource) throws IOException {
        URL resUrl = getClass().getResource(resource);
        if (resUrl == null) {
            throw new IOException("Cannot load resource: " + resource);
        }
        return resUrl.toExternalForm();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
