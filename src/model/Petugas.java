
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.ArrayList;


public class Petugas extends User {

    public Petugas(String username, String password) {
        super(username, password, "Petugas");
    }

    @Override
    public boolean login(String username, String password) {
        // Contoh sederhana, sesuaikan dengan validasi sebenarnya
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public void logout() {
        // Implementasi logout, misal reset session
        System.out.println("Petugas logged out");
    }
}

