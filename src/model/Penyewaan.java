package model;

public class Penyewaan {
    private String idSewa;
    private int hari;
    private double totalBiaya;
    private String status;
    private Pelanggan pelanggan;
    private Kendaraan kendaraan;

    public Penyewaan(String id, Pelanggan p, Kendaraan k, int hari) {
        this.idSewa = id;
        this.pelanggan = p;
        this.kendaraan = k;
        this.hari = hari;
        this.status = "Disewa";
        hitungTotal();
        k.setTersedia(false);
    }

    public void hitungTotal() {
        totalBiaya = kendaraan.kalkulasiHarga(hari);
    }

    public void pengembalian() {
        status = "Selesai";
        kendaraan.setTersedia(true);
    }
}
