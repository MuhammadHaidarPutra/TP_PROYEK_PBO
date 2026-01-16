package controller;

import javafx.fxml.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import model.Session;
import model.Admin;
import javafx.scene.control.Alert;
import javafx.application.Platform;

public class AdminController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        if (!(Session.userAktif instanceof Admin)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Akses Ditolak");
            alert.setHeaderText(null);
            alert.setContentText("Anda tidak memiliki akses ke menu admin.");
            alert.showAndWait();
            logout();
            return;
        }
        loadView("AdminDashboard.fxml");
    }

    private void loadView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxml));
            javafx.scene.Node node = (javafx.scene.Node) loader.load();
            contentArea.getChildren().setAll(node);
            if (node instanceof javafx.scene.layout.Region) {
                javafx.scene.layout.Region region = (javafx.scene.layout.Region) node;
                region.prefWidthProperty().bind(contentArea.widthProperty());
                region.prefHeightProperty().bind(contentArea.heightProperty());
            }
            Stage stage = (Stage) contentArea.getScene().getWindow();
            if (stage != null) {
                stage.setMaximized(true);
            }
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
            Platform.runLater(() -> stage.setMaximized(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
