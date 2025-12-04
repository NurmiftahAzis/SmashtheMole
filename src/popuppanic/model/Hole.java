package popuppanic.model;

import java.awt.*;
import javax.swing.*;

import popuppanic.core.GameObject;

public class Hole extends GameObject {

    // gambar lubang tempat munculnya tikus atau bom
    private Image img;

    // constructor buat set posisi, ukuran, dan load gambar lubang
    public Hole(int x, int y, int w, int h) {
        super(x, y, w, h);
        img = new ImageIcon("src/assets/hole.png").getImage();
        setVisible(true); // lubang selalu tampil
    }

    // lubang tidak punya logika update khusus
    @Override
    public void update(long dt) {
    }

    // gambar lubang di layar
    @Override
    public void render(Graphics2D g) {
        g.drawImage(img, x, y, width, height, null);
    }

    // lubang tidak bisa diklik, jadi selalu return false
    @Override
    public boolean contains(Point p) {
        return false;
    }

    // tidak ada aksi saat lubang diklik
    @Override
    public void onClick() {
    }
}