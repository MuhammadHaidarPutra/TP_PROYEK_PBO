package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PetugasView {

    public void show(Stage stage) {

        Label title = new Label("Menu Petugas");

        Button btnSewa = new Button("Input Penyewaan");
        Button btnKembali = new Button("Pengembalian Kendaraan");
        Button btnLihat = new Button("Lihat Data Kendaraan");
        Button btnLogout = new Button("Logout");

        btnLogout.setOnAction(e -> {
            new LoginView().show(stage);
        });

        VBox root = new VBox(10,
                title,
                btnSewa,
                btnKembali,
                btnLihat,
                btnLogout
        );
        root.setStyle("-fx-padding:20");

        stage.setScene(new Scene(root, 350, 300));
        stage.setTitle("Petugas - SewainAja");
        btnSewa.setOnAction(e -> new PenyewaanView().show(stage));
btnKembali.setOnAction(e -> new PengembalianView().show(stage));
        stage.show();
    }
}
