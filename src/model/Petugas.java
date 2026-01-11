
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.ArrayList;

public class Petugas extends User {

    private ArrayList<Petugas> listPetugas = new ArrayList<>();

    public Petugas(String u, String p) {
        super(u, p, "Petugas");
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    public void tambahPetugas(Petugas p) {
        listPetugas.add(p);
    }

    public ArrayList<Petugas> getListPetugas() {
        return listPetugas;
    }
}

