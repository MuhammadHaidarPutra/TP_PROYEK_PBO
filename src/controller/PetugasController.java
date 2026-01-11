package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import model.Petugas;

import java.io.*;
import java.util.Base64;

public class PetugasController {
        @FXML private TextArea outputArea;
    @FXML private TextField tfUsername;
    @FXML private PasswordField tfPassword;
    @FXML private TableView<Petugas> tablePetugas;
    @FXML private TableColumn<Petugas, String> colUsername;
    @FXML private TableColumn<Petugas, String> colPassword;
    private ObservableList<Petugas> data = FXCollections.observableArrayList();
    private final String CSV_PATH = "src/assets/petugas.csv";

    public PetugasController() {}

    @FXML
    public void initialize() {
        colUsername.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUsername()));
        colPassword.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPassword()));
        loadPetugasFromCSV();
        tablePetugas.setItems(data);
    }

    private void loadPetugasFromCSV() {
        data.clear();
        File file = new File(CSV_PATH);
        if (!file.exists()) {
            showWarning("File data petugas tidak ditemukan! Akan dibuat baru.");
            savePetugasToCSV();
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; } // skip header
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    data.add(new Petugas(username, password));
                }
            }
        } catch (IOException e) {
            showWarning("Gagal baca file data petugas: " + e.getMessage());
        }
    }

    private void savePetugasToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_PATH))) {
            bw.write("username,password\n");
            for (Petugas p : data) {
                bw.write(p.getUsername() + "," + p.getPassword() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Gagal simpan CSV petugas: " + e.getMessage());
        }
    }

    @FXML
    public void handleTambah() {
        String username = tfUsername.getText().trim();
        String password = tfPassword.getText().trim();
        // Validasi field kosong
        if (username.isEmpty() || password.isEmpty()) {
            showWarning("Username dan password harus diisi!");
            return;
        }
        // Validasi duplikasi username
        for (Petugas pet : data) {
            if (pet.getUsername().equalsIgnoreCase(username)) {
                showWarning("Username sudah terdaftar!");
                return;
            }
        }
        Petugas p = new Petugas(username, password);
        data.add(p);
        savePetugasToCSV();
        showInfo("Akun petugas berhasil ditambahkan.");
        clearForm();
    }

    @FXML
    public void handleUbah() {
        Petugas p = tablePetugas.getSelectionModel().getSelectedItem();
        if (p != null) {
            String username = tfUsername.getText().trim();
            String password = tfPassword.getText().trim();
            if (username.isEmpty() || password.isEmpty()) {
                showWarning("Username dan password harus diisi!");
                return;
            }
            // Validasi duplikasi username (kecuali untuk data yang sedang diubah)
            for (Petugas pet : data) {
                if (pet != p && pet.getUsername().equalsIgnoreCase(username)) {
                    showWarning("Username sudah terdaftar!");
                    return;
                }
            }
            p.setUsername(username);
            p.setPassword(password);
            savePetugasToCSV();
            tablePetugas.refresh();
            showInfo("Akun petugas berhasil diubah.");
            clearForm();
        }
    }

    @FXML
    public void handleHapus() {
        Petugas p = tablePetugas.getSelectionModel().getSelectedItem();
        if (p != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Yakin ingin menghapus akun petugas ini?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Konfirmasi Hapus");
            alert.setHeaderText(null);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                data.remove(p);
                savePetugasToCSV();
                showInfo("Akun petugas berhasil dihapus.");
                clearForm();
            }
        }
    }

    private void clearForm() {
        tfUsername.clear();
        tfPassword.clear();
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

    // Handler untuk tombol Input Penyewaan
    @FXML
    private void inputSewa() {
        if (outputArea != null) {
            outputArea.setText("[Input Penyewaan] Fitur belum diimplementasikan.");
        } else {
            showInfo("[Input Penyewaan] Fitur belum diimplementasikan.");
        }
    }

    // Handler untuk tombol Pengembalian
    @FXML
    private void pengembalian() {
        if (outputArea != null) {
            outputArea.setText("[Pengembalian] Fitur belum diimplementasikan.");
        } else {
            showInfo("[Pengembalian] Fitur belum diimplementasikan.");
        }
    }

    // Handler untuk tombol Lihat Kendaraan
    @FXML
    private void lihatKendaraan() {
        if (outputArea != null) {
            outputArea.setText("[Lihat Kendaraan] Fitur belum diimplementasikan.");
        } else {
            showInfo("[Lihat Kendaraan] Fitur belum diimplementasikan.");
        }
    }
}
