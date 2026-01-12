package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.*;
import util.DatabaseDummy;
import javafx.application.Platform;


public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button togglePasswordBtn;
    @FXML private TextField showPasswordField;
    @FXML private Label messageLabel;

    @FXML
    public void login() throws Exception {
        String u = usernameField.getText();
        String p = passwordField.isVisible() ? passwordField.getText() : showPasswordField.getText();
        messageLabel.setText("");
        // Validasi field kosong
        if (u.isEmpty() || p.isEmpty()) {
            showAlert("Username dan password harus diisi!");
            return;
        }
        // Validasi panjang password
        if (p.length() < 6) {
            showAlert("Password minimal 6 karakter!");
            return;
        }
        System.out.println("Login attempt: " + u + " / " + p);

        User user = DatabaseDummy.login(u, p);
        if (user != null) {
            System.out.println("Login success: " + user.getRole());
            Session.userAktif = user;
            if (user instanceof Admin) {
                System.out.println("Opening Admin.fxml");
                open("Admin.fxml");
            } else if (user instanceof Petugas) {
                System.out.println("Opening PetugasDashboard.fxml");
                open("PetugasDashboard.fxml");
            } else {
                showAlert("Login berhasil, tapi tipe user tidak dikenali.");
            }
        } else {
            System.out.println("Login gagal!");
            showAlert("Username atau password salah!");
        }
    }

    private boolean passwordVisible = false;

    @FXML
    public void togglePassword() {
        passwordVisible = !passwordVisible;
        
        if (passwordVisible) {
            // Tampilkan password (TextField biasa)
            showPasswordField.setText(passwordField.getText());
            showPasswordField.setVisible(true);
            showPasswordField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
        } else {
            // Sembunyikan password (PasswordField)
            passwordField.setText(showPasswordField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            showPasswordField.setVisible(false);
            showPasswordField.setManaged(false);
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Login Gagal");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        // Set default state: password tersembunyi
        passwordVisible = false;
        showPasswordField.setVisible(false);
        showPasswordField.setManaged(false);
        
        // Sinkronisasi text antara passwordField dan showPasswordField
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (passwordField.isVisible()) {
                showPasswordField.setText(newValue);
            }
        });
        
        showPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (showPasswordField.isVisible()) {
                passwordField.setText(newValue);
            }
        });
    }

    private void open(String fxml) throws Exception {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxml));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        Platform.runLater(() -> stage.setMaximized(true));
    }
}
