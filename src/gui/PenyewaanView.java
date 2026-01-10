package gui;

import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import service.DataStore;

public class PenyewaanView {

    public void show(Stage stage) {

        TextField txtNama = new TextField();
        txtNama.setPromptText("Nama Pelanggan");

        TextField txtTelp = new TextField();
        txtTelp.setPromptText("No Telepon");

        TextField txtHari = new TextField();
        txtHari.setPromptText("Lama Sewa (hari)");

        ObservableList<Kendaraan> tersedia = FXCollections.observableArrayList();
        for (Kendaraan k : DataStore.kendaraanList) {
            if (k.isTersedia()) tersedia.add(k);
        }

        ComboBox<Kendaraan> cmbKendaraan = new ComboBox<>(tersedia);

        Button btnSewa = new Button("Sewa");
        Button btnKembali = new Button("Kembali");

        btnSewa.setOnAction(e -> {
            Pelanggan p = new Pelanggan("P01", txtNama.getText(), txtTelp.getText());
            Kendaraan k = cmbKendaraan.getValue();
            Penyewaan sewa = new Penyewaan(
                    "S" + (DataStore.penyewaanList.size() + 1),
                    p, k, Integer.parseInt(txtHari.getText())
            );
            DataStore.penyewaanList.add(sewa);
        });

        btnKembali.setOnAction(e -> new PetugasView().show(stage));

        VBox root = new VBox(10,
                txtNama, txtTelp, txtHari,
                cmbKendaraan, btnSewa, btnKembali);

        root.setStyle("-fx-padding:20");
        stage.setScene(new Scene(root, 400, 400));
    }
}
