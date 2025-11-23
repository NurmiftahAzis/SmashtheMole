package popuppanic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {

    private JFrame parent;
    private int userId;
    private String username;

    private List<Hole> holes = new ArrayList<>();
    private List<Mole> moles = new ArrayList<>();
    private List<Bomb> bombs = new ArrayList<>();

    private SpawnManager spawner;
    private Thread spawnThread;
    private GameLoop loop;
    private Timer countdown;

    private int score = 0;
    private int timeLeft = 60;
    private boolean gameOver = false;

    private final Color GREEN = new Color(0x136021);

    public GamePanel(JFrame parent, int userId, String username) {
        // TODO: inisialisasi panel game dan memulai permainan
    }

    private void initField() {
        // TODO: mengatur posisi objek (hole, mole, bomb)
    }

    private void startGame() {
        // TODO: memulai permainan dan semua komponen game
    }

    private void endGame(boolean bombHit) {
        // TODO: logika ketika game berakhir
    }

    private void saveScoreToDB() {
        // TODO: menyimpan skor ke database
    }

    private final MouseAdapter mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            // TODO: menangani klik player pada game
        }
    };

    @Override
    protected void paintComponent(Graphics g0) {
        // TODO: menggambar UI, skor, waktu, dan render objek game
    }
}
