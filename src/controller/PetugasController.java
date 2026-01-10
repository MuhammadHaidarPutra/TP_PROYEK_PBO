package controller;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;

public class PetugasController {

    @FXML private TextField txtIdPelanggan, txtNama, txtTelepon, txtHari;
    @FXML private ComboBox<Kendaraan> cmbKendaraan;
    @FXML private TableView<Penyewaan> tableSewa;

    @FXML private TableColumn<Penyewaan, String> colId, colPelanggan, colKendaraan, colStatus;
    @FXML private TableColumn<Penyewaan, Integer> colHari;
    @FXML private TableColumn<Penyewaan, Double> colTotal;

    private SistemManajemen sistem = new SistemManajemen();
    private ObservableList<Penyewaan> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cmbKendaraan.setItems(FXCollections.observableArrayList(
                new Mobil("M01","Toyota","Avanza",350000,7),
                new Motor("MT01","Honda","Vario",150000,125)
        ));

        colId.setCellValueFactory(c -> c.getValue().idSewaProperty());
        colPelanggan.setCellValueFactory(c -> c.getValue().getPelanggan().namaProperty());
        colKendaraan.setCellValueFactory(c -> c.getValue().getKendaraan().modelProperty());
        colHari.setCellValueFactory(c -> c.getValue().hariProperty().asObject());
        colTotal.setCellValueFactory(c -> c.getValue().totalBiayaProperty().asObject());
        colStatus.setCellValueFactory(c -> c.getValue().statusProperty());

        tableSewa.setItems(data);
    }

    @FXML
    void handleSewa() {
        Pelanggan p = new Pelanggan(
                txtIdPelanggan.getText(),
                txtNama.getText(),
                txtTelepon.getText()
        );

        Kendaraan k = cmbKendaraan.getValue();
        int hari = Integer.parseInt(txtHari.getText());

        String idSewa = "SEWA" + System.currentTimeMillis();
        Penyewaan sewa = new Penyewaan(idSewa, p, k, hari);
        sewa.mulaiSewa();

        data.add(sewa);
    }

    @FXML
    void handlePengembalian() {
        Penyewaan sewa = tableSewa.getSelectionModel().getSelectedItem();
        if (sewa != null) {
            sewa.pengembalian();
            tableSewa.refresh();
        }
    }
}
