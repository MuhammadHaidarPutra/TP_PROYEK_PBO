package util;

import java.util.ArrayList;
import model.*;

public class DatabaseDummy {


    public static Admin admin = new Admin("admin", "admin123");
    public static Petugas petugas = new Petugas("petugas", "petugas123");
    public static ArrayList<User> users = new ArrayList<>();

    static {
        users.add(admin);
        users.add(petugas);
    }

    public static User login(String u, String p) {
        for (User user : users) {
            if (user.getUsername().equals(u) &&
                user.getPassword().equals(p)) {
                return user;
            }
        }
        return null;
    }
}
