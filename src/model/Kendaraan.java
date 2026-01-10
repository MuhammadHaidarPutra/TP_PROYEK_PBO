package model;

public abstract class Kendaraan {

    protected String id_kendaraan;
    protected String merek;
    protected String model;
    protected boolean ketersediaan;
    protected double hargaPerhari;
    protected javafx.beans.property.StringProperty modelProperty = new javafx.beans.property.SimpleStringProperty();

    public Kendaraan(String id, String merek, String model, double harga) {
        this.id_kendaraan = id;
        this.merek = merek;
        this.model = model;
        this.hargaPerhari = harga;
        this.ketersediaan = true;
        this.modelProperty.set(model);
    }
    public String getId() { return id_kendaraan; }
    public void setStatus(String status) {
        this.ketersediaan = !status.equals("Disewa");
    }
    public javafx.beans.property.StringProperty modelProperty() { return modelProperty; }

    public double kalkulasiHarga(int hari) {
        return hari * hargaPerhari;
    }

    public boolean isKetersediaan() {
        return ketersediaan;
    }

    public void setKetersediaan(boolean status) {
        this.ketersediaan = status;
    }

    public abstract String getInfo();
}
