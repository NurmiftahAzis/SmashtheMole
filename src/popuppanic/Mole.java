package popuppanic;

import java.awt.*;
import javax.swing.*;

public class Mole extends GameObject {

    private Image img;
    private boolean visible = false;
    private long visibleTimer = 0;

    public Mole(int x, int y, int w, int h) {
        super(x, y, w, h);
        // TODO: memuat gambar mole
    }

    @Override
    public void update(long dt) {
        // TODO: update timer kemunculan mole
    }

    @Override
    public void render(Graphics2D g) {
        // TODO: menggambar mole jika sedang terlihat
    }

    @Override
    public boolean contains(Point p) {
        // TODO: cek apakah mole diklik
        return false;
    }

    @Override
    public void onClick() {
        // TODO: aksi ketika mole diklik
    }

    public boolean isVisible() {
        // TODO: mengembalikan status visibility mole
        return false;
    }

    public void popUp(long duration) {
        // TODO: menampilkan mole selama durasi tertentu
    }
}
