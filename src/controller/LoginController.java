package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import util.DatabaseDummy;


public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    public void login() throws Exception {
        String u = usernameField.getText();
        String p = passwordField.getText();
        System.out.println("Login attempt: " + u + " / " + p);

        User user = DatabaseDummy.login(u, p);
        if (user != null) {
            System.out.println("Login success: " + user.getRole());
            Session.userAktif = user;
            if (user instanceof Admin) {
                System.out.println("Opening Admin.fxml");
                open("Admin.fxml");
            } else if (user instanceof Petugas) {
                System.out.println("Opening Petugas.fxml");
                open("Petugas.fxml");
            } else {
                messageLabel.setText("Login berhasil, tapi tipe user tidak dikenali.");
            }
        } else {
            System.out.println("Login gagal!");
            messageLabel.setText("Login gagal!");
        }
    }

    private void open(String fxml) throws Exception {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(
            FXMLLoader.load(getClass().getResource("/view/" + fxml))
        ));
    }
}
