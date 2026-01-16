
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Kendaraan {
    private final StringProperty plat = new SimpleStringProperty();
    private final StringProperty merk = new SimpleStringProperty();
    private final StringProperty jenis = new SimpleStringProperty();
    private final StringProperty tahun = new SimpleStringProperty();
    protected boolean ketersediaan;
    protected double hargaPerHari;

    public Kendaraan(String plat, String merk, String jenis, String tahun) {
        this.plat.set(plat);
        this.merk.set(merk);
        this.jenis.set(jenis);
        this.tahun.set(tahun);
        this.ketersediaan = true;
    }

    public StringProperty platProperty() { 
        return plat;
     
    }

    public StringProperty merkProperty() {
        return merk; 
    }

    public StringProperty jenisProperty() {
        return jenis; 
    }

    public StringProperty tahunProperty() { 
        return tahun; 
    }

    public String getPlat() { 
        return plat.get(); 
    }

    public void setPlat(String value) {
        plat.set(value); 
    }

    public String getMerk() { 
        return merk.get();
    }
    public void setMerk(String value) { 
        merk.set(value);
    }

    public String getJenis() { 
        return jenis.get(); 
    }

    public void setJenis(String value) {
        jenis.set(value);
    }

    public String getTahun() { 
        return tahun.get();
    }
    public void setTahun(String value) {
        tahun.set(value); 
    }


    public double kalkulasiHarga(int hari) {
        return hargaPerHari * hari;
    }

    public String getInfo() {
        return "Plat: " + getPlat() +
               "\nMerk: " + getMerk() +
               "\nJenis: " + getJenis() +
               "\nTahun: " + getTahun() +
               "\nHarga/Hari: " + hargaPerHari +
               "\nStatus: " + (ketersediaan ? "Tersedia" : "Disewa");
    }

    
    public boolean isKetersediaan() {
        return ketersediaan;
    }
   
    public void setKetersediaan(boolean status) {
        this.ketersediaan = status;
    }

    public void setHargaPerHari(double harga) {
        this.hargaPerHari = harga;
    }

    public String getIdKendaraan() {
        return getPlat();
    }

    public String getMerek() {
        return getMerk();
    }
   
    public String getModel() {
        return getJenis();
    }

    public double getHargaPerHari() {
        return hargaPerHari;
    }
}