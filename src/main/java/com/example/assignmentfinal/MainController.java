package com.example.assignmentfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

    private Stage getStageFromEvent(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    private Scene loadPage(String fxmlFile, String cssFile) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlUrl = getClass().getResource(fxmlFile);
        if (fxmlUrl == null) {
            throw new IOException("Cannot load FXML file: " + fxmlFile);
        }
        loader.setLocation(fxmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        String css = loadResource(cssFile);
        scene.getStylesheets().add(css);

        return scene;
    }

    private String loadResource(String resource) throws IOException {
        URL resUrl = getClass().getResource(resource);
        if (resUrl == null) {
            throw new IOException("Cannot load resource: " + resource);
        }
        return resUrl.toExternalForm();
    }

    public void switchPage(ActionEvent event, String fxmlFile, String cssFile) {
        try {
            Scene scene = loadPage(fxmlFile, cssFile);
            Stage stage = getStageFromEvent(event);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to switch scenes", e);
        }
    }

    public void loginPage(ActionEvent event) {
        switchPage(event, "login_page.fxml", "styles/styles.css");
    }

    public void registrationPage(ActionEvent event) {
        switchPage(event, "registration_page.fxml", "styles/styles.css");
    }

    public void exit(ActionEvent event) {
        Stage stage = getStageFromEvent(event);
        stage.close();
    }
}
