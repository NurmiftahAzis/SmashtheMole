package popuppanic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartPanel extends JPanel {

    private JFrame parent;
    private int userId;
    private String username;

    private final Color BG = Color.decode("#261702");
    private final Color GREEN = new Color(0x136021);
    private final Color BROWN = new Color(0x755B38);

    public StartPanel(JFrame parent, int userId, String username) {
        this.parent = parent;
        this.userId = userId;
        this.username = username;

        setLayout(null);
        setBackground(BG);
        initUI();
    }

    private void initUI() {

        // ========= ICON PROFILE (KIRI ATAS) =========
        JLabel profileIcon = createIcon("src/assets/icon-profile.png");
        profileIcon.setBounds(30, 30, 70, 70);
        profileIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // kalo diklik pindah ke menu profile
        profileIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.setContentPane(new ProfilePanel(parent, userId));
                parent.revalidate();
                parent.repaint();
            }
        });
        add(profileIcon);

        // ========= ICON LEADERBOARD (KANAN ATAS) =========
        JLabel leaderboardIcon = createIcon("src/assets/icon-leaderboard.png");
        leaderboardIcon.setBounds(800 - 100, 30, 70, 70);
        leaderboardIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // kalo diklik pindah ke leaderboard
        leaderboardIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.setContentPane(new LeaderboardPanel(parent, userId, username));
                parent.revalidate();
                parent.repaint();
            }
        });
        add(leaderboardIcon);

        // ========= JUDUL SAPAAN =========
        // pake panel flowlayout biar teksnya "Halo" + "Nama" + "!" bisa nyambung rapi
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titlePanel.setBounds(0, 130, 800, 70);
        titlePanel.setOpaque(false); // transparan biar background coklatnya kelihatan

        Font titleFont = new Font("Arial Black", Font.BOLD, 48);

        // 1. "HALO, " -> warna putih
        JLabel lblHalo = new JLabel("HALO, ");
        lblHalo.setFont(titleFont);
        lblHalo.setForeground(Color.WHITE);
        titlePanel.add(lblHalo);

        // 2. "USERNAME" -> warna hijau biar ngejreng
        JLabel lblUser = new JLabel(username.toUpperCase());
        lblUser.setFont(titleFont);
        lblUser.setForeground(GREEN);
        titlePanel.add(lblUser);

        // 3. "!" -> warna putih lagi
        JLabel lblBang = new JLabel("!");
        lblBang.setFont(titleFont);
        lblBang.setForeground(Color.WHITE);
        titlePanel.add(lblBang);

        add(titlePanel);

        // ========= TOMBOL MENU =========
        int bw = 280; // lebar tombol
        int bh = 60; // tinggi tombol
        int bx = (800 - bw) / 2; // rumus biar posisinya di tengah layar

        // tombol start game
        JButton start = createButton("START GAME", GREEN, BROWN);
        start.setBounds(bx, 240, bw, bh);
        start.addActionListener(e -> {
            // pindah ke panel game
            parent.setContentPane(new GamePanel(parent, userId, username));
            parent.revalidate();
            parent.repaint();
            // penting: balikin fokus ke window biar keyboard listener jalan
            SwingUtilities.invokeLater(() -> parent.getContentPane().requestFocusInWindow());
        });
        add(start);

        // tombol logout
        JButton logout = createButton("LOGOUT", BROWN, GREEN);
        logout.setBounds(bx, 320, bw, bh);
        logout.addActionListener(e -> {
            // balik ke halaman login
            parent.setContentPane(new LoginPage(parent));
            parent.revalidate();
            parent.repaint();
        });
        add(logout);
    }

    // method helper buat load & resize icon biar gak ribet
    private JLabel createIcon(String path) {
        ImageIcon raw = new ImageIcon(path);
        if (raw.getIconWidth() > 0) {
            Image scaled = raw.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaled));
        }
        return new JLabel();
    }

    // method helper buat bikin tombol custom (warna & border)
    private JButton createButton(String text, Color bg, Color borderColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 22));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(borderColor, 4));

        // efek hover pas mouse masuk/keluar
        Color hover = bg.brighter();
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hover);
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
            }
        });

        return btn;
    }

    // gambar footer
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon footerImg = new ImageIcon("src/assets/footer-start.png");

        if (footerImg.getIconWidth() > 0) {
            int w = footerImg.getIconWidth();
            int h = footerImg.getIconHeight();
            g.drawImage(footerImg.getImage(), (getWidth() - w) / 2, getHeight() - h, w, h, null);
        }
    }
}