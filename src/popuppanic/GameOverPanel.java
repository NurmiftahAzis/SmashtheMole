package popuppanic;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {

    private final Color BG = Color.decode("#261702");
    private final Color GREEN = new Color(0x136021);
    private final Color BROWN = new Color(0x755B38);
    private final Color RED = Color.decode("#FF3131");

    private int userId;
    private String username;
    private JFrame parent;

    public GameOverPanel(JFrame parent, int score, boolean bomb, int userId, String username) {
        // TODO: inisialisasi panel Game Over
    }

    private void restartGame() {
        // TODO: logika untuk memulai ulang game
    }

    private void backToMenu() {
        // TODO: logika untuk kembali ke menu utama
    }

    private JPanel createTextPanel() {
        // TODO: membuat panel teks
        return null;
    }

    private JLabel createLabel(String text, Color color, int size) {
        // TODO: membuat label dengan warna dan ukuran tertentu
        return null;
    }

    private JButton createButton(String text, Color bg, Color borderCol) {
        // TODO: membuat tombol dengan style tertentu
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO: menggambar background/footer
    }
}
