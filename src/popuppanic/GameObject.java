package popuppanic;

import java.awt.*;

public abstract class GameObject implements GameEntity {

    protected int x, y, width, height;
    protected boolean visible = false;

    public GameObject(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    // buat kotak batas objek, dipakai buat deteksi klik
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // method buat gambar objek
    public abstract void render(Graphics2D g);

    // method buat update logika objek tiap frame
    public abstract void update(long dt);

    // cek objek lagi tampil atau disembunyiin
    public boolean isVisible() {
        return visible;
    }

    // atur objek mau ditampilin atau engga
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    // ambil posisi X objek
    public int getX() {
        return x;
    }

    // ambil posisi Y objek
    public int getY() {
        return y;
    }
}
