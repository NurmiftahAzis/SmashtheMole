package popuppanic;

import java.awt.*;
import javax.swing.*;

public class Hole extends GameObject {

    private Image img;

    public Hole(int x, int y, int w, int h) {
        super(x, y, w, h);
        // load gambar lubang
        img = new ImageIcon("src/assets/hole.png").getImage();
        setVisible(true); // hole selalu terlihat
    }

    @Override
    public void update(long dt) {
        // kosong, soalnya lubang gak gerak atau ngapa-ngapain
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(img, x, y, width, height, null);
    }

    @Override
    public boolean contains(Point p) {
        return false; // lubang gak bisa diklik, jadi return false terus
    }

    @Override
    public void onClick() {
    } // gak ada aksi apa2
}