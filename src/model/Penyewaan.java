package model;

public class Penyewaan {


    private String id_sewa;
    private int hari;
    private double totalBiaya;
    private String status; 
    private Pelanggan pelanggan;
    private Kendaraan kendaraan;

    // ===== CONSTRUCTOR =====
    public Penyewaan(String id_sewa, Pelanggan pelanggan, Kendaraan kendaraan, int hari) {
        this.id_sewa = id_sewa;
        this.pelanggan = pelanggan;
        this.kendaraan = kendaraan;
        this.hari = hari;
        this.status = "Disewa";
        hitungTotal();
        mulaiSewa();
    }


    public void hitungTotal() {
        this.totalBiaya = kendaraan.kalkulasiHarga(hari);
    }

    public void mulaiSewa() {
        kendaraan.setKetersediaan(false);
    }

    public void pengembalian() {
        kendaraan.setKetersediaan(true);
        status = "Selesai";
    }

    // Info penyewaan
    public String getInfo() {
        return "ID Sewa: " + id_sewa +
               "\nPelanggan: " + pelanggan.getNama() +
               "\nKendaraan: " + kendaraan.getMerek() + " " + kendaraan.getModel() +
               "\nHari: " + hari +
               "\nTotal: " + totalBiaya +
               "\nStatus: " + status;
    }

    // ===== GETTER =====
    public String getIdSewa() { return id_sewa; }
    public String getStatus() { return status; }
    public Kendaraan getKendaraan() { return kendaraan; }
    public Pelanggan getPelanggan() { return pelanggan; }
    public int getHari() { return hari; }
    public double getTotalBiaya() { return totalBiaya; }
    
    // ===== SETTER =====
    public void setStatus(String status) { this.status = status; }
}
