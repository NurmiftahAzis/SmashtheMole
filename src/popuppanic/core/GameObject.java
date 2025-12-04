package popuppanic.core;

import java.awt.*;

public abstract class GameObject implements GameEntity {

    // posisi dan ukuran objek di game
    protected int x, y, width, height;

    // status objek lagi tampil atau engga
    protected boolean visible = false;

    // constructor buat set posisi & ukuran awal
    public GameObject(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    // buat kotak batas objek, dipakai buat deteksi klik / collision
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // cek objek lagi tampil atau disembunyiin
    public boolean isVisible() {
        return visible;
    }

    // atur objek mau ditampilin atau engga
    public void setVisible(boolean v) {
        this.visible = v;
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