package popuppanic.model;

import java.sql.*;

public class Database {

    // konfigurasi koneksi ke database MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/popup_panic"; // nama database
    private static final String USER = "root"; // username MySQL
    private static final String PASS = ""; // password MySQL

    // method untuk membuat koneksi ke database
    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver"); // load driver MySQL
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // proses login: cek username dan password dari tabel users
    public static int login(String username, String password) {
        try (Connection conn = getConnection()) {

            String q = "SELECT user_id FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setString(1, username); // masukkan username ke query
            ps.setString(2, password); // masukkan password ke query

            ResultSet rs = ps.executeQuery();

            // apabila ditemukan data user, return id-nya
            if (rs.next()) {
                return rs.getInt("user_id");
            }

        } catch (Exception e) {
            e.printStackTrace(); // tampilkan error di console jika gagal
        }

        return -1; // login gagal: tidak ditemukan akun
    }

    // proses registrasi user baru
    public static boolean register(String username, String password) {
        try (Connection conn = getConnection()) {

            // cek apakah username sudah digunakan sebelumnya
            String check = "SELECT * FROM users WHERE username=?";
            PreparedStatement ps1 = conn.prepareStatement(check);
            ps1.setString(1, username);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                return false; // username sudah dipakai
            }

            // simpan user baru ke database
            String ins = "INSERT INTO users(username, password) VALUES(?,?)";
            PreparedStatement ps2 = conn.prepareStatement(ins);
            ps2.setString(1, username);
            ps2.setString(2, password);
            ps2.executeUpdate();

            return true; // registrasi berhasil

        } catch (Exception e) {
            e.printStackTrace();
            return false; // error saat insert
        }
    }

    // mengambil data leaderboard (top 10 score)
    public static ResultSet getLeaderboard() throws Exception {
        Connection conn = getConnection();

        // ambil skor tertinggi, urutkan desc, dan limit 10 data
        // join tabel scores dengan users untuk menampilkan username di leaderboard
        String q = "SELECT users.username, scores.score, scores.timestamp " +
                "FROM scores " +
                "JOIN users ON scores.user_id = users.user_id " +
                "ORDER BY scores.score DESC " +
                "LIMIT 10";

        PreparedStatement ps = conn.prepareStatement(q);
        return ps.executeQuery(); // hasilnya dipakai di LeaderboardPanel
    }
}