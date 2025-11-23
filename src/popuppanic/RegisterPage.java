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
        // TODO: inisialisasi panel register
    }

    private void buildUI() {
        // TODO: membangun tampilan UI register (judul, form, tombol, link login)
    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO: menggambar footer atau elemen visual lain
    }

    private void styleField(JTextField f) {
        // TODO: styling untuk field input
    }

    private void doRegister() {
        // TODO: proses registrasi user baru ke database
    }
}
