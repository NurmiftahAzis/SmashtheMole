package popuppanic;

import java.awt.*;
import javax.swing.*;

public class Bomb extends GameObject {

    private Image img;
    private boolean visible = false;
    private long visibleTimer = 0;

    public Bomb(int x, int y, int w, int h) {
        super(x, y, w, h);
        // TODO: memuat gambar bom
    }

    @Override
    public void update(long dt) {
        // TODO: memperbarui status bom berdasarkan waktu
    }

    @Override
    public void render(Graphics2D g) {
        // TODO: menggambar bom jika tampil
    }

    @Override
    public boolean contains(Point p) {
        // TODO: memeriksa apakah titik klik berada di area bom
        return false;
    }

    @Override
    public void onClick() {
        // TODO: aksi ketika bom diklik
    }

    public boolean isVisible() {
        // TODO: mengembalikan status apakah bom sedang terlihat
        return false;
    }

    public void popUp(long duration) {
        // TODO: menampilkan bom selama durasi tertentu
    }
}
