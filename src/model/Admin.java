package model;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password, "ADMIN");
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public void logout() {
        System.out.println("Admin logged out");
    }
}
