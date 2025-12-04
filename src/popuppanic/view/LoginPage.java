package popuppanic.view;

import javax.swing.*;

import popuppanic.model.Database;

import java.awt.*;
import java.awt.event.*;

import javax.sound.sampled.*;
import java.io.File;

public class LoginPage extends JPanel {

    private JFrame parent; // frame utama untuk ganti halaman

    // field input dari user
    private JTextField usernameField;
    private JPasswordField passwordField;

    // warna tema UI
    private final Color BG = Color.decode("#261702"); // background utama
    private final Color GREEN = new Color(0x136021); // warna tombol utama
    private final Color BROWN = new Color(0x755B38); // warna border
    private final Color BOX_BG = new Color(55, 40, 30); // kotak form
    private final Color FIELD_BG = new Color(30, 20, 15); // background text field

    // constructor halaman login
    public LoginPage(JFrame parent) {
        this.parent = parent;

        setLayout(null); // manual layout, posisi diatur sendiri
        setBackground(BG);

        buildUI(); // buat tampilan komponen login
    }

    // membangun seluruh tampilan UI pada halaman login
    private void buildUI() {

        // judul nama game di bagian atas
        JLabel t1 = new JLabel("POPUP");
        t1.setFont(new Font("Arial Black", Font.BOLD, 60));
        t1.setForeground(Color.WHITE);
        t1.setBounds(170, 20, 300, 80);
        add(t1);

        JLabel t2 = new JLabel("PANIC");
        t2.setFont(new Font("Arial Black", Font.BOLD, 60));
        t2.setForeground(GREEN);
        t2.setBounds(420, 20, 300, 80);
        add(t2);

        // panel kotak form login
        JPanel box = new JPanel(null);
        box.setBackground(BOX_BG);
        box.setBorder(BorderFactory.createLineBorder(BROWN, 3));
        box.setBounds(160, 150, 480, 160);
        add(box);

        // label untuk input username
        JLabel user = new JLabel("Username :");
        user.setForeground(Color.WHITE);
        user.setFont(new Font("SansSerif", Font.PLAIN, 20));
        user.setBounds(40, 25, 150, 30);
        box.add(user);

        // text field input username
        usernameField = new JTextField();
        styleField(usernameField);
        usernameField.setBounds(200, 25, 240, 30);
        box.add(usernameField);

        // label password
        JLabel pass = new JLabel("Password :");
        pass.setForeground(Color.WHITE);
        pass.setFont(new Font("SansSerif", Font.PLAIN, 20));
        pass.setBounds(40, 80, 150, 30);
        box.add(pass);

        // input password
        passwordField = new JPasswordField();
        styleField(passwordField);
        passwordField.setBounds(200, 80, 240, 30);
        box.add(passwordField);

        // teks kecil link ke halaman register
        JLabel txt = new JLabel("Don't have an account yet");
        txt.setForeground(Color.LIGHT_GRAY);
        txt.setFont(new Font("SansSerif", Font.PLAIN, 12));
        txt.setBounds(120, 120, 200, 20);
        box.add(txt);

        JLabel link = new JLabel("Register");
        link.setForeground(new Color(255, 80, 80));
        link.setFont(new Font("SansSerif", Font.BOLD, 12));
        link.setBounds(270, 120, 80, 20);
        link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        box.add(link);

        // klik "Register" pindah ke halaman register
        link.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playClickSound();
                parent.setContentPane(new RegisterPage(parent));
                parent.revalidate();
                parent.repaint();
            }
        });

        // tombol login
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 20));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBackground(GREEN);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createLineBorder(BROWN, 4));
        loginBtn.setBounds(300, 340, 200, 50);
        add(loginBtn);

        // aksi tombol login
        loginBtn.addActionListener(e -> {
            playClickSound();
            doLogin(); // proses login user
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

    // render dekorasi footer visual
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

    // method helper untuk styling input field
    private void styleField(JTextField f) {
        f.setFont(new Font("SansSerif", Font.PLAIN, 16));
        f.setForeground(Color.WHITE);
        f.setBackground(FIELD_BG);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BROWN, 1),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        f.setCaretColor(Color.WHITE); // warna kursor ketik
    }

    // proses login user
    private void doLogin() {
        String u = usernameField.getText();
        String p = new String(passwordField.getPassword());

        // validasi input wajib diisi
        if (u.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi username dan password!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // cek username + password ke database
        int id = Database.login(u, p);

        if (id != -1) {
            JOptionPane.showMessageDialog(this, "Login berhasil!");

            // pindah ke halaman StartPanel jika login sukses
            parent.setContentPane(new StartPanel(parent, id, u));
            parent.revalidate();
            parent.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Username / password salah!", "Login Gagal",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}