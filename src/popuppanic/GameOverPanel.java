package popuppanic;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {

    private final Color BG = Color.decode("#261702");
    private final Color GREEN = new Color(0x136021);
    private final Color BROWN = new Color(0x755B38);
    private final Color RED = Color.decode("#FF3131");

    private int userId;
    private String username;
    private JFrame parent;

    public GameOverPanel(JFrame parent, int score, boolean bomb, int userId, String username) {
        this.parent = parent;
        this.userId = userId;
        this.username = username;

        setLayout(new GridBagLayout());
        setBackground(BG);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        // atur ukuran font judul
        int titleSize = 48;

        // logic bedain pesan kalah: gara2 bom atau waktu habis?
        if (bomb) {
            // kalo kena bom
            JPanel line1 = createTextPanel();
            line1.add(createLabel("BOOM! ", RED, titleSize));
            line1.add(createLabel("KAMU", Color.WHITE, titleSize));
            contentPanel.add(line1);

            JPanel line2 = createTextPanel();
            line2.add(createLabel("KENA ", Color.WHITE, titleSize));
            line2.add(createLabel("BOM!", GREEN, titleSize));
            contentPanel.add(line2);
        } else {
            // kalo waktu habis
            JPanel line1 = createTextPanel();
            line1.add(createLabel("WAKTU ", Color.WHITE, titleSize));
            line1.add(createLabel("HABIS!", BROWN, titleSize));
            contentPanel.add(line1);
        }

        contentPanel.add(Box.createVerticalStrut(15)); // kasih jarak dikit

        // tampilin skor akhir pemain
        JLabel lblScore = new JLabel("Score Akhir : " + score);
        lblScore.setFont(new Font("Arial", Font.PLAIN, 20));
        lblScore.setForeground(Color.WHITE);
        lblScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lblScore);

        contentPanel.add(Box.createVerticalStrut(30));

        // tombol buat main lagi (restart)
        JButton btnAgain = createButton("START AGAIN", GREEN, BROWN);
        btnAgain.addActionListener(e -> restartGame());
        contentPanel.add(btnAgain);

        contentPanel.add(Box.createVerticalStrut(15));

        // tombol buat balik ke menu awal
        JButton btnBack = createButton("BACK", BROWN, GREEN);
        btnBack.addActionListener(e -> backToMenu());
        contentPanel.add(btnBack);

        add(contentPanel);
    }

    // --- HELPER METHODS ---

    private void restartGame() {
        // ganti layar ke gamepanel baru (reset game)
        parent.setContentPane(new GamePanel(this.parent, this.userId, this.username));
        parent.revalidate();
        parent.repaint();
        // balikin fokus ke window biar keyboard/mouse jalan lagi
        SwingUtilities.invokeLater(() -> parent.getContentPane().requestFocusInWindow());
    }

    private void backToMenu() {
        // balik ke halaman start
        parent.setContentPane(new StartPanel(this.parent, this.userId, this.username));
        parent.revalidate();
        parent.repaint();
    }

    // --- UI HELPERS ---
    // method bikin panel/label/button biar ga ngetik berulang-ulang

    private JPanel createTextPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        p.setOpaque(false);
        p.setAlignmentX(Component.CENTER_ALIGNMENT);
        return p;
    }

    private JLabel createLabel(String text, Color color, int size) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, size));
        lbl.setForeground(color);
        return lbl;
    }

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // load gambar footer
        ImageIcon footerImg = new ImageIcon("src/assets/footer-over.png");
        if (footerImg.getIconWidth() > 0) {
            int w = footerImg.getIconWidth();
            int h = footerImg.getIconHeight();
            g2d.drawImage(footerImg.getImage(), (getWidth() - w) / 2, getHeight() - h, w, h, null);
        }
    }
}