package model;

public class Pelanggan {
    private String id_pelanggan;
    private String nama;
    private String no_telepon;
    private String alamat;

    public Pelanggan(String id_pelanggan, String nama, String no_telepon, String alamat) {
        this.id_pelanggan = id_pelanggan;
        this.nama = nama;
        this.no_telepon = no_telepon;
        this.alamat = alamat;
    }

    public String getPelanggan() {
        return "ID Pelanggan : " + id_pelanggan +
               "\nNama         : " + nama +
               "\nNo Telepon   : " + no_telepon +
               "\nAlamat       : " + alamat;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNo_telepon(String no_telepon) {
        this.no_telepon = no_telepon;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getIdPelanggan() {
        return id_pelanggan;
    }

    public String getNama() {
        return nama;
    }

    public String getNoTelepon() {
        return no_telepon;
    }
    
    public String getAlamat() {
        return alamat;
    }
}
