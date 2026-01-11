package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class PetugasController {

    @FXML private TextArea outputArea;

    @FXML
    public void inputSewa() {
        outputArea.setText("Form input penyewaan");
    }

    @FXML
    public void pengembalian() {
        outputArea.setText("Proses pengembalian");
    }

    @FXML
    public void lihatKendaraan() {
        outputArea.setText("Daftar kendaraan");
    }
}
