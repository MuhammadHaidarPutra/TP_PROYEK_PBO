package model;

public class Mobil extends Kendaraan {

    private int jumlahKursi;

    public Mobil(String id, String merek, String model, double harga, int kursi) {
        super(id, merek, model, harga);
        this.jumlahKursi = kursi;
    }

    @Override
    public String getInfo() {
        return "Mobil - " + merek + " " + model +
               " | Kursi: " + jumlahKursi +
               " | Harga/Hari: " + hargaPerhari;
    }
}
