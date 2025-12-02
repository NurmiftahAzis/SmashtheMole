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
        this.parent = parent;
        this.userId = userId;

        setLayout(null); // layout null biar bisa atur koordinat (x,y) manual
        setBackground(BG);

        initUI();
    }

    private void initUI() {

        // judul halaman profile yang gede di atas
        JLabel title = new JLabel("PROFILE", SwingConstants.CENTER);
        title.setFont(new Font("Arial Black", Font.BOLD, 55));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 50, 800, 70);
        add(title);

        // ambil data user (nama & skor) dari database dulu sebelum ditampilkan
        String username = getUsername(userId);
        int highscore = getHighscore(userId);

        // --- Bagian Nama User ---
        // pake panel kecil biar teks label sama isinya bisa rata tengah barengan
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        namePanel.setBounds(0, 160, 800, 50);
        namePanel.setOpaque(false); // background transparan biar warna dasar panel utama kelihatan

        JLabel lblNameTitle = new JLabel("Name : ");
        lblNameTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblNameTitle.setForeground(Color.WHITE);
        namePanel.add(lblNameTitle);

        JLabel lblNameValue = new JLabel(username);
        lblNameValue.setFont(new Font("Arial", Font.BOLD, 28));
        lblNameValue.setForeground(GREEN); // warna ijo biar beda/kontras
        namePanel.add(lblNameValue);

        add(namePanel);

        // --- Bagian High Score ---
        // sama kayak nama, pake panel flowlayout buat nengahin
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

        // --- Tombol Back ---
        JButton backBtn = new JButton("BACK");
        backBtn.setFont(new Font("Arial", Font.BOLD, 22));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(GREEN);
        backBtn.setFocusPainted(false);

        // border coklat tebal biar seragam sama tombol di menu lain
        backBtn.setBorder(BorderFactory.createLineBorder(BROWN, 4));

        backBtn.setBounds(250, 300, 300, 60);

        // kasih efek hover dikit biar interaktif
        Color hover = GREEN.brighter();
        backBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                backBtn.setBackground(hover);
            }

            public void mouseExited(MouseEvent e) {
                backBtn.setBackground(GREEN);
            }
        });

        // kalo diklik balik ke menu start
        backBtn.addActionListener(e -> {
            parent.setContentPane(new StartPanel(parent, userId, username));
            parent.revalidate();
            parent.repaint();
        });

        add(backBtn);
    }

    // method helper buat ambil username berdasarkan ID user
    private String getUsername(int id) {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT username FROM users WHERE user_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("username");
        } catch (Exception e) {
            e.printStackTrace(); // print error kalo query gagal
        }
        return "Unknown"; // default value kalo user ga ketemu
    }

    // method helper buat ambil skor tertinggi
    private int getHighscore(int id) {
        try (Connection conn = Database.getConnection()) {
            // pake fungsi MAX() biar langsung dapet angka tertinggi
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT MAX(score) AS highscore FROM scores WHERE user_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("highscore");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // kalo belum pernah main, skor 0
    }

    // gambar footer
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // load gambar footer profile
        ImageIcon footerImg = new ImageIcon("src/assets/footer-profile.png");

        if (footerImg.getIconWidth() > 0) {
            int w = footerImg.getIconWidth();
            int h = footerImg.getIconHeight();
            int x = (getWidth() - w) / 2;
            int y = getHeight() - h;
            g.drawImage(footerImg.getImage(), x, y, w, h, null);
        }
    }
}