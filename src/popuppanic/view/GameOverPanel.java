package popuppanic.view;

import javax.swing.*;
import java.awt.*;

import javax.sound.sampled.*;
import java.io.File;

public class GameOverPanel extends JPanel {

    // warna background dan elemen UI
    private final Color BG = Color.decode("#261702");
    private final Color GREEN = new Color(0x136021);
    private final Color BROWN = new Color(0x755B38);
    private final Color RED = Color.decode("#FF3131");

    // data pengguna
    private int userId;
    private String username;
    private JFrame parent;

    // sound efek game over
    private Clip overClip;

    // panel game over, tampil setelah kalah
    public GameOverPanel(JFrame parent, int score, boolean bomb, int userId, String username) {
        this.parent = parent;
        this.userId = userId;
        this.username = username;

        setLayout(new GridBagLayout());
        setBackground(BG);

        // mainkan musik game over
        playOverMusic();

        // panel utama konten teks dan tombol
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        int titleSize = 48; // ukuran teks judul

        // tampilkan pesan beda kalau kalah karena bom atau waktu habis
        if (bomb) {
            JPanel line1 = createTextPanel();
            line1.add(createLabel("BOOM! ", RED, titleSize));
            line1.add(createLabel("KAMU", Color.WHITE, titleSize));
            contentPanel.add(line1);

            JPanel line2 = createTextPanel();
            line2.add(createLabel("KENA ", Color.WHITE, titleSize));
            line2.add(createLabel("BOM!", GREEN, titleSize));
            contentPanel.add(line2);
        } else {
            JPanel line1 = createTextPanel();
            line1.add(createLabel("WAKTU ", Color.WHITE, titleSize));
            line1.add(createLabel("HABIS!", BROWN, titleSize));
            contentPanel.add(line1);
        }

        contentPanel.add(Box.createVerticalStrut(15));

        // tampilkan skor terakhir
        JLabel lblScore = new JLabel("Score Akhir : " + score);
        lblScore.setFont(new Font("Arial", Font.PLAIN, 20));
        lblScore.setForeground(Color.WHITE);
        lblScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lblScore);

        contentPanel.add(Box.createVerticalStrut(30));

        // tombol buat mulai game lagi
        JButton btnAgain = createButton("START AGAIN", GREEN, BROWN);
        btnAgain.addActionListener(e -> {
            playClickSound(); // bunyi klik tombol
            restartGame(); // restart game
        });
        contentPanel.add(btnAgain);

        contentPanel.add(Box.createVerticalStrut(15));

        // tombol buat balik ke menu awal
        JButton btnBack = createButton("BACK", BROWN, GREEN);
        btnBack.addActionListener(e -> {
            playClickSound(); // bunyi klik tombol
            backToMenu(); // kembali ke menu utama
        });
        contentPanel.add(btnBack);

        add(contentPanel);
    }

    // restart game: berhentiin musik & load GamePanel baru
    private void restartGame() {
        if (overClip != null && overClip.isRunning()) {
            overClip.stop();
        }

        parent.setContentPane(new GamePanel(this.parent, this.userId, this.username));
        parent.revalidate();
        parent.repaint();

        // fokus lagi ke window biar input jalan normal
        SwingUtilities.invokeLater(() -> parent.getContentPane().requestFocusInWindow());
    }

    // balik ke menu utama
    private void backToMenu() {
        if (overClip != null && overClip.isRunning()) {
            overClip.stop();
        }

        parent.setContentPane(new StartPanel(this.parent, this.userId, this.username));
        parent.revalidate();
        parent.repaint();
    }

    // mainkan musik game over 1x
    private void playOverMusic() {
        try {
            File musicFile = new File("src/assets/over-music.wav");
            if (musicFile.exists()) {
                AudioInputStream stream = AudioSystem.getAudioInputStream(musicFile);
                overClip = AudioSystem.getClip();
                overClip.open(stream);
                overClip.start();
            } else {
                System.out.println("File audio tidak ditemukan!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // bunyi klik setiap tombol ditekan
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

    // helper panel untuk susun teks judul
    private JPanel createTextPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        p.setOpaque(false);
        p.setAlignmentX(Component.CENTER_ALIGNMENT);
        return p;
    }

    // helper buat label teks judul
    private JLabel createLabel(String text, Color color, int size) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial Black", Font.BOLD, size));
        lbl.setForeground(color);
        return lbl;
    }

    // helper buat tombol UI
    private JButton createButton(String text, Color bg, Color borderCol) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBorder(BorderFactory.createLineBorder(borderCol, 4));

        Dimension dim = new Dimension(240, 50);
        btn.setMaximumSize(dim);
        btn.setPreferredSize(dim);
        btn.setMinimumSize(dim);

        return btn;
    }

    // render gambar dekorasi footer
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ImageIcon footerImg = new ImageIcon("src/assets/footer-over.png");

        // gambar footer di bawah panel jika gambar tersedia
        if (footerImg.getIconWidth() > 0) {
            int w = footerImg.getIconWidth();
            int h = footerImg.getIconHeight();
            g2d.drawImage(footerImg.getImage(), (getWidth() - w) / 2, getHeight() - h, w, h, null);
        }
    }
}