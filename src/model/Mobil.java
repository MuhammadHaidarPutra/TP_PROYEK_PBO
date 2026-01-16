package model;

public class Mobil extends Kendaraan {
    private int jumlahKursi;

    public Mobil(String plat, String merk, String jenis, String tahun, int kursi) {
        super(plat, merk, jenis, tahun);
        this.jumlahKursi = kursi;
        this.hargaPerHari = 120000;
    }

    public int getJumlahKursi() {
        return jumlahKursi;
    }

    public void setJumlahKursi(int kursi) {
        this.jumlahKursi = kursi;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + "\nJumlah Kursi: " + jumlahKursi;
    }
}
