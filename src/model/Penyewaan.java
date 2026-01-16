package model;

public class Penyewaan {
    private String tanggal;


    private String id_sewa;
    private int hari;
    private double totalBiaya;
    private String status; 
    private Pelanggan pelanggan;
    private Kendaraan kendaraan;

    public Penyewaan(String id_sewa, Pelanggan pelanggan, Kendaraan kendaraan, int hari) {
        this.id_sewa = id_sewa;
        this.pelanggan = pelanggan;
        this.kendaraan = kendaraan;
        this.hari = hari;
        this.status = "Disewa";
        this.tanggal = null;
        hitungTotal();
        mulaiSewa();
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTanggal() {
        return tanggal;
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

    public String getInfo() {
        return "ID Sewa: " + id_sewa +
               "\nPelanggan: " + pelanggan.getNama() +
               "\nKendaraan: " + kendaraan.getMerek() + " " + kendaraan.getModel() +
               "\nHari: " + hari +
               "\nTotal: " + totalBiaya +
               "\nStatus: " + status;
    }

    public String getIdSewa() { return id_sewa; }
    public String getStatus() { return status; }
    public Kendaraan getKendaraan() { return kendaraan; }
    public Pelanggan getPelanggan() { return pelanggan; }
    public int getHari() { return hari; }
    public double getTotalBiaya() { return totalBiaya; }

    public void setStatus(String status) { this.status = status; }
}
