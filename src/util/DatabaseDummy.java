package util;

import model.User;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDummy {

    private static final List<User> users = new ArrayList<>();

    static {
        users.add(new User("admin", "admin123", "ADMIN"));
        users.add(new User("petugas", "petugas123", "PETUGAS"));
    }

    public static User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) &&
                u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }
}
