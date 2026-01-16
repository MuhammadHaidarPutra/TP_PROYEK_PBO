package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;

public class LaporanController {
    @FXML private BarChart<String, Number> chartPenyewaan;
    @FXML private TableView<ReportRow> tableLaporan;
    @FXML private TableColumn<ReportRow, String> colJenis;
    @FXML private TableColumn<ReportRow, String> colTanggal;
    @FXML private TableColumn<ReportRow, String> colKeterangan;
    @FXML private TableColumn<ReportRow, String> colStatus;
    public static class ReportRow {
        private final SimpleStringProperty jenis;
        private final SimpleStringProperty tanggal;
        private final SimpleStringProperty keterangan;
        private final SimpleStringProperty status;
        public ReportRow(String jenis, String tanggal, String keterangan, String status) {
            this.jenis = new SimpleStringProperty(jenis);
            this.tanggal = new SimpleStringProperty(tanggal);
            this.keterangan = new SimpleStringProperty(keterangan);
            this.status = new SimpleStringProperty(status);
        }
        public String getJenis() { return jenis.get(); }
        public String getTanggal() { return tanggal.get(); }
        public String getKeterangan() { return keterangan.get(); }
        public String getStatus() { return status.get(); }
    }

    @FXML
    public void initialize() {
        // Siapkan TableView
        colJenis.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJenis()));
        colTanggal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTanggal()));
        colKeterangan.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKeterangan()));
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        ObservableList<ReportRow> laporanList = FXCollections.observableArrayList();
        int motor = 0, mobil = 0;
        java.util.Map<String, String> idToJenis = new java.util.HashMap<>();

        // Baca jenis kendaraan
        try (BufferedReader br = new BufferedReader(new FileReader("src/assets/kendaraan.csv"))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    idToJenis.put(parts[0].trim(), parts[2].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal baca kendaraan.csv: " + e.getMessage());
        }

        // Baca penyewaan.csv dan cari index kolom tanggal jika ada
        try (BufferedReader br = new BufferedReader(new FileReader("src/assets/penyewaan.csv"))) {
            String line;
            int idxTanggal = -1;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (first) {
                    // Cek header untuk kolom tanggal
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].trim().equalsIgnoreCase("tanggal")) {
                            idxTanggal = i;
                            break;
                        }
                    }
                    first = false;
                    continue;
                }
                if (parts.length >= 8) {
                    String idKendaraan = parts[5].trim();
                    String jenis = idToJenis.getOrDefault(idKendaraan, "");
                    String hari = parts[6].trim();
                    String status = parts[7].trim();
                    String nama = parts[2].trim();
                    String tanggal = "-";
                    if (idxTanggal != -1 && parts.length > idxTanggal) {
                        tanggal = parts[idxTanggal].trim();
                    }
                    // Format tanggal jika ada
                    if (!tanggal.equals("-") && !tanggal.isEmpty()) {
                        try {
                            java.time.LocalDate date = java.time.LocalDate.parse(tanggal);
                            java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            tanggal = date.format(fmt);
                        } catch (Exception e) { /* ignore */ }
                    }
                    String keterangan = nama + " menyewa " + jenis + " selama " + hari + " hari";
                    laporanList.add(new ReportRow("Penyewaan", tanggal, keterangan, status));
                    if (jenis.equalsIgnoreCase("Motor")) motor++;
                    else if (jenis.equalsIgnoreCase("Mobil")) mobil++;
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal baca penyewaan.csv: " + e.getMessage());
        }
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Penyewaan");
        series.getData().add(new XYChart.Data<>("Motor", motor));
        series.getData().add(new XYChart.Data<>("Mobil", mobil));
        chartPenyewaan.getData().clear();
        chartPenyewaan.getData().add(series);
        tableLaporan.setItems(laporanList);
    }
}
