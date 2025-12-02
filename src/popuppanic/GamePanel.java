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

    // list buat nampung semua objek di layar
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
        this.parent = parent;
        this.userId = userId;
        this.username = username;

        setLayout(null);
        setBackground(Color.decode("#261702")); // background coklat gelap

        initField(); // siapin posisi lubang
        addMouseListener(mouseListener); // biar bisa diklik mouse
        startGame();
    }

    private void initField() {
        int holeW = 220, holeH = 130;
        int moleW = 180, moleH = 180;
        int bombW = 180, bombH = 180;

        int startX = 40;
        int startY = 90;

        int gapX = 230;
        int gapY = 150;

        // bersihin list dulu biar gak dobel
        holes.clear();
        moles.clear();
        bombs.clear();

        // loop buat bikin grid 3 baris 3 kolom
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {

                int x = startX + c * gapX;
                int y = startY + r * gapY;

                holes.add(new Hole(x, y, holeW, holeH));

                // posisi tikus/bom ditengahin dikit dari lubang
                int moleX = x + (holeW - moleW) / 2;
                int moleY = y + (holeH - moleH) / 2 - 15;

                moles.add(new Mole(moleX, moleY, moleW, moleH));
                bombs.add(new Bomb(moleX, moleY, bombW, bombH));
            }
        }
    }

    private void startGame() {
        score = 0;
        timeLeft = 60;
        gameOver = false; // reset status game

        // jalanin thread buat ngatur munculnya tikus/bom
        spawner = new SpawnManager(moles, bombs);
        spawnThread = new Thread(spawner);
        spawnThread.start();

        // gabungin semua objek jadi satu list buat di-loop
        List<GameObject> all = new ArrayList<>();
        all.addAll(holes);
        all.addAll(moles);
        all.addAll(bombs);

        // mulai game loop utama
        loop = new GameLoop(all, this);
        loop.start();

        // timer mundur per detik
        countdown = new Timer(1000, e -> {
            timeLeft--;
            if (timeLeft <= 0)
                endGame(false); // waktu abis, game over
        });
        countdown.start();
    }

    private void endGame(boolean bombHit) {
        if (gameOver)
            return; // biar gak jalan dua kali
        gameOver = true;

        // matiin semua thread biar gak error/berat pas ganti layar
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

        saveScoreToDB(); // simpen skor ke db

        // pindah ke layar game over
        SwingUtilities.invokeLater(() -> {
            parent.setContentPane(new GameOverPanel(parent, score, bombHit, userId, username));
            parent.revalidate();
            parent.repaint();
        });
    }

    private void saveScoreToDB() {
        // coba connect ke db buat simpen skor
        try (Connection conn = Database.getConnection()) {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO scores(user_id, score) VALUES (?,?)");
                ps.setInt(1, userId);
                ps.setInt(2, score);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Gagal simpen skor: " + e.getMessage());
            // biarin aja kalau gagal, yang penting game gak crash
        }
    }

    private final MouseAdapter mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (gameOver)
                return; // kalo udah kalah gak bisa klik lagi

            Point p = e.getPoint();

            // Cek BOM duluan (karena dia paling bahaya)
            for (Bomb b : bombs) {
                if (b.contains(p)) {
                    endGame(true); // kena bom langsung kalah
                    return;
                }
            }

            // Cek TIKUS
            for (Mole m : moles) {
                if (m.contains(p)) {
                    score += 10; // nambah poin
                    m.onClick(); // animasi kepukul
                    return;
                }
            }

            // Kalau klik tanah/meleset, skor dikurangin biar gak spam klik
            score = Math.max(0, score - 2);
        }
    };

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // gambar UI skor sama waktu di atas
        g.setFont(new Font("Arial", Font.BOLD, 38));
        g.setColor(Color.WHITE);
        g.drawString("SCORE :", 20, 60);
        g.setColor(GREEN);
        g.drawString(String.valueOf(score), 200, 60);

        g.setColor(Color.WHITE);
        g.drawString("TIME :", getWidth() - 240, 60);
        g.setColor(GREEN);
        g.drawString(String.valueOf(timeLeft), getWidth() - 100, 60);

        // Render URUTAN PENTING! Lubang paling bawah, baru tikus/bom di atasnya
        for (Hole h : holes)
            h.render(g);
        for (Mole m : moles)
            m.render(g);
        for (Bomb b : bombs)
            b.render(g);
    }
}