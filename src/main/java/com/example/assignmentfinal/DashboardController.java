package com.example.assignmentfinal;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

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

        /*
        *     Parent root = FXMLLoader.load(getClass().getResource("main_page.fxml"));
        String css = this.getClass().getResource("styles/styles.css").toExternalForm();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
        * */

        FXMLLoader loader = new FXMLLoader(getClass().getResource("maze_game.fxml"));
        String css = getClass().getResource("styles/maze_style.css").toExternalForm();



        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = loader.load();
        Scene scene = new Scene(root);

        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();

        MazeController mazeController = loader.getController();
        scene.setOnKeyPressed(ev -> {
            mazeController.handleKeyPress(ev);
            event.consume();
        });

        scene.getRoot().requestFocus();
    }


    public void stage2(ActionEvent event) throws Exception {


    }

    public void stage3(ActionEvent event) throws Exception {

    }

    public void stage4(ActionEvent event) throws Exception {

    }

    public void tutorial(ActionEvent event) throws Exception {

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

    public void fullScreenMenu(ActionEvent event) throws Exception {


    }


}
