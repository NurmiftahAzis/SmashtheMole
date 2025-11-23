package popuppanic;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.SimpleDateFormat;

public class LeaderboardPanel extends JPanel {

    private JFrame parent;
    private int userId;
    private String username;

    private final Color BG = Color.decode("#261702");
    private final Color GREEN = new Color(0x136021);
    private final Color BROWN = new Color(0x755B38);

    private JTable table;
    private DefaultTableModel model;

    public LeaderboardPanel(JFrame parent, int userId, String username) {
        // TODO: inisialisasi panel leaderboard
    }

    private void initUI() {
        // TODO: menyiapkan komponen UI leaderboard
    }

    private void loadLeaderboard() {
        // TODO: memuat data leaderboard dari database
    }

    private void goBack() {
        // TODO: kembali ke menu/start panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO: menggambar footer atau elemen visual tambahan
    }
}
