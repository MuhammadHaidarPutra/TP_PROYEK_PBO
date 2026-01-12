package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class PetugasDashboardController {
    @FXML private Label lblUserInfo;
    @FXML private StackPane contentArea;
    @FXML private Button btnInputSewa;
    @FXML private Button btnPengembalian;
    @FXML private Button btnLihatKendaraan;
    
    private ArrayList<Penyewaan> listPenyewaan = new ArrayList<>();
    private ArrayList<Kendaraan> listKendaraan = new ArrayList<>();
    
    private final String CSV_PENYEWAAN = "src/assets/penyewaan.csv";
    private final String CSV_KENDARAAN = "src/assets/kendaraan.csv";
    
    @FXML
    public void initialize() {
        // Set user info
        if (Session.userAktif != null) {
            lblUserInfo.setText("Petugas: " + Session.userAktif.getUsername());
        }
        
        // Load data
        loadKendaraanFromCSV();
        loadPenyewaanFromCSV();
        
        // Show default page
        showHome();
    }

    private void showHome() {
        if (btnInputSewa != null) btnInputSewa.getStyleClass().remove("active");
        if (btnPengembalian != null) btnPengembalian.getStyleClass().remove("active");
        if (btnLihatKendaraan != null) btnLihatKendaraan.getStyleClass().remove("active");

        VBox container = new VBox(20);
        container.setAlignment(javafx.geometry.Pos.CENTER);
        container.setPadding(new Insets(30));
        
        Label welcome = new Label("Selamat Datang, " + (Session.userAktif != null ? Session.userAktif.getUsername() : "Petugas"));
        welcome.setStyle("-fx-font-size: 28; -fx-font-weight: bold;");
        
        Label sub = new Label("Gunakan menu di samping untuk mengelola penyewaan.");
        sub.setStyle("-fx-font-size: 16;");
        
        container.getChildren().addAll(welcome, sub);
        
        contentArea.getChildren().clear();
        contentArea.getChildren().add(container);
    }
    
    // ========== LOAD DATA ==========
    private void loadKendaraanFromCSV() {
        listKendaraan.clear();
        File file = new File(CSV_KENDARAAN);
        if (!file.exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_KENDARAAN))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    // Format: plat,merk,jenis,tahun
                    Kendaraan k = new Kendaraan(parts[0], parts[1], parts[2], parts[3]);
                    listKendaraan.add(k);
                }
            }
        } catch (IOException e) {
            showAlert("Error", "Gagal memuat data kendaraan: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void loadPenyewaanFromCSV() {
        listPenyewaan.clear();
        File file = new File(CSV_PENYEWAAN);
        if (!file.exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PENYEWAAN))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    try {
                        String idSewa = parts[0];
                        String idPelanggan = parts[1];
                        String namaPelanggan = parts[2];
                        String noTelp = parts[3];
                        
                        // Handle format change (with/without alamat)
                        String alamat = "-";
                        String idKendaraan;
                        int hari;
                        String status;
                        
                        if (parts.length >= 8) {
                            // New format: ..., noTelp, alamat, idKendaraan, hari, status
                            alamat = parts[4];
                            idKendaraan = parts[5];
                            hari = Integer.parseInt(parts[6]);
                            status = parts[7];
                        } else {
                            // Old format: ..., noTelp, idKendaraan, hari, status
                            idKendaraan = parts[4];
                            hari = Integer.parseInt(parts[5]);
                            status = parts.length > 6 ? parts[6] : "Disewa";
                        }
                        
                        Pelanggan pelanggan = new Pelanggan(idPelanggan, namaPelanggan, noTelp, alamat);
                        Kendaraan kendaraan = cariKendaraan(idKendaraan);
                        
                        if (kendaraan != null) {
                            Penyewaan sewa = new Penyewaan(idSewa, pelanggan, kendaraan, hari);
                            if (status.equals("Selesai")) {
                                sewa.setStatus("Selesai");
                                kendaraan.setKetersediaan(true); // Ensure status is synced
                            } else {
                                kendaraan.setKetersediaan(false);
                            }
                            listPenyewaan.add(sewa);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            showAlert("Error", "Gagal memuat data penyewaan: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void savePenyewaanToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_PENYEWAAN))) {
            bw.write("id_sewa,id_pelanggan,nama_pelanggan,no_telp,alamat,id_kendaraan,hari,status\n");
            for (Penyewaan p : listPenyewaan) {
                String line = p.getIdSewa() + "," +
                             p.getPelanggan().getIdPelanggan() + "," +
                             p.getPelanggan().getNama() + "," +
                             p.getPelanggan().getNoTelepon() + "," +
                             p.getPelanggan().getAlamat() + "," +
                             p.getKendaraan().getIdKendaraan() + "," +
                             p.getHari() + "," +
                             p.getStatus() + "\n";
                bw.write(line);
            }
        } catch (IOException e) {
            showAlert("Error", "Gagal menyimpan data penyewaan", Alert.AlertType.ERROR);
        }
    }
    
    private void saveKendaraanToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_KENDARAAN))) {
            bw.write("plat,merk,jenis,tahun\n");
            for (Kendaraan k : listKendaraan) {
                bw.write(k.getPlat() + "," + k.getMerk() + "," + k.getJenis() + "," + k.getTahun() + "\n");
            }
        } catch (IOException e) {
            showAlert("Error", "Gagal menyimpan data kendaraan", Alert.AlertType.ERROR);
        }
    }
    
    private Kendaraan cariKendaraan(String id) {
        for (Kendaraan k : listKendaraan) {
            if (k.getIdKendaraan().equals(id)) {
                return k;
            }
        }
        return null;
    }
    
    // ========== NAVIGATION ==========
    @FXML
    public void showInputSewa() {
        setActiveButton(btnInputSewa);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(createInputSewaView());
    }
    
    @FXML
    public void showPengembalian() {
        setActiveButton(btnPengembalian);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(createPengembalianView());
    }
    
    @FXML
    public void showLihatKendaraan() {
        setActiveButton(btnLihatKendaraan);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(createLihatKendaraanView());
    }
    
    private void setActiveButton(Button activeBtn) {
        btnInputSewa.getStyleClass().remove("active");
        btnPengembalian.getStyleClass().remove("active");
        btnLihatKendaraan.getStyleClass().remove("active");
        activeBtn.getStyleClass().add("active");
    }
    
    private String generateNextPelangganId() {
        int maxId = 0;
        for (Penyewaan p : listPenyewaan) {
            String id = p.getPelanggan().getIdPelanggan();
            if (id != null && id.startsWith("P")) {
                try {
                    int num = Integer.parseInt(id.substring(1));
                    if (num > maxId) maxId = num;
                } catch (NumberFormatException e) {}
            }
        }
        return String.format("P%03d", maxId + 1);
    }

    // ========== INPUT PENYEWAAN ==========
    private VBox createInputSewaView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30));
        container.setStyle("-fx-background-color: white;");
        
        Label title = new Label("📝 Input Penyewaan Kendaraan");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2d62ed;");
        
        // Form
        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 10;");
        
        // Data Pelanggan
        Label lblPelanggan = new Label("DATA PELANGGAN");
        lblPelanggan.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        form.add(lblPelanggan, 0, 0, 2, 1);
        
        form.add(new Label("ID Pelanggan:"), 0, 1);
        TextField tfIdPelanggan = new TextField(generateNextPelangganId());
        tfIdPelanggan.setEditable(false);
        tfIdPelanggan.setStyle("-fx-background-color: #e9ecef;");
        form.add(tfIdPelanggan, 1, 1);
        
        form.add(new Label("Nama Pelanggan:"), 0, 2);
        TextField tfNamaPelanggan = new TextField();
        tfNamaPelanggan.setPromptText("Nama lengkap");
        form.add(tfNamaPelanggan, 1, 2);
        
        form.add(new Label("No. Telepon:"), 0, 3);
        TextField tfNoTelp = new TextField();
        tfNoTelp.setPromptText("08xxxxxxxxxx");
        form.add(tfNoTelp, 1, 3);

        form.add(new Label("Alamat:"), 0, 4);
        TextField tfAlamat = new TextField();
        tfAlamat.setPromptText("Alamat lengkap");
        form.add(tfAlamat, 1, 4);
        
        // Separator
        Separator sep = new Separator();
        form.add(sep, 0, 5, 2, 1);
        
        // Data Kendaraan
        Label lblKendaraan = new Label("DATA KENDARAAN");
        lblKendaraan.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        form.add(lblKendaraan, 0, 6, 2, 1);
        
        form.add(new Label("ID Kendaraan:"), 0, 7);
        ComboBox<String> cbKendaraan = new ComboBox<>();
        for (Kendaraan k : listKendaraan) {
            if (k.isKetersediaan()) {
                cbKendaraan.getItems().add(k.getIdKendaraan() + " - " + k.getMerek() + " " + k.getJenis());
            }
        }
        form.add(cbKendaraan, 1, 7);
        
        form.add(new Label("Lama Sewa (hari):"), 0, 8);
        TextField tfHari = new TextField();
        tfHari.setPromptText("Jumlah hari");
        form.add(tfHari, 1, 8);
        
        // Button
        Button btnSimpan = new Button("💾 Simpan Penyewaan");
        btnSimpan.setStyle("-fx-background-color: #2d62ed; -fx-text-fill: white; " +
                          "-fx-font-size: 14px; -fx-padding: 10 30; -fx-cursor: hand;");
        btnSimpan.setOnAction(e -> {
            String idPelanggan = tfIdPelanggan.getText().trim();
            String nama = tfNamaPelanggan.getText().trim();
            String noTelp = tfNoTelp.getText().trim();
            String alamat = tfAlamat.getText().trim();
            String kendaraanStr = cbKendaraan.getValue();
            String hariStr = tfHari.getText().trim();
            
            // Validasi
            if (idPelanggan.isEmpty() || nama.isEmpty() || noTelp.isEmpty() || alamat.isEmpty() ||
                kendaraanStr == null || hariStr.isEmpty()) {
                showAlert("Peringatan", "Semua field harus diisi!", Alert.AlertType.WARNING);
                return;
            }
            
            try {
                int hari = Integer.parseInt(hariStr);
                if (hari <= 0) {
                    showAlert("Peringatan", "Lama sewa harus lebih dari 0 hari!", Alert.AlertType.WARNING);
                    return;
                }
                
                String idKendaraan = kendaraanStr.split(" - ")[0];
                Kendaraan kendaraan = cariKendaraan(idKendaraan);
                
                if (kendaraan == null || !kendaraan.isKetersediaan()) {
                    showAlert("Error", "Kendaraan tidak tersedia!", Alert.AlertType.ERROR);
                    return;
                }
                
                // Buat penyewaan
                String idSewa = "S" + String.format("%03d", listPenyewaan.size() + 1);
                Pelanggan pelanggan = new Pelanggan(idPelanggan, nama, noTelp, alamat);
                Penyewaan sewa = new Penyewaan(idSewa, pelanggan, kendaraan, hari);
                
                listPenyewaan.add(sewa);
                savePenyewaanToCSV();
                saveKendaraanToCSV();
                
                showAlert("Sukses", "Penyewaan berhasil dicatat!\n\n" + sewa.getInfo(), Alert.AlertType.INFORMATION);
                
                // Clear form
                tfIdPelanggan.setText(generateNextPelangganId());
                tfNamaPelanggan.clear();
                tfNoTelp.clear();
                tfAlamat.clear();
                cbKendaraan.setValue(null);
                tfHari.clear();
                
                // Refresh combo
                cbKendaraan.getItems().clear();
                for (Kendaraan k : listKendaraan) {
                    if (k.isKetersediaan()) {
                        cbKendaraan.getItems().add(k.getIdKendaraan() + " - " + k.getMerek() + " " + k.getJenis());
                    }
                }
                
            } catch (NumberFormatException ex) {
                showAlert("Error", "Lama sewa harus berupa angka!", Alert.AlertType.ERROR);
            }
        });
        
        HBox buttonBox = new HBox(btnSimpan);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        
        container.getChildren().addAll(title, form, buttonBox);
        return container;
    }
    
    // ========== PENGEMBALIAN ==========
    private VBox createPengembalianView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30));
        container.setStyle("-fx-background-color: white;");
        
        Label title = new Label("↩️ Pengembalian Kendaraan");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2d62ed;");
        
        // Table
        TableView<Penyewaan> table = new TableView<>();
        table.setStyle("-fx-background-color: white;");
        
        TableColumn<Penyewaan, String> colIdSewa = new TableColumn<>("ID Sewa");
        colIdSewa.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdSewa()));
        colIdSewa.setPrefWidth(100);
        
        TableColumn<Penyewaan, String> colPelanggan = new TableColumn<>("Pelanggan");
        colPelanggan.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPelanggan().getNama()));
        colPelanggan.setPrefWidth(150);
        
        TableColumn<Penyewaan, String> colKendaraan = new TableColumn<>("Kendaraan");
        colKendaraan.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            data.getValue().getKendaraan().getMerek() + " " + data.getValue().getKendaraan().getJenis()));
        colKendaraan.setPrefWidth(200);
        
        TableColumn<Penyewaan, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));
        colStatus.setPrefWidth(100);
        
        table.getColumns().addAll(colIdSewa, colPelanggan, colKendaraan, colStatus);
        
        // Load data - hanya yang masih disewa
        ObservableList<Penyewaan> dataDisewa = FXCollections.observableArrayList();
        for (Penyewaan p : listPenyewaan) {
            if (p.getStatus().equals("Disewa")) {
                dataDisewa.add(p);
            }
        }
        table.setItems(dataDisewa);
        
        // Button
        Button btnKembalikan = new Button("✓ Kembalikan Kendaraan");
        btnKembalikan.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; " +
                               "-fx-font-size: 14px; -fx-padding: 10 30; -fx-cursor: hand;");
        btnKembalikan.setOnAction(e -> {
            Penyewaan selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Peringatan", "Pilih penyewaan yang akan dikembalikan!", Alert.AlertType.WARNING);
                return;
            }
            
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Konfirmasi");
            confirm.setHeaderText("Kembalikan kendaraan?");
            confirm.setContentText("Yakin ingin mengembalikan:\n" + 
                                  selected.getKendaraan().getMerek() + " " + selected.getKendaraan().getJenis());
            
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                selected.pengembalian();
                savePenyewaanToCSV();
                saveKendaraanToCSV();
                
                showAlert("Sukses", "Kendaraan berhasil dikembalikan!\nStatus kendaraan: Tersedia", Alert.AlertType.INFORMATION);
                
                // Refresh table
                dataDisewa.clear();
                for (Penyewaan p : listPenyewaan) {
                    if (p.getStatus().equals("Disewa")) {
                        dataDisewa.add(p);
                    }
                }
            }
        });
        
        HBox buttonBox = new HBox(btnKembalikan);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        
        container.getChildren().addAll(title, table, buttonBox);
        VBox.setVgrow(table, Priority.ALWAYS);
        
        return container;
    }
    
    // ========== LIHAT KENDARAAN ==========
    private VBox createLihatKendaraanView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30));
        container.setStyle("-fx-background-color: white;");
        
        Label title = new Label("🚗 Data Kendaraan");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2d62ed;");
        
        // Filter
        HBox filterBox = new HBox(10);
        filterBox.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 15; -fx-background-radius: 10;");
        
        Label lblFilter = new Label("Filter:");
        lblFilter.setStyle("-fx-font-weight: bold;");
        
        ComboBox<String> cbFilter = new ComboBox<>();
        cbFilter.getItems().addAll("Semua", "Tersedia", "Disewa");
        cbFilter.setValue("Semua");
        
        filterBox.getChildren().addAll(lblFilter, cbFilter);
        
        // Table
        TableView<Kendaraan> table = new TableView<>();
        table.setStyle("-fx-background-color: white;");
        
        TableColumn<Kendaraan, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdKendaraan()));
        colId.setPrefWidth(80);
        
        TableColumn<Kendaraan, String> colJenis = new TableColumn<>("Jenis");
        colJenis.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getJenis()));
        colJenis.setPrefWidth(100);
        
        TableColumn<Kendaraan, String> colMerek = new TableColumn<>("Merek");
        colMerek.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMerek()));
        colMerek.setPrefWidth(150);
        
        TableColumn<Kendaraan, String> colTahun = new TableColumn<>("Tahun");
        colTahun.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTahun()));
        colTahun.setPrefWidth(120);
        
        TableColumn<Kendaraan, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            data.getValue().isKetersediaan() ? "Tersedia" : "Disewa"));
        colStatus.setPrefWidth(120);
        
        table.getColumns().addAll(colId, colJenis, colMerek, colTahun, colStatus);
        
        // Load data
        ObservableList<Kendaraan> dataKendaraan = FXCollections.observableArrayList(listKendaraan);
        table.setItems(dataKendaraan);
        
        // Filter listener
        cbFilter.valueProperty().addListener((obs, old, newVal) -> {
            dataKendaraan.clear();
            if (newVal.equals("Semua")) {
                dataKendaraan.addAll(listKendaraan);
            } else if (newVal.equals("Tersedia")) {
                for (Kendaraan k : listKendaraan) {
                    if (k.isKetersediaan()) dataKendaraan.add(k);
                }
            } else if (newVal.equals("Disewa")) {
                for (Kendaraan k : listKendaraan) {
                    if (!k.isKetersediaan()) dataKendaraan.add(k);
                }
            }
        });
        
        // Info
        Label lblInfo = new Label();
        updateInfoLabel(lblInfo);
        lblInfo.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
        
        container.getChildren().addAll(title, filterBox, table, lblInfo);
        VBox.setVgrow(table, Priority.ALWAYS);
        
        return container;
    }
    
    private void updateInfoLabel(Label lbl) {
        int total = listKendaraan.size();
        int tersedia = 0;
        for (Kendaraan k : listKendaraan) {
            if (k.isKetersediaan()) tersedia++;
        }
        lbl.setText(String.format("Total: %d kendaraan | Tersedia: %d | Disewa: %d", total, tersedia, total - tersedia));
    }
    
    // ========== LOGOUT ==========
    @FXML
    public void logout() {
        try {
            Session.userAktif = null;
            Stage stage = (Stage) contentArea.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/style/login.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // ========== UTILITY ==========
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
