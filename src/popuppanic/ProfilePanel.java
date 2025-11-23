package popuppanic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class ProfilePanel extends JPanel {

    private JFrame parent;
    private int userId;

    private final Color BG = Color.decode("#261702");
    private final Color GREEN = new Color(0x136021);
    private final Color BROWN = new Color(0x755B38);

    public ProfilePanel(JFrame parent, int userId) {
        // TODO: inisialisasi panel profile
    }

    private void initUI() {
        // TODO: membangun tampilan UI profil (judul, nama, highscore, tombol back)
    }

    private String getUsername(int id) {
        // TODO: mengambil username dari database berdasarkan user ID
        return null;
    }

    private int getHighscore(int id) {
        // TODO: mengambil skor tertinggi user dari database
        return 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO: menggambar footer atau elemen visual tambahan
    }
}
