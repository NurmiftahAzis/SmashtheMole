package popuppanic;

import java.awt.*;

public class Bomb extends GameObject {

    private Image img;
    private long visibleTimer = 0;
    private final long MAX_VISIBLE_DURATION = 1500;

    private final String IMAGE_PATH = "src/assets/bomb.png";

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
        if (isVisible()) {
            visibleTimer += dt;

            if (visibleTimer >= MAX_VISIBLE_DURATION) {
                setVisible(false);
                visibleTimer = 0;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        // menggambar bom jika tampil
        if (isVisible()) {
            if (img != null) {
                g.drawImage(img, x, y, width, height, null);
            } else {
                // pengganti jika gambar gagal dimuat
                g.setColor(Color.RED);
                g.fillRect(x, y, width, height);
                g.setColor(Color.BLACK);
                g.drawString("BOMB", x + width / 4, y + height / 2);
            }
        }
    }

    @Override
    public boolean contains(Point p) {
        // memeriksa apakah titik klik berada di area bom
        if (!isVisible()) // gunakan getter
            return false;

        return getBounds().contains(p);
    }

    @Override
    public void onClick() {
        // aksi ketika bom diklik
        if (isVisible()) {
            System.out.println("Bomb clicked! GAME OVER!");
            setVisible(false); // gunakan setter
            visibleTimer = 0;
        }
    }

    public void popUp(long duration) {
        // menampilkan bom selama durasi tertentu
        setVisible(true);
        visibleTimer = 0; // reset timer
    }

    public void spawn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'spawn'");
    }

    @Override
    protected void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
