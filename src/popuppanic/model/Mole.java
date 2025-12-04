package popuppanic.model;

import java.awt.*;

import popuppanic.core.GameObject;
import popuppanic.view.GamePanel;

public class Mole extends GameObject {

    // gambar tikus
    private Image img;

    // timer buat ngatur berapa lama tikus muncul
    private long visibleTimer = 0;

    // durasi maksimal tikus tampil (1.5 detik)
    private final long MAX_VISIBLE_DURATION = 1500;

    // lokasi file gambar tikus
    private final String IMAGE_PATH = "src/assets/mole.png";

    // constructor: set posisi, ukuran, dan load gambar tikus
    public Mole(int x, int y, int w, int h) {
        super(x, y, w, h);

        try {
            img = Toolkit.getDefaultToolkit().getImage(IMAGE_PATH);
        } catch (Exception e) {
            System.err.println("Error loading Mole Image: " + e.getMessage());
        }
    }

    // update durasi tampil tiap frame
    @Override
    public void update(long dt) {
        if (isVisible()) {
            visibleTimer += dt; // tambah waktu tampil

            // kalo udah lewat batas durasi, sembunyiin tikus lagi
            if (visibleTimer >= MAX_VISIBLE_DURATION) {
                setVisible(false);
                visibleTimer = 0;
            }
        }
    }

    // gambar tikus kalo lagi muncul
    @Override
    public void render(Graphics2D g) {
        if (isVisible()) {
            g.drawImage(img, x, y, width, height, null);
        }
    }

    // cek klik mouse kena area tikus
    @Override
    public boolean contains(Point p) {
        return isVisible() && getBounds().contains(p);
    }

    // aksi kalo tikus diklik
    @Override
    public void onClick() {
        if (isVisible()) {
            System.out.println("Mole clicked! +10 point!"); // debug di terminal
            GamePanel.game.addScore(10); // tambah skor
            GamePanel.game.playSoundEffect("src/assets/hit.wav"); // suara pukulan
            setVisible(false); // sembunyiin tikus
            visibleTimer = 0; // reset timer
        }
    }

    // munculin tikus dengan timer baru
    public void popUp(long duration) {
        setVisible(true);
        visibleTimer = 0;
    }
}