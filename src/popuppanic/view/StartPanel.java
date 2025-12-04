package popuppanic.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// library untuk sound
import javax.sound.sampled.*;
import java.io.File;

public class StartPanel extends JPanel {

    private JFrame parent; // frame utama untuk ganti halaman
    private int userId; // ID user yang login
    private String username; // username untuk ditampilkan

    // musik background menu (static agar tidak ter-play berkali-kali saat panel
    // dibuka ulang)
    private static Clip audioClip;

    // warna tema tampilan UI
    private final Color BG = Color.decode("#261702");
    private final Color GREEN = new Color(0x136021);
    private final Color BROWN = new Color(0x755B38);

    // constructor panel menu awal setelah login
    public StartPanel(JFrame parent, int userId, String username) {
        this.parent = parent;
        this.userId = userId;
        this.username = username;

        setLayout(null); // manual layout menggunakan koordinat
        setBackground(BG);

        // cek dan jalankan musik menu jika belum diputar
        if (audioClip == null || !audioClip.isRunning()) {
            loadSound();
            if (audioClip != null) {
                audioClip.start();
            }
        }

        initUI(); // bangun komponen UI
    }

    // load file musik background menu utama
    private void loadSound() {
        try {
            File audioFile = new File("src/assets/start-music.wav");
            if (audioFile.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                audioClip = AudioSystem.getClip();
                audioClip.open(audioStream);
            } else {
                System.out.println("File audio tidak ditemukan!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // efek suara tombol klik
    private void playClickSound() {
        try {
            File file = new File("src/assets/click.wav");
            if (file.exists()) {
                AudioInputStream stream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                clip.start();
            } else {
                System.out.println("File audio tidak ditemukan!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // membangun seluruh tampilan menu awal
    private void initUI() {

        // icon profile di pojok kiri atas
        JLabel profileIcon = createIcon("src/assets/icon-profile.png");
        profileIcon.setBounds(30, 30, 70, 70);
        profileIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        profileIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playClickSound();
                parent.setContentPane(new ProfilePanel(parent, userId));
                parent.revalidate();
                parent.repaint();
            }
        });
        add(profileIcon);

        // icon leaderboard di pojok kanan atas
        JLabel leaderboardIcon = createIcon("src/assets/icon-leaderboard.png");
        leaderboardIcon.setBounds(700, 30, 70, 70);
        leaderboardIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        leaderboardIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playClickSound();
                parent.setContentPane(new LeaderboardPanel(parent, userId, username));
                parent.revalidate();
                parent.repaint();
            }
        });
        add(leaderboardIcon);

        // panel sapaan username
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titlePanel.setBounds(0, 130, 800, 70);
        titlePanel.setOpaque(false);

        Font titleFont = new Font("Arial Black", Font.BOLD, 48);

        JLabel lblHalo = new JLabel("HALO, ");
        lblHalo.setFont(titleFont);
        lblHalo.setForeground(Color.WHITE);
        titlePanel.add(lblHalo);

        JLabel lblUser = new JLabel(username.toUpperCase());
        lblUser.setFont(titleFont);
        lblUser.setForeground(GREEN);
        titlePanel.add(lblUser);

        JLabel lblBang = new JLabel("!");
        lblBang.setFont(titleFont);
        lblBang.setForeground(Color.WHITE);
        titlePanel.add(lblBang);

        add(titlePanel);

        // ukuran tombol
        int bw = 280, bh = 60;
        int bx = (800 - bw) / 2;

        // tombol untuk mulai permainan
        JButton start = createButton("START GAME", GREEN, BROWN);
        start.setBounds(bx, 240, bw, bh);
        start.addActionListener(e -> {
            playClickSound();
            if (audioClip != null && audioClip.isRunning())
                audioClip.stop();

            parent.setContentPane(new GamePanel(parent, userId, username));
            parent.revalidate();
            parent.repaint();

            SwingUtilities.invokeLater(() -> parent.getContentPane().requestFocusInWindow());
        });
        add(start);

        // tombol logout ke halaman login
        JButton logout = createButton("LOGOUT", BROWN, GREEN);
        logout.setBounds(bx, 320, bw, bh);
        logout.addActionListener(e -> {
            playClickSound();
            if (audioClip != null && audioClip.isRunning())
                audioClip.stop();
            parent.setContentPane(new LoginPage(parent));
            parent.revalidate();
            parent.repaint();
        });
        add(logout);
    }

    // helper untuk membuat icon gambar dengan resize
    private JLabel createIcon(String path) {
        ImageIcon raw = new ImageIcon(path);
        if (raw.getIconWidth() > 0) {
            Image scaled = raw.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaled));
        }
        return new JLabel();
    }

    // helper untuk styling tombol UI
    private JButton createButton(String text, Color bg, Color borderColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 22));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(borderColor, 4));

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

    // menggambar footer dekorasi di bagian bawah layar
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