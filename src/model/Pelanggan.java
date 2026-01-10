package model;

public class Pelanggan {
    private String idPelanggan;
    private String nama;
    private String noTelepon;

    public Pelanggan(String id, String nama, String telp) {
        this.idPelanggan = id;
        this.nama = nama;
        this.noTelepon = telp;
    }

    public String getPelanggan() {
        return nama + " (" + noTelepon + ")";
    }
}
