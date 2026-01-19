
package model;

public class Session {
    public static User userAktif; 

    public static void logout() {
        userAktif = null;
    }
}
