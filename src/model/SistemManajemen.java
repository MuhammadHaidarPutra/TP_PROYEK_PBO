package model;

import java.util.ArrayList;
import java.util.List;

public class SistemManajemen {

    private List<User> users = new ArrayList<>();

    public SistemManajemen() {
        users.add(new Admin("admin", "admin123"));
        users.add(new Petugas("petugas", "petugas123"));

        System.out.println("=== USER TERDAFTAR ===");
        for (User u : users) {
            System.out.println(u.getUsername() + " | " + u.getPassword());
        }
    }

    public User login(String u, String p) {
        for (User user : users) {
            if (user.getUsername().equals(u)
             && user.getPassword().equals(p)) {
                return user;
            }
        }
        return null;
    }
}
