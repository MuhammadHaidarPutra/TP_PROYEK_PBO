
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.ArrayList;

public class Petugas extends User {
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private ArrayList<Petugas> listPetugas = new ArrayList<>();

    public Petugas(String u, String p) {
        super(u, p, "Petugas");
        setUsername(u);
        setPassword(p);
    }

    public String getUsername() {
        return username.get();
    }
    public void setUsername(String u) {
        username.set(u);
    }
    public StringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }
    public void setPassword(String p) {
        password.set(p);
    }
    public StringProperty passwordProperty() {
        return password;
    }

    public void tambahPetugas(Petugas p) {
        listPetugas.add(p);
    }

    public ArrayList<Petugas> getListPetugas() {
        return listPetugas;
    }
}

