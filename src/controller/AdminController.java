package controller;

import javafx.fxml.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class AdminController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        loadView("AdminDashboard.fxml");
    }

    private void loadView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxml));
            contentArea.getChildren().setAll((javafx.scene.Node) loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showDashboard() {
        loadView("AdminDashboard.fxml");
    }

    @FXML
    public void showKendaraan() {
        loadView("KelolaKendaraan.fxml");
    }

    @FXML
    public void showPetugas() {
        loadView("KelolaPetugas.fxml");
    }

    @FXML
    public void showLaporan() {
        loadView("Laporan.fxml");
    }

    @FXML
    public void logout() {
        try {
            Stage stage = (Stage) contentArea.getScene().getWindow();
            Scene scene = new Scene(
                FXMLLoader.load(getClass().getResource("/view/Login.fxml"))
            );
            stage.setScene(scene);
            stage.setMaximized(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ...existing code...
}
