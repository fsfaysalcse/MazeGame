package com.example.assignmentfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {


    @FXML
    protected Stage stage;
    @FXML
    protected Label timerLabel;
    @FXML
    protected Canvas mazeCanvas;
    @FXML
    protected ImageView backgroundImage;



    public void menu(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        String css = this.getClass().getResource("styles/styles.css").toExternalForm();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();

    }


    public void stage1(ActionEvent event) throws Exception {
        navigateGame(event, Difficulty.EASY);
    }


    public void stage2(ActionEvent event) throws Exception {
        navigateGame(event, Difficulty.MEDIUM);
    }

    public void stage3(ActionEvent event) throws Exception {
        navigateGame(event, Difficulty.HARD);

    }

    public void stage4(ActionEvent event) throws Exception {
        navigateGame(event, Difficulty.EXPERT);

    }

    public void tutorial(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tutorial.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Load and add CSS to the scene
            String cssPath = getClass().getResource("styles/tutorial.css").toExternalForm();
            scene.getStylesheets().add(cssPath);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.err.println("Error loading tutorial.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void navigateGame(ActionEvent event, Difficulty difficulty) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("maze_game.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        String cssPath = switch (difficulty) {
            case EASY -> getClass().getResource("styles/maze_style_easy.css").toExternalForm();
            case MEDIUM -> getClass().getResource("styles/maze_style_medium.css").toExternalForm();
            case HARD -> getClass().getResource("styles/maze_style_hard.css").toExternalForm();
            case EXPERT -> getClass().getResource("styles/maze_style_expert.css").toExternalForm();
            default -> getClass().getResource("styles/maze_style_medium.css").toExternalForm();
        };

        scene.getStylesheets().add(cssPath);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        MazeController mazeController = loader.getController();
        mazeController.setDifficulty(difficulty);
        scene.setOnKeyPressed(ev -> {
            mazeController.handleKeyPress(ev);
            ev.consume();
        });
        scene.getRoot().requestFocus();
    }


    public void mainPage(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main_page.fxml"));
        String css = this.getClass().getResource("styles/styles.css").toExternalForm();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();

    }

    public void fullScreenMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            String css = this.getClass().getResource("styles/styles.css").toExternalForm();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(css);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void handleGoBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        String css = this.getClass().getResource("styles/styles.css").toExternalForm();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }
}