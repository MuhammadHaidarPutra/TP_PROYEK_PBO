package auth;

public class LoginService {

    public static String login(String user, String pass) {
  
        if (user == null || pass == null || user.isEmpty() || pass.isEmpty()) {
            return "KOSONG";
        }
        if (user.equals("admin") && pass.equals("admin123")) {
            return "ADMIN";
        }

        if (user.equals("petugas") && pass.equals("petugas123")) {
            return "PETUGAS";
        }

        return "SALAH";
    }
}
