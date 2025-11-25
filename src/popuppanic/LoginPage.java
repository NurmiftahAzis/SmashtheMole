package popuppanic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JPanel {

    private JFrame parent;

    private JTextField usernameField;
    private JPasswordField passwordField;

    private final Color BG = Color.decode("#261702");
    private final Color GREEN = new Color(0x136021);
    private final Color BROWN = new Color(0x755B38);
    private final Color BOX_BG = new Color(55, 40, 30);
    private final Color FIELD_BG = new Color(30, 20, 15);

    public LoginPage(JFrame parent) {
        this.parent = parent;

        setLayout(null);
        setBackground(BG);

        buildUI();
    }

    private void buildUI() {

        // judul game
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

        // kotak panel di tengah buat wadah form login
        JPanel box = new JPanel(null);
        box.setBackground(BOX_BG);
        box.setBorder(BorderFactory.createLineBorder(BROWN, 3));
        box.setBounds(160, 150, 480, 160);
        add(box);

        // --- Isi Form Username ---
        JLabel user = new JLabel("Username :");
        user.setForeground(Color.WHITE);
        user.setFont(new Font("SansSerif", Font.PLAIN, 20));
        user.setBounds(40, 25, 150, 30);
        box.add(user);

        usernameField = new JTextField();
        styleField(usernameField); // panggil helper biar desainnya konsisten
        usernameField.setBounds(200, 25, 240, 30);
        box.add(usernameField);

        // --- Isi Form Password ---
        JLabel pass = new JLabel("Password :");
        pass.setForeground(Color.WHITE);
        pass.setFont(new Font("SansSerif", Font.PLAIN, 20));
        pass.setBounds(40, 80, 150, 30);
        box.add(pass);

        passwordField = new JPasswordField();
        styleField(passwordField);
        passwordField.setBounds(200, 80, 240, 30);
        box.add(passwordField);

        // --- Link Register ---
        JLabel txt = new JLabel("Don't have an account yet");
        txt.setForeground(Color.LIGHT_GRAY);
        txt.setFont(new Font("SansSerif", Font.PLAIN, 12));
        txt.setBounds(120, 120, 200, 20);
        box.add(txt);

        // teks yang bisa diklik buat ke halaman register
        JLabel link = new JLabel("Register");
        link.setForeground(new Color(255, 80, 80));
        link.setFont(new Font("SansSerif", Font.BOLD, 12));
        link.setBounds(270, 120, 80, 20);
        link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        box.add(link);

        link.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // pindah ke halaman register
                parent.setContentPane(new RegisterPage(parent));
                parent.revalidate();
                parent.repaint();
            }
        });

        // tombol login di bawah
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(300, 340, 200, 50);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 20));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBackground(GREEN);
        loginBtn.setFocusPainted(false);

        // border tombol dikasih warna coklat biar match sama desain lain
        loginBtn.setBorder(BorderFactory.createLineBorder(BROWN, 4));

        add(loginBtn);

        loginBtn.addActionListener(e -> doLogin());
    }

    // gambar footer
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

    // helper buat styling text field biar gak nulis ulang kodenya
    private void styleField(JTextField f) {
        f.setFont(new Font("SansSerif", Font.PLAIN, 16));
        f.setForeground(Color.WHITE);
        f.setBackground(FIELD_BG);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BROWN, 1),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        f.setCaretColor(Color.WHITE); // warna kursor ketik jadi putih
    }

    private void doLogin() {
        String u = usernameField.getText();
        String p = new String(passwordField.getPassword());

        // validasi simpel, jangan sampe kosong
        if (u.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi username dan password!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // cek ke database, cocok gak?
        int id = Database.login(u, p);

        if (id != -1) {
            JOptionPane.showMessageDialog(this, "Login berhasil!");
            // TODO: kalo sukses, masuk ke menu utama (StartPanel)
            // parent.setContentPane(new StartPanel(parent, id, u));
            // parent.revalidate();
            // parent.repaint();
        } else {
            // kalo gagal (return -1)
            JOptionPane.showMessageDialog(this, "Username / password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }
}