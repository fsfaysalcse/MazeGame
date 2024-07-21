package com.example.assignmentfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AuthController {

    @FXML
    protected TextField logName;

    @FXML
    protected PasswordField logPassword;

    @FXML
    protected Button logButton;

    @FXML
    protected Button registerButton;

    @FXML
    protected TextField nameField;

    @FXML
    protected PasswordField passwordField;

    protected Stage stage;

    public void mainPage(ActionEvent event) throws IOException {
        changeScene(event, "main_page.fxml", "styles/styles.css");
    }

    public void registrationPage(ActionEvent event) throws IOException {
        changeScene(event, "registration_page.fxml", "styles/styles.css");
    }

    public void loginScene(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login_page.fxml"));
        String css = this.getClass().getResource("styles/styles.css").toExternalForm();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    private void changeScene(ActionEvent event, String fxmlFile, String cssFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    public void register(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String userLogin = nameField.getText();
            String userPassword = passwordField.getText();

            if (registerUser(userLogin, userPassword)) {
                try {
                    System.out.println("Registration successful.");
                    changeScene(e, "login_page.fxml", "styles/styles.css");
                } catch (IOException ioException) {
                    showAlert("Registration Failed", ioException.getMessage());
                }
            } else {
                System.out.println("Registration failed.");
            }
        }
    }

    public void login(ActionEvent e) {
        if (e.getSource() == logButton) {
            String userLogin = logName.getText();
            String userPassword = logPassword.getText();

            if (authenticateUser(userLogin, userPassword)) {
                try {
                    System.out.println("Login successful.");
                    changeScene(e, "menu.fxml", "styles/styles.css");
                } catch (IOException ioException) {
                    System.out.println("Failed to load menu scene.");
                    ioException.printStackTrace();
                }
            } else {
                System.out.println("Login failed.");
            }
        }
    }

    private boolean authenticateUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Failed", "Please enter your email and password.");
            return false;
        }

        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/resources/com/example/assignmentfinal/texts/data.json")) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray users = (JSONArray) jsonObject.get("users");

            for (Object userObj : users) {
                JSONObject user = (JSONObject) userObj;
                if (username.equals(user.get("username")) && password.equals(user.get("password"))) {
                    JSONObject loggedUser = (JSONObject) jsonObject.get("logged_user");
                    if (loggedUser == null) {
                        loggedUser = new JSONObject();
                    }
                    loggedUser.put("username", username);
                    loggedUser.put("password", password);
                    jsonObject.put("logged_user", loggedUser);
                    saveUsers(jsonObject);
                    return true;
                }
            }
            showAlert("Login Failed", "Incorrect email or password.");
        } catch (IOException | ParseException e) {
            showAlert("Login Failed", "An error occurred while accessing user data.");
        }
        return false;
    }

    private boolean registerUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Registration Failed", "Please make sure all fields are filled.");
            return false;
        }

        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("src/main/resources/com/example/assignmentfinal/texts/data.json")) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray users = (JSONArray) jsonObject.get("users");

            for (Object userObj : users) {
                JSONObject user = (JSONObject) userObj;
                String storedUsername = (String) user.get("username");
                if (storedUsername.equals(username)) {
                    showAlert("Registration Failed", "This username is already registered.");
                    return false;
                }
            }

            JSONObject newUser = new JSONObject();
            newUser.put("username", username);
            newUser.put("password", password);
            users.add(newUser);
            saveUsers(jsonObject);

            showAlert("Registration Success", "Registration successful! You can now log in.");
            return true;
        } catch (IOException | ParseException e) {
            showAlert("Registration Failed", "An error occurred while accessing user data.");
            System.out.println("Error accessing or parsing the users file.");
            e.printStackTrace();
        }
        return false;
    }

    private void saveUsers(JSONObject jsonObject) throws IOException {
        try (FileWriter file = new FileWriter("src/main/resources/com/example/assignmentfinal/texts/data.json")) {
            file.write(jsonObject.toJSONString());
            file.flush();
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
