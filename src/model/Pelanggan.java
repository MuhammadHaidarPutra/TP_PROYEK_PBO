package model;

public class Pelanggan {

    // ===== ATRIBUT =====
    private String id_pelanggan;
    private String nama;
    private String no_telepon;

    // ===== CONSTRUCTOR =====
    public Pelanggan(String id_pelanggan, String nama, String no_telepon) {
        this.id_pelanggan = id_pelanggan;
        this.nama = nama;
        this.no_telepon = no_telepon;
    }

    // ===== METHOD =====

    // Mengembalikan data pelanggan dalam bentuk teks
    public String getPelanggan() {
        return "ID Pelanggan : " + id_pelanggan +
               "\nNama         : " + nama +
               "\nNo Telepon   : " + no_telepon;
    }

    // Mengubah nama pelanggan
    public void setNama(String nama) {
        this.nama = nama;
    }

    // Mengubah nomor telepon
    public void setNo_telepon(String no_telepon) {
        this.no_telepon = no_telepon;
    }

    // ===== GETTER TAMBAHAN (opsional) =====
    public String getIdPelanggan() {
        return id_pelanggan;
    }

    public String getNama() {
        return nama;
    }

    public String getNoTelepon() {
        return no_telepon;
    }
}
