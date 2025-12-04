package popuppanic.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import popuppanic.model.Database;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.sound.sampled.*;
import java.io.File;

public class LeaderboardPanel extends JPanel {

    private JFrame parent;
    private int userId;
    private String username;

    // warna tema
    private final Color BG = Color.decode("#261702");
    private final Color GREEN = new Color(0x136021);
    private final Color BROWN = new Color(0x755B38);

    private JTable table;
    private DefaultTableModel model;

    // panel leaderboard (tabel top score)
    public LeaderboardPanel(JFrame parent, int userId, String username) {
        this.parent = parent;
        this.userId = userId;
        this.username = username;

        setLayout(null);
        setBackground(BG);

        initUI(); // setup komponen UI
        loadLeaderboard(); // ambil data dari database
    }

    // setup tampilan leaderboard + tabel
    private void initUI() {

        // judul leaderboard
        JLabel title = new JLabel("TOP 10 PLAYER", SwingConstants.CENTER);
        title.setFont(new Font("Arial Black", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 30, 800, 60);
        add(title);

        // kolom tabel
        String[] columns = { "Rank", "Name", "Score", "Time" };

        // model tabel (read-only, ga bisa edit)
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        // tabel untuk leaderboard
        table = new JTable(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 18));
        table.setRowHeight(40);
        table.setBackground(BG);
        table.setForeground(Color.WHITE);
        table.setGridColor(BROWN);
        table.setShowGrid(true);

        // style header tabel
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setBackground(BROWN);
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createLineBorder(BROWN, 1));

        // rata tengah semua kolom
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // atur lebar kolom
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(260);

        // bungkus tabel biar bisa scroll
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(100, 110, 600, 260);
        scroll.setBorder(BorderFactory.createLineBorder(BROWN, 3));
        scroll.getViewport().setBackground(BG);

        // hilangin scrollbar visual
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        add(scroll);

        // tombol back
        JButton back = new JButton("BACK");
        back.setFont(new Font("Arial", Font.BOLD, 22));
        back.setForeground(Color.WHITE);
        back.setBackground(GREEN);
        back.setFocusPainted(false);
        back.setBorder(BorderFactory.createLineBorder(BROWN, 4));
        back.setBounds(250, 400, 300, 60);

        // efek hover tombol
        Color hover = GREEN.brighter();
        back.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                back.setBackground(hover);
            }

            public void mouseExited(MouseEvent e) {
                back.setBackground(GREEN);
            }
        });

        // aksi klik tombol
        back.addActionListener(e -> {
            playClickSound();
            goBack(); // balik ke StartPanel
        });

        add(back);
    }

    // load data leaderboard dari database
    private void loadLeaderboard() {
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm");

        try {
            ResultSet rs = Database.getLeaderboard();
            int rank = 1;

            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("timestamp");

                model.addRow(new Object[] {
                        rank,
                        rs.getString("username"),
                        rs.getInt("score"),
                        ts != null ? sdf.format(ts) : "-"
                });

                rank++;
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat leaderboard:\n" + e.getMessage());
        }
    }

    // kembali ke halaman start
    private void goBack() {
        parent.setContentPane(new StartPanel(parent, userId, username));
        parent.revalidate();
        parent.repaint();
    }

    // mainkan suara klik tombol
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

    // gambar footer dekorasi
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