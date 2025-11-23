package popuppanic;

import java.sql.*;

public class Database {

    // konfigurasi database
    private static final String URL = "jdbc:mysql://localhost:3306/popup_panic";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() throws Exception {
        // TODO: melakukan koneksi ke database MySQL
        return null;
    }

    public static int login(String username, String password) {
        // TODO: memeriksa username dan password dari database
        return -1;
    }

    public static boolean register(String username, String password) {
        // TODO: mendaftarkan user baru ke database
        return false;
    }

    public static ResultSet getLeaderboard() throws Exception {
        // TODO: mengambil 10 skor tertinggi dari database
        return null;
    }
}
