package gui;

import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import service.DataStore;

public class KelolaKendaraanView {

    private ObservableList<Kendaraan> data =
            FXCollections.observableArrayList(DataStore.kendaraanList);

    public void show(Stage stage) {

        TextField txtId = new TextField();
        txtId.setPromptText("ID Kendaraan");

        TextField txtMerek = new TextField();
        txtMerek.setPromptText("Merek");

        TextField txtModel = new TextField();
        txtModel.setPromptText("Model");

        TextField txtHarga = new TextField();
        txtHarga.setPromptText("Harga / Hari");

        ComboBox<String> cmbJenis = new ComboBox<>();
        cmbJenis.getItems().addAll("Mobil", "Motor");
        cmbJenis.setValue("Mobil");

        ListView<Kendaraan> listView = new ListView<>(data);

        Button btnTambah = new Button("Tambah");
        Button btnHapus = new Button("Hapus");
        Button btnKembali = new Button("Kembali");

        btnTambah.setOnAction(e -> {
            Kendaraan k;
            if (cmbJenis.getValue().equals("Mobil")) {
                k = new Mobil(txtId.getText(), txtMerek.getText(),
                        txtModel.getText(), Double.parseDouble(txtHarga.getText()), 4);
            } else {
                k = new Motor(txtId.getText(), txtMerek.getText(),
                        txtModel.getText(), Double.parseDouble(txtHarga.getText()), 150);
            }
            DataStore.kendaraanList.add(k);
            data.add(k);
        });

        btnHapus.setOnAction(e -> {
            Kendaraan selected = listView.getSelectionModel().getSelectedItem();
            DataStore.kendaraanList.remove(selected);
            data.remove(selected);
        });

        btnKembali.setOnAction(e -> new AdminView().show(stage));

        VBox root = new VBox(10,
                txtId, txtMerek, txtModel, txtHarga, cmbJenis,
                btnTambah, btnHapus, listView, btnKembali);

        root.setStyle("-fx-padding:20");
        stage.setScene(new Scene(root, 400, 500));
    }
}
