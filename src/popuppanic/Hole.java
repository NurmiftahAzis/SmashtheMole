package popuppanic;

import java.awt.*;
import javax.swing.*;

public class Hole extends GameObject {

    private Image img;

    public Hole(int x, int y, int w, int h) {
        super(x, y, w, h);
        // TODO: inisialisasi gambar lubang
    }

    @Override
    public void update(long dt) {
        // TODO: update state lubang (jika diperlukan)
    }

    @Override
    public void render(Graphics2D g) {
        // TODO: menggambar lubang ke layar
    }

    @Override
    public boolean contains(Point p) {
        // TODO: cek apakah lubang diklik
        return false;
    }

    @Override
    public void onClick() {
        // TODO: aksi saat lubang diklik
    }
}
