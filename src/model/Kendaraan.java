package model;

public class Kendaraan {
    protected String idKendaraan;
    protected String merek;
    protected String model;
    protected boolean tersedia;
    protected double hargaPerHari;

    public Kendaraan(String id, String merek, String model, double harga) {
        this.idKendaraan = id;
        this.merek = merek;
        this.model = model;
        this.hargaPerHari = harga;
        this.tersedia = true;
    }

    public double kalkulasiHarga(int hari) {
        return hargaPerHari * hari;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    public void setTersedia(boolean status) {
        this.tersedia = status;
    }

    public String getInfo() {
        return idKendaraan + " - " + merek + " " + model;
    }
}
