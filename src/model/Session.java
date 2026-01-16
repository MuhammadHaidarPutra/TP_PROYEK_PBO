
package model;

public class Session {
    public static User userAktif; // Bisa Admin atau Petugas

    public static void logout() {
        userAktif = null;
    }
}
