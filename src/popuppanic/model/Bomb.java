package popuppanic.model;

import java.awt.*;

import popuppanic.core.GameObject;
import popuppanic.view.GamePanel;

public class Bomb extends GameObject {

    // gambar objek bom
    private Image img;

    // timer untuk menghitung berapa lama bom tampil
    private long visibleTimer = 0;

    // batas maksimal bom tampil (1.5 detik)
    private final long MAX_VISIBLE_DURATION = 1500;

    // lokasi file gambar bom
    private final String IMAGE_PATH = "src/assets/bomb.png";

    // constructor: set posisi, ukuran, dan load gambar bom
    public Bomb(int x, int y, int w, int h) {
        super(x, y, w, h);

        try {
            img = Toolkit.getDefaultToolkit().getImage(IMAGE_PATH);
        } catch (Exception e) {
            System.err.println("Error Loading Bomb Image: " + e.getMessage());
        }
    }

    // update logika bom tiap frame (hitung durasi tampil)
    @Override
    public void update(long dt) {
        if (isVisible()) {
            visibleTimer += dt; // tambah waktu tampil

            // kalo durasi udah lewat batas, sembunyiin bom lagi
            if (visibleTimer >= MAX_VISIBLE_DURATION) {
                setVisible(false);
                visibleTimer = 0;
            }
        }
    }

    // gambar bom di layar kalau lagi tampil
    @Override
    public void render(Graphics2D g) {
        if (isVisible()) {
            g.drawImage(img, x, y, width, height, null);
        }
    }

    // cek klik mouse kena area bom atau engga
    @Override
    public boolean contains(Point p) {
        return isVisible() && getBounds().contains(p);
    }

    // aksi kalau bom diklik
    @Override
    public void onClick() {
        System.out.println("Bomb clicked! GAME OVER!"); // debug di terminal
        GamePanel.game.playSoundEffect("src/assets/explosion.wav"); // mainkan suara ledakan
        GamePanel.game.triggerGameOver(true); // langsung game over karena klik bom
        setVisible(false); // hilangkan bom dari layar
        visibleTimer = 0; // reset timer
    }

    // munculin bom selama durasi yang ditentukan
    public void popUp(long duration) {
        setVisible(true);
        visibleTimer = 0;
    }
}