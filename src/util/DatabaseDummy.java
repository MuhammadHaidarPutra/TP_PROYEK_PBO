package util;

import java.util.ArrayList;
import java.io.*;
import model.*;

public class DatabaseDummy {


    public static Admin admin = new Admin("admin", "admin123");
    public static ArrayList<User> users = new ArrayList<>();

    static {
        users.add(admin);
        // Tidak menambah petugas hardcoded, semua petugas dari CSV
    }

    public static User login(String u, String p) {
        // Cek admin dulu
        for (User user : users) {
            if (user.getUsername().equals(u) && user.getPassword().equals(p)) {
                return user;
            }
        }
        // Cek petugas dari CSV
        String CSV_PATH = "src/assets/petugas.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; } // skip header
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    if (username.equals(u) && password.equals(p)) {
                        return new Petugas(username, password);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal baca file petugas.csv: " + e.getMessage());
        }
        return null;
    }
}
