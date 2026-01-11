package model;

import java.util.ArrayList;

public class SistemManajemen {

    // ===== ATRIBUT =====
    private ArrayList<Pelanggan> listPelanggan = new ArrayList<>();
    private ArrayList<Kendaraan> listKendaraan = new ArrayList<>();
    private ArrayList<Penyewaan> listRental = new ArrayList<>();

    // ===== PELANGGAN =====
    public void tambahPelanggan(Pelanggan p) {
        listPelanggan.add(p);
    }

    public Pelanggan cariPelanggan(String id) {
        for (Pelanggan p : listPelanggan) {
            if (p.getIdPelanggan().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public void tampilPelanggan() {
        for (Pelanggan p : listPelanggan) {
            System.out.println(p.getPelanggan());
            System.out.println("----------------------");
        }
    }

    // ===== KENDARAAN =====
    public void tambahKendaraan(Kendaraan k) {
        listKendaraan.add(k);
    }

    public Kendaraan cariKendaraan(String id) {
        for (Kendaraan k : listKendaraan) {
            if (k.getIdKendaraan().equals(id)) {
                return k;
            }
        }
        return null;
    }

    public void tampilKendaraan() {
        for (Kendaraan k : listKendaraan) {
            System.out.println(k.getInfo());
            System.out.println("----------------------");
        }
    }

    // ===== PENYEWAAN =====
    public void buatRental(String idSewa, Pelanggan p, Kendaraan k, int hari) {
        if (k.isKetersediaan()) {
            Penyewaan r = new Penyewaan(idSewa, p, k, hari);
            listRental.add(r);
            System.out.println("Penyewaan berhasil dibuat!");
        } else {
            System.out.println("Kendaraan sedang disewa.");
        }
    }

    public void kembalikanRental(String idSewa) {
        for (Penyewaan r : listRental) {
            if (r.getIdSewa().equals(idSewa)) {
                r.pengembalian();
                System.out.println("Kendaraan berhasil dikembalikan.");
                return;
            }
        }
        System.out.println("Data sewa tidak ditemukan.");
    }

    public void tampilRental() {
        for (Penyewaan r : listRental) {
            System.out.println(r.getInfo());
            System.out.println("----------------------");
        }
    }
}
