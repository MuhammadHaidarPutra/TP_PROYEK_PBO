package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import util.DatabaseDummy;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label infoLabel;

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = DatabaseDummy.login(username, password);

        if (user == null) {
            infoLabel.setText("Username atau password salah!");
        } else {
            infoLabel.setText("Login berhasil sebagai " + user.getRole());
        }
    }
}
