package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminView {

    public void show(Stage stage) {

        Label title = new Label("Menu Admin");

        Button btnKendaraan = new Button("Kelola Data Kendaraan");
        Button btnPetugas = new Button("Kelola Akun Petugas");
        Button btnLaporan = new Button("Lihat Laporan");
        Button btnLogout = new Button("Logout");

        btnLogout.setOnAction(e -> {
            new LoginView().show(stage);
        });

        VBox root = new VBox(10,
                title,
                btnKendaraan,
                btnPetugas,
                btnLaporan,
                btnLogout
        );
        root.setStyle("-fx-padding:20");

        stage.setScene(new Scene(root, 350, 300));
        stage.setTitle("Admin - SewainAja");
        btnKendaraan.setOnAction(e -> {
    new KelolaKendaraanView().show(stage);
});

        stage.show();
    }
}

