
package model;

import java.util.ArrayList;

public class Petugas extends User {
    private ArrayList<Petugas> listPetugas = new ArrayList<>();

    public Petugas(String u, String p) {
        super(u, p, "Petugas");
    }

    public void tambahPetugas(Petugas p) {
        listPetugas.add(p);
    }

    public ArrayList<Petugas> getListPetugas() {
        return listPetugas;
    }
}

