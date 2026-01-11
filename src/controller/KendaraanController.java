package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Kendaraan;

public class KendaraanController {
    @FXML private TextField tfPlat, tfMerk, tfJenis, tfTahun;
    @FXML private TableView<Kendaraan> tableKendaraan;
    @FXML private TableColumn<Kendaraan, String> colPlat, colMerk, colJenis, colTahun;

    private ObservableList<Kendaraan> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colPlat.setCellValueFactory(cell -> cell.getValue().platProperty());
        colMerk.setCellValueFactory(cell -> cell.getValue().merkProperty());
        colJenis.setCellValueFactory(cell -> cell.getValue().jenisProperty());
        colTahun.setCellValueFactory(cell -> cell.getValue().tahunProperty());
        tableKendaraan.setItems(data);
    }

    @FXML
    public void handleTambah() {
        Kendaraan k = new Kendaraan(tfPlat.getText(), tfMerk.getText(), tfJenis.getText(), tfTahun.getText());
        data.add(k);
        clearForm();
    }

    @FXML
    public void handleUbah() {
        Kendaraan k = tableKendaraan.getSelectionModel().getSelectedItem();
        if (k != null) {
              String plat = tfPlat.getText();
              String merk = tfMerk.getText();
              String jenis = tfJenis.getText();
              String tahun = tfTahun.getText();
              if (!plat.isEmpty()) k.setPlat(plat);
              if (!merk.isEmpty()) k.setMerk(merk);
              if (!jenis.isEmpty()) k.setJenis(jenis);
              if (!tahun.isEmpty()) k.setTahun(tahun);
            tableKendaraan.refresh();
            clearForm();
        }
    }

    @FXML
    public void handleHapus() {
        Kendaraan k = tableKendaraan.getSelectionModel().getSelectedItem();
        if (k != null) {
            data.remove(k);
            clearForm();
        }
    }

    private void clearForm() {
        tfPlat.clear();
        tfMerk.clear();
        tfJenis.clear();
        tfTahun.clear();
    }
}
