package popuppanic;

import java.sql.*;

public class Database {

    // config database
    private static final String URL = "jdbc:mysql://localhost:3306/popup_panic";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() throws Exception {
        // panggil driver mysql biar connect
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static int login(String username, String password) {
        try (Connection conn = getConnection()) {
            // cek user di tabel users
            String q = "SELECT user_id FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            // kalo ketemu, balikin id-nya
            if (rs.next())
                return rs.getInt("user_id");
        } catch (Exception e) {
            e.printStackTrace(); // print error di console kalo gagal
        }
        return -1; // return -1 tandanya gagal login
    }

    public static boolean register(String username, String password) {
        try (Connection conn = getConnection()) {
            // cek dulu usernamenya udah dipake orang lain apa belum
            String check = "SELECT * FROM users WHERE username=?";
            PreparedStatement ps1 = conn.prepareStatement(check);
            ps1.setString(1, username);
            ResultSet rs = ps1.executeQuery();

            // kalo ada isinya berarti username udah ada
            if (rs.next())
                return false;

            // masukin data baru ke database
            String ins = "INSERT INTO users(username, password) VALUES(?,?)";
            PreparedStatement ps2 = conn.prepareStatement(ins);
            ps2.setString(1, username);
            ps2.setString(2, password);
            ps2.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet getLeaderboard() throws Exception {
        Connection conn = getConnection();

        // join tabel score sama user biar dapet namanya
        // ambil 10 besar aja
        String q = "SELECT users.username, scores.score, scores.timestamp " +
                "FROM scores " +
                "JOIN users ON scores.user_id = users.user_id " +
                "ORDER BY scores.score DESC " +
                "LIMIT 10";

        PreparedStatement ps = conn.prepareStatement(q);
        return ps.executeQuery();
    }
}