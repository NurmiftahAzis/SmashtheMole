package popuppanic.view;

import javax.swing.*;

import popuppanic.model.Database;

import java.awt.*;
import java.awt.event.*;

import javax.sound.sampled.*;
import java.io.File;

public class RegisterPage extends JPanel {

    private JFrame parent; // frame utama untuk ganti halaman

    // field input dari user
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;

    // warna tema UI
    private final Color BG = Color.decode("#261702");
    private final Color GREEN = new Color(0x136021);
    private final Color BROWN = new Color(0x755B38);
    private final Color BOX_BG = new Color(55, 40, 30);
    private final Color FIELD_BG = new Color(30, 20, 15);

    // constructor halaman register
    public RegisterPage(JFrame parent) {
        this.parent = parent;

        setLayout(null); // manual layout, posisi dikontrol melalui koordinat x,y
        setBackground(BG);

        buildUI(); // bangun tampilan form register
    }

    // membangun seluruh tampilan UI halaman register
    private void buildUI() {

        // judul game di bagian atas layar
        JLabel t1 = new JLabel("POPUP");
        t1.setFont(new Font("Arial Black", Font.BOLD, 60));
        t1.setForeground(Color.WHITE);
        t1.setBounds(170, 20, 300, 80);
        add(t1);

        JLabel t2 = new JLabel("PANIC");
        t2.setFont(new Font("Arial Black", Font.BOLD, 60));
        t2.setForeground(GREEN); // variasi warna biar menarik
        t2.setBounds(420, 20, 300, 80);
        add(t2);

        // panel kotak untuk form input register
        JPanel box = new JPanel(null);
        box.setBackground(BOX_BG);
        box.setBorder(BorderFactory.createLineBorder(BROWN, 3));
        box.setBounds(140, 120, 520, 210);
        add(box);

        // label input username
        JLabel user = new JLabel("Username :");
        user.setForeground(Color.WHITE);
        user.setFont(new Font("SansSerif", Font.PLAIN, 20));
        user.setBounds(40, 20, 200, 30);
        box.add(user);

        // field input username
        usernameField = new JTextField();
        styleField(usernameField);
        usernameField.setBounds(230, 20, 240, 30);
        box.add(usernameField);

        // label input password
        JLabel pass = new JLabel("Password :");
        pass.setForeground(Color.WHITE);
        pass.setFont(new Font("SansSerif", Font.PLAIN, 20));
        pass.setBounds(40, 70, 200, 30);
        box.add(pass);

        // field input password
        passwordField = new JPasswordField();
        styleField(passwordField);
        passwordField.setBounds(230, 70, 240, 30);
        box.add(passwordField);

        // label konfirmasi password
        JLabel confirm = new JLabel("Confirm Password :");
        confirm.setForeground(Color.WHITE);
        confirm.setFont(new Font("SansSerif", Font.PLAIN, 20));
        confirm.setBounds(40, 120, 200, 30);
        box.add(confirm);

        // field input ulang password
        confirmField = new JPasswordField();
        styleField(confirmField);
        confirmField.setBounds(230, 120, 240, 30);
        box.add(confirmField);

        // teks kecil untuk navigasi ke halaman login
        JLabel txt = new JLabel("Already have an account?");
        txt.setForeground(Color.LIGHT_GRAY);
        txt.setFont(new Font("SansSerif", Font.PLAIN, 12));
        txt.setBounds(150, 165, 200, 20);
        box.add(txt);

        JLabel link = new JLabel("Login");
        link.setForeground(new Color(255, 80, 80));
        link.setFont(new Font("SansSerif", Font.BOLD, 12));
        link.setBounds(310, 165, 80, 20);
        link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        box.add(link);

        // aksi klik link untuk berpindah ke halaman login
        link.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playClickSound();
                parent.setContentPane(new LoginPage(parent));
                parent.revalidate();
                parent.repaint();
            }
        });

        // tombol register
        JButton regBtn = new JButton("REGISTER");
        regBtn.setBounds(300, 340, 200, 50);
        regBtn.setFont(new Font("Arial", Font.BOLD, 20));
        regBtn.setForeground(Color.WHITE);
        regBtn.setBackground(BROWN);
        regBtn.setFocusPainted(false);
        regBtn.setBorder(BorderFactory.createLineBorder(GREEN, 3));
        add(regBtn);

        // aksi tombol register
        regBtn.addActionListener(e -> {
            playClickSound();
            doRegister(); // proses pendaftaran user baru
        });
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
                System.out.println("File audio tidak ditemukan!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // render footer dekorasi visual
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon footerImg = new ImageIcon("src/assets/footer-login.png");
        if (footerImg.getIconWidth() > 0) {
            int w = footerImg.getIconWidth();
            int h = footerImg.getIconHeight();
            g.drawImage(footerImg.getImage(), (getWidth() - w) / 2, getHeight() - h, w, h, null);
        }
    }

    // styling untuk text field agar tampilan konsisten
    private void styleField(JTextField f) {
        f.setFont(new Font("SansSerif", Font.PLAIN, 16));
        f.setForeground(Color.WHITE);
        f.setBackground(FIELD_BG);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BROWN, 1),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        f.setCaretColor(Color.WHITE); // warna kursor input
    }

    // proses registrasi user baru
    private void doRegister() {
        String u = usernameField.getText();
        String p = new String(passwordField.getPassword());
        String c = new String(confirmField.getPassword());

        // validasi input kosong
        if (u.isEmpty() || p.isEmpty() || c.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi semua data!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // cek apakah password dan konfirmasi sama
        if (!p.equals(c)) {
            JOptionPane.showMessageDialog(this, "Password tidak sama!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // daftarkan user ke database
        boolean ok = Database.register(u, p);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Registrasi berhasil!");

            // pindah ke halaman login setelah berhasil
            parent.setContentPane(new LoginPage(parent));
            parent.revalidate();
            parent.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Username sudah digunakan!", "Registrasi Gagal",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}