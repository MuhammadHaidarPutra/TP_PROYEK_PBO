package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

public class LaporanController {
    @FXML private BarChart<String, Number> chartPenyewaan;

    @FXML
    public void initialize() {
        int motor = 0, mobil = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("src/assets/penyewaan.csv"))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    if (parts[1].equalsIgnoreCase("Motor")) motor++;
                    else if (parts[1].equalsIgnoreCase("Mobil")) mobil++;
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
    }
}
