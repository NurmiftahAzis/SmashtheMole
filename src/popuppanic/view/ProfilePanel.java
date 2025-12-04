package popuppanic.view;

import javax.swing.*;

import popuppanic.model.Database;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

import javax.sound.sampled.*;
import java.io.File;

public class ProfilePanel extends JPanel {

    private JFrame parent; // frame utama untuk navigasi antar halaman
    private int userId; // ID user yang sedang login

    // warna tema UI
    private final Color BG = Color.decode("#261702"); // background utama
    private final Color GREEN = new Color(0x136021); // warna tombol utama
    private final Color BROWN = new Color(0x755B38); // warna border tombol & tabel

    // constructor halaman profile
    public ProfilePanel(JFrame parent, int userId) {
        this.parent = parent;
        this.userId = userId;

        setLayout(null); // manual layout, posisi elemen diatur secara bebas
        setBackground(BG);

        initUI(); // bangun tampilan profile
    }

    // membangun seluruh tampilan UI pada halaman profile
    private void initUI() {

        // judul halaman di bagian atas
        JLabel title = new JLabel("PROFILE", SwingConstants.CENTER);
        title.setFont(new Font("Arial Black", Font.BOLD, 55));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 50, 800, 70);
        add(title);

        // ambil data user dari database untuk ditampilkan
        String username = getUsername(userId); // nama user
        int highscore = getHighscore(userId); // skor tertinggi user

        // panel untuk menampilkan nama user
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        namePanel.setBounds(0, 160, 800, 50);
        namePanel.setOpaque(false); // transparan supaya background tetap terlihat

        JLabel lblNameTitle = new JLabel("Name : ");
        lblNameTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblNameTitle.setForeground(Color.WHITE);
        namePanel.add(lblNameTitle);

        JLabel lblNameValue = new JLabel(username);
        lblNameValue.setFont(new Font("Arial", Font.BOLD, 28));
        lblNameValue.setForeground(GREEN);
        namePanel.add(lblNameValue);

        add(namePanel);

        // panel untuk menampilkan highscore user
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        scorePanel.setBounds(0, 210, 800, 50);
        scorePanel.setOpaque(false);

        JLabel lblScoreTitle = new JLabel("High Score : ");
        lblScoreTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblScoreTitle.setForeground(Color.WHITE);
        scorePanel.add(lblScoreTitle);

        JLabel lblScoreValue = new JLabel(String.valueOf(highscore));
        lblScoreValue.setFont(new Font("Arial", Font.BOLD, 28));
        lblScoreValue.setForeground(GREEN);
        scorePanel.add(lblScoreValue);

        add(scorePanel);

        // tombol untuk kembali ke halaman StartPanel
        JButton backBtn = new JButton("BACK");
        backBtn.setFont(new Font("Arial", Font.BOLD, 22));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(GREEN);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createLineBorder(BROWN, 4));
        backBtn.setBounds(250, 300, 300, 60);

        // efek hover tombol
        Color hover = GREEN.brighter();
        backBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                backBtn.setBackground(hover);
            }

            public void mouseExited(MouseEvent e) {
                backBtn.setBackground(GREEN);
            }
        });

        // aksi tombol: kembali ke menu awal
        backBtn.addActionListener(e -> {
            playClickSound(); // putar efek suara klik
            parent.setContentPane(new StartPanel(parent, userId, username));
            parent.revalidate();
            parent.repaint();
        });

        add(backBtn);
    }

    // putar efek suara klik tombol
    private void playClickSound() {
        try {
            File file = new File("src/assets/click.wav");
            if (file.exists()) {
                AudioInputStream stream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                clip.start();
            } else {
                System.out.println("click.wav tidak ditemukan!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // method helper untuk mengambil username dari database berdasarkan user_id
    private String getUsername(int id) {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT username FROM users WHERE user_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("username");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown"; // jika gagal ambil data
    }

    // method helper untuk mengambil skor tertinggi user
    private int getHighscore(int id) {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT MAX(score) AS highscore FROM scores WHERE user_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("highscore");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // jika belum ada skor
    }

    // render footer dekorasi visual pada bagian bawah layar
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon footerImg = new ImageIcon("src/assets/footer-profile.png");
        if (footerImg.getIconWidth() > 0) {
            int w = footerImg.getIconWidth();
            int h = footerImg.getIconHeight();
            g.drawImage(footerImg.getImage(), (getWidth() - w) / 2, getHeight() - h, w, h, null);
        }
    }
}