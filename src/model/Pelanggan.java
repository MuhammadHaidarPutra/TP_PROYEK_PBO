package model;

public class Pelanggan {

    private String id_pelanggan;
    private String nama;
    private String no_telepon;
    private javafx.beans.property.StringProperty namaProperty = new javafx.beans.property.SimpleStringProperty();

    public Pelanggan(String id, String nama, String telp) {
        this.id_pelanggan = id;
        this.nama = nama;
        this.no_telepon = telp;
        this.namaProperty.set(nama);
    }

    public String getPelanggan() {
        return nama + " (" + no_telepon + ")";
    }

    public String getId() { return id_pelanggan; }
    public javafx.beans.property.StringProperty namaProperty() { return namaProperty; }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNo_telepon(String telp) {
        this.no_telepon = telp;
    }
}
