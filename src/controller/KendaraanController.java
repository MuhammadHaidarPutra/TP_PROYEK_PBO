package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Kendaraan;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class KendaraanController {
    @FXML private TextField tfPlat, tfMerk, tfJenis, tfTahun, tfSearch;
    @FXML private TableView<Kendaraan> tableKendaraan;
    @FXML private TableColumn<Kendaraan, String> colPlat, colMerk, colJenis, colTahun;

    private ObservableList<Kendaraan> data = FXCollections.observableArrayList();
    private ObservableList<Kendaraan> filteredData = FXCollections.observableArrayList();
    private final String CSV_PATH = "src/assets/kendaraan.csv";

    private int pageSize = 10;
    private int currentPage = 1;
    @FXML private Label pageInfoLabel;
    @FXML private Button btnPrev, btnNext;

    @FXML
    public void initialize() {
        colPlat.setCellValueFactory(cell -> cell.getValue().platProperty());
        colMerk.setCellValueFactory(cell -> cell.getValue().merkProperty());
        colJenis.setCellValueFactory(cell -> cell.getValue().jenisProperty());
        colTahun.setCellValueFactory(cell -> cell.getValue().tahunProperty());

        loadKendaraanFromCSV();
        updateTablePage();
    }

    private void loadKendaraanFromCSV() {
        data.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; } // skip header
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    data.add(new Kendaraan(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal baca CSV: " + e.getMessage());
        }
    }

    private void saveKendaraanToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_PATH))) {
            bw.write("plat,merk,jenis,tahun\n");
            for (Kendaraan k : data) {
                bw.write(k.getPlat() + "," + k.getMerk() + "," + k.getJenis() + "," + k.getTahun() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Gagal simpan CSV: " + e.getMessage());
        }
    }

    private void updateTablePage() {
        filteredData.clear();
        int from = (currentPage - 1) * pageSize;
        int to = Math.min(from + pageSize, data.size());
        if (from < to) filteredData.addAll(data.subList(from, to));
        tableKendaraan.setItems(filteredData);
        int totalPages = (int) Math.ceil((double) data.size() / pageSize);
        if (pageInfoLabel != null) pageInfoLabel.setText("Halaman " + currentPage + "/" + (totalPages == 0 ? 1 : totalPages));
        if (btnPrev != null) btnPrev.setDisable(currentPage == 1);
        if (btnNext != null) btnNext.setDisable(currentPage >= totalPages);
    }

    @FXML
    public void nextPage() {
        int totalPages = (int) Math.ceil((double) data.size() / pageSize);
        if (currentPage < totalPages) {
            currentPage++;
            updateTablePage();
        }
    }

    @FXML
    public void prevPage() {
        if (currentPage > 1) {
            currentPage--;
            updateTablePage();
        }
    }

    @FXML
    public void handleSearch() {
        String keyword = tfSearch.getText().toLowerCase();
        filteredData.clear();
        if (keyword.isEmpty()) {
            filteredData.addAll(data);
        } else {
            for (Kendaraan k : data) {
                if (k.getPlat().toLowerCase().contains(keyword) ||
                    k.getMerk().toLowerCase().contains(keyword) ||
                    k.getJenis().toLowerCase().contains(keyword) ||
                    k.getTahun().toLowerCase().contains(keyword)) {
                    filteredData.add(k);
                }
            }
        }
        // Auto-select baris pertama hasil pencarian jika ada
        if (!filteredData.isEmpty()) {
            tableKendaraan.getSelectionModel().select(0);
            tableKendaraan.scrollTo(0);
        } else {
            tableKendaraan.getSelectionModel().clearSelection();
        }
    }

    @FXML
    public void handleTambah() {
        String plat = tfPlat.getText().trim();
        String merk = tfMerk.getText().trim();
        String jenis = tfJenis.getText().trim();
        String tahun = tfTahun.getText().trim();
        // Validasi field kosong
        if (plat.isEmpty() || merk.isEmpty() || jenis.isEmpty() || tahun.isEmpty()) {
            showWarning("Semua field harus diisi!");
            return;
        }
        // Validasi format plat nomor (minimal 5 karakter, ada huruf dan angka)
        if (!plat.matches(".*[A-Za-z].*") || !plat.matches(".*\\d.*") || plat.length() < 5) {
            showWarning("Format plat nomor tidak valid!");
            return;
        }
        // Validasi tahun (harus angka dan rentang tahun masuk akal)
        if (!tahun.matches("\\d{4}") || Integer.parseInt(tahun) < 1990 || Integer.parseInt(tahun) > 2026) {
            showWarning("Tahun harus 4 digit dan antara 1990-2026!");
            return;
        }
        // Validasi duplikasi plat nomor
        for (Kendaraan kd : data) {
            if (kd.getPlat().equalsIgnoreCase(plat)) {
                showWarning("Plat nomor sudah terdaftar!");
                return;
            }
        }
        Kendaraan k = new Kendaraan(plat, merk, jenis, tahun);
        data.add(k);
        saveKendaraanToCSV();
        showInfo("Data kendaraan berhasil ditambahkan.");
        updateTablePage();
        handleSearch(); // update filteredData dan tampilan tabel
        clearForm();
    }

    @FXML
    public void handleUbah() {
        Kendaraan k = tableKendaraan.getSelectionModel().getSelectedItem();
        if (k != null) {
            String plat = tfPlat.getText().trim();
            String merk = tfMerk.getText().trim();
            String jenis = tfJenis.getText().trim();
            String tahun = tfTahun.getText().trim();
            if (plat.isEmpty() || merk.isEmpty() || jenis.isEmpty() || tahun.isEmpty()) {
                showWarning("Semua field harus diisi!");
                return;
            }
            // Validasi duplikasi plat nomor (kecuali data yang sedang diubah)
            for (Kendaraan kd : data) {
                if (kd != k && kd.getPlat().equalsIgnoreCase(plat)) {
                    showWarning("Plat nomor sudah terdaftar!");
                    return;
                }
            }
            k.setPlat(plat);
            k.setMerk(merk);
            k.setJenis(jenis);
            k.setTahun(tahun);
            saveKendaraanToCSV();
            tableKendaraan.refresh();
            showInfo("Data kendaraan berhasil diubah.");
            updateTablePage();
            clearForm();
        }
    }

    @FXML
    public void handleHapus() {
        Kendaraan k = tableKendaraan.getSelectionModel().getSelectedItem();
        if (k != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Yakin ingin menghapus data kendaraan ini?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Konfirmasi Hapus");
            alert.setHeaderText(null);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                data.remove(k);
                filteredData.remove(k);
                saveKendaraanToCSV();
                showInfo("Data kendaraan berhasil dihapus.");
                updateTablePage();
                clearForm();
            }
        }
    }

    private void clearForm() {
        tfPlat.clear();
        tfMerk.clear();
        tfJenis.clear();
        tfTahun.clear();
    }
    private void showWarning(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Peringatan");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
