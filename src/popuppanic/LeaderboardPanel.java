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
        this.parent = parent;
        this.userId = userId;
        this.username = username;

        setLayout(null);
        setBackground(BG);

        initUI();
        loadLeaderboard(); // panggil data pas panel dibuat
    }

    private void initUI() {

        // judul di paling atas
        JLabel title = new JLabel("TOP 10 PLAYER", SwingConstants.CENTER);
        title.setFont(new Font("Arial Black", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 30, 800, 60);
        add(title);

        // siapin kolom buat tabel
        String[] columns = { "Rank", "Name", "Score", "Time" };

        // bikin model tabel, di-override dikit biar sel-nya gak bisa diedit
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        // atur font sama warna isi tabel
        table.setFont(new Font("SansSerif", Font.PLAIN, 18));
        table.setRowHeight(40);
        table.setBackground(BG);
        table.setForeground(Color.WHITE);
        table.setGridColor(BROWN); // garis pemisah antar sel
        table.setShowGrid(true);

        // dandanin bagian header tabel
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setBackground(BROWN); // header warna coklat
        header.setForeground(Color.WHITE);

        // kasih border coklat di header biar warnanya nyatu
        header.setBorder(BorderFactory.createLineBorder(BROWN, 1));

        // bikin semua tulisan di tabel jadi rata tengah biar rapi
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // atur lebar kolom manual biar pas
        table.getColumnModel().getColumn(0).setPreferredWidth(60); // rank pendek aja
        table.getColumnModel().getColumn(1).setPreferredWidth(160); // nama agak lebar
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(260); // waktu paling lebar

        // bungkus tabel pake scrollpane
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(100, 110, 600, 260);
        scroll.setBorder(BorderFactory.createLineBorder(BROWN, 3));
        scroll.getViewport().setBackground(BG);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        add(scroll);

        // tombol back di bawah
        JButton back = new JButton("BACK");
        back.setFont(new Font("Arial", Font.BOLD, 22));
        back.setForeground(Color.WHITE);
        back.setBackground(GREEN);
        back.setFocusPainted(false);
        back.setBorder(BorderFactory.createLineBorder(BROWN, 4));

        back.setBounds(250, 400, 300, 60);

        // kasih efek pas mouse di atas tombol (hover)
        Color hover = GREEN.brighter();
        back.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                back.setBackground(hover);
            }

            public void mouseExited(MouseEvent e) {
                back.setBackground(GREEN);
            }
        });

        back.addActionListener(e -> goBack());
        add(back);
    }

    private void loadLeaderboard() {
        model.setRowCount(0); // hapus data lama
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm");

        try {
            // ambil data leaderboard dari database
            ResultSet rs = Database.getLeaderboard();
            int rank = 1;

            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("timestamp");

                // masukin data per baris ke tabel
                model.addRow(new Object[] {
                        rank,
                        rs.getString("username"),
                        rs.getInt("score"),
                        ts != null ? sdf.format(ts) : "-" // format tanggal biar enak dibaca
                });

                rank++;
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat leaderboard:\n" + e.getMessage());
        }
    }

    private void goBack() {
        // balik ke menu utama
        parent.setContentPane(new StartPanel(parent, userId, username));
        parent.revalidate();
        parent.repaint();
    }

    // gambar footer
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon footerImg = new ImageIcon("src/assets/footer-leaderboard.png");

        if (footerImg.getIconWidth() > 0) {
            int w = footerImg.getIconWidth();
            int h = footerImg.getIconHeight();
            g.drawImage(footerImg.getImage(), (getWidth() - w) / 2, getHeight() - h, w, h, null);
        }
    }
}