package popuppanic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartPanel extends JPanel {

    private JFrame parent;
    private int userId;
    private String username;

    private final Color BG = Color.decode("#261702");
    private final Color GREEN = new Color(0x136021);
    private final Color BROWN = new Color(0x755B38);

    public StartPanel(JFrame parent, int userId, String username) {
        // TODO: inisialisasi panel start
    }

    private void initUI() {
        // TODO: membangun seluruh tampilan UI start menu
    }

    private JLabel createIcon(String path) {
        // TODO: memuat dan mengubah ukuran icon
        return null;
    }

    private JButton createButton(String text, Color bg, Color borderColor) {
        // TODO: membuat tombol dengan style khusus
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO: menggambar footer atau elemen visual tambahan
    }
}
