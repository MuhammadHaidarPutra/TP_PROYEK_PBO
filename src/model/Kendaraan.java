package model;

public abstract class Kendaraan {

    // ====== ATRIBUT ======
    protected String id_kendaraan;
    protected String merek;
    protected String model;
    protected boolean ketersediaan;
    protected double hargaPerHari;

    // ====== CONSTRUCTOR ======
    public Kendaraan(String id_kendaraan, String merek, String model, double hargaPerHari) {
        this.id_kendaraan = id_kendaraan;
        this.merek = merek;
        this.model = model;
        this.hargaPerHari = hargaPerHari;
        this.ketersediaan = true; // default tersedia
    }

    // ====== METHOD ======

    // Hitung total harga sewa
    public double kalkulasiHarga(int hari) {
        return hargaPerHari * hari;
    }

    // Info kendaraan
    public String getInfo() {
        return "ID: " + id_kendaraan +
               "\nMerek: " + merek +
               "\nModel: " + model +
               "\nHarga/Hari: " + hargaPerHari +
               "\nStatus: " + (ketersediaan ? "Tersedia" : "Disewa");
    }

    // Cek ketersediaan
    public boolean isKetersediaan() {
        return ketersediaan;
    }

    // Set ketersediaan
    public void setKetersediaan(boolean status) {
        this.ketersediaan = status;
    }

    // Getter tambahan
    public String getIdKendaraan() {
        return id_kendaraan; 
    }
    
    public String getMerek() {
        return merek;
    }

    public String getModel() {
        return model;
    }
    
    public double getHargaPerHari() {
        return hargaPerHari;
    }
}