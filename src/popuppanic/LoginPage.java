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
        // TODO: inisialisasi panel login
    }

    private void buildUI() {
        // TODO: membuat tampilan UI login (judul, form, tombol)
    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO: menggambar footer atau elemen visual tambahan
    }

    private void styleField(JTextField f) {
        // TODO: styling untuk field input
    }

    private void doLogin() {
        // TODO: melakukan proses login ke database
    }
}
