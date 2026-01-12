package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Kendaraan;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class KendaraanController {
    @FXML private TextField tfPlat, tfSearch;
    @FXML private ComboBox<String> cbJenis, cbMerk, cbTahun;
    @FXML private TableView<Kendaraan> tableKendaraan;
    @FXML private TableColumn<Kendaraan, String> colPlat, colMerk, colJenis, colTahun;

    private ObservableList<Kendaraan> data = FXCollections.observableArrayList();
    private ObservableList<Kendaraan> filteredData = FXCollections.observableArrayList();
    private final String CSV_PATH = "src/assets/kendaraan.csv";
    
    // Data Dinamis
    private Map<String, List<String>> mapMerk = new HashMap<>();
    private Map<String, List<String>> mapTahun = new HashMap<>();

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

        setupDataKendaraan();
        loadKendaraanFromCSV();
        updateTablePage();
        
        // Listener saat memilih baris tabel
        tableKendaraan.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillForm(newSelection);
            }
        });
    }

    private void setupDataKendaraan() {
        // Init Jenis
        cbJenis.setItems(FXCollections.observableArrayList("Mobil", "Motor"));

        // Init Data Merk (Indonesia)
        List<String> merkMobil = Arrays.asList(
            "Toyota", "Honda", "Daihatsu", "Suzuki", "Mitsubishi", "Nissan", "Mazda", 
            "Wuling", "Hyundai", "KIA", "BMW", "Mercedes-Benz", "Lexus", "Isuzu", "Chery"
        );
        List<String> merkMotor = Arrays.asList(
            "Honda", "Yamaha", "Suzuki", "Kawasaki", "Vespa", "TVS", "Viar", 
            "Ducati", "BMW Motorrad", "KTM", "Royal Enfield", "Harley-Davidson"
        );

        mapMerk.put("Mobil", merkMobil);
        mapMerk.put("Motor", merkMotor);

        // Listener Jenis -> Update Merk
        cbJenis.setOnAction(e -> {
            String selectedJenis = cbJenis.getValue();
            if (selectedJenis != null && mapMerk.containsKey(selectedJenis)) {
                cbMerk.setItems(FXCollections.observableArrayList(mapMerk.get(selectedJenis)));
                cbMerk.setDisable(false);
            } else {
                cbMerk.getItems().clear();
                cbMerk.setDisable(true);
            }
            cbTahun.getItems().clear();
            cbTahun.setDisable(true);
        });

        // Listener Merk -> Update Tahun
        cbMerk.setOnAction(e -> {
            String selectedMerk = cbMerk.getValue();
            if (selectedMerk != null) {
                updateTahunList(selectedMerk);
                cbTahun.setDisable(false);
            } else {
                cbTahun.getItems().clear();
                cbTahun.setDisable(true);
            }
        });
    }

    private void updateTahunList(String merk) {
        List<String> years = new ArrayList<>();
        int startYear = 1990; // Default start year
        int endYear = 2026;   // Current year

        // Custom start years based on brand entry in Indonesia (approximate)
        if (merk.equals("Wuling") || merk.equals("Hyundai") || merk.equals("Chery")) startYear = 2015;
        if (merk.equals("TVS") || merk.equals("Viar")) startYear = 2010;

        for (int i = endYear; i >= startYear; i--) {
            years.add(String.valueOf(i));
        }
        cbTahun.setItems(FXCollections.observableArrayList(years));
    }

    private void fillForm(Kendaraan k) {
        tfPlat.setText(k.getPlat());
        cbJenis.setValue(k.getJenis());
        // Trigger listener manual if needed, but setValue usually triggers checks
        cbMerk.setValue(k.getMerk());
        cbTahun.setValue(k.getTahun());
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
        String merk = cbMerk.getValue();
        String jenis = cbJenis.getValue();
        String tahun = cbTahun.getValue();
        
        // Validasi field kosong
        if (plat.isEmpty() || merk == null || jenis == null || tahun == null) {
            showWarning("Semua field harus dipilih/diisi!");
            return;
        }
        // Validasi format plat nomor (minimal 5 karakter, ada huruf dan angka)
        if (!plat.matches(".*[A-Za-z].*") || !plat.matches(".*\\d.*") || plat.length() < 5) {
            showWarning("Format plat nomor tidak valid!");
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
        handleSearch(); 
        clearForm();
    }

    @FXML
    public void handleUbah() {
        Kendaraan k = tableKendaraan.getSelectionModel().getSelectedItem();
        if (k != null) {
            String plat = tfPlat.getText().trim();
            String merk = cbMerk.getValue();
            String jenis = cbJenis.getValue();
            String tahun = cbTahun.getValue();
            
            if (plat.isEmpty() || merk == null || jenis == null || tahun == null) {
                showWarning("Semua field harus dipilih/diisi!");
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
        cbJenis.getSelectionModel().clearSelection();
        cbMerk.getItems().clear();
        cbTahun.getItems().clear();
        cbMerk.setDisable(true);
        cbTahun.setDisable(true);
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
