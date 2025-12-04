package popuppanic.view;

import javax.swing.*;

import popuppanic.controller.GameLoop;
import popuppanic.controller.SpawnManager;
import popuppanic.core.GameObject;
import popuppanic.model.Bomb;
import popuppanic.model.Database;
import popuppanic.model.Hole;
import popuppanic.model.Mole;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;
import java.io.File;

public class GamePanel extends JPanel {

    // instance global (biar bisa diakses dari Mole/Bomb buat skor & game over)
    public static GamePanel game;

    private JFrame parent;
    private int userId;
    private String username;

    // list objek game
    private List<GameObject> objects = new ArrayList<>();
    private List<Hole> holes = new ArrayList<>();
    private List<Mole> moles = new ArrayList<>();
    private List<Bomb> bombs = new ArrayList<>();

    // thread & loop game
    private SpawnManager spawner; // generate mole/bomb
    private Thread spawnThread; // thread untuk spawner
    private GameLoop loop; // update setiap frame
    private Timer countdown; // timer mundur

    private int score = 0;
    private int timeLeft = 60;
    private boolean gameOver = false;

    private final Color GREEN = new Color(0x136021);

    // musik background gameplay
    private Clip audioClip;

    // constructor: setup panel & mulai game
    public GamePanel(JFrame parent, int userId, String username) {
        this.parent = parent;
        this.userId = userId;
        this.username = username;
        GamePanel.game = this; // simpan instance aktif

        setLayout(null);
        setBackground(Color.decode("#261702"));

        initField(); // buat objek lubang, mole, dan bomb
        addMouseListener(mouseListener);
        startGame(); // mulai gameplay
    }

    // load musik background
    private void loadSound() {
        try {
            File audioFile = new File("src/assets/game-music.wav");
            if (audioFile.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                audioClip = AudioSystem.getClip();
                audioClip.open(audioStream);
            } else {
                System.out.println("File audio tidak ditemukan!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // mainkan efek suara sekali (hit / bomb)
    public void playSoundEffect(String filePath) {
        try {
            File sfx = new File(filePath);
            if (sfx.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(sfx);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } else {
                System.out.println("Sound effect tidak ditemukan: " + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // buat objek game dan posisikan dalam grid 3x3
    private void initField() {
        int holeW = 220, holeH = 130;
        int moleW = 180, moleH = 180;
        int bombW = 180, bombH = 180;

        int startX = 40, startY = 90;
        int gapX = 230, gapY = 150;

        // kosongkan semua list dulu
        holes.clear();
        moles.clear();
        bombs.clear();
        objects.clear();

        // bangun layout grid objek game 3x3
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {

                int x = startX + c * gapX;
                int y = startY + r * gapY;

                Hole hole = new Hole(x, y, holeW, holeH);
                holes.add(hole);

                // posisikan mole & bomb di tengah lubang
                int moleX = x + (holeW - moleW) / 2;
                int moleY = y + (holeH - moleH) / 2 - 15;

                Mole mole = new Mole(moleX, moleY, moleW, moleH);
                Bomb bomb = new Bomb(moleX, moleY, bombW, bombH);

                moles.add(mole);
                bombs.add(bomb);

                // masukkan semua jenis objek sebagai GameObject (polymorphism)
                objects.add(hole);
                objects.add(mole);
                objects.add(bomb);
            }
        }
    }

    // mulai gameplay: reset skor, start loop & timer
    private void startGame() {
        score = 0;
        timeLeft = 60;
        gameOver = false;

        loadSound();
        if (audioClip != null) {
            audioClip.loop(Clip.LOOP_CONTINUOUSLY); // putar musik berulang
            audioClip.start();
        }

        // jalankan spawner mole/bomb
        spawner = new SpawnManager(moles, bombs);
        spawnThread = new Thread(spawner);
        spawnThread.start();

        // jalankan update loop
        loop = new GameLoop(objects, this);
        loop.start();

        // hitung mundur waktu
        countdown = new Timer(1000, e -> {
            timeLeft--;
            if (timeLeft <= 0)
                triggerGameOver(false);
        });
        countdown.start();
    }

    // tambah skor (dengan minimum 0)
    public void addScore(int value) {
        score = Math.max(0, score + value);
    }

    // terima sinyal game over dari Object lain
    public void triggerGameOver(boolean bombHit) {
        endGame(bombHit);
    }

    // proses akhir game
    private void endGame(boolean bombHit) {
        if (gameOver)
            return;
        gameOver = true;

        // stop musik gameplay
        try {
            if (audioClip != null && audioClip.isRunning())
                audioClip.stop();
        } catch (Exception ignored) {
        }

        // hentikan semua thread & timer
        try {
            spawner.stop();
        } catch (Exception ignored) {
        }
        try {
            spawnThread.interrupt();
        } catch (Exception ignored) {
        }
        try {
            loop.terminate();
        } catch (Exception ignored) {
        }
        try {
            countdown.stop();
        } catch (Exception ignored) {
        }

        saveScoreToDB(); // simpan skor

        // pindah ke layar Game Over
        SwingUtilities.invokeLater(() -> {
            parent.setContentPane(new GameOverPanel(parent, score, bombHit, userId, username));
            parent.revalidate();
            parent.repaint();
        });
    }

    // simpan skor terakhir ke database
    private void saveScoreToDB() {
        try (Connection conn = Database.getConnection()) {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO scores(user_id, score) VALUES (?,?)");
                ps.setInt(1, userId);
                ps.setInt(2, score);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Gagal simpen skor: " + e.getMessage());
        }
    }

    // event klik mouse pada objek game
    private final MouseAdapter mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (gameOver)
                return;

            Point p = e.getPoint();

            // cek klik kena objek
            for (GameObject obj : objects) {
                if (obj.contains(p)) {
                    obj.onClick();
                    return;
                }
            }

            // klik area kosong = penalti
            addScore(-2);
        }
    };

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // tampilin score & waktu di atas
        g.setFont(new Font("Arial", Font.BOLD, 38));
        g.setColor(Color.WHITE);
        g.drawString("SCORE :", 20, 60);
        g.setColor(GREEN);
        g.drawString(String.valueOf(score), 200, 60);

        g.setColor(Color.WHITE);
        g.drawString("TIME :", getWidth() - 240, 60);
        g.setColor(GREEN);
        g.drawString(String.valueOf(timeLeft), getWidth() - 100, 60);

        // gambar objek game (urutan: lubang -> mole -> bomb)
        for (GameObject obj : objects) {
            obj.render(g);
        }
    }
}