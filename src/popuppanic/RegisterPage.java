package popuppanic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterPage extends JPanel {

    private JFrame parent;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;

    private final Color BG = Color.decode("#261702");
    private final Color GREEN = new Color(0x136021);
    private final Color BROWN = new Color(0x755B38);
    private final Color BOX_BG = new Color(55, 40, 30);
    private final Color FIELD_BG = new Color(30, 20, 15);

    public RegisterPage(JFrame parent) {
        this.parent = parent;

        setLayout(null);
        setBackground(BG);

        buildUI();
    }

    private void buildUI() {

        // ================= JUDUL GAME ===================
        JLabel t1 = new JLabel("POPUP");
        t1.setFont(new Font("Arial Black", Font.BOLD, 60));
        t1.setForeground(Color.WHITE);
        t1.setBounds(170, 20, 300, 80);
        add(t1);

        JLabel t2 = new JLabel("PANIC");
        t2.setFont(new Font("Arial Black", Font.BOLD, 60));
        t2.setForeground(GREEN); // warna hijau biar variasi
        t2.setBounds(420, 20, 300, 80);
        add(t2);

        // ================= KOTAK FORM REGISTER ================
        // panel container buat nampung inputan
        JPanel box = new JPanel(null);
        box.setBackground(BOX_BG);
        box.setBorder(BorderFactory.createLineBorder(BROWN, 3));
        box.setBounds(140, 120, 520, 210);
        add(box);

        // --- Input 1: Username ---
        JLabel user = new JLabel("Username :");
        user.setForeground(Color.WHITE);
        user.setFont(new Font("SansSerif", Font.PLAIN, 20));
        user.setBounds(40, 20, 200, 30);
        box.add(user);

        usernameField = new JTextField();
        styleField(usernameField); // styling field biar seragam
        usernameField.setBounds(230, 20, 240, 30);
        box.add(usernameField);

        // --- Input 2: Password ---
        JLabel pass = new JLabel("Password :");
        pass.setForeground(Color.WHITE);
        pass.setFont(new Font("SansSerif", Font.PLAIN, 20));
        pass.setBounds(40, 70, 200, 30);
        box.add(pass);

        passwordField = new JPasswordField();
        styleField(passwordField);
        passwordField.setBounds(230, 70, 240, 30);
        box.add(passwordField);

        // --- Input 3: Confirm Password ---
        JLabel confirm = new JLabel("Confirm Password :");
        confirm.setForeground(Color.WHITE);
        confirm.setFont(new Font("SansSerif", Font.PLAIN, 20));
        confirm.setBounds(40, 120, 200, 30);
        box.add(confirm);

        confirmField = new JPasswordField();
        styleField(confirmField);
        confirmField.setBounds(230, 120, 240, 30);
        box.add(confirmField);

        // ================= TEXT LINK LOGIN ====================
        JLabel txt = new JLabel("Already have an account?");
        txt.setForeground(Color.LIGHT_GRAY);
        txt.setFont(new Font("SansSerif", Font.PLAIN, 12));
        txt.setBounds(150, 165, 200, 20);
        box.add(txt);

        // teks "Login" yang bisa diklik
        JLabel link = new JLabel("Login");
        link.setForeground(new Color(255, 80, 80));
        link.setFont(new Font("SansSerif", Font.BOLD, 12));
        link.setBounds(310, 165, 80, 20);
        link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        box.add(link);

        // ============ EVENT KLIK LINK ============
        link.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // balik ke halaman login
                parent.setContentPane(new LoginPage(parent));
                parent.revalidate();
                parent.repaint();
            }
        });

        // ================= TOMBOL REGISTER ====================
        JButton regBtn = new JButton("REGISTER");
        regBtn.setBounds(300, 340, 200, 50);
        regBtn.setFont(new Font("Arial", Font.BOLD, 20));
        regBtn.setForeground(Color.WHITE);
        regBtn.setBackground(BROWN);
        regBtn.setFocusPainted(false);
        regBtn.setBorder(BorderFactory.createLineBorder(GREEN, 3));
        add(regBtn);

        regBtn.addActionListener(e -> doRegister());
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

    // helper buat styling text field biar gak ngetik ulang-ulang
    private void styleField(JTextField f) {
        f.setFont(new Font("SansSerif", Font.PLAIN, 16));
        f.setForeground(Color.WHITE);
        f.setBackground(FIELD_BG);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BROWN, 1),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        f.setCaretColor(Color.WHITE); // kursor ketik warna putih
    }

    private void doRegister() {
        String u = usernameField.getText();
        String p = new String(passwordField.getPassword());
        String c = new String(confirmField.getPassword());

        // validasi 1: gak boleh ada yang kosong
        if (u.isEmpty() || p.isEmpty() || c.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi semua data!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // validasi 2: password sama confirm harus sama persis
        if (!p.equals(c)) {
            JOptionPane.showMessageDialog(this, "Password tidak sama!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // coba simpan user baru ke database
        boolean ok = Database.register(u, p);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Registrasi berhasil!");
            // kalau sukses, langsung lempar ke halaman login
            parent.setContentPane(new LoginPage(parent));
            parent.revalidate();
            parent.repaint();

        } else {
            // gagal biasanya karena username udah dipake orang lain
            JOptionPane.showMessageDialog(this, "Username sudah digunakan!", "Registrasi Gagal",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}