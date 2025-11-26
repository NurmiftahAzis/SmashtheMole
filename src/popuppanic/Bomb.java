package popuppanic;

import java.awt.*;
import javax.swing.*;

public class Bomb extends GameObject {

    private Image img;
    private boolean visible = false;
    private long visibleTimer = 0;
    private final long MAX_VISIBLE_DURATION = 1500;

    private final String IMAGE_PATH = "assets/bomb.png";

    public Bomb(int x, int y, int w, int h) {
        super(x, y, w, h);
        // memuat gambar bom
        try {
            this.img = Toolkit.getDefaultToolkit().getImage(IMAGE_PATH);
        } catch (Exception e) {
            System.err.println("error Loading Bomb Image: " + e.getMessage());
            this.img = null;
        }
    }

    @Override
    public void update(long dt) {
        // memperbarui status bom berdasarkan waktu
        if (visible) {
            visibleTimer += dt;

            if (visibleTimer >= MAX_VISIBLE_DURATION) {
                visible = false;
                visibleTimer = 0;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        // menggambar bom jika tampil
        if (visible) {
            if (img != null) {
                g.drawImage(img, x, y, width, height, null);
            } else {

                // pengganti jika gambar gagal dimuat
                g.setColor(Color.RED);
                g.fillRect(x, y, width, height);
                g.setColor(Color.BLACK);
                g.drawString("BOMB", x + width/4, y + height/2);
            }
        }
    }

    @Override
    public boolean contains(Point p) {
        // memeriksa apakah titik klik berada di area bom
        if (!visible) return false;

        return getBounds().contains(p);
    }

    @Override
    public void onClick() {
        // aksi ketika bom diklik
        if (visible) {
            System.out.println("Bomb clicked! GAME OVER!");
            visible = false;
            visibleTimer = 0;
        }
    }

    public boolean isVisible() {
        // mengembalikan status apakah bom sedang terlihat
        return visible;
    }

    public void popUp(long duration) {
        // menampilkan bom selama durasi tertentu
        this.visible = true;
        this.visibleTimer = 0; // reset timer
    }
}
