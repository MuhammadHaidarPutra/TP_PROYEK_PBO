package model;

public class Penyewaan {

    private String id;
    private int hari;
    private double totalBiaya;
    private String status;
    private Pelanggan pelanggan;
    private Kendaraan kendaraan;

    // JavaFX Properties
    private javafx.beans.property.StringProperty idSewaProperty = new javafx.beans.property.SimpleStringProperty();
    private javafx.beans.property.StringProperty statusProperty = new javafx.beans.property.SimpleStringProperty();
    private javafx.beans.property.DoubleProperty totalBiayaProperty = new javafx.beans.property.SimpleDoubleProperty();
    private javafx.beans.property.IntegerProperty hariProperty = new javafx.beans.property.SimpleIntegerProperty();

    public Penyewaan(String id, Pelanggan p, Kendaraan k, int hari) {
        this.id = id;
        this.pelanggan = p;
        this.kendaraan = k;
        this.hari = hari;
        this.status = "Disewa";
        this.idSewaProperty.set(id);
        this.statusProperty.set(status);
        this.hariProperty.set(hari);
        hitungTotal();
        this.totalBiayaProperty.set(totalBiaya);
    }

    public String getId() { return id; }
    public String getStatus() { return status; }
    public double getTotalBiaya() { return totalBiaya; }
    public Kendaraan getKendaraan() { return kendaraan; }
    public Pelanggan getPelanggan() { return pelanggan; }

    public javafx.beans.property.StringProperty idSewaProperty() { return idSewaProperty; }
    public javafx.beans.property.StringProperty statusProperty() { return statusProperty; }
    public javafx.beans.property.DoubleProperty totalBiayaProperty() { return totalBiayaProperty; }
    public javafx.beans.property.IntegerProperty hariProperty() { return hariProperty; }

    public void hitungTotal() {
        totalBiaya = kendaraan.kalkulasiHarga(hari);
        totalBiayaProperty.set(totalBiaya);
    }

    public void mulaiSewa() {
        kendaraan.setKetersediaan(false);
        setStatus("Disewa");
    }

    public void pengembalian() {
        setStatus("Selesai");
        kendaraan.setKetersediaan(true);
    }

    public void setStatus(String status) {
        this.status = status;
        this.statusProperty.set(status);
    }
}
