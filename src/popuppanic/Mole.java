package popuppanic;

import java.awt.*;

public class Mole extends GameObject {

    private Image img;
    private long visibleTimer = 0;

    private final long MAX_VISIBLE_DURATION = 1500;
    private final String IMAGE_PATH = "src/assets/mole.png";

    public Mole(int x, int y, int w, int h) {
        super(x, y, w, h);

        // load gambar tikus
        try {
            this.img = Toolkit.getDefaultToolkit().getImage(IMAGE_PATH);
        } catch (Exception e) {
            System.err.println("error Loading Mole Image: " + e.getMessage());
        }
    }

    @Override
    public void update(long dt) {
        // ngitung durasi tikus kelihatan
        if (isVisible()) {
            visibleTimer += dt;

            // kalau udah kelamaan muncul, ilangin lagi
            if (visibleTimer >= MAX_VISIBLE_DURATION) {
                setVisible(false);
                visibleTimer = 0;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        // gambar tikus kalau lagi muncul
        if (isVisible()) {
            g.drawImage(img, x, y, width, height, null);
        }
    }

    @Override
    public boolean contains(Point p) {
        // cek klik kena tikus atau engga
        if (!isVisible())
            return false; // kalau lagi ngumpet, ga bisa diklik

        return getBounds().contains(p); // true kalau klik pas di area tikus
    }

    @Override
    public void onClick() {
        // aksi pas tikus kena pukul
        if (isVisible()) {
            System.out.println("Mole clicked! +10 point!"); // debug di terminal
            setVisible(false); // ilangin tikus abis dipukul
            visibleTimer = 0; // reset timer
        }
    }

    public void popUp(long duration) {
        // munculin tikus
        setVisible(true);
        visibleTimer = 0; // reset batas tampil
    }
}