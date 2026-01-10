package service;

import model.Kendaraan;
import model.Penyewaan;
import java.util.ArrayList;

public class SistemManajemen {
    private ArrayList<Kendaraan> listKendaraan = new ArrayList<>();
    private ArrayList<Penyewaan> listSewa = new ArrayList<>();

    public void tambahKendaraan(Kendaraan k) {
        listKendaraan.add(k);
    }

    public void tampilKendaraan() {
        for (Kendaraan k : listKendaraan) {
            System.out.println(k.getInfo() + " | Tersedia: " + k.isTersedia());
        }
    }
}
