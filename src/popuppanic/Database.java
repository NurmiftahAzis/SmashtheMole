package popuppanic;

import java.sql.*;

public class Database {

    // konfigurasi database
    private static final String URL = "jdbc:mysql://localhost:3306/popup_panic";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("Gagal connect ke database: " + e.getMessage());
            return null;
        }
    }

    public static int login(String username, String password) {
        String sql = "SELECT id FROM users WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, username);
            stmt.setString(2, password);
    
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                return rs.getInt("id");   // login berhasil → return id user
            } else {
                return -1;                            // login gagal
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        return -1;
        }
    }

    public static boolean register(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

    try (Connection conn = getConnection();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, username);
        pst.setString(2, password);

        int result = pst.executeUpdate();

        return result > 0;  // return true jika berhasil

    } catch (Exception e) {
        e.printStackTrace();
    }
        return false;
    }

    public static ResultSet getLeaderboard() throws Exception {
        String sql = "SELECT u.username, s.score, s.created_at " +
                 "FROM scores s " +
                 "JOIN users u ON s.user_id = u.id " +
                 "ORDER BY s.score DESC " +
                 "LIMIT 10";

        Connection conn = getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();
        return null;
    }
}
