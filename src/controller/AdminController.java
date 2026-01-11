package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import model.*;

public class AdminController {

    @FXML private TextArea outputArea;

    @FXML
    public void tambahKendaraan() {
        outputArea.setText("Fitur tambah kendaraan ");
    }

    @FXML
    public void kelolaPetugas() {
        outputArea.setText("Fitur kelola petugas");
    }

    @FXML
    public void lihatLaporan() {
        outputArea.setText("Menampilkan laporan penyewaan");
    }
}
